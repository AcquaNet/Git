/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.jdeconnector.internal.ws;

import com.atina.jdeconnector.internal.model.metadata.E1ReturnWSValue;
import com.atina.jdeconnector.internal.model.metadata.Model;
import com.atina.jdeconnector.internal.model.metadata.ModelType;
import com.atina.jdeconnector.internal.model.metadata.Models;
import com.atina.jdeconnector.internal.model.metadata.Operation;
import com.atina.jdeconnector.internal.model.metadata.Operations;
import com.atina.jdeconnector.internal.model.metadata.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atina.jdeconnectorservice.JDEConnectorService;
import com.atina.jdeconnectorservice.exception.JDESingleException;
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
import java.util.logging.Level;
import oracle.e1.bssvfoundation.base.IContext;
import oracle.e1.bssvfoundation.base.MessageValueObject;
import oracle.e1.bssvfoundation.base.PublishedBusinessService;
import oracle.e1.bssvfoundation.connection.IConnection;
import oracle.e1.bssvfoundation.exception.BusinessServiceException;
import oracle.e1.bssvfoundation.impl.base.Context;
import oracle.e1.bssvfoundation.impl.connection.SBFConnection;
import oracle.e1.bssvfoundation.impl.connection.SBFConnectionManager;
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

        
        // ------------------------------------------
        // Get Metadata for Operation
        // ------------------------------------------
        //
        Operation metadataOperation = operaciones.getOperations().get(operation);
        
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
        Class wsClass = loadClass(metadataOperation.getOperationModelPackage()+"."+metadataOperation.getOperationClass());
        
        // -------------------------------------------------------------
        // Instance Class
        // -------------------------------------------------------------
        //
        Object instanceLoaded = instanceClass(metadataOperation.getOperationModelPackage()+"."+metadataOperation.getOperationClass());
        
        // -------------------------------------------------------------
        // Get Method
        // -------------------------------------------------------------
        
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
        
        // -------------------------------------------------------------
        // Invoke Method
        // -------------------------------------------------------------
         
        E1MessageList returnMessages = new E1MessageList();
        
        Object returnValue = null;
        
        try {

            Object[] parametrosDeInputDelMetodo = new Object[3];

            parametrosDeInputDelMetodo[0] = context;
            //parametrosDeInputDelMetodo[1] = defaultConnection;
            parametrosDeInputDelMetodo[1] = null; 
            parametrosDeInputDelMetodo[2] = inputVOObject;

            returnValue = metodo.invoke(instanceLoaded, parametrosDeInputDelMetodo);

        } catch (IllegalAccessException ex) {

            String errorMessage = ((BusinessServiceException) ex.getCause()).getMessage();

            logger.error("Error invoking WS IllegalAccessException " + operation + " Error: " + errorMessage, ex);
             
            returnMessages.addMessage(new E1Message(context, "019FIS", errorMessage));

        } catch (IllegalArgumentException ex) {

            String errorMessage = ((BusinessServiceException) ex.getCause()).getMessage();

            logger.error("Error invoking WS IllegalArgumentException " + operation + " Error: " + errorMessage, ex);
             
            returnMessages.addMessage(new E1Message(context, "019FIS", errorMessage));

        } catch (InvocationTargetException ex) {
            
            String errorMessage = ((BusinessServiceException) ex.getCause()).getMessage();

            logger.error("Error invoking WS InvocationTargetException " + operation + " Error: " + errorMessage, ex);
             
            returnMessages.addMessage(new E1Message(context, "019FIS", errorMessage));
 
        }
        
        // ================================================
        // Convert Output Object to HashMap
        // ================================================
        //
        
        ObjectMapper mapper = new ObjectMapper();

        mapper.setPropertyNamingStrategy(new MyNamingStrategy());
        
         mapper.configure(MapperFeature.USE_ANNOTATIONS, false);

         String jsonInString = "";

         if(returnValue == null)
         {
             
             E1ReturnWSValue returnWSOutputValue = new E1ReturnWSValue();
             returnWSOutputValue.setMessageValueObject((MessageValueObject) returnValue);
             returnWSOutputValue.setE1MessagesList(returnMessages);
             
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
