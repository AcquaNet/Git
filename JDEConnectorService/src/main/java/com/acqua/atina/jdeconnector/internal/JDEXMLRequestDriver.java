/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acqua.atina.jdeconnector.internal;

import com.acqua.atina.jdeconnectorservice.JDEConnectorService;
import com.acqua.atina.jdeconnectorservice.exception.JDESingleException;
  
import com.jdedwards.system.connector.dynamic.UserSession;
import com.jdedwards.system.connector.dynamic.spec.SpecFailureException;
import com.jdedwards.system.xml.XMLRequest; 
import java.io.File;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.time.Duration;
import java.time.Instant; 
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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
    private final long sessionIdleMinutes = 10;
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
            
            SessionValues sv = new SessionValues(iSessionID,this.sessionIdleMinutes);
              
            // --------------------------------------------
            // Create XML Request
            // --------------------------------------------
            
            String xmlDoc = new String();
                xmlDoc += "<?xml version=\"1.0\"?>";
                xmlDoc += "<jdeRequest type=\"list\" user=\"" + sv.svUserSession.getUserName() + "\" pwd=\"" + sv.svPassword + "\" role=\"" + sv.svUserSession.getUserRole() + "\" environment=\"" + sv.svUserSession.getUserEnvironment() + "\" session=\"" + sv.svSession + "\" sessionidle=\"" + sv.svSessionIdle + "\">"; 
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
            
            XMLRequest xmlRequest = new XMLRequest(sv.svUserSession.getHost(),sv.svUserSession.getPort(), xmlDoc);
             
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
    
    public HashMap<String,Object> getReportDefintion(int iSessionID, String reportName, String cacheFolder) throws JDESingleException {
        
         
        String ubeName = "";
        String ubeVersion = "";
        Document docRequestResult;
        DocumentBuilderFactory dbFactory;
        DocumentBuilder dBuilder;
            
        try {
            
            // --------------------------------------------
            // Split Report Name to get name and version
            // --------------------------------------------
 
            StringTokenizer st = new StringTokenizer(reportName, "-");

            if (st.hasMoreElements()) {
                ubeName = (String) st.nextElement();

            }

            if (st.hasMoreElements()) {
                ubeVersion = (String) st.nextElement();

            }
            
            if(ubeName.isEmpty() || ubeVersion.isEmpty() )
            {
                logger.debug("JDEXMLRequestDriver: Invalid UBE Name: [" + reportName + "] " );
                     
                throw new ParserConfigurationException("JDEXMLRequestDriver: Invalid UBE Name: [" + reportName + "]  ");
                
            }
            
            
            // --------------------------------------------
            // Check Cache
            // --------------------------------------------
              
            String reportFile = cacheFolder + (cacheFolder.trim().endsWith(File.separator) ? "":File.separator) + "UBE_" + reportName + ".xml";
            
            File reportCache = new File(reportFile);
                     
            boolean cacheExists = reportCache.exists() && !reportCache.isDirectory();
            
            // --------------------------------------------
            // Generate Defintion
            // --------------------------------------------
            
            if(cacheExists)
            {
                
                logger.debug("JDEXMLRequestDriver: Reading cache: [" + reportFile + "] " );
                
                // ===================================================
                // Generate Doc and String Doc from local repository
                // ===================================================

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

                try {

                    factory.setNamespaceAware(true);
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    docRequestResult = builder.parse(reportCache);
                    
                    logger.debug("JDEXMLRequestDriver: cache: [" + reportFile + "] loaded" );

                } catch (Exception e) {

                    logger.debug("JDEXMLRequestDriver: Error reading xml cached:  [" + reportFile + "]" );
                     
                    throw new SpecFailureException("JDEXMLRequestDriver: Error reading xml cached:  [" + reportFile + "] " , e);
                
                }

                logger.debug("MULESOFT - UBEDefinition:  - getDefinition() Specs from Cache readed");
                
            } else
            {
                
                logger.debug("JDEXMLRequestDriver: getting specs: [" + reportFile + "] " );
                
                // --------------------------------------------
                // Get User Session
                // --------------------------------------------

                SessionValues sv = new SessionValues(iSessionID,this.sessionIdleMinutes);
                
                // --------------------------------------------
                // Create XML Request
                // --------------------------------------------

                String xmlDoc = new String();
                    xmlDoc += "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>";
                    xmlDoc += "<jdeRequest type=\"ube\" user=\"" + sv.svUserSession.getUserName() + "\" pwd=\"" + sv.svPassword + "\" role=\"" + sv.svUserSession.getUserRole() + "\" environment=\"" + sv.svUserSession.getUserEnvironment() + "\" session=\"" + sv.svSession + "\" sessionidle=\"" + sv.svSessionIdle + "\">"; 
                    xmlDoc += "<ACTION TEMPLATE_TYPE=\"LAUNCH_JOB\" TYPE=\"CREATE_XML\">";
                    xmlDoc += "<REPORT_NAME VALUE=\""+ubeName+"\"/>";
                    xmlDoc += "<REPORT_VERSION VALUE=\""+ubeVersion+"\"/>";
                    xmlDoc += "<JARGON_SYSTEM_CODE VALUE='1'/>";
                    xmlDoc += "<COMMENTS VALUE='1'/>";
                    xmlDoc += "<DATA_TYPING VALUE='1'/>";
                    xmlDoc += "<BUSINESS_VIEW VALUE='0'/>";
                    xmlDoc += "<PRINTER_INFORMATION VALUE='0'/>";
                    xmlDoc += "<POPULATED VALUE='1'/>";
                    xmlDoc += "</ACTION>";
                    xmlDoc += "</jdeRequest>";

                logger.debug("JDEXMLRequestDriver request: " + xmlDoc );
                
                // --------------------------------------------
                // Execute Request
                // --------------------------------------------
 
                XMLRequest xmlRequest = new XMLRequest(sv.svUserSession.getHost(),sv.svUserSession.getPort(), xmlDoc);

                String szDocRequestResult = xmlRequest.execute(500000);

                logger.debug("JDEXMLRequestDriver response: " + szDocRequestResult);

                // --------------------------------------------
                // Save Last Request Time
                // --------------------------------------------

                lastRequest = Instant.now();
                
                // --------------------------------------------
                // Convert Response to Document
                // --------------------------------------------
                 
                szDocRequestResult = cleanNonValidXMLCharacters(szDocRequestResult);
                
                dbFactory = DocumentBuilderFactory.newInstance();
                dBuilder = dbFactory.newDocumentBuilder();

                InputSource is = new InputSource(new StringReader(szDocRequestResult));

                docRequestResult = dBuilder.parse(is);
                
                if (docRequestResult != null) 
                {
                    docRequestResult.getDocumentElement().normalize();
                } else
                {
                    logger.debug("JDEXMLRequestDriver: Error converting XML response:  [" + reportFile + "]" );
                     
                    throw new SpecFailureException("JDEXMLRequestDriver: Error converting XML response:  [" + reportFile + "] ");
                    
                }
                
                // --------------------------------------------
                // Check Error
                // --------------------------------------------
                 
                // --------------------------------------------
                // Save Cache
                // --------------------------------------------
                
                if(!cacheExists)
                {
                    
                    TransformerFactory transformerFactory;
                    Transformer transformer;
                    DOMSource source;
                    StreamResult result;
                    
                    logger.debug("JDEXMLRequestDriver: saving specs: [" + reportFile + "] " );
                     
                    
                    transformerFactory = TransformerFactory.newInstance();
                    transformer = transformerFactory.newTransformer();
                    source = new DOMSource(docRequestResult);
                    result = new StreamResult(new File(reportFile).getAbsolutePath());
                    transformer.transform(source, result);
                      
                }
                 
            }
             
        } catch (Exception e) {

            logger.error(
                    "transaction() error: "
                    + e.getMessage());

            throw new JDESingleException(e.getMessage(), e);

        }

        return null;
        
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
    
    public class SessionValues {
       
        private UserSession svUserSession;
        private String svPassword;
        private String svSession;
        private String svSessionIdle;

        public SessionValues(int iSessionID, long sessionIdleAsMinutes) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
            
            // --------------------------------------------
            // Get User Session
            // --------------------------------------------
            
            svUserSession = com.jdedwards.system.connector.dynamic.Connector.getInstance()
                    .getUserSession(iSessionID);
            
            
            // --------------------------------------------
            // Get Server/Port from User Session
            // --------------------------------------------
            
            
            Field field = svUserSession.getClass().getDeclaredField("securityPassword");    
            field.setAccessible(true);
            svPassword = (String) field.get(svUserSession);
             
                          
            // --------------------------------------------
            // Set Session Id
            // --------------------------------------------
             
            svSession = "";
            svSessionIdle = Long.toString(sessionIdleAsMinutes  * 3600);
            
            if(!currentSessionId.isEmpty() && lastRequest != null)
            {
                Instant currentTime = Instant.now();
   
                long timeElapsed = Duration.between(lastRequest, currentTime).toMinutes();
                
                if(timeElapsed <= sessionIdleMinutes )
                {
                    svSession = currentSessionId;
                }  
                
            }  
            
        }
 
   }
    
    
}
