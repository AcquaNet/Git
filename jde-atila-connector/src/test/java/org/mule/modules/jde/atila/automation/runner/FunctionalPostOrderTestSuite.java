package org.mule.modules.jde.atila.automation.runner;

import org.mule.modules.atila.jde.JDEAtilaConnector;
import org.mule.modules.jde.atila.automation.functional.EjecutarOperacionPostOrderTestCases;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.mule.tools.devkit.ctf.mockup.ConnectorTestContext;

@RunWith(Suite.class)
@SuiteClasses({
        EjecutarOperacionPostOrderTestCases.class
})
public class FunctionalPostOrderTestSuite {

    @BeforeClass
    public static void initialiseSuite() {

        ConnectorTestContext.initialize(JDEAtilaConnector.class);
    }

    @AfterClass
    public static void shutdownSuite() throws Exception {
        ConnectorTestContext.shutDown();
    }
}
