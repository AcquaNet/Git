/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.jdeconnector.internal.model;
 
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jgodi
 */
public abstract class JDEBsfnParameterObject {
  
    private static final Logger logger = LoggerFactory.getLogger(JDEBsfnParameterObject.class);
    
    private Map<String, Object> parameters = new HashMap<String, Object>();

    public Map<String, Object> getParameters() {
        return parameters;
    }
 
 
    public void logParameters() {

        if (parameters != null && !parameters.keySet()
            .isEmpty()) {

            logger.debug("Parameters Begin");

            for (String parameter : parameters.keySet()) {

                if (parameter != null && parameters.get(parameter) != null) {
                    logger.debug("      Key/Value:" + parameter + " [" + parameters.get(parameter) + "]");
                } else {
                    logger.debug("      Key/Value:" + parameter + " []");

                }

            }

            logger.debug("Parameters End");

        }

    }
    
}
