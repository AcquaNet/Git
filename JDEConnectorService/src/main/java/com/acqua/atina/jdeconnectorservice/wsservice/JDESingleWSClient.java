/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acqua.atina.jdeconnectorservice.wsservice;

import com.jdedwards.system.connector.dynamic.ServerFailureException;
import com.jdedwards.system.connector.dynamic.UserSession;
import com.acqua.atina.jdeconnector.internal.JDEConnectionLocker;
import com.acqua.atina.jdeconnector.internal.JDETransactions;
import com.acqua.atina.jdeconnector.internal.model.metadata.ParameterTypeSimple;
import com.acqua.atina.jdeconnector.internal.ws.JDEWSDriver;
import com.acqua.atina.jdeconnectorservice.exception.JDESingleConnectionException;
import com.acqua.atina.jdeconnectorservice.exception.JDESingleConnectorException;  
import com.jdedwards.base.resource.UserPreference;
import com.jdedwards.system.connector.dynamic.Connector;
import com.jdedwards.system.connector.dynamic.InvalidLoginException;
import com.jdedwards.system.security.SecurityToken;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator; 
import java.util.Map;
import java.util.Set;
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
    // User Preference
    // =========================
    
    private UserPreference userPreference; 
     
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

        logger.info("JDESingleWSClient Connecting to JDE. Current Session ID: " + iSessionID);
        logger.info("JDESingleWSClient Login Information: ");
        logger.info("      User: " + this.user);
        logger.info("      Role: " + this.role);
        logger.info("      Environment: " + this.environment); 

        boolean userConnected = false;

        try {

            JDEConnectionLocker.getInstance()
                    .getWriteLock();

            if (iSessionID != 0) {

                logger.info("JDESingleWSClient - JDEConnectorService:  Previously connected to JDE. Id:" + Integer.toString(iSessionID));

                userConnected = com.jdedwards.system.connector.dynamic.Connector.getInstance()
                        .isLoggedIn(iSessionID);

                logger.info("JDESingleWSClient - JDEConnectorService:  is connected to JDE? = " + userConnected);
            
                if (!userConnected) {

                    iSessionID = 0;
                    userPreference = null;

                    logger.info("JDESingleWSClient - The connections has been reseted");

                } else {
                    
                    logger.info("JDESingleWSClient - Current user is connected");
                    
                    if(!com.jdedwards.system.connector.dynamic.Connector.getInstance().getUserSession(iSessionID).isSbfConnectorMode())
                    {
                        iSessionID = 0;
                        userPreference = null;
                        
                        logger.info("JDESingleWSClient - Current user is as SBF Connector Mode");
                    }
                     
                }

            }

            if (iSessionID == 0) {
 
                logger.info("JDESingleWSClient Validate is there is another session opened with the same user");
                
                // ==============================================================
                // Validate is there is another session opened with the same user
                // ==============================================================
                Map<?, ?> connectionsOpen = com.jdedwards.system.connector.dynamic.Connector.getInstance()
                        .getUserSessions();

                Iterator<?> userSessions = connectionsOpen.values()
                        .iterator();
   
                if(userSessions.hasNext())
                {
                     
                    while (userSessions.hasNext()) {

                        UserSession sessionOpen = (UserSession) userSessions.next();

                        logger.info("Current user logged: ");
                        logger.info("      User: " + sessionOpen.getUserName());
                        logger.info("      Role: " + sessionOpen.getUserRole());
                        logger.info("      Environment: " + sessionOpen.getUserEnvironment()); 
                        logger.info("      isSbfConnectorMode: " + sessionOpen.isSbfConnectorMode()); 

                        if (sessionOpen.getUserName().trim().compareTo(user) == 0 && 
                            sessionOpen.getUserRole().trim().compareTo(role) == 0 && 
                            sessionOpen.getUserEnvironment().trim().compareTo(environment) == 0 &&
                            sessionOpen.isSbfConnectorMode()) {

                            iSessionID = (int) sessionOpen.getSessionID();

                            logger.info("      User Connected with Id: " + Integer.toString(iSessionID));

                            userConnected = true;

                            break;
                        }

                    }
                
                } else
                {
                    logger.info("JDESingleWSClient There is not another session opened with the same user");
                }
                
                if(!userConnected)
                {
                     logger.info("      There is not an user Connected with Id: " + Integer.toString(iSessionID));
                }
                
                
                // ==============================================================
                // Validate is there is another session opened with the same user
                // ==============================================================
                if (userConnected) {

                    logger.info("JDESingleWSClient Validate is there is another session opened with the same user locally");
                    
                    userConnected = com.jdedwards.system.connector.dynamic.Connector.getInstance()
                            .isLoggedIn(iSessionID);
                    
                    logger.info("JDESingleWSClient is there is another session opened with the same user locally? " + userConnected);

                }

                // ==============================================================
                // Login
                // ==============================================================
                if (!userConnected) {

                    logger.info("JDESingleWSClient Login again");

                    iSessionID = com.jdedwards.system.connector.dynamic.Connector.getInstance()
                            .loginBase(this.user, this.password, this.environment, this.role, true, true);

                    logger.info("      User Connected with Id: " + Integer.toString(iSessionID));
                    
                    userConnected = true;
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
                    
                    // -----------------------------------------
                    // GET User Preference
                    // -----------------------------------------
                     
                    userPreference = userSession.getUserPreference();
                     

                }

            }

        } catch (ServerFailureException e) {
            throw new JDESingleConnectionException("JDE Conexion Error ServerFailureException: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            throw new JDESingleConnectionException("JDE Conexion Error InterruptedException: " + e.getMessage(), e);
        } catch (InvalidLoginException e) {
            throw new JDESingleConnectionException("JDE Conexion Error InvalidLoginException: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new JDESingleConnectionException("JDE Conexion Error Exception: " + e.getMessage(), e);
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

            logger.debug("ATINA - JDEConnectorService:  Disconnecting from JDE. Id: " + Integer.toString(iSessionID));

            Connector connector = com.jdedwards.system.connector.dynamic.Connector.getInstance();
 
            connector.logoff(iSessionID);

            connector.shutDown();

            JDEConnectionLocker.getInstance()
                .releaseWriteLock();

            logger.debug("ATINA - JDEConnectorService:  Disconnected from JDE");

            iSessionID = 0;
            
            userPreference = null;

        } else {
            logger.debug("ATINA - JDEConnectorService:  Previously disconnected from JDE. Id:" + Integer.toString(iSessionID));

        }
         
    }
    
    
    public int isJDEConnected() {

        boolean userConnected = false;

        if (iSessionID != 0) {

            userConnected = com.jdedwards.system.connector.dynamic.Connector.getInstance()
                .isLoggedIn(iSessionID);

            /* Additional Control */

            if (userConnected) {

                logger.debug("ATINA - JDEConnectorService:  Additional Control for session:" + Integer.toString(iSessionID));

                Integer localInteger = Integer.valueOf(iSessionID);

                UserSession localUserSession = com.jdedwards.system.connector.dynamic.Connector.getInstance()
                    .getUserSession(localInteger);

                userConnected = !localUserSession.isInboundTimedout();

                if (!userConnected) {

                    logger.debug("ATINA - JDEConnectorService:  The connection will be reseted");

                    com.jdedwards.system.connector.dynamic.Connector.getInstance()
                        .logoff(iSessionID);

                    iSessionID = 0;
                    
                    userPreference = null;

                }

            }

        }

        logger.debug("ATINA - JDEConnectorService:  Is Connected to JDE?:" + (userConnected));

        if (userConnected) {

            logger.debug("ATINA - JDEConnectorService:  Connected to JDE Id: " + Integer.toString(iSessionID));

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

    public UserPreference getUserPreference() {
        return userPreference;
    } 
    
    //************************************************************************
    // Bootstrap
    //************************************************************************
    
    public void bootstrap() throws IOException {
        JDEWSDriver.getInstance().bootstrap(this.tmpFolder);
    }
    
    public String getUser() {
        return user;
    } 
    
    public String getEnvironment() {
        return environment;
    }
 
    public String getRole() {
        return role;
    }

    public int getiSessionID() {
        return iSessionID;
    } 

    public E1Principal getE1ppal() {
        return e1ppal;
    }
    
    
 
}
