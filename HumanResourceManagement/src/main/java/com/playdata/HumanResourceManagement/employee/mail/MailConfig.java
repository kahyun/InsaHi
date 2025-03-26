package com.playdata.HumanResourceManagement.employee.mail;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.example.com"); // SMTP 서버 주소
        mailSender.setPort(587); // 포트 설정
        mailSender.setUsername("your-email@example.com"); // 이메일 사용자
        mailSender.setPassword("your-email-password"); // 이메일 비밀번호

        // 추가 설정 (필요에 따라)
        mailSender.setJavaMailProperties(new java.util.Properties() {{
            put("mail.smtp.auth", "true");
            put("mail.smtp.starttls.enable", "true");
        }});

        return mailSender;
    }
}
