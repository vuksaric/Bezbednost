package com.example.KT1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;


@Component
public class EmailContext {

    @Autowired
    private JavaMailSender javaMailSender;

    private final TemplateEngine templateEngine;

    public EmailContext(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Async
    public void send(String to, String subject, String templateName, Context context) {
        String body = templateEngine.process(templateName, context);
        sendMail(to, subject, body, true);
    }

    private void sendMail(String to, String subject, String text, Boolean isHtml) {
        try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(to);
            System.out.println(to);
            helper.setSubject(subject);
            helper.setText(text, isHtml);
            javaMailSender.send(mail);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

