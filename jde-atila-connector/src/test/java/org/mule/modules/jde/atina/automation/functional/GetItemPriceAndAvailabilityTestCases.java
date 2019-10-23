package org.mule.modules.jde.atina.automation.functional;

import java.util.Map;

import org.junit.Test;
import org.mule.modules.atina.jde.exceptions.ExternalConnectorException;
import org.mule.modules.jde.atina.automation.functional.TestDataBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetItemPriceAndAvailabilityTestCases extends AbstractTestCases {

    protected final Logger logger = LoggerFactory.getLogger(GetItemPriceAndAvailabilityTestCases.class);

    @SuppressWarnings({
            "unchecked",
            "unused"
    })
    @Test
    public void executeValidGetItemPriceAndAvailabilityTest() throws Exception {

        String entityType = TestDataBuilder.getItemPriceAvaEntityType();

        logger.info("MULESOFT - FUNCTIONAL_TEST " + entityType + " BEGIN ");

        // ======================
        // Get Connector Instance
        // ======================
        
        Map<String, Object> entityData = TestDataBuilder.getItemPriceAvaEntityData();
        
        try
        {
        	Map<String, Object> result = (Map<String, Object>) ejecucionInterna("GetItemPriceAvaTestCases: ", entityType, entityData);
        	
        } catch(ExternalConnectorException e)
        {
        	logger.error(e.getE1Message());
        } catch(Exception e)
        {
        	logger.error(e.getMessage());
        }
 

        logger.info("MULESOFT - FUNCTIONAL_TEST " + entityType + " END ");

    }

   

}
