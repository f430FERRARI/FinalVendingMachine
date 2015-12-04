package ca.ucalgary.seng301.myvendingmachine;

import java.util.Timer;
import java.util.TimerTask;

import com.sun.org.apache.xpath.internal.functions.FuncUnparsedEntityURI;

import ca.ucalgary.seng301.vendingmachine.hardware.AbstractHardware;
import ca.ucalgary.seng301.vendingmachine.hardware.AbstractHardwareListener;
import ca.ucalgary.seng301.vendingmachine.hardware.Display;
import ca.ucalgary.seng301.vendingmachine.hardware.IndicatorLight;

public class NotificationManager implements FundsAvailableListener, ProductSelectionListener {

	private static NotificationManager instance = new NotificationManager();

	private Display display;
	private IndicatorLight outOfOrderLight;
	private IndicatorLight exactChangeLight;

	private NotificationManager() {
		FundsAvailable.getInstance().register(this);
	}

	public void installNotificationDevices(Display d, IndicatorLight oOL, IndicatorLight eCL) {
		outOfOrderLight = oOL;
		exactChangeLight = eCL;
		display = d;

		display.display("Drink Pop!");
	}

	// TODO: Seems weird
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
			timedNotification(1000, msg); // TODO: Watch execution of these timings
			timedNotification(2000, "No funds!"); 
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

	// Activate out of order light
	// Notify IndicatorLightListeners that it has been activated
	// TODO: Deactivate this light
	@Override
	public void hardwareFailure() {
		outOfOrderLight.activate();
	}

	@Override
	public void insufficientFunds(int cost) {
		String oldMsg = display.read();
		String newMsg = "Cost is " + cost;
		timedNotification(5000, newMsg);
		display.display(oldMsg);
	}

	@Override
	public void dispensed() {
		timedNotification(5000, "Pop and change Dispensed");
		display.display("Drink Pop!");
	}
	
	// TODO: Deactivate this light
	@Override
	public void exactChange() {
		exactChangeLight.activate();
	}

	private void timedNotification(int ms, String newMsg) {
		display.display(newMsg);
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				timer.cancel();
			}
		}, ms);
	}

	@Override
	public void outOfStock() {
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
