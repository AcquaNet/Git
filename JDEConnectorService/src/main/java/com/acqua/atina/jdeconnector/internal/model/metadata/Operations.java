/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acqua.atina.jdeconnector.internal.model.metadata;
 
import com.acqua.atina.jdeconnectorservice.JDEConnectorService;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jgodi
 */
public class Operations {
    
   private static final Logger logger = LoggerFactory.getLogger(JDEConnectorService.class);

   private Map<String, Operation> operations;

    public Operations() {
        this.operations = new HashMap<String, Operation>();
    }

    public Map<String, Operation> getOperations() {
        return operations;
    }

    public void setOperations(Map<String, Operation> operations) {
        this.operations = operations;
    }

    public void addOperacion(Operation operacion) {
        operations.put(operacion.getOperationModelPackage() + "." + operacion.getOperationClass(), operacion);
    }
 
    public String getInputValueObject(String operation) {

        String returnValue = "";
        
        logger.info("ATINA - Operations: getInputValueObject for : [" + operation + "] Exists: " + operations.containsKey(operation));
        
        logger.info("ATINA - Operations: getInputValueObject Size : " + operations.size() );

        Operation operacion = operations.get(operation);

        if (operacion != null) {

            Parameter parameter = operacion.getParameters().getParameters().get("vo");
            
            logger.info("ATINA - Operations: Parameter : [" + parameter.getParameterType());

            return parameter.getParameterType();

        } 

        return returnValue;

    }

    public String getOutputValueObject(String operation) {
  
        Operation operacion = operations.get(operation);
         
        return operacion.getOperationReturnType();
    }
    
    public boolean isOperation(String operacion) {
        return operations.containsKey(operacion);
    }
      
}
