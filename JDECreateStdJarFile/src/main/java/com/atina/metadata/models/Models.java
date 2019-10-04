/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.metadata.models;
 
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 *
 * @author jgodi
 */
public class Models {
    
    private Map<String, Model> models;

    public Models() {
        models = new HashMap<String, Model>();    
    }
    
     public void AddModelo(Model modelo) {
        models.put(modelo.getModelPackage() + "." + modelo.getModelName(), modelo);
    }

    public Model getModelo(String nombreDelModelo) {
        return models.get(nombreDelModelo);
    }

    public Map<String, Model> getModels() {
        return models;
    }

    @Override
    public String toString() {
        
        
        StringBuilder sb = new StringBuilder();
        
        
        Iterator<Model> it = this.models.values().iterator();
  
        while (it.hasNext()) {
            sb.append(it.next().toString());
            sb.append("\n");
        }
        
        String result = sb.toString(); 

        return result;
         
    }
    
    
       
}
