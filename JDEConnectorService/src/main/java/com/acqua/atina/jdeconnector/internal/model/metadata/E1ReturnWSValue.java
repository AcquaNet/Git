/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acqua.atina.jdeconnector.internal.model.metadata;
 
import oracle.e1.bssvfoundation.util.E1MessageList;
import oracle.e1.bssvfoundation.base.MessageValueObject;

/**
 *
 * @author jgodi
 */
public class E1ReturnWSValue {
    
    private MessageValueObject messageValueObject;
    private E1MessageList e1MessageList;

    public MessageValueObject getMessageValueObject() {
        return messageValueObject;
    }

    public void setMessageValueObject(MessageValueObject messageValueObject) {
        this.messageValueObject = messageValueObject;
    }

    public E1MessageList getE1MessageList() {
        return e1MessageList;
    }

    public void setE1MessageList(E1MessageList e1MessageList) {
        this.e1MessageList = e1MessageList;
    } 
}
