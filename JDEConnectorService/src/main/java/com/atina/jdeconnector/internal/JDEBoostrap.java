/**
 * Copyright (C) 2019 ModusBox, Inc. All rights reserved.
 */
package com.atina.jdeconnector.internal;

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

    private static final Logger logger = LoggerFactory.getLogger(JDEBoostrap.class); 

    private static final String JDE_OUT_HANDLER = "JDEOutHandler";
    private static final String JDE_INTEROPINI_FILE = "jdeinterop.ini";
    private JDEHandler muleHandler;
    private String jdeDefaultFolder;

    public JDEBoostrap() {

        logger.debug("MULESOFT - JDEStartUpConfiguration - started...");
         
        logger.debug("MULESOFT - JDEStartUpConfiguration -      Temp Folder: " + FileUtils.getTempDirectory().getAbsolutePath());
 
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

            logger.info("MULESOFT - JDEStartUpConfiguration - Mule Handler added");

        }

    }

    public void removeNewJDEOutputHandler() {

        if (muleHandler != null) {

            JdeLog.removeUserLogOutputHandler(JDE_OUT_HANDLER);

            muleHandler.close();

            muleHandler = null;

            logger.debug("MULESOFT - JDEStartUpConfiguration - Mule Handler Removed");

        }

    }

    public synchronized void loadJDEServersOnInetAddressCache()
        throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {

        JDEJavaDNSCacheLoader.getInstance()
            .loadJDEServersInInetAddressCache();

    }

    public void setJDEDefaultFolder(String environment) throws Exception {

        logger.debug("MULESOFT - JDEStartUpConfiguration - Defining default folder for environment: " + (environment!=null?environment:"NULL"));
 
        if (jdeDefaultFolder == null) {

               try {

                defineDefaultFolder(environment);

            } catch (Exception e) {

                throw new Exception("Error defining JDE Default folder with classpath:" + getClassPath(), e);

            }

        }

        System.setProperty("default_path", jdeDefaultFolder);

        logger.info("MULESOFT - JDEStartUpConfiguration - Property Default Path: " + jdeDefaultFolder);
 
    }

    private void defineDefaultFolder(String environment) throws Exception {

        jdeDefaultFolder = "";

        logger.debug("MULESOFT - JDEStartUpConfiguration - Defining default folder for environment: " + environment);

        // Get Environment Resource

        URL environmentResource = Thread.currentThread()
            .getContextClassLoader()
            .getResource(environment);

        if (environmentResource != null) {

            logger.debug("MULESOFT - JDEStartUpConfiguration - Resource: " + environmentResource.toString());
 
            File environmentFolder = new File(environmentResource.getFile());

            if (!environmentFolder.exists()) {

                jdeDefaultFolder = getInteropIniFile();

            } else {

                jdeDefaultFolder = environmentFolder.getAbsolutePath();

            }

        } else {

            logger.debug("MULESOFT - JDEStartUpConfiguration - Resource is null");

            jdeDefaultFolder = getInteropIniFile();
        }

        logger.debug("MULESOFT - JDEStartUpConfiguration - Default Folder : " + jdeDefaultFolder);
 
    }

    private String getInteropIniFile() throws Exception {

        String interopIniFilePath = "";

        logger.debug("MULESOFT - JDEStartUpConfiguration - Getting Interop.ini file: " + JDE_INTEROPINI_FILE);

        URL jdeInteropResource = Thread.currentThread()
            .getContextClassLoader()
            .getResource(JDE_INTEROPINI_FILE);

        File jdeinteropFile = new File(jdeInteropResource.getFile());

        logger.debug("MULESOFT - JDEStartUpConfiguration - Interop.ini file: " + jdeinteropFile.getAbsolutePath());

        interopIniFilePath = jdeinteropFile.getParent();

        return interopIniFilePath;
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

        logger.debug("MULESOFT - JDEStartUpConfiguration - Getting resources for current thread: ");

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

        logger.debug("MULESOFT - JDEStartUpConfiguration - ClassPath: " + getClassPath());

    }

}
