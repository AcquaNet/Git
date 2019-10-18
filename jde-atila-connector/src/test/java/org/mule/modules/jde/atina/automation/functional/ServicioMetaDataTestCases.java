package org.mule.modules.jde.atina.automation.functional;

import java.util.ArrayList;
import java.util.List;

import org.mule.modules.atina.jde.JDEAtinaConnector;
import org.mule.modules.atina.jde.datasense.ServicioDataSenseResolver;

public class ServicioMetaDataTestCases extends AbstractMetaDataTestCases {

    private static final List<String> keys = new ArrayList<String>();

    static {
        keys.add("ArticuloById_articuloByIdGet");

    }

    public ServicioMetaDataTestCases() {
        super(keys, ServicioDataSenseResolver.class, JDEAtinaConnector.class);

    }

}