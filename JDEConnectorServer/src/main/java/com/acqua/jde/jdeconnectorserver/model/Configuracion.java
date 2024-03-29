/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acqua.jde.jdeconnectorserver.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Configuracion {

    private String user;
    private String password;
    private String environment;
    private String role;
    private Integer sessionId;
    private long tokenExpiration;
    private Date tokenDateExpiration;
    private int mocking;

    public Configuracion(String user, String password, String environment, String role, Integer sessionId,long tokenExpiration, int mocking) {
        this.user = user;
        this.password = password;
        this.environment = environment;
        this.role = role;
        this.sessionId = sessionId;
        this.tokenExpiration = tokenExpiration;
        this.mocking = mocking;
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

    public Integer getSessionId() {
        return sessionId;
    }
    
    public int getSessionIdAsInt() {
        return Integer.parseInt(sessionId.toString());
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public long getTokenExpiration() {
        return tokenExpiration;
    }

    public void setTokenExpiration(long tokenExpiration) {
        this.tokenExpiration = tokenExpiration;
    }

    public Date getTokenDateExpiration() {
        return tokenDateExpiration;
    }
    
    public String getTokenDateExpirationFormated() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(this.tokenDateExpiration);
    }

    public void setTokenDateExpiration(Date tokenDateExpiration) {
        this.tokenDateExpiration = tokenDateExpiration;
    }
     
    @Override
    public String toString() {
        return "Configuracion{" + "user=" + user + ", environment=" + environment + ", role=" + role + ", sessionId=" + sessionId + ", tokenExpiration=" + tokenExpiration + ", mocking=" + mocking + "}";
    }
 
    public int getMocking() {
        return mocking;
    }

    public void setMocking(int mocking) {
        this.mocking = mocking;
    }
      
}
