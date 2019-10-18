/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.jdeconnectorservice.service.poolconnection;

import com.atina.jdeconnectorservice.JDEConnectorService;
import com.atina.jdeconnectorservice.exception.JDESingleConnectionException;
import com.atina.jdeconnectorservice.service.JDESingleConnection;
import com.atina.jdeconnectorservice.service.poolconnection.JDEConnection;
import com.atina.jdeconnectorservice.wsservice.JDESingleWSConnection;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jgodi
 */
public class JDEPoolConnections {
    
    private static final Logger logger = LoggerFactory.getLogger(JDEConnectorService.class);
    
    HashMap<Integer, JDEConnection> pool = new HashMap<Integer, JDEConnection>();
     
    private JDEPoolConnections() {
    }
    
    public static JDEPoolConnections getInstance() {
        return JDEPoolConnectionsHolder.INSTANCE;
    }
    
    private static class JDEPoolConnectionsHolder {

        private static final JDEPoolConnections INSTANCE = new JDEPoolConnections();
        
    }
    
    public int createConnection(String user, String password, String environment, String role, int sessionID, boolean wsConnection) throws JDESingleConnectionException {
        
        logger.info("Creating JDE Connection: ");
        logger.info("      User: " + user);
        logger.info("      Role: " + role);
        logger.info("      Environment: " + environment);
        logger.info("      sessionID: " + sessionID);
        logger.info("      wsConnection: " + wsConnection);
                    
        int currentSessionId = 0;
        
        if(sessionID != 0 && pool.containsKey(sessionID))
        {
            JDEConnection jdeConnection = pool.get(sessionID);
            
            currentSessionId = jdeConnection.isJDEConnected();
            
            logger.info((currentSessionId>0?"User is still connected":"User session expired"));
             
        }
         
        if(currentSessionId == 0)
        {
            if(sessionID !=0)
            {
                pool.remove(sessionID);
                logger.info("Previous session removed from the poll connection");
                
            }
            
            JDEConnection jdeConnection = null;
            
            if(wsConnection)
            {
                jdeConnection = new JDESingleWSConnection(user,password,environment,role);
            }
            else
            {
                jdeConnection = new JDESingleConnection(user,password,environment,role);
            }
            
            
            currentSessionId = jdeConnection.connect();
            
            logger.info("New user session: " + currentSessionId);
            
            pool.put(currentSessionId, jdeConnection);
             
        }
         
        return currentSessionId;
        
        
    }
    
    public JDEConnection getSingleConnection(int sessionID) throws JDESingleConnectionException{
    
        if(this.pool.containsKey(sessionID))
        {
            return this.pool.get(sessionID);
        }
        else
        {
            throw new JDESingleConnectionException("There is not a session in poll connections for session id: " +sessionID);
        }
         
    } 
    
    public void disconnect(int sessionID) {
    
        if(this.pool.containsKey(sessionID))
        {
           this.pool.get(sessionID).disconnect();
           this.pool.remove(sessionID);
        }
        else
        {
            throw new JDESingleConnectionException("There is not a session in poll connections for session id: " +sessionID);
        }
         
    } 
    
}
