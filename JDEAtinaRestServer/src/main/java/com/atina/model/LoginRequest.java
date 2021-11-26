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
public class LoginRequest {
    
    private String user;
    private String password;
    private String environment;
    private String role;
    private Long transactionId;

    public LoginRequest() {
    }

    public LoginRequest(String user, String password, String environment, String role, Long transactionId) {
        this.user = user;
        this.password = password;
        this.environment = environment;
        this.role = role;
        this.transactionId = transactionId;
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

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    } 
    
}
