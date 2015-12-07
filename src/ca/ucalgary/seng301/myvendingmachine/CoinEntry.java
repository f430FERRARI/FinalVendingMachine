package ca.ucalgary.seng301.myvendingmachine;

import java.util.Map;
import java.util.TreeMap;

import ca.ucalgary.seng301.vendingmachine.Coin;
import ca.ucalgary.seng301.vendingmachine.hardware.AbstractHardware;
import ca.ucalgary.seng301.vendingmachine.hardware.AbstractHardwareListener;
import ca.ucalgary.seng301.vendingmachine.hardware.CapacityExceededException;
import ca.ucalgary.seng301.vendingmachine.hardware.CoinSlot;
import ca.ucalgary.seng301.vendingmachine.hardware.CoinSlotListener;
import ca.ucalgary.seng301.vendingmachine.hardware.DisabledException;
import ca.ucalgary.seng301.vendingmachine.hardware.SimulationException;
import ca.ucalgary.seng301.vendingmachine.hardware.VendingMachine;

public class CoinEntry implements CoinSlotListener, FundsInterface {

	private int cashFunds;
	private VendingMachine vendingMachine;
	
	public CoinEntry(VendingMachine vm) {
		vendingMachine = vm;
		vm.getCoinSlot().register(this);	 
		FundsAvailable.getInstance().notifyExactChange(checkExactChange());
	}

	public boolean checkExactChange() { 
		Map<Integer, Integer> coinQuantities = new TreeMap<>();

		// Get coin racks and the amount of coins stored in each
		for (int i = 0; i < vendingMachine.getNumberOfCoinRacks(); i++) {
			int value = vendingMachine.getCoinKindForRack(i);  
			coinQuantities.put(value, vendingMachine.getCoinRack(i).size()); 
		}  
		
		// Check if the balance in each coin rack can guarantee exact change
		for (int i=0; i < vendingMachine.getNumberOfCoinRacks()-1; i++) {
		    int outerKey = vendingMachine.getCoinKindForRack(i+1);
			int sum = 0;
			for (int j = i; j>= 0; j--) { 
		    	int innerKey = vendingMachine.getCoinKindForRack(j);  
		    	System.out.println(coinQuantities.get(innerKey));
		    	sum += coinQuantities.get(innerKey) * innerKey; 
		    }
		    if (sum < outerKey) { 
		    	return true;
		    }
		}
		return false;
	}
	
	// Respond to a coin being inserted into a coin slot
	@Override
	public void validCoinInserted(CoinSlot coinSlotSimulator, Coin coin) {
		addFunds(coin.getValue());
	}

	@Override
	public int getFunds() {
		return cashFunds;
	}

	@Override
	public void addFunds(int amount) {
		cashFunds += amount;
		FundsAvailable.getInstance().notifyFundsAdded(amount);
	}

	@Override
	public void removeFunds(int amount) {
		cashFunds -= amount;
		FundsAvailable.getInstance().notifyFundsRemoved(amount);
	}

	// Clear funds and return coins in coin receptacle
	@Override
	public void returnFunds() {
		clearFunds();
		try {
			vendingMachine.getCoinReceptacle().returnCoins();
		} catch (CapacityExceededException | DisabledException e) {
			throw new SimulationException(e);
		}
	};

	@Override
	public void clearFunds() {
		cashFunds = 0; 
		FundsAvailable.getInstance().notifyFundsRemoved(cashFunds);
	}

	@Override
	public void coinRejected(CoinSlot coinSlotSimulator, Coin coin) {
	}

	@Override
	public void enabled(AbstractHardware<AbstractHardwareListener> hardware) {
	}

	@Override
	public void disabled(AbstractHardware<AbstractHardwareListener> hardware) {
	}

}
