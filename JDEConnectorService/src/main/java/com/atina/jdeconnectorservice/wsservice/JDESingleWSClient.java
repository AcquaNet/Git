/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.jdeconnectorservice.wsservice;

import com.jdedwards.system.connector.dynamic.ServerFailureException;
import com.jdedwards.system.connector.dynamic.UserSession;
import com.atina.jdeconnector.internal.JDEConnectionLocker;
import com.atina.jdeconnector.internal.JDETransactions; 
import com.atina.jdeconnector.internal.model.metadata.ParameterTypeSimple;
import com.atina.jdeconnector.internal.ws.JDEWSDriver;
import com.atina.jdeconnectorservice.exception.JDESingleConnectionException;
import com.atina.jdeconnectorservice.exception.JDESingleConnectorException; 
import com.atina.jdeconnectorservice.service.poolconnection.JDEConnection;
import com.jdedwards.system.connector.dynamic.Connector;
import com.jdedwards.system.security.SecurityToken;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator; 
import java.util.Map;
import java.util.Set; 
import oracle.e1.bssvfoundation.base.IContext;
import oracle.e1.bssvfoundation.impl.security.E1Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jgodi
 */
public class JDESingleWSClient {

    private static final Logger logger = LoggerFactory.getLogger(JDESingleWSClient.class);

    // =========================
    // Login Parameters
    // =========================
    //
    private String user;
    private String password;
    private String environment;
    private String role;
 
    // =========================
    // Cache
    // =========================
    //
    private File tmpFolder;
    
    private File tmpFolderCache;
     
    // =========================
    // Session Parameters
    // =========================
    //
    private int iSessionID = 0; 
    UserSession userSession;
    SecurityToken secToken;
    E1Principal e1ppal; 
      
    public JDESingleWSClient(String user, String password, String environment, String role) {
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
                    
                    if(!com.jdedwards.system.connector.dynamic.Connector.getInstance().getUserSession(iSessionID).isSbfConnectorMode())
                    {
                        iSessionID = 0;
                        
                        logger.info("Current user is as SBF Connector Mode");
                    }
                     
                }

            }

            if (iSessionID == 0) {

                Connector connector = com.jdedwards.system.connector.dynamic.Connector.getInstance();
 
                iSessionID = connector.loginBase(this.user, this.password, this.environment, this.role, true, true);

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
                            sessionOpen.isSbfConnectorMode()) {

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
                            .loginBase(this.user, this.password, this.environment, this.role, true, true);

                    logger.info("      User Connected with Id: " + Integer.toString(iSessionID));
                }

                // ==============================================================
                // Get UserSession
                // ==============================================================
                //
                if (userConnected) {

                    userSession = Connector.getInstance().getUserSession(iSessionID);
                    userSession.setSbfConnectorMode(true);

                    // -----------------------------------------
                    // GET Token
                    // -----------------------------------------
                    //
                    secToken = userSession.getSecurityToken();

                    // -----------------------------------------
                    // GET E1Principal
                    // -----------------------------------------
                    //
                    e1ppal = new E1Principal("JDE", secToken, "JDV920", "*ALL", iSessionID);

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
        return JDEWSDriver.getInstance().getWSList(tmpFolder);
    }
    
    public HashMap<String, ParameterTypeSimple> getWSInputParameter(String operation) {
           
        return JDEWSDriver.getInstance().getWSInputParameter(operation,tmpFolder);
    }
    
    public HashMap<String, ParameterTypeSimple> getWSOutputParameter(String operation) {
           
        return JDEWSDriver.getInstance().getWSOutputParameter(operation,tmpFolder);
    }
    
    public HashMap<String, Object> callJDEWS(String operation, HashMap<String, Object> inputValues) throws JDESingleConnectorException{
    
         
        return JDEWSDriver.getInstance().callJDEWS(this.e1ppal, iSessionID,operation, inputValues,tmpFolderCache);
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

    //************************************************************************
    // Bootstrap
    //************************************************************************
    
    public void bootstrap() throws IOException {
        JDEWSDriver.getInstance().bootstrap(this.tmpFolder);
    }
    

}
