package org.gxu.design.socket

import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import io.netty.util.AttributeKey
import java.util.*


val SESSION_KEY: AttributeKey<Session>? = AttributeKey.valueOf("SESSION_KEY")

/**
 * 会话
 */
class Session {
    var channel:Channel
    lateinit var user:Any
    var sessionId:String

    constructor( channel: Channel){
        this.channel = channel
        sessionId = generateSessionId()
        channel.attr(SESSION_KEY).set(this) // 当前通道跟session绑定
    }

    /**
     * 生成sessionId
     */
    private fun generateSessionId(): String {
        val toString = UUID.randomUUID().toString()
        return toString.replace("-","")

    }
    // 伴生实例
    companion object{
        fun getSession(ctx:ChannelHandlerContext):Session{
            return ctx.channel().attr(SESSION_KEY).get()
        }
    }
}