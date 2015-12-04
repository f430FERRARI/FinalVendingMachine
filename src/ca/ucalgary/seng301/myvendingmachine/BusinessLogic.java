package ca.ucalgary.seng301.myvendingmachine;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import ca.ucalgary.seng301.vendingmachine.hardware.AbstractHardware;
import ca.ucalgary.seng301.vendingmachine.hardware.AbstractHardwareListener;
import ca.ucalgary.seng301.vendingmachine.hardware.Button;
import ca.ucalgary.seng301.vendingmachine.hardware.ButtonListener;
import ca.ucalgary.seng301.vendingmachine.hardware.CapacityExceededException;
import ca.ucalgary.seng301.vendingmachine.hardware.CoinRack;
import ca.ucalgary.seng301.vendingmachine.hardware.DisabledException;
import ca.ucalgary.seng301.vendingmachine.hardware.EmptyException;
import ca.ucalgary.seng301.vendingmachine.hardware.ProductRack;
import ca.ucalgary.seng301.vendingmachine.hardware.SimulationException;
import ca.ucalgary.seng301.vendingmachine.hardware.VendingMachine;

public class BusinessLogic extends AbstractHardware<ProductSelectionListener> implements ButtonListener {

	private Vector<ProductSelectionListener> listeners = new Vector<ProductSelectionListener>();

	private VendingMachine vendingMachine; 
	private NotificationManager notificationManager;
	
	private Map<Button, Integer> buttonToIndex = new HashMap<>();
	private Map<Integer, Integer> valueToIndexMap = new HashMap<>();

	public BusinessLogic(VendingMachine vm) {
		vendingMachine = vm;  
		FundsAvailable.getInstance().reset();
		FundsAvailable.getInstance().registerPaymentMethod(vm);
		notificationManager = new NotificationManager(vendingMachine.getDisplay(), vendingMachine.getOutOfOrderLight(), vendingMachine.getExactChangeLight()); //TODO: Might wanna move this
		register(notificationManager);

		for (int i = 0; i < vm.getNumberOfSelectionButtons(); i++) {
			Button sb = vm.getSelectionButton(i);
			sb.register(this);
			buttonToIndex.put(sb, i);
		}

		for (int i = 0; i < vm.getNumberOfCoinRacks(); i++) {
			int value = vm.getCoinKindForRack(i);
			valueToIndexMap.put(value, i);
		}
	}

	@Override
	public void enabled(AbstractHardware<AbstractHardwareListener> hardware) {
	}

	@Override
	public void disabled(AbstractHardware<AbstractHardwareListener> hardware) {
	}

	@Override
	public void pressed(Button button) {

		// Return button is pressed
		if (button == vendingMachine.getReturnButton()) {
			FundsAvailable.getInstance().returnFunds();
		}

		// Button is selected for product

		Integer index = buttonToIndex.get(button);

		if (index == null)
			throw new SimulationException("An invalid selection button was pressed");

		int cost = vendingMachine.getProductKindCost(index);

		if (cost <= FundsAvailable.getInstance().getFunds()) {
			ProductRack productRack = vendingMachine.getProductRack(index);
			if (productRack.size() > 0) {
				try {
					productRack.dispenseProduct();
					vendingMachine.getCoinReceptacle().storeCoins();
					deliverChange(cost);
				} catch (DisabledException | EmptyException | CapacityExceededException e) {
					throw new SimulationException(e);
				}
			}
		} else {
			notifyInsufficientFunds(cost);
		}
	}

	private Map<Integer, List<Integer>> changeHelper(ArrayList<Integer> values, int index, int changeDue) {
		if (index >= values.size())
			return null;

		int value = values.get(index);
		Integer ck = valueToIndexMap.get(value);
		CoinRack cr = vendingMachine.getCoinRack(ck);

		Map<Integer, List<Integer>> map = changeHelper(values, index + 1, changeDue);

		if (map == null) {
			map = new TreeMap<>(new Comparator<Integer>() {
				@Override
				public int compare(Integer o1, Integer o2) {
					return o2 - o1;
				}
			});
			map.put(0, new ArrayList<Integer>());
		}

		Map<Integer, List<Integer>> newMap = new TreeMap<>(map);
		for (Integer total : map.keySet()) {
			List<Integer> res = map.get(total);
			int intTotal = total;

			for (int count = cr.size(); count >= 0; count--) {
				int newTotal = count * value + intTotal;
				if (newTotal <= changeDue) {
					List<Integer> newRes = new ArrayList<>();
					if (res != null)
						newRes.addAll(res);

					for (int i = 0; i < count; i++)
						newRes.add(ck);

					newMap.put(newTotal, newRes);
				}
			}
		}

		return newMap;
	}

	private int deliverChange(int cost) throws CapacityExceededException, EmptyException, DisabledException {
		int changeDue = FundsAvailable.getInstance().getFunds() - cost;
		FundsAvailable.getInstance().removeFunds(cost);

		if (changeDue < 0)
			throw new InternalError("Cost was greater than entered, which should not happen");

		ArrayList<Integer> values = new ArrayList<>();
		for (Integer ck : valueToIndexMap.keySet())
			values.add(ck);

		Map<Integer, List<Integer>> map = changeHelper(values, 0, changeDue);

		List<Integer> res = map.get(changeDue);
		if (res == null) {
			// An exact match was not found, so do your best
			Iterator<Integer> iter = map.keySet().iterator();
			Integer max = 0;
			while (iter.hasNext()) {
				Integer temp = iter.next();
				if (temp > max)
					max = temp;
			}
			res = map.get(max);
		}

		for (Integer ck : res) {
			CoinRack cr = vendingMachine.getCoinRack(ck);
			cr.releaseCoin();
			FundsAvailable.getInstance().removeFunds(vendingMachine.getCoinKindForRack(ck));
		}

		return changeDue;
	}

	private void notifyInsufficientFunds(int cost) {
		Class<?>[] parameterTypes = new Class<?>[] { int.class };
		Object[] args = new Object[] { cost };
		notifyListeners(ProductSelectionListener.class, "insufficientFunds", parameterTypes, args);
	}

}
