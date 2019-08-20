/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jde.jdeconnector.services;


import com.atina.jdeconnectorservice.exception.JDESingleConnectionException;
import com.atina.jdeconnectorservice.service.JDEPoolConnections;
import com.atina.jdeconnectorservice.service.JDESingleConnection;
import com.jde.jdeconnectorserver.model.Configuracion;
import com.jde.jdeserverwp.servicios.JDEServiceGrpc;
import com.jde.jdeserverwp.servicios.Operacion;
import com.jde.jdeserverwp.servicios.OperacionesResponse;
import com.jde.jdeserverwp.servicios.SessionResponse; 
import io.grpc.Status;
import java.io.File;
import java.util.Set;
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
 
            int sessionID = JDEPoolConnections.getInstance().createConnection(request.getUser(), request.getPassword(), request.getEnvironment(), request.getRole(), (int) request.getSessionId());
           
            SessionResponse response = SessionResponse.newBuilder().setSessionId(sessionID).build();
   
            responseObserver.onNext(response);

            responseObserver.onCompleted();
            
        } catch (JDESingleConnectionException ex) {
             
            StringBuilder sb = new StringBuilder();
            sb.append("Error Autenticando Usuario");
            sb.append("|");
            sb.append(ex.getMessage());
            sb.append("|%ExternalServiceException%");
 
            responseObserver.onError(Status.INTERNAL
                    .withDescription(sb.toString())
                    .withCause(ex)
                    .asRuntimeException());
 
      
        } catch (Exception ex) {
            
            String msg = "Error Swagger Server: " + ex.getMessage();
            logger.error(msg, ex);
            
            StringBuilder sb = new StringBuilder();
            sb.append("Error Autenticando Usuario");
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
        
        int sessionID = JDEPoolConnections.getInstance()
                            .createConnection(  request.getUser(), 
                                                request.getPassword(), 
                                                request.getEnvironment(), 
                                                request.getRole(), 
                                                (int) request.getSessionId());
      
          
        
        // ================================================
        // Get Single Connection
        // ================================================
        //
        JDESingleConnection singleConnection = JDEPoolConnections.getInstance().getSingleConnection(sessionID);
        
        if (tipoDeOperacion.equals(TIPO_BSFN)) {

            // ================================================
            // Get Operations
            // ================================================
            // 
            Set<String> bsfnList = singleConnection.generateBSFNListFromCacheRepository();

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
    
    @Override
    public void getMetadaParaOperacion(com.jde.jdeserverwp.servicios.GetMetadataRequest request,
            io.grpc.stub.StreamObserver<com.jde.jdeserverwp.servicios.GetMetadataResponse> responseObserver) {
        
        
        
    }
    
    @Override
    public void ejecutarOperacion(com.jde.jdeserverwp.servicios.EjecutarOperacionRequest request,
            io.grpc.stub.StreamObserver<com.jde.jdeserverwp.servicios.EjecutarOperacionResponse> responseObserver) {
        
        
        
        
        
        
    }
    
    

}
