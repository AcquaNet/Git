package org.mule.modules.jde.atina.automation.functional;

import java.util.Map;

import org.junit.Test;
import org.mule.modules.jde.atina.automation.functional.TestDataBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetItemPriceAndAvailabilityTestCases extends AbstractTestCases {

    protected final Logger logger = LoggerFactory.getLogger(GetItemPriceAndAvailabilityTestCases.class);

    @SuppressWarnings({
            "unchecked",
            "unused"
    })
    @Test
    public void executeTest1() throws Exception {

        String entityType = TestDataBuilder.getItemPriceAvaEntityType();

        logger.info("MULESOFT - FUNCTIONAL_TEST " + entityType + " BEGIN ");

        // ======================
        // Get Connector Instance
        // ======================

        Map<String, Object> entityData = TestDataBuilder.getItemPriceAvaEntityData();

        Map<String, Object> result = (Map<String, Object>) ejecucionInterna("GetItemPriceAvaTestCases: ", entityType, entityData);

        logger.info("MULESOFT - FUNCTIONAL_TEST " + entityType + " END ");

    }

    @SuppressWarnings({
            "unchecked",
            "unused"
    })
    @Test
    public void executeTest2() throws Exception {

        String entityType = TestDataBuilder.getItemPriceEntityType();

        logger.info("MULESOFT - FUNCTIONAL_TEST " + entityType + " BEGIN ");

        // ======================
        // Get Connector Instance
        // ======================

        Map<String, Object> entityData = TestDataBuilder.getItemPriceInvalidItemEntityData();

        Map<String, Object> result = (Map<String, Object>) ejecucionInterna("GetItemPriceTestCases: ", entityType, entityData);

        logger.info("MULESOFT - FUNCTIONAL_TEST " + entityType + " END ");

    }

}
