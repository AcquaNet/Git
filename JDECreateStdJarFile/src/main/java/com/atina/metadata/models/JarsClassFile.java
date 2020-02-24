/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.metadata.models;

import com.atina.metadata.models.JarClassFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author jgodi
 */
public class JarsClassFile {
    
    private ArrayList<JarClassFile> jars = new ArrayList<JarClassFile>();
    private String metadataFolder;
    private String metadataDriver;

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

    public String getMetadataFolder() {
        return metadataFolder;
    }

    public void setMetadataFolder(String metadataFolder) {
        this.metadataFolder = metadataFolder;
    }

    public String getMetadataDriver() {
        return metadataDriver;
    }

    public void setMetadataDriver(String metadataDriver) {
        this.metadataDriver = metadataDriver;
    } 
    
    public void sort() {
         
        Collections.sort(jars, JarsClassFile.jarComp);
        
    } 
     
    @Override
    public String toString() {
        return "JarsFile{" + "jars=" + Arrays.toString(jars.toArray()) + '}';
    }
 
    
    public static Comparator<JarClassFile> jarComp = new Comparator<JarClassFile>() {

	public int compare(JarClassFile s1, JarClassFile s2) {
	   String jar1 = s1.getName().toUpperCase();
	   String jar2 = s2.getName().toUpperCase();

	   //ascending order
	   return jar1.compareTo(jar2);

	   //descending order
	   //return StudentName2.compareTo(StudentName1);
    }};
    
     
}
