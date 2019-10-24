/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acqua.jde.jdeconnectorserver.exceptions;

import java.lang.reflect.InvocationTargetException;

/**
 * https://stackify.com/java-custom-exceptions/
 * @author jgodi
 */
public class InternalServerException extends Exception{
     
    private static final long serialVersionUID = 7718828512143293558L;
    private String errorMessage;
    private String claseDeLaOperacion;
    private String metodoDeLaOperacion;
    private Exception exceptionResponse;
    private String request;

    public InternalServerException() {
    }
    
     public InternalServerException(String message) {
        super(message); 
    } 

    public InternalServerException(String message, String claseDeLaOperacion, String metodoDeLaOperacion,  String request , Exception exceptionResponse) {
        super(message); 
        this.errorMessage = exceptionResponse.getMessage();
        this.claseDeLaOperacion = claseDeLaOperacion;
        this.metodoDeLaOperacion = metodoDeLaOperacion;
        this.exceptionResponse = exceptionResponse;
        this.request = request;
    } 

    public String getClaseDeLaOperacion() {
        return claseDeLaOperacion;
    }

    public void setClaseDeLaOperacion(String claseDeLaOperacion) {
        this.claseDeLaOperacion = claseDeLaOperacion;
    }

    public String getMetodoDeLaOperacion() {
        return metodoDeLaOperacion;
    }

    public void setMetodoDeLaOperacion(String metodoDeLaOperacion) {
        this.metodoDeLaOperacion = metodoDeLaOperacion;
    }

    public Exception getExceptionResponse() {
        return exceptionResponse;
    }

    public void setExceptionResponse(InvocationTargetException exceptionResponse) {
        this.exceptionResponse = exceptionResponse;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
     
     

     public InternalServerException(String message, Throwable cause) {
        super(message, cause); 
    }
     
     public InternalServerException(Throwable cause) {
        super(cause); 
    }
 
    
}
