/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.jdeconnector.internal.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jdedwards.system.kernel.CallObjectErrorList;
import com.atina.jdeconnectorservice.exception.JDESingleBSFNException;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jgodi
 */
public class JDEBsfnParametersOutputObject extends JDEBsfnParameterObject {
    
    private int returnValue = 0;
    
    private int noErrors = 0;
     
    private String errorsDetail = "";

    public int getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(int returnValue) {
        this.returnValue = returnValue;
    }

    public int getNoErrors() {
        return noErrors;
    }

    public void setNoErrors(int noErrors) {
        this.noErrors = noErrors;
    } 

    public String getErrorsDetail() {
        return errorsDetail;
    }

    public void setErrorsDetail(CallObjectErrorList bsfnListError) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        this.errorsDetail = mapper.writeValueAsString(bsfnListError);
        
        
        try {
            mapper.writerFor(CallObjectErrorList.class).writeValues(new File("C:/tmp/file.json"));
        } catch (IOException ex) {
            Logger.getLogger(JDEBsfnParametersOutputObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         
        

    }

    @Override
    public String toString() {
        return "JDEBsfnOutputObject{" + "returnValue=" + returnValue + ", noErrors=" + noErrors + ", errorsDetail=" + errorsDetail + '}';
    } 
    
}
