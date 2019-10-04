/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.jdeconnector.internal.ws;

import com.atila.metadata.metadata.driver.MetadataWSDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule; 
import com.atina.jdeconnector.internal.model.metadata.Model;
import com.atina.jdeconnector.internal.model.metadata.ModelType;
import com.atina.jdeconnector.internal.model.metadata.Models;
import com.atina.jdeconnector.internal.model.metadata.Operation;
import com.atina.jdeconnector.internal.model.metadata.Operations;
import com.atina.jdeconnector.internal.model.metadata.SimpleParameterType;
import com.atina.jdeconnectorservice.JDEConnectorService;
import com.atina.jdeconnectorservice.exception.JDESingleBSFNException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

/**
 *
 * @author franciscogodinoconte
 */
public class JDEWSDriver {

    private static final Logger logger = LoggerFactory.getLogger(JDEConnectorService.class);

    private static final String WS_LIST = "wslist.json";
    private static String WS_JSON = "ws.json";
    private static String VO_JSON = "vo.json";  
    
    private Models models;
    private Operations operaciones;

    public static JDEWSDriver getInstance() {
        return JDEBsfnDriverHolder.INSTANCE;
    }
    
    public void bootstrap(File tmpFolder) throws IOException {
        
        // ================================================
        // Load Operations and Value Objects
        // ================================================
        // 
        
        MetadataWSDriver.saveMetadataLocallyFromResource(tmpFolder.getAbsolutePath());
        
        this.operaciones = loadOperacionesMetadata(tmpFolder);

        this.models = loadValueObjectsMetadata(tmpFolder);
        
    }

    private static class JDEBsfnDriverHolder {

        private JDEBsfnDriverHolder() {
        }

        private static final JDEWSDriver INSTANCE = new JDEWSDriver();
    }
    
    public Set<String> getWSList(File cacheFolder) throws JDESingleBSFNException {

        Set<String> wsList = new TreeSet<String>();

        File functionsFile = new File(cacheFolder + File.separator + WS_LIST);

        logger.info("MULESOFT - SpecsGenerator: BussinessFunctionList() Checking file: " + cacheFolder + File.separator + WS_LIST);

        if (functionsFile.exists()) {

            ObjectMapper mapper = new ObjectMapper();

            SimpleModule module = new SimpleModule();

            try {

                mapper.registerModule(module);

                wsList = mapper.readValue(functionsFile, TreeSet.class);

                logger.info("MULESOFT - SpecsGenerator: BussinessFunctionList() File Readed");

            } catch (JsonParseException e) {

                logger.error("MULESOFT - SpecsGenerator: Error parsing file", e);

                throw new JDESingleBSFNException("Fail to read functions: " + e.getMessage(), e);

            } catch (JsonMappingException e) {

                logger.error("MULESOFT - SpecsGenerator: Error Mapping JSON file", e);

                throw new JDESingleBSFNException("Fail to read functions: " + e.getMessage(), e);

            } catch (IOException e) {

                logger.error("MULESOFT - SpecsGenerator: Error reading functions file", e);

                throw new JDESingleBSFNException("Fail to read functions: " + e.getMessage(), e);

            } catch (Exception ex) {

                logger.error("MULESOFT - SpecsGenerator: Error reading functions file", ex);

                throw new JDESingleBSFNException("Fail to connect to Oneworld Database " + ex.getMessage(), ex);

            }

        } else {
  
            try {

                MetadataWSDriver.saveMetadataLocallyFromResource(cacheFolder.getAbsolutePath());
                
                Iterator it = operaciones.getOperations().entrySet().iterator();
                
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    System.out.println(pair.getKey() + " = " + pair.getValue());
                    wsList.add(((Operation) pair.getValue()).getOperationModelPackage() + "." + ((Operation) pair.getValue()).getOperationClass() + "." + ((Operation) pair.getValue()).getOperationMethod());
                    it.remove(); // avoids a ConcurrentModificationException
                }
                   
                // ================
                // Persists List
                // ================
                try {

                    ObjectMapper mapper = new ObjectMapper();

                    mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

                    mapper.writeValue(functionsFile, wsList);

                    logger.info("MULESOFT - SpecsGenerator: BussinessFunctionList() Functions saved on "
                            + functionsFile.getAbsoluteFile());

                } catch (Exception e) {

                    logger.error("MULESOFT - SpecsGenerator: BussinessFunctionList() Error saving functions on "
                            + functionsFile.getAbsoluteFile());

                    throw new JDESingleBSFNException("Fail to save functions " + e.getMessage(), e);

                }
 
            } catch (Exception ex) {

                throw new JDESingleBSFNException("Fail to connect to Oneworld Database " + ex.getMessage(), ex);

            }

        }

        return wsList;

    }
   
    public HashMap<String, Object> getWSInputParameter(String operation, File cacheFolder) throws JDESingleBSFNException {

        HashMap<String, Object> returnValue = null;
 

        // ================================================
        // Get Input Parameter Type
        // ================================================
        // 
        String inputModel = this.operaciones.getInputValueObject(operation);

        if (!inputModel.isEmpty()) {

            returnValue = processModel(models, models.getModelo(inputModel));

        }
        else
        {
            throw new JDESingleBSFNException("Operation without Input Parameter");
        }

        return returnValue;
    }
    
    public HashMap<String, Object> getWSOutputParameter(String operation, File cacheFolder) throws JDESingleBSFNException {

        HashMap<String, Object> returnValue = null;
  
        // ================================================
        // Get Input Parameter Type
        // ================================================
        // 
        String inputModel = this.operaciones.getOutputValueObject(operation);

        if (!inputModel.isEmpty()) {

            returnValue = processModel(models, models.getModelo(inputModel));

        }
        else
        {
            throw new JDESingleBSFNException("Operation without Output Parameter");
        }

        return returnValue;
    }
    
      
      private HashMap<String, Object> processModel(Models models, Model parameters) throws JDESingleBSFNException {
      
        HashMap<String, Object> returnValue = new HashMap<String, Object>();

        if (parameters != null) {
            
            logger.info("Process Model " + parameters.toString());

            Collection<ModelType> param = parameters.getParametersType().values();

            for (ModelType modelType : param) {
                if (!models.isModel(modelType.getParameterType())) {
                    if (modelType.getParameterType().equals("oracle.e1.bssvfoundation.util.MathNumeric")) {
                        returnValue.put(modelType.getParameterName(), new SimpleParameterType("java.math.BigDecimal", modelType.getParameterSequence()));
                    } else {
                        returnValue.put(modelType.getParameterName(), new SimpleParameterType(modelType.getParameterType(), modelType.getParameterSequence()));
                    }
                } else {

                    returnValue.put(modelType.getParameterName(), processModel(models, models.getModelo(modelType.getParameterType())));

                }

            }

        }

        return returnValue;
    }
      
    
    public synchronized HashMap<String, Object> callJDEWS(int session, String operation, HashMap<String, Object> inputValues,File cacheFolder)
    {
        
        // ================================================
        // Get Input Object
        // ================================================
        //
        String inputModelClass = this.operaciones.getInputValueObject(operation);
        
        Model inputModelMetadata = models.getModelo(inputModelClass);
        
        Object inputObject = JDEWSCreateObjectUtil.generateObject(inputModelClass, models, inputModelMetadata,inputValues);
        
        
        // ================================================
        // Get Output Object
        // ================================================
        //
        
        
        // ================================================
        // Instance Input Object
        // ================================================
        //
        
        
        
        
        
        return null;
    }
    
    private Object createInputObject(Object inputObject) {
        
        
        
        
        return null;
        
        
    }
    
    private Operations loadOperacionesMetadata(File metadataDir) throws JDESingleBSFNException {

        Operations returnValue = null;
        
        ObjectMapper objectMapper = new ObjectMapper();
        
        try {

            returnValue= objectMapper.readValue(new File(metadataDir + File.separator + WS_JSON), Operations.class);

        } catch (IOException ex) {

            logger.error("Error leyendo metadata de transacciones: " + ex.getMessage());

            throw new JDESingleBSFNException("Error reading operation metadata: " + ex.getMessage(), ex);

        }
 
        return returnValue;
    }
    
    private Models loadValueObjectsMetadata(File metadataDir) throws JDESingleBSFNException {

        Models returnValue = null;
        
        ObjectMapper objectMapper = new ObjectMapper();
        
        try {

            returnValue= objectMapper.readValue(new File(metadataDir + File.separator + VO_JSON), Models.class);

        } catch (IOException ex) {

            logger.error("Error leyendo metadata de transacciones: " + ex.getMessage());

            throw new JDESingleBSFNException("Error reading operation metadata: " + ex.getMessage(), ex);

        }
 
        return returnValue;
    }
     

}
