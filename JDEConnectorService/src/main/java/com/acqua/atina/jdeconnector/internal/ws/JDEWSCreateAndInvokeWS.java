/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acqua.atina.jdeconnector.internal.ws;

import com.acqua.atina.jdeconnector.internal.model.metadata.E1ReturnWSValue;
import com.acqua.atina.jdeconnector.internal.model.metadata.ModelType;
import com.acqua.atina.jdeconnector.internal.model.metadata.Models;
import com.acqua.atina.jdeconnector.internal.model.metadata.Operation;
import com.acqua.atina.jdeconnector.internal.model.metadata.Operations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acqua.atina.jdeconnectorservice.JDEConnectorService;
import com.acqua.atina.jdeconnectorservice.exception.JDESingleException;
import com.acqua.atina.jdeconnectorservice.exception.JDESingleWSException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import oracle.e1.bssvfoundation.base.IContext;
import oracle.e1.bssvfoundation.base.MessageValueObject;
import oracle.e1.bssvfoundation.connection.IConnection;
import oracle.e1.bssvfoundation.exception.BusinessServiceException;
import oracle.e1.bssvfoundation.impl.base.Context;
import oracle.e1.bssvfoundation.impl.security.E1Principal;  
import oracle.e1.bssvfoundation.util.E1Message;
import oracle.e1.bssvfoundation.util.E1MessageList;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.mule.util.ClassUtils;


/**
 *
 * @author franciscogodinoconte
 */
public class JDEWSCreateAndInvokeWS {

    private static final Logger logger = LoggerFactory.getLogger(JDEConnectorService.class);

    public static HashMap<String, Object> invokeObject( E1Principal e1ppal,
                                                        Operations operaciones,
                                                        Models models,
                                                        String operation,
                                                        Object inputVOObject) {

        
        logger.info("                                    Invoke: " + operation);
        
        // ------------------------------------------
        // Get Metadata for Operation
        // ------------------------------------------
        //
        Operation metadataOperation = operaciones.getOperations().get(operation);
        
        logger.info("                                    Metadata Operation Done");
        
        // ------------------------------------------
        // Get Context
        // ------------------------------------------
        //
      
        
        String mainWS = metadataOperation.getOperationModelPackage().substring(metadataOperation.getOperationModelPackage().lastIndexOf(".") + 1);
                
        IContext context = new oracle.e1.bssvfoundation.impl.base.Context(true,e1ppal,mainWS, metadataOperation.getOperationMethod()); 
        
        context.setApplicationID(mainWS);
        context.setGUID(metadataOperation.getOperationClass());
        context.setWorkstationName("WS");
        
        ((Context) context).incrementPublishedMethodCounter();
        
//        // -----------------------------------------
//        // Obtencion del SBFConnection
//        // -----------------------------------------
//        
//        SBFConnectionManager valor = SBFConnectionManager.getInstance();
//         
//        SBFConnection defaultConnection = valor.getDefaultConnection((Context) context, true);
        
        // -------------------------------------------------------------
        // Load Class
        // -------------------------------------------------------------
        //
        
        logger.info("                                    Loading Package: " + metadataOperation.getOperationModelPackage());
        logger.info("                                    Loading Class: " + metadataOperation.getOperationClass());
        
        Class wsClass = loadClass(metadataOperation.getOperationModelPackage()+"."+metadataOperation.getOperationClass());
        
        logger.info("                                    Loading Class Done");
        
        // -------------------------------------------------------------
        // Instance Class
        // -------------------------------------------------------------
        //
        logger.info("                                    Instancing Class");
        
        Object instanceLoaded = instanceClass(metadataOperation.getOperationModelPackage()+"."+metadataOperation.getOperationClass());
        
        logger.info("                                    Instancing Class Done");
        
        // -------------------------------------------------------------
        // Get Method
        // -------------------------------------------------------------
        
        logger.info("                                    Getting Method...");
        
        Method metodo = null;
        
        Class<?>[] parametroClasss = new Class[3];
        
        try {
            
            parametroClasss[0] = Class.forName(IContext.class.getName());
            parametroClasss[1] = Class.forName(IConnection.class.getName());
            
            String voClass = metadataOperation.getParameters().getParameters().get("vo").getParameterType();
                    
            parametroClasss[2] = Class.forName(voClass);
            
            metodo = ClassUtils.getMethod(wsClass, metadataOperation.getOperationMethod(), parametroClasss, true);
            
            
            
        } catch (ClassNotFoundException ex) {
            
           logger.error("Error invoking WS " + operation + " Error: " + ex.getMessage(), ex);
           
           throw new JDESingleException("Error invoking WS " + operation + " Error: " + ex.getMessage());
           
           
        }
        
        logger.info("                                    Getting Method Done");
        
        // -------------------------------------------------------------
        // Invoke Method
        // -------------------------------------------------------------
         
        E1MessageList returnMessages = new E1MessageList();
        
        Object returnValue = null;
        
        try {

            Object[] parametrosDeInputDelMetodo = new Object[3];

            parametrosDeInputDelMetodo[0] = context; 
            parametrosDeInputDelMetodo[1] = null; 
            parametrosDeInputDelMetodo[2] = inputVOObject;

            logger.info("                                    Invoking Method...");
            
            returnValue = metodo.invoke(instanceLoaded, parametrosDeInputDelMetodo);
            
            logger.info("                                    Invoking Method Done");

        } catch (IllegalAccessException ex) {

            String errorMessage = ex.getMessage();
                    
            if( ex.getCause() != null)
            {
                errorMessage = ((BusinessServiceException) ex.getCause()).getMessage();
            }  

            logger.error("Error invoking WS IllegalAccessException " + operation + " Error: " + errorMessage, ex);
             
            returnMessages.addMessage(new E1Message(context, "019FIS", errorMessage));

        } catch (IllegalArgumentException ex) {

            String errorMessage = ex.getMessage();
                    
            if( ex.getCause() != null)
            {
                errorMessage = ((BusinessServiceException) ex.getCause()).getMessage();
            }  

            logger.error("Error invoking WS IllegalArgumentException " + operation + " Error: " + errorMessage, ex);
             
            returnMessages.addMessage(new E1Message(context, "019FIS", errorMessage));

        } catch (InvocationTargetException ex) {
            
            String errorMessage = ex.getMessage();
                 
            if( ex.getCause() != null)
            {
                logger.error("Exception Clase  XXXX" + ex.getCause().getClass().getName());
                
                errorMessage = ((BusinessServiceException) ex.getCause()).getMessage();
            }  
               
            logger.error("Error invoking WS InvocationTargetException " + operation + " Error: " + errorMessage, ex);
            
            E1Message e1Message = new E1Message(context, "019FIS", errorMessage);
             
            String e1MessageAsJson  = "";
            
            ObjectMapper mapper = new ObjectMapper();

            mapper.setPropertyNamingStrategy(new MyNamingStrategy());
        
            mapper.configure(MapperFeature.USE_ANNOTATIONS, false);
            
            try {
                
                 e1MessageAsJson = mapper.writeValueAsString(e1Message);
                 
            } catch (JsonProcessingException exj) {
                
                throw new JDESingleWSException("Error invoking WS JsonProcessingException " + ex.getMessage(), ex, ex.getMessage());
                
            } 
            
            throw new JDESingleWSException("Error invoking WS InvocationTargetException " + ex.getMessage(), ex, e1MessageAsJson);
 
        }
        
        // ================================================
        // Convert Output Object to HashMap
        // ================================================
        //
        
        logger.info("                                    Converting Output to HashMap...");
        
        ObjectMapper mapper = new ObjectMapper();

        mapper.setPropertyNamingStrategy(new MyNamingStrategy());
        
         mapper.configure(MapperFeature.USE_ANNOTATIONS, false);

         String jsonInString = "";

         if(returnValue == null)
         {
             
             E1ReturnWSValue returnWSOutputValue = new E1ReturnWSValue();
             returnWSOutputValue.setMessageValueObject((MessageValueObject) returnValue);
             returnWSOutputValue.setE1MessageList(returnMessages);
             
             try {

                 jsonInString = mapper.writeValueAsString(returnWSOutputValue);
                 
                 String parametersName = "";
                 
                 // "messageValueObject"
                 HashMap<String, ModelType> parametersList = models.getModelo(metadataOperation.getOperationReturnType()).getParametersType();
                 
                 Iterator it = parametersList.entrySet().iterator();
                 
                 while (it.hasNext()) {
                     
                     Map.Entry pair = (Map.Entry) it.next();
            
                    ModelType parameter = (ModelType) pair.getValue();
                    
                    if(parameter.getParameterSequence() == 0)
                    {
                        parametersName = parameter.getParameterName();
                        break;
                    }
                      
                 }
                 
                 if(!parametersName.isEmpty())
                 {
                     jsonInString = jsonInString.replaceAll("messageValueObject", parametersName);
                 }
                 

             } catch (JsonProcessingException ex) {

                 logger.error("Error generating output json " + ex.getMessage());

                 throw new JDESingleException("Error generating output json " + ex.getMessage(), ex);

             }
             
         }
         else
         {
             try {

                 jsonInString = mapper.writeValueAsString(returnValue);
                 
                 logger.info("                                    Converting Output Json: " + jsonInString);

             } catch (JsonProcessingException ex) {

                 logger.error("Error generating output json " + ex.getMessage());

                 throw new JDESingleException("Error generating output json " + ex.getMessage(), ex);

             }
             
         } 
         
        HashMap<String, Object> valorAsHashMap = new HashMap();

        try {

            valorAsHashMap = mapper.readValue(jsonInString, new TypeReference<Map<String, Object>>() {
            });

        } catch (IOException ex) {

             logger.error("Error generating output json " + ex.getMessage());
             
            throw new JDESingleException("Error generating output hashmap " + ex.getMessage(),ex);
        } 

        logger.info("                                    Converting Output Done...");
        
        return valorAsHashMap;
    }
    
    private static Class loadClass(String className) throws JDESingleException {

        Class cls = null;

        try {

            cls = ClassUtils.loadClass(className, Thread.currentThread().getContextClassLoader());

        } catch (ClassNotFoundException ex) {

            logger.error("Error getting class " + className + " Error: " + ex.getMessage(), ex);

            throw new JDESingleException("Error getting class : " + ex.getMessage(), ex);

        }

        if (cls == null) {

            logger.error("Class cannot be loaded " + className, null, null);

            throw new JDESingleException("Class cannot be loaded");

        }

        return cls;
    }
    
    private static Object instanceClass(String className) throws JDESingleException {

        Object instance = null;

        try {

            instance = ClassUtils.instanciateClass(className);

        } catch (ClassNotFoundException ex) {

            logger.error("Error Loading Class  " + className + " Error: " + ex.getMessage(), ex);

            throw new JDESingleException("Error Cargando Clase de la Operacion : " + ex.getMessage(), ex);

        } catch (SecurityException ex) {

            logger.error("Error Loading Class  " + className + " Error: " + ex.getMessage(), ex);

            throw new JDESingleException("Error Cargando Clase de la Operacion : " + ex.getMessage(), ex);

        } catch (NoSuchMethodException ex) {

            logger.error("Error Loading Class  " + className + " Error: " + ex.getMessage(), ex);

            throw new JDESingleException("Error Cargando Clase de la Operacion : " + ex.getMessage(), ex);

        } catch (IllegalArgumentException ex) {

            logger.error("Error Loading Class  " + className + " Error: " + ex.getMessage(), ex);

            throw new JDESingleException("Error Cargando Clase de la Operacion : " + ex.getMessage(), ex);

        } catch (InstantiationException ex) {

            logger.error("Error Loading Class  " + className + " Error: " + ex.getMessage(), ex);

            throw new JDESingleException("Error Cargando Clase de la Operacion : " + ex.getMessage(), ex);

        } catch (IllegalAccessException ex) {

            logger.error("Error Loading Class  " + className + " Error: " + ex.getMessage(), ex);

            throw new JDESingleException("Error Cargando Clase de la Operacion : " + ex.getMessage(), ex);

        } catch (InvocationTargetException ex) {

            logger.error("Error Loading Class  " + className + " Error: " + ex.getMessage(), ex);

            throw new JDESingleException("Error Cargando Clase de la Operacion : " + ex.getMessage(), ex);

        }

        return instance;
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
