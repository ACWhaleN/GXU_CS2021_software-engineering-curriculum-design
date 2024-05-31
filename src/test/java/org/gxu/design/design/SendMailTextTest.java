package org.gxu.design.design;

import cn.hutool.extra.template.engine.thymeleaf.ThymeleafEngine;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.gxu.design.entity.User;
import org.gxu.design.mapper.UserMapper;
import org.gxu.design.util.SendMsgByMail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class SendMailTextTest {
    @Autowired
    JavaMailSender sender;
    @Autowired
    TemplateEngine engine;
    @Autowired
    SendMsgByMail sendMsgByMail;
    @Test
    public void testSend(){
        SimpleMailMessage m = new SimpleMailMessage();
        m.setSubject("邮箱主题");
        m.setFrom("1062459694@qq.com"); // 发送人
        m.setTo("1062459694@qq.com"); // 接收者
        m.setCc("2187803118@qq.com"); // 设置邮件抄送人
        m.setSentDate(new Date()); // 发送日期
        m.setText("发送的文本");
        sender.send(m);
    }
    @Test
    public void sendMoreContent() throws MessagingException {
        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setSubject("邮箱主题");
        mimeMessageHelper.setFrom("1062459694@qq.com"); // 发送人
        mimeMessageHelper.setTo("1062459694@qq.com"); // 接收者
        mimeMessageHelper.setCc("2187803118@qq.com"); // 设置邮件抄送人
        mimeMessageHelper.setSentDate(new Date()); // 发送日期
        Context context = new Context();
        context.setVariable("content","模板引擎测试");
        String process = engine.process("mailTest.html", context);
        mimeMessageHelper.setText(process,true);
        sender.send(mimeMessage);

    }

    @Test
    public void startToSendByOther(){
        boolean b = sendMsgByMail.sendSimpleText(new String[]{"1062459694@qq.com"}, "哈喽", "dads");
        System.out.println(b);
    }
    @Test
    public void sendFile() throws Exception {
        sendMsgByMail.sendCanHaveAttachment(new String[]{"1062459694@qq.com"},"test","hello 带附件了",new String[]{"index.html"},new File[]{new File("D:\\idea-workspace\\backenddesign1\\src\\main\\resources\\templates\\index.html")});


    }

    @Test
    public void sendByHtml(){
        Context context = new Context();
        context.setVariable("content","测试网页是否yes");
        //网页路径相对templates文件夹即可
        sendMsgByMail.sendByHTML(new String[]{"1062459694@qq.com"},"网页哟","mailTest.html",context);
    }

}
