package org.mule.modules.jde.atila.automation.functional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.mule.modules.jde.atila.automation.functional.TestDataBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjecutarOperacionPostOrderTestCases extends AbstractTestCases {

    protected final Logger logger = LoggerFactory.getLogger(EjecutarOperacionPostOrderTestCases.class);

    @Test
    public void testPostOrder() throws Exception {

        logger.info("MULESOFT - FUNCTIONAL_TEST EjecutarOperacionGetImageTestCases: testGetImage() BEGIN ");

        // ======================
        // Get Connector Instance
        // ======================

        String entityType = TestDataBuilder.getPostOrderEntityType();

        Map<String, Object> entityData = TestDataBuilder.getPostOrderEntityData();

        ejecucionInterna("CallObjectTestCases: testEjecutarOperacionValoresGetValorId: ", entityType, entityData);

        logger.info("MULESOFT - FUNCTIONAL_TEST EjecutarOperacionGetImageTestCases: testGetImage() END ");

    }

}
