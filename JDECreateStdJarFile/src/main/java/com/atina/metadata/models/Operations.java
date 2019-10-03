/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.metadata.models;
 
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jgodi
 */
public class Operations {
  
    
    private Map<String, Operation> operations;

    public Operations() {
        this.operations = new HashMap<String, Operation>();
    }

    public Map<String, Operation> getOperations() {
        return operations;
    }

    public void setOperations(Map<String, Operation> operations) {
        this.operations = operations;
    }

    public void addOperacion(Operation operacion) {
        operations.put(operacion.getOperationModelPackage() + "_" + operacion.getOperationClass() + "_" + operacion.getOperationMethod(), operacion);
    }
 
    public boolean isOperation(String operacion) {
        return operations.containsKey(operacion);
    }
      
}
