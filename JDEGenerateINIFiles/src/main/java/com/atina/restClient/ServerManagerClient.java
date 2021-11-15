/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.restClient;
  
import java.util.HashMap;  
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient; 
import javax.ws.rs.POST;
import javax.ws.rs.Path; 
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType; 
import javax.ws.rs.core.Response;

/**
 *
 * @author jgodi
 */
@Path("/mgmtrestservice")
@RegisterRestClient(configKey="server-manager-api") 
public interface ServerManagerClient {
     
     
    @POST
    @Path("/authenticate")
    @Produces(MediaType.APPLICATION_JSON)
    Response authenticate(@HeaderParam("Authorization") String authorization) throws RestException;
    
}
