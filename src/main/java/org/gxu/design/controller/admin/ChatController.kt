package org.gxu.design.controller.admin

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import org.gxu.design.entity.Admin
import org.gxu.design.entity.MessageItems
import org.gxu.design.entity.User
import org.gxu.design.mapper.AdminMapper
import org.gxu.design.mapper.MessageItemsMapper
import org.gxu.design.mapper.UserMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
open class ChatController {
    @Autowired
    lateinit var messageItemsMapper: MessageItemsMapper
    @Autowired
    lateinit var userMapper: UserMapper
    @Autowired
    lateinit var adminMapper: AdminMapper

    open fun viewMsg(senderId: Int?, receiverId: Int?): List<Map<String, Any>> {
        val queryWrapper = QueryWrapper<MessageItems>()
        queryWrapper.eq(senderId != null, "adminId", senderId)
        queryWrapper.eq(receiverId != null, "userId", receiverId)

        val res = messageItemsMapper.selectMaps(queryWrapper)
        res.forEach { it ->
            val adminSendId = it["adminId"] as Int
            val userReceiveId = it["userId"] as Int

            it["senderMsg"] = adminMapper.selectById(adminSendId)
            it["receiverMsg"] = userMapper.selectById(userReceiveId)
        }
        return res
    }

    open fun sendMsg(msg: String, senderId: Int, receiverId: Int?): MessageItems {
        val admin = adminMapper.selectById(senderId)
        val user = userMapper.selectById(receiverId)
        val message = MessageItems.build(
            null, 0, admin, user, msg, Date(), 0)
        messageItemsMapper.insert(message)
        return message
    }

    open fun recallMsg(msgId: Int) {
        messageItemsMapper.deleteById(msgId)
    }
}
