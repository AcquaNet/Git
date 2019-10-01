/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.metadata.models;

import java.util.Map;

/**
 *
 * @author jgodi
 */
public class ParametrosMetadata {
    
    private Map<String,ParametroMetadata> listaDeParametros;

    public ParametrosMetadata() {
    }

    public ParametrosMetadata(Map<String, ParametroMetadata> listaDeParametros) {
        this.listaDeParametros = listaDeParametros;
    }

    public Map<String, ParametroMetadata> getListaDeParametros() {
        return listaDeParametros;
    }

    public void setListaDeParametros(Map<String, ParametroMetadata> listaDeParametros) {
        this.listaDeParametros = listaDeParametros;
    }
      
}
