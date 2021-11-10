package org.mule.modules.jde.atina.automation.functional;

import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.Test;
import org.mule.modules.atina.jde.exceptions.ExternalConnectorException;
import org.mule.modules.jde.atina.automation.functional.TestDataBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetPurchaseOrdersForApproverTestCases extends AbstractTestCases {

    protected final Logger logger = LoggerFactory.getLogger(GetPurchaseOrdersForApproverTestCases.class);

    @SuppressWarnings({
            "unused",
            "unchecked"
    })
    @Test
    public void executeTest1() throws Exception {

        String entityType = TestDataBuilder.getPurchaseOrdersForApproverEntityType();

        logger.info("MULESOFT - FUNCTIONAL_TEST " + entityType + " BEGIN ");

        // ======================
        // Get Connector Instance
        // ======================

        Map<String, Object> entityData = TestDataBuilder.getPurchaseOrdersForApproverEntityData();

        Map<String, Object> result = (Map<String, Object>) ejecucionInterna("GetPurchaseOrdersForApproverTestCases: ", entityType, entityData);

        logger.info("MULESOFT - FUNCTIONAL_TEST " + entityType + " END ");

    }

    @Test
    public void executeTestInvalidOperation() throws Exception {

        String entityType = TestDataBuilder.getPurchaseOrdersForApproverEntityType();

        logger.info("MULESOFT - FUNCTIONAL_TEST " + entityType + " BEGIN ");

        // ======================
        // Get Connector Instance
        // ======================

        entityType = entityType.concat("XX");

        Map<String, Object> entityData = TestDataBuilder.getPurchaseOrdersForApproverEntityData();

        try {

            ejecucionInterna("GetPurchaseOrdersForApproverTestCases: ", entityType, entityData);

            fail("No se ha producido la exception esperada");

        } catch (ExternalConnectorException e) {
            logger.error(e.getE1Message());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        logger.info("MULESOFT - FUNCTIONAL_TEST " + entityType + " END ");

    }

}
