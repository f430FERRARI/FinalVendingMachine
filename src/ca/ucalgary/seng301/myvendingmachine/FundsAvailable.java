package ca.ucalgary.seng301.myvendingmachine;

import java.util.Iterator;
import java.util.Vector;

public class FundsAvailable {
	//TODO: Aggregates instances? 
	//TODO: Aggregates children?
	
	private Vector<FundsAvailableListener> listeners = new Vector<FundsAvailableListener>();

	public final void register(FundsAvailableListener listener) {
		listeners.add(listener);
	}
	
	public int getAvailable() {
		// TODO Auto-generated method stub
		return 0; 
	} 
	
	public void removeFunds(int amount) {
		// TODO Auto-generated method stub

	} 
	
	protected void addFunds(int amount) {
		// TODO Auto-generated method stub
		
	} 
	
	protected Iterator<FundsAvailable> getChildren() {  	//Not sure about the type parameter
		// TODO Auto-generated method stub

		return null;		
	} 
	
	public void returnFunds() {
		// TODO Auto-generated method stub

	} 
	
	public FundsAvailable getInstance() {
		// TODO Auto-generated method stub

		return null;		
	}
	
}
