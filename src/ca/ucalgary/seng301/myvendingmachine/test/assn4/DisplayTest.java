package ca.ucalgary.seng301.myvendingmachine.test.assn4;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import ca.ucalgary.seng301.myvendingmachine.BusinessLogic;
import ca.ucalgary.seng301.myvendingmachine.NotificationManager;
import ca.ucalgary.seng301.vendingmachine.Coin;
import ca.ucalgary.seng301.vendingmachine.hardware.DisabledException;
import ca.ucalgary.seng301.vendingmachine.hardware.VendingMachine;

public class DisplayTest {

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
	public void testDoNothing() {
		assertEquals("Drink Pop!", vm.getDisplay().read());
	}

	@Test
	public void testDoNothingAndReturn() {
		// press(return)
		vm.getReturnButton().press();
		assertEquals("Drink Pop!", vm.getDisplay().read());
	}

	@Test
	public void testInsertScrambledCoinKinds() throws DisabledException {
		// insert(100)
		// insert(100)
		// insert(25)
		// insert(25)
		// press(0)
		vm.getCoinSlot().addCoin(new Coin(100));
		assertEquals("Total: 100 units", vm.getDisplay().read());

		vm.getCoinSlot().addCoin(new Coin(100));
		assertEquals("Total: 200 units", vm.getDisplay().read());

		vm.getCoinSlot().addCoin(new Coin(25));
		assertEquals("Total: 225 units", vm.getDisplay().read());

		vm.getCoinSlot().addCoin(new Coin(25));
		assertEquals("Total: 250 units", vm.getDisplay().read());

	}

	@Test
	public void testInsertScrambledCoinsAndPress() throws DisabledException {
		// insert(100)
		// insert(100)
		// insert(25)
		// insert(25)
		// press(0)
		vm.getCoinSlot().addCoin(new Coin(100));
		vm.getCoinSlot().addCoin(new Coin(100));
		vm.getCoinSlot().addCoin(new Coin(25));
		vm.getCoinSlot().addCoin(new Coin(25));

		vm.getSelectionButton(0).press();
		assertEquals("Pop and change Dispensed", NotificationManager.getInstance().getLastDisplayMsg());
		assertEquals("Drink Pop!", vm.getDisplay().read()); 
	}

	@Test
	public void testReturnScrambledCoinKinds() throws DisabledException {
		// insert(100)
		// insert(5)
		// insert(25)
		// press(return)
		vm.getCoinSlot().addCoin(new Coin(100));
		vm.getCoinSlot().addCoin(new Coin(5));
		vm.getCoinSlot().addCoin(new Coin(25));
		vm.getReturnButton().press();
		assertEquals("Funds returned", NotificationManager.getInstance().getLastDisplayMsg());
		assertEquals("Drink Pop!", vm.getDisplay().read()); 
	}

	@Test
	public void testInsufficientFundsPress() throws DisabledException {
		// insert(100)
		// press(0)
		vm.getCoinSlot().addCoin(new Coin(100));

		vm.getSelectionButton(0).press(); 
		
		assertEquals("Cost is 250", NotificationManager.getInstance().getLastDisplayMsg());
		assertEquals("Total: 100 units", vm.getDisplay().read()); 

	}
}
