package org.mule.modules.jde.atina.automation.functional;

import java.util.Map;

import org.junit.Test;
import org.mule.modules.atina.jde.exceptions.ExternalConnectorException;
import org.mule.modules.jde.atina.automation.functional.TestDataBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetItemPriceTestCases extends AbstractTestCases {

    protected final Logger logger = LoggerFactory.getLogger(GetItemPriceTestCases.class);

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

        try
        {
            Map<String, Object> result = (Map<String, Object>) ejecucionInterna("GetItemPriceTestCases: ", entityType, entityData);

        } catch (ExternalConnectorException e)
        {
            logger.error(e.getE1Message());

        } catch (Exception e)
        {
            logger.error(e.getMessage());
        }

        logger.info("MULESOFT - FUNCTIONAL_TEST " + entityType + " END ");

    }

}
