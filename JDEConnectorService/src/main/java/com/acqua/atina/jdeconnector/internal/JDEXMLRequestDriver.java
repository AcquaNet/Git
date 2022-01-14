/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acqua.atina.jdeconnector.internal;

import com.acqua.atina.jdeconnector.sqltransform.TransformSQSentenceLtoXMLSentece;
import com.acqua.atina.jdeconnectorservice.JDEConnectorService;
import com.acqua.atina.jdeconnectorservice.exception.JDESingleException;
  
import com.jdedwards.system.connector.dynamic.UserSession;
import com.jdedwards.system.connector.dynamic.spec.SpecFailureException;
import com.jdedwards.system.xml.XMLRequest; 
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.time.Duration;
import java.time.Instant; 
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.StringTokenizer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
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
    
    private static final Integer TIMEOUT = 500000;
    
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
             
            String szDocRequestResult = xmlRequest.execute(TIMEOUT);
            
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
        Node nodeDoc = null;
        Node nodeAttr = null;
        NamedNodeMap attr = null;
        
        HashMap<String,Object> returnValue = new HashMap<String,Object>();
            
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
            // Get Report Definition
            // --------------------------------------------
             
            docRequestResult = getReportDefinition(iSessionID, cacheFolder, reportName, ubeName, ubeVersion);
            
            // --------------------------------------------
            // Prepare Response
            // --------------------------------------------
            
            // Processing Options
            
            nodeDoc = docRequestResult.getElementsByTagName("AVAILABLE_MEMBERS").item(0);
              
            HashMap<String,Object> pos = new HashMap<String,Object>();
            
            if (nodeDoc != null) {

                if (nodeDoc.getParentNode().getNodeName().equals("PROCESSING_OPTIONS")) {

                    NodeList nodesMember = nodeDoc.getChildNodes();

                    if (nodesMember != null && nodesMember.getLength() > 0) {

                        for (int i = 0; i < nodesMember.getLength(); i++) {

                            attr = nodesMember.item(i).getAttributes();

                            String name = "";
                            String value = "";

                            if (attr != null && attr.getNamedItem("ID") != null) {

                                name = attr.getNamedItem("ID").getNodeValue();

                            }

                            if (attr != null && attr.getNamedItem("VALUE") != null) {

                                value = attr.getNamedItem("VALUE").getNodeValue();

                            }

                            pos.put(name,value);

                        }

                    }

                }

            }
            
            returnValue.put("PROCESSING_OPTIONS", pos);
            
            // Report Interconnect
            
            nodeDoc = docRequestResult.getElementsByTagName("REPORT_INTERCONNECT").item(0); 
            
            HashMap<String,Object> ris = new HashMap<String,Object>();
            
            if (nodeDoc != null) {

                NodeList nodesMember = nodeDoc.getChildNodes();

                if (nodesMember != null && nodesMember.getLength() > 0) {

                    for (int i = 0; i < nodesMember.getLength(); i++) {

                        attr = nodesMember.item(i).getAttributes();

                        String name = "";
                            String value = "";

                        if (attr != null && attr.getNamedItem("ID") != null) {

                            name = attr.getNamedItem("ID").getNodeValue();

                        }

                        if (attr != null && attr.getNamedItem("VALUE") != null) {

                            value = attr.getNamedItem("VALUE").getNodeValue();

                        }

                        ris.put(name,value);

                    }

                }

            }
            
            returnValue.put("REPORT_INTERCONNECT", ris);
            
            // JOBQUEUE
            
            nodeDoc = docRequestResult.getElementsByTagName("JOBQUEUE").item(0);
            
            String jobQueue = "";

            if (nodeDoc != null) {

                attr = nodeDoc.getAttributes();

                if (attr != null && attr.getNamedItem("VALUE") != null) {

                    nodeAttr = attr.getNamedItem("VALUE");

                    jobQueue = nodeAttr.getNodeValue();

                }

            }

            returnValue.put("JOBQUEUE", jobQueue);
            
            
             
        } catch (Exception e) {

            logger.error(
                    "transaction() error: "
                    + e.getMessage());

            throw new JDESingleException(e.getMessage(), e);

        }

        return returnValue;
        
    }
    
    private Document getReportDefinition(int iSessionID, String cacheFolder, String reportName, String ubeName, String ubeVersion)
    {
        
        Document docRequestResult;

        DocumentBuilderFactory dbFactory;
        DocumentBuilder dBuilder;
 
        Node nodeDoc = null;
        Node nodeAttr = null;
        NamedNodeMap attr = null;

        try {

            String reportFile = cacheFolder + (cacheFolder.trim().endsWith(File.separator) ? "" : File.separator) + "UBE_" + reportName + ".xml";

            File reportCache = new File(reportFile);

            boolean cacheExists = reportCache.exists() && !reportCache.isDirectory();

            // --------------------------------------------
            // Generate Defintion
            // --------------------------------------------
            if (cacheExists) {

                logger.debug("JDEXMLRequestDriver: Reading cache: [" + reportFile + "] ");

                // ===================================================
                // Generate Doc and String Doc from local repository
                // ===================================================
                //
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

                try {

                    factory.setNamespaceAware(true);
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    docRequestResult = builder.parse(reportCache);

                    logger.debug("JDEXMLRequestDriver: cache: [" + reportFile + "] loaded");

                } catch (Exception e) {

                    logger.debug("JDEXMLRequestDriver: Error reading xml cached:  [" + reportFile + "]");

                    throw new SpecFailureException("JDEXMLRequestDriver: Error reading xml cached:  [" + reportFile + "] ", e);

                }

                logger.debug("MULESOFT - UBEDefinition:  - getDefinition() Specs from Cache readed");

            } else {

                logger.debug("JDEXMLRequestDriver: getting specs: [" + reportFile + "] ");

                // --------------------------------------------
                // Get User Session
                // --------------------------------------------
                //
                SessionValues sv = new SessionValues(iSessionID, this.sessionIdleMinutes);

                // --------------------------------------------
                // Create XML Request
                // --------------------------------------------
                //
                String xmlDoc = new String();
                xmlDoc += "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>";
                xmlDoc += "<jdeRequest type=\"ube\" user=\"" + sv.svUserSession.getUserName() + "\" pwd=\"" + sv.svPassword + "\" role=\"" + sv.svUserSession.getUserRole() + "\" environment=\"" + sv.svUserSession.getUserEnvironment() + "\" session=\"" + sv.svSession + "\" sessionidle=\"" + sv.svSessionIdle + "\">";
                xmlDoc += "<ACTION TEMPLATE_TYPE=\"LAUNCH_JOB\" TYPE=\"CREATE_XML\">";
                xmlDoc += "<REPORT_NAME VALUE=\"" + ubeName + "\"/>";
                xmlDoc += "<REPORT_VERSION VALUE=\"" + ubeVersion + "\"/>";
                xmlDoc += "<JARGON_SYSTEM_CODE VALUE='1'/>";
                xmlDoc += "<COMMENTS VALUE='1'/>";
                xmlDoc += "<DATA_TYPING VALUE='1'/>";
                xmlDoc += "<BUSINESS_VIEW VALUE='0'/>";
                xmlDoc += "<PRINTER_INFORMATION VALUE='0'/>";
                xmlDoc += "<POPULATED VALUE='1'/>";
                xmlDoc += "</ACTION>";
                xmlDoc += "</jdeRequest>";

                logger.debug("JDEXMLRequestDriver request: " + xmlDoc);

                // --------------------------------------------
                // Execute Request
                // --------------------------------------------
                //
                XMLRequest xmlRequest = new XMLRequest(sv.svUserSession.getHost(), sv.svUserSession.getPort(), xmlDoc);

                String szDocRequestResult = xmlRequest.execute(TIMEOUT);

                logger.debug("JDEXMLRequestDriver response: " + szDocRequestResult);

                // --------------------------------------------
                // Save Last Request Time
                // --------------------------------------------
                //
                lastRequest = Instant.now();

                // --------------------------------------------
                // Convert Response to Document
                // --------------------------------------------
                //
                szDocRequestResult = cleanNonValidXMLCharacters(szDocRequestResult);

                dbFactory = DocumentBuilderFactory.newInstance();
                dBuilder = dbFactory.newDocumentBuilder();

                InputSource is = new InputSource(new StringReader(szDocRequestResult));

                docRequestResult = dBuilder.parse(is);

                if (docRequestResult != null) {
                    docRequestResult.getDocumentElement().normalize();
                } else {
                    logger.debug("JDEXMLRequestDriver: Error converting XML response:  [" + reportFile + "]");

                    throw new SpecFailureException("JDEXMLRequestDriver: Error converting XML response:  [" + reportFile + "] ");

                }

                // --------------------------------------------
                // Check Error
                // --------------------------------------------
                /* 
                
                <?xml version='1.0' encoding='UTF-8' ?>
                <jdeResponse environment='JPS920' role='*ALL' type='ube' user='JDE'>
                        <ACTION TYPE='ERROR_MESSAGE'>
                                <ERROR VALUE='XJDE0001 Version is not available forR004251Report'/>
                        </ACTION>
                </jdeResponse>
                
                 */
                nodeDoc = docRequestResult.getElementsByTagName("ERROR").item(0);

                if (nodeDoc != null) {
                    attr = nodeDoc.getAttributes();

                    if (attr != null && attr.getNamedItem("VALUE") != null) {

                        nodeAttr = attr.getNamedItem("VALUE");

                        String errorReturnValue = nodeAttr.getNodeValue();

                        logger.debug("JDEXMLRequestDriver: Error getting UBE specs:  [" + errorReturnValue + "]");

                        throw new SpecFailureException("JDEXMLRequestDriver: Error getting UBE specs:  [" + errorReturnValue + "] ");

                    }

                }

                nodeDoc = docRequestResult.getElementsByTagName("returnCode").item(0);

                if (nodeDoc != null) {

                    attr = nodeDoc.getAttributes();

                    if (attr != null && attr.getNamedItem("code") != null) {

                        nodeAttr = attr.getNamedItem("code");

                        String errorReturnValue = "";

                        if (nodeAttr.getTextContent()
                                .compareTo("0") != 0) {

                            errorReturnValue = nodeAttr.getNodeValue();

                            logger.debug("JDEXMLRequestDriver: Error getting UBE specs:  [" + errorReturnValue + "]");

                            throw new SpecFailureException("JDEXMLRequestDriver: Error getting UBE specs:  [" + errorReturnValue + "] ");
                        }

                    }

                }

                // --------------------------------------------
                // Save Cache
                // --------------------------------------------
                //
                if (!cacheExists) {

                    TransformerFactory transformerFactory;
                    Transformer transformer;
                    DOMSource source;
                    StreamResult result;

                    logger.debug("JDEXMLRequestDriver: saving specs: [" + reportFile + "] ");

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
        
        return docRequestResult;
        
    }
    
    public HashMap<String,Object> submitReport(int iSessionID, String reportName, Map<String, Object> inputValues, String cacheFolder) throws JDESingleException {
        
        String ubeName = "";
        String ubeVersion = "";
        
        Document docRequestResult; 
        Node nodeDoc = null;
        Node nodeAttr = null;
        NamedNodeMap attr = null;
        
        DocumentBuilderFactory dbFactory;
        DocumentBuilder dBuilder;
        
        HashMap<String,Object> returnValue = new HashMap<String,Object>();
            
        try {
            
            // --------------------------------------------
            // Get User Session
            // --------------------------------------------
            
            SessionValues sv = new SessionValues(iSessionID,this.sessionIdleMinutes);
            
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
            // Get Report Definition
            // --------------------------------------------
             
            docRequestResult = getReportDefinition(iSessionID, cacheFolder, reportName, ubeName, ubeVersion);
            
            // --------------------------------------------
            // Prepare Request
            // --------------------------------------------
             
            // -----------------------------------------
            // Replace JOBQ
            // -----------------------------------------
            //
            
            if(inputValues.containsKey("JOBQUEUE") && !((String)inputValues.get("JOBQUEUE")).isEmpty())
            {
                
                nodeDoc = docRequestResult.getElementsByTagName("JOBQUEUE").item(0);

                if (nodeDoc != null) {

                    attr = nodeDoc.getAttributes();

                    if (attr != null) {

                        nodeAttr = attr.getNamedItem("VALUE");

                        if (nodeAttr != null) {

                            nodeAttr.setNodeValue((String) inputValues.get("JOBQUEUE"));

                        }

                    }

                }
            
            }
             
            // -----------------------------------------
            // Replace DS
            // -----------------------------------------
            //
             
            if(inputValues.containsKey("DATA_SELECTION") &&  !((String)inputValues.get("DATA_SELECTION")).isEmpty())
            {
                
                nodeDoc = docRequestResult.getElementsByTagName("DATA_SELECTION").item(0);

                if (nodeDoc != null) {

                    // Get and Remove Current Selection

                    NodeList list = nodeDoc.getChildNodes();

                    if (list != null && list.getLength() > 0) {

                        // Remove Current Selection

                        for (int i = 0; i < list.getLength(); i++) {

                                Node node = list.item(i);

                                nodeDoc.removeChild(node);

                            }

                        }

                }

                // Add new Data Selection 

                String sqlSentence = (String) inputValues.get("DATA_SELECTION");
                
                TransformSQSentenceLtoXMLSentece converter = new TransformSQSentenceLtoXMLSentece();
                
                String xmlSelection = converter.convertWhereSQLtoXML(sqlSentence);

                if(!xmlSelection.isEmpty())
                {
                     
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

                    factory.setNamespaceAware(true);

                    DocumentBuilder builder = factory.newDocumentBuilder();
                    
                    String sqlWhere = "<?xml version=\"1.0\" encoding=\"utf-8\"?><ROOT>" + xmlSelection + "</ROOT>";

                    Document doc2 = builder.parse(new ByteArrayInputStream(sqlWhere.getBytes(Charset.forName("UTF-8"))));

                    Node clauses = doc2.getElementsByTagName("ROOT")
                            .item(0);

                    if (clauses != null) {

                        // Get and Remove Current Selection
                        NodeList listClauses = clauses.getChildNodes();

                        if (listClauses != null && listClauses.getLength() > 0) {

                            for (int i = 0; i < listClauses.getLength(); i++) {

                                Node node = listClauses.item(i);

                                Node nodeSql = docRequestResult.importNode(node, true);

                                nodeDoc.appendChild(nodeSql);

                            }

                        }

                    } 

                }
            
            }
            
            // -----------------------------------------
            // Replace PO
            // -----------------------------------------
            //
              
            if(inputValues.containsKey("PROCESSING_OPTIONS") && inputValues.get("PROCESSING_OPTIONS") instanceof Map && !((Map)inputValues.get("PROCESSING_OPTIONS")).isEmpty())
            {
                 
                nodeDoc = docRequestResult.getElementsByTagName("AVAILABLE_MEMBERS").item(0);

                if (nodeDoc != null) {

                    if (nodeDoc.getParentNode().getNodeName().equals("PROCESSING_OPTIONS")) {

                        NodeList nodesMember = nodeDoc.getChildNodes();

                        if (nodesMember != null && nodesMember.getLength() > 0) {

                            for (int i = 0; i < nodesMember.getLength(); i++) {

                                attr = nodesMember.item(i).getAttributes();

                                String name = ""; 

                                if (attr != null && attr.getNamedItem("ID") != null) {

                                    name = attr.getNamedItem("ID").getNodeValue();

                                }

                                HashMap<String,Object> pos = (HashMap<String,Object>) inputValues.get("PROCESSING_OPTIONS");

                                if(pos.containsKey(name))
                                {

                                    if (attr != null && attr.getNamedItem("VALUE") != null) {

                                        attr.getNamedItem("VALUE").setNodeValue((String) pos.get(name));

                                    }

                                } 

                            }

                        }

                    }

                }
            
            }
            
            // -----------------------------------------
            // Replace RI
            // -----------------------------------------
            //
            
            if(inputValues.containsKey("REPORT_INTERCONNECT") && inputValues.get("REPORT_INTERCONNECT") instanceof Map &&  !((Map)inputValues.get("REPORT_INTERCONNECT")).isEmpty())
            {
                 
                nodeDoc = docRequestResult.getElementsByTagName("REPORT_INTERCONNECT").item(0); 

                if (nodeDoc != null) {

                    NodeList nodesMember = nodeDoc.getChildNodes();

                    if (nodesMember != null && nodesMember.getLength() > 0) {

                        for (int i = 0; i < nodesMember.getLength(); i++) {

                            attr = nodesMember.item(i).getAttributes();

                            String name = "";

                            if (attr != null && attr.getNamedItem("ID") != null) {

                                name = attr.getNamedItem("ID").getNodeValue();

                            }

                            HashMap<String,Object> ris = (HashMap<String,Object>) inputValues.get("REPORT_INTERCONNECT");

                            if(ris.containsKey(name))
                            {

                                if (attr != null && attr.getNamedItem("VALUE") != null) {

                                     attr.getNamedItem("VALUE").setNodeValue((String) ris.get(name));

                                } 

                            }

                        }

                    }

                } 
            
            }
            
            // ----------------------------------------
            // Submit UBE
            // ----------------------------------------
            //
            
            String request = parseDocRequest(docRequestResult);
            
            logger.debug("JDEXMLRequestDriver: Request: [" + request + "] " );
            
            // ----------------------------------------
            // Submit UBE
            // ----------------------------------------
            // 
            
            XMLRequest xmlRequest = new XMLRequest(sv.svUserSession.getHost(), sv.svUserSession.getPort(), request);

            
            String szDocRequestResult = xmlRequest.execute(TIMEOUT);

            
            logger.debug("JDEXMLRequestDriver response: " + szDocRequestResult);
            
            // --------------------------------------------
            // Save Last Request Time
            // --------------------------------------------
            
            lastRequest = Instant.now();
             
            
            // --------------------------------------------
            // Convert Response to Document
            // --------------------------------------------
            //
            
            szDocRequestResult = cleanNonValidXMLCharacters(szDocRequestResult);

            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();

            InputSource is = new InputSource(new StringReader(szDocRequestResult));

            docRequestResult = dBuilder.parse(is);

            if (docRequestResult != null) {
                    docRequestResult.getDocumentElement().normalize();
            } else {
                    
                logger.debug("JDEXMLRequestDriver: Error converting XML response:  [" + reportName + "]");

                throw new SpecFailureException("JDEXMLRequestDriver: Error converting XML response:  [" + reportName + "] ");

            }

            // --------------------------------------------
            // Check Error
            // --------------------------------------------
            /* 

            <?xml version='1.0' encoding='UTF-8' ?>
            <jdeResponse environment='JPS920' role='*ALL' type='ube' user='JDE'>
                    <ACTION TYPE='ERROR_MESSAGE'>
                            <ERROR VALUE='XJDE0001 Version is not available forR004251Report'/>
                    </ACTION>
            </jdeResponse>

             */
            nodeDoc = docRequestResult.getElementsByTagName("ERROR").item(0);

            if (nodeDoc != null) {
                attr = nodeDoc.getAttributes();

                if (attr != null && attr.getNamedItem("VALUE") != null) {

                    nodeAttr = attr.getNamedItem("VALUE");

                    String errorReturnValue = nodeAttr.getNodeValue();

                    logger.debug("JDEXMLRequestDriver: Error getting UBE specs:  [" + errorReturnValue + "]");

                    throw new SpecFailureException("JDEXMLRequestDriver: Error getting UBE specs:  [" + errorReturnValue + "] ");

                }

            }

            nodeDoc = docRequestResult.getElementsByTagName("returnCode").item(0);

            if (nodeDoc != null) {

                attr = nodeDoc.getAttributes();

                if (attr != null && attr.getNamedItem("code") != null) {

                    nodeAttr = attr.getNamedItem("code");

                    String errorReturnValue = "";

                    if (nodeAttr.getTextContent()
                            .compareTo("0") != 0) {

                        errorReturnValue = nodeAttr.getNodeValue();

                        logger.debug("JDEXMLRequestDriver: Error getting UBE specs:  [" + errorReturnValue + "]");

                        throw new SpecFailureException("JDEXMLRequestDriver: Error getting UBE specs:  [" + errorReturnValue + "] ");
                    }

                }

            }
            
            // JOBQUEUE
            
            nodeDoc = docRequestResult.getElementsByTagName("JOBID").item(0);
            
            String jobId = "";

            if (nodeDoc != null) {

                attr = nodeDoc.getAttributes();

                if (attr != null && attr.getNamedItem("VALUE") != null) {

                    nodeAttr = attr.getNamedItem("VALUE");

                    jobId = nodeAttr.getNodeValue();

                }

            }

            returnValue.put("JOBID", jobId);
            
             
        } catch (Exception e) {

            logger.error(
                    "transaction() error: "
                    + e.getMessage(), e);

            throw new JDESingleException(e.getMessage(), e);

        }

        return returnValue;
        
    }
    
    private String parseDocRequest(Document docRequestResult) throws TransformerConfigurationException, TransformerException {
        
        // ===================================================
        // Initialize Output Var
        // ===================================================
        
        String returnValue = "";
        
        // ===================================================
        // Initialize Working Variable
        // ===================================================
        
        TransformerFactory transformerFactory;
        Transformer transformer;
        DOMSource domSource;
        StringWriter stringWriter;
        StreamResult streamResult;
        
        // ===================================================
        // Convert Doc Request
        // ===================================================
        
        transformerFactory = TransformerFactory.newInstance();
        transformer = transformerFactory.newTransformer();
        domSource = new DOMSource(docRequestResult);
        stringWriter = new StringWriter();
        streamResult = new StreamResult(stringWriter);
        transformer.transform(domSource, streamResult);
        returnValue = stringWriter.getBuffer().toString();
            
        return returnValue;
        
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
