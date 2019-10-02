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
import java.util.Properties;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jdedwards.database.base.JDBComparisonOp;
import com.jdedwards.database.base.JDBCompositeSelection;
import com.jdedwards.database.base.JDBDatabaseAccess;
import com.jdedwards.database.base.JDBException;
import com.jdedwards.database.base.JDBField;
import com.jdedwards.database.base.JDBFieldComparison;
import com.jdedwards.database.base.JDBFieldMap;
import com.jdedwards.database.base.JDBResultSet;
import com.jdedwards.database.jdb.JDBSystem;
import com.jdedwards.database.services.serviceobj.F9862;
import com.jdedwards.services.ServiceException;
import com.jdedwards.services.objectlookup.ObjectLookupService;
import com.jdedwards.services.objectlookup.ObjectLookupServiceLoader;
import com.jdedwards.system.connector.dynamic.Connector;
import com.jdedwards.system.connector.dynamic.OneworldTransaction;
import com.jdedwards.system.connector.dynamic.UserSession;
import com.jdedwards.system.connector.dynamic.callmethod.ExecutableMethod;
import com.jdedwards.system.connector.dynamic.spec.SpecFailureException;
import com.jdedwards.system.connector.dynamic.spec.dbservices.BSFNLookupFailureException;
import com.jdedwards.system.connector.dynamic.spec.dictionary.OneworldSpecDictionary;
import com.jdedwards.system.connector.dynamic.spec.dictionary.SpecDictionary;
import com.jdedwards.system.connector.dynamic.spec.source.BSFNMethod;
import com.jdedwards.system.connector.dynamic.spec.source.BSFNParameter;
import com.jdedwards.system.connector.dynamic.spec.source.BSFNSpecSource;
import com.jdedwards.system.connector.dynamic.spec.source.ImageBSFNSpecSource;
import com.jdedwards.system.connector.dynamic.spec.source.OneworldBSFNSpecSource;
import com.jdedwards.system.connector.dynamic.util.SpecImageGenerator;
import com.jdedwards.system.connector.dynamic.util.SpecImageValidator;
import com.jdedwards.system.connector.dynamic.util.SpecImageValidator.ValidationResultSet;
import com.jdedwards.system.kernel.CallObjectErrorList;
import com.jdedwards.system.security.UserOCMContextSession;
import com.atina.jdeconnector.internal.model.JDEBsfnParametersOutputObject;
import com.atina.jdeconnector.internal.model.JDEBsfnParameter;
import com.atina.jdeconnector.internal.model.JDEBsfnParametersInputObject;
import com.atina.jdeconnector.internal.model.metadata.Operation;
import com.atina.jdeconnector.internal.model.metadata.Operations;
import com.atina.jdeconnectorservice.JDEConnectorService;
import com.atina.jdeconnectorservice.exception.JDESingleBSFNException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.TreeSet;
import java.util.logging.Level;

/**
 *
 * @author franciscogodinoconte
 */
public class JDEWSDriver {

    private static final Logger logger = LoggerFactory.getLogger(JDEConnectorService.class);

    private static final String WS_LIST = "wslist.json";
    private static String WS_JSON = "ws.json";
    private static String VO_JSON = "vo.json"; 

    public static JDEWSDriver getInstance() {
        return JDEBsfnDriverHolder.INSTANCE;
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
                
                Operations operaciones = loadOperacionesMetadata(cacheFolder);
                
                for(Operation oper: operaciones.getOperations())
                {
                    
                    wsList.add(oper.getOperationClass() + "_" + oper.getOperationMethod());
                    
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
    
    public Operations loadOperacionesMetadata(File metadataDir) throws JDESingleBSFNException {

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
    
      public Set<JDEBsfnParameter> getWSParameter(int session, String bsfnName,  File tmpFolderCache) throws JDESingleBSFNException {
          
          
          return null;
      }
    
    public synchronized JDEBsfnParametersOutputObject callJDEWS(int session, String bsfnName, JDEBsfnParametersInputObject inputObject,File tmpFolderCache)
    {
        
        
        
        
        
        
        return null;
    }
    

}
