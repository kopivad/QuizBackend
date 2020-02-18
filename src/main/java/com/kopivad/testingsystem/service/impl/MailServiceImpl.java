package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.domain.Mail;
import com.kopivad.testingsystem.service.MailService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Log4j2
public class MailServiceImpl implements MailService {
    public final JavaMailSender emailSender;

    @Override
    public void sendMessage(Mail mail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail.getReceiver());
        message.setSubject(mail.getSubject());
        message.setText(mail.getText());
        emailSender.send(message);
    }
}
