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
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
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
public class MailService {

    @Resource(name = "mail/gmail")
    private Session mailSession;

    @Asynchronous
    @Lock(LockType.READ)
    public void sendMail(@Observes(during = TransactionPhase.AFTER_SUCCESS) MailEvent event) {
        try {
            Message message = new MimeMessage(mailSession);

            message.setRecipient(Message.RecipientType.TO, new InternetAddress(event.getTo()));
            message.setSubject(event.getSubject());
            message.setContent(event.getMessage(), "text/plain");

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
