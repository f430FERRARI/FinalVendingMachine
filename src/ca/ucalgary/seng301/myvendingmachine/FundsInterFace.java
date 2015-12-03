package ca.ucalgary.seng301.myvendingmachine;

public interface FundsInterFace {
	public int getFunds(); 
	public void addFunds(int amount); 
	public void removeFunds(int amount); 
	public void clearFunds();
}
