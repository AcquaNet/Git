/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.jdeconnector.internal.ws;

import com.atina.jdeconnector.internal.model.metadata.Model;
import com.atina.jdeconnector.internal.model.metadata.ModelType;
import com.atina.jdeconnector.internal.model.metadata.Models;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atina.jdeconnectorservice.JDEConnectorService;
import com.atina.jdeconnectorservice.exception.JDESingleBSFNException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.mule.util.ClassUtils;

/**
 *
 * @author franciscogodinoconte
 */
public class JDEWSCreateObjectUtil {

    private static final Logger logger = LoggerFactory.getLogger(JDEConnectorService.class);

    public static Object generateObject(String inputModelClass, Models models, Model model, HashMap<String, Object> inputParameters) {

        // -------------------------------------------------------------
        // Load Class
        // -------------------------------------------------------------
        //
        Class inputValueClass = loadClass(inputModelClass);

        // -------------------------------------------------------------
        // Instance Class
        // -------------------------------------------------------------
        //
        Object instanceLoaded = instanceClass(inputModelClass);

        // -------------------------------------------------------------
        // For Each Parameter
        // -------------------------------------------------------------
        //
        int i = 0;

        // Por cada Parametro de Input:
        //
        for (Map.Entry<String, Object> inputParameter : inputParameters.entrySet()) {

            //-------------------------------------------------------------------------
            // Example of inputParameter
            //-------------------------------------------------------------------------
            // "approver": {
            //			"parameterType": "oracle.e1.bssv.util.J0100010.valueobject.Entity",
            //			"parameterName": "approver",
            //			"parameterSequence": 1,
            //			"repetead": false
            //		}
            //
            //  Key = String
            //  Value = ModelType
            //
            //
            // 
            
            // -----------------------------------
            // Set Model Type
            // -----------------------------------
            
            ModelType currentModelType = (ModelType) model.getParametersType().get(inputParameter.getKey());
             
            String variableName = currentModelType.getParameterName();
            
            String parameterType = currentModelType.getParameterType();
             

            // Check Parameter Type
            if (models.isModel(parameterType)) {
                
                Model newModel = models.getModelo(parameterType);
                
                Object parameterInstance = generateObject(parameterType,models, newModel, (HashMap<String, Object>) inputParameter.getValue());
                
                setValue(inputValueClass,instanceLoaded, variableName, parameterType, parameterInstance);

            } else {
                 
                setValue(inputValueClass,instanceLoaded, variableName, parameterType, inputParameter.getValue());
                 
            }

            logger.info("Error getting class " + inputParameter.getClass().getName());

        }

        return instanceLoaded;

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

    private static void setValue(Class cls, 
                                 Object instanceLoaded, 
                                 String variableName, 
                                 String variableClass, 
                                 Object valueObject) {

        try {

            String setterName = "set" + variableName.substring(0, 1).toUpperCase() + variableName.substring(1);

            Class<?>[] parametroClasss = new Class[1];

            parametroClasss[0] = Class.forName(variableClass);

            Method metodo = ClassUtils.getMethod(cls, setterName, parametroClasss, true);

            if (metodo == null) {

                logger.error("Error getting method: " + cls.getName() + "." + setterName);

                throw new JDESingleBSFNException("Error getting method: " + cls.getName() + "." + setterName);
            }

            Object[] parametrosDeInputDelMetodo = new Object[1];
            
            parametrosDeInputDelMetodo[0] = valueObject;

            metodo.invoke(instanceLoaded, parametrosDeInputDelMetodo);

        } catch (ClassNotFoundException ex) {

            logger.error("Class not found " + variableClass + " Error: " + ex.getMessage(), ex);

            throw new JDESingleBSFNException("Class not found : " + ex.getMessage(), ex);

        } catch (IllegalAccessException ex) {

            logger.error("Class not found " + variableClass + " Error: " + ex.getMessage(), ex);

            throw new JDESingleBSFNException("Class not found : " + ex.getMessage(), ex);

        } catch (IllegalArgumentException ex) {

            logger.error("Class not found " + variableClass + " Error: " + ex.getMessage(), ex);

            throw new JDESingleBSFNException("Class not found : " + ex.getMessage(), ex);

        } catch (InvocationTargetException ex) {

            logger.error("Class not found " + variableClass + " Error: " + ex.getMessage(), ex);

            throw new JDESingleBSFNException("Class not found : " + ex.getMessage(), ex);

        }

    }

}
