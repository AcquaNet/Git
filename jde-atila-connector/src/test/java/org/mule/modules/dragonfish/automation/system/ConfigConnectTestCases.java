package org.mule.modules.dragonfish.automation.system;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mule.api.ConnectionException;
import org.mule.modules.atila.jde.models.DragonFishConfiguracion;
import org.mule.tools.devkit.ctf.configuration.util.ConfigurationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acqua.dragonfishserverwp.servicios.DragonFishServiceGrpc.DragonFishServiceBlockingStub;

import io.grpc.StatusRuntimeException;
import junit.framework.Assert;

public class ConfigConnectTestCases extends AbstractConfigConnectTestCases {

    protected final Logger logger = LoggerFactory.getLogger(ConfigConnectTestCases.class);

    private static final String LOG_PREFIX = "DragonFish - SYSTEM_TEST - ConfigConnectTestCases:";

    public org.mule.modules.atila.jde.config.ConnectorConfig configDragonFish;

    @Before
    public void setup() throws Exception {

        validCredentials = ConfigurationUtils.getAutomationCredentialsProperties();

    }

    @After
    public void close() throws Exception {

        logger.info(LOG_PREFIX + " Disconnecting... ");

        configDragonFish.disconnect();

        logger.info(LOG_PREFIX + " ConfigConnectTestCases closed");

    }

    @Test
    public void validarConexion() throws Exception {

        logger.info(LOG_PREFIX + " validarConexion() INICIO ");

        configDragonFish = new org.mule.modules.atila.jde.config.ConnectorConfig();

        String serverName = validCredentials.getProperty("config.urlBase");
        String codigoConfCliente = validCredentials.getProperty("config.codigoConfCliente");
        String clavePrivadaConfCliente = validCredentials.getProperty("config.clavePrivadaConfCliente");
        String algoritmo = validCredentials.getProperty("config.algoritmo");
        String user = validCredentials.getProperty("config.user");
        String password = validCredentials.getProperty("config.password");
        Integer expiracion = Integer.valueOf(validCredentials.getProperty("config.expiracion"));
        String servidorServicio = validCredentials.getProperty("config.servidorServicio");
        Integer puertoServicio = Integer.valueOf(validCredentials.getProperty("config.puertoServicio"));

        configDragonFish.connect(serverName, codigoConfCliente, clavePrivadaConfCliente, algoritmo, user, password, expiracion, servidorServicio, puertoServicio);

        logger.info(LOG_PREFIX + " validarConexion() FIN ");

    }

    @Test
    public void InvalidConexion() throws Exception {

        logger.info(LOG_PREFIX + " validarConexion() INICIO ");

        configDragonFish = new org.mule.modules.atila.jde.config.ConnectorConfig();

        String serverName = validCredentials.getProperty("config.urlBase");
        String codigoConfCliente = validCredentials.getProperty("config.codigoConfCliente");
        String clavePrivadaConfCliente = validCredentials.getProperty("config.clavePrivadaConfCliente");
        String algoritmo = validCredentials.getProperty("config.algoritmo");
        String user = "INVALID USER";
        String password = validCredentials.getProperty("config.password");
        Integer expiracion = Integer.valueOf(validCredentials.getProperty("config.expiracion"));
        String servidorServicio = validCredentials.getProperty("config.servidorServicio");
        Integer puertoServicio = Integer.valueOf(validCredentials.getProperty("config.puertoServicio"));

        try
        {
            configDragonFish.connect(serverName, codigoConfCliente, clavePrivadaConfCliente, algoritmo, user, password, expiracion, servidorServicio, puertoServicio);
        } catch (org.mule.modules.connector.exceptions.ExternalConnectorException e)
        {
            logger.error("Validacion de Login Correcta: " + e.getErrorMessage(), e);

            Assert.assertTrue(e.getErrorMessage()
                    .startsWith("INTERNAL: Error Autenticando Usuario"));
        }

        logger.info(LOG_PREFIX + " validarConexion() FIN ");

    }

    @Test
    // REQUIERE QUE public void login(DragonFishServiceBlockingStub stub, DragonFishConfiguracion configuracion)
    // Tenga otro nombre en .setConnectorName("DRAGONX")
    public void InvalidConnectorName() throws Exception {

        logger.info(LOG_PREFIX + " validarConexion() INICIO ");

        configDragonFish = new org.mule.modules.atila.jde.config.ConnectorConfig();

        String serverName = validCredentials.getProperty("config.urlBase");
        String codigoConfCliente = validCredentials.getProperty("config.codigoConfCliente");
        String clavePrivadaConfCliente = validCredentials.getProperty("config.clavePrivadaConfCliente");
        String algoritmo = validCredentials.getProperty("config.algoritmo");
        String user = validCredentials.getProperty("config.user");
        ;
        String password = validCredentials.getProperty("config.password");
        Integer expiracion = Integer.valueOf(validCredentials.getProperty("config.expiracion"));
        String servidorServicio = validCredentials.getProperty("config.servidorServicio");
        Integer puertoServicio = Integer.valueOf(validCredentials.getProperty("config.puertoServicio"));

        try
        {
            configDragonFish.connect(serverName, codigoConfCliente, clavePrivadaConfCliente, algoritmo, user, password, expiracion, servidorServicio, puertoServicio);
        } catch (ConnectionException e)
        {
            logger.error("Validacion de Login Correcta: " + e.getMessage(), e);

            Assert.assertTrue(e.getMessage()
                    .startsWith("INTERNAL: Error Cargando Clases de Java Para Autenticar"));
        }

        logger.info(LOG_PREFIX + " validarConexion() FIN ");

    }

    @Test
    public void InvalidURLBase() throws Exception {

        logger.info(LOG_PREFIX + " validarConexion() INICIO ");

        configDragonFish = new org.mule.modules.atila.jde.config.ConnectorConfig();

        String serverName = "http://localhost:8009/api.Dragonfish";
        String codigoConfCliente = validCredentials.getProperty("config.codigoConfCliente");
        String clavePrivadaConfCliente = validCredentials.getProperty("config.clavePrivadaConfCliente");
        String algoritmo = validCredentials.getProperty("config.algoritmo");
        String user = validCredentials.getProperty("config.user");
        ;
        String password = validCredentials.getProperty("config.password");
        Integer expiracion = Integer.valueOf(validCredentials.getProperty("config.expiracion"));
        String servidorServicio = validCredentials.getProperty("config.servidorServicio");
        Integer puertoServicio = Integer.valueOf(validCredentials.getProperty("config.puertoServicio"));

        try
        {
            configDragonFish.connect(serverName, codigoConfCliente, clavePrivadaConfCliente, algoritmo, user, password, expiracion, servidorServicio, puertoServicio);
        } catch (org.mule.modules.connector.exceptions.ExternalConnectorException e)
        {
            logger.error("Validacion de Login Correcta: " + e.getErrorMessage(), e);

            Assert.assertTrue(e.getErrorMessage()
                    .startsWith("INTERNAL: Error Autenticando Usuario"));
        }

        logger.info(LOG_PREFIX + " validarConexion() FIN ");

    }

}
