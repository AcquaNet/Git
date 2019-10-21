package org.mule.modules.jde.atina.automation.functional;

import java.util.ArrayList;
import java.util.List;

import org.mule.modules.atina.jde.JDEAtinaConnector;
import org.mule.modules.atina.jde.datasense.WSDataSenseResolver;

public class ServicioMetaDataTestCases extends AbstractMetaDataTestCases {

    private static final List<String> keys = new ArrayList<String>();

    static {
        keys.add("oracle.e1.bssv.JP430000.ProcurementManager.getPurchaseOrdersForApprover");

    }

    public ServicioMetaDataTestCases() {
        super(keys, WSDataSenseResolver.class, JDEAtinaConnector.class);

    }

}