package org.mule.modules.dragonfish.automation.functional;

import java.util.List;

import org.mule.modules.atila.jde.DragonfishConnector;
import org.mule.tools.devkit.ctf.junit.AbstractMetaDataTestCase;

public class AbstractMetaDataTestCases extends AbstractMetaDataTestCase<DragonfishConnector> {

    public AbstractMetaDataTestCases(List<String> metadataIds, Class<?> categoryClass, Class<DragonfishConnector> connectorClass) {
        super(metadataIds, categoryClass, connectorClass);
    }

}