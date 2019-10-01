/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.buildjdebundle.exceptions;

/**
 * https://stackify.com/java-custom-exceptions/
 * @author jgodi
 */
public class MetadataServerException extends Exception{
     
    private static final long serialVersionUID = 7718828512143293558L;

    public MetadataServerException() {
    }
    
     public MetadataServerException(String message) {
        super(message); 
    } 

     public MetadataServerException(String message, Throwable cause) {
        super(message, cause); 
    }
     
     public MetadataServerException(Throwable cause) {
        super(cause); 
    }
  
    
}
