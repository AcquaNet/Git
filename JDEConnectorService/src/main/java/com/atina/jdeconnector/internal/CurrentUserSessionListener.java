/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.jdeconnector.internal;

import com.atina.jdeconnectorservice.service.JDESingleClient;
import com.jdedwards.system.connector.dynamic.UserSessionEvent;
import com.jdedwards.system.connector.dynamic.UserSessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jgodi
 */
public class CurrentUserSessionListener 
  implements UserSessionListener{

    private static final Logger logger = LoggerFactory.getLogger(CurrentUserSessionListener.class);
    
    private JDESingleClient client;

    public CurrentUserSessionListener(JDESingleClient client) {
        this.client = client;
    }
     
     
    @Override
    public void onUserSessionEvent(UserSessionEvent use) {
        
        logger.info(use.getEventType().toString());
        
        client.isJDEConnected();
        
    }
    
}
