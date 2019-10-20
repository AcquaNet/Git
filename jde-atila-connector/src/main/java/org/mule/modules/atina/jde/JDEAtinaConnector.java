package org.mule.modules.atina.jde;

import java.util.Map;

import org.mule.api.ConnectionException;
import org.mule.api.annotations.Config;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.param.MetaDataKeyParam;
import org.mule.api.annotations.param.MetaDataKeyParamAffectsType;
import org.mule.api.annotations.MetaDataScope;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.param.Default;
import org.mule.modules.atina.jde.config.ConnectorConfig;
import org.mule.modules.atina.jde.datasense.WSDataSenseResolver;
import org.mule.modules.atina.jde.exceptions.InternalConnectorException;

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

    @Processor(friendlyName = "Invoke WS")
    @MetaDataScope(WSDataSenseResolver.class)
    public Object invokeWS(@MetaDataKeyParam(affects = MetaDataKeyParamAffectsType.BOTH) String entityType, @Default("#[payload]") Map<String, Object> entityData)
            throws InternalConnectorException, ConnectionException {

        logger.info("JDE Atina - Invoke WS: [" + entityType + "]");

        if (!config.isConnected())
        {
            logger.info("JDE Atina - Token Expired");

            this.getConfig()
                    .getService()
                    .login(this.getConfig()
                            .getStub(), this.getConfig()
                            .getConfiguracion());

        }

        Object entity = invokeWebService(entityType, entityData);

        logger.info("JDE Atina - WS: [" + entityType + "] Executed");

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

}