/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.jdeconnector.internal.ws;

import com.atina.jdeconnector.internal.model.metadata.Model;
import com.atina.jdeconnector.internal.model.metadata.Models;
import com.atina.jdeconnector.internal.model.metadata.Operation;
import com.atina.jdeconnector.internal.model.metadata.Operations;
import com.atina.jdeconnector.internal.model.metadata.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atina.jdeconnectorservice.JDEConnectorService;
import com.atina.jdeconnectorservice.exception.JDESingleBSFNException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.logging.Level;
import oracle.e1.bssvfoundation.base.IContext;
import oracle.e1.bssvfoundation.connection.IConnection;
import oracle.e1.bssvfoundation.impl.base.Context;
import oracle.e1.bssvfoundation.impl.connection.SBFConnection;
import oracle.e1.bssvfoundation.impl.connection.SBFConnectionManager;
import oracle.e1.bssvfoundation.impl.security.E1Principal;
import org.mule.util.ClassUtils;

/**
 *
 * @author franciscogodinoconte
 */
public class JDEWSCreateAndInvokeWS {

    private static final Logger logger = LoggerFactory.getLogger(JDEConnectorService.class);

    public static Object invokeObject( E1Principal e1ppal,
                                       Operations operaciones,
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
        
        // -----------------------------------------
        // Obtencion del SBFConnection
        // -----------------------------------------
        
        SBFConnectionManager valor = SBFConnectionManager.getInstance();
         
        SBFConnection defaultConnection = valor.getDefaultConnection((Context) context, true);
        
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
           
           throw new JDESingleBSFNException("Error invoking WS " + operation + " Error: " + ex.getMessage());
           
           
        }
        
        // -------------------------------------------------------------
        // Invoke Method
        // -------------------------------------------------------------
         
        Object returnValue = null;
        
        try {

            Object[] parametrosDeInputDelMetodo = new Object[3];

            parametrosDeInputDelMetodo[0] = context;
            parametrosDeInputDelMetodo[1] = defaultConnection;
            parametrosDeInputDelMetodo[2] = inputVOObject;

            returnValue = metodo.invoke(instanceLoaded, parametrosDeInputDelMetodo);

        } catch (IllegalAccessException ex) {

            logger.error("Error invoking WS " + operation + " Error: " + ex.getMessage(), ex);

            throw new JDESingleBSFNException("Error invoking WS " + operation + " Error: " + ex.getMessage());

        } catch (IllegalArgumentException ex) {

            logger.error("Error invoking WS " + operation + " Error: " + ex.getMessage(), ex);

            throw new JDESingleBSFNException("Error invoking WS " + operation + " Error: " + ex.getMessage());

        } catch (InvocationTargetException ex) {

            logger.error("Error invoking WS " + operation + " Error: " + ex.getMessage(), ex);

            throw new JDESingleBSFNException("Error invoking WS " + operation + " Error: " + ex.getMessage());

        }
         
        return returnValue;
    }
    
    private static Class loadClass(String className) throws JDESingleBSFNException {

        Class cls = null;

        try {

            cls = ClassUtils.loadClass(className, Thread.currentThread().getContextClassLoader());

        } catch (ClassNotFoundException ex) {

            logger.error("Error getting class " + className + " Error: " + ex.getMessage(), ex);

            throw new JDESingleBSFNException("Error getting class : " + ex.getMessage(), ex);

        }

        if (cls == null) {

            logger.error("Class cannot be loaded " + className, null, null);

            throw new JDESingleBSFNException("Class cannot be loaded");

        }

        return cls;
    }
    
    private static Object instanceClass(String className) throws JDESingleBSFNException {

        Object instance = null;

        try {

            instance = ClassUtils.instanciateClass(className);

        } catch (ClassNotFoundException ex) {

            logger.error("Error Loading Class  " + className + " Error: " + ex.getMessage(), ex);

            throw new JDESingleBSFNException("Error Cargando Clase de la Operacion : " + ex.getMessage(), ex);

        } catch (SecurityException ex) {

            logger.error("Error Loading Class  " + className + " Error: " + ex.getMessage(), ex);

            throw new JDESingleBSFNException("Error Cargando Clase de la Operacion : " + ex.getMessage(), ex);

        } catch (NoSuchMethodException ex) {

            logger.error("Error Loading Class  " + className + " Error: " + ex.getMessage(), ex);

            throw new JDESingleBSFNException("Error Cargando Clase de la Operacion : " + ex.getMessage(), ex);

        } catch (IllegalArgumentException ex) {

            logger.error("Error Loading Class  " + className + " Error: " + ex.getMessage(), ex);

            throw new JDESingleBSFNException("Error Cargando Clase de la Operacion : " + ex.getMessage(), ex);

        } catch (InstantiationException ex) {

            logger.error("Error Loading Class  " + className + " Error: " + ex.getMessage(), ex);

            throw new JDESingleBSFNException("Error Cargando Clase de la Operacion : " + ex.getMessage(), ex);

        } catch (IllegalAccessException ex) {

            logger.error("Error Loading Class  " + className + " Error: " + ex.getMessage(), ex);

            throw new JDESingleBSFNException("Error Cargando Clase de la Operacion : " + ex.getMessage(), ex);

        } catch (InvocationTargetException ex) {

            logger.error("Error Loading Class  " + className + " Error: " + ex.getMessage(), ex);

            throw new JDESingleBSFNException("Error Cargando Clase de la Operacion : " + ex.getMessage(), ex);

        }

        return instance;
    }
 

}
