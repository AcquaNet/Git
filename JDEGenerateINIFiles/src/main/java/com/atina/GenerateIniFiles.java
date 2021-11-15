/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina;

import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author jgodi
 */


@ApplicationScoped
public class GenerateIniFiles {
    
    public String generateFiles(Configuration config) {
        return "hello " + config.getUser()  + config.getPassword();
    }
    
    
}
