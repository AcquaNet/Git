package com.atina.jdeconnector.internal.model.metadata;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class ModelType {

    private String modelType; 
    private String variableName; 
    private Boolean javaClass; 
    
    public ModelType() {
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }
 
    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public Boolean getJavaClass() {
        return javaClass;
    }

    public void setJavaClass(Boolean javaClass) {
        this.javaClass = javaClass;
    }
      
    @Override
    public String toString() {
        return "TipoDelModelo{" + "tipoDeVariable=" + modelType + ", nombreDeLaVariable=" + variableName + '}';
    }
   
}
