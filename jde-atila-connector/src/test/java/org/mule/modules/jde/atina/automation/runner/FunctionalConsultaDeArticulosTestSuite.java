package org.mule.modules.jde.atina.automation.runner;

import org.mule.modules.atina.jde.JDEAtinaConnector;
import org.mule.modules.jde.atina.automation.functional.CreateOrderTestCases;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.mule.tools.devkit.ctf.mockup.ConnectorTestContext;

@RunWith(Suite.class)
@SuiteClasses({
        CreateOrderTestCases.class
})
public class FunctionalConsultaDeArticulosTestSuite {

    @BeforeClass
    public static void initialiseSuite() {

        ConnectorTestContext.initialize(JDEAtinaConnector.class);
    }

    @AfterClass
    public static void shutdownSuite() throws Exception {
        ConnectorTestContext.shutDown();
    }
}
