package org.mule.modules.jde.atina.automation.functional;

import java.util.Date;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.junit.Test;
import org.mule.modules.atina.jde.exceptions.ExternalConnectorException;
import org.mule.modules.jde.atina.automation.functional.TestDataBuilder;
import org.mule.modules.jde.atina.automation.model.Configuracion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;

public class GetItemPriceAndAvailabilityWithTokenTestCases extends AbstractTestCases {

    protected final Logger logger = LoggerFactory.getLogger(GetItemPriceAndAvailabilityWithTokenTestCases.class);

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

    @Test
    public void executeValidGetItemPriceAndAvailabilityTest() throws Exception {

        String entityType = TestDataBuilder.getItemPriceAvaEntityType();

        logger.info("MULESOFT - FUNCTIONAL_TEST " + entityType + " BEGIN ");

        // ======================
        // Generate Token
        // ======================

        Configuracion config = new Configuracion();

        config.setUser("JDE");
        config.setPassword("Modus2017!");
        config.setEnvironment("JDV920");
        config.setRole("*ALL");
        config.setSessionId(0);

        String token = getJWT(config);

        // ======================
        // Get Connector Instance
        // ======================

        Map<String, Object> entityData = TestDataBuilder.getItemPriceAvaEntityData();

        entityData.put("JDE Token", token);

        try {

            ejecucionInterna("GetItemPriceAvaEntityData", entityType,
                    entityData);

        } catch (ExternalConnectorException e) {
            logger.error(e.getE1Message());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        logger.info("MULESOFT - FUNCTIONAL_TEST " + entityType + " END ");

    }

    @SuppressWarnings("unused")
    private String getJWT(Configuracion config) {

        long ttlMillis = 0;

        // The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter
                .parseBase64Binary("123456789012345678901234567890123456789012345678901234567890");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        // Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setId("1231231")
                .setIssuedAt(now)
                .setSubject("Subject")
                .setIssuer("Issue")
                .claim("user", config.getUser())
                .claim("password", config.getPassword())
                .claim("environment", config.getEnvironment())
                .claim("role", config.getRole())
                .claim("sessionId", config.getSessionId())
                .signWith(signingKey, signatureAlgorithm);

        return builder.compact();

    }

    @SuppressWarnings("unchecked")
    @Test
    public void getValidGetItemPriceAndAvailabilityJsonTestWithAuth() throws Exception {

        logger.info("MULESOFT - FUNCTIONAL_TEST BEGIN ");

        // ======================
        // Generate Token
        // ======================

        Configuracion config = new Configuracion();

        config.setUser("JDE");
        config.setPassword("Modus2017!");
        config.setEnvironment("JDV920");
        config.setRole("*ALL");
        config.setSessionId(0);

        String token = getJWT(config);

        // ======================
        // Authorization
        // ======================

        String entityTypeAuth = TestDataBuilder.getAuthorizationFromTokenEntityType();

        Map<String, Object> entityDataAuth = TestDataBuilder.getAuthorizationFromTokenEntityData(token);

        Object result = autorizacion(entityTypeAuth, entityDataAuth);

        logger.info("MULESOFT - Token: " + result);

        // ======================
        // Get WS JSON
        // ======================

        String entityType = TestDataBuilder.getItemPriceAvaEntityType();

        Map<String, Object> entityData = TestDataBuilder.getJSONSchemaEntityData((String) (((Map<String, Object>) result).get("token")));

        Map<String, Object> resultItemPrice = null;

        try {
            resultItemPrice = (Map<String, Object>) ejecucionInternaGetJson("GetItemPriceAvaEntityData", entityType,
                    entityData);

        } catch (ExternalConnectorException e) {
            logger.error(e.getE1Message());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        // ======================
        // Logout
        // ======================

        entityTypeAuth = TestDataBuilder.getAuthorizationLogoutEntityType();

        entityDataAuth = TestDataBuilder.getAuthorizationFromTokenEntityData((String) resultItemPrice.get("JDE Token"));

        result = autorizacion(entityTypeAuth, entityDataAuth);

        logger.info("MULESOFT - Token: " + result);

        logger.info("MULESOFT - FUNCTIONAL_TEST END ");

    }

}
