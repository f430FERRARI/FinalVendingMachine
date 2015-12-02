package ca.ucalgary.seng301.myvendingmachine;

import java.util.Vector;

import ca.ucalgary.seng301.vendingmachine.Product;
import ca.ucalgary.seng301.vendingmachine.hardware.AbstractHardware;
import ca.ucalgary.seng301.vendingmachine.hardware.AbstractHardwareListener;
import ca.ucalgary.seng301.vendingmachine.hardware.ProductRack;
import ca.ucalgary.seng301.vendingmachine.hardware.ProductRackListener;

public class ProductKind implements ProductRackListener {

	private int cost;
	private String name;

	private Vector<ProductKindListener> listeners = new Vector<ProductKindListener>();

	public final void register(ProductKindListener listener) {
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
	public void productAdded(ProductRack productRack, Product product) {
		// TODO Auto-generated method stub

	}

	@Override
	public void productRemoved(ProductRack productRack, Product product) {
		// TODO Auto-generated method stub

	}

	@Override
	public void productFull(ProductRack productRack) {
		// TODO Auto-generated method stub

	}

	@Override
	public void productEmpty(ProductRack productRack) {
		// TODO Auto-generated method stub

	}

	public int getQuantityAvailable() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getStandardCost() {
		return cost;
	}

	public String getName() {
		return name;
	}

	public void dispenseProduct() {
		// TODO Auto-generated method stub

	}

	protected void setStandardCost(int cost) {
		// TODO Auto-generated method stub

	}

	protected void setName(String name) {
		// TODO Auto-generated method stub

	}

	public void popRemoved() {
		// TODO Auto-generated method stub

	}

	public void empty() {
		// TODO Auto-generated method stub

	}

	public void failure() {
		// TODO Auto-generated method stub

	}

	// TODO: register listeners?
}
