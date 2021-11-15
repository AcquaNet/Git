/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.sm;

import org.eclipse.microprofile.config.inject.ConfigProperties;

/**
 *
 * @author jgodi
 */
@ConfigProperties(prefix = "post-service")
public class SMClientProperties {
    
    private String baseUrl;
    
     public String getBaseUrl() {
        return baseUrl;
    }
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
