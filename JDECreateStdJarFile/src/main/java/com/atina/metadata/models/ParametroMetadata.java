/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.metadata.models;

import java.util.HashMap;
import org.mule.common.metadata.datatype.DataType;
 
/**
 *
 * @author jgodi
 */
public class ParametroMetadata {
     
    String tipoDelParametro;
    DataType tipoDelParametroMule;
    String secuencia;
    HashMap<String, ParametroMetadata> parametros; 

    public DataType getTipoDelParametroMule() {
        return tipoDelParametroMule;
    }

    public void setTipoDelParametroMule(DataType tipoDelParametroMule) {
        this.tipoDelParametroMule = tipoDelParametroMule;
    }
 
    public ParametroMetadata() { 
        this.parametros = new HashMap<>();
    }
 
    public String getTipoDelParametro() {
        return tipoDelParametro;
    }

    public void setTipoDelParametro(String tipoDelParametro) {
        this.tipoDelParametro = tipoDelParametro;
    }

    public String getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(String secuencia) {
        this.secuencia = secuencia;
    }

    public HashMap<String, ParametroMetadata> getParametros() {
        return parametros;
    }

    public void setParametros(HashMap<String, ParametroMetadata> parametros) {
        this.parametros = parametros;
    }
    
    
    
    
    
}
