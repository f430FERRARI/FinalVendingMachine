package ca.ucalgary.seng301.myvendingmachine.test.assn4;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import ca.ucalgary.seng301.myvendingmachine.BusinessLogic;
import ca.ucalgary.seng301.vendingmachine.Coin;
import ca.ucalgary.seng301.vendingmachine.hardware.DisabledException;
import ca.ucalgary.seng301.vendingmachine.hardware.VendingMachine;

public class SmallSetupVendingMachineFactoryTest1 {
	private VendingMachine vm;

	@Before
	public void setup() {
		// construct(5, 10, 25, 100; 1; 10; 10; 10)
		// configure("stuff"; 140)
		// load(0, 5, 1, 1; 1)

		vm = new VendingMachine(new int[] { 5, 10, 25, 100 }, 1, 10, 10, 10);
		new BusinessLogic(vm);

		vm.configure(Arrays.asList("stuff"), Arrays.asList(140));
		Utilities.loadCoins(vm, 0, 5, 1, 1);
		Utilities.loadProducts(vm, 1);
	}

	@Test
	public void testT08ApproximateChange() throws DisabledException {
		// insert(100)
		// insert(100)
		// insert(100)
		// press(0)
		// extract()
		// CHECK_DELIVERY(155, "stuff")
		// unload()
		// CHECK_TEARDOWN(320; 0)
		vm.getCoinSlot().addCoin(new Coin(100));
		vm.getCoinSlot().addCoin(new Coin(100));
		vm.getCoinSlot().addCoin(new Coin(100));

		vm.getSelectionButton(0).press();
		assertEquals(Arrays.asList(155, "stuff"), Utilities.extractAndSortFromDeliveryChute(vm));
		assertEquals(320, Utilities.extractAndSumFromCoinRacks(vm));
		assertEquals(0, Utilities.extractAndSumFromStorageBin(vm));
		assertEquals(Arrays.asList(), Utilities.extractAndSortFromProductRacks(vm));
	}

	@Test
	public void testT12ApproximateChangeWithCredit() throws DisabledException {
		// insert(100)
		// insert(100)
		// insert(100)
		// press(0)
		// extract()
		// CHECK_DELIVERY(155, "stuff")
		// unload()
		// CHECK_TEARDOWN(320; 0)
		// load(10, 10, 10, 10; 1)
		// insert(25)
		// insert(100)
		// insert(10)
		// press(0)
		// extract()
		// CHECK_DELIVERY(0, "stuff")
		// unload()
		// CHECK_TEARDOWN(1400; 135)

		vm.getCoinSlot().addCoin(new Coin(100));
		vm.getCoinSlot().addCoin(new Coin(100));
		vm.getCoinSlot().addCoin(new Coin(100));
		vm.getSelectionButton(0).press();
		assertEquals(Arrays.asList(155, "stuff"), Utilities.extractAndSortFromDeliveryChute(vm));
		assertEquals(320, Utilities.extractAndSumFromCoinRacks(vm));
		assertEquals(0, Utilities.extractAndSumFromStorageBin(vm));
		assertEquals(Arrays.asList(), Utilities.extractAndSortFromProductRacks(vm));

		Utilities.loadCoins(vm, 10, 10, 10, 10);
		Utilities.loadProducts(vm, 1);

		vm.getCoinSlot().addCoin(new Coin(25));
		vm.getCoinSlot().addCoin(new Coin(100));
		vm.getCoinSlot().addCoin(new Coin(10));
		vm.getSelectionButton(0).press();
		assertEquals(Arrays.asList(0, "stuff"), Utilities.extractAndSortFromDeliveryChute(vm));
		assertEquals(1400, Utilities.extractAndSumFromCoinRacks(vm));
		assertEquals(135, Utilities.extractAndSumFromStorageBin(vm));
		assertEquals(Arrays.asList(), Utilities.extractAndSortFromProductRacks(vm));
	}
}
