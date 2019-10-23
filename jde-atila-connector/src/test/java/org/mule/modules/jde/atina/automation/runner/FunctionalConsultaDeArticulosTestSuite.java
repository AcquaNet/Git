package org.mule.modules.jde.atina.automation.runner;

import org.mule.modules.atina.jde.JDEAtinaConnector;
import org.mule.modules.jde.atina.automation.functional.GetItemPriceAndAvailabilityTestCases;
import org.mule.modules.jde.atina.automation.functional.GetItemPriceTestCases;
import org.mule.modules.jde.atina.automation.functional.GetPurchaseOrdersForApproverTestCases;
import org.mule.modules.jde.atina.automation.functional.WriteOffProcessingOptionsTestCases;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.mule.tools.devkit.ctf.mockup.ConnectorTestContext;

@RunWith(Suite.class)
@SuiteClasses({
        //GetPurchaseOrdersForApproverTestCases.class,
        //GetItemPriceTestCases.class
        //WriteOffProcessingOptionsTestCases.class,
        GetItemPriceAndAvailabilityTestCases.class
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
