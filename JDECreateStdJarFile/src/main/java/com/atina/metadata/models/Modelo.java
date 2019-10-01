/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.metadata.models;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author jgodi
 */
public class Modelo {

    private String nombreDelModelo;
    private ArrayList<TipoDelModelo> tipos;
    private boolean errorGettingMetadata;
 
    public Modelo() {

        this.nombreDelModelo = "";
        this.tipos = new ArrayList<TipoDelModelo>();

    }

    public String getNombreDelModelo() {
        return nombreDelModelo;
    }

    public void setNombreDelModelo(String nombreDelModelo) {
        this.nombreDelModelo = nombreDelModelo;
    }

    public ArrayList<TipoDelModelo> getTipos() {
        return tipos;
    }

    public void addTipoDelModelo(TipoDelModelo tipo) {
        this.tipos.add(tipo);
    }

    public boolean isErrorGettingMetadata() {
        return errorGettingMetadata;
    }

    public void setErrorGettingMetadata(boolean errorGettingMetadata) {
        this.errorGettingMetadata = errorGettingMetadata;
    } 

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        Iterator<TipoDelModelo> it = this.tipos.iterator();

        sb.append("Modelo:" + nombreDelModelo);
         
        while (it.hasNext()) {
            sb.append("\n").append(it.next().toString());
        }
        
        String result = sb.toString(); 

        return result;

    }

}
