package org.mule.modules.dragonfish.automation.functional;

import java.util.ArrayList;
import java.util.List;

import org.mule.modules.atila.jde.DragonfishConnector;
import org.mule.modules.atila.jde.datasense.ServicioDataSenseResolver;

public class ServicioMetaDataTestCases extends AbstractMetaDataTestCases {

    private static final List<String> keys = new ArrayList<String>();

    static {
        keys.add("ArticuloById_articuloByIdGet");

    }

    public ServicioMetaDataTestCases() {
        super(keys, ServicioDataSenseResolver.class, DragonfishConnector.class);

    }

}