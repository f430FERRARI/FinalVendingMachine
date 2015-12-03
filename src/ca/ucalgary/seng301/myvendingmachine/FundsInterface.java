package ca.ucalgary.seng301.myvendingmachine;

public interface FundsInterface {
	public int getFunds();
	
	public void addFunds(int amount); 
	
	public void returnFunds(); 
	
	public void removeFunds(int amount); 
	
	public void clearFunds(); 
	
}
