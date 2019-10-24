/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acqua.atina.jdeconnectorservice.exception;

import com.jdedwards.system.kernel.CallObjectErrorList; 

/**
 *
 * @author franciscogodinoconte
 */
public class JDESingleException extends JDESingleConnectorException{
     
    private static final long serialVersionUID = 1997753363232807011L;
    
    private final CallObjectErrorList bsfnListError = new CallObjectErrorList();
    
    public JDESingleException(String message) {
        super(message);
    }

    public JDESingleException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public JDESingleException(String message, CallObjectErrorList bsfnListError) {

        super(message);

        for (int i = 0; i < bsfnListError.length(); i++) {

            this.bsfnListError.addError(bsfnListError.getItem(i));

        }

        this.bsfnListError.setBSFNErrorCode(bsfnListError.getBSFNErrorCode());

    }

    public CallObjectErrorList getBsfnListError() {
        return bsfnListError;
    }
   
}
