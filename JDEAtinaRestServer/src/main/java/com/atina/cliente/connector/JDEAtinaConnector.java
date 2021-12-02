/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.cliente.connector;

 
import com.atina.cliente.exception.ConnectionException;
import com.atina.cliente.exception.InternalConnectorException;
import com.jde.jdeserverwp.servicios.TipoDelParametroInput;
import com.jde.jdeserverwp.servicios.TipoDelParametroOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jgodi
 */
public class JDEAtinaConnector {
    
    private static final Logger logger = LoggerFactory.getLogger(JDEAtinaConnector.class);
     
    JDEAtinaConfigDriver config;
    
    public Object authenticate(String entityType, Map<String, Object> entityData)
            throws ConnectionException {

        Object entity = authenticateUser(entityType, entityData);

        logger.info("JDE Atina - Authenticate: [" + entityType + "] Executed");

        return entity;

    }
    
    public Object invokeWS(String entityType, Map<String, Object> entityData)
            throws InternalConnectorException, ConnectionException {

        logger.info("JDE Atina - Invoke WS: [" + entityType + "]");

        Object entity = invokeWebService(entityType, entityData);

        logger.info("JDE Atina - WS: [" + entityType + "] Executed");

        return entity;

    }
     
    public Object processAtinaToken(String entityType, Map<String, Object> entityData)
            throws InternalConnectorException, ConnectionException {

        Object entity = processToken(entityType, entityData);

        logger.info("JDE Atina - Process Token: [" + entityType + "] Executed");

        return entity;

    }
    
    public Object metadata(String entityType, Map<String, Object> entityData)
            throws InternalConnectorException, ConnectionException {

        Object entity = processMetadata(entityType, entityData);

        logger.info("JDE Atina - Metadata Operations: [" + entityType + "] Executed");

        return entity;

    }
    
    public Object monitor(String entityType, Map<String, Object> entityData)
            throws InternalConnectorException, ConnectionException {

        Object entity = processMonitor(entityType, entityData);

        logger.info("JDE Atina - Metadata Operations: [" + entityType + "] Executed");

        return entity;

    }
    
    
    
    public JDEAtinaConfigDriver getConfig() {
        return config;
    }

    public void setConfig(JDEAtinaConfigDriver config) {
        this.config = config;
    }
    
    private Object authenticateUser(String entityType, Map<String, Object> entityData) throws ConnectionException {

        Map<String, Object> returnValue = new HashMap<String, Object>();

        try {

            JDEAtinaConfiguracion currentConfiguration = config.getConfiguracion();

            logger.info("JDE Atina - Authenticate User with Option: [" + entityType + "]");
            logger.info("           Current Configuration: [" + currentConfiguration.toString());
            logger.info("           Transaction ID: [" + entityData.get("Transaction ID"));

            if (entityType.equals("FromUserData") || entityType.equals("FromTokenData"))
            {

                if (entityData.containsKey("JDE Token") && !(((String) entityData.get("JDE Token")).isEmpty()))
                {
                    logger.info("           Token received: [" + entityData.get("JDE Token") + "]");

                    JDEAtinaConfiguracion currentConfigurationToken = new JDEAtinaConfiguracion();

                    currentConfigurationToken.setJdeUser("");
                    currentConfigurationToken.setJdePassword("");
                    currentConfigurationToken.setJdeEnvironment("");
                    currentConfigurationToken.setJdeRole("");
                    currentConfigurationToken.setSessionID(config.getConfiguracion()
                            .getSessionID());
                    currentConfigurationToken.setWsConnection(true);
                    currentConfigurationToken.setToken((String) entityData.get("JDE Token"));

                    this.getConfig()
                            .getService()
                            .login(config.getStub(), currentConfigurationToken, (Long) entityData.get("Transaction ID"));

                    returnValue.put("token", currentConfigurationToken.getToken());
                    returnValue.put("sessionId", currentConfigurationToken.getSessionID());
                    returnValue.put("userAddressBookNo", currentConfigurationToken.getAddressBookNumber());
                    returnValue.put("Transaction ID", currentConfigurationToken.getTransactionID()); 

                } else if (entityData.containsKey("JDE User") &&
                        entityData.containsKey("JDE Password") &&
                        entityData.containsKey("JDE Environment") &&
                        entityData.containsKey("JDE Role") &&
                        entityData.containsKey("Session Id"))
                {
                    JDEAtinaConfiguracion currentConfigurationToken = new JDEAtinaConfiguracion();

                    currentConfigurationToken.setJdeUser((String) entityData.get("JDE User"));
                    currentConfigurationToken.setJdePassword((String) entityData.get("JDE Password"));
                    currentConfigurationToken.setJdeEnvironment((String) entityData.get("JDE Environment"));
                    currentConfigurationToken.setJdeRole((String) entityData.get("JDE Role"));
                    currentConfigurationToken.setSessionID(config.getConfiguracion()
                            .getSessionID());
                    currentConfigurationToken.setToken("");
                    currentConfigurationToken.setWsConnection(true);

                    logger.info("           Information received: [" + currentConfigurationToken.toString() + "]");

                    this.getConfig()
                            .getService()
                            .login(config.getStub(), currentConfigurationToken, (Long) entityData.get("Transaction ID"));

                    returnValue.put("token", currentConfigurationToken.getToken());
                    returnValue.put("sessionId", currentConfigurationToken.getSessionID());
                    returnValue.put("userAddressBookNo", currentConfigurationToken.getAddressBookNumber());
                    returnValue.put("Transaction ID", currentConfigurationToken.getTransactionID()); 

                } else
                {

                    JDEAtinaConfiguracion currentConfigurationToken = new JDEAtinaConfiguracion();

                    currentConfigurationToken.setJdeUser((String) entityData.get("JDE User"));
                    currentConfigurationToken.setJdePassword((String) entityData.get("JDE Password"));
                    currentConfigurationToken.setJdeEnvironment((String) entityData.get("JDE Environment"));
                    currentConfigurationToken.setJdeRole((String) entityData.get("JDE Role"));
                    currentConfigurationToken.setSessionID(0L);
                    currentConfigurationToken.setToken("");
                    currentConfigurationToken.setWsConnection(true);

                    logger.info("           Information received: [" + currentConfigurationToken.toString() + "]");

                    this.getConfig()
                            .getService()
                            .login(config.getStub(), currentConfigurationToken, (Long) entityData.get("Transaction ID"));

                    returnValue.put("token", config.getConfiguracion()
                            .getToken());
                    returnValue.put("Transaction ID", currentConfigurationToken.getTransactionID()); 
                }

            }

            if (entityType.equals("LogoutTokenData"))
            {

                if (entityData.containsKey("JDE Token") && !(((String) entityData.get("JDE Token")).isEmpty()))
                {
                    JDEAtinaConfiguracion currentConfigurationToken = new JDEAtinaConfiguracion();

                    currentConfigurationToken.setJdeUser("");
                    currentConfigurationToken.setJdePassword("");
                    currentConfigurationToken.setJdeEnvironment("");
                    currentConfigurationToken.setJdeRole("");
                    currentConfigurationToken.setSessionID(config.getConfiguracion()
                            .getSessionID());
                    currentConfigurationToken.setWsConnection(true);
                    currentConfigurationToken.setToken((String) entityData.get("JDE Token"));

                    this.getConfig()
                            .getService()
                            .logout(config.getStub(), currentConfigurationToken, (Long) entityData.get("Transaction ID"));

                    returnValue.put("token", (String) currentConfigurationToken.getToken());
                    returnValue.put("sessionId", currentConfigurationToken.getSessionID()); 
                    returnValue.put("Transaction ID", currentConfigurationToken.getTransactionID()); 

                }

            }
            
            if (entityType.equals("IsConnected"))
            {

                if (entityData.containsKey("JDE Token") && !(((String) entityData.get("JDE Token")).isEmpty()))
                {
                    JDEAtinaConfiguracion currentConfigurationToken = new JDEAtinaConfiguracion();

                    currentConfigurationToken.setJdeUser("");
                    currentConfigurationToken.setJdePassword("");
                    currentConfigurationToken.setJdeEnvironment("");
                    currentConfigurationToken.setJdeRole("");
                    currentConfigurationToken.setSessionID(config.getConfiguracion()
                            .getSessionID());
                    currentConfigurationToken.setWsConnection(true);
                    currentConfigurationToken.setToken((String) entityData.get("JDE Token"));

                    Boolean connected = this.getConfig()
                            .getService()
                            .isConnected(config.getStub(), currentConfigurationToken, (Long) entityData.get("Transaction ID"));

                    returnValue.put("token", currentConfigurationToken.getToken());
                    returnValue.put("sessionId", currentConfigurationToken.getSessionID()); 
                    returnValue.put("Transaction ID", currentConfigurationToken.getTransactionID()); 
                    returnValue.put("Connected", connected); 

                }

            }

        } catch (Exception e) {

            logger.error("ERROR JDE ATILA authenticateUser:  ..." + e.getMessage(), e);

            throw new ConnectionException("JDE ATILA Error Connection: " + e.getMessage(), e);
        }

        return returnValue;
    }
    
     private Object invokeWebService(String entityType, Map<String, Object> entityData) {

        Object returnValue = this.getConfig()
                .getService()
                .ejecutarServicio(config.getStub(), config.getConfiguracion(), entityType, entityData);

        return returnValue;
    }
     
      
      
      private Object processMetadata(String entityType, Map<String, Object> entityData) throws ConnectionException {

        Map<String, Object> returnValue = new HashMap<String, Object>();

        try {

            JDEAtinaConfiguracion currentConfiguration = config.getConfiguracion();

            logger.info("JDE Atina - Process Metadata with Option: [" + entityType + "]");
            logger.info("           Current Configuration: [" + currentConfiguration.toString());
            logger.info("           Transaction ID: [" + entityData.get("Transaction ID"));

            if (entityType.equals("Operations"))
            {

                JDEAtinaConfiguracion currentConfigurationToken = new JDEAtinaConfiguracion();

                    currentConfigurationToken.setJdeUser("");
                    currentConfigurationToken.setJdePassword("");
                    currentConfigurationToken.setJdeEnvironment("");
                    currentConfigurationToken.setJdeRole("");
                    currentConfigurationToken.setSessionID(0L);
                    currentConfigurationToken.setToken((String) entityData.get("JDE Token"));
                    currentConfigurationToken.setWsConnection(true);

                    logger.info("           Information received: [" + currentConfigurationToken.toString() + "]");

                    Map<String, String> operations = this.getConfig()
                        .getService()
                        .getMetadataOperations(config.getStub(), currentConfigurationToken, (Long) entityData.get("Transaction ID"),(String) entityData.get("Filter"));

                    returnValue.put("token", currentConfigurationToken.getToken());
                    returnValue.put("sessionId", currentConfigurationToken.getSessionID()); 
                    returnValue.put("Transaction ID", currentConfigurationToken.getTransactionID()); 
                    returnValue.put("Operations", operations); 
                     
            }
            
            if (entityType.equals("Input"))
            {

                JDEAtinaConfiguracion currentConfigurationToken = new JDEAtinaConfiguracion();

                    currentConfigurationToken.setJdeUser("");
                    currentConfigurationToken.setJdePassword("");
                    currentConfigurationToken.setJdeEnvironment("");
                    currentConfigurationToken.setJdeRole("");
                    currentConfigurationToken.setSessionID(0L);
                    currentConfigurationToken.setToken((String) entityData.get("JDE Token"));
                    currentConfigurationToken.setWsConnection(true);

                    logger.info("           Information received: [" + currentConfigurationToken.toString() + "]");

                    List<TipoDelParametroInput> parameters = this.getConfig()
                        .getService()
                        .getInputMetadataForOperation(config.getStub(), currentConfigurationToken,(String) entityData.get("Operation"), (Long) entityData.get("Transaction ID"));
 
                    ArrayList<String> output = new ArrayList<String>();
                    
                    for (TipoDelParametroInput parameter : parameters) {

                        int level = 0;

                        saveParameterInput(parameter, level, output);

                    }
                    
                    returnValue.put("token", currentConfigurationToken.getToken());
                    returnValue.put("sessionId", currentConfigurationToken.getSessionID()); 
                    returnValue.put("Transaction ID", currentConfigurationToken.getTransactionID()); 
                    returnValue.put("Parameters", output); 
                     
            }
            
            if (entityType.equals("Output"))
            {

                JDEAtinaConfiguracion currentConfigurationToken = new JDEAtinaConfiguracion();

                    currentConfigurationToken.setJdeUser("");
                    currentConfigurationToken.setJdePassword("");
                    currentConfigurationToken.setJdeEnvironment("");
                    currentConfigurationToken.setJdeRole("");
                    currentConfigurationToken.setSessionID(0L);
                    currentConfigurationToken.setToken((String) entityData.get("JDE Token"));
                    currentConfigurationToken.setWsConnection(true);

                    logger.info("           Information received: [" + currentConfigurationToken.toString() + "]");

                    List<TipoDelParametroOutput> parameters = this.getConfig()
                        .getService()
                        .getOutputMetadataForOperation(config.getStub(), currentConfigurationToken,(String) entityData.get("Operation"), (Long) entityData.get("Transaction ID"));
  
                    ArrayList<String> output = new ArrayList<String>();
                    
                    for (TipoDelParametroOutput parameter : parameters) {

                        int level = 0;

                        saveParameterOutput(parameter, level, output);

                    }
                    
                    returnValue.put("token", currentConfigurationToken.getToken());
                    returnValue.put("sessionId", currentConfigurationToken.getSessionID()); 
                    returnValue.put("Transaction ID", currentConfigurationToken.getTransactionID()); 
                    returnValue.put("Parameters", output); 
                     
            }
            
            if (entityType.equals("Payload"))
            {

                JDEAtinaConfiguracion currentConfigurationToken = new JDEAtinaConfiguracion();

                    currentConfigurationToken.setJdeUser("");
                    currentConfigurationToken.setJdePassword("");
                    currentConfigurationToken.setJdeEnvironment("");
                    currentConfigurationToken.setJdeRole("");
                    currentConfigurationToken.setSessionID(0L);
                    currentConfigurationToken.setToken((String) entityData.get("JDE Token"));
                    currentConfigurationToken.setWsConnection(true);

                    logger.info("           Information received: [" + currentConfigurationToken.toString() + "]");

                    Object payload = this.getConfig()
                        .getService() 
                        .getJsonFromOperations(config.getStub(), currentConfigurationToken,(String) entityData.get("Operation"), (Long) entityData.get("Transaction ID"));
   
                    returnValue.put("token", currentConfigurationToken.getToken());
                    returnValue.put("sessionId", currentConfigurationToken.getSessionID()); 
                    returnValue.put("Transaction ID", currentConfigurationToken.getTransactionID()); 
                    returnValue.put("Parameters", payload); 
                     
            }
  
        } catch (Exception e) {

            logger.error("ERROR JDE ATILA Process Metadata: " + e.getMessage(), e);

            throw new ConnectionException("JDE ATILA Error Metadata: " + e.getMessage(), e);
        }

        return returnValue;
    }

      private Object processToken(String entityType, Map<String, Object> entityData) throws ConnectionException {

        Map<String, Object> returnValue = new HashMap<String, Object>();

        try {

            JDEAtinaConfiguracion currentConfiguration = config.getConfiguracion();

            logger.info("JDE Atina - Process Token with Option: [" + entityType + "]");
            logger.info("           Current Configuration: [" + currentConfiguration.toString());
            logger.info("           Transaction ID: [" + entityData.get("Transaction ID"));

            if (entityType.equals("CreateToken"))
            {

                JDEAtinaConfiguracion currentConfigurationToken = new JDEAtinaConfiguracion();

                    currentConfigurationToken.setJdeUser((String) entityData.get("JDE User"));
                    currentConfigurationToken.setJdePassword((String) entityData.get("JDE Password"));
                    currentConfigurationToken.setJdeEnvironment((String) entityData.get("JDE Environment"));
                    currentConfigurationToken.setJdeRole((String) entityData.get("JDE Role"));
                    currentConfigurationToken.setTokenExpirationMilliseconds((Long) entityData.get("JDE Token Expiration"));
                    currentConfigurationToken.setSessionID(config.getConfiguracion()
                            .getSessionID());
                    currentConfigurationToken.setToken("");
                    currentConfigurationToken.setWsConnection(true);

                    logger.info("           Information received: [" + currentConfigurationToken.toString() + "]");

                    this.getConfig()
                            .getService()
                            .processToken("create", config.getStub(), currentConfigurationToken, (Long) entityData.get("Transaction ID"));

                    returnValue.put("token", currentConfigurationToken.getToken());
                    returnValue.put("sessionId", currentConfigurationToken.getSessionID()); 
                    returnValue.put("Transaction ID", currentConfigurationToken.getTransactionID()); 

            }

            if (entityType.equals("ParseToken"))
            {

                 JDEAtinaConfiguracion currentConfigurationToken = new JDEAtinaConfiguracion();

                    currentConfigurationToken.setJdeUser("");
                    currentConfigurationToken.setJdePassword("");
                    currentConfigurationToken.setJdeEnvironment("");
                    currentConfigurationToken.setJdeRole("");
                    currentConfigurationToken.setSessionID(config.getConfiguracion()
                            .getSessionID());
                    currentConfigurationToken.setToken((String) entityData.get("JDE Token"));
                    currentConfigurationToken.setWsConnection(true);

                    logger.info("           Information received: [" + currentConfigurationToken.toString() + "]");

                    this.getConfig()
                            .getService()
                            .processToken("parse", config.getStub(), currentConfigurationToken, (Long) entityData.get("Transaction ID"));

                    returnValue.put("JDE User", currentConfigurationToken.getJdeUser());
                    returnValue.put("JDE Environment", currentConfigurationToken.getJdeEnvironment());
                    returnValue.put("JDE Role", currentConfigurationToken.getJdeRole()); 
                    returnValue.put("token", currentConfigurationToken.getToken());
                    returnValue.put("sessionId", currentConfigurationToken.getSessionID()); 
                    returnValue.put("Transaction ID", currentConfigurationToken.getTransactionID()); 
                    returnValue.put("Token Expiration", currentConfigurationToken.getTokenExpiration()); 
 
            }

        } catch (Exception e) {

            logger.error("ERROR JDE ATILA Process Token: " + e.getMessage(), e);

            throw new ConnectionException("JDE ATILA Error Connection: " + e.getMessage(), e);
        }

        return returnValue;
    }
      
      private Object processMonitor(String entityType, Map<String, Object> entityData) throws ConnectionException {

        Map<String, Object> returnValue = new HashMap<String, Object>();

        try {

            JDEAtinaConfiguracion currentConfiguration = config.getConfiguracion();

            logger.info("JDE Atina - Process Monitor with Option: [" + entityType + "]");
            logger.info("            Current Configuration: [" + currentConfiguration.toString());
            logger.info("            Transaction ID: [" + entityData.get("Transaction ID"));

            if (entityType.equals("Connections"))
            {

                JDEAtinaConfiguracion currentConfigurationToken = new JDEAtinaConfiguracion();

                    currentConfigurationToken.setJdeUser("");
                    currentConfigurationToken.setJdePassword("");
                    currentConfigurationToken.setJdeEnvironment("");
                    currentConfigurationToken.setJdeRole("");
                    currentConfigurationToken.setSessionID(0L);
                    currentConfigurationToken.setToken("");
                    currentConfigurationToken.setWsConnection(true);

                    logger.info("           Information received: [" + currentConfigurationToken.toString() + "]");

                    List<Map<String,Object>> connections = this.getConfig()
                        .getService()
                        .getConnections(config.getStub(), currentConfigurationToken, (Long) entityData.get("Transaction ID"));

                    returnValue.put("Transaction ID", currentConfigurationToken.getTransactionID()); 
                    returnValue.put("Connections", connections); 
                     
            }
            
            if (entityType.equals("Logs"))
            {
                JDEAtinaConfiguracion currentConfigurationToken = new JDEAtinaConfiguracion();

                currentConfigurationToken.setJdeUser("");
                currentConfigurationToken.setJdePassword("");
                currentConfigurationToken.setJdeEnvironment("");
                currentConfigurationToken.setJdeRole("");
                currentConfigurationToken.setSessionID(0L);
                currentConfigurationToken.setToken("");
                currentConfigurationToken.setWsConnection(true);

                logger.info("           Information received: [" + currentConfigurationToken.toString() + "]");

                String logs = this.getConfig()
                        .getService()
                        .getLogs(config.getStub(), currentConfigurationToken, (Long) entityData.get("Transaction ID"));

                returnValue.put("Transaction ID", currentConfigurationToken.getTransactionID());
                returnValue.put("Logs", logs);
                    
            }
             
  
        } catch (Exception e) {

            logger.error("ERROR JDE ATILA Process Metadata: " + e.getMessage(), e);

            throw new ConnectionException("JDE ATILA Error Metadata: " + e.getMessage(), e);
        }

        return returnValue;
    }
      
    private static void saveParameterInput(TipoDelParametroInput parameter, int level, ArrayList<String> output) {

         
        String space = StringUtils.repeat(".", level * 1);
        
        level++;

        output.add(space + parameter.getNombreDelParametro() + (parameter.getRepeatedParameter()?": List":":") + " <" + parameter.getTipoDelParametroJava() + ">");

        for (TipoDelParametroInput input : parameter.getSubParametroList()) {
            if (!input.getNombreDelParametro().isEmpty()) {
                saveParameterInput(input, level, output);
            }

        }
        
    }
    
    private static void saveParameterOutput(TipoDelParametroOutput parameter, int level, ArrayList<String> output) {

         
        String space = StringUtils.repeat(".", level * 1);
        
        level++;

        output.add(space + parameter.getNombreDelParametro() + (parameter.getRepeatedParameter()?": List":":") + " <" + parameter.getTipoDelParametroJava() + ">");

        for (TipoDelParametroOutput input : parameter.getSubParametroList()) {
            if (!input.getNombreDelParametro().isEmpty()) {
                saveParameterOutput(input, level, output);
            }

        }
        
    }
      
    
}
