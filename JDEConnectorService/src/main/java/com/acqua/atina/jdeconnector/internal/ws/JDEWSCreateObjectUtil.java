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
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map; 
import java.util.Set;
import org.mule.util.ClassUtils;

/**
 *
 * @author franciscogodinoconte
 */
public class JDEWSCreateObjectUtil {

    private static final Logger logger = LoggerFactory.getLogger(JDEConnectorService.class);
    
    private static final Set<String> PRIMITIVE_TYPES = new HashSet(Arrays.asList(
    "boolean", "byte", "char", "float", "int", "long", "short", "double"));
    
    public static boolean isPrimitiveType(String clazz) {
        return PRIMITIVE_TYPES.contains(clazz);
    }

    public static Object generateObject(String inputModelClass, Models models, Model model, HashMap<String, Object> inputParameters) {

        // -------------------------------------------------------------
        // Load Class
        // -------------------------------------------------------------
        //
        
        logger.info("JDEWSCreateObjectUtil: Generata Java Object: " + inputModelClass);
        
        Class inputValueClass = loadClass(inputModelClass);
        
        logger.info("JDEWSCreateObjectUtil: Generata Java Object Done");

        // -------------------------------------------------------------
        // Instance Class
        // -------------------------------------------------------------
        //
        
        logger.info("JDEWSCreateObjectUtil: Instancing Java Object " + inputModelClass);
        
        Object instanceLoaded = instanceClass(inputModelClass);
        
        logger.info("JDEWSCreateObjectUtil: Instancing Java Object " + inputModelClass + " Done");

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
            
            logger.info("JDEWSCreateObjectUtil:                   ----------------------------------------------------------------");
            logger.info("JDEWSCreateObjectUtil:                   Parameter: " + inputParameter.getKey());
            
            ModelType currentModelType = (ModelType) model.getParametersType().get(inputParameter.getKey());
             
            String variableName = currentModelType.getParameterName();
            String parameterType = currentModelType.getParameterType();
            boolean repeated = currentModelType.isRepetead();
            
            logger.info("JDEWSCreateObjectUtil:                   Parameter Name: " + variableName);
            logger.info("JDEWSCreateObjectUtil:                   Parameter Type: " + parameterType);
            logger.info("JDEWSCreateObjectUtil:                   Parameter Repeated: " + repeated);
            logger.info("JDEWSCreateObjectUtil:                   Parameter Seq: " + currentModelType.getParameterSequence());
             
            // Check Parameter Type
            if (models.isModel(parameterType)) {
                
                Model newModel = models.getModelo(parameterType);
                
                Object parameterInstance = null;
                
                if(currentModelType.isRepetead())
                {
                    ArrayList parameterInstanceList = new ArrayList();
                    
                    for(Object value:(ArrayList) inputParameter.getValue())
                    {
                        Object parameterInstanceObject = generateObject(parameterType,models, newModel, (HashMap<String, Object>) value);
                        
                        parameterInstanceList.add(parameterInstanceObject);
                        
                    }
                    
                    setValue(inputValueClass,instanceLoaded, variableName, parameterType, parameterInstanceList, i, currentModelType.isRepetead());
                    
                }
                else
                {
                    parameterInstance = generateObject(parameterType,models, newModel, (HashMap<String, Object>) inputParameter.getValue());
                    setValue(inputValueClass,instanceLoaded, variableName, parameterType, parameterInstance, i, currentModelType.isRepetead());
                } 
                
                

            } else {
                 
                setValue(inputValueClass,instanceLoaded, variableName, parameterType, inputParameter.getValue(),i, currentModelType.isRepetead());
                
                 
            }

            logger.info("JDEWSCreateObjectUtil:                   Value Stted");
            
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
            
            parametroClasss = new Class[1];
            
            Field field = cls.getDeclaredField(variableName);
            
            parametroClasss[0] = field.getType();
           
            Method metodo = ClassUtils.getMethod(cls, setterName, parametroClasss, true);

            if (metodo == null) {

                logger.error("Error getting method: " + cls.getName() + "." + setterName);

                throw new JDESingleException("Error getting method: " + cls.getName() + "." + setterName);
            }

            Object[] parametrosDeInputDelMetodo = null;
            
            if(repeated)
            {
                
                if(variableClass.equals("byte"))
                {
                    byte[] imageByteArray = Base64.getDecoder().decode((String) valueObject);
                     
                    parametrosDeInputDelMetodo = new Object[1];
                    parametrosDeInputDelMetodo[0] = imageByteArray;
                    metodo.invoke(instanceLoaded, parametrosDeInputDelMetodo);
                    
                } else
                {
                    
                    ArrayList   values = (ArrayList) valueObject;

                    if(values.size() > 0)
                    {

                        Object[] newArray2 = arrayListToArray(values);
                        parametrosDeInputDelMetodo = new Object[1];
                        parametrosDeInputDelMetodo[0] = newArray2;

                        metodo.invoke(instanceLoaded, parametrosDeInputDelMetodo);

                    }
                
                }
                
            } else
            {
                parametrosDeInputDelMetodo = new Object[1];
                
                if(isPrimitiveType(variableClass))
                {
                    switch (variableClass) {
                        
                        case "int":
                            
                            int value = ((Integer)valueObject).intValue();  
                            parametrosDeInputDelMetodo[0] = value;
                             metodo.invoke(instanceLoaded, value);
                             break;
                                    
                    }
                     
                    
                } else
                {
                    parametrosDeInputDelMetodo[0] = valueObject;
                    metodo.invoke(instanceLoaded, parametrosDeInputDelMetodo);
                }
                 
            }
              
            

        }  catch (IllegalAccessException ex) {

            logger.error("Error setting value. Illegal Access Exception " + variableClass + " Error: " + ex.getMessage(), ex);

            throw new JDESingleException("Error setting value. Illegal Access Exception " + ex.getMessage(), ex);

        } catch (IllegalArgumentException ex) {

            logger.error("Error setting value. Illegal Argument Exception " + variableClass + " Error: " + ex.getMessage(), ex);

            throw new JDESingleException("Error setting value. Illegal Argument Exception " + ex.getMessage(), ex);

        } catch (InvocationTargetException ex) {

            logger.error("Error setting value. Invocation Target Exception " + variableClass + " Error: " + ex.getMessage(), ex);

            throw new JDESingleException("Error setting value. Invocation Target Exception " + ex.getMessage(), ex);

        } catch (NoSuchFieldException ex) {
            
            logger.error("Error setting value. No Such Field Exception " + variableClass + " Error: " + ex.getMessage(), ex);

            throw new JDESingleException("Error setting value. No Such Field Exception " + ex.getMessage(), ex);
            
        } catch (SecurityException ex) {
            
            logger.error("Error setting value. Security Exception " + variableClass + " Error: " + ex.getMessage(), ex);

            throw new JDESingleException("Error setting value. Security Exception: " + ex.getMessage(), ex);
            
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
