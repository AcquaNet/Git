package com.atina.metadata.models;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class Operation {

    private String operationModelPackage;
    private String operationClass; 
    private String operationMethod;
    private String operationReturnType;
    private Parameters parameters;
    

    public Operation() { 
        parameters = new Parameters();
    }

    public String getOperationClass() {
        return operationClass;
    }

    public void setOperationClass(String operationClass) {
        this.operationClass = operationClass;
    }
 
    public String getOperationMethod() {
        return operationMethod;
    }

    public void setOperationMethod(String operationMethod) {
        this.operationMethod = operationMethod;
    }

    public String getOperationReturnType() {
        return operationReturnType;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setOperationReturnType(String operationReturnType) {
        this.operationReturnType = operationReturnType;
    }

    public String getOperationModelPackage() {
        return operationModelPackage;
    }

    public void setOperationModelPackage(String operationModelPackage) {
        this.operationModelPackage = operationModelPackage;
    }
     
    
    public Parameter obtenerParametro(String idParametro)
    {
        
        if(parameters.getParameters().containsKey(idParametro))
        {
            return parameters.getParameters().get(idParametro);
        }
         
        return null;
    }

    @Override
    public String toString() {
        return "Operacion{" + "Clase=" + operationModelPackage + "." + operationClass + ", metodo=" + operationMethod + ", returnType=" + operationReturnType + ", parameters=" + parameters + '}';
    } 

}
