/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.jdeconnector.internal;
 
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
import com.atina.jdeconnector.internal.model.JDEBsfnParameter; 
import com.atina.jdeconnector.internal.model.metadata.E1ReturnBSFNValue;
import com.atina.jdeconnector.internal.model.metadata.E1ReturnWSValue;
import com.atina.jdeconnector.internal.ws.JDEWSCreateAndInvokeWS;
import com.atina.jdeconnectorservice.JDEConnectorService;
import com.atina.jdeconnectorservice.exception.JDESingleException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.jdedwards.system.kernel.CallObjectErrorItem;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;  
import java.util.logging.Level;
import oracle.e1.bssvfoundation.base.MessageValueObject;
import oracle.e1.bssvfoundation.util.E1Message;
import oracle.e1.bssvfoundation.util.E1MessageList;
import org.apache.commons.lang3.reflect.FieldUtils;


/**
 *
 * @author franciscogodinoconte
 */
public class JDEBsfnDriver {

    private static final Logger logger = LoggerFactory.getLogger(JDEConnectorService.class);
    
    private static final String FUNTIONS_RANGE_FROM = "                                ";

    private static final String FUNTIONS_RANGE_TO = "ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ";

    private static final String FUNTIONS_LIST = "bsfnlist.json";
    
    private boolean validateSpecs = false;

    public static JDEBsfnDriver getInstance() {
        return JDEBsfnDriverHolder.INSTANCE;
    }

    private static class JDEBsfnDriverHolder {

        private JDEBsfnDriverHolder() {
        }

        private static final JDEBsfnDriver INSTANCE = new JDEBsfnDriver();
    }

    public Set<String> getBSFNListFromEnterpriseServer(int session, File cacheFolder) throws JDESingleException {

        Set<String> bsfnList = new TreeSet<String>();
        
        File functionsFile = new File(cacheFolder + File.separator + FUNTIONS_LIST);

        logger.info("MULESOFT - SpecsGenerator: BussinessFunctionList() Checking file: " + cacheFolder + File.separator + FUNTIONS_LIST);

        if (functionsFile.exists()) {

            ObjectMapper mapper = new ObjectMapper();

            SimpleModule module = new SimpleModule();

            try {

                mapper.registerModule(module);

                bsfnList = mapper.readValue(functionsFile, TreeSet.class);
 
                logger.info("MULESOFT - SpecsGenerator: BussinessFunctionList() File Readed");

            } catch (JsonParseException e) {
                
                logger.error("MULESOFT - SpecsGenerator: Error parsing file", e);
                
                throw new JDESingleException("Fail to read functions: " + e.getMessage(), e);
                
            } catch (JsonMappingException e) {
                
                logger.error("MULESOFT - SpecsGenerator: Error Mapping JSON file", e);
                
                throw new JDESingleException("Fail to read functions: " + e.getMessage(), e);
                
            } catch (IOException e) {
                
                logger.error("MULESOFT - SpecsGenerator: Error reading functions file", e);
                
                throw new JDESingleException("Fail to read functions: " + e.getMessage(), e);

            } catch (Exception ex) {

                logger.error("MULESOFT - SpecsGenerator: Error reading functions file", ex);
                 
                throw new  JDESingleException("Fail to connect to Oneworld Database " + ex.getMessage(), ex);

            }

        } else {
            
            // ================
            // Init Object
            // ================

            ObjectLookupService objectLookupService;

            // ================
            // Get User Session
            // ================
            
            Integer localInteger = Integer.valueOf(session);

            UserSession localUserSession = Connector.getInstance()
                    .getUserSession(localInteger);

            if (!localUserSession.isSbfConnectorMode()) {

                try {

                    Properties localProperties = new Properties();

                    UserOCMContextSession localUserOCMContextSession = new UserOCMContextSession(localUserSession);

                    localProperties.put("session", localUserOCMContextSession);

                    objectLookupService = ObjectLookupServiceLoader.findService(localProperties);

                    JDBDatabaseAccess localJDBDatabaseAccess = JDBSystem.connect(localUserOCMContextSession, objectLookupService);

                    localUserSession.setUserDBAccess(localJDBDatabaseAccess);
                    
                     bsfnList = executeSQLSentence(localJDBDatabaseAccess);
                      
                     
                     // ================
                    // Persists List
                    // ================
                    
                    try {

                        ObjectMapper mapper = new ObjectMapper();

                        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

                        mapper.writeValue(functionsFile, bsfnList);

                        logger.info("MULESOFT - SpecsGenerator: BussinessFunctionList() Functions saved on "
                                + functionsFile.getAbsoluteFile());

                    } catch (Exception e) {

                        logger.error("MULESOFT - SpecsGenerator: BussinessFunctionList() Error saving functions on "
                                + functionsFile.getAbsoluteFile());
 

                        throw new JDESingleException("Fail to save functions " + e.getMessage(), e);

                    }

                } catch (ServiceException ex) {

                    throw new JDESingleException("Fail to initialize Object Lookup service" + ex.getMessage(), ex);

                } catch (JDBException ex) {

                    throw new JDESingleException("Fail to connect to Oneworld Database " + ex.getMessage(), ex);

                } catch (BSFNLookupFailureException ex) {
                    
                   throw new JDESingleException("Fail to connect to Oneworld Database " + ex.getMessage(), ex);
                   
                } catch (Exception ex) {
                    
                   throw new JDESingleException("Fail to connect to Oneworld Database " + ex.getMessage(), ex);
                   
                }
            }
  

        }

        return bsfnList;

    }
    
     public Set<JDEBsfnParameter> getBSFNParameter(int session, String bsfnName,  File tmpFolderCache) throws JDESingleException {
       
        // ===================================================
        // Initizialize Method
        // ===================================================
       
        LinkedHashSet<JDEBsfnParameter> returnValue = new LinkedHashSet<JDEBsfnParameter>();
        
        boolean flagReadCache = false;

        String functionXMLFile = "";

        File functioXMLCache = null;

        logger.debug("MULESOFT - JDEConnectorService:  - getBSFNParameterList() Function: " + bsfnName);

        // ===================================================
        // Verify Function XML File
        // ===================================================
        
        try {

            // Create File Name
            
            functionXMLFile = tmpFolderCache.getAbsolutePath() + File.separator + bsfnName + ".xml";

            logger.debug("MULESOFT - JDEConnectorService:  - getBSFNParameterList() XML File: " + functionXMLFile);

            functioXMLCache = new File(functionXMLFile);

            // Check if file exists and it is not a directory
            
            if (functioXMLCache.exists() && !functioXMLCache.isDirectory()) {

                flagReadCache = true;

            }
            
         } catch (Exception e) {

             throw new JDESingleException(e.getMessage(),e);
             
        }
   
         // ===================================================
         // Read Specs from cache
         // ===================================================
         
         BSFNSpecSource specSource = null;
 
         if (flagReadCache) {

             logger.debug("MULESOFT - JDEConnectorService:  - getBSFNParameterList() Reading parameters from xml specs... ");

             try {

                 specSource = new ImageBSFNSpecSource(functionXMLFile);

             } catch (SpecFailureException e) {

                 flagReadCache = false;

                 logger.error("MULESOFT - JDEConnectorService:  - getBSFNParameterList() Error reading xml specs.", e);

             }

             logger.debug("MULESOFT - JDEConnectorService:  - getBSFNParameterList() Ready parameters from xml specs.");

             // Validate Spec
             if (specSource != null && validateSpecs) {

                 logger.debug("MULESOFT - JDEConnectorService:  - getBSFNParameterList() Validate Spec.");

                 SpecImageValidator validator = new com.jdedwards.system.connector.dynamic.util.SpecImageValidator(specSource);

                 ValidationResultSet validationResult = null;
                 
                 try {
                     validationResult = validator.validate(specSource);
                     
                 } catch (SpecFailureException ex) {
                     
                     logger.debug("MULESOFT - JDEConnectorService:  - getBSFNParameterList() BSFN Changed.");

                     flagReadCache = false;
                     
                 }

                 if (validationResult != null && validationResult.hasDifference()) {

                     logger.debug("MULESOFT - JDEConnectorService:  - getBSFNParameterList() BSFN Changed.");

                     flagReadCache = false;

                 }

             }

         }

         // ===================================================
         // Read Specs from database
         // ===================================================
         
         if (!flagReadCache) {

             if (session != 0) {

                 logger.debug("MULESOFT - JDEConnectorService:  - getBSFNParameterList() Reading parameters from database specs... ");

                 try {

                     specSource = new OneworldBSFNSpecSource(session);

                 } catch (Exception e) {

                     logger.error("MULESOFT - JDEConnectorService:  - getBSFNParameterList() Error reading database specs. Error: " + e.getMessage());

                     throw new JDESingleException(e.getMessage(), e);

                 }

                 logger.debug("MULESOFT - JDEConnectorService:  - getBSFNParameterList() Ready parameters from database specs.");

             } else {

                 logger.error("MULESOFT - JDEConnectorService:  - getBSFNParameterList() Error reading xml from database.");

                 throw new JDESingleException("There is not connection to read database specs. ");

             }

         }

         // -------------------------------------------------
         // Get BSFN Description
         // -------------------------------------------------
         
         BSFNMethod method = null;

         try {

             method = specSource.getBSFNMethod(bsfnName);

         } catch (Exception e) {

             logger.error("MULESOFT - JDEConnectorService:  - getBSFNParameterList() Error reading specs from source.");

             throw new JDESingleException("Error reading specs from source", e);

         }

         // -------------------------------------------------
         // Generate Parameter List
         // -------------------------------------------------
         
         BSFNParameter[] paraList = method.getParameters(); 
         
         for (int i = 0; i < paraList.length; i++) {

             BSFNParameter para = paraList[i];
             
             
             JDEBsfnParameter jdep = new JDEBsfnParameter();
             
             jdep.setName(para.getName());
             jdep.setDataType(para.getDataType().getTypeName());
             jdep.setLength(para.getLength());
             
             returnValue.add(jdep);
              
         }

         // -------------------------------------------------
         // Save Cache for new function
         // -------------------------------------------------
         
         if (tmpFolderCache != null && !flagReadCache) {

             if (functioXMLCache.exists() && !functioXMLCache.isDirectory()) {

                 // Delete current cache
                 try {

                     functioXMLCache.delete();

                 } catch (Exception e) {

                     logger.error("MULESOFT - JDEConnectorService:  - getBSFNParameterList() Error deleting specs into cache directory.");

                     throw new JDESingleException("Error deleting specs into cache directory", e);

                 }

             }

             // Create Spec Dictionary
             
             SpecDictionary specDictionary = null;

            try {
                
                specDictionary = new OneworldSpecDictionary(session);
                
            } catch (SpecFailureException ex) {
                
                throw new JDESingleException(ex.getMessage(), ex);
                
            }

             // Create Image Generator
             SpecImageGenerator specGen = null;
            try {
                
                specGen = new SpecImageGenerator(specDictionary, specSource);
                
            } catch (SpecFailureException ex) {
                
                throw new JDESingleException(ex.getMessage(), ex);
                
            }

            try {
                
                // Import Specs into Image Generator
                
                specGen.importBSFNSpec(method.getName());
                
            } catch (SpecFailureException ex) {
                
                throw new JDESingleException(ex.getMessage(), ex);
                
            }

             // Generate XML
             try {

                 specGen.generateImage(functionXMLFile, SpecImageGenerator.ImageType.SSI);

             } catch (IOException e) {

                 logger.error("MULESOFT - JDEConnectorService:  - getBSFNParameterList() Error saving specs into cache directory.");

                 throw new JDESingleException("Error saving specs into cache directory", e);

             }

         }

         logger.debug("MULESOFT - JDEConnectorService:  - getBSFNParameterList() Ready.");

         return returnValue;
 
    } 
     
    public synchronized HashMap<String, Object> callJDEBsfn(int session, String bsfnName, Map<String, Object> inputObject, Integer transactionID, File tmpFolderCache)
    {
        // ===================================================
        // Log Parameters Received
        // ===================================================

        logger.debug("callJDEBsfn() function: " + bsfnName);
        logger.debug("              input   : " + inputObject.toString());
        logParameters(inputObject);
        
        
        HashMap<String, Object> outputParameters = new HashMap<String, Object>();
         
        // ===================================================
        // Get JDE Transaction
        // ===================================================
         
        OneworldTransaction transaction = null;
         
        if(transactionID > 0)
        { 
            transaction = JDETransactionPool.getInstance()
                .getTransaction(transactionID);
             
        }
        
        // ===================================================
        // Get Parameters
        // ===================================================
         
        String functionXMLFile = tmpFolderCache.getAbsolutePath() + File.separator + bsfnName + ".xml";
        
        if(!new File(functionXMLFile).exists())
        {
            this.getBSFNParameter(session,bsfnName,tmpFolderCache);
         
        }
        
        BSFNSpecSource specSource = null;
        
        // ===================================================
        // Reading Specs
        // ===================================================
        
        logger.debug("MULESOFT - JDEConnectorService:  - callBSFN() Reading parameters from xml specs... ");
        
        
        
        try {
            
            specSource = new ImageBSFNSpecSource(functionXMLFile);
            
        } catch (SpecFailureException e) {
            
            throw new JDESingleException(e.getMessage(), e);
            
        }

        logger.debug("MULESOFT - JDEConnectorService:  - callBSFN() Ready parameters from xml specs.");
        
        // ------------------------------
        // Process Valid Specs
        // ------------------------------
            
        if (specSource != null) {
             
            // ------------------------------
            // Get BSFN Method 
            // ------------------------------
         
            logger.debug("MULESOFT - JDEConnectorService:  - callBSFN() Getting Specs: " + bsfnName);

            BSFNMethod method;
             
            try {
                
                method = specSource.getBSFNMethod(bsfnName);
                
            } catch (SpecFailureException e) {
                
                throw new JDESingleException(e.getMessage(), e);
            
            }
             
            // ------------------------------
            // Create JDE CallObject
            // ------------------------------
            
            logger.debug("MULESOFT - JDEConnectorService:  - callBSFN() Creating Call Object: " + bsfnName);

            ExecutableMethod callobject;

            callobject = method.createExecutable();
            
            // ------------------------------
            // Populate Parameters 
            // ------------------------------
            
            for (BSFNParameter parameter : method.getParameters()) {
                
                 if (parameter != null) {
                     
                     
                     if (inputObject.get(parameter.getName()) != null) {
                          
                        logger.debug("MULESOFT - JDEConnectorService:  - callBSFN() Preparing Parameter: " + parameter.getName() + " value: "
                                     + inputObject.get(parameter.getName()) + " JDE Type: " + inputObject.get(parameter.getName()) + " Java Class: "
                                     + (inputObject.get(parameter.getName())
                                         .getClass()
                                         .getName()));
                         
                         switch (parameter.getDataType().getTypeName()) {

                        case "DATE":

                            JDEConverter.convertServiceInputDataToJDEFormatToJDEDate(bsfnName, inputObject, callobject, parameter);

                            break;

                        case "UTIME":

                            JDEConverter.convertServiceInputDataToJDEUtime(bsfnName, inputObject, callobject, parameter);

                            break;

                        case "MATH_NUMERIC":

                            JDEConverter.convertServiceInputDataToJDEFormatToMathNumeric(bsfnName, inputObject, callobject, parameter);

                            break;

                        case "INT":

                            JDEConverter.convertServiceInputDataToJDEFormatToJDEInt(bsfnName, inputObject, callobject, parameter);

                            break;

                        default:

                            callobject.setValue(parameter.getName(), String.valueOf(inputObject.get(parameter.getName())));

                            break;

                        }
                     }
                     
                     
                 }
                
                
            }
            
            // ------------------------------
            // Execute BSFN
            // ------------------------------

            CallObjectErrorList bsfnListError = null;

            try {

                logger.debug("MULESOFT - JDEConnectorService:  - callBSFN() Calling BSFN Parameters: ");

                if (transaction != null) {

                    logger.debug("MULESOFT - JDEConnectorService:  - callBSFN() Calling using Transaction...");

                    bsfnListError = callobject.executeBSFN(transaction);

                    logger.debug("MULESOFT - JDEConnectorService:  - callBSFN() Called using Transaction...");

                } else {

                    logger.debug("MULESOFT - JDEConnectorService:  - callBSFN() Calling without Transaction...");

                    bsfnListError = callobject.executeBSFN(session);

                    logger.debug("MULESOFT - JDEConnectorService:  - callBSFN() Called without Transaction...");

                }

            } catch (Exception e) {

                logger.error("MULESOFT - JDEConnectorService: callBSFN() Error calling BSFN: " + bsfnName);
                logger.error("MULESOFT - JDEConnectorService: callBSFN() Error: " + e.getMessage(), e);
         
                throw new JDESingleException(getBSFNErrorMessage(bsfnName, inputObject, e.getMessage()), e);

            }

            // ------------------------------
            // Send Exception
            // ------------------------------

            boolean sendException =  bsfnListError.getBSFNErrorCode() == 2;
  
            // ------------------------------
            // Return Parameters
            // ------------------------------
            
            
            if(!sendException)
            {
                for (BSFNParameter parameter : method.getParameters()) {

                        Object value = callobject.getValue(parameter.getName());

                        if (value != null) {

                            String parameterValue = parameter.getDataType()
                                .getTypeName();

                            String classNameForParameter = value.getClass()
                                .getName();

                            logger.debug("   Parameter Key: " + parameter.getName() + " Parameter Value: "
                                         + value.toString() + " JDE Parameter Type: " + parameterValue
                                         + " Java Class: "
                                         + classNameForParameter);

                            switch (parameterValue) {

                            case "CHAR":

                                outputParameters.put(parameter.getName(), JDEConverter.convertJDEDataToMUleFormatFromChar(value));

                                break;

                            case "STRING":

                                String strValue = (String)value;

                                outputParameters.put(parameter.getName(), strValue);

                                break;

                            case "MATH_NUMERIC":

                                outputParameters.put(parameter.getName(), JDEConverter.convertJDEDataToMUleFormatFromMathNumeric(value));

                                break;

                            case "DATE":

                                outputParameters.put(parameter.getName(), JDEConverter.convertJDEDataToMUleFormatFromJDEDate(value));

                                if (outputParameters.get(parameter.getName()) == null) {

                                    throw new JDESingleException(getBSFNErrorMessage(bsfnName, outputParameters, "Missing convertion for JDE DATE type from :" + classNameForParameter),
                                                                bsfnListError);

                                }

                                break;

                            case "UTIME":

                                outputParameters.put(parameter.getName(), JDEConverter.convertJDEDataToMUleFormatFromJDEUTime(value));

                                if (outputParameters.get(parameter.getName()) == null) {
                                    throw new JDESingleException(
                                                                getBSFNErrorMessage(bsfnName, outputParameters,
                                                                                    "Missing convertion for JDE UTIME type from :" + classNameForParameter),
                                                                bsfnListError);
                                }

                                break;

                            case "SHORT":
                            case "INT":
                            case "UNSIGNED_INT":
                            case "UTIMESBF":
                            case "CHARSBF":
                            case "MATH_NUMERICSBF":

                                break;

                            default:

                                throw new JDESingleException(getBSFNErrorMessage(bsfnName, outputParameters, "Invalid JDE Type"), bsfnListError);

                            }

                        } else {

                            logger.debug("MULESOFT - JDEConnectorService:  - callBSFN() Returning Parameter: " + parameter.getName() + " value: [NULL]");
                            outputParameters.put(parameter.getName(), null);

                        }

                }

            }
            
            // Convert Error List to HashMap
            
            if (sendException) {
                
                HashMap<String, Object> valorAsHashMap = new HashMap();
                 
                E1MessageList errorList = new E1MessageList();
                
                for (int i = 0; i < bsfnListError.length(); i++) { 
                    
                    CallObjectErrorItem errorItemValue = bsfnListError.getItem(i);
                     
                    E1Message e1Msg = new E1Message(errorItemValue);
                    
                    errorList.addMessage(e1Msg);
                    
                }
                 
                ObjectMapper mapper = new ObjectMapper();

                mapper.setPropertyNamingStrategy(new JDEWSCreateAndInvokeWS.MyNamingStrategy());

                mapper.configure(MapperFeature.USE_ANNOTATIONS, false);

                String jsonInString = "";

                try {

                    jsonInString = mapper.writeValueAsString(errorList);

                } catch (JsonProcessingException ex) {

                    logger.error("Error generating output json " + ex.getMessage());

                    throw new JDESingleException("Error generating output json " + ex.getMessage(), ex);

                }
                
                try {

                    valorAsHashMap = mapper.readValue(jsonInString, new TypeReference<Map<String, Object>>() {
                    });

                } catch (IOException ex) {

                    logger.error("Error generating output json " + ex.getMessage());

                    throw new JDESingleException("Error generating output hashmap " + ex.getMessage(), ex);
                }
                
                outputParameters.put("_ErrorList", valorAsHashMap);
                
                outputParameters.put("_ErrorCode", bsfnListError.getBSFNErrorCode());

            }
             

//            // ------------------------------
//            // Send Exception
//            // ------------------------------
//            if (sendException) {
//
//                throw new JDESingleBSFNException(getBSFNErrorMessage(bsfnName, outputParameters, "Error calling BSFN"), bsfnListError);
//
//            }
             
            
        } else
        {
            throw new JDESingleException("Invalid Specs");
            
        }
         
        return outputParameters;
    }
     
     
     
    private Set<String> executeSQLSentence(JDBDatabaseAccess localJDBDatabaseAccess) throws BSFNLookupFailureException, JDBException {

        Set<String> returnValue = new TreeSet<String>();

        JDBResultSet localJDBResultSet = null;

        try {

            JDBCompositeSelection localJDBCompositeSelection = new JDBCompositeSelection();

            JDBFieldComparison selection1 = new JDBFieldComparison(F9862.FCTNM_FIELD, JDBComparisonOp.GE, FUNTIONS_RANGE_FROM);

            JDBFieldComparison selection2 = new JDBFieldComparison(F9862.FCTNM_FIELD, JDBComparisonOp.LT, FUNTIONS_RANGE_TO);

            localJDBCompositeSelection.addSelection(selection1);

            localJDBCompositeSelection.addSelection(selection2);

            localJDBResultSet = localJDBDatabaseAccess.select(F9862.getInstance(),
                    new JDBField[]{F9862.FCTNM_FIELD, F9862.MD_FIELD}, localJDBCompositeSelection, null);

            JDBFieldMap arrayOfJDBFieldMap = localJDBResultSet.fetchNext();

            while (arrayOfJDBFieldMap != null) {

                String functionName = arrayOfJDBFieldMap.getString(F9862.FCTNM_FIELD).trim();

                returnValue.add(functionName);

                arrayOfJDBFieldMap = localJDBResultSet.fetchNext();

            }

            localJDBResultSet.close();

        } catch (JDBException e) {

            logger.error(
                    "MULESOFT - ERROR SpecsGenerator: BussinessFunctionList() Error Reading Functions from F9862",
                    e);

            throw new JDESingleException("Fail to fetch functions " + e.getMessage(),
                    e);

        } finally {

            if (localJDBResultSet != null) {

                localJDBResultSet.close();

                logger.info("MULESOFT - SpecsGenerator: BussinessFunctionList() Connections closed");

            }

        }

        return returnValue;

    } 
      
    private String getBSFNErrorMessage(String entityType, Map<String, Object> entityData, String errorMessage) {

        StringBuilder message = new StringBuilder();

        message.append("BSFN: ");
        message.append(entityType);
        message.append(" has the following error: [");
        message.append(errorMessage);
        message.append("] ");

        if (entityData != null && !entityData.keySet()
            .isEmpty()) {

            message.append("Parameters: ");

            for (String parameter : entityData.keySet()) {

                message.append("[");
                message.append(parameter);
                message.append(":");

                if (parameter != null && entityData.get(parameter) != null) {
                    message.append(entityData.get(parameter));
                } else {
                    message.append("null");
                }

                message.append("]");

            }

        } else {
            message.append(" without Parameters.");
        }

        return message.toString();

    }
    
    private void logParameters(Map<String, Object> parameters) {

        if (parameters != null && !parameters.keySet()
            .isEmpty()) {

            logger.debug("MULESOFT - JDEConnectorService:  -  Parameter Begin");

            for (String parameter : parameters.keySet()) {

                if (parameter != null && parameters.get(parameter) != null) {
                    logger.debug("MULESOFT - JDEConnectorService:  - Parameter  entityData Key/Value:" + parameter + " [" + parameters.get(parameter) + "]");
                } else {
                    logger.debug("MULESOFT - JDEConnectorService:  - Parameter  entityData Key/Value:" + parameter + " []");

                }

            }

            logger.debug("MULESOFT - JDEConnectorService:  - Parameter End");

        }

    }
    
    public static class MyNamingStrategy extends PropertyNamingStrategy {

        @Override
        public String nameForField(MapperConfig<?> config, AnnotatedField field, String defaultName) {
            return field.getName();
        }

        @Override
        public String nameForGetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
            return convert(method, defaultName);
        }

        @Override
        public String nameForSetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
            return convert(method, defaultName);
        }

        private String convert(AnnotatedMethod method, String defaultName) {
 
            Class<?> clazz = method.getDeclaringClass();
            List<Field> flds = FieldUtils.getAllFieldsList(clazz);
            for (Field fld : flds) {
                if (fld.getName().equalsIgnoreCase(defaultName)) {
                    return fld.getName();
                }
            }

            return defaultName;
        }
    }
    
    
    
}
