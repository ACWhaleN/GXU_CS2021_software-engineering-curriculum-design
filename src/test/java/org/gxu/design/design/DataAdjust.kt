package org.gxu.design.design

import cn.hutool.core.lang.UUID
import cn.hutool.core.util.RandomUtil
import org.gxu.design.mapper.UserMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.streams.toList

/**
 * 用户细微修改数据库的数据
 */
@SpringBootTest
class DataAdjust {


  /*  @Test
    fun editLost(){
        val file = File("D:\\期末复习\\软件工程\\数据库资料\\物品图片")
        if (file.isDirectory) {
            file.listFiles().forEach {
                println(it.name)

            }
        }
    }

    @Test
    fun UUIDTest(){
        val randomUUID = UUID.randomUUID(true)
        println(randomUUID)
    }
    *//**
     * 修改物品文件名
     *//*
    @Test
    fun realEditLost(){
        val basePath = "D:\\期末复习\\软件工程\\数据库资料\\物品图片\\"
        val list = lostSpecificationMapper.selectList(null)
        list.forEach {
            var fileName = it.picture
            val file = File("${basePath}${fileName}")
            val newFileName = UUID.randomUUID(true).toString().replace("-","") +  ".png"
            val newFile = File("${basePath}${newFileName}")
            file.renameTo(newFile)
            it.picture = newFileName
            lostSpecificationMapper.updateById(it)

        }
    }

    *//**
     * 生成found相关表的数据
     *//*
    @Test
    fun generateFoundData(){
        val users = userMapper.selectList(null)
        val usersId = ArrayList<String>(users.size) // 用户id
        users.forEach {
            usersId.add(it.id)
        }
        val selectList = lostSpecificationMapper.selectList(null)
        val addresses = selectList.stream().map {
            return@map it.address
        }.toList() // 地址
        val catalog = selectList.stream().map {
            return@map it.catalog
        }.toList() // 物品分类
        val itemNames = selectList.stream().map {
            it.itemName
        }.toList() // 物品名称
        val lostDate = selectList.stream().map {
            it.lostTime
        }.toList() // 时间
        // 生成found数据
        for(i in 1..10){
            val foundSpecification = FoundSpecification()
            val itemName = RandomUtil.randomEle(itemNames)
            val address = RandomUtil.randomEle(addresses)
            // 描述暂且为空
            val date = RandomUtil.randomEle(lostDate)
            val itemCatelog = RandomUtil.randomEle(catalog)
            val foundId = RandomUtil.randomEle(usersId)
            val founderId = RandomUtil.randomEle(usersId)
            if (foundId == founderId){
                continue
            }
            foundSpecification.itemName = itemName
            foundSpecification.address = address
            foundSpecification.foundTime = date
            foundSpecification.founderId = foundId
            foundSpecification.foundUserId = founderId
            // wait write
        }


    }*/
}