package org.mule.modules.jde.atina.automation.system;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.api.ConnectionException;
import org.mule.modules.atina.jde.exceptions.ExternalConnectorException;
import org.mule.modules.atina.jde.exceptions.InternalConnectorException;
import org.mule.tools.devkit.ctf.configuration.util.ConfigurationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.Assert;

public class ConfigConnectTestCases extends AbstractConfigConnectTestCases {

    protected final Logger logger = LoggerFactory.getLogger(ConfigConnectTestCases.class);

    private static final String LOG_PREFIX = "JDE Atina - SYSTEM_TEST - ConfigConnectTestCases:";

    public org.mule.modules.atina.jde.config.ConnectorConfig configJDEAtina;

    @Before
    public void setup() throws Exception {

        validCredentials = ConfigurationUtils.getAutomationCredentialsProperties();

    }

    @After
    public void close() throws Exception {

        logger.info(LOG_PREFIX + " Disconnecting... ");

        configJDEAtina.disconnect();

        logger.info(LOG_PREFIX + " ConfigConnectTestCases closed");

    }

    @Test
    public void validWSConnection() throws Exception {

        logger.info(LOG_PREFIX + " validarConexion() INICIO ");

        configJDEAtina = new org.mule.modules.atina.jde.config.ConnectorConfig();

        String jdeUser = validCredentials.getProperty("config.jdeUser");
        String jdePassword = validCredentials.getProperty("config.jdePassword");
        String jdeEnvironment = validCredentials.getProperty("config.jdeEnvironment");
        String jdeRole = validCredentials.getProperty("config.jdeRole");
        Boolean wsConnection = Boolean.valueOf(validCredentials.getProperty("config.wsConnection"));
        String microServiceName = validCredentials.getProperty("config.microServiceName");
        Integer microServicePort = Integer.valueOf(validCredentials.getProperty("config.microServicePort"));
        String token = "";

        configJDEAtina.connect(jdeUser, jdePassword, jdeEnvironment, jdeRole,token, wsConnection, microServiceName, microServicePort);

        Assert.assertTrue(configJDEAtina.getConfiguracion()
                .getSessionID() != 0);

        logger.info(LOG_PREFIX + " validarConexion() FIN ");

    }

    @Test
    public void InvalidWSConexionWithExternalConnectorException() throws Exception {

        logger.info(LOG_PREFIX + " validarConexion() INICIO ");

        configJDEAtina = new org.mule.modules.atina.jde.config.ConnectorConfig();

        String jdeUser = validCredentials.getProperty("config.jdeUser") + "INVALID";
        String jdePassword = validCredentials.getProperty("config.jdePassword");
        String jdeEnvironment = validCredentials.getProperty("config.jdeEnvironment");
        String jdeRole = validCredentials.getProperty("config.jdeRole");
        Boolean wsConnection = Boolean.valueOf(validCredentials.getProperty("config.wsConnection"));
        String microServiceName = validCredentials.getProperty("config.microServiceName");
        Integer microServicePort = Integer.valueOf(validCredentials.getProperty("config.microServicePort"));
        String token = "";

        try
        {
            configJDEAtina.connect(jdeUser, jdePassword, jdeEnvironment, jdeRole, token, wsConnection, microServiceName, microServicePort);

        } catch (ConnectionException e)
        {

            ExternalConnectorException causeBy = (ExternalConnectorException) e.getCause();

            logger.error("Invalid Connection: " + causeBy.getErrorMessage(), e);
            logger.error("        Error: " + causeBy.getClaseDeLaOperacion(), e);

            Assert.assertTrue(causeBy.getErrorMessage()
                    .startsWith("INTERNAL: Error Creating Connection"));

            Assert.assertTrue(causeBy.getClaseDeLaOperacion()
                    .startsWith("JDE Conexion Error InvalidLoginException: Invalid UserName and/or Password"));

        }

        logger.info(LOG_PREFIX + " validarConexion() FIN ");

    }

    @Test
    public void InvalidWSConexionWithInternalConnectorException() throws Exception {

        logger.info(LOG_PREFIX + " validarConexion() INICIO ");

        configJDEAtina = new org.mule.modules.atina.jde.config.ConnectorConfig();

        String jdeUser = validCredentials.getProperty("config.jdeUser");
        String jdePassword = validCredentials.getProperty("config.jdePassword");
        String jdeEnvironment = validCredentials.getProperty("config.jdeEnvironment");
        String jdeRole = validCredentials.getProperty("config.jdeRole");
        Boolean wsConnection = Boolean.valueOf(validCredentials.getProperty("config.wsConnection"));
        String microServiceName = validCredentials.getProperty("config.microServiceName");
        Integer microServicePort = Integer.valueOf(validCredentials.getProperty("config.microServicePort"));
        String token = "";

        try
        {

            microServiceName = "Invalid";

            configJDEAtina.connect(jdeUser, jdePassword, jdeEnvironment, jdeRole, token, wsConnection, microServiceName, microServicePort);

            fail("Exception not thrown");

        } catch (ConnectionException e)
        {
            logger.error("Invalid Connection ConnectionException : " + e.getMessage(), e);

            InternalConnectorException causeBy = (InternalConnectorException) e.getCause();

            Assert.assertTrue(causeBy.getErrorMessage()
                    .startsWith("UNAVAILABLE: NameResolver returned an empty list"));

        }

        logger.info(LOG_PREFIX + " validarConexion() FIN ");

    }

}
