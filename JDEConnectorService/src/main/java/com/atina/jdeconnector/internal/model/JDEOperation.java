/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.jdeconnector.internal.model;

/**
 *
 * @author jgodi
 */
public class JDEOperation {
    
    
    private String operationClass;
    private String operationMethod;

    public JDEOperation(String operationClass, String operationMethod) {
        this.operationClass = operationClass;
        this.operationMethod = operationMethod;
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
     
    
}
