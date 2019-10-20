package org.mule.modules.jde.atina.automation.functional;

import java.util.Map;

import org.junit.Test;
import org.mule.modules.jde.atina.automation.functional.TestDataBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjecutarOperacionConsultaDeArticulosTestCases extends AbstractTestCases {

    protected final Logger logger = LoggerFactory.getLogger(EjecutarOperacionConsultaDeArticulosTestCases.class);

    @Test
    public void testEjecutarOperacionConsultaDeArticulos() throws Exception {

        logger.info("MULESOFT - FUNCTIONAL_TEST CallObjectTestCases: testCallObject() BEGIN ");

        // ======================
        // Get Connector Instance
        // ======================

        String entityType = TestDataBuilder.getArticuloEntityType();

        Map<String, Object> entityData = TestDataBuilder.getArticuloEntityData();

        Map<String, Object> result = (Map<String, Object>) ejecucionInterna("testEjecutarOperacionConsultaDeArticulos: ", entityType, entityData);

        Thread.sleep(60000);

        result = (Map<String, Object>) ejecucionInterna("testEjecutarOperacionConsultaDeArticulos: ", entityType, entityData);

        logger.info("MULESOFT - FUNCTIONAL_TEST CallObjectTestCases: testCallObject() END ");

    }

}
