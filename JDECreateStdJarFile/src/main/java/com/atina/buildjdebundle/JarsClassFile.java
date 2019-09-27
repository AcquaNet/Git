/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.buildjdebundle;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author jgodi
 */
public class JarsClassFile {
    
    ArrayList<JarClassFile> jars = new ArrayList<JarClassFile>();

    public JarsClassFile() {
    }

    public ArrayList<JarClassFile> getJars() {
        return jars;
    }

    public void setJars(ArrayList<JarClassFile> jars) {
        this.jars = jars;
    }
    
    public void addJar(JarClassFile jars) {
        this.jars.add(jars);
    } 

    @Override
    public String toString() {
        return "JarsFile{" + "jars=" + Arrays.toString(jars.toArray()) + '}';
    }
 
     
}
