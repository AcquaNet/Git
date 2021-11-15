/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.restClient;
  
/**
 *
 * @author jgodi
 */
public class RestException extends RuntimeException  {

    private static final long serialVersionUID = 2L;
    
    private String statusMessage;
    private int statusCode;
    /**
     * Creates a new instance of <code>RestException</code> without detail
     * message.
     */
    public RestException() {
         
    }

    /**
     * Constructs an instance of <code>RestException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public RestException(String msg, int statusCode) {
        super(msg);
        this.statusMessage = msg;
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
     
    
}
