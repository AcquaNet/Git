package org.mule.modules.atila.jde;

import java.util.HashMap;
import java.util.Map;

import org.mule.api.annotations.Config;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.param.MetaDataKeyParam;
import org.mule.api.annotations.param.MetaDataKeyParamAffectsType;
import org.mule.api.annotations.MetaDataScope;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.param.Default;
import org.mule.modules.atila.jde.config.ConnectorConfig;
import org.mule.modules.atila.jde.datasense.ServicioDataSenseResolver;
import org.mule.modules.connector.exceptions.ExternalConnectorException;
import org.mule.modules.connector.exceptions.InternalConnectorException;

import io.grpc.StatusRuntimeException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Connector(name = "dragonfish", friendlyName = "Dragonfish")
public class DragonfishConnector {

    private static final Logger logger = LogManager.getLogger(DragonfishConnector.class);

    @Config
    ConnectorConfig config;

    @Processor
    public HashMap<String, String> autentificar() {

        HashMap<String, String> loginOper = new HashMap<String, String>();

        loginOper.put("ClavePrivadaConfCliente", config.getConfiguracion()
                .getClavePrivadaConfCliente());
        loginOper.put("CodigoConfCliente", config.getConfiguracion()
                .getCodigoConfCliente());
        loginOper.put("User", config.getConfiguracion()
                .getUser());
        loginOper.put("Password", config.getConfiguracion()
                .getPassword());
        loginOper.put("UrlBase", config.getConfiguracion()
                .getUrlBase());
        loginOper.put("Token", config.getConfiguracion()
                .getTocken());

        try {

            this.getConfig()
                    .getService()
                    .login(config.getStub(), config.getConfiguracion());

        } catch (ExternalConnectorException e) {

            logger.error("DRAGONFISH - Servicio: [" + e.getErrorMessage() + "] Ejecutado", e);

            loginOper.put("ErrorMessage", e.getErrorMessage());

        }

        return loginOper;

    }

    /**
     * DataSense processor
     * 
     * @param key
     *            Key to be used to populate the entity
     * @param entity
     *            Map that represents the entity
     * @return Some string
     */

    @Processor(friendlyName = "Ejecutar Servicio")
    @MetaDataScope(ServicioDataSenseResolver.class)
    public Object servicio(@MetaDataKeyParam(affects = MetaDataKeyParamAffectsType.BOTH) String entityType, @Default("#[payload]") Map<String, Object> entityData) {

        logger.info("DRAGONFISH - Ejecutar Servicio: [" + entityType + "]");

        if (!config.isConnected())
        {
            logger.info("DRAGONFISH - Token Expired");

            this.getConfig()
                    .getService()
                    .login(this.getConfig()
                            .getStub(), this.getConfig()
                            .getConfiguracion());

            logger.info("DRAGONFISH - new Token:" + this.getConfig()
                    .getConfiguracion()
                    .getTocken());
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