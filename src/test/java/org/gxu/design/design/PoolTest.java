package org.gxu.design.design;

import org.gxu.design.util.SendMsgByMail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@SpringBootTest
public class PoolTest {

    @Autowired
    ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    SendMsgByMail sendTool;
    /*@Test
    public void commitTask() throws ExecutionException, InterruptedException {
        Future<Boolean> submit = taskExecutor.submit(() -> {
            return sendTool.sendSimpleText(new String[]{"1062459694@qq.com"}, "ssss", "测试");
        });
        System.out.println(submit.get());
    }*/
}
