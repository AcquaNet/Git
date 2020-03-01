package org.mule.modules.jde.atina.automation.functional;
  
import java.util.Map;
 
import org.junit.Test;
import org.mule.modules.atina.jde.exceptions.ExternalConnectorException;
import org.mule.modules.jde.atina.automation.functional.TestDataBuilder;
import org.mule.modules.jde.atina.automation.model.Configuracion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

 

public class GetItemPriceWithTokenAndTransactionIDTestCases extends AbstractTestCases {

    protected final Logger logger = LoggerFactory.getLogger(GetItemPriceWithTokenAndTransactionIDTestCases.class);

    @SuppressWarnings({
            "unchecked",
            "unused"
    })
    @Test
    public void executeValidGetItemPriceAndAvailabilityTestWithAuth() throws Exception {

        logger.info("MULESOFT - FUNCTIONAL_TEST BEGIN ");

        // ======================
        // Generate Token
        // ======================

        int sessionID = 0;

        Configuracion config = new Configuracion();

        config.setUser("JDE");
        config.setPassword("Modus2017!");
        config.setEnvironment("JDV920");
        config.setRole("*ALL");
        config.setSessionId(0);

        // String token = getJWT(config);

        // ======================
        // Authorization
        // ======================

        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjMxMjMxIiwiaWF0IjoxNTcyNDQ3Nzc0LCJzdWIiOiJTdWJqZWN0IiwiaXNzIjoiSXNzdWUiLCJ1c2VyIjoiSkRFIiwicGFzc3dvcmQiOiJNb2R1czIwMTchIiwiZW52aXJvbm1lbnQiOiJKRFY5MjAiLCJyb2xlIjoiKkFMTCIsInNlc3Npb25JZCI6MH0.u9olIwWVsyCQnEPz2q0ngr1Kny24RqcRCU23xHFvr3o";

        String entityTypeAuth = TestDataBuilder.getAuthorizationFromTokenEntityType();

        Map<String, Object> entityDataAuth = TestDataBuilder.getAuthorizationFromTokenEntityData(token);

        Object result = autorizacion(entityTypeAuth, entityDataAuth);

        logger.info("MULESOFT - Token: " + result);

        // ======================
        // Invoke WS
        // ======================

        String entityType = TestDataBuilder.getItemPriceAvaEntityType();

        Map<String, Object> entityData = TestDataBuilder.getItemPriceAvaEntityData();

        entityData.put("JDE Token", ((Map<String, Object>) result).get("token"));
        entityData.put("Transaction ID", 20200229180140004L);

        try {
            Map<String, Object> resultItemPrice = (Map<String, Object>) ejecucionInterna("GetItemPriceAvaEntityData",
                    entityType, entityData);

        } catch (ExternalConnectorException e) {
            logger.error(e.getE1Message());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        // ======================
        // Logout
        // ======================

        entityTypeAuth = TestDataBuilder.getAuthorizationLogoutEntityType();

        entityDataAuth = TestDataBuilder.getAuthorizationFromTokenEntityData((String) ((Map<String, Object>) result).get("token"));

        result = autorizacion(entityTypeAuth, entityDataAuth);

        logger.info("MULESOFT - Token: " + ((Map<String, Object>) result).get("token"));

        logger.info("MULESOFT - FUNCTIONAL_TEST END ");

    }

}
