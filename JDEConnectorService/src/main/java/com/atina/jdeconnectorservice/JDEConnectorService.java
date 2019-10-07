/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.jdeconnectorservice;
 
import com.atina.jdeconnector.internal.model.JDEBsfnParameter;
import com.jdedwards.system.connector.dynamic.ServerFailureException; 
import com.atina.jdeconnector.internal.model.JDEBsfnParametersInputObject;
import com.atina.jdeconnector.internal.model.JDEBsfnParametersOutputObject;
import com.atina.jdeconnectorservice.exception.JDESingleConnectorException;
import com.atina.jdeconnectorservice.service.JDESingleConnection;
import com.atina.jdeconnectorservice.wsservice.JDESingleWSConnection;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author fjg
 */
public class JDEConnectorService {
    
    private static final Logger logger = LoggerFactory.getLogger(JDEConnectorService.class);
     
    public String testConnection(String user, String pwd, String environment, String role) throws ServerFailureException, InterruptedException, JDESingleConnectorException  {

        logger.info("Testign Connections...");
        
        JDESingleConnection jdeConnection = new JDESingleConnection(user,pwd,environment,role);
        
        jdeConnection.connect();
        
        // Call BSFBN
        Set<String> bsfnlist = jdeConnection.generateBSFNListFromCacheRepository();
        for(String bsfnName:bsfnlist)
        {
            logger.debug(bsfnName);
        }
        
        Set<JDEBsfnParameter> parameterList = jdeConnection.getBSFNParameter("AddressBookMasterMBF");
        
        for(JDEBsfnParameter parameter:parameterList)
        {
            logger.debug(parameter.toString());
        }
        
        // Calling BSFN
        JDEBsfnParametersInputObject inputObject = new JDEBsfnParametersInputObject();
        
        inputObject.setTransactionID(0);
        inputObject.getParameters().put("cActionCode", "I");
        inputObject.getParameters().put("mnAddressBookNumber", 28);
        
        JDEBsfnParametersOutputObject outputObject = jdeConnection.callJDEBsfn("AddressBookMasterMBF", inputObject);
        
        outputObject.logParameters();
        
        logger.debug("Output: " + outputObject.toString());
        
        
        Thread.sleep(30000);
        
        outputObject = jdeConnection.callJDEBsfn("AddressBookMasterMBF", inputObject);
         
        outputObject.logParameters();
         
        jdeConnection.disconnect();
         
        return "";
    }
    
    public String testWSConnection(String user, String pwd, String environment, String role) throws ServerFailureException, InterruptedException, JDESingleConnectorException  {
        
        logger.info("Testign Connections...");
        
        JDESingleWSConnection jdeConnection = new JDESingleWSConnection(user,pwd,environment,role);
        
        jdeConnection.connect();
        
        Set<String> operation = jdeConnection.generateWSListFromCacheRepository();
        
        HashMap<String, Object> input = jdeConnection.getWSInputParameter("oracle.e1.bssv.JP430000.ProcurementManager.getPurchaseOrdersForApprover");
        
        HashMap<String, Object> output = jdeConnection.getWSOutputParameter("oracle.e1.bssv.JP430000.ProcurementManager.getPurchaseOrdersForApprover");
        
        
        // ----------------------------------------------
        // Passing Parameters
        // ----------------------------------------------
        
        HashMap<String, Object> approver = new HashMap<String, Object>();
        approver.put("entityId", new Integer(533095));
        
        
        HashMap<String, Object> inputValue = new HashMap<String, Object>();
        
        inputValue.put("orderTypeCode", "OP");
        inputValue.put("businessUnitCode", "         30");
        inputValue.put("statusCodeNext", "230");
        inputValue.put("statusApproval", "2N");
        inputValue.put("approver", approver);
         
        HashMap<String, Object> outputValue = jdeConnection.callJDEWS("oracle.e1.bssv.JP430000.ProcurementManager.getPurchaseOrdersForApprover",inputValue);
        
        Thread.sleep(30000);
         
        jdeConnection.disconnect();
          
        return "";
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)   {

        // create the command line parser
        CommandLineParser parser = new DefaultParser();

        // create the Options
        Options options = new Options();

        options.addOption("u", "jde_user", true, "Enter JDE User");
        options.addOption("p", "jde_pwd", true, "Enter JDE Password");
        options.addOption("r", "jde_role", true, "Enter JDE Role");
        options.addOption("e", "jde_environment", true, "Enter JDE Environment"); 
        
        logger.info("JDEConnectorService Testing connections...");
        
         
        try {
            // parse the command line arguments
            CommandLine line = parser.parse(options, args);

            if (line != null) {

                // validate that block-size has been set
                if (line.hasOption("jde_user") && line.hasOption("jde_pwd") && line.hasOption("jde_role") && line.hasOption("jde_environment")) {
 
                    logger.info(" jde_user: " + line.getOptionValue("jde_user"));
                    logger.info(" jde_pwd: " + line.getOptionValue("jde_pwd"));
                    logger.info(" jde_role: " + line.getOptionValue("jde_role"));
                    logger.info(" jde_environment: " + line.getOptionValue("jde_environment")); 
                    
                    JDEConnectorService util = new JDEConnectorService();
                    
                    try {
                        
                        //util.testConnection( line.getOptionValue("jde_user"), line.getOptionValue("jde_pwd"), line.getOptionValue("jde_environment"), line.getOptionValue("jde_role"));
                        util.testWSConnection( line.getOptionValue("jde_user"), line.getOptionValue("jde_pwd"), line.getOptionValue("jde_environment"), line.getOptionValue("jde_role"));
                   
                    } catch (ServerFailureException ex) {
                        java.util.logging.Logger.getLogger(JDEConnectorService.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InterruptedException ex) {
                        java.util.logging.Logger.getLogger(JDEConnectorService.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    HelpFormatter formatter = new HelpFormatter();
                    formatter.printHelp("java -jar JDEConnectorJavaUtil.jar", options);
                }

            }

        } catch (ParseException exp) {
            logger.info("Unexpected exception:" + exp.getMessage());
        }
         
    }
    
}
