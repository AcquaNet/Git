/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.metadata.models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jgodi
 */
public class Operaciones {

    private ArrayList<Operacion> operaciones;

    public Operaciones() {
        this.operaciones = new ArrayList<Operacion>();
    }

    public ArrayList<Operacion> getOperaciones() {
        return operaciones;
    }

    public void setOperaciones(ArrayList<Operacion> operaciones) {
        this.operaciones = operaciones;
    }

    public void addOperacion(Operacion operacion) {
        operaciones.add(operacion);
    }

    public ArrayList<Operacion> getOperaciones(Transaccion transaccion) {

        ArrayList<Operacion> returnValue = new ArrayList<Operacion>();

        for (Operacion operacion : operaciones) {
            if (operacion.getClase().getNombre().equals(transaccion.getNombre())) {
                returnValue.add(operacion);
            }
        }

        return returnValue;
    }

    public boolean contieneMetodo(String operacionInput) {

        boolean returnValue = false;

        for (Operacion operacion : operaciones) {
            if (operacion.getMetodo().compareTo(operacionInput) == 0) {
                return true;
            }
        }

        return returnValue;
    }
      
}
