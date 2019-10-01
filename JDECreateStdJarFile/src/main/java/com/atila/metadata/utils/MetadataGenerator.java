/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atila.metadata.utils;


import com.atina.metadata.models.Modelos;
import com.atina.metadata.models.Operaciones;
import com.atina.metadata.models.Transacciones;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;  
import java.util.HashMap;
import java.util.Map;
import com.atina.buildjdebundle.exceptions.MetadataServerException;  

/**
 *
 * @author jgodi
 */
public class MetadataGenerator {

    private static final Logger logger = LoggerFactory.getLogger(MetadataGenerator.class);

    private static String TRANSACCIONES_JSON = "transacciones.json";
    private static String OPERACIONES_JSON = "operaciones.json";
    private static String MODELOS_JSON = "modelos.json";

    private ObjectMapper objectMapper;
    private Map<String,Transacciones> transacciones;
    private Map<String,Operaciones> operaciones;
    private Map<String,Modelos> modelos;

    private MetadataGenerator() {
        objectMapper = new ObjectMapper();
        transacciones = new HashMap();
        operaciones = new HashMap();
        modelos = new HashMap();
    }

    public static MetadataGenerator getInstance() {
        return MetadataGeneratorHolder.INSTANCE;
    }

    private static class MetadataGeneratorHolder {

        private static final MetadataGenerator INSTANCE = new MetadataGenerator();
    }

    public void saveMetadata(File metadataDir, Transacciones transacciones, Operaciones operaciones, Modelos modelos) throws MetadataServerException  {

        logger.info("Serializando Metadata:");
 
        try {

            if (!metadataDir.exists()) {
                metadataDir.mkdir();
            }

            ObjectMapper objectMapper = new ObjectMapper();

            objectMapper.writeValue(new File(metadataDir.getAbsolutePath() + File.separator + TRANSACCIONES_JSON), transacciones);

            objectMapper.writeValue(new File(metadataDir.getAbsolutePath() + File.separator + OPERACIONES_JSON), operaciones);

            objectMapper.writeValue(new File(metadataDir.getAbsolutePath() + File.separator + MODELOS_JSON), modelos);

            logger.info("Serializado");

        } catch (Exception ex) {

            logger.error("Error Serializando Metadata: " + ex.getMessage());

            throw new MetadataServerException("Error Serializando Metadata: " + ex.getMessage(), ex);

        }

    }

    public void loadMetadata(String connectorName, File metadataDir) throws MetadataServerException {

        loadTransaccionesMetadata(connectorName, metadataDir);
        
        loadOperacionesMetadata(connectorName, metadataDir);
        
        loadModelosMetadata(connectorName, metadataDir);
    }
    
    public void loadTransaccionesMetadata(String connectorName, File metadataDir) throws MetadataServerException {

        try {

            transacciones.put(connectorName, objectMapper.readValue(new File(metadataDir + File.separator + TRANSACCIONES_JSON), Transacciones.class));

        } catch (IOException ex) {

            logger.error("Error leyendo metadata de transacciones: " + ex.getMessage());

            throw new MetadataServerException("Error leyendo metadata de transacciones: " + ex.getMessage(), ex);

        }
 
    }
    
    public void loadOperacionesMetadata(String connectorName, File metadataDir) throws MetadataServerException  {

        try {

            operaciones.put(connectorName, objectMapper.readValue(new File(metadataDir + File.separator  + OPERACIONES_JSON), Operaciones.class));

        } catch (IOException ex) {

            logger.error("Error leyendo metadata de operaciones: " + ex.getMessage());

            throw new MetadataServerException("Error leyendo metadata de operaciones: " + ex.getMessage(), ex);

        }
 
    }
    
     public void loadModelosMetadata(String connectorName, File metadataDir) throws MetadataServerException {

         try {

            modelos.put(connectorName, objectMapper.readValue(new File(metadataDir +  File.separator + MODELOS_JSON), Modelos.class));

        } catch (IOException ex) {

            logger.error("Error leyendo metadata de modelos: " + ex.getMessage());

            throw new MetadataServerException("Error leyendo metadata de modelos: " + ex.getMessage(), ex);

        }
 
    }

    public Transacciones getTransacciones(String connectorName, File metadataDir) throws MetadataServerException {

        if (!transacciones.containsKey(connectorName)) {

              loadTransaccionesMetadata(connectorName,metadataDir);
        } 

        return this.transacciones.get(connectorName);
    }

    public Operaciones getOperaciones(String connectorName, File metadataDir) throws MetadataServerException {

        if (!operaciones.containsKey(connectorName)) {

            loadOperacionesMetadata(connectorName,metadataDir);
        } 

        return this.operaciones.get(connectorName);
    }

    public Modelos getModelos(String connectorName, File metadataDir) throws MetadataServerException  {

        if (!modelos.containsKey(connectorName)) {

            loadModelosMetadata(connectorName, metadataDir);
            
        } 

        return this.modelos.get(connectorName);
    }

}
