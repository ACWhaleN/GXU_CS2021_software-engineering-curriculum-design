package org.gxu.design.socket

import org.gxu.design.controller.admin.ChatController
import org.gxu.design.entity.MessageItems
import org.gxu.design.mapper.AdminMapper
import org.gxu.design.mapper.MessageItemsMapper
import org.gxu.design.mapper.UserMapper
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

/**
 * 使用 spring 管理的实例
 */
var app: ApplicationContext? = null
var chatController: ChatController? = null
var userMapper: UserMapper? = null
var adminMapper: AdminMapper? = null
var messageItemsMapper: MessageItemsMapper? = null

/**
 * 全局常量
 */
enum class MessageType {
    INIT,
    SEND,
    DEL,
    HADCHECK
}
val SENDER_USER_ID = "curUserId"
val RECEIVE_MSG_TYPE = "type"
val SEND_MSG_TYPE = "type"
val RECEIVE_MSG_KEY = "data"
val SEND_MSG_KEY = "data"

@Component
class BeanUtil : ApplicationContextAware {
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        app = applicationContext
        initBean(applicationContext)
    }

    fun initBean(context: ApplicationContext) {
        chatController = context.getBean(ChatController::class.java)
        userMapper = context.getBean(UserMapper::class.java)
        adminMapper = context.getBean(AdminMapper::class.java)
        messageItemsMapper = context.getBean(MessageItemsMapper::class.java)
    }
}
