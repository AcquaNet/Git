/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.cliente.exceptions;

public class ExternalConnectorException extends RuntimeException {

    private static final long serialVersionUID = 1997753363232807017L;

    private String errorMessage;
    private String claseDeLaOperacion;
    private String metodoDeLaOperacion;
    private String request;
    private String response;
    private String httpStatusReason = "";
    private int httpStatus = 0;
    private String e1Message = null;

    public ExternalConnectorException(String errorMessage, String claseDeLaOperacion, String metodoDeLaOperacion, int httpStatus, String httpStatusReason, String request,
            String response, String e1Message, Throwable cause) {
        super(errorMessage, cause);
        this.errorMessage = errorMessage;
        this.claseDeLaOperacion = claseDeLaOperacion;
        this.metodoDeLaOperacion = metodoDeLaOperacion;
        this.request = request;
        this.response = response;
        this.httpStatusReason = httpStatusReason;
        this.httpStatus = httpStatus;
        this.e1Message = e1Message;
    }

    public ExternalConnectorException(String message) {
        super(message);
    }

    public String getClaseDeLaOperacion() {
        return claseDeLaOperacion;
    }

    public String getMetodoDeLaOperacion() {
        return metodoDeLaOperacion;
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

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getE1Message() {
        return e1Message;
    }

}
