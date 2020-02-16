/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acqua.jde.jdeconnector.services;


import com.acqua.atina.jdeconnector.internal.model.JDEBsfnParameter;
import com.acqua.atina.jdeconnector.internal.model.metadata.ParameterTypeObject;
import com.acqua.atina.jdeconnector.internal.model.metadata.ParameterTypeSimple;
import com.acqua.atina.jdeconnectorservice.exception.JDESingleConnectionException; 
import com.acqua.atina.jdeconnectorservice.exception.JDESingleWSException;
import com.acqua.atina.jdeconnectorservice.service.poolconnection.JDEPoolConnections;
import com.acqua.atina.jdeconnectorservice.service.JDESingleConnection;
import com.acqua.atina.jdeconnectorservice.service.poolconnection.JDEConnection;
import com.acqua.atina.jdeconnectorservice.wsservice.JDESingleWSConnection;
import com.acqua.jde.jdeconnectorserver.JDEConnectorServer;
import com.acqua.jde.jdeconnectorserver.configuration.ServerConfiguration;
import com.acqua.jde.jdeconnectorserver.model.Configuracion;
import com.google.protobuf.ByteString;
import com.google.protobuf.Timestamp; 
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.jde.jdeserverwp.servicios.EjecutarOperacionResponse;
import com.jde.jdeserverwp.servicios.EjecutarOperacionValores;
import com.jde.jdeserverwp.servicios.GetJsonsForOperationResponse;
import com.jde.jdeserverwp.servicios.GetMetadataResponse;
import com.jde.jdeserverwp.servicios.IsConnectedResponse;
import com.jde.jdeserverwp.servicios.JDEServiceGrpc;
import com.jde.jdeserverwp.servicios.Operacion;
import com.jde.jdeserverwp.servicios.OperacionesResponse;
import com.jde.jdeserverwp.servicios.SessionResponse; 
import com.jde.jdeserverwp.servicios.TipoDelParametroInput;
import com.jde.jdeserverwp.servicios.TipoDelParametroOutput;
import com.jdedwards.base.datatypes.SqlDate;
import io.grpc.Status;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set; 
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.reflect.FieldUtils;
import java.lang.reflect.Field;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 * https://github.com/saturnism/grpc-java-by-example/blob/master/simple-grpc-server/src/main/java/com/example/grpc/server/MyGrpcServer.java
 *
 * @author jgodi
 */
public class JDEServiceImpl extends JDEServiceGrpc.JDEServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(JDEConnectorServer.class);

    private static String SEPARADOR_CLASE_OPERACION = "_";
    private static String DIR_INSTALACION = "service-files";
    private static String SERVICES_DIR_SUFFIX = "-files";
    private static String METADATA = "metadata"; 
    
    private static String TIPO_BSFN = "BSFN"; 
    private static String TIPO_WS = "WS"; 
    
    private String userDir;
    private File directorioDeInstalacion; 

    private String claseDeLaOperacion = "";
    private String metodoDeLaOperacion = "";
    
    private ServerConfiguration configuracion;
    
    private int counter;
     

    public JDEServiceImpl() {

        logger.info("Iniciando JDE Service Impl...");

        userDir = System.getProperty("user.dir");

        directorioDeInstalacion = new File(userDir + File.separator + DIR_INSTALACION);
  

    }
    
    @Override
    public void login(com.jde.jdeserverwp.servicios.SessionRequest request,
            io.grpc.stub.StreamObserver<com.jde.jdeserverwp.servicios.SessionResponse> responseObserver) {

        
        logger.info("-------------------------------------------------------------------------------");
        logger.info("JDE Login: Begin Login");
        logger.info("Atina Transaction ID: " + Long.toString(request.getTransactionID()));
        
        // -----------------------------------------
        // Generar Session
        // -----------------------------------------
        //
        
        Configuracion config = new Configuracion();
        
        config.setUser(request.getUser());

        config.setPassword(request.getPassword());

        config.setEnvironment(request.getEnvironment());

        config.setRole(request.getRole());

        config.setSessionId((int) request.getSessionId());
        
        config.setTokenExpiration(configuracion.getTokenExpiration());
          
        logger.info("              Request Received: " + config.toString());
         
        try {
    
            // -----------------------------------------
            // Process JWT Token
            // -----------------------------------------
            //
              
            if(!request.getJwtToken().isEmpty())
            {
             
                config = getConfigFromJWT(request.getJwtToken()); 
                
                logger.info("              Token converted: " + config.toString());
                   
            }
 
            int sessionID = JDEPoolConnections.getInstance().createConnection(  config.getUser(), 
                                                                                config.getPassword(), 
                                                                                config.getEnvironment(), 
                                                                                config.getRole(), 
                                                                                config.getSessionIdAsInt(),
                                                                                request.getWsconnection());
            
            config.setSessionId(sessionID);
            
            logger.info("              Session ID: " + sessionID);
            
            SessionResponse.Builder responseBuilder = SessionResponse.newBuilder();
            
            responseBuilder.setSessionId(sessionID);
            
            responseBuilder.setJwtToken(getJWT(config)); 
             
            responseObserver.onNext(responseBuilder.build());

            responseObserver.onCompleted();
            
        } catch (JDESingleConnectionException ex) {
            
            
            String msg = "Error WS Server: " + ex.getMessage();
            logger.error(msg, ex);
            logger.info("-------------------------------------------------------------------------------");
             
            StringBuilder sb = new StringBuilder();
            sb.append("Error Creating Connection");
            sb.append("|");
            sb.append(ex.getMessage());
            sb.append("|%ExternalServiceException%");
 
            responseObserver.onError(Status.INTERNAL
                    .withDescription(sb.toString())
                    .withCause(ex)
                    .asRuntimeException());
 
      
        } catch (Exception ex) {
            
            String msg = "Error WS Server: " + ex.getMessage();
            logger.error(msg, ex);
            logger.info("-------------------------------------------------------------------------------");
            
            StringBuilder sb = new StringBuilder();
            sb.append("Error Creating Connection");
            sb.append("|");
            sb.append(ex.getMessage());
            sb.append("|%ServiceServerException%");
 
            responseObserver.onError(Status.INTERNAL
                    .withDescription(sb.toString())
                    .withCause(ex)
                    .asRuntimeException());
             
        } 
 
        logger.info("JDE Login: End Login");
        logger.info("-------------------------------------------------------------------------------");

    } 
    
    private Configuracion getConfigFromJWT(String jwtToken) {
        
        logger.info("          Get Config From JWT: " + jwtToken);
        
        Configuracion config = new Configuracion();
        
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(configuracion.getSecretKey()))
                .parseClaimsJws(jwtToken).getBody();

        config.setUser(claims.get("user", String.class));

        config.setPassword(claims.get("password", String.class));

        config.setEnvironment(claims.get("environment", String.class));

        config.setRole(claims.get("role", String.class));
 
        config.setSessionId(claims.get("sessionId", Integer.class));
        
        config.setTokenExpiration(this.configuracion.getTokenExpiration());
        
        logger.info("          Configuration Received: " + config.toString());
        
        return config;
        
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
    
    @Override
    public void logout(com.jde.jdeserverwp.servicios.LogoutRequest request,
            io.grpc.stub.StreamObserver<com.jde.jdeserverwp.servicios.SessionResponse> responseObserver) {

        logger.info("-------------------------------------------------------------------------------");
        logger.info("JDE Logout: Begin");
        logger.info("Atina Transaction ID: " + Long.toString(request.getTransactionID()));
        
        Configuracion config = new Configuracion();
        
        config.setUser("");

        config.setPassword("");

        config.setEnvironment("");

        config.setRole("");

        config.setSessionId((int) request.getSessionId());
        
        config.setTokenExpiration(configuracion.getTokenExpiration());
        
          
        // -----------------------------------------
        // Generar Session
        // -----------------------------------------
        //
         
        try {
            
            if(!request.getJwtToken().isEmpty())
            {
             
                config = getConfigFromJWT(request.getJwtToken()); 
                   
            }
 
            JDEPoolConnections.getInstance().disconnect(config.getSessionId());
             
            SessionResponse response = SessionResponse.newBuilder().setSessionId(0).build();
   
            responseObserver.onNext(response);

            responseObserver.onCompleted();
            
        } catch (JDESingleConnectionException ex) {
             
            String msg = "Error WS Server: " + ex.getMessage();
            logger.error(msg, ex);
            logger.info("-------------------------------------------------------------------------------");
            
            StringBuilder sb = new StringBuilder();
            sb.append("Error with Logout");
            sb.append("|");
            sb.append(ex.getMessage());
            sb.append("|%ExternalServiceException%");
 
            responseObserver.onError(Status.INTERNAL
                    .withDescription(sb.toString())
                    .withCause(ex)
                    .asRuntimeException());
 
      
        } catch (Exception ex) {
            
            String msg = "Error WS Server: " + ex.getMessage();
            logger.error(msg, ex);
            logger.info("-------------------------------------------------------------------------------");
            
            StringBuilder sb = new StringBuilder();
            sb.append("Error with Logout");
            sb.append("|");
            sb.append(ex.getMessage());
            sb.append("|%ServiceServerException%");
 
            responseObserver.onError(Status.INTERNAL
                    .withDescription(sb.toString())
                    .withCause(ex)
                    .asRuntimeException());
             
        } 

        logger.info("JDE Logout: End");
        logger.info("-------------------------------------------------------------------------------");

    } 
    
    @Override
    public void isConnected(com.jde.jdeserverwp.servicios.IsConnectedRequest request,
            io.grpc.stub.StreamObserver<com.jde.jdeserverwp.servicios.IsConnectedResponse> responseObserver) {

        
        logger.info("-------------------------------------------------------------------------------");
        logger.info("JDE isConnected: Begin with Session ID: " + request.getSessionId());
        logger.info("Atina Transaction ID: " + Long.toString(request.getTransactionID()));
        
        // -----------------------------------------
        // Generar Session
        // -----------------------------------------
        //
        
        Configuracion config = new Configuracion();
        
        config.setUser("");

        config.setPassword("");

        config.setEnvironment("");

        config.setRole("");

        config.setSessionId((int) request.getSessionId());
        
        config.setTokenExpiration(configuracion.getTokenExpiration());
          
        try {
            
            if(!request.getJwtToken().isEmpty())
            {
             
                config = getConfigFromJWT(request.getJwtToken()); 
                   
            }
 
            int sessionId = JDEPoolConnections.getInstance().getSingleConnection(config.getSessionId()).isJDEConnected();
            
            IsConnectedResponse response = IsConnectedResponse.newBuilder().setConnected(sessionId!=0).build();
            
            logger.info("JDE isConnected: " + Boolean.toString(sessionId!=0)); 
            
            responseObserver.onNext(response);

            responseObserver.onCompleted();
             
            
        } catch (JDESingleConnectionException ex) {
             
            String msg = "Error WS Server: " + ex.getMessage();
            logger.error(msg, ex);
            logger.info("-------------------------------------------------------------------------------");
            
            StringBuilder sb = new StringBuilder();
            sb.append("Error with Logout");
            sb.append("|");
            sb.append(ex.getMessage());
            sb.append("|%ExternalServiceException%");
 
            responseObserver.onError(Status.INTERNAL
                    .withDescription(sb.toString())
                    .withCause(ex)
                    .asRuntimeException());
 
      
        } catch (Exception ex) {
            
            String msg = "Error WS Server: " + ex.getMessage();
            logger.error(msg, ex);
            logger.info("-------------------------------------------------------------------------------");
            
            StringBuilder sb = new StringBuilder();
            sb.append("Error with Logout");
            sb.append("|");
            sb.append(ex.getMessage());
            sb.append("|%ServiceServerException%");
 
            responseObserver.onError(Status.INTERNAL
                    .withDescription(sb.toString())
                    .withCause(ex)
                    .asRuntimeException());
             
        } 

         logger.info("JDE isConnected: End with Session ID: " + request.getSessionId()); 
         logger.info("-------------------------------------------------------------------------------");
         
    } 
    
    @Override
    public void operaciones(com.jde.jdeserverwp.servicios.OperacionesRequest request,
            io.grpc.stub.StreamObserver<com.jde.jdeserverwp.servicios.OperacionesResponse> responseObserver) {
        
        logger.info("-------------------------------------------------------------------------------");
        logger.info("JDE Connector Server. Getting operations");
        logger.info("Atina Transaction ID: " + Long.toString(request.getTransactionID()));

        String tipoDeOperacion = request.getConnectorName();   // BSFN or WS

        // ================================================
        // Get Session ID
        // ================================================
        //
       
        Configuracion config = new Configuracion();
        
        config.setUser(request.getUser());

        config.setPassword(request.getPassword());

        config.setEnvironment(request.getEnvironment());

        config.setRole(request.getRole());

        config.setSessionId((int) request.getSessionId());
        
        config.setTokenExpiration(configuracion.getTokenExpiration());
        
        try {
            
            if(!request.getJwtToken().isEmpty())
            {
             
                config = getConfigFromJWT(request.getJwtToken()); 
                   
            }
        
            int sessionID = JDEPoolConnections.getInstance().createConnection(  config.getUser(), 
                                                                                config.getPassword(), 
                                                                                config.getEnvironment(), 
                                                                                config.getRole(), 
                                                                                (int) config.getSessionIdAsInt(),
                                                                                request.getWsconnection());
      
            config.setSessionId(sessionID);
        
            // ================================================
            // Get Single Connection
            // ================================================
            //
            JDEConnection singleConnection = JDEPoolConnections.getInstance().getSingleConnection(sessionID);

            if (tipoDeOperacion.equals(TIPO_BSFN) || tipoDeOperacion.equals(TIPO_WS)) {

                // ================================================
                // Get Operations
                // ================================================
                // 
                Set<String> bsfnList = singleConnection.getOperationList();

                OperacionesResponse.Builder responseBuilder = OperacionesResponse.newBuilder();

                for (String operation : bsfnList) {

                    Operacion.Builder operacion = com.jde.jdeserverwp.servicios.Operacion.newBuilder();

                    operacion.setIdOperacion(operation);

                    operacion.setNombreOperacion(operation);

                    responseBuilder.addOperaciones(0, operacion);

                }

                responseBuilder.setSessionId(sessionID);
                
                responseBuilder.setJwtToken(getJWT(config));

                OperacionesResponse response = responseBuilder.build();

                responseObserver.onNext(response);

                responseObserver.onCompleted();

            }
        }
        catch (JDESingleConnectionException ex) {
             
            String msg = "Error JDE Server: " + ex.getMessage();
            logger.error(msg, ex);
            logger.info("-------------------------------------------------------------------------------");
            
            StringBuilder sb = new StringBuilder();
            sb.append("Error getting operations");
            sb.append("|");
            sb.append(ex.getMessage());
            sb.append("|%ExternalServiceException%");
 
            responseObserver.onError(Status.INTERNAL
                    .withDescription(sb.toString())
                    .withCause(ex)
                    .asRuntimeException());
 
      
        } catch (Exception ex) {
            
            String msg = "Error JDE Server: " + ex.getMessage();
            logger.error(msg, ex);
            logger.info("-------------------------------------------------------------------------------");
            
            StringBuilder sb = new StringBuilder();
            sb.append("Error getting operations");
            sb.append("|");
            sb.append(ex.getMessage());
            sb.append("|%ServiceServerException%");
 
            responseObserver.onError(Status.INTERNAL
                    .withDescription(sb.toString())
                    .withCause(ex)
                    .asRuntimeException());
             
        } 

        logger.info("-------------------------------------------------------------------------------");
        
    }
    
    @Override
    public void getMetadaParaOperacion(com.jde.jdeserverwp.servicios.GetMetadataRequest request,
            io.grpc.stub.StreamObserver<com.jde.jdeserverwp.servicios.GetMetadataResponse> responseObserver) {
        
        logger.info("-------------------------------------------------------------------------------");
        logger.info("JDE Connector Server. Get Metadata for Operation");
        logger.info("Atina Transaction ID: " + Long.toString(request.getTransactionID()));
        
        String tipoDeOperacion = request.getConnectorName();   // BSFN or WS
        
        Configuracion config = new Configuracion();
        
        config.setUser(request.getUser());

        config.setPassword(request.getPassword());

        config.setEnvironment(request.getEnvironment());

        config.setRole(request.getRole());

        config.setSessionId((int) request.getSessionId());
         
        try {
            
            if(!request.getJwtToken().isEmpty())
            {
             
                config = getConfigFromJWT(request.getJwtToken()); 
                   
            }
        
           // ================================================
           // Get Session ID
           // ================================================
           //

            int sessionID = JDEPoolConnections.getInstance().createConnection(  config.getUser(), 
                                                                                config.getPassword(), 
                                                                                config.getEnvironment(), 
                                                                                config.getRole(), 
                                                                                (int) config.getSessionId(),
                                                                                request.getWsconnection());
            
            
            config.setSessionId(sessionID);
        
            // ================================================
            // Get Single Connection
            // ================================================
            // 

            if (tipoDeOperacion.equals(TIPO_BSFN)) {

                JDESingleConnection singleConnection = (JDESingleConnection) JDEPoolConnections.getInstance().getSingleConnection(sessionID);
                
                // ================================================
                // Get Operations
                // ================================================
                //
                Set<JDEBsfnParameter> parameters = singleConnection.getBSFNParameter(request.getOperacionKey());
                
                // --------------------------------------------------
                // Crear el Objeto Mensaje de GetMetadataResponse
                // --------------------------------------------------
                //
                GetMetadataResponse.Builder responseBuilder = GetMetadataResponse.newBuilder();

                // ==================================================
                // Generar Parametro de Input
                // ==================================================
                // 
                logger.info("Parametros de Input:");
   
                int x = 0;
              
                for (JDEBsfnParameter parametro : parameters) {
                    
                     TipoDelParametroInput.Builder parametrosInputN = TipoDelParametroInput.newBuilder();
                     
                     // ---------------------------------------------------------
                    // Inicio los valores 
                    // ---------------------------------------------------------
                    //
                    parametrosInputN.setNombreDelParametro(parametro.getName()); 
                    parametrosInputN.setTipoDelParametroJava(parametro.getDataType()); 
                    parametrosInputN.addSubParametro(TipoDelParametroInput.newBuilder().build());
                     
                    responseBuilder.addListaDeParametrosInput(parametrosInputN.build());
                }
                
                // --------------------------------------------------
                // Generar Objecto Metadata
                // --------------------------------------------------
                // 
                GetMetadataResponse metadataResponse = responseBuilder.build();

                // --------------------------------------------------
                // Crear el Objeto Mensaje de GetMetadataResponse
                // --------------------------------------------------
                //  
                responseObserver.onNext(metadataResponse);

                responseObserver.onCompleted();
                

            }
            
            if (tipoDeOperacion.equals(TIPO_WS)) {

                JDESingleWSConnection singleConnection = (JDESingleWSConnection) JDEPoolConnections.getInstance().getSingleConnection(sessionID);
                
                // ================================================
                // Get Operations
                // ================================================
                //
                HashMap<String, ParameterTypeSimple> inputParameters = singleConnection.getWSInputParameter(request.getOperacionKey());
                
                HashMap<String, ParameterTypeSimple> outputParameters = singleConnection.getWSOutputParameter(request.getOperacionKey());
                
                // --------------------------------------------------
                // Crear el Objeto Mensaje de GetMetadataResponse
                // --------------------------------------------------
                //
                GetMetadataResponse.Builder responseBuilder = GetMetadataResponse.newBuilder();

                // ==================================================
                // Generar Parametro de Input
                // ==================================================
                // 
                 
                generateInputTreeMetadata(responseBuilder, inputParameters);
                 
                
                // ==================================================
                // Generar Parametro de Output
                // ==================================================
                // 
                generateOutputTreeMetadata(responseBuilder, outputParameters);      
                
                responseBuilder.setJwtToken(getJWT(config));
                
                // --------------------------------------------------
                // Generar Objecto Metadata
                // --------------------------------------------------
                // 
                GetMetadataResponse metadataResponse = responseBuilder.build();
                
                

                // --------------------------------------------------
                // Crear el Objeto Mensaje de GetMetadataResponse
                // --------------------------------------------------
                //  
                responseObserver.onNext(metadataResponse);

                responseObserver.onCompleted();
                

            }

        } catch (JDESingleConnectionException ex) {

            String msg = "Error JDE Server: " + ex.getMessage();
            logger.error(msg, ex);
            logger.info("-------------------------------------------------------------------------------");
            
            StringBuilder sb = new StringBuilder();
            sb.append("Error getting metadata operations");
            sb.append("|");
            sb.append(ex.getMessage());
            sb.append("|%ExternalServiceException%");

            responseObserver.onError(Status.INTERNAL
                    .withDescription(sb.toString())
                    .withCause(ex)
                    .asRuntimeException());

        } catch (Exception ex) {

            String msg = "Error JDE Server: " + ex.getMessage();
            logger.error(msg, ex);
            logger.info("-------------------------------------------------------------------------------");

            StringBuilder sb = new StringBuilder();
            sb.append("Error getting metadata operations");
            sb.append("|");
            sb.append(ex.getMessage());
            sb.append("|%ServiceServerException%");

            responseObserver.onError(Status.INTERNAL
                    .withDescription(sb.toString())
                    .withCause(ex)
                    .asRuntimeException());

        }

        logger.info("-------------------------------------------------------------------------------");
        
    }
    
    @Override
    public void getJsonsForOperation(com.jde.jdeserverwp.servicios.GetMetadataRequest request,
            io.grpc.stub.StreamObserver<com.jde.jdeserverwp.servicios.GetJsonsForOperationResponse> responseObserver) {
        
        logger.info("-------------------------------------------------------------------------------");
        logger.info("JDE Connector Server. Get Metadata for Operation");
        logger.info("Atina Transaction ID: " + Long.toString(request.getTransactionID()));
        
        String tipoDeOperacion = request.getConnectorName();   // BSFN or WS
        
        Configuracion config = new Configuracion();
        
        config.setUser(request.getUser());

        config.setPassword(request.getPassword());

        config.setEnvironment(request.getEnvironment());

        config.setRole(request.getRole());

        config.setSessionId((int) request.getSessionId());
        
        config.setTokenExpiration(configuracion.getTokenExpiration());
         
        try {
        
           // ================================================
           // Get Session ID
           // ================================================
           //
           
           if(!request.getJwtToken().isEmpty())
            {
             
                config = getConfigFromJWT(request.getJwtToken()); 
                   
            }

            int sessionID = JDEPoolConnections.getInstance().createConnection(  config.getUser(), 
                                                                                config.getPassword(), 
                                                                                config.getEnvironment(), 
                                                                                config.getRole(), 
                                                                                (int) config.getSessionIdAsInt(),
                                                                                request.getWsconnection());
         
        
            // ================================================
            // Get Single Connection
            // ================================================
            // 

            if (tipoDeOperacion.equals(TIPO_BSFN)) {

                
                

            }
            
            if (tipoDeOperacion.equals(TIPO_WS)) {

                JDESingleWSConnection singleConnection = (JDESingleWSConnection) JDEPoolConnections.getInstance().getSingleConnection(sessionID);
                
                // ================================================
                // Get Operations
                // ================================================
                //
                HashMap<String, ParameterTypeSimple> inputParameters = singleConnection.getWSInputParameter(request.getOperacionKey());
                
                HashMap<String, ParameterTypeSimple> outputParameters = singleConnection.getWSOutputParameter(request.getOperacionKey());
                
                // --------------------------------------------------
                // Crear el Objeto Mensaje de GetMetadataResponse
                // --------------------------------------------------
                //
                GetJsonsForOperationResponse.Builder responseBuilder = GetJsonsForOperationResponse.newBuilder();

                // ==================================================
                // Generar Parametro de Input
                // ==================================================
                // 
                
                HashMap<String, Object> inputValues = new HashMap<String, Object>();
                
                HashMap<String, Object> outputValues = new HashMap<String, Object>();
                
                 
                generateJsonInput(inputValues, inputParameters);
                
                generateJsonInput(outputValues, outputParameters);
                 
                
                // ==================================================
                // Convert HashMap to JSON
                // ==================================================
                // 
                String inputAsJson = "";
                
                String outputAsJson = "";
                
                ObjectMapper mapper = new ObjectMapper();

                mapper.setPropertyNamingStrategy(new MyNamingStrategy());

                mapper.configure(MapperFeature.USE_ANNOTATIONS, false);

                try {

                    inputAsJson = mapper.writeValueAsString(inputValues);
                    
                    outputAsJson  = mapper.writeValueAsString(outputValues);

                } catch (JsonProcessingException ex) {

                    throw new JDESingleConnectionException("Error invoking WS JsonProcessingException " + ex.getMessage(), ex);

                } 
                
                // ==================================================
                // Generar Parametro de Output
                // ==================================================
                // 
                
                responseBuilder.setInputAsJson(inputAsJson);
                
                responseBuilder.setOutputAsJson(outputAsJson); 
           
                responseBuilder.setJwtToken(getJWT(config));

                // --------------------------------------------------
                // Crear el Objeto Mensaje de GetMetadataResponse
                // --------------------------------------------------
                //  
                responseObserver.onNext(responseBuilder.build());

                responseObserver.onCompleted();
                

            }

        } catch (JDESingleConnectionException ex) {

            String msg = "Error JDE Server: " + ex.getMessage();
            logger.error(msg, ex);
            logger.info("-------------------------------------------------------------------------------");
            
            StringBuilder sb = new StringBuilder();
            sb.append("Error getting metadata operations");
            sb.append("|");
            sb.append(ex.getMessage());
            sb.append("|%ExternalServiceException%");

            responseObserver.onError(Status.INTERNAL
                    .withDescription(sb.toString())
                    .withCause(ex)
                    .asRuntimeException());

        } catch (Exception ex) {

            String msg = "Error JDE Server: " + ex.getMessage();
            logger.error(msg, ex);
            logger.info("-------------------------------------------------------------------------------");

            StringBuilder sb = new StringBuilder();
            sb.append("Error getting metadata operations");
            sb.append("|");
            sb.append(ex.getMessage());
            sb.append("|%ServiceServerException%");

            responseObserver.onError(Status.INTERNAL
                    .withDescription(sb.toString())
                    .withCause(ex)
                    .asRuntimeException());

        }

        logger.info("-------------------------------------------------------------------------------");
        
    }
    
    private void generateInputTreeMetadata(GetMetadataResponse.Builder responseBuilder, HashMap<String, ParameterTypeSimple> parameters) {
 
         logger.info("Input Parameter");
 
         for (Map.Entry<String, ParameterTypeSimple> entry : parameters.entrySet()) {

             TipoDelParametroInput.Builder parametrosInputN = TipoDelParametroInput.newBuilder();

             // ---------------------------------------------------------
             // Inicio los valores
             // ---------------------------------------------------------
             //
             Object parameterValue = entry.getValue();
             
             String parameterName = entry.getKey();
             
             parametrosInputN.setNombreDelParametro(parameterName);
             

             if (!(parameterValue instanceof ParameterTypeObject)) {

                 ParameterTypeSimple simpleParameterType = (ParameterTypeSimple) parameterValue;
                 
                 parametrosInputN.setRepeatedParameter(Boolean.valueOf(simpleParameterType.isRepeated()));
                 
                 parametrosInputN.setIsObject(Boolean.FALSE);
                 
                 parametrosInputN.setTipoDelParametroJava(simpleParameterType.getModelType()); 
                 
                 parametrosInputN.addSubParametro(TipoDelParametroInput.newBuilder().build());
                 
             } else // Another HashMap
             {
                
                 ParameterTypeObject objectParameterType = (ParameterTypeObject) parameterValue;

                 parametrosInputN.setTipoDelParametroJava(objectParameterType.getModelType());

                 parametrosInputN.setRepeatedParameter(objectParameterType.isRepeated());

                 parametrosInputN.setIsObject(Boolean.TRUE);

                 generateInputSubParameter(parametrosInputN, (HashMap<String, ParameterTypeSimple>) objectParameterType.getSubParameters());
                  
             } 
             
             responseBuilder.addListaDeParametrosInput(parametrosInputN.build());

         }
 
    }
     
     
     private void generateInputSubParameter(TipoDelParametroInput.Builder source, HashMap<String, ParameterTypeSimple> inputParameters) {
         
         for (Map.Entry<String, ParameterTypeSimple> entry : inputParameters.entrySet()) {
             
             TipoDelParametroInput.Builder parametrosInputN = TipoDelParametroInput.newBuilder();
             
             // ---------------------------------------------------------
             // Inicio los valores
             // ---------------------------------------------------------
             //
             Object parameterValue = entry.getValue();
             
             String parameterName = entry.getKey();
             
             parametrosInputN.setNombreDelParametro(parameterName);

             if (!(parameterValue instanceof ParameterTypeObject)) {

                 ParameterTypeSimple simpleParameterType = (ParameterTypeSimple) parameterValue;
                 
                 parametrosInputN.setRepeatedParameter(Boolean.valueOf(simpleParameterType.isRepeated()));
  
                 parametrosInputN.setTipoDelParametroJava(simpleParameterType.getModelType()); 
                 
                 parametrosInputN.addSubParametro(TipoDelParametroInput.newBuilder().build());
                 
                 parametrosInputN.setIsObject(Boolean.FALSE);
                 
             } else // Another HashMap
             {
                   
                 ParameterTypeObject objectParameterType = (ParameterTypeObject) parameterValue;
                 
                 parametrosInputN.setTipoDelParametroJava(objectParameterType.getModelType());
                 
                 parametrosInputN.setRepeatedParameter(objectParameterType.isRepeated());
                 
                 parametrosInputN.setIsObject(Boolean.TRUE);
                   
                 generateInputSubParameter(parametrosInputN,(HashMap<String, ParameterTypeSimple>) objectParameterType.getSubParameters());
                 
             } 
               
             source.addSubParametro(parametrosInputN.build());
               
         } 
         
     }
    
     private void generateJsonInput(HashMap<String, Object> inputValues, HashMap<String, ParameterTypeSimple> parameters) {
 
         logger.info("Input Parameter");
 
         for (Map.Entry<String, ParameterTypeSimple> entry : parameters.entrySet()) {
 
             // ---------------------------------------------------------
             // Inicio los valores
             // ---------------------------------------------------------
             //
             Object parameterValue = entry.getValue();
             
             String parameterName = entry.getKey(); 
             
             Object parameterDefaultValue = "";

             if (!(parameterValue instanceof ParameterTypeObject)) {

                 ParameterTypeSimple simpleParameterType = (ParameterTypeSimple) parameterValue;
                  
                 if(simpleParameterType.isRepeated())
                 {
                     ArrayList<Object> newList = new ArrayList<Object>();
                      
                     newList.add(simpleParameterType.getModelType());
                     
                     parameterDefaultValue = newList;
                     
                     
                 }
                 else
                 {
                     parameterDefaultValue = simpleParameterType.getModelType();
                 }
                  
                 
             } else  
             {
                
                 ParameterTypeObject objectParameterType = (ParameterTypeObject) parameterValue;
                 
                 if(objectParameterType.isRepeated())
                 {
                     
                     ArrayList<Object> newList = new ArrayList<Object>();
                      
                     HashMap<String, Object> inputSubValues = new HashMap<String, Object>();
                     
                     generateJsonInputSubParameter(inputSubValues, (HashMap<String, ParameterTypeSimple>) objectParameterType.getSubParameters());
                     
                     newList.add(inputSubValues);
                     
                     parameterDefaultValue = newList;
                     
                     
                 }
                 else
                 {
                     
                     HashMap<String, Object> inputSubValues = new HashMap<String, Object>();
                     
                     generateJsonInputSubParameter(inputSubValues, (HashMap<String, ParameterTypeSimple>) objectParameterType.getSubParameters());
                     
                     parameterDefaultValue = inputSubValues;
                     
                        
                 }
                  
                  
             } 
             
             inputValues.put(parameterName, parameterDefaultValue);
              

         }
 
    }
     
     
     private void generateJsonInputSubParameter(HashMap<String, Object> inputValues, HashMap<String, ParameterTypeSimple> inputParameters) {
         
         for (Map.Entry<String, ParameterTypeSimple> entry : inputParameters.entrySet()) {
              
             // ---------------------------------------------------------
             // Inicio los valores
             // ---------------------------------------------------------
             //
             Object parameterValue = entry.getValue();
             
             String parameterName = entry.getKey();
             
             Object parameterDefaultValue = ""; 

             if (!(parameterValue instanceof ParameterTypeObject)) {

                 ParameterTypeSimple simpleParameterType = (ParameterTypeSimple) parameterValue;
                 
                 if(simpleParameterType.isRepeated())
                 {
                     ArrayList<Object> newList = new ArrayList<Object>();
                      
                     newList.add(simpleParameterType.getModelType());
                     
                     parameterDefaultValue = newList;
                     
                     
                 }
                 else
                 {
                     parameterDefaultValue = simpleParameterType.getModelType();
                 }
                 
             } else // Another HashMap
             {
                   
                 ParameterTypeObject objectParameterType = (ParameterTypeObject) parameterValue;
                 
                 if(objectParameterType.isRepeated())
                 {
                     
                     ArrayList<Object> newList = new ArrayList<Object>();
                      
                     HashMap<String, Object> inputSubValues = new HashMap<String, Object>();
                     
                     generateJsonInputSubParameter(inputSubValues, (HashMap<String, ParameterTypeSimple>) objectParameterType.getSubParameters());
                     
                     newList.add(inputSubValues);
                     
                     parameterDefaultValue = newList;
                     
                     
                 }
                 else
                 {
                     
                     HashMap<String, Object> inputSubValues = new HashMap<String, Object>();
                     
                     generateJsonInputSubParameter(inputSubValues, (HashMap<String, ParameterTypeSimple>) objectParameterType.getSubParameters());
                     
                     parameterDefaultValue = inputSubValues;
                     
                        
                 }
                 
             } 
             
             inputValues.put(parameterName, parameterDefaultValue);
             
               
         } 
         
     }
     
     private void generateOutputTreeMetadata(GetMetadataResponse.Builder responseBuilder, HashMap<String, ParameterTypeSimple> parameters) {
 
         logger.info("Output Parameter");
 
         for (Map.Entry<String, ParameterTypeSimple> entry : parameters.entrySet()) {

             TipoDelParametroOutput.Builder parametrosOutputN = TipoDelParametroOutput.newBuilder();

             // ---------------------------------------------------------
             // Inicio los valores
             // ---------------------------------------------------------
             //
             Object parameterValue = entry.getValue();
             
             String parameterName = entry.getKey();
             
             parametrosOutputN.setNombreDelParametro(parameterName);

             if (!(parameterValue instanceof ParameterTypeObject)) {

                 ParameterTypeSimple simpleParameterType = (ParameterTypeSimple) parameterValue;
                 
                 parametrosOutputN.setRepeatedParameter(Boolean.valueOf(simpleParameterType.isRepeated()));
                 
                 parametrosOutputN.setIsObject(Boolean.FALSE);
                 
                 parametrosOutputN.setTipoDelParametroJava(simpleParameterType.getModelType()); 
                 
                 parametrosOutputN.addSubParametro(TipoDelParametroOutput.newBuilder().build());
                 
             } else // Another HashMap
             {
                
                 ParameterTypeObject objectParameterType = (ParameterTypeObject) parameterValue;
                 
                 parametrosOutputN.setTipoDelParametroJava(objectParameterType.getModelType());
                 
                  parametrosOutputN.setRepeatedParameter(objectParameterType.isRepeated());
                  
                   parametrosOutputN.setIsObject(Boolean.TRUE);
                  
                 generateOutputSubParameter(parametrosOutputN, (HashMap<String, ParameterTypeSimple>) objectParameterType.getSubParameters());
                  
             } 
             
             responseBuilder.addListaDeParametrosOutput(parametrosOutputN.build());

         }
 
    }
     
     
     private void generateOutputSubParameter(TipoDelParametroOutput.Builder source, HashMap<String, ParameterTypeSimple> inputParameters) {
         
         for (Map.Entry<String, ParameterTypeSimple> entry : inputParameters.entrySet()) {
             
             TipoDelParametroOutput.Builder parametrosOutputN = TipoDelParametroOutput.newBuilder();
             
             // ---------------------------------------------------------
             // Inicio los valores
             // ---------------------------------------------------------
             //
             Object parameterValue = entry.getValue();
             
             String parameterName = entry.getKey();
             
             parametrosOutputN.setNombreDelParametro(parameterName);

             if (!(parameterValue instanceof ParameterTypeObject)) {

                 ParameterTypeSimple simpleParameterType = (ParameterTypeSimple) parameterValue;
                 
                 parametrosOutputN.setRepeatedParameter(Boolean.valueOf(simpleParameterType.isRepeated()));
                 
                 parametrosOutputN.setIsObject(Boolean.FALSE);
  
                 parametrosOutputN.setTipoDelParametroJava(simpleParameterType.getModelType()); 
                 
                 parametrosOutputN.addSubParametro(TipoDelParametroOutput.newBuilder().build());
                 
             } else // Another HashMap
             {
                   
                 ParameterTypeObject objectParameterType = (ParameterTypeObject) parameterValue;
                 
                 parametrosOutputN.setTipoDelParametroJava(objectParameterType.getModelType());
                 
                 parametrosOutputN.setRepeatedParameter(objectParameterType.isRepeated());
                 
                 parametrosOutputN.setIsObject(Boolean.TRUE);
                   
                 generateOutputSubParameter(parametrosOutputN,(HashMap<String, ParameterTypeSimple>) objectParameterType.getSubParameters());
                 
             } 
               
             source.addSubParametro(parametrosOutputN.build());
               
         } 
         
     } 
     
    @Override
    public void ejecutarOperacion(com.jde.jdeserverwp.servicios.EjecutarOperacionRequest request,
            io.grpc.stub.StreamObserver<com.jde.jdeserverwp.servicios.EjecutarOperacionResponse> responseObserver) {
        
        
        logger.info("-------------------------------------------------------------------------------");
        logger.info("JDE Connector Server. Execute Operation");
        logger.info("Atina Transaction ID: " + Long.toString(request.getTransactionID())); 
        
        String tipoDeOperacion = request.getConnectorName();   // BSFN or WS
        
        Configuracion config = new Configuracion();
        
        config.setUser(request.getUser());

        config.setPassword(request.getPassword());

        config.setEnvironment(request.getEnvironment());

        config.setRole(request.getRole());

        config.setSessionId((int) request.getSessionId());
        
        config.setTokenExpiration(configuracion.getTokenExpiration());
         
        try {
        
           // ================================================
           // Get Session ID
           // ================================================
           //
           
           if(!request.getJwtToken().isEmpty())
            {
             
                config = getConfigFromJWT(request.getJwtToken()); 
                   
            }

            int sessionID = JDEPoolConnections.getInstance().createConnection(  config.getUser(), 
                                                                                config.getPassword(), 
                                                                                config.getEnvironment(), 
                                                                                config.getRole(), 
                                                                                (int) config.getSessionIdAsInt(),
                                                                                request.getWsconnection());
         
        
            // ================================================
            // Get Single Connection
            // ================================================
            // 

            if (tipoDeOperacion.equals(TIPO_BSFN)) {

                JDESingleConnection singleConnection = (JDESingleConnection) JDEPoolConnections.getInstance().getSingleConnection(sessionID);
                
                 
                

            }
            
            if (tipoDeOperacion.equals(TIPO_WS)) {

                JDESingleWSConnection singleConnection = (JDESingleWSConnection) JDEPoolConnections.getInstance().getSingleConnection(sessionID);
                
                // ================================================
                // Get Operations
                // ================================================
                //
                HashMap<String, ParameterTypeSimple> inputParameters = singleConnection.getWSInputParameter(request.getOperacionKey());
                
                HashMap<String, ParameterTypeSimple> outputParameters = singleConnection.getWSOutputParameter(request.getOperacionKey());
                
                
                // =========================================================
                // Obtenere la Lista de Valores Enviado por el Conector
                // =========================================================
                //
                List<EjecutarOperacionValores> valoresEnviadosPorElConectorParaInvocacion = request.getListaDeValoresList();
                
                // -----------------------------------------------------
                // Convert Input Value as Hash Map
                // -----------------------------------------------------
                //
                HashMap<String, Object> inputValues = convertirInputEnHashMap(valoresEnviadosPorElConectorParaInvocacion, inputParameters, 0);
                 
                HashMap<String, Object> returnValues = singleConnection.callJDEOperation(request.getOperacionKey(), inputValues, sessionID);
                
                logger.info("Converting Input into HashMap. Parameter Name: ");
                
                // =========================================================
                // Preparar Response
                // =========================================================
                //
                
                EjecutarOperacionResponse.Builder responseBuilder = EjecutarOperacionResponse.newBuilder();
                
                responseBuilder.setNombreDelParametro("response");
                responseBuilder.setTipoDelParametro("Object");
                responseBuilder.setRepeatedParameter(false);
                responseBuilder.setNullValue(false);
                responseBuilder.setIsObject(true);
                 
                createOperationResponse(responseBuilder, outputParameters,returnValues,0);
                 
                responseBuilder.setJwtToken(getJWT(config));
                
                // --------------------------------------------------
                // Crear el Objeto Mensaje de GetMetadataResponse
                // --------------------------------------------------
                //  
                responseObserver.onNext(responseBuilder.build());

                responseObserver.onCompleted();

            }

        } catch (JDESingleConnectionException ex) {

            String msg = "Error JDE Server: " + ex.getMessage();
            logger.error(msg, ex);
            logger.info("-------------------------------------------------------------------------------");
            
            StringBuilder sb = new StringBuilder();
            sb.append("Error getting metadata operations");
            sb.append("|");
            sb.append(ex.getMessage());
            sb.append("|%ExternalServiceException%");

            responseObserver.onError(Status.INTERNAL
                    .withDescription(sb.toString())
                    .withCause(ex)
                    .asRuntimeException());

        } catch (JDESingleWSException ex) {

            String msg = "Error JDE Server: " + ex.getMessage();
            logger.error(msg, ex);
            logger.info("-------------------------------------------------------------------------------");
            
            StringBuilder sb = new StringBuilder();
            sb.append("Error Invoking WS");
            sb.append("|");
            sb.append(ex.getE1Message());
            sb.append("|%WSException%");

            responseObserver.onError(Status.INTERNAL
                    .withDescription(sb.toString())
                    .withCause(ex)
                    .asRuntimeException());

        } catch (Exception ex) {

            String msg = "Error JDE Server: " + ex.getMessage();
            logger.error(msg, ex);
            logger.info("-------------------------------------------------------------------------------");

            StringBuilder sb = new StringBuilder();
            sb.append("Error getting metadata operations");
            sb.append("|");
            sb.append(ex.getMessage());
            sb.append("|%ServiceServerException%");

            responseObserver.onError(Status.INTERNAL
                    .withDescription(sb.toString())
                    .withCause(ex)
                    .asRuntimeException());

        }
         
        logger.info("-------------------------------------------------------------------------------");
        
    }
    
    private void createOperationResponse(EjecutarOperacionResponse.Builder response, HashMap<String, ParameterTypeSimple> metadataParameters, HashMap<String, Object> valuesReturned, int level) throws IOException {

           
        String levelLog = StringUtils.repeat(" > ", level);
        
        logger.info(levelLog + "createOperationResponse - Level:" + level);
        
        if (valuesReturned != null && !valuesReturned.isEmpty()) {

            // ---------------------------------------------
            // For each value returned
            // ---------------------------------------------
            for (Map.Entry<String, Object> valueReturned : valuesReturned.entrySet()) {
                
                // --------------------------------------------
                // Greate Response Object
                // --------------------------------------------                
                //  
                EjecutarOperacionResponse.Builder newOperationResponse = EjecutarOperacionResponse.newBuilder();

                // --------------------------------------------
                // Get Parameter Name
                // --------------------------------------------                
                // 
                String nombreDelParametro = valueReturned.getKey();
                
                // --------------------------------------------
                // Get Value
                // --------------------------------------------                
                // 
                Object value = valueReturned.getValue();
                 
                logger.info(levelLog + "Converting Input into HashMap. Parameter Name: " + nombreDelParametro +  " Level:" + level);
                
                // Get Metadata
                
                ParameterTypeSimple parameterMetadata = metadataParameters.get(nombreDelParametro);
                
                if(value != null)
                { 

                    logger.info(levelLog + "    Type: " + parameterMetadata.getModelType());
                    logger.info(levelLog + "    Real Type: " + value.getClass().getName()); 
                    logger.info(levelLog + "    Repeated: " + parameterMetadata.isRepeated());

                    if (parameterMetadata instanceof ParameterTypeObject) {
                         
                        if (parameterMetadata.isRepeated()) {
                                  
                            newOperationResponse.setNombreDelParametro(nombreDelParametro);
                            newOperationResponse.setTipoDelParametro(parameterMetadata.getModelType());
                            newOperationResponse.setRepeatedParameter(parameterMetadata.isRepeated());
                            newOperationResponse.setIsObject(true);
                             
                            ArrayList<LinkedHashMap> values = (ArrayList<LinkedHashMap>) value;
                            
                            newOperationResponse.setNullValue(values.isEmpty());

                            for(LinkedHashMap eachValue:values)
                            {
                                EjecutarOperacionResponse.Builder operacionResponseObject = EjecutarOperacionResponse.newBuilder();
                        
                                operacionResponseObject.setNombreDelParametro(nombreDelParametro);
                                operacionResponseObject.setTipoDelParametro(parameterMetadata.getModelType());
                                operacionResponseObject.setRepeatedParameter(false);
                                operacionResponseObject.setNullValue(false); 
                                operacionResponseObject.setIsObject(true);
                                
                                parameterMetadata.setRepeated(false);
                                
                                level++;
                                
                                createOperationResponse(operacionResponseObject, ((ParameterTypeObject) parameterMetadata).getSubParameters(), (HashMap<String, Object>) eachValue,level);

                                level--;
                                
                                parameterMetadata.setRepeated(true);
                                  
                                newOperationResponse.addListaDeValores(operacionResponseObject.build());

                            }


                        } else {
                                
                            
                            newOperationResponse.setNombreDelParametro(nombreDelParametro);
                            newOperationResponse.setTipoDelParametro(parameterMetadata.getModelType());
                            newOperationResponse.setRepeatedParameter(false);
                            newOperationResponse.setIsObject(true);
                            newOperationResponse.setNullValue(false);
 
                            level++;
                            
                            createOperationResponse(newOperationResponse,((ParameterTypeObject) parameterMetadata).getSubParameters(), (HashMap<String, Object>) value,level);
                            
                            level-- ;
                             
                        }

                    } else {

                        if (parameterMetadata.isRepeated()) {

                            newOperationResponse.setNombreDelParametro(nombreDelParametro);

                            newOperationResponse.setTipoDelParametro(parameterMetadata.getModelType());
                            
                            newOperationResponse.setIsObject(false);
                            
                            newOperationResponse.setNullValue(false);
                            
                            ArrayList<LinkedHashMap> values = (ArrayList<LinkedHashMap>) value;
                            
                            newOperationResponse.setNullValue(values.isEmpty());
                            
                            for(LinkedHashMap eachValue:values)
                            {
                                EjecutarOperacionResponse.Builder operacionResponseObject = EjecutarOperacionResponse.newBuilder();
                        
                                operacionResponseObject.setNombreDelParametro(nombreDelParametro);
                                operacionResponseObject.setTipoDelParametro(parameterMetadata.getModelType());
                                operacionResponseObject.setRepeatedParameter(false);
                                operacionResponseObject.setNullValue(false);
                                operacionResponseObject.setIsObject(false); 
                                
                                level++;
                                
                                createOperationResponse(operacionResponseObject, ((ParameterTypeObject) parameterMetadata).getSubParameters(), (HashMap<String, Object>) eachValue,level);

                                level--;
                                
                                parameterMetadata.setRepeated(true);
                                  
                                newOperationResponse.addListaDeValores(operacionResponseObject.build());

                            }


                        } else {
  
                            
                            newOperationResponse.setNombreDelParametro(nombreDelParametro); 
                            newOperationResponse.setTipoDelParametro(parameterMetadata.getModelType()); 
                            newOperationResponse.setNullValue(false); 
                            newOperationResponse.setIsObject(false);

                            switch (parameterMetadata.getModelType()) {

                                case "java.lang.String":
                                    newOperationResponse.setValueAsString((String) value);
                                    break;
                                case "java.lang.Integer":
                                case "int":
                                    newOperationResponse.setValueAsInteger((int) value);
                                    break;
                                case "java.lang.Boolean":
                                case "boolean":
                                    newOperationResponse.setValueAsBoolean((boolean) value);
                                    break;
                                case "java.lang.Long":
                                case "long":
                                    if(value instanceof java.lang.Integer)
                                    {
                                        newOperationResponse.setValueAsLong(Long.valueOf((java.lang.Integer) value));
                                        
                                    } else
                                    {
                                        newOperationResponse.setValueAsLong((long) value);
                                    }
                                    
                                    break;
                                case "java.math.BigDecimal":
                                case "java.lang.Double":
                                case "double":
                                    
                                    if(value instanceof java.lang.Integer)
                                    {
                                        newOperationResponse.setValueAsDouble(Double.valueOf((java.lang.Integer) value));
                                    }
                                    else
                                    {
                                         newOperationResponse.setValueAsDouble((double) value);
                                    }
                                   
                                    break;
                                case "java.lang.Float":
                                case "float":
                                    newOperationResponse.setValueAsFloat((float) value);
                                    break;
                                case "java.util.Date":
                                case "java.util.Calendar":
                                    
                                    SqlDate jdeDate = new SqlDate((long) value);
                         
                                    Timestamp.Builder tmpBuilder = Timestamp.newBuilder();
                                    tmpBuilder.setNanos(1000);
                                    tmpBuilder.setSeconds(jdeDate.getCalendar().getTimeInMillis()/1000);                                    
                                    newOperationResponse.setValueAsDate(tmpBuilder.build());
                                    break;

                                case "java.lang.Byte":
                                    newOperationResponse.setValueAsStringBytes((ByteString) value);
                                    break;
                                case "java.io.File":

                                    Map.Entry primerValorX = (Map.Entry) value;
                                    java.io.File obj = (java.io.File) primerValorX.getValue();
                                    Path path1 = obj.toPath();
                                    byte[] valor1 = Files.readAllBytes(path1);
                                    String encoded = Base64.getEncoder().encodeToString(valor1);
                                    ByteString byteString = (ByteString) ByteString.copyFrom(encoded, Charset.forName("UTF-8"));
                                    newOperationResponse.setValueAsStringBytes(byteString);
                                    break;
                                     
                                default:
                                    throw new JDESingleConnectionException("Invalid Type " + parameterMetadata.getModelType(),null);
                            }
 

                        }

                    }

                }
                else
                {
                    logger.info(levelLog + "Parameter has value in null");
                    
                    newOperationResponse.setNombreDelParametro(nombreDelParametro);

                    newOperationResponse.setTipoDelParametro(parameterMetadata.getModelType());
                    
                    newOperationResponse.setNullValue(true); 
                    
                }
                
                // ---------------------------------------------------
                // Return New Parameter
                // ---------------------------------------------------
                //
                response.addListaDeValores(newOperationResponse.build());

            }
            
        }
         
    }
     
    
    private HashMap<String, Object> convertirInputEnHashMap(List<EjecutarOperacionValores> values, HashMap<String, ParameterTypeSimple> metadataFromInput , int level) {
        
        HashMap<String, Object> returnValue = new HashMap<String, Object>();
        
        for (EjecutarOperacionValores valor : values) {

            // Nombre del Parametro
            //
            String nombreDelParametro = valor.getNombreDelParametro();
 
            logger.info("Converting Input into HashMap. Parameter Name: " + nombreDelParametro);
            
            // Get Metadata
  
            ParameterTypeSimple parameterMetadata = metadataFromInput.get(nombreDelParametro);
            
            logger.info("    Type: " + parameterMetadata.getModelType());
            logger.info("    Repeated: " + parameterMetadata.isRepeated());  
            
            if (parameterMetadata instanceof ParameterTypeObject) {
                 
                
                if(parameterMetadata.isRepeated())
                {
                    
                    ArrayList<Object> listaDeValores = new ArrayList();
                    
                    List<EjecutarOperacionValores> valores = valor.getListaDeValoresList();
                    
                    for (EjecutarOperacionValores valorLista : valores) {
                        
                        level ++;
                        
                        listaDeValores.add(convertirInputEnHashMap(valorLista.getListaDeValoresList(), metadataFromInput,level));
                        
                        level--;
                        
                    }
                    
                    returnValue.put(nombreDelParametro, listaDeValores);
                    
                }
                else
                {
                    // The value is an Object
                    
                    returnValue.put(nombreDelParametro, convertirInputEnHashMap(valor.getListaDeValoresList(), ((ParameterTypeObject) parameterMetadata).getSubParameters(), level+1));
                
                    
                }
                 

            } else {

                if (parameterMetadata.isRepeated()) {

                    ArrayList<Object> listaDeValores = new ArrayList();
                    
                    List<EjecutarOperacionValores> valores = valor.getListaDeValoresList();
                    
                    for (EjecutarOperacionValores valorLista : valores) {
                        
                        Object valorActual = null;

                        switch (parameterMetadata.getModelType()) {

                            case "java.lang.String":
                                valorActual = valorLista.getValueAsString();
                                break;
                            case "java.lang.Integer":
                                valorActual = valorLista.getValueAsInteger();
                                break;
                            case "java.lang.Boolean":
                                valorActual = valorLista.getValueAsBoolean();
                                break;
                            case "java.lang.Long":
                                valorActual = valorLista.getValueAsLong();
                                break;
                            case "java.lang.Double":
                                valorActual = valorLista.getValueAsDouble();
                                break;
                            case "java.lang.Float":
                                valorActual = valorLista.getValueAsFloat();
                                break;
                            case "java.util.Date":
                                // Convertir de TimeStamp to Date
                                com.google.protobuf.Timestamp ts = valorLista.getValueAsDate();
                                Instant valorTM = Instant.ofEpochSecond(ts.getSeconds(), ts.getNanos());
                                valorActual = Date.from(valorTM);
                                break;
                            case "java.lang.Byte":
                                valorActual = valorLista.getValuesAsByteString();
                                break;
                            case "BDecimal":
                            case "java.math.BigDecimal":
                                Double valueDouble = new Double(valorLista.getValueAsDouble());
                                String strValueD = valueDouble.toString();
                                valorActual = new BigDecimal(strValueD);
                                break; 
                            case "BInteger":
                            case "java.math.BigInteger":
                                Long valueLong = new Long(valorLista.getValueAsLong());
                                String strValueL = valueLong.toString();
                                valorActual = new BigInteger(strValueL);
                                break;
                            default:
                                logger.info("Error convirtiendo tipoDelParametroDeInput: " + parameterMetadata.getModelType());
                                break;

                        }

                        listaDeValores.add(valorActual);
                        
                    }
                    
                    returnValue.put(nombreDelParametro, listaDeValores);
                    
                    
                } else {

                    Object valorActual = null;

                    switch (parameterMetadata.getModelType()) {

                        case "java.lang.String":
                            valorActual = valor.getValueAsString();
                            break;
                        case "java.lang.Integer":
                            valorActual = valor.getValueAsInteger();
                            break;
                        case "java.lang.Boolean":
                            valorActual = valor.getValueAsBoolean();
                            break;
                        case "java.lang.Long":
                            valorActual = valor.getValueAsLong();
                            break;
                        case "java.lang.Double":
                            valorActual = valor.getValueAsDouble();
                            break;
                        case "java.lang.Float":
                            valorActual = valor.getValueAsFloat();
                            break;
                        case "java.util.Date":
                            // Convertir de TimeStamp to Date
                            com.google.protobuf.Timestamp ts = valor.getValueAsDate();
                            Instant valorTM = Instant.ofEpochSecond(ts.getSeconds(), ts.getNanos());
                            valorActual = Date.from(valorTM);
                            break;
                        case "java.lang.Byte":
                            valorActual = valor.getValuesAsByteString();
                            break;
                        case "BDecimal":
                        case "java.math.BigDecimal":
                            Double valueDouble = new Double(valor.getValueAsDouble());
                            String strValueD = valueDouble.toString();
                            valorActual = new BigDecimal(strValueD);
                            break;
                        case "BInteger":
                        case "java.math.BigInteger":
                            Long valueLong = new Long(valor.getValueAsLong());
                            String strValueL = valueLong.toString();
                            valorActual = new BigInteger(strValueL);
                            break;
                        default:
                            logger.info("Error convirtiendo tipoDelParametroDeInput: " + parameterMetadata.getModelType());
                            break;

                    }

                    returnValue.put(nombreDelParametro, valorActual);

                }
            }

        }
        
        return returnValue;
 
    }
    
    public static class MyNamingStrategy extends PropertyNamingStrategy {

        @Override
        public String nameForField(MapperConfig<?> config, AnnotatedField field, String defaultName) {
            return field.getName();
        }

        @Override
        public String nameForGetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
            return convert(method, defaultName);
        }

        @Override
        public String nameForSetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
            return convert(method, defaultName);
        }

        private String convert(AnnotatedMethod method, String defaultName) {
 
            Class<?> clazz = method.getDeclaringClass();
            List<Field> flds = FieldUtils.getAllFieldsList(clazz);
            for (Field fld : flds) {
                if (fld.getName().equalsIgnoreCase(defaultName)) {
                    return fld.getName();
                }
            }

            return defaultName;
        }
    }


    public void setConfiguracion(ServerConfiguration configuracion) {
        this.configuracion = configuracion;
    }
     
}
