package com.kadirgurturk.emailSender.buisness.service;

import com.kadirgurturk.emailSender.utilty.EmailUtlity;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${VERIFY_EMAIL_HOST}")
    private String host;
    @Value("${EMAIL_USERANAME}")
    private String fromEmail;

    private final TemplateEngine templateEngine; // we'll use this class when we try to connect to out html template.

    private final JavaMailSender emailSender;

    @Async
    public void sendSimpleMailMessage(String firstName, String to, String token)
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

    @Async
    public void sendMimeMassageWithAttachment(String firstName, String to, String token)
    {
        try{
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true,"UTF-8");
            helper.setPriority(1);
            helper.setSubject("New User Account Verification");
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setText(EmailUtlity.getEmailMessage(firstName,to,token));
            //Add attachments file or files
            FileSystemResource kofte = new FileSystemResource(new File(System.getProperty("users.USER") + "/Dowloands/kfte"));
            helper.addAttachment(kofte.getFilename(),kofte); // we attach kofte image to helper
            emailSender.send(message);
        }catch (Exception exp){
            System.out.println(exp.getMessage());
            throw new RuntimeException(exp);
        }
    }

    @Async
    public void sendHtmlMail(String firstName, String to, String token)
    {
        try{
            Context context = new Context(); // we need use the thymleaf for to connect our html template which we send with email
            context.setVariable("name",firstName);
            context.setVariable("url",EmailUtlity.getVerificationUrl(host,token)); // we assign to variable we need to use
            String text = templateEngine.process("emailTemplate",context); // we connect our contwxt to tamplate, with this way we can use this variables into tamplate
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true,"UTF-8");
            helper.setPriority(1);
            helper.setSubject("New User Account Verification");
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setText(text,true); // we change this method

        }catch (Exception exp){
            System.out.println(exp.getMessage());
            throw new RuntimeException(exp);
        }

    }


}
