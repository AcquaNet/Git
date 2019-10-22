package org.mule.modules.jde.atina.automation.functional;

import java.util.Map;

import org.junit.Test;
import org.mule.modules.jde.atina.automation.functional.TestDataBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetPurchaseOrdersForApproverTestCases extends AbstractTestCases {

    protected final Logger logger = LoggerFactory.getLogger(GetPurchaseOrdersForApproverTestCases.class);

    @Test
    public void testEjecutarOperacionConsultaDeArticulos() throws Exception {

    	String entityType = TestDataBuilder.getPurchaseOrdersForApproverEntityType();
    	 
        logger.info("MULESOFT - FUNCTIONAL_TEST "+ entityType + " BEGIN ");

        // ======================
        // Get Connector Instance
        // ====================== 

        Map<String, Object> entityData = TestDataBuilder.getPurchaseOrdersForApproverEntityData();

        Map<String, Object> result = (Map<String, Object>) ejecucionInterna("testEjecutarOperacionConsultaDeArticulos: ", entityType, entityData);
       
        logger.info("MULESOFT - FUNCTIONAL_TEST "+ entityType + " END ");

    }

}
