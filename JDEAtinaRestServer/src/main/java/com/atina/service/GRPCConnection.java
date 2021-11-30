/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.service;

import java.util.UUID;

/**
 *
 * @author jgodi
 */
public class GRPCConnection {
     
    public static int getChannelId() {
          
        return UUID.randomUUID().hashCode();
    }
     
    
}
