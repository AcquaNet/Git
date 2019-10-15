/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.jdeconnector.internal.model.metadata;
 
import com.jdedwards.system.kernel.CallObjectErrorList;
import java.util.Map;
import oracle.e1.bssvfoundation.util.E1MessageList;
import oracle.e1.bssvfoundation.base.MessageValueObject;

/**
 *
 * @author jgodi
 */
public class E1ReturnBSFNValue {
    
    private Map<String, Object> messageValueObject;
    private CallObjectErrorList e1MessageList;

    public Map<String, Object> getMessageValueObject() {
        return messageValueObject;
    }

    public void setMessageValueObject(Map<String, Object> messageValueObject) {
        this.messageValueObject = messageValueObject;
    }
 
    public CallObjectErrorList getE1MessageList() {
        return e1MessageList;
    }

    public void setE1MessageList(CallObjectErrorList e1MessageList) {
        this.e1MessageList = e1MessageList;
    }

     
}
