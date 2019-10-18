package org.mule.modules.jde.atina.automation.functional;

import java.util.List;

import org.mule.modules.atina.jde.JDEAtinaConnector;
import org.mule.tools.devkit.ctf.junit.AbstractMetaDataTestCase;

public class AbstractMetaDataTestCases extends AbstractMetaDataTestCase<JDEAtinaConnector> {

    public AbstractMetaDataTestCases(List<String> metadataIds, Class<?> categoryClass, Class<JDEAtinaConnector> connectorClass) {
        super(metadataIds, categoryClass, connectorClass);
    }

}