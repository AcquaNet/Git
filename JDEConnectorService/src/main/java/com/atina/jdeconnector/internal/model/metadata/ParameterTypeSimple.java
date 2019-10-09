package com.atina.jdeconnector.internal.model.metadata;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class ParameterTypeSimple {

    String modelType;  
    int parameterSequence;
    boolean repeated;
    
    public ParameterTypeSimple() {
    }

    public ParameterTypeSimple(String modelType, int parameterSequence, boolean repeated) {
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
        return "ParameterTypeSimple{" + "modelType=" + modelType + ", parameterSequence=" + parameterSequence + ", repeated=" + repeated + '}';
    } 
   
}
