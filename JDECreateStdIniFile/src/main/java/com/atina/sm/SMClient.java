/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.sm;

import com.google.common.net.HttpHeaders;
import java.io.IOException; 
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException; 
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.WWWAuthenticationProtocolHandler;
import org.eclipse.jetty.client.api.AuthenticationStore;
import org.eclipse.jetty.client.api.ContentResponse;  
import org.eclipse.jetty.client.util.BasicAuthentication;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.HttpVersion;

/**
 *
 * @author jgodi
 */
public class SMClient {

    HttpClient client = null;

    public SMClient() throws Exception {

        client = new HttpClient();

        client.start();

    }

    public void stop() {
        
        if(client.isStarted())
        {
            try {
                client.stop();
            } catch (Exception ex) {
                Logger.getLogger(SMClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ContentResponse authenticate(String baseUrl, String authorization) throws InterruptedException, TimeoutException, ExecutionException, URISyntaxException {
          
        ContentResponse response = null;
        
        client.getProtocolHandlers().remove(WWWAuthenticationProtocolHandler.NAME);
        
        response = client.newRequest(baseUrl)
                .scheme("http")
                .header(HttpHeaders.AUTHORIZATION, authorization) 
                .agent("Jetty HTTP client")
                .version(HttpVersion.HTTP_1_1) 
                .method(HttpMethod.POST)
                .timeout(5, TimeUnit.MINUTES)
                .send();
        
        
            
         

        return response;
    }
    
    public ContentResponse authenticate(String baseUrl,String realm, String username, String password) throws InterruptedException, TimeoutException, ExecutionException, URISyntaxException {
          
        ContentResponse response = null;
        
        URI uri =new URI("http://"+ baseUrl);
            
        AuthenticationStore auth  = client.getAuthenticationStore();
        auth.addAuthentication(new BasicAuthentication(uri, realm, username, password));
        
        response = client.newRequest(baseUrl)
                .scheme("http")
                .agent("Jetty HTTP client")
                .version(HttpVersion.HTTP_1_1) 
                .method(HttpMethod.POST)
                .timeout(5, TimeUnit.MINUTES)
                .send();
        

        return response;
    }

    public ContentResponse readServerGroupInfo(String baseUrl, String authorization) throws IOException, InterruptedException, TimeoutException, ExecutionException {

        ContentResponse response = client.newRequest(baseUrl)
                .scheme("http")
                .header("TOKEN", authorization)
                .header(HttpHeaders.ACCEPT, "application/json")
                .agent("Jetty HTTP client")
                .version(HttpVersion.HTTP_1_1)
                .method(HttpMethod.GET)
                .timeout(5, TimeUnit.MINUTES)
                .send();

        return response;
    }

    public ContentResponse getJDEInstanceValues(String baseUrl, String authorization, String instanceName) throws IOException, InterruptedException, TimeoutException, ExecutionException {

        ContentResponse response = client.newRequest(baseUrl)
                .scheme("http")
                .header("TOKEN", authorization)
                .header(HttpHeaders.ACCEPT, "application/json")
                .agent("Jetty HTTP client")
                .param("instanceName", instanceName)
                .version(HttpVersion.HTTP_1_1)
                .method(HttpMethod.GET)
                .timeout(5, TimeUnit.MINUTES)
                .send();

        return response;

    }

}
