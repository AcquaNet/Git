/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.jdeconnector.internal.model.metadata;
 
import oracle.e1.bssvfoundation.util.E1MessageList;
import oracle.e1.bssvfoundation.base.MessageValueObject;

/**
 *
 * @author jgodi
 */
public class E1ReturnWSValue {
    
    private MessageValueObject messageValueObject;
    private E1MessageList e1MessagesList;

    public MessageValueObject getMessageValueObject() {
        return messageValueObject;
    }

    public void setMessageValueObject(MessageValueObject messageValueObject) {
        this.messageValueObject = messageValueObject;
    }

    public E1MessageList getE1MessagesList() {
        return e1MessagesList;
    }

    public void setE1MessagesList(E1MessageList e1MessagesList) {
        this.e1MessagesList = e1MessagesList;
    } 
}
