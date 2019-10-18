package org.mule.modules.jde.atina.automation.functional;

import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.mule.modules.jde.atina.automation.functional.TestDataBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.Assert;

public class EjecutarOperacionTestCases extends AbstractTestCases {

    protected final Logger logger = LoggerFactory.getLogger(EjecutarOperacionTestCases.class);

    @Test
    public void testEjecutarOperacionValoresGetValorId() throws Exception {

        logger.info("MULESOFT - FUNCTIONAL_TEST CallObjectTestCases: testCallObject() BEGIN ");

        // ======================
        // Get Connector Instance
        // ======================

        String entityType = TestDataBuilder.getValoresGetValorIdEntityType();

        Map<String, Object> entityData = TestDataBuilder.getValoresGetValorIdEntityData();

        Map<String, Object> result = (Map<String, Object>) ejecucionInterna("CallObjectTestCases: testEjecutarOperacionValoresGetValorId: ", entityType, entityData);

        logger.info("MULESOFT - FUNCTIONAL_TEST CallObjectTestCases: testCallObject() END ");

    }

    @Test
    public void testEjecutarOperacionLimiteDeConsumo() throws Exception {

        logger.info("MULESOFT - FUNCTIONAL_TEST CallObjectTestCases: testCallObject() BEGIN ");

        // ======================
        // Get Connector Instance
        // ======================

        String entityType = TestDataBuilder.getLimiteDeConsumoEntityType();

        Map<String, Object> entityData = TestDataBuilder.getLimiteDeConsumoEntityData();

        Map<String, Object> result = (Map<String, Object>) ejecucionInterna("CallObjectTestCases: testEjecutarOperacionLimiteDeConsumo: ", entityType, entityData);

        logger.info("MULESOFT - FUNCTIONAL_TEST CallObjectTestCases: testCallObject() END ");

    }

    @Test
    public void testEjecutarOperacionArticulosPostArticulo() throws Exception {

        logger.info("MULESOFT - FUNCTIONAL_TEST CallObjectTestCases: testCallObject() BEGIN ");

        // ======================
        // Delete Articulo
        // ======================

        String entityTypeDelete = TestDataBuilder.deleteArticuloById();

        Map<String, Object> entityDataDelete = TestDataBuilder.deleteArticulo();

        try
        {
            Map<String, Object> resultDelete = (Map<String, Object>) ejecucionInterna("CallObjectTestCases: testEjecutarOperacionLimiteDeConsumo: ", entityTypeDelete,
                    entityDataDelete);

        } catch (Exception e)
        {
            logger.error(e.getMessage());
        }

        // ======================
        // Get Connector Instance
        // ======================

        String entityType = TestDataBuilder.postArticuloEntityType();

        Map<String, Object> entityData = TestDataBuilder.postArticulo();

        Map<String, Object> result = (Map<String, Object>) ejecucionInterna("CallObjectTestCases: testEjecutarOperacionLimiteDeConsumo: ", entityType, entityData);

        logger.info("MULESOFT - FUNCTIONAL_TEST CallObjectTestCases: testCallObject() END ");

    }

    @Test
    @Ignore
    public void testEjecutarOperacionArticulosGetArticuloWithInvalidParameter() throws Exception {

        logger.info("MULESOFT - FUNCTIONAL_TEST CallObjectTestCases: testEjecutarOperacionArticulosGetArticulo() BEGIN ");

        // ======================
        // Get Connector Instance
        // ======================

        String entityType = TestDataBuilder.getArticuloByID();

        Map<String, Object> entityData = TestDataBuilder.getArticuloByIDEntityDataInvalidParameter();

        try
        {
            Map<String, Object> result = (Map<String, Object>) ejecucionInterna("CallObjectTestCases: testEjecutarOperacionArticulosGetArticulo: ", entityType, entityData);

        } catch (Exception e)
        {
            logger.error(e.getMessage());
            Assert.assertTrue(e.getMessage()
                    .startsWith("Parametro Invalido [idxxx]"));

        }

        logger.info("MULESOFT - FUNCTIONAL_TEST CallObjectTestCases: testEjecutarOperacionArticulosGetArticulo() END ");

    }

    @Test
    public void testEjecutarOperacionArticulosGetArticuloWithInvalidArticulo() throws Exception {

        logger.info("MULESOFT - FUNCTIONAL_TEST CallObjectTestCases: testEjecutarOperacionArticulosGetArticulo() BEGIN ");

        // ======================
        // Get Connector Instance
        // ======================

        String entityType = TestDataBuilder.getArticuloByID();

        Map<String, Object> entityData = TestDataBuilder.getArticuloByIDEntityDataInvalidArticulo();

        try
        {
            Map<String, Object> result = (Map<String, Object>) ejecucionInterna("CallObjectTestCases: testEjecutarOperacionArticulosGetArticulo: ", entityType, entityData);

        } catch (Exception e)
        {
            logger.error(e.getMessage());
            Assert.assertTrue(e.getMessage()
                    .startsWith("INTERNAL: El dato buscado 00100101A de la entidad ARTICULOS no existe"));

        }

        logger.info("MULESOFT - FUNCTIONAL_TEST CallObjectTestCases: testEjecutarOperacionArticulosGetArticulo() END ");

    }

}
