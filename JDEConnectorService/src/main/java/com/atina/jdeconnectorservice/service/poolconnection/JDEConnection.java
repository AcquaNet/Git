/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.jdeconnectorservice.service.poolconnection;

import com.atina.jdeconnectorservice.exception.JDESingleConnectionException;

/**
 *
 * @author jgodi
 */
public interface JDEConnection {
    
    public int isJDEConnected();
    public int connect() throws JDESingleConnectionException;
    public void disconnect();
    public boolean isWSConnection();
    
}
