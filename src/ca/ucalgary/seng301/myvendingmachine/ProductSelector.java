package ca.ucalgary.seng301.myvendingmachine;

import java.util.Vector;

import ca.ucalgary.seng301.vendingmachine.hardware.AbstractHardware;
import ca.ucalgary.seng301.vendingmachine.hardware.AbstractHardwareListener;
import ca.ucalgary.seng301.vendingmachine.hardware.Button;
import ca.ucalgary.seng301.vendingmachine.hardware.ButtonListener;

//Associated with ProductKind and FundsAvailable 
public class ProductSelector implements ButtonListener, ProductKindListener {

	private Vector<ProductSelectionListener> listeners = new Vector<ProductSelectionListener>();

	public final void register(ProductSelectionListener listener) {
		listeners.add(listener);
	}
	
	@Override
	public void enabled(AbstractHardware<AbstractHardwareListener> hardware) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disabled(AbstractHardware<AbstractHardwareListener> hardware) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void outOfStock() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hardwareFailure() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pressed(Button button) {
		// TODO Auto-generated method stub
		
	}
	
}
