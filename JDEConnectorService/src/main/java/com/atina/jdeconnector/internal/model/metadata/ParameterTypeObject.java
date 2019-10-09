package com.atina.jdeconnector.internal.model.metadata;

import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class ParameterTypeObject extends ParameterTypeSimple{
   
    private HashMap<String,Object> subParameters;
    
    public ParameterTypeObject() {
        this.subParameters = new HashMap<String,Object>();
    }

    public ParameterTypeObject(String modelType, int parameterSequence, boolean repeated) {
        this.modelType = modelType;
        this.parameterSequence = parameterSequence;
        this.repeated = repeated;
        this.subParameters = new HashMap<String,Object>();
    } 
 
    @Override
    public String toString() {
        return "ParameterTypeObject{" + "modelType=" + modelType + ", parameterSequence=" + parameterSequence + ", repeated=" + repeated + '}';
    }
       
   
}
