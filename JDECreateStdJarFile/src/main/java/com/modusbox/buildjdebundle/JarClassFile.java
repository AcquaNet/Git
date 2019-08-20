/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modusbox.buildjdebundle;

import java.io.File;

/**
 *
 * @author jgodi
 */
public class JarClassFile {
    
    private String name = "";
    private File jarFile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
 
    
    public JarClassFile(File jarFile) {
        int pos = jarFile.getName().lastIndexOf(".");
        this.name = jarFile.getName().substring(0, pos);
        this.jarFile = jarFile;
    }

    public String getNameWithoutExtension() { 
        return name;
    }
  
    public File getJarFile() {
        return jarFile;
    }

    public void setJarFile(File jarFile) {
        this.jarFile = jarFile;
    } 

    @Override
    public String toString() {
        return "JarFile{" + "name=" +name + '}';
    } 
    
}
