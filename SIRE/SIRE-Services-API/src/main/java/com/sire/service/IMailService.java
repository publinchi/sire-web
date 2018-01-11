/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.service;

import com.sire.event.MailEvent;

/**
 *
 * @author pestupinan
 */
public interface IMailService {

    public void sendMail(MailEvent event);
}
