package com.codecrafter.WebAppManagerRoomGymBE.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final Configuration config;

    public void sendEmailWithTemplate(String to, String subject, String templateName, Map<String,Object> placeholders, File attachment){
        try {
            Template t = config.getTemplate(templateName);
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t,placeholders);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html,true);

            // Add attachment if provided
            if(attachment != null){
                FileSystemResource fileSystemResource = new FileSystemResource(attachment);
                helper.addAttachment(fileSystemResource.getFilename(),fileSystemResource);
            }

            javaMailSender.send(message);
            log.info("Email sent successfully to {}",to);

        }catch (MessagingException | TemplateException | IOException e ){
            log.error("Failed to send email to {}",to,e);
        }
    }
    public void emailTemplate( String packageName, String packageDescription, double packagePrice,
                              int memberId, String memberName,String email, String rawPassword, String phoneNumber, Date birthDate, String qrData) {
        log.info("Sending email to: " + email);

        // Tạo bản đồ chứa các thông tin cần truyền vào template
        Map<String, Object> placeholders = new HashMap<>();
        placeholders.put("packageName", packageName);
        placeholders.put("packageDescription", packageDescription);
        placeholders.put("packagePrice", packagePrice);
        placeholders.put("memberId", memberId);
        placeholders.put("memberName", memberName);
        placeholders.put("email", email);
        placeholders.put("rawPassword", rawPassword);
        placeholders.put("phoneNumber", phoneNumber);
        placeholders.put("birthDate", birthDate);
        placeholders.put("qrData", qrData);

        // Gửi email với template
        sendEmailWithTemplate(email, "Thông tin đăng ký gói tập", "emailTemplate.ftl", placeholders, null);
    }

}
