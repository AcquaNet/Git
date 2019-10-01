/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.jdeconnector.internal.model.metadata;

/**
 *
 * @author jgodi
 */
public class ParametrosInstalador {
    
    private String protocolo;
    private String server;
    private Integer puerto;
    private String pathSwaggerJson;
    private String tipoDeToken;
    private String token;
    private String connectorName;
    
    private String javaCompiler;
    
    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public Integer getPuerto() {
        return puerto;
    }

    public void setPuerto(Integer puerto) {
        this.puerto = puerto;
    }
 
    public String getPathSwaggerJson() {
        return pathSwaggerJson;
    }

    public void setPathSwaggerJson(String pathSwaggerJson) {
        this.pathSwaggerJson = pathSwaggerJson;
    }

    public String getJavaCompiler() {
        return javaCompiler;
    }

    public void setJavaCompiler(String javaCompiler) {
        this.javaCompiler = javaCompiler;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipoDeToken() {
        return tipoDeToken;
    }

    public void setTipoDeToken(String tipoDeToken) {
        this.tipoDeToken = tipoDeToken;
    }

    public String getConnectorName() {
        return connectorName;
    }

    public void setConnectorName(String connectorName) {
        this.connectorName = connectorName;
    } 
     
}
