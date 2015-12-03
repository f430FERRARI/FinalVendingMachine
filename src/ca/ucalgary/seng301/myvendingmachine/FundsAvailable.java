package ca.ucalgary.seng301.myvendingmachine;

import java.util.ArrayList;
import java.util.Vector;

import ca.ucalgary.seng301.vendingmachine.Coin;
import ca.ucalgary.seng301.vendingmachine.hardware.AbstractHardware;
import ca.ucalgary.seng301.vendingmachine.hardware.VendingMachine;

public class FundsAvailable extends AbstractHardware<FundsAvailableListener> {

	private static FundsAvailable instance = new FundsAvailable();
	
	private ArrayList<FundsInterface> paymentMethods = new ArrayList<FundsInterface>();
	private Vector<FundsAvailableListener> listeners = new Vector<FundsAvailableListener>();
	//TODO: Register listeners
	
	private FundsAvailable() {} 
	
	public static FundsAvailable getInstance() {
		return instance;
	}
	
	public void removeFunds(int amount) {
		for (FundsInterface paymentMethod : paymentMethods) {
			if (paymentMethod.getFunds() >= amount) {
				paymentMethod.removeFunds(amount);
				break;
			} else {
				amount -= paymentMethod.getFunds();
				paymentMethod.clearFunds();
			}
		} 
		notifyFundsRemoved(amount);
	}

	public void registerPaymentMethod(VendingMachine vm) {
		CoinEntry coinPayments = new CoinEntry(vm);
		paymentMethods.add(coinPayments);
	}

	public int getFunds() {
		int availableFunds = 0;
		for (FundsInterface paymentMethod : paymentMethods) {
			availableFunds += paymentMethod.getFunds();
		}
		return availableFunds;
	} 
	
	public void returnFunds() {
		clearFunds();
		notifyFundsReturned();
	}
	
	public void addFunds(int amount) { 
	} 

	public void clearFunds() {
		for (FundsInterface paymentMethod : paymentMethods) {
			paymentMethod.clearFunds(); 
		} 
	} 
	
	private boolean checkExactChange() {
		return false; 
	}
	
	protected void notifyFundsAdded(int amount) {
		Class<?>[] parameterTypes = new Class<?>[] { int.class };
		Object[] args = new Object[] { amount };
		notifyListeners(FundsAvailableListener.class, "fundsAdded", parameterTypes, args);
	}

	protected void notifyFundsRemoved(int amount) {
		Class<?>[] parameterTypes = new Class<?>[] { int.class };
		Object[] args = new Object[] { amount };
		notifyListeners(FundsAvailableListener.class, "fundsRemoved", parameterTypes, args);
	}

	protected void notifyFundsReturned() {
		Class<?>[] parameterTypes = new Class<?>[] { FundsAvailable.class, Coin.class };
		Object[] args = new Object[] {};
		notifyListeners(FundsAvailableListener.class, "fundsReturned", parameterTypes, args);
	} 


}
