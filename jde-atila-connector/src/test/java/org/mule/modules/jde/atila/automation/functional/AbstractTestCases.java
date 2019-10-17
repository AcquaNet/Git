package org.mule.modules.jde.atila.automation.functional;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.mule.modules.atila.jde.JDEAtilaConnector;
import org.mule.tools.devkit.ctf.configuration.util.ConfigurationUtils;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class AbstractTestCases extends AbstractTestCase<JDEAtilaConnector> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractTestCases.class);
    @SuppressWarnings("unused")
    private static Properties validCredentials;

    public AbstractTestCases() {
        super(JDEAtilaConnector.class);
    }

    @BeforeClass
    public static void initialise() throws Exception {

        logger.info("MULESOFT - FUNCTIONAL_TEST AbstractTestCases: initialise: BEGIN");

        validCredentials = ConfigurationUtils.getAutomationCredentialsProperties();

        logger.info("MULESOFT - FUNCTIONAL_TEST AbstractTestCases: initialise: END");

    }

    @AfterClass
    public static void cleaning() throws Exception {

        logger.info("MULESOFT - FUNCTIONAL_TEST AbstractTestCases: cleaning: BEGIN");

        logger.info("MULESOFT - FUNCTIONAL_TEST AbstractTestCases: cleaning: END");
    }

    public Object ejecucionInterna(String origin, String entityType, Map<String, Object> entityData)
            throws Exception {

        logger.info("MULESOFT - FUNCTIONAL_TEST BEGIN ejecucionInterna: " + origin + " Calling: " + entityType);

        for (String key : entityData.keySet()) {
            logger.info("MULESOFT - FUNCTIONAL_TEST ejecucionInterna: Key: [" + key + "] Value: ["
                    + (entityData.get(key) != null ? entityData.get(key)
                            .toString() : "NULL") + "]");
        }

        Object returnValue = null;

        returnValue = getConnector().servicio(entityType, entityData);

        if (returnValue instanceof HashMap) {

            for (String key : ((Map<String, Object>) returnValue).keySet()) {
                logger.info("MULESOFT - FUNCTIONAL_TEST ejecucionInterna: Return Values: Key: [" + key + "] Value: ["
                        + (entityData.get(key) != null ? entityData.get(key)
                                .toString() : "NULL") + "]");
            }

        }

        logger.info("MULESOFT - FUNCTIONAL_TEST END ejecucionInterna: " + origin + " Calling: " + entityType);

        return returnValue;

    }

}
