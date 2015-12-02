package ca.ucalgary.seng301.myvendingmachine;

public interface FundsAvailableListener {
	
	public void fundsAdded(int amount); 
	
	public void fundsRemoved(int amount); 
	
	public void fundsReturned(); 
	
	public void hardwareFailure();
}
