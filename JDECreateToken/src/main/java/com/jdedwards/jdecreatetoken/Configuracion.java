/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jdedwards.jdecreatetoken;

/**
 *
 * @author jgodi
 */
public class Configuracion {
    
    private String user;
    private String password;
    private String environment;
    private String role;
    private Integer sessionId;
    private long tokenExpiration;

    public Configuracion(String user, String password, String environment, String role, Integer sessionId, long tokenExpiration) {
        this.user = user;
        this.password = password;
        this.environment = environment;
        this.role = role;
        this.sessionId = sessionId;
        this.tokenExpiration = tokenExpiration;
    }

    public Configuracion() {
        super();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    
    public Integer getSessionId() {
        return sessionId;
    }
    
        public int getSessionIdAsInt() {
        return Integer.parseInt(sessionId.toString());
    }
 
}
