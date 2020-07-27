package com.namct.reddit.mail.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.namct.reddit.exceptions.BaseException;
import com.namct.reddit.mail.MailContentBuilder;
import com.namct.reddit.mail.model.NotificationEmail;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {
    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    public void sendMail(NotificationEmail notificationEmail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("tester.reddit@gmail.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody(), "signup-template"), true);
        };
        try {
            mailSender.send(messagePreparator);
            log.info("Activation email sent to email: " + notificationEmail.getRecipient());
        } catch (MailException e) {
            throw new BaseException(
                    "Exception occurred when sending mail to " + notificationEmail.getRecipient(), e);
        }
    }
}