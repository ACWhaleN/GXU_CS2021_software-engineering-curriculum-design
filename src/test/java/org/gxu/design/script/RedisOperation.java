package org.gxu.design.script;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 封装一些redis的操作
 */
@SpringBootTest
@Slf4j
public class RedisOperation {

    @Autowired
    RedisTemplate redisTemplate;

    @Resource
    ReentrantLock lock;
    /**
     * 删除redis所有数据
     */
    @Test
    public void delAllData(){
        try {
            lock.lock();
            val keys = redisTemplate.keys("*");
            redisTemplate.delete(keys);
        }finally {
            lock.unlock();
        }
    }

    /**
     * 查询redis所有数据
     */
    @Test
    public void queryAllData(){
        try {
            lock.lock();
            val redis = redisTemplate.opsForValue();
            val keys = redisTemplate.keys("*");
            keys.forEach((k)->{
                val one = redis.get(k);
                log.info("数据:{}={}",k,one);
            });
        }catch (Exception e){
            // ?
        }finally {
            lock.unlock();
        }
    }

    /**
     * 根据key获取数据
     */
    @Test
    public void getOneByKey(){
        Scanner sc = new Scanner(System.in);
        String key = sc.nextLine();
        try {
            lock.lock();
            log.info("数据为:{}",redisTemplate.opsForValue().get(key));
        }finally {
            lock.unlock();
            sc.close();
        }
    }


}
