package org.gxu.design.socket

import io.netty.channel.ChannelHandlerContext
import java.util.concurrent.ConcurrentHashMap

/**
 * 会话集合
 */
object SessionMap {
    val sessionMap:ConcurrentHashMap<String,Session> = ConcurrentHashMap()

    val channelContextMap:ConcurrentHashMap<String,ChannelHandlerContext> = ConcurrentHashMap()
    /**
     * 关闭所有会话
     */
    fun stop(){
        sessionMap.forEach {
            it.value.channel.close()
        }
    }
}