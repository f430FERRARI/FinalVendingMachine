package ca.ucalgary.seng301.myvendingmachine.test.assn4;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import ca.ucalgary.seng301.myvendingmachine.BusinessLogic;
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
		
	} 
	
	@Test 
	public void testInsertScrambledCoinKindsAndReturn() { 
		
	} 
	
	@Test 
	public void testOverloadCoinReceptableAndReturn() { 
		
	}
}
