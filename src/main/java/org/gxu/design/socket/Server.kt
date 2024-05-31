package org.gxu.design.socket

import cn.hutool.json.JSONUtil
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.*
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler
import org.gxu.design.entity.Admin
import org.gxu.design.entity.MessageItems
import org.gxu.design.entity.User
import org.gxu.design.mapper.AdminMapper
import org.gxu.design.mapper.UserMapper
import org.gxu.design.mapper.MessageItemsMapper
import org.gxu.design.controller.admin.ChatController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.net.InetSocketAddress
import java.util.*
import kotlin.collections.HashMap

@Component
class Server {

    @Autowired
    lateinit var chatController: ChatController

    @Autowired
    lateinit var adminMapper: AdminMapper

    @Autowired
    lateinit var userMapper: UserMapper

    @Autowired
    lateinit var messageItemsMapper: MessageItemsMapper

    lateinit var channel: Channel
    val group: EventLoopGroup = NioEventLoopGroup(Runtime.getRuntime().availableProcessors())

    /** 启动服务 */
    fun start() {
        // TCP 通道
        val bootstrap = ServerBootstrap()
        bootstrap.group(group)
            .channel(NioServerSocketChannel::class.java)
            .childHandler(ChatServerInitializer(chatController, adminMapper, userMapper, messageItemsMapper))

        val bind = bootstrap.bind(InetSocketAddress(9090))
        bind.syncUninterruptibly() // 等待服务启动
        channel = bind.channel()

        // JVM关闭钩子
        Runtime.getRuntime().addShutdownHook(Thread { stop() })
    }

    /** 关闭服务 */
    fun stop() {
        channel.close()
        group.shutdownGracefully()
        println("netty服务器关闭!")
    }
}

/** 自定义入站处理器 */
class TextWebSocketFrameHandler(
    private val chatController: ChatController,
    private val adminMapper: AdminMapper,
    private val userMapper: UserMapper,
    private val messageItemsMapper: MessageItemsMapper
) : SimpleChannelInboundHandler<TextWebSocketFrame>() {

    override fun channelRead0(ctx: ChannelHandlerContext, msg: TextWebSocketFrame) {
        // 接收数据类型转换
        val data = JSONUtil.toBean(msg.text(), Map::class.java).toMap()
        val messageType = MessageType.valueOf(data[SEND_MSG_TYPE].toString().uppercase())

        when (messageType) {
            // 消息类型
            MessageType.INIT -> handleInitMsg(data, ctx)
            // 客户端发送信息
            MessageType.SEND -> handleSendMsg(data, ctx)
            // 删除信息
            MessageType.DEL -> handleDelMsg(data, ctx)
            // 处理已读消息
            MessageType.HADCHECK -> handleHadCheckMsg(data, ctx)
        }
    }

    override fun userEventTriggered(ctx: ChannelHandlerContext, evt: Any) {
        when (evt) {
            /** 协议从http升级到websocket时的回调 */
            is WebSocketServerProtocolHandler.HandshakeComplete -> {
                val session = Session(ctx.channel())
                SessionMap.sessionMap[session.sessionId] = session
                SessionMap.channelContextMap[session.sessionId] = ctx

                val sendMsg = HashMap<String, Any>()
                sendMsg["sessionId"] = session.sessionId
                sendMsg["type"] = "open"
                ctx.writeAndFlush(TextWebSocketFrame(JSONUtil.toJsonStr(sendMsg)))
            }
            else -> super.userEventTriggered(ctx, evt)
        }
    }

    /** 底层TCP连接断开回调 */
    override fun channelInactive(ctx: ChannelHandlerContext) {
        val session = Session.getSession(ctx)
        println("会话${session.sessionId}关闭")
        // 移除会话
        SessionMap.sessionMap.remove(session.sessionId)
        // 关闭通道
        ctx.close()
    }

    private fun handleInitMsg(data: Map<Any?, Any?>, ctx: ChannelHandlerContext) {
        val curUserId = (data[SENDER_USER_ID] as String).toIntOrNull()
        if (curUserId != null) {
            val session = Session.getSession(ctx)
            // 设置session对应的用户信息
            session.user = if (curUserId == 0) {
                adminMapper.selectById(curUserId)
            } else {
                userMapper.selectById(curUserId)
            }

            if (chatController != null) {
                val msgData = chatController.viewMsg(curUserId, null) + chatController.viewMsg(null, curUserId)

                // 构建待发送消息
                val msg = HashMap<String, Any>()
                msg[SEND_MSG_TYPE] = "init"
                msg[SEND_MSG_KEY] = msgData

                // 发送消息
                ctx.writeAndFlush(TextWebSocketFrame(JSONUtil.toJsonStr(msg)))
            }
        }
    }

    private fun handleSendMsg(data: Map<Any?, Any?>, ctx: ChannelHandlerContext) {
        val senderId = (data["senderId"] as String).toIntOrNull()
        val receiverId = (data["receiverId"] as String).toIntOrNull()
        val msg = data["msg"] as String

        if (senderId != null && receiverId != null) {
            // 储存消息
            val sendMsg = chatController.sendMsg(msg, senderId, receiverId)

            // 构建消息
            val res = HashMap<String, Any?>()
            res["type"] = "receive"
            res["data"] = sendMsg
            ctx.writeAndFlush(TextWebSocketFrame(JSONUtil.toJsonStr(res)))

            // 转发消息
            SessionMap.sessionMap.forEach {
                val value = it.value
                var isReceiver = false
                val user = value.user
                when (user) {
                    is User -> if (user.id == receiverId) isReceiver = true
                    is Admin -> if (user.id == receiverId) isReceiver = true
                }
                if (isReceiver) {
                    value.channel.writeAndFlush(TextWebSocketFrame(JSONUtil.toJsonStr(res)))
                }
            }
        }
    }

    private fun handleDelMsg(data: Map<Any?, Any?>, ctx: ChannelHandlerContext) {
        val msg = (data["msg"] as Map<String, Any>)
        val msgId = (msg["id"] as String).toIntOrNull()
        val receiverId = (msg["receiverId"] as String).toIntOrNull()

        if (msgId != null && receiverId != null) {
            // 被删除的消息
            val oldMessage = messageItemsMapper.selectById(msgId)
            chatController.recallMsg(msgId) // 删除该消息

            // 回显数据给发送者
            val sendMsg = HashMap<String, Any>()
            sendMsg["type"] = "del"
            sendMsg["data"] = oldMessage
            ctx.writeAndFlush(TextWebSocketFrame(JSONUtil.toJsonStr(sendMsg)))

            SessionMap.sessionMap.forEach {
                val session = it.value
                var isShouldSolve = false
                val user = session.user
                when (user) {
                    is Admin -> if (user.id == receiverId) isShouldSolve = true
                    is User -> if (user.id == receiverId) isShouldSolve = true
                }
                if (isShouldSolve) {
                    session.channel.writeAndFlush(TextWebSocketFrame(JSONUtil.toJsonStr(sendMsg)))
                }
            }
        }
    }

    private fun handleHadCheckMsg(data: Map<Any?, Any?>, ctx: ChannelHandlerContext) {
        val senderId = data["senderId"] as String
        val receiverId = data["receiverId"] as String

        val wrapper = UpdateWrapper<MessageItems>()
        wrapper.set("isUsed", 1).eq("adminId", senderId).eq("userId", receiverId)
        messageItemsMapper.update(null, wrapper)
    }
}

