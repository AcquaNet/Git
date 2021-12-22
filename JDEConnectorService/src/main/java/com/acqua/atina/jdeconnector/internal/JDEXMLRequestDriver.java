/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acqua.atina.jdeconnector.internal;

import com.acqua.atina.jdeconnectorservice.JDEConnectorService;
import com.acqua.atina.jdeconnectorservice.exception.JDESingleException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
 
import com.jdedwards.system.connector.dynamic.UserSession;
import com.jdedwards.system.connector.dynamic.spec.SpecFailureException;
import com.jdedwards.system.xml.XMLRequest; 
import java.io.StringReader;
import java.lang.reflect.Field;
import java.time.Duration;
import java.time.Instant; 
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author jgodi
 */
public class JDEXMLRequestDriver {
     
    private static final Logger logger = LoggerFactory.getLogger(JDEConnectorService.class);
    
    private String currentSessionId = "";
    private long sessionIdleMinutes = 10;
    private Instant lastRequest;
    
    private JDEXMLRequestDriver() {
    }
    
    public static JDEXMLRequestDriver getInstance() {
        return JDEXMLRequestDriverHolder.INSTANCE;
    }
    
    private static class JDEXMLRequestDriverHolder {

        private static final JDEXMLRequestDriver INSTANCE = new JDEXMLRequestDriver();
    }
    
    
    public HashMap<String,Object> getTableDefintion(int iSessionID, String tableName) throws JDESingleException {
        
        
         
        HashMap<String,Object> returnObject = new HashMap<String,Object>();
        
        logger.debug("JDEXMLRequestDriver getTableDefintion: " + tableName );
        
        try {

            // --------------------------------------------
            // Get User Session
            // --------------------------------------------
            
            UserSession userSession = com.jdedwards.system.connector.dynamic.Connector.getInstance()
                    .getUserSession(iSessionID);
            
            // --------------------------------------------
            // Get Server/Port from User Session
            // --------------------------------------------
            
            
            Field field = userSession.getClass().getDeclaredField("securityPassword");    
            field.setAccessible(true);
            String password = (String) field.get(userSession);
             
                          
            // --------------------------------------------
            // Set Session Id
            // --------------------------------------------
            
            
            String session = "";
            String sessionIdle = Long.toString(sessionIdleMinutes  * 3600);
            
            if(!currentSessionId.isEmpty() && lastRequest != null)
            {
                Instant currentTime = Instant.now();
   
                long timeElapsed = Duration.between(lastRequest, currentTime).toMinutes();
                
                if(timeElapsed <= sessionIdleMinutes )
                {
                    session = currentSessionId;
                }  
                
            }  
             
            
            // --------------------------------------------
            // Create XML Request
            // --------------------------------------------
            
            String xmlDoc = new String();
                xmlDoc += "<?xml version=\"1.0\"?>";
                xmlDoc += "<jdeRequest type=\"list\" user=\"" + userSession.getUserName() + "\" pwd=\"" + password + "\" role=\"" + userSession.getUserRole() + "\" environment=\"" + userSession.getUserEnvironment() + "\" session=\"" + session + "\" sessionidle=\"" + sessionIdle + "\">"; 
                xmlDoc += "<ACTION TYPE=\"GetTemplate\">";
                xmlDoc += "<TABLE_NAME VALUE=\"" + tableName +"\"/>";
                xmlDoc += "<TABLE_TYPE VALUE=\"OWTABLE\"/>";
                xmlDoc += "<TEMPLATE_TYPE VALUE=\"OUTPUT\"/>";
                xmlDoc += "</ACTION>";
                xmlDoc += "</jdeRequest>";
            
            logger.debug("JDEXMLRequestDriver request: " + xmlDoc );
                
            // --------------------------------------------
            // Execute Request
            // --------------------------------------------
            
            XMLRequest xmlRequest = new XMLRequest(userSession.getHost(),userSession.getPort(), xmlDoc);
             
            String szDocRequestResult = xmlRequest.execute();
            
            logger.debug("JDEXMLRequestDriver response: " + szDocRequestResult);
             
            // --------------------------------------------
            // Save Last Request Time
            // --------------------------------------------
            
            lastRequest = Instant.now();
             
            // --------------------------------------------
            // Convert Request to HashMap
            // --------------------------------------------
            
            DocumentBuilderFactory dbFactory;
        
            DocumentBuilder dBuilder;
            
            Document docRequestResult;
        
            if (!szDocRequestResult.isEmpty()) {
                 
                szDocRequestResult = cleanNonValidXMLCharacters(szDocRequestResult);
                
                dbFactory = DocumentBuilderFactory.newInstance();
                
                dBuilder = dbFactory.newDocumentBuilder();
                
                InputSource is = new InputSource(new StringReader(szDocRequestResult));
                
                docRequestResult = dBuilder.parse(is);
                
                if (docRequestResult != null) {
                    
                    docRequestResult.getDocumentElement()
                        .normalize();
                    
                    logger.debug("JDEXMLRequestDriver validated");

                } else {
                    
                    logger.debug("JDEXMLRequestDriver: Error Generating XML Response");
                     
                    throw new SpecFailureException("JDEXMLRequestDriver: Error Generating XML Response", null);

                }
                
                // Was there an error?
                
                NamedNodeMap attr = null;
                Node nodeAttr = null;
                Node nodejdeJDE = null;
        
                // <?xml version='1.0' ?>
                // <jdeResponse type="list" session="23080.1640127296.12">
               	// <returnCode code='15'>TC Wrapper error</returnCode>
                // <ACTION TYPE="GetTemplate">
                // <Error>Error in building TC header</Error>
                // </ACTION>
                // </jdeResponse>
                
                nodejdeJDE = docRequestResult.getElementsByTagName("jdeResponse").item(0);
                
                if (nodejdeJDE != null) {
                    
                    attr = nodejdeJDE.getAttributes();
                     
                    if (attr != null && attr.getNamedItem("session") != null) {
                        
                        nodeAttr = attr.getNamedItem("session");
                        
                        if(nodeAttr != null)
                        {
                            currentSessionId = nodeAttr.getNodeValue();
                        }
                        
                    }
                    
                }
                  
                nodejdeJDE = docRequestResult.getElementsByTagName("returnCode").item(0);
                
                String returnCode = "";
                String returnText = "";
                
                if (nodejdeJDE != null) {

                    attr = nodejdeJDE.getAttributes(); 

                    if (attr != null && attr.getNamedItem("code") != null) {

                        nodeAttr = attr.getNamedItem("code");
                        
                        if(nodeAttr != null)
                        {
                            returnCode = nodeAttr.getNodeValue();
                        }
 
                    }

                }
                 
                if(returnCode.isEmpty())
                {
                    logger.debug("JDEXMLRequestDriver: Invalid XML Response: [" + szDocRequestResult + "] " );
                     
                    throw new SpecFailureException("JDEXMLRequestDriver: Invalid XML Response: [" + szDocRequestResult + "] ", null);
                }
                
                if(!returnCode.equals("0"))
                {
                    
                    nodejdeJDE = docRequestResult.getElementsByTagName("Error").item(0);
                    
                    if (nodejdeJDE != null) {
                        
                        returnText = nodejdeJDE.getNodeValue();
                        
                    }
                    
                    logger.debug("JDEXMLRequestDriver: XML Response: [" + returnCode + "] " + returnText );
                     
                    throw new SpecFailureException("JDEXMLRequestDriver: XML Response: [" + returnCode + "] " + returnText , null);
                }
                
                returnObject.put("returnCode", returnCode);
                //returnObject.put("returnText", returnText); 
                returnObject.put("reponse", new HashMap<String,Object>());
                
                 
                // --------------------------------------------
                // Prepare Response
                // --------------------------------------------
                 
                nodejdeJDE = docRequestResult.getElementsByTagName("ACTION").item(0);
                
                if (nodejdeJDE != null) {
                    
                    Node child = nodejdeJDE.getFirstChild();
                     
                    while(child != null)
                    {
                        HashMap<String,Object> column = new HashMap<String,Object>();
                        
                        child = child.getNextSibling();
                        
                        if(child != null)
                        {
                            if(child.getNodeName().equals("COLUMN"))
                            {
                                 
                                attr = child.getAttributes();
                                
                                for (int i=0;i<attr.getLength();i++) {
                                    
                                    Node valueAt = attr.item(i);
                                    
                                    column.put(valueAt.getNodeName(), valueAt.getNodeValue()); 
                                    
                                }
                                  
                            }
                            
                        }
                        
                        if(column.size()>0)
                        {
                            ((HashMap) returnObject.get("reponse")).put(column.get("NAME"), column);
                        }
                        
                        
                    }
                    
                }
                
                
            }
            
             
                

        } catch (Exception e) {

            logger.error(
                    "transaction() error: "
                    + e.getMessage());

            throw new JDESingleException(e.getMessage(), e);

        }

         
        return returnObject;
         
    }
    
    
    private String cleanNonValidXMLCharacters(String in) {
        
        StringBuffer output = new StringBuffer(); // Used to hold the output.
        char currentChar; // Used to reference the currentChar character.

        if (in == null || ("".equals(in)))
            return ""; // vacancy test.
        
        for (int i = 0; i < in.length(); i++) {
            currentChar = in.charAt(i); // NOTE: No IndexOutOfBoundsException caught here; it should not happen.
            if ((currentChar == 0x9) ||
                (currentChar == 0xA) ||
                (currentChar == 0xD) ||
                ((currentChar >= 0x20) && (currentChar <= 0xD7FF)) ||
                ((currentChar >= 0xE000) && (currentChar <= 0xFFFD)) ||
                ((currentChar >= 0x10000) && (currentChar <= 0x10FFFF)))
                output.append(currentChar);
        }
        
        return output.toString();
    }
    
}
