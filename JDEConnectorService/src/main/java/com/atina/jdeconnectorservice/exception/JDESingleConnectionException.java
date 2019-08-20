package com.atina.jdeconnectorservice.exception;
  
/**
 *
 * @author jgodi
 */
public class JDESingleConnectionException  extends JDESingleConnectorException{
    
    private static final long serialVersionUID = 1997753363232807015L;

    public JDESingleConnectionException(String message) {
        super(message);
    }

    public JDESingleConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

}
