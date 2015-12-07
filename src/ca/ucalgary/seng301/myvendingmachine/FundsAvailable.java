package ca.ucalgary.seng301.myvendingmachine;

import java.util.ArrayList;
import java.util.Vector;

import ca.ucalgary.seng301.vendingmachine.hardware.AbstractHardware;
import ca.ucalgary.seng301.vendingmachine.hardware.VendingMachine;

public class FundsAvailable extends AbstractHardware<FundsAvailableListener> {

	private static FundsAvailable instance = new FundsAvailable();
	
	private ArrayList<FundsInterface> paymentMethods = new ArrayList<FundsInterface>();
	private Vector<FundsAvailableListener> listeners = new Vector<FundsAvailableListener>();
	
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

	// Reset FundsAvailable at start of test by resetting all variables
	public void reset() {
		paymentMethods.clear(); 
		listeners.clear();
	}
	
	// Used to register different payment methods
	public void registerPaymentMethod(VendingMachine vm) {
		CoinEntry coinPayments = new CoinEntry(vm);
		paymentMethods.add(coinPayments);
	}

	// Get funds from all payment methods
	public int getFunds() {
		int availableFunds = 0;
		for (FundsInterface paymentMethod : paymentMethods) {
			availableFunds += paymentMethod.getFunds();
		}
		return availableFunds;
	} 
	
	// Instruct all payment methods to return their funds
	public void returnFunds() {
		for (FundsInterface paymentMethod : paymentMethods) {
			paymentMethod.returnFunds(); 
		} 
		notifyFundsReturned();
	}

	// Instruct all payment methods to clear their funds balance
	public void clearFunds() {
		for (FundsInterface paymentMethod : paymentMethods) {
			paymentMethod.clearFunds(); 
		} 
	}  
	
	// Check coin racks to see if exact change is needed
	public void checkExactChange() {
		boolean isExactChange = paymentMethods.get(0).checkExactChange(); 
		notifyExactChange(isExactChange);
	}
	
	void notifyFundsAdded(int amount) {
		Class<?>[] parameterTypes = new Class<?>[] { int.class };
		Object[] args = new Object[] { amount };
		notifyListeners(FundsAvailableListener.class, "fundsAdded", parameterTypes, args);
	}

	void notifyFundsRemoved(int amount) {
		Class<?>[] parameterTypes = new Class<?>[] { int.class };
		Object[] args = new Object[] { amount };
		notifyListeners(FundsAvailableListener.class, "fundsRemoved", parameterTypes, args);
	}

	void notifyFundsReturned() {
		Class<?>[] parameterTypes = new Class<?>[] {};
		Object[] args = new Object[] {};
		notifyListeners(FundsAvailableListener.class, "fundsReturned", parameterTypes, args);
	} 
	
	void notifyExactChange(boolean isExact) { 
		Class<?>[] parameterTypes = new Class<?>[] { boolean.class };
		Object[] args = new Object[] { isExact };
		notifyListeners(FundsAvailableListener.class, "exactChange", parameterTypes, args);
	} 
}
