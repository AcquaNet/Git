package com.atina.jdeconnectorservice.exception;

import oracle.e1.bssvfoundation.util.E1Message;

/**
 *
 * @author jgodi
 */
public class JDESingleConnectorException  extends RuntimeException{
     
    private static final long serialVersionUID = 1997753363232807015L;
    
    private E1Message e1Message = null;

    public JDESingleConnectorException(String message) {
        super(message);
    }

    public JDESingleConnectorException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public JDESingleConnectorException(String message, Throwable cause, E1Message e1Message) {
        super(message, cause);
        this.e1Message = e1Message;
    }

    public E1Message getE1Message() {
        return e1Message;
    }
     
}
