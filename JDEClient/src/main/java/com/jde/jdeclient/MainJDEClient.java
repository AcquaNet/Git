/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jde.jdeclient;

import com.jde.jdeclient.configuracion.Configuracion;
import com.jde.jdeclient.configuracion.ConfiguracionServer;
import com.jde.jdeserverwp.servicios.EjecutarOperacionRequest;
import com.jde.jdeserverwp.servicios.EjecutarOperacionResponse;
import com.jde.jdeserverwp.servicios.EjecutarOperacionValores;
import com.jde.jdeserverwp.servicios.GetJsonsForOperationResponse;
import com.jde.jdeserverwp.servicios.GetMetadataRequest;
import com.jde.jdeserverwp.servicios.GetMetadataResponse;
import com.jde.jdeserverwp.servicios.IsConnectedRequest;
import com.jde.jdeserverwp.servicios.IsConnectedResponse;
import com.jde.jdeserverwp.servicios.JDEServiceGrpc;
import com.jde.jdeserverwp.servicios.JDEServiceGrpc.JDEServiceBlockingStub;
import com.jde.jdeserverwp.servicios.LogoutRequest;
import com.jde.jdeserverwp.servicios.Operacion;
import com.jde.jdeserverwp.servicios.OperacionesRequest;
import com.jde.jdeserverwp.servicios.OperacionesResponse;
import com.jde.jdeserverwp.servicios.SessionRequest;
import com.jde.jdeserverwp.servicios.SessionResponse;
import com.jde.jdeserverwp.servicios.TipoDelParametroInput;
import com.jde.jdeserverwp.servicios.TipoDelParametroOutput;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.internal.DnsNameResolverProvider;
import io.grpc.util.RoundRobinLoadBalancerFactory;
import io.jsonwebtoken.JwtBuilder;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm; 
import java.net.InetAddress;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author jgodi
 */
public class MainJDEClient {

    private static final Logger logger = LoggerFactory.getLogger(MainJDEClient.class);
    
    private static final Boolean testWS_Logout = Boolean.FALSE;
    private static final Boolean testWS_WriteOffProcessingOptions = Boolean.FALSE;
    private static final Boolean testWS_ItemPrice = Boolean.FALSE;
    private static final Boolean testWS_PurchaseOrdersForApprover = Boolean.FALSE;
    private static final Boolean testBSFN = Boolean.FALSE;
    
    private static final Boolean testWS_PurchaseOrdersForApproverGetJson = Boolean.FALSE;
    
    private static final Boolean testWS_PurchaseOrdersForApproverWithToken = Boolean.TRUE;
    

    public void iniciarAplicacion(String[] args) throws Exception {

        ConfiguracionServer configuracion = new ConfiguracionServer();

        //configuracion.setServidorServicio("192.168.99.100"); // Servidor Hub
        //configuracion.setPuertoServicio(8085);
        configuracion.setServidorServicio("c7629931.ngrok.io"); // Servidor Hub
        configuracion.setPuertoServicio(80); // Puerto Servidor Hub

        configuracion.setUser("JDE");
        configuracion.setPassword("Modus2017!");
        configuracion.setEnvironment("JDV920");
        configuracion.setRole("*ALL");
        configuracion.setSession(new Integer(0));
        
        if(testBSFN)
        {
         
            configuracion.setWsConnection(Boolean.FALSE);

            // ===========================  
            // Crear Canal de Comunicacion  
            // ===========================  
            //
            ManagedChannel channel = ManagedChannelBuilder.forAddress(configuracion.getServidorServicio(), configuracion.getPuertoServicio())
                    .usePlaintext()
                    .build();

            // =========================== 
            // Creacion del Stub           
            // ===========================  
            // 
            JDEServiceBlockingStub stub = JDEServiceGrpc.newBlockingStub(channel);

            int sessionID = 0;

            // ===========================  
            // Login                       
            // ===========================  
            //
            try {

                SessionResponse tokenResponse = stub.login(
                        SessionRequest.newBuilder()
                                .setUser(configuracion.getUser())
                                .setPassword(configuracion.getPassword())
                                .setEnvironment(configuracion.getEnvironment())
                                .setRole(configuracion.getRole())
                                .setWsconnection(configuracion.getWsConnection())
                                .build());

                sessionID = (int) tokenResponse.getSessionId();

                System.out.println("Logeado con Session [" + tokenResponse.getSessionId() + "]");

            } catch (Exception ex) {

                logger.error("Error ejecutando metodo ");

                throw new RuntimeException("Error Logeando", null);

            }

            // ===========================  
            // Get Operations                       
            // ===========================  
            //
            try {

                OperacionesResponse operaciones = stub.operaciones(
                        OperacionesRequest.newBuilder()
                                .setConnectorName("BSFN")
                                .setUser(configuracion.getUser())
                                .setPassword(configuracion.getPassword())
                                .setEnvironment(configuracion.getEnvironment())
                                .setRole(configuracion.getRole())
                                .setSessionId(sessionID)
                                .setWsconnection(configuracion.getWsConnection())
                                .build());

                for(Operacion operacion: operaciones.getOperacionesList())
                {
 
                    logger.info("Operacion [" + operacion.getNombreOperacion() + "]");

                }

            } catch (Exception ex) {

                logger.error("Error ejecutando metodo ");

                throw new RuntimeException("Error Logeando", null);

            }

            // ===========================  
            // Get Metadata                       
            // ===========================  
            //
            try {

                GetMetadataResponse operaciones = stub.getMetadaParaOperacion(
                        GetMetadataRequest.newBuilder()
                                .setConnectorName("BSFN")
                                .setUser(configuracion.getUser())
                                .setPassword(configuracion.getPassword())
                                .setEnvironment(configuracion.getEnvironment())
                                .setRole(configuracion.getRole())
                                .setSessionId(sessionID)
                                .setWsconnection(configuracion.getWsConnection())
                                .setOperacionKey("AddressBookMasterMBF")
                                .build());

                for (TipoDelParametroInput parameter : operaciones.getListaDeParametrosInputList()) {

                    logger.info("Operacion [" + parameter.getNombreDelParametro() + "]");  
                    logger.info("Operacion [" + parameter.getTipoDelParametroJava() + "]");  
                     
                }

            } catch (Exception ex) {

                logger.error("Error ejecutando metodo ");

                throw new RuntimeException("Error Logeando", ex);

            }
        
        }
        
        if(testWS_PurchaseOrdersForApprover)
        {
            configuracion.setWsConnection(Boolean.TRUE);

            // ===========================  
            // Crear Canal de Comunicacion  
            // ===========================  
            //
            ManagedChannel channel = ManagedChannelBuilder.forAddress(configuracion.getServidorServicio(), configuracion.getPuertoServicio())
                    .usePlaintext()
                    .build();

            // =========================== 
            // Creacion del Stub           
            // ===========================  
            // 
            JDEServiceBlockingStub stub = JDEServiceGrpc.newBlockingStub(channel);

            int sessionID = 0;

            // ===========================  
            // Login                       
            // ===========================  
            //
            try {

                SessionResponse tokenResponse = stub.login(
                        SessionRequest.newBuilder()
                                .setUser(configuracion.getUser())
                                .setPassword(configuracion.getPassword())
                                .setEnvironment(configuracion.getEnvironment())
                                .setRole(configuracion.getRole())
                                .setWsconnection(configuracion.getWsConnection())
                                .build());

                sessionID = (int) tokenResponse.getSessionId();

                System.out.println("Logeado con Session [" + tokenResponse.getSessionId() + "]");

            } catch (Exception ex) {

                logger.error("Error ejecutando metodo ");

                throw new RuntimeException("Error Logeando", null);

            }

            // ===========================  
            // Get Operations                       
            // ===========================  
            //
            try {

                OperacionesResponse operaciones = stub.operaciones(
                        OperacionesRequest.newBuilder()
                                .setConnectorName("WS")
                                .setUser(configuracion.getUser())
                                .setPassword(configuracion.getPassword())
                                .setEnvironment(configuracion.getEnvironment())
                                .setRole(configuracion.getRole())
                                .setSessionId(sessionID)
                                .setWsconnection(configuracion.getWsConnection())
                                .build());

                for(Operacion operacion: operaciones.getOperacionesList())
                {

                    System.out.println("Operacion [" + operacion.getNombreOperacion() + "]");

                }

            } catch (Exception ex) {

                logger.error("Error ejecutando metodo ");

                throw new RuntimeException("Error Logeando", null);

            }

            // ===========================  
            // Get Metadata                       
            // ===========================  
            //
            try {

                GetMetadataResponse operaciones = stub.getMetadaParaOperacion(
                        GetMetadataRequest.newBuilder()
                                .setConnectorName("WS")
                                .setUser(configuracion.getUser())
                                .setPassword(configuracion.getPassword())
                                .setEnvironment(configuracion.getEnvironment())
                                .setRole(configuracion.getRole())
                                .setSessionId(sessionID)
                                .setWsconnection(configuracion.getWsConnection())
                                .setOperacionKey("oracle.e1.bssv.JP430000.ProcurementManager.getPurchaseOrdersForApprover")
                                .build());

                 logger.info("Input  ");
                 
                for (TipoDelParametroInput parameter : operaciones.getListaDeParametrosInputList()) {
                    
                    int level = 0;

                     printParameter(parameter, level);
                     
                       
                }
                
                 logger.info("Output  ");
                
                for (TipoDelParametroOutput parameter : operaciones.getListaDeParametrosOutputList()) {

                    int level = 0;

                     printParameterOutput(parameter, level);

                }

            } catch (Exception ex) {

                logger.error("Error ejecutando metodo ");

                throw new RuntimeException("Error Logeando", ex);

            }
            
            // ===========================  
            // Invoke WS              
            // ===========================  
            // 
            
            EjecutarOperacionValores.Builder entityId = EjecutarOperacionValores.newBuilder();
            entityId.setNombreDelParametro("entityId");
            entityId.setValueAsInteger(533095);

            EjecutarOperacionValores.Builder approver = EjecutarOperacionValores.newBuilder();
            approver.setNombreDelParametro("approver");
            approver.addListaDeValores(entityId);
            
            EjecutarOperacionValores.Builder orderTypeCode = EjecutarOperacionValores.newBuilder();
            orderTypeCode.setNombreDelParametro("orderTypeCode");
            orderTypeCode.setValueAsString("OP");
            
            EjecutarOperacionValores.Builder businessUnitCode = EjecutarOperacionValores.newBuilder();
            businessUnitCode.setNombreDelParametro("businessUnitCode");
            businessUnitCode.setValueAsString("         30");
            
            EjecutarOperacionValores.Builder statusCodeNext = EjecutarOperacionValores.newBuilder();
            statusCodeNext.setNombreDelParametro("statusCodeNext");
            statusCodeNext.setValueAsString("230");
            
            EjecutarOperacionValores.Builder statusApproval = EjecutarOperacionValores.newBuilder();
            statusApproval.setNombreDelParametro("statusApproval");
            statusApproval.setValueAsString("2N");
             
            EjecutarOperacionResponse ejecutarOperacionesResponse = stub.ejecutarOperacion(
                    EjecutarOperacionRequest.newBuilder()
                            .setConnectorName("WS")
                            .setOperacionKey("oracle.e1.bssv.JP430000.ProcurementManager.getPurchaseOrdersForApprover")
                            .setUser(configuracion.getUser())
                            .setPassword(configuracion.getPassword())
                            .setEnvironment(configuracion.getEnvironment())
                            .setRole(configuracion.getRole())
                            .setWsconnection(configuracion.getWsConnection())
                            .setSessionId(sessionID)
                            .addListaDeValores(approver.build())
                            .addListaDeValores(orderTypeCode.build())
                            .addListaDeValores(businessUnitCode.build())
                            .addListaDeValores(statusCodeNext.build())
                            .addListaDeValores(statusApproval.build())
                            .build());

            ejecutarOperacionesResponse.toString();
            
            
            
            
        }
        
        if(testWS_ItemPrice)
        {
            
            String operationKey = "oracle.e1.bssv.JP410000.InventoryManager.getItemPrice";
            
            configuracion.setWsConnection(Boolean.TRUE);

            // ===========================  
            // Crear Canal de Comunicacion  
            // ===========================  
            //
            ManagedChannel channel = ManagedChannelBuilder.forAddress(configuracion.getServidorServicio(), configuracion.getPuertoServicio())
                    .usePlaintext()
                    .build();

            // =========================== 
            // Creacion del Stub           
            // ===========================  
            // 
            JDEServiceBlockingStub stub = JDEServiceGrpc.newBlockingStub(channel);

            int sessionID = 0;

            // ===========================  
            // Login                       
            // ===========================  
            //
            try {

                SessionResponse tokenResponse = stub.login(
                        SessionRequest.newBuilder()
                                .setUser(configuracion.getUser())
                                .setPassword(configuracion.getPassword())
                                .setEnvironment(configuracion.getEnvironment())
                                .setRole(configuracion.getRole())
                                .setWsconnection(configuracion.getWsConnection())
                                .build());

                sessionID = (int) tokenResponse.getSessionId();

                System.out.println("Logeado con Session [" + tokenResponse.getSessionId() + "]");

            } catch (Exception ex) {

                logger.error("Error ejecutando metodo ");

                throw new RuntimeException("Error Logeando", null);

            }

            // ===========================  
            // Get Operations                       
            // ===========================  
            //
            try {

                OperacionesResponse operaciones = stub.operaciones(
                        OperacionesRequest.newBuilder()
                                .setConnectorName("WS")
                                .setUser(configuracion.getUser())
                                .setPassword(configuracion.getPassword())
                                .setEnvironment(configuracion.getEnvironment())
                                .setRole(configuracion.getRole())
                                .setSessionId(sessionID)
                                .setWsconnection(configuracion.getWsConnection())
                                .build());

                for(Operacion operacion: operaciones.getOperacionesList())
                {

                    System.out.println("Operacion [" + operacion.getNombreOperacion() + "]");

                }

            } catch (Exception ex) {

                logger.error("Error ejecutando metodo ");

                throw new RuntimeException("Error Logeando", null);

            }

            // ===========================  
            // Get Metadata                       
            // ===========================  
            //
            try {

                GetMetadataResponse operaciones = stub.getMetadaParaOperacion(
                        GetMetadataRequest.newBuilder()
                                .setConnectorName("WS")
                                .setUser(configuracion.getUser())
                                .setPassword(configuracion.getPassword())
                                .setEnvironment(configuracion.getEnvironment())
                                .setRole(configuracion.getRole())
                                .setSessionId(sessionID)
                                .setWsconnection(configuracion.getWsConnection())
                                .setOperacionKey(operationKey)
                                .build());

                 logger.info("Input  ");
                 
                for (TipoDelParametroInput parameter : operaciones.getListaDeParametrosInputList()) {
                    
                    int level = 0;

                     printParameter(parameter, level);
                     
                       
                }
                
                 logger.info("Output  ");
                
                for (TipoDelParametroOutput parameter : operaciones.getListaDeParametrosOutputList()) {

                    int level = 0;

                     printParameterOutput(parameter, level);

                }

            } catch (Exception ex) {

                logger.error("Error ejecutando metodo ");

                throw new RuntimeException("Error Logeando", ex);

            }
            
            // ===========================  
            // Invoke WS              
            // ===========================  
            // 
            
            EjecutarOperacionValores.Builder itemId = EjecutarOperacionValores.newBuilder();
            itemId.setNombreDelParametro("itemId");
            itemId.setValueAsInteger(60003);

            EjecutarOperacionValores.Builder item = EjecutarOperacionValores.newBuilder();
            item.setNombreDelParametro("item");
            item.addListaDeValores(itemId);
            
            EjecutarOperacionValores.Builder branchPlantList = EjecutarOperacionValores.newBuilder();
            branchPlantList.setNombreDelParametro("branchPlantList");
            branchPlantList.setValueAsString("          10");
             
            EjecutarOperacionResponse ejecutarOperacionesResponse = stub.ejecutarOperacion(
                    EjecutarOperacionRequest.newBuilder()
                            .setConnectorName("WS")
                            .setOperacionKey(operationKey)
                            .setUser(configuracion.getUser())
                            .setPassword(configuracion.getPassword())
                            .setEnvironment(configuracion.getEnvironment())
                            .setRole(configuracion.getRole())
                            .setWsconnection(configuracion.getWsConnection())
                            .setSessionId(sessionID)
                            .addListaDeValores(item.build())
                            .addListaDeValores(branchPlantList.build())
                            .build()); 
            
            logger.info("Resonpse  ");
            logger.info(ejecutarOperacionesResponse.toString());
             
        }
        
        if(testWS_WriteOffProcessingOptions)
        {
            
            String operationKey = "oracle.e1.bssv.JP000040.FinancialComplianceManager.getWriteOffProcessingOptions";
            
            configuracion.setWsConnection(Boolean.TRUE);

            // ===========================  
            // Crear Canal de Comunicacion  
            // ===========================  
            //
            ManagedChannel channel = ManagedChannelBuilder.forAddress(configuracion.getServidorServicio(), configuracion.getPuertoServicio())
                    .usePlaintext()
                    .build();

            // =========================== 
            // Creacion del Stub           
            // ===========================  
            // 
            JDEServiceBlockingStub stub = JDEServiceGrpc.newBlockingStub(channel);

            int sessionID = 0;

            // ===========================  
            // Login                       
            // ===========================  
            //
            try {

                SessionResponse tokenResponse = stub.login(
                        SessionRequest.newBuilder()
                                .setUser(configuracion.getUser())
                                .setPassword(configuracion.getPassword())
                                .setEnvironment(configuracion.getEnvironment())
                                .setRole(configuracion.getRole())
                                .setWsconnection(configuracion.getWsConnection())
                                .build());

                sessionID = (int) tokenResponse.getSessionId();

                System.out.println("Logeado con Session [" + tokenResponse.getSessionId() + "]");

            } catch (Exception ex) {

                logger.error("Error ejecutando metodo ");

                throw new RuntimeException("Error Logeando", null);

            }

            // ===========================  
            // Get Operations                       
            // ===========================  
            //
            try {

                OperacionesResponse operaciones = stub.operaciones(
                        OperacionesRequest.newBuilder()
                                .setConnectorName("WS")
                                .setUser(configuracion.getUser())
                                .setPassword(configuracion.getPassword())
                                .setEnvironment(configuracion.getEnvironment())
                                .setRole(configuracion.getRole())
                                .setSessionId(sessionID)
                                .setWsconnection(configuracion.getWsConnection())
                                .build());

                for(Operacion operacion: operaciones.getOperacionesList())
                {

                    System.out.println("Operacion [" + operacion.getNombreOperacion() + "]");

                }

            } catch (Exception ex) {

                logger.error("Error ejecutando metodo ");

                throw new RuntimeException("Error Logeando", null);

            }

            // ===========================  
            // Get Metadata                       
            // ===========================  
            //
            try {

                GetMetadataResponse operaciones = stub.getMetadaParaOperacion(
                        GetMetadataRequest.newBuilder()
                                .setConnectorName("WS")
                                .setUser(configuracion.getUser())
                                .setPassword(configuracion.getPassword())
                                .setEnvironment(configuracion.getEnvironment())
                                .setRole(configuracion.getRole())
                                .setSessionId(sessionID)
                                .setWsconnection(configuracion.getWsConnection())
                                .setOperacionKey(operationKey)
                                .build());

                 logger.info("Input  ");
                 
                for (TipoDelParametroInput parameter : operaciones.getListaDeParametrosInputList()) {
                    
                    int level = 0;

                     printParameter(parameter, level);
                     
                       
                }
                
                 logger.info("Output  ");
                
                for (TipoDelParametroOutput parameter : operaciones.getListaDeParametrosOutputList()) {

                    int level = 0;

                     printParameterOutput(parameter, level);

                }

            } catch (Exception ex) {

                logger.error("Error ejecutando metodo ");

                throw new RuntimeException("Error Logeando", ex);

            }
            
            // ===========================  
            // Invoke WS              
            // ===========================  
            // 
           
            
            EjecutarOperacionValores.Builder processingVersionStandardReceiptEntryZJDE00001 = EjecutarOperacionValores.newBuilder();
            processingVersionStandardReceiptEntryZJDE00001.setNombreDelParametro("");
            processingVersionStandardReceiptEntryZJDE00001.setValueAsString("ZJDE0001");
            
            EjecutarOperacionValores.Builder processingVersionStandardReceiptEntry = EjecutarOperacionValores.newBuilder();
            processingVersionStandardReceiptEntry.setNombreDelParametro("processingVersionStandardReceiptEntry");
            processingVersionStandardReceiptEntry.addListaDeValores(processingVersionStandardReceiptEntryZJDE00001.build());
             
            EjecutarOperacionResponse ejecutarOperacionesResponse = stub.ejecutarOperacion(
                    EjecutarOperacionRequest.newBuilder()
                            .setConnectorName("WS")
                            .setOperacionKey(operationKey)
                            .setUser(configuracion.getUser())
                            .setPassword(configuracion.getPassword())
                            .setEnvironment(configuracion.getEnvironment())
                            .setRole(configuracion.getRole())
                            .setWsconnection(configuracion.getWsConnection())
                            .setSessionId(sessionID)
                            .addListaDeValores(processingVersionStandardReceiptEntry.build())
                            .build()); 
            
            logger.info("Resonpse  ");
            logger.info(ejecutarOperacionesResponse.toString());
             
        }
        
        if(testWS_Logout)
        {
              
            configuracion.setWsConnection(Boolean.TRUE);

            // ===========================  
            // Crear Canal de Comunicacion  
            // ===========================  
            //
            ManagedChannel channel = ManagedChannelBuilder.forAddress(configuracion.getServidorServicio(), configuracion.getPuertoServicio())
                    .usePlaintext()
                    .build();

            // =========================== 
            // Creacion del Stub           
            // ===========================  
            // 
            JDEServiceBlockingStub stub = JDEServiceGrpc.newBlockingStub(channel);

            int sessionID = 0;
            int sessionIDOld = 0;

            // ===========================  
            // Login                       
            // ===========================  
            //
            try {

                SessionResponse tokenResponse = stub.login(
                        SessionRequest.newBuilder()
                                .setUser(configuracion.getUser())
                                .setPassword(configuracion.getPassword())
                                .setEnvironment(configuracion.getEnvironment())
                                .setRole(configuracion.getRole())
                                .setWsconnection(configuracion.getWsConnection())
                                .build());

                sessionID = (int) tokenResponse.getSessionId();
                
                sessionIDOld = sessionID;

                System.out.println("Logeado con Session [" + tokenResponse.getSessionId() + "]");

            } catch (Exception ex) {

                logger.error("Error ejecutando metodo ");

                throw new RuntimeException("Error Logeando", null);

            }
            
            // ===========================  
            // Is Connected                       
            // ===========================  
            //
            
            Boolean isConnected = Boolean.FALSE;
            
            try {

                IsConnectedResponse tokenResponse = stub.isConnected(
                        IsConnectedRequest.newBuilder()
                                .setSessionId(sessionID)
                                .setWsconnection(configuracion.getWsConnection())
                                .build());

                isConnected =  tokenResponse.getConnected();

                System.out.println("Logeado con Session [" + sessionID + "] ? " + isConnected);

            } catch (Exception ex) {

                logger.error("Error ejecutando metodo ");

                throw new RuntimeException("Error Logeando", null);

            }
            
            // ===========================  
            // Logout                   
            // ===========================  
            //
            
            try {

                SessionResponse tokenResponse = stub.logout(
                        LogoutRequest.newBuilder()
                                .setSessionId(sessionID)
                                .setWsconnection(configuracion.getWsConnection())
                                .build());

                sessionID =  (int) tokenResponse.getSessionId();

                System.out.println("Logeado con Session [" + sessionID + "] ? " + isConnected);

            } catch (Exception ex) {

                logger.error("Error ejecutando metodo ");

                throw new RuntimeException("Error Logeando", null);

            }
            
            // ===========================  
            // Is Connected                       
            // ===========================  
            //
            
            isConnected = Boolean.FALSE;
            
            try {

                IsConnectedResponse tokenResponse = stub.isConnected(
                        IsConnectedRequest.newBuilder()
                                .setSessionId(sessionIDOld)
                                .setWsconnection(configuracion.getWsConnection())
                                .build());

                isConnected =  tokenResponse.getConnected();

                System.out.println("Logout con Session [" + sessionID + "] ");

            } catch (Exception ex) {

                logger.error("Error ejecutando metodo ");

                throw new RuntimeException("Error Logeando", null);

            }
            
            
        }
        
        if(testWS_PurchaseOrdersForApproverGetJson)
        {
            
            configuracion.setWsConnection(Boolean.TRUE);

            // ===========================  
            // Crear Canal de Comunicacion  
            // ===========================  
            //
            ManagedChannel channel = ManagedChannelBuilder.forAddress(configuracion.getServidorServicio(), configuracion.getPuertoServicio())
                    .usePlaintext()
                    .build();

            // =========================== 
            // Creacion del Stub           
            // ===========================  
            // 
            JDEServiceBlockingStub stub = JDEServiceGrpc.newBlockingStub(channel);

            int sessionID = 0;
            
            // ===========================  
            // Generate JWT                       
            // ===========================  
            //
            
            long ttlMillis = 0;
            
             //The JWT signature algorithm we will be using to sign the token
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
            
            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);
            
            
            //We will sign our JWT with our ApiKey secret 
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("123456789012345678901234567890123456789012345678901234567890");
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
            
            
            //Let's set the JWT Claims
            JwtBuilder builder = Jwts.builder().setId("1231231")
                                .setIssuedAt(now)
                                .setSubject("Subject")
                                .setIssuer("Issue")
                                .claim("user", configuracion.getUser())
                                .claim("password", configuracion.getPassword())
                                .claim("environment", configuracion.getEnvironment())
                                .claim("role", configuracion.getRole()) 
                                .claim("sessionId", sessionID) 
                                .signWith(signingKey, signatureAlgorithm);
            
            
            //if it has been specified, let's add the expiration
            if (ttlMillis >= 0) {
                long expMillis = nowMillis + ttlMillis;
                Date exp = new Date(expMillis);
                builder.setExpiration(exp);
            }
            
            String token =  builder.compact();


            // ===========================  
            // Login                       
            // ===========================  
            //
            try {

                SessionResponse tokenResponse = stub.login(
                        SessionRequest.newBuilder()
                                .setUser(configuracion.getUser())
                                .setPassword(configuracion.getPassword())
                                .setEnvironment(configuracion.getEnvironment())
                                .setRole(configuracion.getRole()) 
                                .setWsconnection(configuracion.getWsConnection())
                                .setJwtToken(token)
                                .build());

                sessionID = (int) tokenResponse.getSessionId();

                System.out.println("Logeado con Session [" + tokenResponse.getSessionId() + "]");

            } catch (Exception ex) {

                logger.error("Error ejecutando metodo ");

                throw new RuntimeException("Error Logeando", null);

            } 

            // ===========================  
            // Get Metadata                       
            // ===========================  
            //
            try {

                GetJsonsForOperationResponse operaciones = stub.getJsonsForOperation(
                        GetMetadataRequest.newBuilder()
                                .setConnectorName("WS")
                                .setUser(configuracion.getUser())
                                .setPassword(configuracion.getPassword())
                                .setEnvironment(configuracion.getEnvironment())
                                .setRole(configuracion.getRole())
                                .setSessionId(sessionID)
                                .setWsconnection(configuracion.getWsConnection())
                                .setOperacionKey("oracle.e1.bssv.JP430000.ProcurementManager.getPurchaseOrdersForApprover")
                                .build());

                 logger.info("Input  ");
                 
                 logger.info(operaciones.getInputAsJson());
                 
                 logger.info("Output  ");
                 
                 logger.info(operaciones.getOutputAsJson());
                
                 

            } catch (Exception ex) {

                logger.error("Error ejecutando metodo ");

                throw new RuntimeException("Error Logeando", ex);

            } 
            
            
            
        }
        
        if(testWS_PurchaseOrdersForApproverWithToken)
        {
            configuracion.setWsConnection(Boolean.TRUE);

            configuracion.setServidorServicio("fedfaa75.ngrok.io"); // Servidor Hub
            configuracion.setPuertoServicio(80); // Puerto Servidor Hub
        
            // ===========================
            // Crear Canal de Comunicacion
            // ===========================  
            //
            InetAddress ipJDEServer = InetAddress.getByName(configuracion.getServidorServicio());
            
            byte[] ip = ipJDEServer.getAddress();

              String ipS = getIPString(ip);
             
            ManagedChannel channel = ManagedChannelBuilder.forAddress(configuracion.getServidorServicio(), configuracion.getPuertoServicio())
                    .usePlaintext()
                    .nameResolverFactory(new DnsNameResolverProvider())  // this is on by default
                    .build();
            
             

            // =========================== 
            // Creacion del Stub           
            // ===========================  
            // 
            JDEServiceBlockingStub stub = JDEServiceGrpc.newBlockingStub(channel);

            int sessionID = 0;
            
            Configuracion config = new Configuracion();
        
            config.setUser(configuracion.getUser());

            config.setPassword(configuracion.getPassword());

            config.setEnvironment(configuracion.getEnvironment());

            config.setRole(configuracion.getRole());

            config.setSessionId((int) sessionID);
            
            config.setTokenExpiration(0L);
             
            String token = getJWT(config);

            // ===========================  
            // Login                       
            // ===========================  
            //
            try {

                SessionResponse tokenResponse = stub.login(
                        SessionRequest.newBuilder()
                                .setUser("")
                                .setPassword("")
                                .setEnvironment("")
                                .setRole("")
                                .setJwtToken(token)
                                .setWsconnection(configuracion.getWsConnection())
                                .build());

                sessionID = (int) tokenResponse.getSessionId();
                
                token = tokenResponse.getJwtToken();

                System.out.println("Logeado con Session [" + tokenResponse.getSessionId() + "]");

            } catch (Exception ex) {

                logger.error("Error ejecutando metodo " + ex.getMessage());

                throw new RuntimeException("Error Logeando", null);

            }

            // ===========================  
            // Get Operations                       
            // ===========================  
            //
            try {

                OperacionesResponse operaciones = stub.operaciones(
                        OperacionesRequest.newBuilder()
                                .setConnectorName("WS")
                                .setUser("")
                                .setPassword("")
                                .setEnvironment("")
                                .setRole("")
                                .setSessionId(0)
                                .setJwtToken(token)
                                .setWsconnection(configuracion.getWsConnection())
                                .build());
                
                token = operaciones.getJwtToken();

                for(Operacion operacion: operaciones.getOperacionesList())
                {

                    System.out.println("Operacion [" + operacion.getNombreOperacion() + "]");

                }

            } catch (Exception ex) {

                logger.error("Error ejecutando metodo ");

                throw new RuntimeException("Error Logeando", null);

            }

            // ===========================  
            // Get Metadata                       
            // ===========================  
            //
            try {

                GetMetadataResponse operaciones = stub.getMetadaParaOperacion(
                        GetMetadataRequest.newBuilder()
                                .setConnectorName("WS")
                                .setUser("")
                                .setPassword("")
                                .setEnvironment("")
                                .setRole("")
                                .setSessionId(0)
                                .setJwtToken(token)
                                .setWsconnection(configuracion.getWsConnection())
                                .setOperacionKey("oracle.e1.bssv.JP430000.ProcurementManager.getPurchaseOrdersForApprover")
                                .build());
                
                token = operaciones.getJwtToken();

                 logger.info("Input  ");
                 
                for (TipoDelParametroInput parameter : operaciones.getListaDeParametrosInputList()) {
                    
                    int level = 0;

                     printParameter(parameter, level);
                     
                       
                }
                
                 logger.info("Output  ");
                
                for (TipoDelParametroOutput parameter : operaciones.getListaDeParametrosOutputList()) {

                    int level = 0;

                     printParameterOutput(parameter, level);

                }

            } catch (Exception ex) {

                logger.error("Error ejecutando metodo ");

                throw new RuntimeException("Error Logeando", ex);

            }
            
            // ===========================  
            // Invoke WS              
            // ===========================  
            // 
            
            EjecutarOperacionValores.Builder entityId = EjecutarOperacionValores.newBuilder();
            entityId.setNombreDelParametro("entityId");
            entityId.setValueAsInteger(533095);

            EjecutarOperacionValores.Builder approver = EjecutarOperacionValores.newBuilder();
            approver.setNombreDelParametro("approver");
            approver.addListaDeValores(entityId);
            
            EjecutarOperacionValores.Builder orderTypeCode = EjecutarOperacionValores.newBuilder();
            orderTypeCode.setNombreDelParametro("orderTypeCode");
            orderTypeCode.setValueAsString("OP");
            
            EjecutarOperacionValores.Builder businessUnitCode = EjecutarOperacionValores.newBuilder();
            businessUnitCode.setNombreDelParametro("businessUnitCode");
            businessUnitCode.setValueAsString("         30");
            
            EjecutarOperacionValores.Builder statusCodeNext = EjecutarOperacionValores.newBuilder();
            statusCodeNext.setNombreDelParametro("statusCodeNext");
            statusCodeNext.setValueAsString("230");
            
            EjecutarOperacionValores.Builder statusApproval = EjecutarOperacionValores.newBuilder();
            statusApproval.setNombreDelParametro("statusApproval");
            statusApproval.setValueAsString("2N");
             
            EjecutarOperacionResponse ejecutarOperacionesResponse = stub.ejecutarOperacion(
                    EjecutarOperacionRequest.newBuilder()
                            .setConnectorName("WS")
                            .setOperacionKey("oracle.e1.bssv.JP430000.ProcurementManager.getPurchaseOrdersForApprover")
                            .setUser("")
                            .setPassword("")
                            .setEnvironment("")
                            .setRole("")
                            .setWsconnection(configuracion.getWsConnection())
                            .setSessionId(0)
                            .setJwtToken(token)
                            .addListaDeValores(approver.build())
                            .addListaDeValores(orderTypeCode.build())
                            .addListaDeValores(businessUnitCode.build())
                            .addListaDeValores(statusCodeNext.build())
                            .addListaDeValores(statusApproval.build())
                            .build());

            token = ejecutarOperacionesResponse.getJwtToken();
            
            ejecutarOperacionesResponse.toString();
             
            
        }

    }
    
    private String getJWT(Configuracion config) {

        long ttlMillis = config.getTokenExpiration();

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret 
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("123456789012345678901234567890123456789012345678901234567890");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId("1231231")
                .setIssuedAt(now)
                .setSubject("Subject")
                .setIssuer("Issue")
                .claim("user", config.getUser())
                .claim("password", config.getPassword())
                .claim("environment", config.getEnvironment())
                .claim("role", config.getRole())
                .claim("sessionId", config.getSessionId())
                .signWith(signingKey, signatureAlgorithm);
        
        //if it has been specified, let's add the expiration
        if (ttlMillis > 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        return builder.compact();
         
    }
    
    
    
    private void printParameter(TipoDelParametroInput parameter, int level)
    {
        level++;
        
        String space = StringUtils.repeat(".", level * 2);
         
        logger.info(space + "Parameter Name: [" + parameter.getNombreDelParametro() + "]" + " Type [" + parameter.getTipoDelParametroJava() + "] Repeated: " + parameter.getRepeatedParameter());
        
         
                
            for(TipoDelParametroInput input:parameter.getSubParametroList())
            {
                if(!input.getNombreDelParametro().isEmpty())
                {
                    printParameter(input, level);
                }
                
            }
          
        
    }
    
    private void printParameterOutput(TipoDelParametroOutput parameter, int level) {
        level++;

        String space = StringUtils.repeat(".", level * 2);

        logger.info(space + "Parameter Name: [" + parameter.getNombreDelParametro() + "]" + " Type [" + parameter.getTipoDelParametroJava() + "] Repeated: " + parameter.getRepeatedParameter());

        for (TipoDelParametroOutput input : parameter.getSubParametroList()) {
            if (!input.getNombreDelParametro().isEmpty()) {
                printParameterOutput(input, level);
            }
        }

    }

    public static void main(String[] args) {

        try {

            MainJDEClient mainApp = new MainJDEClient();

            mainApp.iniciarAplicacion(args);

        } catch (Exception ex) {

            logger.info("Error. No se pudo iniciar la aplicacion:");
            logger.info("     " + ex.getMessage());
            logger.info("Ver log para mas detalle");
            logger.error(ex.getMessage(), ex);

        }

    }
    
    private String getIPString(byte[] ipInBytes) {

        StringBuilder ipSB = new StringBuilder();

        int temp = 0;

        for (int i = 0, j = ipInBytes.length; i < j; i++) {

            temp = (int)(ipInBytes[i] & 255);

            if (i != 3) {
                ipSB.append(temp)
                    .append(".");
            } else {
                ipSB.append(temp);
            }
        }

        logger.info("IP: " + ipSB.toString());
        return ipSB.toString();
    }

}
