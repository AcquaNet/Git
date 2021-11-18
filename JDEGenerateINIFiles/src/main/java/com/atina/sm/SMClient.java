/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.sm;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author jgodi
 */
@ApplicationScoped
public class SMClient {
    
    private final ExecutorService executorService = Executors.newFixedThreadPool(1);
      
    private final HttpClient httpClient = HttpClient.newBuilder()
            .executor(executorService)
            .version(HttpClient.Version.HTTP_2)
            .build();
    
    public SMClient() {
    }
    
    public HttpResponse<String> authenticate(String baseUrl, String authorization, String api) throws IOException, InterruptedException {
         
        HttpRequest request = HttpRequest.newBuilder()
                .POST(BodyPublishers.ofString(""))
                .timeout(Duration.ofMinutes(10))
                .uri(URI.create(baseUrl + api))
                .header("Authorization", authorization)
                .build();
        
       return  this.httpClient.send(request,BodyHandlers.ofString());
    }
    
    public HttpResponse<String> readServerGroupInfo(String baseUrl, String authorization, String api) throws IOException, InterruptedException {
         
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .timeout(Duration.ofMinutes(10))
                .uri(URI.create(baseUrl + api))
                .header("TOKEN", authorization)
                .header("Accept", "application/json")
                .build();
        
       return  this.httpClient.send(request,BodyHandlers.ofString());
    }
    
     public HttpResponse<String> getJDEInstanceValues(String baseUrl, String authorization, String api, String instanceName) throws IOException, InterruptedException {
         
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .timeout(Duration.ofMinutes(10))
                .uri(URI.create(baseUrl + api + "?instanceName=" + instanceName))
                .header("TOKEN", authorization)
                .header("Accept", "application/json")
                .build();
        
       return  this.httpClient.send(request,BodyHandlers.ofString());
    }
    
}
