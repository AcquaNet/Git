package org.mule.modules.jde.atina.automation.functional;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.mule.modules.atina.jde.JDEAtinaConnector;
import org.mule.tools.devkit.ctf.configuration.util.ConfigurationUtils;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;

public class AbstractTestCases extends AbstractTestCase<JDEAtinaConnector> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractTestCases.class);
    @SuppressWarnings("unused")
    private static Properties validCredentials;

    public AbstractTestCases() {
        super(JDEAtinaConnector.class);
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

    @SuppressWarnings("unchecked")
    public Object ejecucionInterna(String origin, String entityType, Map<String, Object> entityData)
            throws Exception {

        logger.info("JDE ATINA - Parameters: " + origin + " Calling: " + entityType);

        hashMapper((HashMap<String, Object>) entityData, 0);

        Object returnValue = null;

        returnValue = getConnector().invokeWS(entityType, entityData);

        if (returnValue instanceof HashMap) {

            hashMapper((HashMap<String, Object>) returnValue, 0);

        }

        logger.info("JDE ATINA - FUNCTIONAL_TEST END ejecucionInterna: " + origin + " Calling: " + entityType);

        return returnValue;

    }
    
    public Object autorizacion(String entityType, Map<String, Object> entityData)
            throws Exception {

        logger.info("JDE ATINA - Parameters for Authorization: " + entityType);

        hashMapper((HashMap<String, Object>) entityData, 0);

        Object returnValue = null;

        returnValue = getConnector().authenticate(entityType, entityData);

         

        logger.info("JDE ATINA - FUNCTIONAL_TEST END ejecucionInterna: Calling: " + entityType);

        return returnValue;

    }

    @SuppressWarnings("unchecked")
    public static void hashMapper(Map<String, Object> lhm1, int level) throws ParseException {
        String levelLog = StringUtils.repeat(" > ", level);
        for (Map.Entry<String, Object> entry : lhm1.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (!(value instanceof Map) && !(value instanceof ArrayList)) {
                logger.info("JDE ATINA - " + levelLog + " Key: [" + key + "] Value: [" +
                        value + "]");
            } else if (value instanceof ArrayList) {
                logger.info("JDE ATINA - " + levelLog + " Key: [" + key + "]");
                for (Object obj : (ArrayList<?>) value)
                {
                    logger.info("JDE ATINA " + levelLog + "-----------------------------------------------");

                    if (obj instanceof Map)
                    {
                        Map<String, Object> subMap = (Map<String, Object>) obj;
                        level++;
                        hashMapper(subMap, level);
                        level--;
                    }

                }

            } else if (value instanceof Map) {
                logger.info("JDE ATINA - " + levelLog + " Key: [" + key + "]");
                Map<String, Object> subMap = (Map<String, Object>) value;
                level++;
                hashMapper(subMap, level);
                level--;
            } else {
                throw new IllegalArgumentException(String.valueOf(value));
            }

        }
    }

}
