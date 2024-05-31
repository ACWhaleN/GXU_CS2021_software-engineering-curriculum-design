package org.gxu.design.socket

import io.netty.channel.Channel
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelPipeline
import io.netty.handler.codec.http.HttpObjectAggregator
import io.netty.handler.codec.http.HttpServerCodec
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler
import io.netty.handler.stream.ChunkedWriteHandler
import io.netty.handler.timeout.IdleStateHandler
import org.gxu.design.controller.admin.ChatController
import org.gxu.design.mapper.AdminMapper
import org.gxu.design.mapper.MessageItemsMapper
import org.gxu.design.mapper.UserMapper
import java.util.concurrent.TimeUnit

class ChatServerInitializer(
    private val chatController: ChatController,
    private val adminMapper: AdminMapper,
    private val userMapper: UserMapper,
    private val messageItemsMapper: MessageItemsMapper
) : ChannelInitializer<Channel>() {

    override fun initChannel(ch: Channel) {
        val pipeline = ch.pipeline()
        pipeline.addLast(WebSocketServerProtocolHandler("/ws"))
        pipeline.addLast(TextWebSocketFrameHandler(chatController, adminMapper, userMapper, messageItemsMapper))
    }
}