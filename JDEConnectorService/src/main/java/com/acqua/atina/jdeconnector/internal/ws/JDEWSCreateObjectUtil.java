/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acqua.atina.jdeconnector.internal.ws;

import com.acqua.atina.jdeconnector.internal.model.metadata.Model;
import com.acqua.atina.jdeconnector.internal.model.metadata.ModelType;
import com.acqua.atina.jdeconnector.internal.model.metadata.Models;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acqua.atina.jdeconnectorservice.JDEConnectorService;
import com.acqua.atina.jdeconnectorservice.exception.JDESingleException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
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
                
                setValue(inputValueClass,instanceLoaded, variableName, parameterType, parameterInstance, i, currentModelType.isRepetead());

            } else {
                 
                setValue(inputValueClass,instanceLoaded, variableName, parameterType, inputParameter.getValue(),i, currentModelType.isRepetead());
                
                 
            }

            logger.info("Error getting class " + inputParameter.getClass().getName());
            
            i++;

        }

        return instanceLoaded;

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

    private static void setValue(Class cls, 
                                 Object instanceLoaded, 
                                 String variableName, 
                                 String variableClass, 
                                 Object valueObject,
                                 int pos,
                                 boolean repeated) {

        try {

            String setterName = "set" + variableName.substring(0, 1).toUpperCase() + variableName.substring(1);

            Class<?>[] parametroClasss = null;
            
            if(repeated)
            {
                parametroClasss = new Class[1];
                parametroClasss[0] = Array.newInstance(Class.forName(variableClass), 0).getClass();;
            } 
            else
            {
                parametroClasss = new Class[1];
                parametroClasss[0] = Class.forName(variableClass);
            }
  
            Method metodo = ClassUtils.getMethod(cls, setterName, parametroClasss, true);

            if (metodo == null) {

                logger.error("Error getting method: " + cls.getName() + "." + setterName);

                throw new JDESingleException("Error getting method: " + cls.getName() + "." + setterName);
            }

            Object[] parametrosDeInputDelMetodo = null;
            
            if(repeated)
            {
                ArrayList   values = (ArrayList) valueObject;
                
                parametrosDeInputDelMetodo = new Object[1];
                
                //Object[] newArray = values.toArray(new String[values.size()]); 
                
                Object[] newArray2 = arrayListToArray(values);
                
                parametrosDeInputDelMetodo[0] = newArray2;
                 
                
            } else
            {
                parametrosDeInputDelMetodo = new Object[1];
                parametrosDeInputDelMetodo[0] = valueObject;
            }
              
            metodo.invoke(instanceLoaded, parametrosDeInputDelMetodo);

        } catch (ClassNotFoundException ex) {

            logger.error("Class not found " + variableClass + " Error: " + ex.getMessage(), ex);

            throw new JDESingleException("Class not found : " + ex.getMessage(), ex);

        } catch (IllegalAccessException ex) {

            logger.error("Class not found " + variableClass + " Error: " + ex.getMessage(), ex);

            throw new JDESingleException("Class not found : " + ex.getMessage(), ex);

        } catch (IllegalArgumentException ex) {

            logger.error("Class not found " + variableClass + " Error: " + ex.getMessage(), ex);

            throw new JDESingleException("Class not found : " + ex.getMessage(), ex);

        } catch (InvocationTargetException ex) {

            logger.error("Class not found " + variableClass + " Error: " + ex.getMessage(), ex);

            throw new JDESingleException("Class not found : " + ex.getMessage(), ex);

        }

    }
    
    public static <E> E[] arrayListToArray(ArrayList<E> list)
    {
        int s;
        if(list == null || (s = list.size())<1)
            return null;
        E[] temp;
        E typeHelper = list.get(0);

        try
        {
            Object o = Array.newInstance(typeHelper.getClass(), s);
            temp = (E[]) o;

            for (int i = 0; i < list.size(); i++)
                Array.set(temp, i, list.get(i));
        }
        catch (Exception e)
        {return null;}

        return temp;
    }

}