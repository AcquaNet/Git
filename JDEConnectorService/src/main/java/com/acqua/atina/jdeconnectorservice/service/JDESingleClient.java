/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acqua.atina.jdeconnectorservice.service;

import com.acqua.atina.jdeconnector.internal.CurrentUserSessionListener;
import com.jdedwards.system.connector.dynamic.ServerFailureException;
import com.jdedwards.system.connector.dynamic.UserSession;
import com.acqua.atina.jdeconnector.internal.JDEBsfnDriver;
import com.acqua.atina.jdeconnector.internal.JDEConnectionLocker;
import com.acqua.atina.jdeconnector.internal.JDETransactions; 
import com.acqua.atina.jdeconnector.internal.model.JDEBsfnParameter;  
import com.acqua.atina.jdeconnectorservice.JDEConnectorService;
import com.acqua.atina.jdeconnectorservice.exception.JDESingleConnectionException;
import com.acqua.atina.jdeconnectorservice.exception.JDESingleConnectorException; 
import com.acqua.atina.jdeconnectorservice.service.poolconnection.JDEConnection;
import com.jdedwards.system.connector.dynamic.Connector;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator; 
import java.util.Map;
import java.util.Set; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jgodi
 */
public class JDESingleClient {

    private static final Logger logger = LoggerFactory.getLogger(JDEConnectorService.class);

    // =========================
    // Login Parameters
    // =========================
    private String user;
    private String password;
    private String environment;
    private String role;
 
    // =========================
    // Cache
    // =========================
    private File tmpFolder;
    
    private File tmpFolderCache;
     
    // =========================
    // Session ID
    // =========================
    private int iSessionID = 0;
    
    private CurrentUserSessionListener ul;

    public JDESingleClient(String user, String password, String environment, String role) {
        this.user = user;
        this.password = password;
        this.environment = environment;
        this.role = role;
    }

    public int login() throws JDESingleConnectionException {

        logger.info("Connecting to JDE ..");

        boolean userConnected = false;

        try {
            
            JDEConnectionLocker.getInstance()
                .getWriteLock();
            
            if (iSessionID != 0) {

                logger.info("MULESOFT - JDEConnectorService:  Previously connected to JDE. Id:" + Integer.toString(iSessionID));

                userConnected = com.jdedwards.system.connector.dynamic.Connector.getInstance()
                        .isLoggedIn(iSessionID);

                logger.info("MULESOFT - JDEConnectorService:  is connected to JDE? = " + userConnected);

                if (!userConnected) {

                    iSessionID = 0;

                    logger.info("The connections has been reseted");

                } else {
                    logger.info("Current user is connected");
                    
                    if(com.jdedwards.system.connector.dynamic.Connector.getInstance().getUserSession(iSessionID).isSbfConnectorMode())
                    {
                        iSessionID = 0;
                        
                        logger.info("Current user is as SBF Connector Mode");
                    }
                    
                }

            }

            if (iSessionID == 0) {

                Connector connector = com.jdedwards.system.connector.dynamic.Connector.getInstance();
                
                ul = new CurrentUserSessionListener(this);
                
                connector.addUserSessionListener(ul);
                
                iSessionID = connector.login(this.user, this.password, this.environment, this.role);

                logger.info("      User Connected with Id: " + Integer.toString(iSessionID));

                // ==============================================================
                // Validate is there is another session opened with the same user
                // ==============================================================
                Map<?, ?> connectionsOpen = com.jdedwards.system.connector.dynamic.Connector.getInstance()
                        .getUserSessions();

                Iterator<?> userSessions = connectionsOpen.values()
                        .iterator();

                logger.info("Current user logged: ");

                while (userSessions.hasNext()) {

                    UserSession sessionOpen = (UserSession) userSessions.next();

                    logger.info("Current user logged: ");
                    logger.info("      User: " + sessionOpen.getUserName());
                    logger.info("      Role: " + sessionOpen.getUserRole());
                    logger.info("      Environment: " + sessionOpen.getUserEnvironment());

                    if (sessionOpen.getUserName()
                            .compareTo(user) == 0 && sessionOpen.getUserRole()
                            .compareTo(role) == 0
                            && sessionOpen.getUserEnvironment()
                                    .compareTo(environment) == 0 &&
                            !sessionOpen.isSbfConnectorMode()) {

                        iSessionID = (int) sessionOpen.getSessionID();

                        logger.info("      User Connected with Id: " + Integer.toString(iSessionID));

                        userConnected = true;
                    }

                }

                // ==============================================================
                // Validate is there is another session opened with the same user
                // ==============================================================
                if (userConnected) {

                    userConnected = com.jdedwards.system.connector.dynamic.Connector.getInstance()
                            .isLoggedIn(iSessionID);

                }

                // ==============================================================
                // Login
                // ==============================================================
                if (!userConnected) {

                    logger.info("Connecting to JDE ..");

                    iSessionID = com.jdedwards.system.connector.dynamic.Connector.getInstance()
                            .login(user, password, environment, role);

                    logger.info("      User Connected with Id: " + Integer.toString(iSessionID));
                }

            }

        } catch (ServerFailureException e) {
            throw new JDESingleConnectionException("JDE Conexion Error" + e.getMessage(), e);
        } catch (InterruptedException e) {
            throw new JDESingleConnectionException("JDE Conexion Error" + e.getMessage(), e);
        } finally {

            JDEConnectionLocker.getInstance()
                    .releaseWriteLock();

        }

        return iSessionID;
        
    }
    
    public void logout() throws JDESingleConnectionException {
        
        if (iSessionID != 0) {

            if (JDETransactions.getInstance()
                .existAPendingTransaction()) {

                throw new JDESingleConnectionException("There is a transaction open.");

            }

            logger.debug("MULESOFT - JDEConnectorService:  Disconnecting from JDE. Id: " + Integer.toString(iSessionID));

            Connector connector = com.jdedwards.system.connector.dynamic.Connector.getInstance();
            
            if(ul!=null)
            {
                connector.removeUserSessionListener(ul);
            }
            
            connector.logoff(iSessionID);

            connector.shutDown();

            JDEConnectionLocker.getInstance()
                .releaseWriteLock();

            logger.debug("MULESOFT - JDEConnectorService:  Disconnected from JDE");

            iSessionID = 0;

        } else {
            logger.debug("MULESOFT - JDEConnectorService:  Previously disconnected from JDE. Id:" + Integer.toString(iSessionID));

        }
        
        
    }
    
    
    public int isJDEConnected() {

        boolean userConnected = false;

        if (iSessionID != 0) {

            userConnected = com.jdedwards.system.connector.dynamic.Connector.getInstance()
                .isLoggedIn(iSessionID);

            /* Additional Control */

            if (userConnected) {

                logger.debug("MULESOFT - JDEConnectorService:  Additional Control for session:" + Integer.toString(iSessionID));

                Integer localInteger = Integer.valueOf(iSessionID);

                UserSession localUserSession = com.jdedwards.system.connector.dynamic.Connector.getInstance()
                    .getUserSession(localInteger);

                userConnected = !localUserSession.isInboundTimedout();

                if (!userConnected) {

                    logger.debug("MULESOFT - JDEConnectorService:  The connection will be reseted");

                    com.jdedwards.system.connector.dynamic.Connector.getInstance()
                        .logoff(iSessionID);

                    iSessionID = 0;

                }

            }

        }

        logger.debug("MULESOFT - JDEConnectorService:  Is Connected to JDE?:" + (userConnected));

        if (userConnected) {

            logger.debug("MULESOFT - JDEConnectorService:  Connected to JDE Id: " + Integer.toString(iSessionID));

        }

        return (userConnected?iSessionID:0);
    }
    
    
    //************************************************************************
    // BSFN Operations
    //************************************************************************
    
    
    
    public Set<String> getOperationList() throws JDESingleConnectorException{
          
        if(iSessionID==0 || tmpFolder == null )
        { 
            throw new JDESingleConnectorException("Error getting BSFN List");
            
        } 
        return JDEBsfnDriver.getInstance().getBSFNListFromEnterpriseServer(iSessionID, tmpFolder);
    }
    
    public Set<JDEBsfnParameter> getBSFNParameter(String bsfnName) throws JDESingleConnectorException{
           
        return JDEBsfnDriver.getInstance().getBSFNParameter(iSessionID,bsfnName,tmpFolderCache);
    }
    
    public HashMap<String, Object> callJDEBsfn(String bsfnName, Map<String, Object>  inputObject, Integer transactionID) throws JDESingleConnectorException{
    
       // login();
        return JDEBsfnDriver.getInstance().callJDEBsfn(iSessionID,bsfnName, inputObject, transactionID, tmpFolderCache);
    }
          
    //************************************************************************
    // Setters and Getters
    //************************************************************************
    
    public File getTmpFolder() {
        return tmpFolder;
    }
    
    public void setTmpFolder(File tmpFolder) {
        this.tmpFolder = tmpFolder;
    }

    public File getTmpFolderCache() {
        return tmpFolderCache;
    }

    public void setTmpFolderCache(File tmpFolderCache) {
        this.tmpFolderCache = tmpFolderCache;
    }

   
    

}
