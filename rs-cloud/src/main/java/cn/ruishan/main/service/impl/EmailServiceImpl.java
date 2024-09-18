package cn.ruishan.main.service.impl;

import java.io.IOException;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import cn.ruishan.main.service.IEmailService;

/**
 * 
 * @author longgang.lei
 * @date 2019年9月5日
 */
@Service
public class EmailServiceImpl implements IEmailService {

    private static final String FROM_EMAIL = "xxxx@foxmail.com";  // 发件人

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendTextEmail(String title, String content, String[] toEmails) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FROM_EMAIL);
        message.setTo(toEmails);
        message.setSubject(title);
        message.setText(content);
        mailSender.send(message);
    }

    @Override
    public void sendFullTextEmail(String title, String html, String[] toEmails) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(FROM_EMAIL);
        helper.setTo(toEmails);
        helper.setSubject(title);
        // 发送邮件
        helper.setText(html, true);
        mailSender.send(mimeMessage);
    }

    @Override
    public void sendHtmlEmail(String title, String htmlPath, Map<String, Object> map, String[] toEmails) throws MessagingException, IOException {
//        ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader("templates/");
//        Configuration cfg = Configuration.defaultConfiguration();
//        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
//        Template t = gt.getTemplate(htmlPath);  // 加载html模板
//        t.binding(map);  // 填充数据
//        String html = t.render();  // 获得渲染后的html
//        sendFullTextEmail(title, html, toEmails);  // 发送邮件
    }

}
