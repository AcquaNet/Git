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
    private CallObjectErrorList e1MessagesList;

    public Map<String, Object> getMessageValueObject() {
        return messageValueObject;
    }

    public void setMessageValueObject(Map<String, Object> messageValueObject) {
        this.messageValueObject = messageValueObject;
    }
 
    public CallObjectErrorList getE1MessagesList() {
        return e1MessagesList;
    }

    public void setE1MessagesList(CallObjectErrorList e1MessagesList) {
        this.e1MessagesList = e1MessagesList;
    }

     
}
