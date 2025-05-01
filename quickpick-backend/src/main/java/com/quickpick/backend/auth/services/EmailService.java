package com.quickpick.backend.auth.services;

import com.quickpick.backend.auth.entities.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class EmailService {
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public String sendMail(User user) throws MessagingException, UnsupportedEncodingException {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            String subject = "Verify your email";
            String senderName = "ShopEase";
            String mailContent = "Hello " + user.getUsername() + ",\n";
            mailContent += "Your verification code is: " + user.getVerificationCode() + "\n";
            mailContent += "Please enter this code to verify your email.";
            mailContent +="\n";
            mailContent+= senderName;

            helper.setFrom(sender, "QuickPick");
            helper.setTo(user.getEmail());
            helper.setSubject(subject);
            helper.setText(mailContent, true);

            mailSender.send(mimeMessage);
        }

        catch (Exception e) {
            throw new MessagingException("Error while sending email", e);
        }

        return "Email sent!";
    }

}
