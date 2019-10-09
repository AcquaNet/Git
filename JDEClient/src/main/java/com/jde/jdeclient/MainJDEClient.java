/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jde.jdeclient;

import com.jde.jdeclient.configuracion.Configuracion;
import com.jde.jdeserverwp.servicios.GetMetadataRequest;
import com.jde.jdeserverwp.servicios.GetMetadataResponse;
import com.jde.jdeserverwp.servicios.JDEServiceGrpc;
import com.jde.jdeserverwp.servicios.JDEServiceGrpc.JDEServiceBlockingStub;
import com.jde.jdeserverwp.servicios.Operacion;
import com.jde.jdeserverwp.servicios.OperacionesRequest;
import com.jde.jdeserverwp.servicios.OperacionesResponse;
import com.jde.jdeserverwp.servicios.SessionRequest;
import com.jde.jdeserverwp.servicios.SessionResponse;
import com.jde.jdeserverwp.servicios.TipoDelParametroInput;
import com.jde.jdeserverwp.servicios.TipoDelParametroOutput;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jgodi
 */
public class MainJDEClient {

    private static final Logger logger = LoggerFactory.getLogger(MainJDEClient.class);
    
    private static final Boolean testWS = Boolean.TRUE;
    private static final Boolean testBSFN = Boolean.FALSE;

    public void iniciarAplicacion(String[] args) throws Exception {

        Configuracion configuracion = new Configuracion();

        configuracion.setServidorServicio("localhost"); // Servidor Hub
        configuracion.setPuertoServicio(8085); // Puerto Servidor Hub

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
                    logger.info("Operacion [" + parameter.getSecuencia() + "]");
                    logger.info("Operacion [" + parameter.getTipoDelParametroJava() + "]");
                    logger.info("Operacion [" + parameter.getTipoDelParametroMule() + "]");
                    
                     
                }

            } catch (Exception ex) {

                logger.error("Error ejecutando metodo ");

                throw new RuntimeException("Error Logeando", ex);

            }
        
        }
        
        if(testWS)
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
            
        }

    }
    
    private void printParameter(TipoDelParametroInput parameter, int level)
    {
        level++;
        
        String space = StringUtils.repeat(".", level * 2);
         
        logger.info(space + "Parameter Name: [" + parameter.getNombreDelParametro() + "]" + " Type [" + parameter.getTipoDelParametroJava() + "]");
        
         
                
            for(TipoDelParametroInput input:parameter.getSubParametroList())
            {
                if(!input.getNombreDelParametro().isEmpty())
                {
                    printParameter(input, level);
                }
                
            }
          
        
    }
    
    private void printParameterOutput(TipoDelParametroOutput parameter, int level)
    {
        level++;
        
        String space = StringUtils.repeat(".", level * 2);
         
        logger.info(space + "Parameter Name: [" + parameter.getNombreDelParametro() + "]" + " Type [" + parameter.getTipoDelParametroJava() + "]");
        
        
            
              
            for(TipoDelParametroOutput input:parameter.getSubParametroList())
            {
                if(!input.getNombreDelParametro().isEmpty())
                {
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

}
