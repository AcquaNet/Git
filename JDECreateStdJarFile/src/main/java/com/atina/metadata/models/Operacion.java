package com.atina.metadata.models;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class Operacion {

    private Transaccion Clase; 
    private String metodo;
    private String returnType;
    private Parametros parameters;
    

    public Operacion() { 
        parameters = new Parametros();
    }

    public Transaccion getClase() {
        return Clase;
    }

    public void setClase(Transaccion Clase) {
        this.Clase = Clase;
    }
 
    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public String getReturnType() {
        return returnType;
    }

    public Parametros getParameters() {
        return parameters;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }
    
    
    public Parametro obtenerParametro(String idParametro)
    {
         for (Parametro parametro : parameters.getParameters()) {
             
             if(parametro.getNombre().compareTo(idParametro) == 0)
             {
                 return parametro;
             }
        }
        
        return null;
    }

    @Override
    public String toString() {
        return "Operacion{" + "Clase=" + Clase + ", metodo=" + metodo + ", returnType=" + returnType + ", parameters=" + parameters + '}';
    } 

}
