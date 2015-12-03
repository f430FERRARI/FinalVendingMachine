package ca.ucalgary.seng301.myvendingmachine;

import java.util.ArrayList;
import java.util.Vector;

import ca.ucalgary.seng301.vendingmachine.Coin;
import ca.ucalgary.seng301.vendingmachine.hardware.AbstractHardware;
import ca.ucalgary.seng301.vendingmachine.hardware.VendingMachine;

public class FundsAvailable extends AbstractHardware<FundsAvailableListener> {

	private ArrayList<FundsAvailable> paymentMethods = new ArrayList<FundsAvailable>();
	private Vector<FundsAvailableListener> listeners = new Vector<FundsAvailableListener>();

	public void removeFunds(int amount) {
		for (FundsAvailable paymentMethod : paymentMethods) {
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
		for (FundsAvailable paymentMethod : paymentMethods) {
			availableFunds += paymentMethod.getFunds();
		}
		return availableFunds;
	} 
	
	public FundsAvailable getInstance() {
		return this;
	}
	
	public void addFunds(int amount) { 
	} 

	public void clearFunds() {
		
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
