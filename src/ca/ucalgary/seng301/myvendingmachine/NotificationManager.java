package ca.ucalgary.seng301.myvendingmachine;

import ca.ucalgary.seng301.vendingmachine.hardware.AbstractHardware;
import ca.ucalgary.seng301.vendingmachine.hardware.AbstractHardwareListener;
import ca.ucalgary.seng301.vendingmachine.hardware.Display;
import ca.ucalgary.seng301.vendingmachine.hardware.DisplayListener;
import ca.ucalgary.seng301.vendingmachine.hardware.IndicatorLight;
import ca.ucalgary.seng301.vendingmachine.hardware.SimulationException;

public class NotificationManager implements FundsAvailableListener, ProductSelectionListener, DisplayListener {

	private static NotificationManager instance = new NotificationManager();

	private Display display;
	private IndicatorLight outOfOrderLight;
	private IndicatorLight exactChangeLight; 
	private String lastMsg;

	private NotificationManager() {
		FundsAvailable.getInstance().register(this);
	}

	public void installNotificationDevices(Display d, IndicatorLight oOL, IndicatorLight eCL) {
		outOfOrderLight = oOL;
		exactChangeLight = eCL;
		display = d; 

		display.register(this); 
		
		//Set default display message
		display.display("Drink Pop!");
	}

	public void registerBusinessLogic(BusinessLogic bl) {
		bl.register(this);
	}

	public static NotificationManager getInstance() {
		return instance;
	}

	@Override
	public void fundsAdded(int amount) {
		display.display("Total: " + FundsAvailable.getInstance().getFunds() + " units");
	}

	@Override
	public void fundsRemoved(int amount) {
		if (FundsAvailable.getInstance().getFunds() == 0) { 
			String msg = "Total: "+ FundsAvailable.getInstance().getFunds() + " units";
			timedNotification(400, msg);
			display.display("Drink Pop!");
		} else {
		display.display("Total: " + FundsAvailable.getInstance().getFunds() + " units"); 
		}
	}

	@Override
	public void fundsReturned() {
		String newMsg = "Funds returned";
		timedNotification(2000, newMsg); 
		display.display("Drink Pop!");
	}

	@Override
	public void hardwareFailure() {
		outOfOrderLight.activate();
	}

	@Override
	public void insufficientFunds(int cost) {
		String oldMsg = display.read();
		String newMsg = "Cost is " + cost;
		timedNotification(2000, newMsg);
		display.display(oldMsg);
	}

	@Override
	public void dispensed() {
		timedNotification(5000, "Pop and change Dispensed");
		display.display("Drink Pop!"); 
	}
	
	@Override
	public void exactChange(boolean isExact) {
		if (isExact) {
			exactChangeLight.activate();  
		} else { 
			exactChangeLight.deactivate();
		}
	}

	private void timedNotification(int ms, String newMsg) {
		display.display(newMsg); 
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			throw new SimulationException(e);
		}
	}

	@Override
	public void messageChange(Display display, String oldMsg, String newMsg) {
		lastMsg = oldMsg;
	} 
	
	public String getLastDisplayMsg() {
		return lastMsg;
	} 
	
	@Override
	public void enabled(AbstractHardware<AbstractHardwareListener> hardware) {
	}

	@Override
	public void disabled(AbstractHardware<AbstractHardwareListener> hardware) {
	}
}
