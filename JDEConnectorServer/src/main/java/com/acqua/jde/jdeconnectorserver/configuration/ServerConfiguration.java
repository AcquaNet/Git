/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acqua.jde.jdeconnectorserver.configuration;

/**
 *
 * @author jgodino
 */
public class ServerConfiguration{
    
    private int portServicio;
    
    private String ipServicio;
    
    private String ipLocalServicio;
      
    private String secretKey;

    public ServerConfiguration() {
        this.portServicio = 8085;
        this.ipServicio = "localhost";
        this.ipLocalServicio = "0.0.0.0";  
        this.secretKey = "";
    }

    public int getPortServicio() {
        return portServicio;
    }

    public void setPortServicio(int portServicio) {
        this.portServicio = portServicio;
    }

    public String getIpServicio() {
        return ipServicio;
    }

    public void setIpServicio(String ipServicio) {
        this.ipServicio = ipServicio;
    }

    public String getIpLocalServicio() {
        return ipLocalServicio;
    }

    public void setIpLocalServicio(String ipLocalServicio) {
        this.ipLocalServicio = ipLocalServicio;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
    
}
