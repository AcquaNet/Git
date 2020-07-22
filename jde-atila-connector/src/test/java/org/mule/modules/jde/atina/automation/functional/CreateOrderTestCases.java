package org.mule.modules.jde.atina.automation.functional;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.mule.modules.jde.atina.automation.functional.TestDataBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CreateOrderTestCases extends AbstractTestCases {

    protected final Logger logger = LoggerFactory.getLogger(CreateOrderTestCases.class);

    @SuppressWarnings({
            "unchecked"
    })
    @Test
    public void executeTest() throws Exception {

        String entityType = TestDataBuilder.getCreateOrderEntityType();

        logger.info("MULESOFT - FUNCTIONAL_TEST " + entityType + " BEGIN ");

        // ======================
        // Get Payload
        // ======================

        InputStream inputStreamJSON;

        inputStreamJSON = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("payload/" + entityType + ".json");

        assertNotNull(inputStreamJSON);

        // ======================
        // Get Connector Instance
        // ======================

        Map<String, Object> entityData2 = null;

        try {

            String text = IOUtils.toString(inputStreamJSON, StandardCharsets.UTF_8.name());

            logger.info("MULESOFT - INPUT: " + text);

            ObjectMapper oM = new ObjectMapper();

            entityData2 = new ObjectMapper().readValue(text, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, Object> result = (Map<String, Object>) ejecucionInterna("Create Voucher: ", entityType, entityData2);

        String response = new ObjectMapper().writeValueAsString(result);

        logger.info("MULESOFT - OUTPUT: " + response);

        // Get Voucher No

        assertTrue(result.containsKey("confirmVoucherHeader"));
        Map<String, Object> vh = (Map<String, Object>) result.get("confirmVoucherHeader");

        assertTrue(vh.containsKey("voucherKey"));
        Map<String, Object> vk = (Map<String, Object>) vh.get("voucherKey");

        assertTrue(vk.containsKey("documentNumber"));
        Integer value = (Integer) vk.get("documentNumber");

        assertTrue(value > 0);

        logger.info("MULESOFT - FUNCTIONAL_TEST " + entityType + " END ");

    }

}
