/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acqua.atina.jdeconnector.internal;

import com.acqua.atina.jdeconnectorservice.JDEConnectorService;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.PrivilegedAction;
import java.security.Security;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author franciscogodinoconte
 */
public class JDEJavaDNSCacheLoader {
    
    private static final Logger logger = LoggerFactory.getLogger(JDEConnectorService.class);

    private static final String cachePolicyProp = "networkaddress.cache.ttl";
    private Map<String, String> ocmServers;

    public static JDEJavaDNSCacheLoader getInstance() {
        return DNSCacheLoaderHolder.INSTANCE;
    }

    private static class DNSCacheLoaderHolder {

        private DNSCacheLoaderHolder() {
        }

        private static final JDEJavaDNSCacheLoader INSTANCE = new JDEJavaDNSCacheLoader();
    }

    public void loadJDEServersInInetAddressCache(boolean forceUseOCMFile)
        throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        logger.info("Loading JDE Inet Address...");
        
        if (this.ocmServers == null) {

            logger.info("Loading OCM Servers in DNS Cache ...");

            String networkaddressCacheTTL = System.getProperty("networkaddress.cache.ttl");
             
            if (networkaddressCacheTTL != null) {
                logger.info("Property 'networkaddress.cache.ttl' value is " + networkaddressCacheTTL);
            } else
            {
                logger.info("           System networkaddress.cache.ttl: NULL"); 
            }

            showPolicy();

            ocmServers = (Map<String, String>)JDEPropertyReader.getInstance()
                .getOCMServers();
            
            logger.info("       OCM Server loaded");
            

        }

        if (this.ocmServers != null && !ocmServers.isEmpty()) {
            
            logger.info("Loading JDE Inet Address...");

            if (ocmServers.size() > 0) {

                Iterator<Entry<String, String>> serverValues = ocmServers.entrySet()
                    .iterator();

                while (serverValues.hasNext()) {

                    // Get DNS Record

                    Entry<String, String> dsnRecord = serverValues.next();

                    String jdeServerName = dsnRecord.getKey()
                        .trim();

                    String fqdnOrIP = dsnRecord.getValue()
                        .trim();

                    logger.info("Processing JDE Server Name Mapping: [" + jdeServerName + "/" + fqdnOrIP + "]");

                    // Validate jdeServerName

                    InetAddress ipJDEServer = null;

                    boolean jdeServerValid = false;

                    logger.info("ATINA - DNSCacheLoader: loadOCMServerInDNSCache: Validation JDE Server Name: [" + jdeServerName + "] ... ");

                    if(!forceUseOCMFile)
                    {
                        try {

                            logger.info("         Resolving ip for : [" + jdeServerName + "]");

                            ipJDEServer = InetAddress.getByName(jdeServerName);

                            logger.info("         InetAddress : [" + ipJDEServer.toString() + "]");

                            byte[] ip = ipJDEServer.getAddress();

                            logger.info("         Bytes for IP: [" + Arrays.toString(ip) + "]");

                            logger.info("         JDE Server: [" + jdeServerName + "] has IP [" + getIPString(ip) + "]");

                            jdeServerValid = true;

                        } catch (UnknownHostException e) { // NOSONAR

                            logger.info("         JDE Server: [" + jdeServerName + "] hasn't IP. Msg:" + e.getMessage());

                        }
                    
                    }

                    // get IP address from FQDN or IP

                    if (!jdeServerValid) {

                        logger.info("         Validation FQDN or IP: [" + fqdnOrIP + "] ... ");

                        try {

                            ipJDEServer = InetAddress.getByName(fqdnOrIP);

                            byte[] ip = ipJDEServer.getAddress();

                            logger.info("         JDE Server: [" + fqdnOrIP + "] has IP [" + getIPString(ip) + "]");

                            // Adding JDE Server name to DNS Cache

                            logger.info("         Adding JDE Server name to DNS Cache ...");

                            registerHost(jdeServerName, ipJDEServer);

                            logger.info("         JDE Server [" + jdeServerName + "] has been added to DNS Cache with this IP: ["
                                        + getIPString(ip) + "]");

                        } catch (UnknownHostException e) { // NOSONAR

                            logger.info("         JDE Server: [" + fqdnOrIP + "] hasn't IP. Msg:" + e.getMessage());

                        }

                    }

                }

            }

        } else {

            logger.info("There isn't [OCM_SERVERS] section inside jdeinterop.ini file");

        }

    }

    private void showPolicy() {

        Integer tmp = null;

        try {

            tmp = Integer.valueOf(java.security.AccessController.doPrivileged(
                new PrivilegedAction<String>() {

                    public String run() {
                        return Security.getProperty(cachePolicyProp);
                    }
                }));

        } catch (NumberFormatException e) {
            // Ignore
        }

        if (tmp != null) {
            int cachePolicy = tmp.intValue();
            logger.info("Cache Policy: " + cachePolicy);

        } else {
            logger.debug("ATINA - DNSCacheLoader: loadOCMServerInDNSCache: Cache Policy doesn't exist");
        }

    }

    private void registerHost(String host, InetAddress... ip)
        throws NoSuchMethodException, SecurityException, UnknownHostException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        Method method = InetAddress.class.getDeclaredMethod("cacheAddresses", String.class, InetAddress[].class, Boolean.TYPE);
        method.setAccessible(true);
        InetAddress someInetAddress = InetAddress.getLocalHost();
        method.invoke(someInetAddress, host, ip, true);

    }

    public void clean() {
        this.ocmServers = null;
    }

    private String getIPString(byte[] ipInBytes) {

        StringBuilder ipSB = new StringBuilder();

        int temp = 0;

        for (int i = 0, j = ipInBytes.length; i < j; i++) {

            temp = (int)(ipInBytes[i] & 255);

            if (i != 3) {
                ipSB.append(temp)
                    .append(".");
            } else {
                ipSB.append(temp);
            }
        }

        return ipSB.toString();
    }
    
}
