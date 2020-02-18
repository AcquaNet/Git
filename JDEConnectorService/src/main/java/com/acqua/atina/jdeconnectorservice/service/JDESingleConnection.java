/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acqua.atina.jdeconnectorservice.service;

import com.acqua.atina.jdeconnector.internal.JDEBoostrap;
import com.acqua.atina.jdeconnector.internal.model.JDEBsfnParameter; 
import com.acqua.atina.jdeconnectorservice.JDEConnectorService;
import com.acqua.atina.jdeconnectorservice.exception.JDESingleConnectionException;
import com.acqua.atina.jdeconnectorservice.exception.JDESingleConnectorException;
import com.acqua.atina.jdeconnectorservice.service.poolconnection.JDEConnection;
import com.jdedwards.base.resource.UserPreference;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author jgodi
 */
public class JDESingleConnection implements JDEConnection{

    private static final Logger logger = LoggerFactory.getLogger(JDEConnectorService.class);

    private JDESingleClient client;
    
    private File tmpFolder;
    
    private File tmpCache;
    

    public JDESingleConnection(String user, String password, String environment, String role) throws JDESingleConnectionException{

        try {

            logger.info("MULESOFT - JDEConnection - Show Classpath:");

            showClassPath();
             
            this.tmpFolder = new File(FileUtils.getTempDirectory()
                .getAbsolutePath());
            
            this.tmpCache = defineTmpFolderCache(tmpFolder,environment);
               

        } catch (IOException e) {

            logger.error("MULESOFT - JDEConnection - Error showing Classpath:" + e.getMessage());

            throw new JDESingleConnectionException(e.getMessage(), e);

        }

        // ----------------------------------------------
        // Creating JDE Client Connector
        // ----------------------------------------------
        logger.info("MULESOFT - JDEConnection - Creating JDE Client Connector with this values:");

        logger.info("     User: " + user);
        logger.info("     Environment: " + environment);
        logger.info("     Role: " + role);

        this.client = new JDESingleClientFactory().createInstance(user, password, environment, role);

        logger.info("MULESOFT - JDEConnection -JDE Client Connector is done.");

        // ----------------------------------------------
        // Startup JDE Std configuration
        // ----------------------------------------------
        
        logger.debug("MULESOFT - JDEConnection - Startup JDE Configuration for Environment: " + environment);

        try {

            JDEBoostrap.getInstance()
                    .setJDEDefaultFolderForMicroService(environment);
            

            logger.debug("MULESOFT - JDEConnection - startupJDEConfiguration()  Default Folder: " + environment);

        } catch (Exception e) {

            logger.error("MULESOFT - JDEConnection - startupJDEConfiguration() - Error loading jdeinterop.ini file ..." + e.getMessage(), e);

            throw new JDESingleConnectionException("Error loading jdeinterop.ini file:" + e.getMessage(), e);

        }

        logger.debug("MULESOFT - JDEConnection - startupJDEConfiguration() for adding new JDE Output Handler: " + environment);

        JDEBoostrap.getInstance()
                .addNewJDEOutputHandler();

        try {

            JDEBoostrap.getInstance()
                    .loadJDEServersOnInetAddressCache(true);

        } catch (IOException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

            logger.error("MULESOFT - JDEConnection - testConnect() - Error loading DNS Cache ..." + e.getMessage(), e);
            throw new JDESingleConnectionException("JDE Conexion Error" + e.getMessage(), e);

        }
        
        
        // ----------------------------------------------
        // Passing Files
        // ----------------------------------------------
        
        this.client.setTmpFolder(tmpFolder);
        
        this.client.setTmpFolderCache(tmpCache);
        
        

    }
    
    public int connect() throws JDESingleConnectionException {

        // ----------------------------------------------
        // Connecting to JDE
        // ----------------------------------------------
 
        return this.client.login();
 

    }
    
    public void disconnect()
    {
        // ----------------------------------------------
        // Disconnecting from JDE
        // ----------------------------------------------

        this.client.logout();

        // ----------------------------------------------
        // Clear JDE Std configuration
        // ----------------------------------------------

        JDEBoostrap.getInstance().removeNewJDEOutputHandler();

    }
    
    
    public int isJDEConnected() {

        // ----------------------------------------------
        // Checking connections
        // ----------------------------------------------

        return client.isJDEConnected();

    }
    
    // ====================================================================================
    // JDE Bsfn operations
    // ====================================================================================
  
    public Set<String> getOperationList() throws JDESingleConnectorException {

        return client.getOperationList();

    } 
    
    public Set<JDEBsfnParameter> getBSFNParameter(String bsfnName) throws JDESingleConnectorException {

        logger.info("MULESOFT - JDEClient - Getting BSFN Parameters");

        return client.getBSFNParameter(bsfnName);
        
    }
    
    public HashMap<String, Object> callJDEOperation(String bsfnName, HashMap<String, Object> inputObject, Integer transactionID) throws JDESingleConnectorException {

        logger.info("MULESOFT - JDEClient - Calling BSFN"); 
     
        return client.callJDEBsfn(bsfnName, inputObject, transactionID);
        
    }
     
    // ====================================================================================
    // PRIVATE operations
    // ====================================================================================
    
    private void showClassPath() throws IOException {

        logger.debug("MULESOFT - JDEStartUpConfiguration - Showing resources for current thread: ");

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        Enumeration<URL> e = classLoader.getResources("");

        while (e.hasMoreElements()) {
            logger.debug("MULESOFT - JDEStartUpConfiguration -                        : " + e.nextElement());
        }

        logger.debug("MULESOFT - JDEStartUpConfiguration - Showing resources for current class: ");

        classLoader = this.getClass().getClassLoader();

        e = classLoader.getResources("");

        while (e.hasMoreElements()) {
            logger.debug("MULESOFT - JDEStartUpConfiguration -                        : " + e.nextElement());
        }

    }
    
    private File defineTmpFolderCache(File tmpFolder, String environment) throws JDESingleConnectionException {

        File fwEnv = null;

        try {

            logger.info("MULESOFT - JDEClient - Definining Folder Cache for environment: " + environment);

            String cacheFolderWithEnvironment = "";
 
            logger.debug("MULESOFT - JDEClient - Temporal Ditectory: " + tmpFolder);

            cacheFolderWithEnvironment = tmpFolder.getAbsolutePath().concat(File.separator)
                .concat(environment);

            logger.info("MULESOFT - JDEClient - Folder Cache with Environmen: " + cacheFolderWithEnvironment);

            fwEnv = new File(cacheFolderWithEnvironment);

            if (fwEnv.exists() && fwEnv.isDirectory()) {
       
                logger.info("MULESOFT - JDEClient - Image Folder Cache with Environment has been verified: " + cacheFolderWithEnvironment);

            } else {

                FileUtils.forceMkdir(fwEnv);
 
                logger.debug("MULESOFT - JDEClient - Image Folder Cache with Environment has been verified and created: " + cacheFolderWithEnvironment);

            }

            logger.info("MULESOFT - JDEClient - Setting Image Folder cache in: " + fwEnv.getAbsolutePath());

        } catch (NullPointerException e) {

            logger.error("MULESOFT - JDEClient -  Folder Specs Image Cache Error..." + e.getMessage(), e);
            throw new JDESingleConnectionException("Folder Specs Image Cache Error: Null Pointer Exception", e);

        } catch (IOException e) {

            logger.error("MULESOFT - JDEClient - Error Generating Cache Folder..." + e.getMessage(), e);
            throw new JDESingleConnectionException("Folder Specs Image Cache Error: Cannot create Folder Cache", e);

        }

        return fwEnv;

    }

    @Override
    public boolean isWSConnection() {
        return false;
    }

    @Override
    public UserPreference getUserPreference() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
      
}
