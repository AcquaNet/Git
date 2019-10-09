package com.atina.jdeconnector.internal.model.metadata;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class SimpleParameterType {

    private String modelType;  
    private int parameterSequence;
    private boolean repeated;
    
    public SimpleParameterType() {
    }

    public SimpleParameterType(String modelType, int parameterSequence, boolean repeated) {
        this.modelType = modelType;
        this.parameterSequence = parameterSequence;
        this.repeated = repeated;
    } 

    public boolean isRepeated() {
        return repeated;
    }

    public void setRepeated(boolean repeated) {
        this.repeated = repeated;
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
