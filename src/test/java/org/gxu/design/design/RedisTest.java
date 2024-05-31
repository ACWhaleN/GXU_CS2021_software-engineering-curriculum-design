package org.gxu.design.design;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * redis function test
 */
@SpringBootTest
@Slf4j
public class RedisTest {
    @Autowired
    RedisTemplate redisTemplate;

    /*@Test
    public void printIt(){
        log.info("{}",redisTemplate);
        ValueOperations operations = redisTemplate.opsForValue();
        System.out.println(operations.get("code"));
    }*/
}
