/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.metadata.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 *
 * @author jgodi
 */
public class JarFileLoader extends URLClassLoader {

    
    public JarFileLoader(URL[] urls) {
        super(urls);
    }
      
     public void addJarFile (String path) throws MalformedURLException
    {
        String urlPath = "jar:" + path + "!/";
        addURL (new URL (urlPath));
    }
   

}
