package com.atina.jdeconnectorservice.exception;

/**
 *
 * @author jgodi
 */
public class JDESingleConnectorException  extends RuntimeException{
     
    private static final long serialVersionUID = 1997753363232807015L;

    public JDESingleConnectorException(String message) {
        super(message);
    }

    public JDESingleConnectorException(String message, Throwable cause) {
        super(message, cause);
    }
  
}
