package com.atina.jdeconnector.internal.model.metadata;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class SimpleParameterType {

    private String modelType;  
    private int parameterSequence;
    
    public SimpleParameterType() {
    }

    public SimpleParameterType(String modelType, int parameterSequence) {
        this.modelType = modelType;
        this.parameterSequence = parameterSequence;
    }
     
    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    } 
    
    public int getParameterSequence() {
        return parameterSequence;
    }

    public void setParameterSequence(int parameterSequence) {
        this.parameterSequence = parameterSequence;
    } 

    @Override
    public String toString() {
        return "SimpleParameterType{" + "modelType=" + modelType + ", parameterSequence=" + parameterSequence + '}';
    }
       
   
}
