package ca.ucalgary.seng301.myvendingmachine;

import ca.ucalgary.seng301.vendingmachine.hardware.AbstractHardware;
import ca.ucalgary.seng301.vendingmachine.hardware.AbstractHardwareListener;

//Associated with display, and funds availible
public class NotificationManager implements FundsAvailableListener, ProductSelectionListener {


	@Override
	public void fundsAdded(int amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fundsRemoved(int amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fundsReturned() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hardwareFailure() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insufficientFunds() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void outOfStock() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispensed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enabled(AbstractHardware<AbstractHardwareListener> hardware) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disabled(AbstractHardware<AbstractHardwareListener> hardware) {
		// TODO Auto-generated method stub
		
	}

}
