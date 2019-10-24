package com.acqua.atina.jdeconnector.internal.model.metadata;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class ModelType {

    private String parameterType; 
    private String parameterName;  
    private int parameterSequence;
    private boolean repetead;
    
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

    public int getParameterSequence() {
        return parameterSequence;
    }

    public void setParameterSequence(int parameterSequence) {
        this.parameterSequence = parameterSequence;
    } 

    public boolean isRepetead() {
        return repetead;
    }

    public void setRepetead(boolean repetead) {
        this.repetead = repetead;
    }

    @Override
    public String toString() {
        return "ModelType{" + "parameterType=" + parameterType + ", parameterName=" + parameterName + ", parameterSequence=" + parameterSequence + ", repetead=" + repetead + '}';
    }
      
}
