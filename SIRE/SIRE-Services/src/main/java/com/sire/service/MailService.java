/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.service;

import com.sire.event.MailEvent;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author publio
 */
@Singleton
@Startup
public class MailService {

    @Resource(name = "mail/gmail")
    private Session mailSession;

    @Asynchronous
    @Lock(LockType.READ)
    public void sendMail(MailEvent event) {
        try {
            Message message = new MimeMessage(mailSession);

            message.setRecipient(Message.RecipientType.TO, new InternetAddress(event.getTo()));
            message.setSubject(event.getSubject());
            if (event.getMimeMultipart() == null) {
                message.setContent(event.getMessage(), "text/plain");
            } else {
                message.setContent(event.getMimeMultipart());
            }

            Transport.send(message);
            System.out.println("E-Mail sent to " + event.getTo());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
