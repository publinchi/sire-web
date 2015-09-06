/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.web;

import java.util.Date;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author pestupinan
 */
@ManagedBean
public class TimerBean {

    private Date date;

    public Date getDate() {
        date = new Date();
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
