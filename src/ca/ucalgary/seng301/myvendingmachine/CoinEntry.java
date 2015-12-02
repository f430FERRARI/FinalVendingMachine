package ca.ucalgary.seng301.myvendingmachine;

import ca.ucalgary.seng301.vendingmachine.Coin;
import ca.ucalgary.seng301.vendingmachine.hardware.AbstractHardware;
import ca.ucalgary.seng301.vendingmachine.hardware.AbstractHardwareListener;
import ca.ucalgary.seng301.vendingmachine.hardware.CoinRack;
import ca.ucalgary.seng301.vendingmachine.hardware.CoinRackListener;
import ca.ucalgary.seng301.vendingmachine.hardware.CoinReceptacle;
import ca.ucalgary.seng301.vendingmachine.hardware.CoinReceptacleListener;

//MAY be missing methods, look at diagram  
//Diagram says this class is supposed to be private 
public class CoinEntry extends NotificationManager implements CoinRackListener, CoinReceptacleListener {

	@Override
	public void enabled(AbstractHardware<AbstractHardwareListener> hardware) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disabled(AbstractHardware<AbstractHardwareListener> hardware) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void coinAdded(CoinReceptacle receptacle, Coin coin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void coinsRemoved(CoinReceptacle receptacle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void coinsFull(CoinReceptacle receptacle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void coinsFull(CoinRack rack) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void coinsEmpty(CoinRack rack) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void coinAdded(CoinRack rack, Coin coin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void coinRemoved(CoinRack rack, Coin coin) {
		// TODO Auto-generated method stub
		
	}

	public void coinInserted() {
		// TODO Auto-generated method stub

	} 
	
	public void failure() {
		// TODO Auto-generated method stub

	} 
	
	public void full() {
		// TODO Auto-generated method stub

	} 
	
	public void empty() {
		// TODO Auto-generated method stub

	} 
	
	public CoinEntry getInstance() {
		return null;
		// TODO Auto-generated method stub

	}
}
