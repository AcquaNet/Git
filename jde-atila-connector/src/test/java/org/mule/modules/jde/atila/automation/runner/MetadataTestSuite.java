package org.mule.modules.jde.atila.automation.runner;

import org.mule.modules.atila.jde.JDEAtinaConnector;
import org.mule.modules.jde.atila.automation.functional.ServicioMetaDataTestCases;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.mule.tools.devkit.ctf.mockup.ConnectorTestContext;

@RunWith(Suite.class)
@SuiteClasses({
        ServicioMetaDataTestCases.class
})
public class MetadataTestSuite {

    @BeforeClass
    public static void initialiseSuite() {

        ConnectorTestContext.initialize(JDEAtinaConnector.class);
    }

    @AfterClass
    public static void shutdownSuite() throws Exception {
        ConnectorTestContext.shutDown();
    }
}
