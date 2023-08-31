package com.kadirgurturk.emailSender.buisness.service;

import com.kadirgurturk.emailSender.utilty.EmailUtlity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${EMAIL_HOST}")
    private String host;
    @Value("${EMAIL_USERANAME}")
    private String fromEmail;

    private final JavaMailSender emailSender;

    public void sendSimpleMailMesaage(String firstName, String to, String token)
    {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("New User Account Verification");
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setText(EmailUtlity.getEmailMessage(firstName,to,token));
            emailSender.send(message);
        }catch (Exception exp){
            System.out.println(exp.getMessage());
            throw new RuntimeException(exp);
        }
    }


}
