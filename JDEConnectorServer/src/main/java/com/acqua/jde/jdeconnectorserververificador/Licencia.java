/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acqua.jde.jdeconnectorserververificador;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author jgodi
 */
public class Licencia implements Serializable {
    
    private Calendar fechaDeVigencia;

    public Licencia() {
    }
  
    public Licencia(Calendar fechaDeVigencia) {
        this.fechaDeVigencia = fechaDeVigencia;
    }
  
    public Calendar getFechaDeVigencia() {
        return fechaDeVigencia;
    }
    
    public String getFechaDeVigenciaFormateada() {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        return format1.format(this.fechaDeVigencia.getTime());
    }

    public void setFechaDeVigencia(Calendar fechaDeVigencia) {
        this.fechaDeVigencia = fechaDeVigencia;
    }
  
    
}
