package com.atina.jdeconnector.internal.model.metadata;

import java.util.HashMap;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class ParameterTypeObject extends ParameterTypeSimple{
   
    private HashMap<String,ParameterTypeSimple> subParameters;
    
    public ParameterTypeObject() {
        this.subParameters = new HashMap<String,ParameterTypeSimple>();
    }

    public ParameterTypeObject(String modelType, int parameterSequence, boolean repeated) {
        this.modelType = modelType;
        this.parameterSequence = parameterSequence;
        this.repeated = repeated;
        this.subParameters = new HashMap<String,ParameterTypeSimple>();
    } 
    
    public void addParameterType(String parameterName, HashMap<String,ParameterTypeSimple> parameterType) {
        
        for (Map.Entry<String, ParameterTypeSimple> entry : parameterType.entrySet()) {
            this.subParameters.put(entry.getKey(), entry.getValue());
        } 
        
    } 

    public HashMap<String, ParameterTypeSimple> getSubParameters() {
        return subParameters;
    } 
 
    @Override
    public String toString() {
        return "ParameterTypeObject{" + "modelType=" + modelType + ", parameterSequence=" + parameterSequence + ", repeated=" + repeated + '}';
    }
       
   
}
