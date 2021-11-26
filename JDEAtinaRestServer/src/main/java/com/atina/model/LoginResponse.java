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
public class LoginResponse {
    
    private String token;
    private String addressBookNo;
    private Integer sessionId;

    public LoginResponse() {

    }

    public LoginResponse(String token, String addressBookNo, Integer sessionId) {
        this.token = token;
        this.addressBookNo = addressBookNo;
        this.sessionId = sessionId;
    }
     
    public String getAddressBookNo() {
        return addressBookNo;
    }

    public void setAddressBookNo(String addressBookNo) {
        this.addressBookNo = addressBookNo;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }
    
      
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
     
    
}
