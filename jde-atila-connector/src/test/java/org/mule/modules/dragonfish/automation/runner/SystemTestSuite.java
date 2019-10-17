package org.mule.modules.dragonfish.automation.runner;

import org.mule.modules.dragonfish.automation.system.ConfigConnectTestCases;
import org.mule.modules.dragonfish.automation.system.MetadataTestCases;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        // ConfigConnectTestCases.class,
        MetadataTestCases.class
})
public class SystemTestSuite {

}
