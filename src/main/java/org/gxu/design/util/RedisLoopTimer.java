package org.gxu.design.util;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 定期清除内存数据
 */
@Component
@Slf4j
public class RedisLoopTimer extends Timer implements InitializingBean {
    private static final long ONEDAY_TIMESTAMP = 1000 * 60 * 60 * 24L;
    @Resource(name = "redisLock")
    private ReentrantLock redisLock;
    private RedisTemplate redisTemplate;


    public RedisLoopTimer(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;

    }

    @Override
    public void afterPropertiesSet() throws Exception {
      // 程序启动后每周执行一次任务
      schedule(new RedisLoopTasker() , 7 * ONEDAY_TIMESTAMP, 7 * ONEDAY_TIMESTAMP);
    }

    private class RedisLoopTasker extends TimerTask{

        @Override
        public void run() {
            long startTaskTime = System.currentTimeMillis();
            log.info("清理redis数据任务开始时间:{}",startTaskTime);
            val redis = redisTemplate.opsForValue();
            try {
                redisLock.lock();
                Set<String> keys = redisTemplate.keys("*");
                Set<String> needToDel = new HashSet<>(keys.size());
                keys.forEach((key)->{
                    // 被认领了才有资格删除
                    if (key.contains("itemState") && (int)redis.get(key) == 1 ) {
                        val foundApplyId = key.substring(0,key.indexOf(":"));
                        needToDel.add(foundApplyId);
                    }
                });
                keys.forEach((key)->{
                    val id = key.substring(0,key.indexOf(":"));
                    // 在删除名单中
                    if (needToDel.contains(id)){
                        redisTemplate.delete(key);
                    }
                });
                log.info("清理任务完成,耗时:{}ms",System.currentTimeMillis() - startTaskTime);
            }catch (Exception e){
                // don't solve
                log.info("数据清理异常!");

            }
            finally {
                redisLock.unlock();
            }
        }
    }
}
