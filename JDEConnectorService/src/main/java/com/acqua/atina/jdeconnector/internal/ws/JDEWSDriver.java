/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acqua.atina.jdeconnector.internal.ws;

import com.atina.metadata.metadata.driver.MetadataWSDriver;
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
import com.acqua.atina.jdeconnector.internal.model.metadata.Model;
import com.acqua.atina.jdeconnector.internal.model.metadata.ModelType;
import com.acqua.atina.jdeconnector.internal.model.metadata.Models;
import com.acqua.atina.jdeconnector.internal.model.metadata.Operation;
import com.acqua.atina.jdeconnector.internal.model.metadata.Operations;
import com.acqua.atina.jdeconnector.internal.model.metadata.ParameterTypeObject;
import com.acqua.atina.jdeconnector.internal.model.metadata.ParameterTypeSimple;
import com.acqua.atina.jdeconnectorservice.JDEConnectorService;
import com.acqua.atina.jdeconnectorservice.exception.JDESingleException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;
import oracle.e1.bssvfoundation.impl.security.E1Principal;
 

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
        
        logger.info("ATINA - JDEWSDriver: bootstrap() Saving Metadata locally ...");
        
        MetadataWSDriver.saveMetadataLocallyFromResource(tmpFolder.getAbsolutePath());
        
        logger.info("ATINA - JDEWSDriver: bootstrap() Loading Operations ...");
        
        operaciones = loadOperacionesMetadata(tmpFolder.getAbsolutePath());
         
        logger.info("ATINA - JDEWSDriver: bootstrap() Loading Models ...");

        models = loadValueObjectsMetadata(tmpFolder.getAbsolutePath());
         
        
    }

    private static class JDEBsfnDriverHolder {

        private JDEBsfnDriverHolder() {
        }

        private static final JDEWSDriver INSTANCE = new JDEWSDriver();
    }
    
    public Set<String> getWSList(File cacheFolder) throws JDESingleException {

        Set<String> wsList = new TreeSet<String>();

        File functionsFile = new File(cacheFolder + File.separator + WS_LIST);

        logger.info("ATINA - JDEWSDriver: getWSList() Checking file: " + cacheFolder + File.separator + WS_LIST + " Exists: " + functionsFile.exists());

        if (functionsFile.exists()) {

            ObjectMapper mapper = new ObjectMapper();

            SimpleModule module = new SimpleModule();

            try {

                mapper.registerModule(module);

                wsList = mapper.readValue(functionsFile, TreeSet.class);

                logger.info("ATINA - JDEWSDriver: getWSList() File Readed. Qty: " + wsList.size());

            } catch (JsonParseException e) {

                logger.error("ATINA - JDEWSDriver: getWSList(): Error parsing file", e);

                throw new JDESingleException("Fail to read functions: " + e.getMessage(), e);

            } catch (JsonMappingException e) {

                logger.error("ATINA - JDEWSDriver: getWSList(): Error Mapping JSON file", e);

                throw new JDESingleException("Fail to read functions: " + e.getMessage(), e);

            } catch (IOException e) {

                logger.error("ATINA - JDEWSDriver: getWSList(): Error reading functions file", e);

                throw new JDESingleException("Fail to read functions: " + e.getMessage(), e);

            } catch (Exception ex) {

                logger.error("ATINA - JDEWSDriver: getWSList(): Error reading functions file", ex);

                throw new JDESingleException("Fail to connect to Oneworld Database " + ex.getMessage(), ex);

            }

        } else {
  
            try {

                // MetadataWSDriver.saveMetadataLocallyFromResource(cacheFolder.getAbsolutePath());
                
                logger.info("ATINA - JDEWSDriver: getWSList() Creating : " + cacheFolder + File.separator + WS_LIST + " Exists: Operations list: " + operaciones.getOperations().size());
                
                Iterator it = operaciones.getOperations().entrySet().iterator();
                
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    System.out.println(pair.getKey() + " = " + pair.getValue());
                    wsList.add(((Operation) pair.getValue()).getOperationModelPackage() + "." + ((Operation) pair.getValue()).getOperationClass() + "." + ((Operation) pair.getValue()).getOperationMethod());
                    //it.remove(); // avoids a ConcurrentModificationException
                }
                
                logger.info("ATINA - JDEWSDriver: getWSList() wsList Size : " + wsList.size());

                // ================
                // Persists List
                // ================
                try {

                    ObjectMapper mapper = new ObjectMapper();

                    mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

                    mapper.writeValue(functionsFile, wsList);

                    logger.info("ATINA - JDEWSDriver: getWSList() Functions saved on "
                            + functionsFile.getAbsoluteFile() + " Size : " + wsList.size() + " Operations : " + operaciones.getOperations().size());

                } catch (Exception e) {

                    logger.error("ATINA - JDEWSDriver: getWSList() Error saving functions on "
                            + functionsFile.getAbsoluteFile());

                    throw new JDESingleException("ATINA - JDEWSDriver: Fail to save functions " + e.getMessage(), e);

                }
 
            } catch (Exception ex) {

                throw new JDESingleException("ATINA - JDEWSDriver: Fail to connect to Oneworld Database " + ex.getMessage(), ex);

            }

        }

        return wsList;

    }
   
    public HashMap<String, ParameterTypeSimple> getWSInputParameter(String operation, File cacheFolder) throws JDESingleException {

        HashMap<String, ParameterTypeSimple> returnValue = null;
 

        // ================================================
        // Get Input Parameter Type
        // ================================================
        // 
        logger.info("----------------------------------------------------");
        logger.info("ATINA - JDEWSDriver getWSInputParameter: Operation [" + operation + "] Operations Size: " + operaciones.getOperations().size());
        
        String inputModel = operaciones.getInputValueObject(operation);

        if (!inputModel.isEmpty()) {

            logger.info("Getting Input Model : " + inputModel);
            
            returnValue = processModel(models, models.getModelo(inputModel),0);
            
            logHashMapAsJson(returnValue);
             
            logger.info("Getting Input Model Done");

        }
        else
        {
            logger.error("Operation without Input Parameter: " + operation);
            throw new JDESingleException("Operation without Input Parameter");
        }

        return returnValue;
    }
    
    public HashMap<String, ParameterTypeSimple> getWSOutputParameter(String operation, File cacheFolder) throws JDESingleException {

        HashMap<String, ParameterTypeSimple> returnValue = null;
  
        // ================================================
        // Get Input Parameter Type
        // ================================================
        // 
        logger.info("----------------------------------------------------");
        logger.info("ATINA - JDEWSDriver getWSOutputParameter: Operation [" + operation + "] Operations: " + operaciones.getOperations().size());
        
        String inputModel = operaciones.getOutputValueObject(operation);

        if (!inputModel.isEmpty()) {
            
             logger.info("Getting Output Model : " + inputModel);
               
            returnValue = processModel(models, models.getModelo(inputModel),0);
            
            logHashMapAsJson(returnValue);
            
            logger.info("Getting Output Model Done");

        }
        else
        {
            logger.error("Operation without Output Parameter: " + operation);
            
            throw new JDESingleException("Operation without Output Parameter");
        }

        return returnValue;
    }
    
    private void logHashMapAsJson(HashMap returnValue) {

        String e1ReturnValueAsJson = "";

        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(MapperFeature.USE_ANNOTATIONS, false);

        try {

            e1ReturnValueAsJson = mapper.writeValueAsString(returnValue);

            logger.info("         Input Model : " + e1ReturnValueAsJson);

        } catch (JsonProcessingException exj) {

            logger.error("Error converting Input Model to String: " + exj.getMessage());

        }

    }
      
      private HashMap<String, ParameterTypeSimple> processModel(Models models, Model parameters, int level) throws JDESingleException {
      
        HashMap<String, ParameterTypeSimple> returnValue = new HashMap<String, ParameterTypeSimple>();

        level++;
        
        if (parameters != null) {
             
            Collection<ModelType> param = parameters.getParametersType().values();

            for (ModelType modelType : param) {
                
                if (!models.isModel(modelType.getParameterType())) {
                    
                    if (modelType.getParameterType().equals("oracle.e1.bssvfoundation.util.MathNumeric")) {
                        
                        returnValue.put(modelType.getParameterName(), new ParameterTypeSimple("java.math.BigDecimal", modelType.getParameterSequence(),modelType.isRepetead()));
                        
                    } else {
                        
                        returnValue.put(modelType.getParameterName(), new ParameterTypeSimple(modelType.getParameterType(), modelType.getParameterSequence(),modelType.isRepetead()));
                        
                    }
                    
                } else {
                    
                    ParameterTypeObject parameterObject = new ParameterTypeObject(modelType.getParameterType(),modelType.getParameterSequence(),modelType.isRepetead());

                    parameterObject.addParameterType(modelType.getParameterName(), processModel(models, models.getModelo(modelType.getParameterType()),level));
                
                    returnValue.put(modelType.getParameterName(), parameterObject);

                }

            }

        }
        
        return returnValue;
    }
      
    
    public synchronized HashMap<String, Object> callJDEWS(E1Principal e1ppal, int session, String operation, HashMap<String, Object> inputValues,File cacheFolder)
    {
        
        // ================================================
        // Get Input Object
        // ================================================
        //
        logger.info("Call JDE WS. Operation: " + operation); 
        
        String inputModelClass = operaciones.getInputValueObject(operation);
        
        logger.info("Call JDE WS. Input Model Class: " + inputModelClass);
        
        Model inputModelMetadata = models.getModelo(inputModelClass);
        
        Object inputObject = JDEWSCreateObjectUtil.generateObject(inputModelClass, models, inputModelMetadata,inputValues);
         
        // ================================================
        // Invoke Method
        // ================================================
        //
        
        HashMap<String, Object> outputObject = JDEWSCreateAndInvokeWS.invokeObject(e1ppal,operaciones,models,operation,inputObject);
         
        
        return outputObject;
    }
     
    
    public Operations loadOperacionesMetadata(String metadataDir) throws JDESingleException {

        Operations returnValue = null;
        
        ObjectMapper objectMapper = new ObjectMapper();
        
        try {

            logger.info("Reading Operations: " + metadataDir + File.separator + WS_JSON);
            
            returnValue= objectMapper.readValue(new File(metadataDir + File.separator + WS_JSON), Operations.class);
            
            logger.info("Operations: " + metadataDir + File.separator + WS_JSON + " readed. Qty: " + returnValue.getOperations().size() );

        } catch (IOException ex) {

            logger.error("Error leyendo metadata de transacciones: " + ex.getMessage());

            throw new JDESingleException("Error reading operation metadata: " + ex.getMessage(), ex);

        }
 
        return returnValue;
    }
    
    public Models loadValueObjectsMetadata(String metadataDir) throws JDESingleException {

        Models returnValue = null;
        
        ObjectMapper objectMapper = new ObjectMapper();
        
        try {

            logger.info("Reading Models: " + metadataDir + File.separator + WS_JSON);
            
            returnValue= objectMapper.readValue(new File(metadataDir + File.separator + VO_JSON), Models.class);
            
            logger.info("Models: " + metadataDir + File.separator + VO_JSON + " readed. Qty: " + returnValue.getModels().size() );

        } catch (IOException ex) {

            logger.error("Error leyendo metadata de transacciones: " + ex.getMessage());

            throw new JDESingleException("Error reading operation metadata: " + ex.getMessage(), ex);

        }
 
        return returnValue;
    }

    public Models getModels() {
        return models;
    }

    public Operations getOperaciones() {
        return operaciones;
    }
     
}
