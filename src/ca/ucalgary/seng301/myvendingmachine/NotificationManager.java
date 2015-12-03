package ca.ucalgary.seng301.myvendingmachine;

import java.util.Timer;
import java.util.TimerTask;

import ca.ucalgary.seng301.vendingmachine.hardware.AbstractHardware;
import ca.ucalgary.seng301.vendingmachine.hardware.AbstractHardwareListener;
import ca.ucalgary.seng301.vendingmachine.hardware.Display;
import ca.ucalgary.seng301.vendingmachine.hardware.IndicatorLight;

//Associated with display, and funds available
public class NotificationManager implements FundsAvailableListener, ProductSelectionListener {

	Display display;
	IndicatorLight outOfOrderLight; 
	IndicatorLight exactChangeLight;
	
	public NotificationManager(Display d, IndicatorLight oOL, IndicatorLight eCL) {
		display = d; 
		outOfOrderLight = oOL; 
		exactChangeLight = eCL;
	}

	private void timedNotification(int ms, String oldMsg, String newMsg) {
		display.display(newMsg); 
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				timer.cancel();
			}
		}, ms); 
		display.display(oldMsg);
	}
	
	@Override
	public void fundsAdded(int amount) {
		display.display("Total: " + FundsAvailable.getInstance().getFunds() + " units");
	}

	@Override
	public void fundsRemoved(int amount) {
		display.display("Total: " + FundsAvailable.getInstance().getFunds() + " units"); 
	}

	@Override
	public void fundsReturned() { 
		String oldMsg = display.read();
		String newMsg = "Funds returned";
		timedNotification(2000, oldMsg, newMsg);
	}

	//Activate out of order light 
	//Notify IndicatorLightListeners that it has been activated 
	//TODO: Deactivate this light
	@Override
	public void hardwareFailure() {
		outOfOrderLight.activate(); 
	}

	@Override
	public void insufficientFunds(int cost) { 
		String oldMsg = display.read();
		String newMsg = "Cost is " + cost; 
		timedNotification(5000, oldMsg, newMsg);;
	}

	//TODO: Deactivate this light
	@Override
	public void exactChange() {
		exactChangeLight.activate();
	} 
	
	@Override
	public void outOfStock() {
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
