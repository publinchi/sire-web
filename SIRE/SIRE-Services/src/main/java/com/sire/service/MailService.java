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
import javax.mail.Address;
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

    private static final Logger log = Logger.getLogger(MailService.class.getName());

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
            message.setSubject(event.getSubject());
            if (event.getMimeMultipart() == null) {
                message.setContent(event.getMessage(), "text/plain");
            } else {
                message.setContent(event.getMimeMultipart());
            }

            String mailFrom;

            mailFrom = mailSession.getProperties().get("mail.smtp.from") != null ?
                    mailSession.getProperties().get("mail.smtp.from").toString() : null;

            if(mailFrom == null)
                mailFrom = mailSession.getProperties().get("mail.smtps.from") != null ?
                        mailSession.getProperties().get("mail.smtps.from").toString() : null;
            
            if (mailFrom == null) {
                mailFrom = System.getProperty("sire.mail.from");
            }

            if(mailFrom != null){
                Address[] from = InternetAddress.parse(mailFrom);//Your domain email
                message.addFrom(from);
            }

            String mailCopy = System.getProperty("sire.mail.copy");
            if (mailCopy == null) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(event.getTo()));
            } else {
                Address[] to = InternetAddress.parse(event.getTo() + "," + mailCopy);
                message.addRecipients(Message.RecipientType.TO, to);
            }

            Transport.send(message);
            log.log(Level.INFO,"E-Mail sent to {0} , with secuential {1}",
                    new Object[]{event.getTo(), event.getProperties().get("secuencial")});
        } catch (MessagingException e) {
            try {
                log.log(Level.INFO,"E-Mail not sent to {0}, Cause: {1}",
                        new Object[]{event.getTo(), e.getCause()});
                log.info("Retrying after 5 seconds ...");
                Thread.sleep(5000L);
                Transport.send(message);
                log.log(Level.INFO,"E-Mail sent again to {0} , with secuential {1}",
                        new Object[]{event.getTo(), event.getProperties().get("secuencial")});
            } catch (InterruptedException | MessagingException ex) {
                Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
