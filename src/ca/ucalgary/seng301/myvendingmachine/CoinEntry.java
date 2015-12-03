package ca.ucalgary.seng301.myvendingmachine;

import ca.ucalgary.seng301.vendingmachine.Coin;
import ca.ucalgary.seng301.vendingmachine.hardware.AbstractHardware;
import ca.ucalgary.seng301.vendingmachine.hardware.AbstractHardwareListener;
import ca.ucalgary.seng301.vendingmachine.hardware.CapacityExceededException;
import ca.ucalgary.seng301.vendingmachine.hardware.CoinReceptacle;
import ca.ucalgary.seng301.vendingmachine.hardware.CoinReceptacleListener;
import ca.ucalgary.seng301.vendingmachine.hardware.CoinSlot;
import ca.ucalgary.seng301.vendingmachine.hardware.CoinSlotListener;
import ca.ucalgary.seng301.vendingmachine.hardware.DisabledException;
import ca.ucalgary.seng301.vendingmachine.hardware.SimulationException;
import ca.ucalgary.seng301.vendingmachine.hardware.VendingMachine;

//MAY be missing methods, look at diagram  
//Diagram says this class is supposed to be private 
public class CoinEntry implements CoinSlotListener, CoinReceptacleListener, FundsInterface {

	private int cashFunds;
	private VendingMachine vendingMachine;
	
	public CoinEntry(VendingMachine vm) {
		vendingMachine = vm;
		vm.getCoinSlot().register(this);
	}

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enabled(AbstractHardware<AbstractHardwareListener> hardware) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disabled(AbstractHardware<AbstractHardwareListener> hardware) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void coinAdded(CoinReceptacle receptacle, Coin coin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void coinsFull(CoinReceptacle receptacle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void coinsRemoved(CoinReceptacle receptacle) {
	}

}
