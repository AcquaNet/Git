/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.cliente.models;

import com.jde.jdeserverwp.servicios.TipoDelParametroInput;
import java.util.HashMap;

/**
 *
 * @author jgodi
 */
public class ParametroInput {

    private TipoDelParametroInput parametro;
    private HashMap<String, ParametroInput> listaSubParametros;

    public ParametroInput() {

    }

    public TipoDelParametroInput getParametro() {
        return parametro;
    }

    public void setParametro(TipoDelParametroInput parametro) {
        this.parametro = parametro;
    }

    public HashMap<String, ParametroInput> getListaSubParametros() {
        return listaSubParametros;
    }

    public void setListaSubParametros(HashMap<String, ParametroInput> listaSubParametros) {
        this.listaSubParametros = listaSubParametros;
    }

}
