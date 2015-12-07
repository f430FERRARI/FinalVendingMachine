package ca.ucalgary.seng301.myvendingmachine.test.assn4;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import ca.ucalgary.seng301.myvendingmachine.BusinessLogic;
import ca.ucalgary.seng301.vendingmachine.Coin;
import ca.ucalgary.seng301.vendingmachine.hardware.DisabledException;
import ca.ucalgary.seng301.vendingmachine.hardware.VendingMachine;

public class OutOfOrderLightTest {
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
	public void testDisabledCoinRack() throws DisabledException { 
		assertEquals(false, vm.getOutOfOrderLight().isActive());
		
		vm.getCoinRack(3).disable(); 
		vm.getCoinSlot().addCoin(new Coin(100));
		vm.getCoinSlot().addCoin(new Coin(100));
		vm.getCoinSlot().addCoin(new Coin(100));

		vm.getSelectionButton(0).press(); 
		
		assertEquals(true, vm.getOutOfOrderLight().isActive());
	} 
	
	@Test 
	public void testDisabledProductRack() throws DisabledException { 
		assertEquals(false, vm.getOutOfOrderLight().isActive());

		vm.getProductRack(0).disable();  
		
		vm.getCoinSlot().addCoin(new Coin(100));
		vm.getCoinSlot().addCoin(new Coin(100));
		vm.getCoinSlot().addCoin(new Coin(100));

		vm.getSelectionButton(0).press(); 
		
		assertEquals(true, vm.getOutOfOrderLight().isActive());
	}
	
	//TODO: How about empty coin rack?
	
	@Test 
	public void testInsertIntoFullCoinRack() throws DisabledException { 
		assertEquals(false, vm.getOutOfOrderLight().isActive());

		vm.getCoinSlot().addCoin(new Coin(100));
		vm.getCoinSlot().addCoin(new Coin(100));
		vm.getCoinSlot().addCoin(new Coin(100));
		vm.getCoinSlot().addCoin(new Coin(100));
		vm.getCoinSlot().addCoin(new Coin(100));
		vm.getCoinSlot().addCoin(new Coin(100));
		vm.getCoinSlot().addCoin(new Coin(100));
		vm.getCoinSlot().addCoin(new Coin(100));
		vm.getCoinSlot().addCoin(new Coin(100));
		vm.getCoinSlot().addCoin(new Coin(100));
		vm.getCoinSlot().addCoin(new Coin(100));
		vm.getCoinSlot().addCoin(new Coin(100));

		vm.getSelectionButton(0).press();  
		
		assertEquals(true, vm.getOutOfOrderLight().isActive());
	} 
	
	@Test 
	public void testEmptyProductRack() throws DisabledException { 
		assertEquals(false, vm.getOutOfOrderLight().isActive());

		Utilities.extractAndSortFromProductRacks(vm); 
		
		vm.getCoinSlot().addCoin(new Coin(100));
		vm.getCoinSlot().addCoin(new Coin(100)); 
		vm.getCoinSlot().addCoin(new Coin(100));
		vm.getCoinSlot().addCoin(new Coin(10));
		vm.getCoinSlot().addCoin(new Coin(10));
		vm.getCoinSlot().addCoin(new Coin(25));

		
		vm.getSelectionButton(0).press();  
		
		assertEquals(true, vm.getOutOfOrderLight().isActive());
	}
	
}
