package ca.ucalgary.seng301.myvendingmachine;

public interface ProductSelectionListener {
	public void insufficientFunds(); 
	
	public void outOfStock(); 
	
	public void dispensed(); 
	
	public void hardwareFailure();
}
