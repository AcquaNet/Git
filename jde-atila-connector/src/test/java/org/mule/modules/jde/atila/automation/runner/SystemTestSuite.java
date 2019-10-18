package org.mule.modules.jde.atila.automation.runner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.mule.modules.jde.atila.automation.system.ConfigConnectTestCases;
import org.mule.modules.jde.atila.automation.system.MetadataTestCases;

@RunWith(Suite.class)
@SuiteClasses({
        ConfigConnectTestCases.class,
        //MetadataTestCases.class
})
public class SystemTestSuite {

}
