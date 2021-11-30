/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.cliente.exception;

/**
 *
 * @author jgodi
 */
public class ConnectionException extends RuntimeException {
    
    private static final long serialVersionUID = 1997753363232807014L;
    
      public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException(String message, Exception e) {
        super(message,e);
    }
 
      
}
