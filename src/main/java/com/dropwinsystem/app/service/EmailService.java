package com.dropwinsystem.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmail(String toEmail, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);
        
        try {
            mailSender.send(message);
            log.info("비밀번호 재설정 이메일 전송 성공: {}", toEmail);
        } catch (Exception e) {
            log.error("비밀번호 재설정 이메일 전송 실패: {}", toEmail, e);
            throw new RuntimeException("이메일 전송 실패", e);
        }
    }
}
