package org.mule.modules.dragonfish.automation.functional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.mule.modules.dragonfish.automation.functional.TestDataBuilder;

public class EjecutarOperacionPutArticuloTestCases extends AbstractTestCases {

    protected final Logger logger = LoggerFactory.getLogger(EjecutarOperacionPutArticuloTestCases.class);

    @Test
    public void testPutArticuloImage() throws Exception {

        logger.info("MULESOFT - FUNCTIONAL_TEST EjecutarOperacionGetImageTestCases: testGetImage() BEGIN ");

        // ======================
        // Get Connector Instance
        // ======================

        String entityType = TestDataBuilder.getPutArticuloByIDEntityType();

        Map<String, Object> entityData = TestDataBuilder.putArticuloByIDEntityData();

        ejecucionInterna("CallObjectTestCases: testEjecutarOperacionValoresGetValorId: ", entityType, entityData);

        logger.info("MULESOFT - FUNCTIONAL_TEST EjecutarOperacionGetImageTestCases: testGetImage() END ");

    }

    @Test
    @Ignore
    public void testPostEquivalencia() throws Exception {

        logger.info("MULESOFT - FUNCTIONAL_TEST testPostEquivalencia: BEGIN ");

        // ======================
        // Get Connector Instance
        // ======================

        String entityType = TestDataBuilder.getPostEquivalenciaEntityType();

        Map<String, Object> entityData = TestDataBuilder.postEquivalenciaDEntityData();

        ejecucionInterna("CallObjectTestCases: testEjecutarOperacionValoresGetValorId: ", entityType, entityData);

        logger.info("MULESOFT - FUNCTIONAL_TEST testPostEquivalencia: END ");

    }

}
