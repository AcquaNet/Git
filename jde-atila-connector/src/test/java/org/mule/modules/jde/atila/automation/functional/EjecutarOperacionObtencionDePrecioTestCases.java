package org.mule.modules.jde.atila.automation.functional;

import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.mule.modules.jde.atila.automation.functional.TestDataBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.Assert;

public class EjecutarOperacionObtencionDePrecioTestCases extends AbstractTestCases {

    protected final Logger logger = LoggerFactory.getLogger(EjecutarOperacionObtencionDePrecioTestCases.class);

    @Test
    public void testEjecutarOperacionArticulosGetArticulo() throws Exception {

        logger.info("MULESOFT - FUNCTIONAL_TEST CallObjectTestCases: testEjecutarOperacionArticulosGetArticulo() BEGIN ");

        // ======================
        // Get Connector Instance
        // ======================

        String entityType = TestDataBuilder.getPrecioYStock();

        Map<String, Object> entityData = TestDataBuilder.getPrecioYStockEntityData();

        Map<String, Object> result = (Map<String, Object>) ejecucionInterna("CallObjectTestCases: testEjecutarOperacionArticulosGetArticulo: ", entityType, entityData);

        logger.info("MULESOFT - FUNCTIONAL_TEST CallObjectTestCases: testEjecutarOperacionArticulosGetArticulo() END ");

    }

}
