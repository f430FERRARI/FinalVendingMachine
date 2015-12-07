package ca.ucalgary.seng301.myvendingmachine.test.assn4;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import ca.ucalgary.seng301.myvendingmachine.BusinessLogic;
import ca.ucalgary.seng301.vendingmachine.Coin;
import ca.ucalgary.seng301.vendingmachine.hardware.DisabledException;
import ca.ucalgary.seng301.vendingmachine.hardware.VendingMachine;

public class ReturnButtonTest {
	private VendingMachine vm;

	@Before
	public void setup() {
		// construct(5, 10, 25, 100; 3; 10; 10; 10)
		// configure("Coke", "water", "stuff"; 250, 250, 205)
		// load(1, 1, 2, 0; 1, 1, 1)
		vm = new VendingMachine(new int[] { 5, 10, 25, 100 }, 3, 10, 10, 10);
		new BusinessLogic(vm);

		vm.configure(Arrays.asList(new String[] { "Coke", "water", "stuff" }),
				Arrays.asList(new Integer[] { 250, 250, 205 }));
		Utilities.loadCoins(vm, 1, 1, 2, 0);
		Utilities.loadProducts(vm, 1, 1, 1);
	}

	@Test
	public void testT20NoCoinsInsertedAndReturn() {
		// press(return)
		// extract()
		// CHECK_DELIVERY(0)
		// unload()
		// CHECK_TEARDOWN(65; 0; "Coke", "water", "stuff")
		vm.getReturnButton().press();
		assertEquals(Arrays.asList(0), Utilities.extractAndSortFromDeliveryChute(vm));
		assertEquals(65, Utilities.extractAndSumFromCoinRacks(vm));
		assertEquals(0, Utilities.extractAndSumFromStorageBin(vm));
		assertEquals(Arrays.asList("Coke", "stuff", "water"), Utilities.extractAndSortFromProductRacks(vm));
	}

	@Test
	public void testInsertScrambledCoinKindsAndReturn() throws DisabledException {
		// extract()
		// CHECK_DELIVERY(0)
		// insert(100)
		// insert(5)
		// insert(25) 
		// press(return)
		// extract()
		// CHECK_DELIVERY(0)
		// unload()
		// CHECK_TEARDOWN(65; 0; "Coke", "water", "stuff")

		assertEquals(Arrays.asList(0), Utilities.extractAndSortFromDeliveryChute(vm));
		vm.getCoinSlot().addCoin(new Coin(100));
		vm.getCoinSlot().addCoin(new Coin(5));
		vm.getCoinSlot().addCoin(new Coin(25)); 
		vm.getReturnButton().press();

		assertEquals(Arrays.asList(130), Utilities.extractAndSortFromDeliveryChute(vm));
		assertEquals(65, Utilities.extractAndSumFromCoinRacks(vm));
		assertEquals(0, Utilities.extractAndSumFromStorageBin(vm));
		assertEquals(Arrays.asList("Coke", "stuff", "water"), Utilities.extractAndSortFromProductRacks(vm));
	}
	@Test
	public void testInsertScrambledCoinKindsAndReturnTwice() throws DisabledException {
		// extract()
		// CHECK_DELIVERY(0)
		// insert(100)
		// insert(5)
		// insert(25) 
		// press(return)
		// extract()
		// CHECK_DELIVERY(0)
		// unload()
		// CHECK_TEARDOWN(65; 0; "Coke", "water", "stuff")

		assertEquals(Arrays.asList(0), Utilities.extractAndSortFromDeliveryChute(vm));
		vm.getCoinSlot().addCoin(new Coin(100));
		vm.getCoinSlot().addCoin(new Coin(5));
		vm.getCoinSlot().addCoin(new Coin(25)); 
		vm.getReturnButton().press();

		assertEquals(Arrays.asList(130), Utilities.extractAndSortFromDeliveryChute(vm));
		assertEquals(65, Utilities.extractAndSumFromCoinRacks(vm));
		assertEquals(0, Utilities.extractAndSumFromStorageBin(vm));
		assertEquals(Arrays.asList("Coke", "stuff", "water"), Utilities.extractAndSortFromProductRacks(vm)); 
		
		vm.getReturnButton().press(); 

		assertEquals(Arrays.asList(0), Utilities.extractAndSortFromDeliveryChute(vm));
		assertEquals(0, Utilities.extractAndSumFromCoinRacks(vm));
		assertEquals(0, Utilities.extractAndSumFromStorageBin(vm));
		assertEquals(Arrays.asList(), Utilities.extractAndSortFromProductRacks(vm)); 
		
	}
	
}
