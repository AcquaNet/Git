/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.cliente.connector;

 
import com.atina.cliente.exception.ConnectionException;
import com.atina.cliente.exception.InternalConnectorException;
import java.util.HashMap;
import java.util.Map;
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
    
    public Object getJSONSchema(String entityType, Map<String, Object> entityData)
            throws InternalConnectorException, ConnectionException {

        logger.info("JDE Atina - Invoke WS: [" + entityType + "]");

        Object entity = invokeGetSchema(entityType, entityData);

        logger.info("JDE Atina - WS: [" + entityType + "] Executed");

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

                    returnValue.put("token", "");

                }

            }

        } catch (Exception e) {

            logger.error("ERROR JDE ATILA authenticateUser:  ..." + e.getMessage(), e);

            throw new ConnectionException("JDE ATILA Error Connection: ", e);
        }

        return returnValue;
    }
    
     private Object invokeWebService(String entityType, Map<String, Object> entityData) {

        Object returnValue = this.getConfig()
                .getService()
                .ejecutarServicio(config.getStub(), config.getConfiguracion(), entityType, entityData);

        return returnValue;
    }
     
      private Object invokeGetSchema(String entityType, Map<String, Object> entityData) {

        Object returnValue = this.getConfig()
                .getService()
                .getJsonFromOperations(config.getStub(), config.getConfiguracion(), entityType, entityData);

        return returnValue;
    }

    
}
