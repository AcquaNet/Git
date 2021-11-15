/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.sm;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

/**
 *
 * @author jgodi
 */
@ApplicationScoped
public class SMClient {
    
    private ExecutorService executorService = Executors.newCachedThreadPool();
    
    private Client client; 
    
    @Inject
    public SMClient() {
         
        client = ClientBuilder.newBuilder()
                .executorService(executorService)
                .build();
        
    } 
    
    public Response authenticateE(String baseUrl) {
          
        return client.target(baseUrl + "/mgmtrestservice/authenticate")
                .request()  
                .get(Response.class);
    }
     
}
