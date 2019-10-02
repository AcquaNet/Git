/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.metadata.models;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author jgodi
 */
public class Model {

    private String modelPackage;
    private String modelName;
    private ArrayList<ModelType> parametersType; 
 
    public Model() {

        this.modelName = "";
        this.parametersType = new ArrayList<ModelType>();

    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public ArrayList<ModelType> getParametersType() {
        return parametersType;
    }

    public void addTipoDelModelo(ModelType tipo) {
        this.parametersType.add(tipo);
    }

    public String getModelPackage() {
        return modelPackage;
    }

    public void setModelPackage(String modelPackage) {
        this.modelPackage = modelPackage;
    }
  
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        Iterator<ModelType> it = this.parametersType.iterator();

        sb.append("Modelo:" + modelPackage + "." + modelName);
         
        while (it.hasNext()) {
            sb.append("\n").append(it.next().toString());
        }
        
        String result = sb.toString(); 

        return result;

    }

}
