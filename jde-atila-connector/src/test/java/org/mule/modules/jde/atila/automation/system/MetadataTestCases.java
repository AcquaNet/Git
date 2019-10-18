package org.mule.modules.jde.atila.automation.system;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mule.common.Result;
import org.mule.common.metadata.DefaultMetaDataKey;
import org.mule.common.metadata.MetaData;
import org.mule.common.metadata.MetaDataKey;
import org.mule.modules.atila.jde.JDEAtilaConnector;
import org.mule.modules.atila.jde.exceptions.InternalConnectorException;
import org.mule.modules.atila.jde.interfaces.ConnectorServiceInterface;
import org.mule.modules.tests.ConnectorTestUtils;
import org.mule.tools.devkit.ctf.configuration.util.ConfigurationUtils;
import org.mule.tools.devkit.ctf.mockup.ConnectorDispatcher;
import org.mule.tools.devkit.ctf.mockup.ConnectorTestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jde.jdeserverwp.servicios.TipoDelParametroInput;
import com.jde.jdeserverwp.servicios.TipoDelParametroOutput;

import org.mule.modules.jde.atila.automation.system.AbstractConfigConnectTestCases;

import junit.framework.Assert;

public class MetadataTestCases extends AbstractConfigConnectTestCases {

    protected final Logger logger = LoggerFactory.getLogger(MetadataTestCases.class);

    private static final String LOG_PREFIX = "DragonFish - SYSTEM_TEST - ConfigConnectTestCases:";

    public static org.mule.modules.atila.jde.config.ConnectorConfig configDragonFish;

    private static ConnectorTestContext<JDEAtilaConnector> context = null;
    private static ConnectorDispatcher<JDEAtilaConnector> dispatcher = null;

    @BeforeClass
    public static void setup() throws Exception {

        validCredentials = ConfigurationUtils.getAutomationCredentialsProperties();

        // Current connector context instance

        ConnectorTestContext.initialize(JDEAtilaConnector.class);

        context = ConnectorTestContext.getInstance();

        // Connector dispatcher
        dispatcher = context.getConnectorDispatcher();

    }

    @AfterClass
    public static void close() throws Exception {

        if (configDragonFish != null)
        {
            configDragonFish.disconnect();
        }

    }

    @Test
    @Ignore
    public void validarMetadata() throws Exception {

        logger.info(LOG_PREFIX + " validarMetadata() INICIO ");

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

        // configDragonFish.connect(serverName, codigoConfCliente, clavePrivadaConfCliente, algoritmo, user, password, expiracion, servidorServicio, puertoServicio);

        ConnectorServiceInterface servicio = configDragonFish.getService();

        Map<String, String> operaciones = servicio.getMetadataOperations(configDragonFish.getStub(), configDragonFish.getConfiguracion());

        // MetaDataKey key = new MetaDataKey();

        MetaDataKey mdkey = new DefaultMetaDataKey("Articulo_articuloPost", "Alta de Articulos");

        List<TipoDelParametroInput> inputList = servicio.getInputMetadataForOperation(configDragonFish.getStub(), configDragonFish.getConfiguracion(), "Articulo_articuloPost");

        List<TipoDelParametroOutput> outputList = servicio.getOutputMetadataForOperation(configDragonFish.getStub(), configDragonFish.getConfiguracion(), "Articulo_articuloPost");

        logger.info(LOG_PREFIX + " validarConexion() FIN ");

    }

    @Test
    @Ignore
    public void validarConnectorMetadata() throws Exception {

        try {

            Result<List<MetaDataKey>> metaDataKeysResult = dispatcher.fetchMetaDataKeys();

            assertTrue(Result.Status.SUCCESS.equals(metaDataKeysResult.getStatus()));

            List<MetaDataKey> metaDataKeys = metaDataKeysResult.get();

            MetaDataKey accountKey = null;

            for (MetaDataKey key : metaDataKeys) {

                System.out.println(key);

                if (accountKey == null && key.getId()
                        .equals("Articulo_articuloPost")) {
                    accountKey = key;
                }

            }

            assertNotNull(accountKey);

            Result<MetaData> accountKeyResult = dispatcher.fetchMetaData(accountKey);

            assertTrue(Result.Status.SUCCESS.equals(accountKeyResult.getStatus()));

        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @Test
    @Ignore
    public void invalidOperation() throws Exception {

        logger.info(LOG_PREFIX + " validarMetadata() INICIO ");

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

        // configDragonFish.connect(serverName, codigoConfCliente, clavePrivadaConfCliente, algoritmo, user, password, expiracion, servidorServicio, puertoServicio);

        ConnectorServiceInterface servicio = configDragonFish.getService();

        try
        {
            Map<String, String> operaciones = servicio.getMetadataOperations(configDragonFish.getStub(), configDragonFish.getConfiguracion());

        } catch (InternalConnectorException e)
        {
            logger.error("Validacion de Login Correcta: " + e.getErrorMessage(), e);

            Assert.assertTrue(e.getErrorMessage()
                    .startsWith("org.mule.modules.connector.exceptions.InternalConnectorException: INTERNAL: Error Swagger Server: Error leyendo metadata de operaciones"));
        }

        logger.info(LOG_PREFIX + " validarConexion() FIN ");

    }

    @Test
    public void validInputMetadata() throws Exception {

        logger.info(LOG_PREFIX + " validarMetadata() INICIO ");

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

        // configDragonFish.connect(serverName, codigoConfCliente, clavePrivadaConfCliente, algoritmo, user, password, expiracion, servidorServicio, puertoServicio);

        ConnectorServiceInterface servicio = configDragonFish.getService();

        // MetaDataKey key = new MetaDataKey();

        MetaDataKey mdkey = new DefaultMetaDataKey("Articulo", "Get Articulos");

        try
        {
            List<TipoDelParametroInput> inputList = servicio.getInputMetadataForOperation(configDragonFish.getStub(), configDragonFish.getConfiguracion(),
                    "ArticuloById_articuloByIdGet");

        } catch (InternalConnectorException e)
        {
            logger.error("Validacion de Login Correcta: " + e.getErrorMessage(), e);

            Assert.assertTrue(e.getErrorMessage()
                    .startsWith("INTERNAL: Error Swagger Server: Error leyendo metadata de operaciones"));
        }

        logger.info(LOG_PREFIX + " validarConexion() FIN ");

    }

    @Test
    @Ignore
    public void invalidInputMetadata() throws Exception {

        logger.info(LOG_PREFIX + " validarMetadata() INICIO ");

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

        // configDragonFish.connect(serverName, codigoConfCliente, clavePrivadaConfCliente, algoritmo, user, password, expiracion, servidorServicio, puertoServicio);

        ConnectorServiceInterface servicio = configDragonFish.getService();

        // MetaDataKey key = new MetaDataKey();

        MetaDataKey mdkey = new DefaultMetaDataKey("Articulo_articuloPost", "Alta de Articulos");

        try
        {
            List<TipoDelParametroInput> inputList = servicio.getInputMetadataForOperation(configDragonFish.getStub(), configDragonFish.getConfiguracion(), "Articulo_articuloPostX");

        } catch (InternalConnectorException e)
        {
            logger.error("Validacion de Login Correcta: " + e.getErrorMessage(), e);

            Assert.assertTrue(e.getErrorMessage()
                    .startsWith("INTERNAL: Error Swagger Server: Error leyendo metadata de operaciones"));
        }

        logger.info(LOG_PREFIX + " validarConexion() FIN ");

    }

    @Test
    @Ignore
    public void invalidOutputMetadata() throws Exception {

        logger.info(LOG_PREFIX + " validarMetadata() INICIO ");

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

        // configDragonFish.connect(serverName, codigoConfCliente, clavePrivadaConfCliente, algoritmo, user, password, expiracion, servidorServicio, puertoServicio);

        ConnectorServiceInterface servicio = configDragonFish.getService();

        // MetaDataKey key = new MetaDataKey();

        MetaDataKey mdkey = new DefaultMetaDataKey("Articulo_articuloPost", "Alta de Articulos");

        try
        {
            List<TipoDelParametroOutput> outputList = servicio.getOutputMetadataForOperation(configDragonFish.getStub(), configDragonFish.getConfiguracion(),
                    "Articulo_articuloPost");

        } catch (InternalConnectorException e)
        {
            logger.error("Validacion de Login Correcta: " + e.getErrorMessage(), e);

            Assert.assertTrue(e.getErrorMessage()
                    .startsWith("INTERNAL: Error Swagger Server: Error leyendo metadata de operaciones"));
        }

        logger.info(LOG_PREFIX + " validarConexion() FIN ");

    }

}
