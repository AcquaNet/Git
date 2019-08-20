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
import com.atina.jdeconnector.internal.model.JDEBsfnParametersOutputObject;
import com.atina.jdeconnector.internal.model.JDEBsfnParameter;
import com.atina.jdeconnector.internal.model.JDEBsfnParametersInputObject;
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
public class JDEBsfnDriver {

    private static final Logger logger = LoggerFactory.getLogger(JDEBsfnDriver.class);
    
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

    public Set<String> getBSFNListFromEnterpriseServer(int session, File cacheFolder) throws JDESingleBSFNException {

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
                
                throw new JDESingleBSFNException("Fail to read functions: " + e.getMessage(), e);
                
            } catch (JsonMappingException e) {
                
                logger.error("MULESOFT - SpecsGenerator: Error Mapping JSON file", e);
                
                throw new JDESingleBSFNException("Fail to read functions: " + e.getMessage(), e);
                
            } catch (IOException e) {
                
                logger.error("MULESOFT - SpecsGenerator: Error reading functions file", e);
                
                throw new JDESingleBSFNException("Fail to read functions: " + e.getMessage(), e);

            } catch (Exception ex) {

                logger.error("MULESOFT - SpecsGenerator: Error reading functions file", ex);
                 
                throw new  JDESingleBSFNException("Fail to connect to Oneworld Database " + ex.getMessage(), ex);

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
 

                        throw new JDESingleBSFNException("Fail to save functions " + e.getMessage(), e);

                    }

                } catch (ServiceException ex) {

                    throw new JDESingleBSFNException("Fail to initialize Object Lookup service" + ex.getMessage(), ex);

                } catch (JDBException ex) {

                    throw new JDESingleBSFNException("Fail to connect to Oneworld Database " + ex.getMessage(), ex);

                } catch (BSFNLookupFailureException ex) {
                    
                   throw new JDESingleBSFNException("Fail to connect to Oneworld Database " + ex.getMessage(), ex);
                   
                } catch (Exception ex) {
                    
                   throw new JDESingleBSFNException("Fail to connect to Oneworld Database " + ex.getMessage(), ex);
                   
                }
            }
  

        }

        return bsfnList;

    }
    
     public Set<JDEBsfnParameter> getBSFNParameter(int session, String bsfnName,  File tmpFolderCache) throws JDESingleBSFNException {
       
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

             throw new JDESingleBSFNException(e.getMessage(),e);
             
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

                     throw new JDESingleBSFNException(e.getMessage(), e);

                 }

                 logger.debug("MULESOFT - JDEConnectorService:  - getBSFNParameterList() Ready parameters from database specs.");

             } else {

                 logger.error("MULESOFT - JDEConnectorService:  - getBSFNParameterList() Error reading xml from database.");

                 throw new JDESingleBSFNException("There is not connection to read database specs. ");

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

             throw new JDESingleBSFNException("Error reading specs from source", e);

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

                     throw new JDESingleBSFNException("Error deleting specs into cache directory", e);

                 }

             }

             // Create Spec Dictionary
             
             SpecDictionary specDictionary = null;

            try {
                
                specDictionary = new OneworldSpecDictionary(session);
                
            } catch (SpecFailureException ex) {
                
                throw new JDESingleBSFNException(ex.getMessage(), ex);
                
            }

             // Create Image Generator
             SpecImageGenerator specGen = null;
            try {
                
                specGen = new SpecImageGenerator(specDictionary, specSource);
                
            } catch (SpecFailureException ex) {
                
                throw new JDESingleBSFNException(ex.getMessage(), ex);
                
            }

            try {
                
                // Import Specs into Image Generator
                
                specGen.importBSFNSpec(method.getName());
                
            } catch (SpecFailureException ex) {
                
                throw new JDESingleBSFNException(ex.getMessage(), ex);
                
            }

             // Generate XML
             try {

                 specGen.generateImage(functionXMLFile, SpecImageGenerator.ImageType.SSI);

             } catch (IOException e) {

                 logger.error("MULESOFT - JDEConnectorService:  - getBSFNParameterList() Error saving specs into cache directory.");

                 throw new JDESingleBSFNException("Error saving specs into cache directory", e);

             }

         }

         logger.debug("MULESOFT - JDEConnectorService:  - getBSFNParameterList() Ready.");

         return returnValue;
 
    } 
     
    public synchronized JDEBsfnParametersOutputObject callJDEBsfn(int session, String bsfnName, JDEBsfnParametersInputObject inputObject,File tmpFolderCache)
    {
        // ===================================================
        // Log Parameters Received
        // ===================================================

        logger.debug("callJDEBsfn() function: " + bsfnName);
        logger.debug("              input   : " + inputObject.toString());
        inputObject.logParameters();
        
        
        JDEBsfnParametersOutputObject outputParameters = new JDEBsfnParametersOutputObject();
 
        // ===================================================
        // Get JDE Transaction
        // ===================================================
         
        OneworldTransaction transaction = null;
         
        if(inputObject.hasTransactionID())
        {
            
            transaction = JDETransactionPool.getInstance()
                .getTransaction(inputObject.getTransactionID());
            
            
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
            
            throw new JDESingleBSFNException(e.getMessage(), e);
            
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
                
                throw new JDESingleBSFNException(e.getMessage(), e);
            
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
                     
                     
                     if (inputObject.getParameters().get(parameter.getName()) != null) {
                          
                        logger.debug("MULESOFT - JDEConnectorService:  - callBSFN() Preparing Parameter: " + parameter.getName() + " value: "
                                     + inputObject.getParameters().get(parameter.getName()) + " JDE Type: " + inputObject.getParameters().get(parameter.getName()) + " Java Class: "
                                     + (inputObject.getParameters().get(parameter.getName())
                                         .getClass()
                                         .getName()));
                         
                         switch (parameter.getDataType().getTypeName()) {

                        case "DATE":

                            JDEConverter.convertServiceInputDataToJDEFormatToJDEDate(bsfnName, inputObject.getParameters(), callobject, parameter);

                            break;

                        case "UTIME":

                            JDEConverter.convertServiceInputDataToJDEUtime(bsfnName, inputObject.getParameters(), callobject, parameter);

                            break;

                        case "MATH_NUMERIC":

                            JDEConverter.convertServiceInputDataToJDEFormatToMathNumeric(bsfnName, inputObject.getParameters(), callobject, parameter);

                            break;

                        case "INT":

                            JDEConverter.convertServiceInputDataToJDEFormatToJDEInt(bsfnName, inputObject.getParameters(), callobject, parameter);

                            break;

                        default:

                            callobject.setValue(parameter.getName(), String.valueOf(inputObject.getParameters().get(parameter.getName())));

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
         
                throw new JDESingleBSFNException(getBSFNErrorMessage(bsfnName, inputObject.getParameters(), e.getMessage()), e);

            }

            // ------------------------------
            // Send Exception
            // ------------------------------

            boolean sendException = false;

            if (inputObject.isThrowExceptionWithError()) {

                sendException = bsfnListError.getBSFNErrorCode() == 2;

            }

            // ------------------------------
            // Return Errors
            // ------------------------------
            outputParameters.setReturnValue(bsfnListError.getBSFNErrorCode());

            if (bsfnListError.getBSFNErrorCode() > 0) {

                outputParameters.setNoErrors(bsfnListError.length());
                
                try {
                    
                    outputParameters.setErrorsDetail(bsfnListError);

                } catch (JsonProcessingException e) {

                    logger.error("MULESOFT - JDEConnectorService: callBSFN() Error calling BSFN: " + bsfnName);
                    logger.error("MULESOFT - JDEConnectorService: callBSFN() Error: " + e.getMessage(), e);

                    throw new JDESingleBSFNException(getBSFNErrorMessage(bsfnName, inputObject.getParameters(), e.getMessage()), e);
                }

            }

            // ------------------------------
            // Return Parameters
            // ------------------------------
            
            
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

                            outputParameters.getParameters().put(parameter.getName(), JDEConverter.convertJDEDataToMUleFormatFromChar(value));

                            break;

                        case "STRING":

                            String strValue = (String)value;

                            outputParameters.getParameters().put(parameter.getName(), strValue);

                            break;

                        case "MATH_NUMERIC":

                            outputParameters.getParameters().put(parameter.getName(), JDEConverter.convertJDEDataToMUleFormatFromMathNumeric(value));

                            break;

                        case "DATE":

                            outputParameters.getParameters().put(parameter.getName(), JDEConverter.convertJDEDataToMUleFormatFromJDEDate(value));

                            if (outputParameters.getParameters().get(parameter.getName()) == null) {

                                throw new JDESingleBSFNException(getBSFNErrorMessage(bsfnName, outputParameters.getParameters(), "Missing convertion for JDE DATE type from :" + classNameForParameter),
                                                            bsfnListError);

                            }

                            break;

                        case "UTIME":

                            outputParameters.getParameters().put(parameter.getName(), JDEConverter.convertJDEDataToMUleFormatFromJDEUTime(value));

                            if (outputParameters.getParameters().get(parameter.getName()) == null) {
                                throw new JDESingleBSFNException(
                                                            getBSFNErrorMessage(bsfnName, outputParameters.getParameters(),
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

                            throw new JDESingleBSFNException(getBSFNErrorMessage(bsfnName, outputParameters.getParameters(), "Invalid JDE Type"), bsfnListError);

                        }

                    } else {

                        logger.debug("MULESOFT - JDEConnectorService:  - callBSFN() Returning Parameter: " + parameter.getName() + " value: [NULL]");
                        outputParameters.getParameters().put(parameter.getName(), null);

                    }

                }

                // ------------------------------
                // Send Exception
                // ------------------------------

                if (sendException) {

                    throw new JDESingleBSFNException(getBSFNErrorMessage(bsfnName, outputParameters.getParameters(), "Error calling BSFN"), bsfnListError);

                }
             
            
        } else
        {
            throw new JDESingleBSFNException("Invalid Specs");
            
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

            throw new JDESingleBSFNException("Fail to fetch functions " + e.getMessage(),
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
    
    
}
