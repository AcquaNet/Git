/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acqua.atina.jdeconnector.internal.model.metadata;
 
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jgodi
 */
public class Parameters {
    
    Map<String, Parameter> parameters;

    public Parameters() {
        
        parameters = new HashMap<String, Parameter>();
    }
    
    public Map<String, Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Parameter> parameters) {
        this.parameters = parameters;
    } 
     
}
