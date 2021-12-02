/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.exception;

import com.atina.cliente.exception.ExternalConnectorException;
import com.atina.cliente.exception.InternalConnectorException;
import javax.json.Json; 
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CustomExceptionHandler implements ExceptionMapper<CustomException> {
 
    String userNotFound = "";

    @Override
    public Response toResponse(CustomException e) {
 
        Throwable origin = e.getCause().getCause();
        
        if(origin instanceof InternalConnectorException)
        {
            InternalConnectorException exc = (InternalConnectorException)origin;
            
            ErrorMessage em = new ErrorMessage();
            em.setErrorMessage(exc.getErrorMessage());
            em.setErrorDetail(exc.getMetodoDeLaOperacion());  
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(em)
                        .header("ChannelId", e.getChannelID())
                        .header("TransactionId", e.getTransactionId())
                        .build();
             
        } else if(origin instanceof ExternalConnectorException)
        {
            ExternalConnectorException exc = (ExternalConnectorException)origin;
            
            ErrorMessage em = new ErrorMessage();
            em.setErrorMessage(exc.getErrorMessage());
            em.setErrorDetail(exc.getMetodoDeLaOperacion());  
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .header("ChannelId", e.getChannelID())
                        .header("TransactionId", e.getTransactionId())
                        .entity(em)
                        .build();
            
        } else
        {
            ErrorMessage em = new ErrorMessage();
            em.setErrorMessage(e.getMessage());
            em.setErrorDetail("");  
            
            return Response.status(Response.Status.BAD_REQUEST)
                                .header("ChannelId", e.getChannelID())
                                .header("TransactionId", e.getTransactionId())
                                .entity(em)
                                .build();
        }
        
      
    }
}