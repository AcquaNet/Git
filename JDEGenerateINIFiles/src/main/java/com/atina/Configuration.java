/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina;

/**
 *
 * @author jgodi
 */
public class Configuration {
    
    private String user;
    private String password;
    private String token;

    public Configuration() {
    }

    public Configuration(String user, String password) {
        this.user = user;
        this.password = password;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Configuration{" + "user=" + user + ", token=" + token + '}';
    }
     
}
