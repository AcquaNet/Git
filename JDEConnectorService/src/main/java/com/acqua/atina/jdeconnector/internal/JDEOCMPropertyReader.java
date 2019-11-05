/**
 * Copyright (C) 2019 ModusBox, Inc. All rights reserved.
 */
package com.acqua.atina.jdeconnector.internal;

import com.acqua.atina.jdeconnectorservice.JDEConnectorService;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JDE Configuration Object
 * 
 * @author ModusBox, Inc.
 */
public class JDEOCMPropertyReader {

    private static final Logger logger = LoggerFactory.getLogger(JDEConnectorService.class);
    private static final String JDE_INTEROP_INI = "ocm.ini"; 
    private HashMap<String, String> ocmServers; 

    private JDEOCMPropertyReader() {
       
    }

    public static JDEOCMPropertyReader getInstance() {
        return JDEOCMPropertyHolder.INSTANCE;
    }

    private static class JDEOCMPropertyHolder {

        private JDEOCMPropertyHolder() {
        }

        private static final JDEOCMPropertyReader INSTANCE = new JDEOCMPropertyReader();
    }
 

    public Map<String, String> getOCMServers() throws IOException {

        if (this.ocmServers == null || ocmServers.isEmpty()) {

            ocmServers = new HashMap<String, String>();

            readConfigFile();

        }

        return ocmServers;

    }
 

    public void resetUpdated() {

        logger.info("                             JDEOCMPropertyReader Reseting values...");
        
        
        if (ocmServers != null) {
            this.ocmServers.clear();
        }
         
        logger.info("                             JDEOCMPropertyReader cleared...");

    }

    private void readConfigFile() throws IOException {

        logger.info("                             JDEOCMPropertyReader Reading Config File...");
        
        InputStream inputStreamXMLBase;

        String defaulFolder = JDEBoostrap.getInstance()
            .getJdeDefaultFolder();

        File interopFile = new File(defaulFolder + File.separator + JDE_INTEROP_INI);
        
        logger.info("                             JDEOCMPropertyReader          Config File: " + interopFile.getAbsolutePath());

        try {

            inputStreamXMLBase = FileUtils.openInputStream(interopFile);

            InputStreamReader streamReader = new InputStreamReader(inputStreamXMLBase, "UTF-8");

            BufferedReader bufferReader = new BufferedReader(streamReader);

            boolean serverFound = false;

            boolean ocmServerFound = false;

            // reads each line

            String l;

            while ((l = bufferReader.readLine()) != null) {

                 
                // OCM Servers

                if (l.startsWith("[OCM_SERVERS]")) {

                    logger.info("                             BEGIN [OCM_SERVERS]");

                    ocmServerFound = true;

                }

                if (!l.startsWith("[") && ocmServerFound && !l.startsWith("#") && l.contains("=")) {

                    logger.info("                                 Value: [" + l + "]");
                    
                    String lines[] = l.split("=");
                    
                    if (lines.length != 0) {
                        ocmServers.put(lines[0].trim(), lines[1].trim());
                    }

                }

                if (l.startsWith("[") && ocmServerFound && !l.startsWith("[OCM_SERVERS]")) {

                    logger.info("                             END [OCM_SERVERS]");

                    ocmServerFound = false;

                }

            }

            bufferReader.close();

            streamReader.close();

        } catch (IOException e) {

            logger.error("                             JDEOCMPropertyReader Error: " + e.getMessage());

            throw e;

        } 

    }

}
