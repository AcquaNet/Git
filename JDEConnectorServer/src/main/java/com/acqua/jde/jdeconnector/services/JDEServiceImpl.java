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
import com.acqua.atina.jdeconnectorservice.exception.JDESingleConnectorException;
import com.acqua.atina.jdeconnectorservice.exception.JDESingleWSException;
import com.acqua.atina.jdeconnectorservice.service.poolconnection.JDEPoolConnections;
import com.acqua.atina.jdeconnectorservice.service.JDESingleConnection;
import com.acqua.atina.jdeconnectorservice.service.poolconnection.JDEConnection;
import com.acqua.atina.jdeconnectorservice.wsservice.JDESingleWSConnection;
import com.google.protobuf.ByteString;
import com.google.protobuf.Timestamp;
import com.acqua.jde.jdeconnectorserver.model.Configuracion;
import com.jde.jdeserverwp.servicios.EjecutarOperacionResponse;
import com.jde.jdeserverwp.servicios.EjecutarOperacionValores;
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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set; 
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * https://github.com/saturnism/grpc-java-by-example/blob/master/simple-grpc-server/src/main/java/com/example/grpc/server/MyGrpcServer.java
 *
 * @author jgodi
 */
public class JDEServiceImpl extends JDEServiceGrpc.JDEServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(JDEServiceImpl.class);

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
    
    private Configuracion configuracion;
    
    private int counter;
     

    public JDEServiceImpl() {

        logger.info("Iniciando Swagger Server...");

        userDir = System.getProperty("user.dir");

        directorioDeInstalacion = new File(userDir + File.separator + DIR_INSTALACION);
  

    }
    
    @Override
    public void login(com.jde.jdeserverwp.servicios.SessionRequest request,
            io.grpc.stub.StreamObserver<com.jde.jdeserverwp.servicios.SessionResponse> responseObserver) {

        
        logger.info("JDE Login: Begin Login");
          
        // -----------------------------------------
        // Generar Session
        // -----------------------------------------
        //
         
        try {
 
            int sessionID = JDEPoolConnections.getInstance().createConnection(  request.getUser(), 
                                                                                request.getPassword(), 
                                                                                request.getEnvironment(), 
                                                                                request.getRole(), 
                                                                                (int) request.getSessionId(),
                                                                                request.getWsconnection());
           
            SessionResponse response = SessionResponse.newBuilder().setSessionId(sessionID).build();
   
            responseObserver.onNext(response);

            responseObserver.onCompleted();
            
        } catch (JDESingleConnectionException ex) {
             
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

        logger.info("Swagger Login: End Login");

    } 
    
    @Override
    public void logout(com.jde.jdeserverwp.servicios.LogoutRequest request,
            io.grpc.stub.StreamObserver<com.jde.jdeserverwp.servicios.SessionResponse> responseObserver) {

        
        logger.info("JDE Logout: Begin");
          
        // -----------------------------------------
        // Generar Session
        // -----------------------------------------
        //
         
        try {
 
            JDEPoolConnections.getInstance().disconnect((int) request.getSessionId());
             
            SessionResponse response = SessionResponse.newBuilder().setSessionId(0).build();
   
            responseObserver.onNext(response);

            responseObserver.onCompleted();
            
        } catch (JDESingleConnectionException ex) {
             
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

        logger.info("Swagger Login: End Login");

    } 
    
    @Override
    public void isConnected(com.jde.jdeserverwp.servicios.IsConnectedRequest request,
            io.grpc.stub.StreamObserver<com.jde.jdeserverwp.servicios.IsConnectedResponse> responseObserver) {

        
        logger.info("JDE Logout: Begin");
          
        // -----------------------------------------
        // Generar Session
        // -----------------------------------------
        //
         
        try {
 
            int sessionId = JDEPoolConnections.getInstance().getSingleConnection((int) request.getSessionId()).isJDEConnected();
             
            IsConnectedResponse response = IsConnectedResponse.newBuilder().setConnected(sessionId!=0).build();
            
            responseObserver.onNext(response);

            responseObserver.onCompleted();
             
            
        } catch (JDESingleConnectionException ex) {
             
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

        logger.info("Swagger Login: End Login");

    } 
    
    @Override
    public void operaciones(com.jde.jdeserverwp.servicios.OperacionesRequest request,
            io.grpc.stub.StreamObserver<com.jde.jdeserverwp.servicios.OperacionesResponse> responseObserver) {
        
        logger.info("JDE Connector Server. Getting operations");

        String tipoDeOperacion = request.getConnectorName();   // BSFN or WS

        // ================================================
        // Get Session ID
        // ================================================
        //
        
        try {
        
            int sessionID = JDEPoolConnections.getInstance().createConnection(  request.getUser(), 
                                                                                request.getPassword(), 
                                                                                request.getEnvironment(), 
                                                                                request.getRole(), 
                                                                                (int) request.getSessionId(),
                                                                                request.getWsconnection());
      
          
        
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

                OperacionesResponse response = responseBuilder.build();

                responseObserver.onNext(response);

                responseObserver.onCompleted();

            }
        }
        catch (JDESingleConnectionException ex) {
             
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

        
    }
    
    @Override
    public void getMetadaParaOperacion(com.jde.jdeserverwp.servicios.GetMetadataRequest request,
            io.grpc.stub.StreamObserver<com.jde.jdeserverwp.servicios.GetMetadataResponse> responseObserver) {
        
        
        logger.info("JDE Connector Server. Get Metadata for Operation");
        
        String tipoDeOperacion = request.getConnectorName();   // BSFN or WS
         
        try {
        
           // ================================================
           // Get Session ID
           // ================================================
           //

            int sessionID = JDEPoolConnections.getInstance().createConnection(  request.getUser(), 
                                                                                request.getPassword(), 
                                                                                request.getEnvironment(), 
                                                                                request.getRole(), 
                                                                                (int) request.getSessionId(),
                                                                                request.getWsconnection());
         
        
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
        
        
        logger.info("JDE Connector Server. Get Metadata for Operation");
        
        String tipoDeOperacion = request.getConnectorName();   // BSFN or WS
         
        try {
        
           // ================================================
           // Get Session ID
           // ================================================
           //

            int sessionID = JDEPoolConnections.getInstance().createConnection(  request.getUser(), 
                                                                                request.getPassword(), 
                                                                                request.getEnvironment(), 
                                                                                request.getRole(), 
                                                                                (int) request.getSessionId(),
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
                 
                
                
                // --------------------------------------------------
                // Crear el Objeto Mensaje de GetMetadataResponse
                // --------------------------------------------------
                //  
                responseObserver.onNext(responseBuilder.build());

                responseObserver.onCompleted();

            }

        } catch (JDESingleConnectionException ex) {

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
                                    newOperationResponse.setValueAsInteger((int) value);
                                    break;
                                case "java.lang.Boolean":
                                    newOperationResponse.setValueAsBoolean((boolean) value);
                                    break;
                                case "java.lang.Long":
                                    newOperationResponse.setValueAsLong((long) value);
                                    break;
                                case "java.math.BigDecimal":
                                case "java.lang.Double":
                                    
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
                            Double valueDouble = new Double(valor.getValueAsDouble());
                            String strValueD = valueDouble.toString();
                            valorActual = new BigDecimal(strValueD);
                            break;
                        case "BInteger":
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
     
}
