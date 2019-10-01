/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.jdeconnector.internal.model.metadata;
 

/**
 *
 * @author jgodi
 */
public class Parametro implements Comparable<Parametro>{
    
    private String nombre;   
    private String Type; 
    private int secuencia;

    public Parametro() {
    } 
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
 
    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    
    public int getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(int secuencia) {
        this.secuencia = secuencia;
    }

    @Override
    public String toString() {
        return "Parametro{" + "nombre=" + nombre + ", Type=" + Type + ", secuencia=" + secuencia + '}';
    }

    @Override
    public int compareTo(Parametro o) {
        return getSecuencia() - o.getSecuencia();
    }
       
}
