package com.atina.metadata.models;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class Operacion {

    private String modelPackage;
    private String Clase; 
    private String metodo;
    private String returnType;
    private Parametros parameters;
    

    public Operacion() { 
        parameters = new Parametros();
    }

    public String getClase() {
        return Clase;
    }

    public void setClase(String Clase) {
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

    public String getModelPackage() {
        return modelPackage;
    }

    public void setModelPackage(String modelPackage) {
        this.modelPackage = modelPackage;
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
        return "Operacion{" + "Clase=" + modelPackage + "." + Clase + ", metodo=" + metodo + ", returnType=" + returnType + ", parameters=" + parameters + '}';
    } 

}
