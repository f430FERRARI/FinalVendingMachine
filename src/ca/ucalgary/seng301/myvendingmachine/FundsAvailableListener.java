package ca.ucalgary.seng301.myvendingmachine;

import ca.ucalgary.seng301.vendingmachine.hardware.AbstractHardwareListener;

public interface FundsAvailableListener extends AbstractHardwareListener{
	
	public void fundsAdded(int amount); 
	
	public void fundsRemoved(int amount); 
	
	public void fundsReturned(); 
	
	public void hardwareFailure();
}
