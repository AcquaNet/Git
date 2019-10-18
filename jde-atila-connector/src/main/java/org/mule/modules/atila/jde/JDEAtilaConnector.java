package org.mule.modules.atila.jde;

import java.util.HashMap;
import java.util.Map;

import org.mule.api.ConnectionException;
import org.mule.api.annotations.Config;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.param.MetaDataKeyParam;
import org.mule.api.annotations.param.MetaDataKeyParamAffectsType;
import org.mule.api.annotations.MetaDataScope;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.param.Default;
import org.mule.modules.atila.jde.config.ConnectorConfig;
import org.mule.modules.atila.jde.datasense.ServicioDataSenseResolver;
import org.mule.modules.atila.jde.exceptions.InternalConnectorException;

import io.grpc.StatusRuntimeException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Connector(name = "jdeatila", friendlyName = "JDE Atila")
public class JDEAtilaConnector {

    private static final Logger logger = LogManager.getLogger(JDEAtilaConnector.class);

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
    @MetaDataScope(ServicioDataSenseResolver.class)
    public Object servicio(@MetaDataKeyParam(affects = MetaDataKeyParamAffectsType.BOTH) String entityType, @Default("#[payload]") Map<String, Object> entityData) throws InternalConnectorException, ConnectionException {

        logger.info("DRAGONFISH - Ejecutar Servicio: [" + entityType + "]");

        if (!config.isConnected())
        {
            logger.info("DRAGONFISH - Token Expired");

            this.getConfig()
                    .getService()
                    .login(this.getConfig()
                            .getStub(), this.getConfig()
                            .getConfiguracion());

        }

        Object entity = ejecutarServicio(entityType, entityData);

        logger.info("DRAGONFISH - Servicio: [" + entityType + "] Ejecutado");

        return entity;
    }

    public ConnectorConfig getConfig() {
        return config;
    }

    public void setConfig(ConnectorConfig config) {
        this.config = config;
    }

    private Object ejecutarServicio(String entityType, Map<String, Object> entityData) {

        Object returnValue = this.getConfig()
                .getService()
                .ejecutarServicio(config.getStub(), config.getConfiguracion(), entityType, entityData);

        return returnValue;
    }

}