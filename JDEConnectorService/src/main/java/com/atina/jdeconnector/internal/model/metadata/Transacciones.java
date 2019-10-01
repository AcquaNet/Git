/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.jdeconnector.internal.model.metadata;
 
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author jgodi
 */
public class Transacciones {
    
    private Map<String, Transaccion> transacciones;

    public Transacciones() {
        transacciones = new HashMap<String, Transaccion>();    
    }
    
     public void AddTransaccion(Transaccion transaccion) {
        transacciones.put(transaccion.getNombre(), transaccion);
    }

    public Transaccion getTransaccion(String transaccionName) {
        return transacciones.get(transaccionName);
    }

    public Map<String, Transaccion> getTransacciones() {
        return transacciones;
    }
       
}
