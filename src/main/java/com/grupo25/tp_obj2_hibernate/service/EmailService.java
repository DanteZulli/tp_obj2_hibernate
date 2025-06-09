package com.grupo25.tp_obj2_hibernate.service;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
} 