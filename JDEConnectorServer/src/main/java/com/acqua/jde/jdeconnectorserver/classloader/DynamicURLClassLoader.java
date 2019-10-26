/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acqua.jde.jdeconnectorserver.classloader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 *
 * @author jgodi
 */
public class  DynamicURLClassLoader  {
    
    
    public static void addURL(URL url) throws SecurityException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
       
        
        
        URLClassLoader sysloader = (URLClassLoader)ClassLoader.getSystemClassLoader();
       
        Class sysclass = URLClassLoader.class;
        
        Class[] parameters = new Class[]{URL.class};
        
        Method method = sysclass.getDeclaredMethod("addURL",parameters);
        
        method.setAccessible(true);
        
        method.invoke(sysloader,new Object[]{ url });
         
        
    }
    
}
