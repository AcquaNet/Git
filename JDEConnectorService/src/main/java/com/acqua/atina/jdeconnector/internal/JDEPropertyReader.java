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
public class JDEPropertyReader {

    private static final Logger logger = LoggerFactory.getLogger(JDEConnectorService.class);
    private static final String JDE_INTEROP_INI = "jdeinterop.ini";
    private static final String VALIDATE_SERVICES_BOTH = "BOTH";
    private static final String VALIDATE_SERVICES_UBENAME = "R0008P_XJDE0001";
    private String serverName;
    private int serverPort;
    private boolean updateTransaction;
    private boolean lockRecorsYN;
    private int xmlSessionIdle;
    private boolean useSessionIdYN;
    private String updated;
    private String specialEDItables;
    private HashMap<String, String> ocmServers;
    private String validateEnterprisesServicesWith;
    private String validateEnterprisesServicesUBEName;
    private int threadsToGetMetadata;

    private JDEPropertyReader() {
        updateTransaction = true;
        lockRecorsYN = false;
        xmlSessionIdle = 3000;
        lockRecorsYN = true;
        useSessionIdYN = true;
        specialEDItables = "";
        validateEnterprisesServicesWith = VALIDATE_SERVICES_BOTH;
        validateEnterprisesServicesUBEName = VALIDATE_SERVICES_UBENAME;
        threadsToGetMetadata = 1;
    }

    public static JDEPropertyReader getInstance() {
        return JDEConfigHolder.INSTANCE;
    }

    private static class JDEConfigHolder {

        private JDEConfigHolder() {
        }

        private static final JDEPropertyReader INSTANCE = new JDEPropertyReader();
    }

    public String getValidateEnterprisesServicesUBEName() {
        return validateEnterprisesServicesUBEName;
    }

    public String getValidateEnterprisesServicesWith() {
        return validateEnterprisesServicesWith;
    }

    public void setUseSessionIdYN(boolean useSessionIdYN) {
        this.useSessionIdYN = useSessionIdYN;
    }

    public boolean getUseSessionIdYN() {
        return useSessionIdYN;
    }

    public String getServerName() {
        return serverName;
    }

    public int getServerPort() {
        return serverPort;
    }

    public boolean getUpdateTransaction() {
        return updateTransaction;
    }

    public boolean getLockRecorsYN() {
        return lockRecorsYN;
    }

    public void setUpdateTransaction(boolean updateTransaction) {
        this.updateTransaction = updateTransaction;
    }

    public void setLockRecorsYN(boolean lockRecorsYN) {
        this.lockRecorsYN = lockRecorsYN;
    }

    public int getXmlSessionIdle() {
        return xmlSessionIdle;
    }

    public String getSpecialEDItables() {
        return specialEDItables;
    }

    public void getEventFlags() throws IOException {

        if (updated == null) {

            readConfigFile();

            updated = "Y";

        }

    }

    public void getServerNameAndPort() throws IOException {

        if (this.serverName == null) {

            readConfigFile();
        }

    }

    public Map<String, String> getOCMServers() throws IOException {

        if (this.ocmServers == null || ocmServers.isEmpty()) {

            ocmServers = new HashMap<String, String>();

            readConfigFile();

        }

        return ocmServers;

    }

    public int getThreadsToGetMetadata() {
        return threadsToGetMetadata;
    }

    public void resetUpdated() {

        this.updated = null;
        this.serverName = null;
        if (ocmServers != null) {
            this.ocmServers.clear();
        }
        this.serverPort = 0;
        this.xmlSessionIdle = 0;
        this.updateTransaction = true;
        this.lockRecorsYN = false;
        this.xmlSessionIdle = 3000;
        this.useSessionIdYN = true;
        this.specialEDItables = "";
        this.validateEnterprisesServicesWith = VALIDATE_SERVICES_BOTH;
        this.validateEnterprisesServicesUBEName = VALIDATE_SERVICES_UBENAME;
        this.threadsToGetMetadata = 2;

    }

    private void readConfigFile() throws IOException {

        InputStream inputStreamXMLBase;

        String defaulFolder = JDEBoostrap.getInstance()
            .getJdeDefaultFolder();

        File interopFile = new File(defaulFolder + File.separator + JDE_INTEROP_INI);

        try {

            inputStreamXMLBase = FileUtils.openInputStream(interopFile);

            InputStreamReader streamReader = new InputStreamReader(inputStreamXMLBase, "UTF-8");

            BufferedReader bufferReader = new BufferedReader(streamReader);

            boolean serverFound = false;

            boolean ocmServerFound = false;

            // reads each line

            String l;

            while ((l = bufferReader.readLine()) != null) {

                if (l.startsWith("validateEnterpriseServicesUBEName")) {

                    this.validateEnterprisesServicesUBEName = l.substring(34)
                        .trim()
                        .toUpperCase();

                }

                if (l.startsWith("validateEnterpriseServicesWith")) {

                    this.validateEnterprisesServicesWith = l.substring(31)
                        .trim()
                        .toUpperCase();

                }

                if (l.startsWith("updateEventsYN")) {

                    updateTransaction = l.substring(15)
                        .compareTo("Y") == 0;

                }

                if (l.startsWith("lockEventsYN")) {

                    lockRecorsYN = l.substring(13)
                        .compareTo("Y") == 0;

                }

                if (l.startsWith("sessionIdle")) {

                    xmlSessionIdle = Integer.parseInt(l.substring(12));

                }

                if (l.startsWith("threadsForMetadata")) {

                    threadsToGetMetadata = Integer.parseInt(l.substring(19));

                    if (threadsToGetMetadata > 5)
                    {
                        threadsToGetMetadata = 5;
                    }

                }

                if (l.startsWith("useSessionIdYN")) {

                    useSessionIdYN = l.substring(15)
                        .compareTo("Y") == 0;

                }

                if (l.startsWith("specialEDITables")) {

                    specialEDItables = l.substring(17);

                }

                // Server Name and Port

                if (l.startsWith("[INTEROP]")) {

                    logger.debug("BEGIN [INTEROP]");

                    serverFound = true;

                }

                if (l.startsWith("enterpriseServer") && serverFound) {

                    this.serverName = l.substring(17);

                    logger.debug("Server=" + this.serverName);

                }

                if (l.startsWith("e1servername") && serverFound) {

                    this.serverName = l.substring(13);

                    logger.debug("Server=" + this.serverName);

                }

                if (l.startsWith("port") && serverFound) {

                    this.serverPort = Integer.parseInt(l.substring(5));

                    logger.debug("Port=" + this.serverPort);

                }

                if (l.startsWith("[") && !l.startsWith("[INTEROP]") && serverFound) {

                    logger.debug("END [INTEROP]");

                    serverFound = false;

                }

                // OCM Servers

                if (l.startsWith("[OCM_SERVERS]")) {

                    logger.debug("BEGIN [OCM_SERVERS]");

                    ocmServerFound = true;

                }

                if (!l.startsWith("[") && ocmServerFound && !l.startsWith("#") && l.contains("=")) {

                    String lines[] = l.split("=");
                    if (lines.length != 0) {
                        ocmServers.put(lines[0].trim(), lines[1].trim());
                    }

                }

                if (l.startsWith("[") && ocmServerFound && !l.startsWith("[OCM_SERVERS]")) {

                    logger.debug("END [OCM_SERVERS]");

                    ocmServerFound = false;

                }

            }

            bufferReader.close();

            streamReader.close();

        } catch (IOException e) {

            logger.error("MULESOFT - JDEConnectorService:  - getDefinition() Error jdeinterop.ini");

            throw e;

        }

        updated = "Y";

    }

}
