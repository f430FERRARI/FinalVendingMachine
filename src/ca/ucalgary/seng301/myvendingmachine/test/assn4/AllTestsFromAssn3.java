package ca.ucalgary.seng301.myvendingmachine.test.assn4;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BadVendingMachineFactoryTest.class, ChangingConfigurationVendingMachineTests.class,
		NoConfigureOrLoadVendingMachineFactoryTests.class, ScrambledCoinKindsVendingMachineFactoryTests.class,
		ScrambledCoinKindsVendingMachineFactoryTests2.class, SmallSetupVendingMachineFactoryTest1.class,
		SmallSetupVendingMachineFactoryTest2.class, SmallSetupVendingMachineFactoryTest3.class,
		StandardSetupVendingMachineFactoryTests.class })
public class AllTestsFromAssn3 {

}
