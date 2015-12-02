package ca.ucalgary.seng301.myvendingmachine.test.assn4;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.ucalgary.seng301.myvendingmachine.VendingMachineLogic;
import ca.ucalgary.seng301.vendingmachine.hardware.SimulationException;
import ca.ucalgary.seng301.vendingmachine.hardware.VendingMachine;

public class BadVendingMachineFactoryTest extends WRAPPER {
	private VendingMachine vm;

	@Before
	public void setup() {
		vm = new VendingMachine(new int[] { 5, 10, 25, 100 }, 3, 10, 10, 10);
		new VendingMachineLogic(vm);
	}

	@After
	public void teardown() {
		vm = null;
	}

	// Note: U01 doesn't make much sense anymore. If you didn't construct the
	// VendingMachine instance, the rest will immediately fail, always.

	@Test(expected = SimulationException.class)
	public void testU02Costs() {
		vm.configure(Arrays.asList("Coke", "water", "stuff"), Arrays.asList(250, 250));
	}

	@Test(expected = SimulationException.class)
	public void testU03Names() {
		vm.configure(Arrays.asList("Coke", "water"), Arrays.asList(250, 250));
	}

	@Test(expected = SimulationException.class)
	public void testU04NonUniqueDenomination() {
		new VendingMachine(new int[] { 1, 1 }, 1, 1, 1, 1);
	}

	@Test(expected = SimulationException.class)
	public void testU05CoinKind() {
		new VendingMachine(new int[] { 0 }, 1, 1, 1, 1);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testU06ButtonNumber() {
		vm.getSelectionButton(3).press();
	}

	@Test(expected = SimulationException.class)
	public void testU07EmptyName() {
		vm.configure(Arrays.asList("Coke", "", "stuff"), Arrays.asList(250, 250));
	}
}
