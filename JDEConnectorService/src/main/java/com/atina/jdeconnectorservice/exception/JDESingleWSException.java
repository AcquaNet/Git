package com.atina.jdeconnectorservice.exception;

import oracle.e1.bssvfoundation.util.E1Message;

/**
 *
 * @author jgodi
 */
public class JDESingleWSException  extends RuntimeException{
     
    private static final long serialVersionUID = 1997753363232807015L;
    
    private String e1MessageAsJson = null;

    public JDESingleWSException(String message) {
        super(message);
    }

    public JDESingleWSException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public JDESingleWSException(String message, Throwable cause, String e1Message) {
        super(message, cause);
        this.e1MessageAsJson = e1Message;
    }

    public String getE1Message() {
        return e1MessageAsJson;
    }
     
}
