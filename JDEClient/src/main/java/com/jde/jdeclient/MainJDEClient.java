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
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jgodi
 */
public class MainJDEClient {

    private static final Logger logger = LoggerFactory.getLogger(MainJDEClient.class);

    public void iniciarAplicacion(String[] args) throws Exception {

        Configuracion configuracion = new Configuracion();

        configuracion.setServidorServicio("localhost"); // Servidor Hub
        configuracion.setPuertoServicio(8085); // Puerto Servidor Hub

        configuracion.setUser("JDE");
        configuracion.setPassword("Modus2017!");
        configuracion.setEnvironment("JDV920");
        configuracion.setRole("*ALL");
        configuracion.setSession(new Integer(0));

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
                            .build());

            sessionID = (int) tokenResponse.getSessionId();
            
            System.out.println("Logeado con Session [" + tokenResponse.getSessionId() + "]");

        } catch (Exception ex) {

            logger.error("Error ejecutando metodo ");

            throw new RuntimeException("Error Logeando", null);

        }
        
//        // ===========================  
//        // Get Operations                       
//        // ===========================  
//        //
//        try {
//
//            OperacionesResponse operaciones = stub.operaciones(
//                    OperacionesRequest.newBuilder()
//                            .setConnectorName("BSFN")
//                            .setUser(configuracion.getUser())
//                            .setPassword(configuracion.getPassword())
//                            .setEnvironment(configuracion.getEnvironment())
//                            .setRole(configuracion.getRole())
//                            .setSessionId(sessionID)
//                            .build());
//
//            for(Operacion operacion: operaciones.getOperacionesList())
//            {
//                
//                System.out.println("Operacion [" + operacion.getNombreOperacion() + "]");
//                
//            }
//
//        } catch (Exception ex) {
//
//            logger.error("Error ejecutando metodo ");
//
//            throw new RuntimeException("Error Logeando", null);
//
//        }
//        
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
                            .setOperacionKey("AddressBookMasterMBF")
                            .build());

            for (TipoDelParametroInput parameter : operaciones.getListaDeParametrosInputList()) {

                System.out.println("Operacion [" + parameter.getNombreDelParametro() + "]");
                System.out.println("Operacion [" + parameter.getSecuencia() + "]"); 
                System.out.println("Operacion [" + parameter.getTipoDelParametroJava() + "]"); 
                System.out.println("Operacion [" + parameter.getTipoDelParametroMule() + "]");

            }

        } catch (Exception ex) {

            logger.error("Error ejecutando metodo ");

            throw new RuntimeException("Error Logeando", null);

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
