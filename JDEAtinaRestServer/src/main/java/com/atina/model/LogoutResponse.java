/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.model;

/**
 *
 * @author jgodi
 */
public class LogoutResponse {

    private String token;
    private Integer sessionId;

    public LogoutResponse() {

    }
 
    public LogoutResponse(String token, Integer sessionId) {
        this.token = token;
        this.sessionId = sessionId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }
     
}
