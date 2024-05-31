package org.gxu.design.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;
import java.util.Objects;

/**
 * 发送信息到邮箱
 */
@Component
@Data
public class SendMsgByMail {


    JavaMailSender sendTool;
    TemplateEngine engine; // 模板解析引擎
    private final String sender = "1062459694@qq.com";

    @Autowired
    public SendMsgByMail(JavaMailSender sendTool, TemplateEngine engine) {
        this.sendTool = sendTool;
        this.engine = engine;
    }

    /**
     * 发送普通文本
     * @param receiverMails 接收者们邮箱
     * @param subject 邮件主题
     * @param text 邮件信息
     * @return 是否发送成功
     */
    public boolean sendSimpleText(String[] receiverMails,String subject,String text){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(subject);
            message.setFrom(sender);
            message.setTo(receiverMails);
            message.setSentDate(new Date()); // 发送日期
            message.setText(text);
            sendTool.send(message);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 可以发送有附件的邮件
     * @param receiverMails 接收人
     * @param subject 邮件主题
     * @param text 邮件内容
     * @param attachmentNames 所有附件的name
     * @param attachments 所有附件
     * @return 是否发送成功
     */
    public boolean sendCanHaveAttachment(String[] receiverMails, String subject, String text, String[] attachmentNames, File[] attachments) throws Exception {
        if (Objects.isNull(attachmentNames) || Objects.isNull(attachments) || attachmentNames.length != attachments.length){
            throw new Exception("不合法的参数！！！");
        }
        try {

            MimeMessage message = sendTool.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true); // 支持多附件发送
            helper.setSubject(subject);
            helper.setFrom(sender);
            helper.setTo(receiverMails);
            helper.setSentDate(new Date());
            helper.setText(text);
            for (int i = 0 ; i < attachmentNames.length;i++){
                helper.addAttachment(attachmentNames[i],attachments[i]);
            }
            sendTool.send(message);
            return true;

        }catch (Exception e){
            return false;
        }
    }

    /**
     * 发送消息以网页形式
     * @param receiverMails 接收人
     * @param subject 主题
     * @param htmlPath html路径
     * @param context thymeleaf渲染需要的数据(K-V形式)
     * @return 是否发送成功
     */
    public boolean sendByHTML(String[] receiverMails, String subject, String htmlPath, Context context){
        try {
            MimeMessage message = sendTool.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSentDate(new Date());
            helper.setSubject(subject);
            helper.setFrom(sender);
            helper.setTo(receiverMails);
            String text = engine.process(htmlPath, context);
            helper.setText(text,true);
            sendTool.send(message);
            return true;
        }catch (Exception e){
            return false;
        }

    }
}
