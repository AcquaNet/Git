/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jde.jdeconnector.services;


import com.atina.jdeconnector.internal.model.JDEBsfnParameter;
import com.atina.jdeconnectorservice.exception.JDESingleConnectionException;
import com.atina.jdeconnectorservice.service.poolconnection.JDEPoolConnections;
import com.atina.jdeconnectorservice.service.JDESingleConnection;
import com.jde.jdeconnectorserver.model.Configuracion;
import com.jde.jdeserverwp.servicios.GetMetadataResponse;
import com.jde.jdeserverwp.servicios.JDEServiceGrpc;
import com.jde.jdeserverwp.servicios.Operacion;
import com.jde.jdeserverwp.servicios.OperacionesResponse;
import com.jde.jdeserverwp.servicios.SessionResponse; 
import com.jde.jdeserverwp.servicios.TipoDelParametroInput;
import io.grpc.Status;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
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
        
        try {
        
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
                    parametrosInputN.setTipoDelParametroMule(parametro.getDataType());
                    parametrosInputN.setTipoDelParametroJava(parametro.getDataType());
                    parametrosInputN.setSecuencia(Integer.toString(x++));
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

        } catch (JDESingleConnectionException ex) {

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
    public void ejecutarOperacion(com.jde.jdeserverwp.servicios.EjecutarOperacionRequest request,
            io.grpc.stub.StreamObserver<com.jde.jdeserverwp.servicios.EjecutarOperacionResponse> responseObserver) {
        
        
        
        
        
        
    }
    
    private ArrayList<TipoDelParametroInput.Builder> generarParametrosDeInput(HashMap<String, Object> parametrosInput, int nivelRequerido) {

        // ---------------------------------------------------
        // Por cada Paramtro de Output del Metadata
        // ---------------------------------------------------
        //
        ArrayList<TipoDelParametroInput.Builder> parametrosDelNivel = new ArrayList();

        // ---------------------------------------------------
        // Por cada Paramtro de Output del Metadata
        // ---------------------------------------------------
        //
        for (Map.Entry<String, Object> entrada : parametrosInput.entrySet()) {

            // ---------------------------------------------------
            // Obtengo la Secuencia y Nivel
            // ---------------------------------------------------
            //
            String secuencia = (String) ((Map<String, Object>) entrada.getValue()).get("secuencia");

            int nivel = Integer.parseInt(obtenerNivel(secuencia));

            if (nivel == nivelRequerido) {

                // ---------------------------------------------------
                // Obtengo el nombre de la variable
                // ---------------------------------------------------
                //
                String key = entrada.getKey();

                // ------------------------------------------------------------
                // Obtengo los valores que necesito para generar la respuesta
                // ------------------------------------------------------------
                //
                String tipoParametroMule = (String) ((Map<String, Object>) entrada.getValue()).get("tipoDelParametroMule");
                String tipoParametroJava = (String) ((Map<String, Object>) entrada.getValue()).get("tipoDelParametro");

                HashMap<String, Object> parametros = (HashMap<String, Object>) ((Map<String, Object>) entrada.getValue()).get("parametros");

                logger.info("      Parametro: " + StringUtils.repeat(" > ", nivelRequerido) + key + " / " + tipoParametroMule + " / {" + tipoParametroJava + "} / " + secuencia);

                // ---------------------------------------------------------
                // Si no es un tipoDeVariableDelResponse con subparametros
                // ---------------------------------------------------------
                //
                if (parametros.isEmpty()) {

                    TipoDelParametroInput.Builder parametrosInputN = TipoDelParametroInput.newBuilder();;

                    // ---------------------------------------------------------
                    // Inicio los valores 
                    // ---------------------------------------------------------
                    //
                    parametrosInputN.setNombreDelParametro(key);
                    parametrosInputN.setTipoDelParametroMule(tipoParametroMule);
                    parametrosInputN.setTipoDelParametroJava(tipoParametroJava);
                    parametrosInputN.setSecuencia(secuencia);
                    parametrosInputN.addSubParametro(TipoDelParametroInput.newBuilder().build());

                    // ---------------------------------------------------------
                    // Agrego al response el Parametro generado 
                    // ---------------------------------------------------------
                    //
                    parametrosDelNivel.add(parametrosInputN);

                } else {

                    // ---------------------------------------------------------
                    // Si tiene parametrosInputBuilder (Lista o Map)
                    // ---------------------------------------------------------
                    //
                    TipoDelParametroInput.Builder parametrosInputN = TipoDelParametroInput.newBuilder();

                    parametrosInputN.setNombreDelParametro(key);
                    parametrosInputN.setTipoDelParametroMule(tipoParametroMule);
                    parametrosInputN.setTipoDelParametroJava(tipoParametroJava);
                    parametrosInputN.setSecuencia(secuencia);

                    ArrayList<TipoDelParametroInput.Builder> subParametros = generarParametrosDeInput(parametros, nivelRequerido + 1);

                    for (TipoDelParametroInput.Builder subParametro : subParametros) {
                        parametrosInputN.addSubParametro(subParametro.build());
                    }

                    parametrosDelNivel.add(parametrosInputN);

                }

            }

        }

        return parametrosDelNivel;

    }
    
    private String obtenerNivel(String levelN) {

        StringTokenizer elementos = new StringTokenizer(levelN, ".");

        return elementos.nextToken();
    }

}
