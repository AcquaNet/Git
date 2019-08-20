/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jde.jdeconnector.services;


import com.jde.jdeconnectorserver.model.Configuracion;
import com.jde.jdeserverwp.servicios.JDEServiceGrpc;
import java.io.File;
import java.util.HashSet;
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
    private static String SOURCE_API = "srcApi"; 
    private static String DRAGONFISH_JARS = "DragonFish";

    private String userDir;
    private File directorioDeInstalacion; 

    private String claseDeLaOperacion = "";
    private String metodoDeLaOperacion = "";
    
    private Configuracion configuracion;
    
    private int counter;
    
    private Set<String> jarsLoaded;

    public JDEServiceImpl() {

        logger.info("Iniciando Swagger Server...");

        userDir = System.getProperty("user.dir");

        directorioDeInstalacion = new File(userDir + File.separator + DIR_INSTALACION);
 
        jarsLoaded = new HashSet();

    }
 

}
