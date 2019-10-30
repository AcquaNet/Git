package org.mule.modules.jde.atina.automation.system;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.common.Result;
import org.mule.common.metadata.MetaData;
import org.mule.common.metadata.MetaDataKey;
import org.mule.modules.atina.jde.JDEAtinaConnector;
import org.mule.modules.atina.jde.exceptions.InternalConnectorException;
import org.mule.modules.atina.jde.interfaces.ConnectorServiceInterface;
import org.mule.modules.jde.atina.automation.system.AbstractConfigConnectTestCases;
import org.mule.modules.tests.ConnectorTestUtils;
import org.mule.tools.devkit.ctf.configuration.util.ConfigurationUtils;
import org.mule.tools.devkit.ctf.mockup.ConnectorDispatcher;
import org.mule.tools.devkit.ctf.mockup.ConnectorTestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jde.jdeserverwp.servicios.TipoDelParametroInput;
import com.jde.jdeserverwp.servicios.TipoDelParametroOutput;

import junit.framework.Assert;

public class WSMetadataTestCases extends AbstractConfigConnectTestCases {

    protected final Logger logger = LoggerFactory.getLogger(WSMetadataTestCases.class);

    private static final String LOG_PREFIX = "DragonFish - SYSTEM_TEST - ConfigConnectTestCases:";

    public static org.mule.modules.atina.jde.config.ConnectorConfig configJDEAtina;

    private static ConnectorTestContext<JDEAtinaConnector> context = null;
    private static ConnectorDispatcher<JDEAtinaConnector> dispatcher = null;

    @BeforeClass
    public static void setup() throws Exception {

        validCredentials = ConfigurationUtils.getAutomationCredentialsProperties();

        // Current connector context instance

        ConnectorTestContext.initialize(JDEAtinaConnector.class);

        context = ConnectorTestContext.getInstance();

        // Connector dispatcher
        dispatcher = context.getConnectorDispatcher();

    }

    @AfterClass
    public static void close() throws Exception {

        if (configJDEAtina != null)
        {
            configJDEAtina.disconnect();
        }

    }

    @SuppressWarnings("unused")
    @Test
    public void validarWSMetadata() throws Exception {

        logger.info(LOG_PREFIX + " validarMetadata() INICIO ");

        configJDEAtina = new org.mule.modules.atina.jde.config.ConnectorConfig();

        String jdeUser = validCredentials.getProperty("config.jdeUser");
        String jdePassword = validCredentials.getProperty("config.jdePassword");
        String jdeEnvironment = validCredentials.getProperty("config.jdeEnvironment");
        String jdeRole = validCredentials.getProperty("config.jdeRole");
        Boolean wsConnection = Boolean.valueOf(validCredentials.getProperty("config.wsConnection"));
        String microServiceName = validCredentials.getProperty("config.microServiceName");
        Integer microServicePort = Integer.valueOf(validCredentials.getProperty("config.microServicePort"));
        String token = "";

        configJDEAtina.connect(jdeUser, jdePassword, jdeEnvironment, jdeRole, wsConnection, microServiceName, microServicePort);

        Assert.assertTrue(configJDEAtina.getConfiguracion()
                .getSessionID() != 0);

        ConnectorServiceInterface servicio = configJDEAtina.getService();

        Map<String, String> operaciones = servicio.getMetadataOperations(configJDEAtina.getStub(), configJDEAtina.getConfiguracion());

        List<TipoDelParametroInput> inputList = servicio.getInputMetadataForOperation(configJDEAtina.getStub(), configJDEAtina.getConfiguracion(),
                "oracle.e1.bssv.JP430000.ProcurementManager.getPurchaseOrdersForApprover");

        List<TipoDelParametroOutput> outputList = servicio.getOutputMetadataForOperation(configJDEAtina.getStub(), configJDEAtina.getConfiguracion(),
                "oracle.e1.bssv.JP430000.ProcurementManager.getPurchaseOrdersForApprover");

        logger.info(LOG_PREFIX + " validarConexion() FIN ");

    }

    @Test
    public void validarConnectorMetadata() throws Exception {

        try {

            Result<List<MetaDataKey>> metaDataKeysResult = dispatcher.fetchMetaDataKeys();

            assertTrue(Result.Status.SUCCESS.equals(metaDataKeysResult.getStatus()));

            List<MetaDataKey> metaDataKeys = metaDataKeysResult.get();

            MetaDataKey accountKey = null;

            for (MetaDataKey key : metaDataKeys) {

                System.out.println(key);

                if (accountKey == null && key.getId()
                        .equals("oracle.e1.bssv.JP430000.ProcurementManager.getPurchaseOrdersForApprover")) {
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

    @SuppressWarnings("unused")
    @Test
    public void invalidOperation() throws Exception {

        logger.info(LOG_PREFIX + " invalidOperation() INICIO ");

        configJDEAtina = new org.mule.modules.atina.jde.config.ConnectorConfig();

        String jdeUser = validCredentials.getProperty("config.jdeUser");
        String jdePassword = validCredentials.getProperty("config.jdePassword");
        String jdeEnvironment = validCredentials.getProperty("config.jdeEnvironment");
        String jdeRole = validCredentials.getProperty("config.jdeRole");
        Boolean wsConnection = Boolean.valueOf(validCredentials.getProperty("config.wsConnection"));
        String microServiceName = validCredentials.getProperty("config.microServiceName");
        Integer microServicePort = Integer.valueOf(validCredentials.getProperty("config.microServicePort"));
        String token = "";

        configJDEAtina.connect(jdeUser, jdePassword, jdeEnvironment, jdeRole, wsConnection, microServiceName, microServicePort);

        Assert.assertTrue(configJDEAtina.getConfiguracion()
                .getSessionID() != 0);

        ConnectorServiceInterface servicio = configJDEAtina.getService();

        try
        {
            configJDEAtina.getConfiguracion()
                    .setSessionID(0);
            configJDEAtina.getConfiguracion()
                    .setJdeUser("Invalid");

            Map<String, String> operaciones = servicio.getMetadataOperations(configJDEAtina.getStub(), configJDEAtina.getConfiguracion());

        } catch (InternalConnectorException e)
        {
            logger.error("Validacion de Login Correcta: " + e.getClaseDeLaOperacion(), e);

            Assert.assertTrue(e.getClaseDeLaOperacion()
                    .startsWith("JDE Conexion Error InvalidLoginException: Invalid UserName and/or Password"));
        }

        logger.info(LOG_PREFIX + " invalidOperation() FIN ");

    }

    @SuppressWarnings("unused")
    @Test
    public void invalidInputMetadata() throws Exception {

        logger.info(LOG_PREFIX + " invalidInputMetadata() INICIO ");

        configJDEAtina = new org.mule.modules.atina.jde.config.ConnectorConfig();

        String jdeUser = validCredentials.getProperty("config.jdeUser");
        String jdePassword = validCredentials.getProperty("config.jdePassword");
        String jdeEnvironment = validCredentials.getProperty("config.jdeEnvironment");
        String jdeRole = validCredentials.getProperty("config.jdeRole");
        Boolean wsConnection = Boolean.valueOf(validCredentials.getProperty("config.wsConnection"));
        String microServiceName = validCredentials.getProperty("config.microServiceName");
        Integer microServicePort = Integer.valueOf(validCredentials.getProperty("config.microServicePort"));
        String token = "";

        configJDEAtina.connect(jdeUser, jdePassword, jdeEnvironment, jdeRole, wsConnection, microServiceName, microServicePort);

        Assert.assertTrue(configJDEAtina.getConfiguracion()
                .getSessionID() != 0);

        ConnectorServiceInterface servicio = configJDEAtina.getService();

        try
        {

            List<TipoDelParametroInput> inputList = servicio.getInputMetadataForOperation(configJDEAtina.getStub(), configJDEAtina.getConfiguracion(),
                    "Invalid Operation");

        } catch (InternalConnectorException e)
        {
            logger.error("Error: " + e.getClaseDeLaOperacion(), e);

            Assert.assertTrue(e.getClaseDeLaOperacion()
                    .startsWith("Operation without Input Parameter"));
        }

        logger.info(LOG_PREFIX + " invalidInputMetadata() FIN ");

    }

    @SuppressWarnings("unused")
    @Test
    public void invalidOutputMetadata() throws Exception {

        logger.info(LOG_PREFIX + " invalidOutputMetadata() INICIO ");

        configJDEAtina = new org.mule.modules.atina.jde.config.ConnectorConfig();

        String jdeUser = validCredentials.getProperty("config.jdeUser");
        String jdePassword = validCredentials.getProperty("config.jdePassword");
        String jdeEnvironment = validCredentials.getProperty("config.jdeEnvironment");
        String jdeRole = validCredentials.getProperty("config.jdeRole");
        Boolean wsConnection = Boolean.valueOf(validCredentials.getProperty("config.wsConnection"));
        String microServiceName = validCredentials.getProperty("config.microServiceName");
        Integer microServicePort = Integer.valueOf(validCredentials.getProperty("config.microServicePort"));
        String token = "";

        configJDEAtina.connect(jdeUser, jdePassword, jdeEnvironment, jdeRole, wsConnection, microServiceName, microServicePort);

        Assert.assertTrue(configJDEAtina.getConfiguracion()
                .getSessionID() != 0);

        ConnectorServiceInterface servicio = configJDEAtina.getService();

        try
        {

            List<TipoDelParametroOutput> outputList = servicio.getOutputMetadataForOperation(configJDEAtina.getStub(), configJDEAtina.getConfiguracion(),
                    "Invalid Operation");

        } catch (InternalConnectorException e)
        {
            logger.error("Error: " + e.getClaseDeLaOperacion(), e);

            Assert.assertTrue(e.getClaseDeLaOperacion()
                    .startsWith("Operation without Input Parameter"));
        }

        logger.info(LOG_PREFIX + " invalidOutputMetadata() FIN ");

    }

}
