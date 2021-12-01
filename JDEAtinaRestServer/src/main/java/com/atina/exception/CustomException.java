/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.exception;
import java.io.Serializable;

/**
 *
 * @author jgodi
 */
public class CustomException extends
        RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private final String channelID;
    private final Long TransactionId;

    public CustomException(String message, Throwable cause, String channelID, Long TransactionId) {
        super(message, cause);
        this.channelID = channelID;
        this.TransactionId = TransactionId;
    }

    public String getChannelID() {
        return channelID;
    }

    public Long getTransactionId() {
        return TransactionId;
    }
     
}
