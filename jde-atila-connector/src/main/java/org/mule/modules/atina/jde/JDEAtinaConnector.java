package org.mule.modules.atina.jde;

import java.util.HashMap;
import java.util.Map;

import org.mule.api.ConnectionException;
import org.mule.api.ConnectionExceptionCode;
import org.mule.api.annotations.Config;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.param.MetaDataKeyParam;
import org.mule.api.annotations.param.MetaDataKeyParamAffectsType;
import org.mule.api.annotations.MetaDataScope;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.param.Default;
import org.mule.modules.atina.jde.config.ConnectorConfig;
import org.mule.modules.atina.jde.datasense.AuthenticateDataSenseResolver;
import org.mule.modules.atina.jde.datasense.GetJSONShemaDataSenseResolver;
import org.mule.modules.atina.jde.datasense.WSDataSenseResolver;
import org.mule.modules.atina.jde.exceptions.InternalConnectorException;
import org.mule.modules.atina.jde.models.JDEAtilaConfiguracion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Connector(name = "jdeatina", friendlyName = "JDE Atina")
public class JDEAtinaConnector {

    private static final Logger logger = LogManager.getLogger(JDEAtinaConnector.class);

    @Config
    ConnectorConfig config;

    /**
     * DataSense processor
     * 
     * @param key
     *            Key to be used to populate the entity
     * @param entity
     *            Map that represents the entity
     * @return Some string
     * @throws ConnectionException
     * @throws InternalConnectorException
     */

    @Processor(friendlyName = "Authenticate")
    @MetaDataScope(AuthenticateDataSenseResolver.class)
    public Object authenticate(@MetaDataKeyParam(affects = MetaDataKeyParamAffectsType.BOTH) String entityType, @Default("#[payload]") Map<String, Object> entityData)
            throws InternalConnectorException, ConnectionException {

        Object entity = authenticateUser(entityType, entityData);

        logger.info("JDE Atina - WS: [" + entityType + "] Executed");

        return entity;

    }

    @Processor(friendlyName = "Invoke WS")
    @MetaDataScope(WSDataSenseResolver.class)
    public Object invokeWS(@MetaDataKeyParam(affects = MetaDataKeyParamAffectsType.BOTH) String entityType, @Default("#[payload]") Map<String, Object> entityData)
            throws InternalConnectorException, ConnectionException {

        logger.info("JDE Atina - Invoke WS: [" + entityType + "]");

        Object entity = invokeWebService(entityType, entityData);

        logger.info("JDE Atina - WS: [" + entityType + "] Executed");

        return entity;
    }

    @Processor(friendlyName = "Get JSON Schema")
    @MetaDataScope(GetJSONShemaDataSenseResolver.class)
    public Object getJSONSchema(@MetaDataKeyParam(affects = MetaDataKeyParamAffectsType.BOTH) String entityType, @Default("#[payload]") Map<String, Object> entityData)
            throws InternalConnectorException, ConnectionException {

        logger.info("JDE Atina - GetJSONShemaDataSenseResolver WS: [" + entityType + "]");

        Object entity = invokeGetSchema(entityType, entityData);

        logger.info("JDE Atina - GetJSONShemaDataSenseResolver: [" + entityType + "] Executed");

        return entity;

    }

    public ConnectorConfig getConfig() {
        return config;
    }

    public void setConfig(ConnectorConfig config) {
        this.config = config;
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

    private Object authenticateUser(String entityType, Map<String, Object> entityData) throws ConnectionException {

        Map<String, Object> returnValue = new HashMap<String, Object>();

        try {

            JDEAtilaConfiguracion currentConfiguration = config.getConfiguracion();

            logger.info("JDE Atina - Authenticate User with Option: [" + entityType + "]");
            logger.info("           Current Configuration: [" + currentConfiguration.toString());
            logger.info("           Transaction ID: [" + entityData.get("Transaction ID"));

            if (entityType.equals("FromUserData") || entityType.equals("FromTokenData"))
            {

                if (entityData.containsKey("JDE Token") && !(((String) entityData.get("JDE Token")).isEmpty()))
                {
                    logger.info("           Token received: [" + entityData.get("JDE Token") + "]");

                    JDEAtilaConfiguracion currentConfigurationToken = new JDEAtilaConfiguracion();

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
                    returnValue.put("userAddressBookNo", currentConfigurationToken.getAddressBookNumber());

                } else if (entityData.containsKey("JDE User") &&
                        entityData.containsKey("JDE Password") &&
                        entityData.containsKey("JDE Environment") &&
                        entityData.containsKey("JDE Role") &&
                        entityData.containsKey("Session Id"))
                {
                    JDEAtilaConfiguracion currentConfigurationToken = new JDEAtilaConfiguracion();

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
                    returnValue.put("userAddressBookNo", currentConfigurationToken.getAddressBookNumber());

                } else
                {

                    JDEAtilaConfiguracion currentConfigurationToken = new JDEAtilaConfiguracion();

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
                    JDEAtilaConfiguracion currentConfigurationToken = new JDEAtilaConfiguracion();

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

            throw new ConnectionException(ConnectionExceptionCode.CANNOT_REACH, "JDE ATILA Error Connection: ", e.getMessage(), e);
        }

        return returnValue;
    }

}