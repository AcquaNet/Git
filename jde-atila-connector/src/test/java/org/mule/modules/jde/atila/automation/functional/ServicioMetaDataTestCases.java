package org.mule.modules.jde.atila.automation.functional;

import java.util.ArrayList;
import java.util.List;

import org.mule.modules.atila.jde.JDEAtinaConnector;
import org.mule.modules.atila.jde.datasense.ServicioDataSenseResolver;

public class ServicioMetaDataTestCases extends AbstractMetaDataTestCases {

    private static final List<String> keys = new ArrayList<String>();

    static {
        keys.add("ArticuloById_articuloByIdGet");

    }

    public ServicioMetaDataTestCases() {
        super(keys, ServicioDataSenseResolver.class, JDEAtinaConnector.class);

    }

}