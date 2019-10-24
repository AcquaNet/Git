/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acqua.atina.jdeconnectorservice.wsservice;
 
/**
 *
 * @author jgodi
 */
public class JDESingleWSClientFactory {
    
    /**
     * Creates a new JDE Client Instance
     * 
     * @param user JDE User
     * @param password JDE Password
     * @param environment JDE environment
     * @param role JDE role
     */
    public JDESingleWSClient createInstance(String user, String password, String environment, String role) {
        return new JDESingleWSClient(user, password, environment, role);
    }
    
}
