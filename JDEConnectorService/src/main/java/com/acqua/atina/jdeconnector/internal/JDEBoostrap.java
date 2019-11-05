/**
 * Copyright (C) 2019 ModusBox, Inc. All rights reserved.
 */
package com.acqua.atina.jdeconnector.internal;

import com.acqua.atina.jdeconnectorservice.JDEConnectorService;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Enumeration;

import org.apache.commons.io.FileUtils; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
import com.jdedwards.base.logging.JdeLog;
import com.jdedwards.system.kernel.LocalLogicControl;

public class JDEBoostrap {

    private static final Logger logger = LoggerFactory.getLogger(JDEConnectorService.class); 

    private static final String JDE_OUT_HANDLER = "JDEOutHandler";
    private static final String JDE_INTEROPINI_FILE = "jdeinterop.ini";
    private JDEHandler muleHandler;
    private String jdeDefaultFolder;

    public JDEBoostrap() {

        logger.info("JDE ATINA - JDEStartUpConfiguration - started...");
         
        logger.info("JDE ATINA - JDEStartUpConfiguration -      Temp Folder: " + FileUtils.getTempDirectory().getAbsolutePath());
 
        LocalLogicControl.getInstance().setEnabled(false);

    }

    public static JDEBoostrap getInstance() {
        return JDEStartUpConfigurationHolder.INSTANCE;
    }

    private static class JDEStartUpConfigurationHolder {

        private JDEStartUpConfigurationHolder() {
        }

        private static final JDEBoostrap INSTANCE = new JDEBoostrap();
    }

    public synchronized void addNewJDEOutputHandler() {

        if (muleHandler == null) {

            muleHandler = new JDEHandler();

            JdeLog.addUserLogOutputHandler(JDE_OUT_HANDLER, muleHandler);

            logger.info("JDE ATINA  - JDEStartUpConfiguration - Mule Handler added");

        }

    }

    public void removeNewJDEOutputHandler() {

        if (muleHandler != null) {

            JdeLog.removeUserLogOutputHandler(JDE_OUT_HANDLER);

            muleHandler.close();

            muleHandler = null;

            logger.info("JDE ATINA - JDEStartUpConfiguration - Mule Handler Removed");

        }

    }

    public synchronized void loadJDEServersOnInetAddressCache(boolean forceUseOCMFile)
        throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {

        JDEJavaDNSCacheLoader.getInstance()
            .loadJDEServersInInetAddressCache(forceUseOCMFile);

    }

    public void setJDEDefaultFolderForMicroService(String environment) throws Exception {
         
        logger.info("JDE ATINA -                             JDEStartUpConfiguration - Setting Default Folder. Current Default Folder: " + (jdeDefaultFolder!=null?jdeDefaultFolder:"NULL"));
        
        File tmpFolder = new File(FileUtils.getTempDirectory()
                .getAbsolutePath());

        logger.info("JDE ATINA -                             JDEStartUpConfiguration - Setting Default Folder. Temporal Folder: " + tmpFolder);

        jdeDefaultFolder = tmpFolder.getAbsolutePath().concat(File.separator)
                .concat("config").concat(File.separator)
                .concat(environment);

        logger.info("JDE ATINA -                             JDEStartUpConfiguration - Setting Default Folder. New Default Folder: " + (jdeDefaultFolder != null ? jdeDefaultFolder : "NULL"));

        System.setProperty("default_path", jdeDefaultFolder);
            
        logger.info("JDE ATINA -                             JDEStartUpConfiguration - Default Folder : " + jdeDefaultFolder);
        
    }
      
    public String getJdeDefaultFolder() {
        return jdeDefaultFolder;
    }

    public void setJdeDefaultFolder(String jdeDefaultFolder) {
        this.jdeDefaultFolder = jdeDefaultFolder;
    }

    public void clean() {
        this.muleHandler = null;
        this.jdeDefaultFolder = null;
    }

    private String getClassPath() throws IOException
    {

        logger.info("JDE ATINA - JDEStartUpConfiguration - Getting resources for current thread: ");

        StringBuilder stringBuilder = new StringBuilder();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        Enumeration<URL> e = classLoader.getResources("");

        stringBuilder.append("Resources:[");

        while (e.hasMoreElements())
        {
            stringBuilder.append(e.nextElement());
            stringBuilder.append("|");
        }

        stringBuilder.append("]");

        return stringBuilder.toString();

    }

    public void copyJDEIniFilesToClasspath(String environment) throws Exception
    {

        logger.info("JDE ATINA - JDEStartUpConfiguration - ClassPath: " + getClassPath());

    }

}
