package org.mule.modules.jde.atila.automation.functional;

import java.util.List;

import org.mule.modules.atila.jde.JDEAtilaConnector;
import org.mule.tools.devkit.ctf.junit.AbstractMetaDataTestCase;

public class AbstractMetaDataTestCases extends AbstractMetaDataTestCase<JDEAtilaConnector> {

    public AbstractMetaDataTestCases(List<String> metadataIds, Class<?> categoryClass, Class<JDEAtilaConnector> connectorClass) {
        super(metadataIds, categoryClass, connectorClass);
    }

}