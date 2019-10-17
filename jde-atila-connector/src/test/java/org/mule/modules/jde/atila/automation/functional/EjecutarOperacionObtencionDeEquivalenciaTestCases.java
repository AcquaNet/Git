package org.mule.modules.jde.atila.automation.functional;

import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.mule.modules.jde.atila.automation.functional.TestDataBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.Assert;

public class EjecutarOperacionObtencionDeEquivalenciaTestCases extends AbstractTestCases {

    protected final Logger logger = LoggerFactory.getLogger(EjecutarOperacionObtencionDeEquivalenciaTestCases.class);

    @Test
    public void testEjecutarOperacionEquivalencia() throws Exception {

        logger.info("MULESOFT - FUNCTIONAL_TEST CallObjectTestCases: testEjecutarOperacionEquivalencia() BEGIN ");

        // ======================
        // Get Connector Instance
        // ======================

        String entityType = TestDataBuilder.getEquivalenciaEntityType();

        Map<String, Object> entityData = TestDataBuilder.getEquivalenciaEntityData();

        Map<String, Object> result = (Map<String, Object>) ejecucionInterna("testEjecutarOperacionEquivalencia: ", entityType, entityData);

        logger.info("MULESOFT - FUNCTIONAL_TEST CallObjectTestCases: testEjecutarOperacionEquivalencia() END ");

    }

}
