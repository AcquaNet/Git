/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atila.metadata.utils;
 
import java.io.File; 
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jgodi
 */
public class JDEReflection {

    private static final Logger logger = LoggerFactory.getLogger(JDEReflection.class);
    static JarFileLoader loader;
 
    private JDEReflection() {
    }

    public static JDEReflection getInstance() {
        return DragonFishReflectionHolder.INSTANCE;
    }

    private static class DragonFishReflectionHolder {

        private static final JDEReflection INSTANCE = new JDEReflection();
    }

    public void loadJarFile(File jarFile) throws MalformedURLException {

        URI uriJarFileName = jarFile.toURI();

        URL urls[] = {};

        loader = new JarFileLoader(urls);

        loader.addJarFile(uriJarFileName.toString());

    } 
    
    public Method obtenerMetodo(Class serviceClass, String methodName) throws MalformedURLException, ClassNotFoundException {
 
        Method metodo = null;

        for (Method dm : serviceClass.getDeclaredMethods()) {
            if (dm.getName().equals(methodName)) {
                metodo = dm;
            }
        }
        
        return metodo;
  
    } 
     
    private static Class<?> retrieveMethodArgument(Class<?> serviceClass, String methodName) {
        return retrieveMethodArgument(serviceClass, methodName, null, 0);
    }

    private static Class<?> retrieveMethodArgument(Class<?> serviceClass, String methodName, Class<?> filter, int index) {

        List<Method> candidates = new LinkedList<>();

        for (Method dm : serviceClass.getDeclaredMethods()) {
            if (dm.getName().equals(methodName)) {
                candidates.add(dm);
            }
        }

        if (candidates.isEmpty()) {
            logger.info("Could not find method " + methodName + " in class " + serviceClass.getName());
            return null;
        }

        for (Method m : candidates) {
            Class<?> argument = m.getParameterTypes().length >= index + 1 ? m.getParameterTypes()[index] : null;

            if (argument == null) {
                //not the right one.
                continue;
            }

            if (filter == null) {
                //blindly return whatever we found first.
                return argument;
            }

            //this means we have a filter
            if (filter.isAssignableFrom(argument)) {
                return argument;
            } else {
                continue;
            }
        }
        logger.info("method " + methodName + " not found or does not take at least " + index + " argument(s).");
        return null;
    }

    private void logMethodNotFound(String name, Throwable cause) {
        logger.error("Could not find gRPC Service Method " + name, cause);
    }

}
