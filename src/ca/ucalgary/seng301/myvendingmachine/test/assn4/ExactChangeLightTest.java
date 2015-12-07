package ca.ucalgary.seng301.myvendingmachine.test.assn4;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import ca.ucalgary.seng301.myvendingmachine.BusinessLogic;
import ca.ucalgary.seng301.vendingmachine.hardware.VendingMachine;

public class ExactChangeLightTest {

	private VendingMachine vm;

	@Test
	public void testEnoughCoins() {
		// construct(5, 10, 25, 100; 3; 10; 10; 10)
		// configure("Coke", "water", "stuff"; 250, 250, 205)
		// load(1, 1, 2, 0; 1, 1, 1)
		vm = new VendingMachine(new int[] { 5, 10, 25, 100 }, 3, 10, 10, 10);
		vm.configure(Arrays.asList(new String[] { "Coke", "water", "stuff" }),
				Arrays.asList(new Integer[] { 250, 250, 205 }));
		Utilities.loadCoins(vm, 2, 2, 3, 0);
		Utilities.loadProducts(vm, 1, 1, 1); 
		new BusinessLogic(vm);
		
		//Expected: Should not off
		assertEquals(false, vm.getExactChangeLight().isActive());
	}

	
	@Test
	public void testSomeCoinsNeedExactChange() {
		// construct(5, 10, 25, 100; 3; 10; 10; 10)
		// configure("Coke", "water", "stuff"; 250, 250, 205)
		// load(1, 1, 2, 0; 1, 1, 1)
		vm = new VendingMachine(new int[] { 5, 10, 25, 100 }, 3, 10, 10, 10);
		vm.configure(Arrays.asList(new String[] { "Coke", "water", "stuff" }),
				Arrays.asList(new Integer[] { 250, 250, 205 }));
		Utilities.loadCoins(vm, 1, 1, 0, 0);
		Utilities.loadProducts(vm, 1, 1, 1);
		new BusinessLogic(vm);

		//Expected: Should be on
		assertEquals(true, vm.getExactChangeLight().isActive());
	
	}

	@Test
	public void testEmptyCoinRack() {
		// construct(5, 10, 25, 100; 3; 10; 10; 10)
		// configure("Coke", "water", "stuff"; 250, 250, 205)
		// load(1, 1, 2, 0; 1, 1, 1)
		vm = new VendingMachine(new int[] { 5, 10, 25, 100 }, 3, 10, 10, 10);
		vm.configure(Arrays.asList(new String[] { "Coke", "water", "stuff" }),
				Arrays.asList(new Integer[] { 250, 250, 205 }));
		Utilities.loadCoins(vm, 0, 0, 0, 0);
		Utilities.loadProducts(vm, 1, 1, 1); 
		new BusinessLogic(vm);

		//Expected: Should be on
		assertEquals(true, vm.getExactChangeLight().isActive());
	}
}
