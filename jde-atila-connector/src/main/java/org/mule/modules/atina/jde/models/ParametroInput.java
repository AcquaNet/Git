package org.mule.modules.atina.jde.models;

import java.util.HashMap;

import com.jde.jdeserverwp.servicios.TipoDelParametroInput;

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
