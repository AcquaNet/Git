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
public class ExternalServiceException extends Exception{
     
    private static final long serialVersionUID = 7718828512143293558L;
    private String errorMessage;
    private String claseDeLaOperacion;
    private String metodoDeLaOperacion;
    private InvocationTargetException exceptionResponse;
    private String request;
    private String response; 
    private String httpStatusReason = "";
    private int httpStatus = 0;
 
    public ExternalServiceException(String claseDeLaOperacion, String metodoDeLaOperacion, InvocationTargetException ex, String request, String response, int httpStatus, String httpStatusReason) {
        super(ex.getMessage(), ex);

        this.errorMessage = ex.getMessage();
        this.claseDeLaOperacion = claseDeLaOperacion;
        this.metodoDeLaOperacion = claseDeLaOperacion + "." + metodoDeLaOperacion;
        this.exceptionResponse = ex;
        this.request = request; 
        this.response = response; 
        this.httpStatus = httpStatus;
        this.httpStatusReason = httpStatusReason;
        
    } 

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public InvocationTargetException getExceptionResponse() {
        return exceptionResponse;
    }

    public void setExceptionResponse(InvocationTargetException exceptionResponse) {
        this.exceptionResponse = exceptionResponse;
    }

    
    public String getRequest() {
        return request;
    }

    public String getResponse() {
        return response;
    } 
    
    public String getHttpStatusReason() {
        return httpStatusReason;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public String getClaseDeLaOperacion() {
        return claseDeLaOperacion;
    }

    public String getMetodoDeLaOperacion() {
        return metodoDeLaOperacion;
    }
     
     
}
