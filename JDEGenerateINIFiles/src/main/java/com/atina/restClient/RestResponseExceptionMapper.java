/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.restClient;
  
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

/** 
 *
 * @author jgodi
 */
@Provider
public class RestResponseExceptionMapper implements ResponseExceptionMapper<RestException> {

 
    @Override
    public RestException toThrowable(Response rspns) {  
        
        return new RestException(rspns.toString(),rspns.getStatus());
    }
    
}
