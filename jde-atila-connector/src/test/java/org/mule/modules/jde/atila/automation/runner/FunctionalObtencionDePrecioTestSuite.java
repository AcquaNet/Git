package org.mule.modules.jde.atila.automation.runner;

import org.mule.modules.atila.jde.JDEAtilaConnector;
import org.mule.modules.jde.atila.automation.functional.EjecutarOperacionObtencionDePrecioTestCases;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.mule.tools.devkit.ctf.mockup.ConnectorTestContext;

@RunWith(Suite.class)
@SuiteClasses({
        EjecutarOperacionObtencionDePrecioTestCases.class
})
public class FunctionalObtencionDePrecioTestSuite {

    @BeforeClass
    public static void initialiseSuite() {

        ConnectorTestContext.initialize(JDEAtilaConnector.class);
    }

    @AfterClass
    public static void shutdownSuite() throws Exception {
        ConnectorTestContext.shutDown();
    }
}
