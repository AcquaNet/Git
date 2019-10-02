/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.metadata.models;

import java.util.ArrayList; 

/**
 *
 * @author jgodi
 */
public class Operations {

    private ArrayList<Operation> operations;

    public Operations() {
        this.operations = new ArrayList<Operation>();
    }

    public ArrayList<Operation> getOperations() {
        return operations;
    }

    public void setOperations(ArrayList<Operation> operations) {
        this.operations = operations;
    }

    public void addOperacion(Operation operacion) {
        operations.add(operacion);
    }
 
    public boolean contieneMetodo(String operacionInput) {

        boolean returnValue = false;

        for (Operation operacion : operations) {
            if (operacion.getOperationMethod().compareTo(operacionInput) == 0) {
                return true;
            }
        }

        return returnValue;
    }
      
}
