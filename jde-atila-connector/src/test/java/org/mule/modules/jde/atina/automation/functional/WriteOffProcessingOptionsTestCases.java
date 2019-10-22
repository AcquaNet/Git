package org.mule.modules.jde.atina.automation.functional;

import java.util.Map;

import org.junit.Test;
import org.mule.modules.jde.atina.automation.functional.TestDataBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WriteOffProcessingOptionsTestCases extends AbstractTestCases {

    protected final Logger logger = LoggerFactory.getLogger(WriteOffProcessingOptionsTestCases.class);

    @SuppressWarnings({
            "unused",
            "unchecked"
    })
    @Test
    public void executeTest1() throws Exception {

        String entityType = TestDataBuilder.getWriteOffProcessingOptionsEntityType();

        logger.info("MULESOFT - FUNCTIONAL_TEST " + entityType + " BEGIN ");

        // ======================
        // Get Connector Instance
        // ======================

        Map<String, Object> entityData = TestDataBuilder.getWriteOffProcessingOptionsEntityData();

        Map<String, Object> result = ((Map<String, Object>) ejecucionInterna("WriteOffProcessingOptionsTestCases: ", entityType, entityData));

        logger.info("MULESOFT - FUNCTIONAL_TEST " + entityType + " END ");

    }

}
