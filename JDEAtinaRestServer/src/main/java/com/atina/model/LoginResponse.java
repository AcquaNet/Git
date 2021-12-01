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
     
    private String addressBookNo;
  
    public LoginResponse() {

    }

    public LoginResponse(String addressBookNo) {
        this.addressBookNo = addressBookNo;
    }
     
    public String getAddressBookNo() {
        return addressBookNo;
    }

    public void setAddressBookNo(String addressBookNo) {
        this.addressBookNo = addressBookNo;
    }
 
}
