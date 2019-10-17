package org.mule.modules.jde.atila.automation.functional;

import java.util.Map;

import org.junit.Test;
import org.mule.modules.jde.atila.automation.functional.TestDataBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjecutarOperacionDeleteCustomerTestCases extends AbstractTestCases {

    protected final Logger logger = LoggerFactory.getLogger(EjecutarOperacionDeleteCustomerTestCases.class);

    @Test
    public void testEjecutarOperacionArticulosGetArticulo() throws Exception {

        logger.info("MULESOFT - FUNCTIONAL_TEST CallObjectTestCases: testEjecutarOperacionArticulosGetArticulo() BEGIN ");

        // ======================
        // Get Connector Instance
        // ======================

        String entityType = TestDataBuilder.getAddCustomerEntityType();

        Map<String, Object> entityData = TestDataBuilder.getAddCustomerEntityData();

        Map<String, Object> result = (Map<String, Object>) ejecucionInterna("CallObjectTestCases: testEjecutarOperacionArticulosGetArticulo: ", entityType, entityData);

        entityType = TestDataBuilder.getDeleteCustomerEntityType();

        entityData = TestDataBuilder.getDeleteCustomerEntityData();

        result = (Map<String, Object>) ejecucionInterna("CallObjectTestCases: testEjecutarOperacionArticulosGetArticulo: ", entityType, entityData);

        logger.info("MULESOFT - FUNCTIONAL_TEST CallObjectTestCases: testEjecutarOperacionArticulosGetArticulo() END ");

    }

}
