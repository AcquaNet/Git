/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.jdeconnector.internal.model;

/**
 *
 * @author jgodi
 */
public class JDEBsfnParameter {
    
    private String name = "";

    private String dataType = "";

    private int length = 0;
 
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "JDEBsfnParameter{" + "name=" + name + ", dataType=" + dataType + ", length=" + length + '}';
    }
             
            
}
