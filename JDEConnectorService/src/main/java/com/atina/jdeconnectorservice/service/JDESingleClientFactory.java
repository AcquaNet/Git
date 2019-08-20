/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.jdeconnectorservice.service;

/**
 *
 * @author jgodi
 */
public class JDESingleClientFactory {
    
    /**
     * Creates a new JDE Client Instance
     * 
     * @param user JDE User
     * @param password JDE Password
     * @param environment JDE environment
     * @param role JDE role
     */
    public JDESingleClient createInstance(String user, String password, String environment, String role) {
        return new JDESingleClient(user, password, environment, role);
    }
    
}
