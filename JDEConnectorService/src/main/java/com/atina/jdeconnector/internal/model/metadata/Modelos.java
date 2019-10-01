/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.jdeconnector.internal.model.metadata;
 
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 *
 * @author jgodi
 */
public class Modelos {
    
    private Map<String, Modelo> modelos;

    public Modelos() {
        modelos = new HashMap<String, Modelo>();    
    }
    
     public void AddModelo(Modelo modelo) {
        modelos.put(modelo.getNombreDelModelo(), modelo);
    }

    public Modelo getModelo(String nombreDelModelo) {
        return modelos.get(nombreDelModelo);
    }

    public Map<String, Modelo> getModelos() {
        return modelos;
    }

    @Override
    public String toString() {
        
        
        StringBuilder sb = new StringBuilder();
        
        
        Iterator<Modelo> it = this.modelos.values().iterator();
  
        while (it.hasNext()) {
            sb.append(it.next().toString());
            sb.append("\n");
        }
        
        String result = sb.toString(); 

        return result;
         
    }
    
    
       
}
