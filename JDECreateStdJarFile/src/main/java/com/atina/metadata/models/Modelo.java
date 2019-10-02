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

    private String modelPackage;
    private String nombreDelModelo;
    private ArrayList<TipoDelModelo> tipos; 
 
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

    public String getModelPackage() {
        return modelPackage;
    }

    public void setModelPackage(String modelPackage) {
        this.modelPackage = modelPackage;
    }
  
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        Iterator<TipoDelModelo> it = this.tipos.iterator();

        sb.append("Modelo:" + modelPackage + "." + nombreDelModelo);
         
        while (it.hasNext()) {
            sb.append("\n").append(it.next().toString());
        }
        
        String result = sb.toString(); 

        return result;

    }

}
