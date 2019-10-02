package com.atina.metadata.models;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class TipoDelModelo {

    private String tipoDeVariable; 
    private String nombreDeLaVariable; 
    private Boolean javaClass; 
    
    public TipoDelModelo() {
    }

    public String getTipoDeVariable() {
        return tipoDeVariable;
    }

    public void setTipoDeVariable(String tipoDeVariable) {
        this.tipoDeVariable = tipoDeVariable;
    }
 
    public String getNombreDeLaVariable() {
        return nombreDeLaVariable;
    }

    public void setNombreDeLaVariable(String nombreDeLaVariable) {
        this.nombreDeLaVariable = nombreDeLaVariable;
    }

    public Boolean getJavaClass() {
        return javaClass;
    }

    public void setJavaClass(Boolean javaClass) {
        this.javaClass = javaClass;
    }
      
    @Override
    public String toString() {
        return "TipoDelModelo{" + "tipoDeVariable=" + tipoDeVariable + ", nombreDeLaVariable=" + nombreDeLaVariable + '}';
    }
   
}
