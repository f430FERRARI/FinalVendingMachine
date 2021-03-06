package ca.ucalgary.seng301.myvendingmachine.test.assn4;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import ca.ucalgary.seng301.myvendingmachine.BusinessLogic;
import ca.ucalgary.seng301.vendingmachine.hardware.VendingMachine;

public class NoConfigureOrLoadVendingMachineFactoryTests {
	private VendingMachine vm;

	@Before
	public void setup() {
		// construct(5, 10, 25, 100; 3; 10; 10; 10)
		vm = new VendingMachine(new int[] { 5, 10, 25, 100 }, 3, 10, 10, 10);
		new BusinessLogic(vm);
	}

	@Test
	public void testT03TeardownWithoutConfigureOrLoad() {
		// extract()
		// CHECK_DELIVERY(0)
		// unload()
		// CHECK_TEARDOWN(0; 0)
		assertEquals(Arrays.asList(0), Utilities.extractAndSortFromDeliveryChute(vm));
		assertEquals(0, Utilities.extractAndSumFromCoinRacks(vm));
		assertEquals(0, Utilities.extractAndSumFromStorageBin(vm));
		assertEquals(Arrays.asList(), Utilities.extractAndSortFromProductRacks(vm));
	}
}
