/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jde.jdeconnector.services;


import com.atina.jdeconnectorservice.exception.JDESingleConnectionException;
import com.atina.jdeconnectorservice.service.JDEPoolConnections;
import com.jde.jdeconnectorserver.model.Configuracion;
import com.jde.jdeserverwp.servicios.JDEServiceGrpc;
import com.jde.jdeserverwp.servicios.SessionResponse; 
import io.grpc.Status;
import java.io.File;
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
        
        String token = "";

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

}
