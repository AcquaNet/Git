/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mule.modules.atila.jde.exceptions;

public class ExternalConnectorException extends RuntimeException {

    private static final long serialVersionUID = 1997753363232807017L;

    private String errorMessage;
    private String claseDeLaOperacion;
    private String metodoDeLaOperacion;
    private String request;
    private String response;
    private String httpStatusReason = "";
    private int httpStatus = 0;

    public ExternalConnectorException(String errorMessage, String claseDeLaOperacion, String metodoDeLaOperacion, int httpStatus, String httpStatusReason, String request,
            String response, Throwable cause) {
        super(errorMessage, cause);
        this.errorMessage = errorMessage;
        this.claseDeLaOperacion = claseDeLaOperacion;
        this.metodoDeLaOperacion = metodoDeLaOperacion;
        this.request = request;
        this.response = response;
        this.httpStatusReason = httpStatusReason;
        this.httpStatus = httpStatus;
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

}
