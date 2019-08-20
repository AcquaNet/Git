/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.jdeconnector.internal.model;

import java.util.Map;

/**
 *
 * @author jgodi
 */
public class JDEBsfnParametersInputObject extends JDEBsfnParameterObject {
    
    private boolean throwExceptionWithError = false;
    
    private Integer transactionID = 0;

    public boolean isThrowExceptionWithError() {
        return throwExceptionWithError;
    }

    public void setThrowExceptionWithError(boolean throwExceptionWithError) {
        this.throwExceptionWithError = throwExceptionWithError;
    }

    public Integer getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(Integer transactionID) {
        this.transactionID = transactionID;
    }

    @Override
    public String toString() {
        return "JDEBsfnParametersInputObject{" + "throwExceptionWithError=" + throwExceptionWithError + ", transactionID=" + transactionID + '}';
    }
     
    public boolean hasTransactionID()
    {
        return transactionID!=null && transactionID > 0;
    }
    
    
}
