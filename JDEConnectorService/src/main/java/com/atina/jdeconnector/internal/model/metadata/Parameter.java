/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.jdeconnector.internal.model.metadata;
 

/**
 *
 * @author jgodi
 */
public class Parameter implements Comparable<Parameter>{
    
    private String parameterName;   
    private String parameterType; 
    private int parameterSequence;
    private Boolean javaClass; 

    public Parameter() {
    } 
    
    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }
 
    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    
    public int getParameterSequence() {
        return parameterSequence;
    }

    public void setParameterSequence(int parameterSequence) {
        this.parameterSequence = parameterSequence;
    }

    public Boolean getJavaClass() {
        return javaClass;
    }

    public void setJavaClass(Boolean javaClass) {
        this.javaClass = javaClass;
    }
     
    @Override
    public String toString() {
        return "Parametro{" + "nombre=" + parameterName + ", Type=" + parameterType + ", secuencia=" + parameterSequence + '}';
    }

    @Override
    public int compareTo(Parameter o) {
        return getParameterSequence() - o.getParameterSequence();
    }
       
}