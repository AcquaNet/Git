package org.mule.modules.jde.atila.automation.functional;

import java.util.List;

import org.mule.modules.atila.jde.JDEAtinaConnector;
import org.mule.tools.devkit.ctf.junit.AbstractMetaDataTestCase;

public class AbstractMetaDataTestCases extends AbstractMetaDataTestCase<JDEAtinaConnector> {

    public AbstractMetaDataTestCases(List<String> metadataIds, Class<?> categoryClass, Class<JDEAtinaConnector> connectorClass) {
        super(metadataIds, categoryClass, connectorClass);
    }

}