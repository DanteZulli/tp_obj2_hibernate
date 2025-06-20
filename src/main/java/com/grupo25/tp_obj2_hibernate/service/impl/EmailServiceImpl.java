package com.grupo25.tp_obj2_hibernate.service.impl;

import com.grupo25.tp_obj2_hibernate.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Value("${email.default.recipients}")
    private String[] defaultRecipients;

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-reply@ticketera-grupo25.com");

        String[] allRecipients = new String[defaultRecipients.length + 1];
        allRecipients[0] = to;
        System.arraycopy(defaultRecipients, 0, allRecipients, 1, defaultRecipients.length);

        message.setTo(allRecipients);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}