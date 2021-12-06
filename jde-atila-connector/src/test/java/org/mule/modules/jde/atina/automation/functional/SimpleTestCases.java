package org.mule.modules.jde.atina.automation.functional;

import java.util.Map;

import org.junit.Test;
import org.mule.modules.jde.atina.automation.functional.TestDataBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleTestCases extends AbstractTestCases {

    protected final Logger logger = LoggerFactory.getLogger(SimpleTestCases.class);

    @SuppressWarnings({
            "unchecked",
            "unused"
    })
    @Test
    public void executeTest1() throws Exception {

        String entityType = TestDataBuilder.getItemPriceEntityType();

        logger.info("MULESOFT - FUNCTIONAL_TEST " + entityType + " BEGIN ");

        // ======================
        // Get Connector Instance
        // ======================

        Map<String, Object> entityData = TestDataBuilder.getItemPriceEntityData();

        Map<String, Object> result = (Map<String, Object>) ejecucionInterna("GetItemPriceTestCases: ", entityType, entityData);

        logger.info("MULESOFT - FUNCTIONAL_TEST " + entityType + " END ");

    }

}
