/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.event;

import javax.mail.internet.MimeMultipart;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 *
 * @author publio
 */
@Getter
@Setter
public class MailEvent {

    private String to;
    private String message;
    private String subject;
    private MimeMultipart mimeMultipart;
    private Map<String, String> properties;
}
