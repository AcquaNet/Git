package org.mule.modules.jde.atina.automation.functional;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;
import org.mule.modules.atina.jde.exceptions.ExternalConnectorException;
import org.mule.modules.jde.atina.automation.functional.TestDataBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CreateVoucherTestCases extends AbstractTestCases {

    protected final Logger logger = LoggerFactory.getLogger(CreateVoucherTestCases.class);

    @SuppressWarnings({
            "unchecked"
    })
    @Test
    public void executeTest() throws Exception {

        String entityType = TestDataBuilder.getCreteVoucherEntityType();

        logger.info("MULESOFT - FUNCTIONAL_TEST " + entityType + " BEGIN ");

        // ======================
        // Get Connector Instance
        // ======================

        Map<String, Object> entityData = TestDataBuilder.getCreateVoucherEntityData();
        
        String json = new ObjectMapper().writeValueAsString(entityData);
        
        logger.info("MULESOFT - INPUT: " + json  );

        Map<String, Object> result = (Map<String, Object>) ejecucionInterna("Create Voucher: ", entityType, entityData);
        
        String response = new ObjectMapper().writeValueAsString(result);
        
        logger.info("MULESOFT - OUTPUT: " + response  );
        
        // Get Voucher No
        
        assertTrue(result.containsKey("confirmVoucherHeader"));
        Map<String, Object> vh = (Map<String, Object>)result.get("confirmVoucherHeader");
        
        assertTrue(vh.containsKey("voucherKey"));
        Map<String, Object> vk = (Map<String, Object>)vh.get("voucherKey");
        
        assertTrue(vk.containsKey("documentNumber"));
        Integer value = (Integer) vk.get("documentNumber");
         
        
        assertTrue(value>0);

        logger.info("MULESOFT - FUNCTIONAL_TEST " + entityType + " END ");
        
        

    }

     

}
