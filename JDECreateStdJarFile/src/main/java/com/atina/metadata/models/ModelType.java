package com.atina.metadata.models;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class ModelType {

    private String parameterType; 
    private String parameterName; 
    private Boolean javaClass; 
    private int parameterSequence;
    
    public ModelType() {
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }
 
    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public Boolean getJavaClass() {
        return javaClass;
    }

    public void setJavaClass(Boolean javaClass) {
        this.javaClass = javaClass;
    }

    public int getParameterSequence() {
        return parameterSequence;
    }

    public void setParameterSequence(int parameterSequence) {
        this.parameterSequence = parameterSequence;
    } 
      
    @Override
    public String toString() {
        return "TipoDelModelo{" + "tipoDeVariable=" + parameterType + ", nombreDeLaVariable=" + parameterName + '}';
    }
   
}
