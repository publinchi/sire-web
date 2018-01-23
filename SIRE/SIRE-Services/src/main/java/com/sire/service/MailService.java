/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.service;

import com.sire.event.MailEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author publio
 */
@Singleton
@Startup
public class MailService implements IMailService {

//    @Resource(name = "mail/gmail")
//    private Session mailSession;
    @Asynchronous
    @Lock(LockType.READ)
    @Override
    public void sendMail(MailEvent event) {
        Message message = null;
        try {
            String mail = System.getProperty("sire.mail");
            if (mail == null) {
                mail = "mail/gmail";
            }
            Context initContext = new InitialContext();
            Session mailSession = (Session) initContext.lookup(mail);
            message = new MimeMessage(mailSession);

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
            try {
                System.out.println("E-Mail not sent to " + event.getTo() + ", Cause: " + e.getCause());
                System.out.println("Retrying after 5 seconds ...");
                Thread.sleep(5000L);
                Transport.send(message);
                System.out.println("E-Mail sent again to " + event.getTo());
            } catch (InterruptedException | MessagingException ex) {
                Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
