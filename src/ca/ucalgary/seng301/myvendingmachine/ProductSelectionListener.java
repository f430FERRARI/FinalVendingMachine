package ca.ucalgary.seng301.myvendingmachine;

import ca.ucalgary.seng301.vendingmachine.hardware.AbstractHardwareListener;

public interface ProductSelectionListener extends AbstractHardwareListener{
	public void insufficientFunds(int cost); 
	
	public void outOfStock(); 
	
	public void dispensed(); 
	
	public void hardwareFailure();
}
