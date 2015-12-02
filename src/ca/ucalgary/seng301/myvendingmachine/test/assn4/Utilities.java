package ca.ucalgary.seng301.myvendingmachine.test.assn4;

import java.util.ArrayList;
import java.util.List;

import ca.ucalgary.seng301.vendingmachine.Coin;
import ca.ucalgary.seng301.vendingmachine.Product;
import ca.ucalgary.seng301.vendingmachine.hardware.CoinRack;
import ca.ucalgary.seng301.vendingmachine.hardware.ProductRack;
import ca.ucalgary.seng301.vendingmachine.hardware.SimulationException;
import ca.ucalgary.seng301.vendingmachine.hardware.VendingMachine;

public class Utilities {
    /**
     * A convenience method for checking the contents of the delivery chute
     * against an expected list. The list should contain one String per expected
     * product (its name) and zero or more positive Integers, in any order. The
     * Integers are added together and compared to the total value of coins in
     * the delivery chute. Empties the chute.
     * 
     * @param expectedItems
     *            A list of Strings and Integers
     * @return true if the delivery is as expected, else false
     */
    public static List<Object> extractAndSortFromDeliveryChute(VendingMachine vm) {
	Object[] actualItems = vm.getDeliveryChute().removeItems();
	int actualValue = 0;
	List<Object> actualList = new ArrayList<>();

	for(Object obj : actualItems) {
	    if(obj instanceof Product) {
		Product pc = (Product)obj;
		String name = pc.getName();
		actualList.add(name);
	    }
	    else
		actualValue += ((Coin)obj).getValue();
	}

	actualList.sort(null);

	actualList.add(0, actualValue);
	return actualList;
    }

    /**
     * Convenience method for checking the total value of coins in the coin
     * racks. Empties the coin racks.
     *
     * @param expectedSum
     *            The total value expected
     * @return true if the actual sum of coin values equals the expected sum,
     *         else false
     */
    public static int extractAndSumFromCoinRacks(VendingMachine vm) {
	int total = 0;

	for(int i = 0, max = vm.getNumberOfCoinRacks(); i < max; i++) {
	    CoinRack cr = vm.getCoinRack(i);
	    List<Coin> coins = cr.unloadWithoutEvents();
	    for(Coin coin : coins)
		total += coin.getValue();
	}

	return total;
    }

    /**
     * Convenience method for checking the total value of coins in the storage
     * bin. Empties the storage bin.
     *
     * @param expectedSum
     *            The total value expected
     * @return true if the actual sum of coin values equals the expected sum,
     *         else false
     */
    public static int extractAndSumFromStorageBin(VendingMachine vm) {
	int total = 0;

	List<Coin> coins = vm.getStorageBin().unloadWithoutEvents();
	for(Coin coin : coins)
	    total += coin.getValue();

	return total;
    }

    /**
     * Convenience method for checking the contents of product racks. Empties the
     * product racks.
     *
     * @param expectedList
     *            The names of the products expected to be present. Can be an empty
     *            list. The same string can be repeated as necessary. The order
     *            is not significant.
     * @return true if the actual products have the exactly the same names as the
     *         expected ones else false
     */
    public static List<String> extractAndSortFromProductRacks(VendingMachine vm) {
	List<Product> actualProducts = new ArrayList<>();
	for(int i = 0, max = vm.getNumberOfProductRacks(); i < max; i++)
	    actualProducts.addAll(vm.getProductRack(i).unloadWithoutEvents());

	List<String> actualList = new ArrayList<>();
	for(Product productCan : actualProducts)
	    actualList.add(productCan.getName());

	actualList.sort(null);

	return actualList;
    }

    /**
     * A convenience method for constructing and loading a set of product cans into
     * the machine.
     * 
     * @param productCounts
     *            A variadic list of ints each representing the number of products
     *            to create and load into the corresponding rack.
     * @throws SimulationException
     *             If the number of arguments is different than the number of
     *             racks, or if any of the counts are negative.
     */
    public static void loadProducts(VendingMachine vm, int... productCounts) {
	if(productCounts.length != vm.getNumberOfProductRacks())
	    throw new SimulationException("Product counts have to equal number of racks");

	int i = 0;
	for(int productCount : productCounts) {
	    if(productCount < 0)
		throw new SimulationException("Each count must not be negative");

	    ProductRack pcr = vm.getProductRack(i);
	    String name = vm.getProductKindName(i);
	    for(int products = 0; products < productCount; products++)
		pcr.loadWithoutEvents(new Product(name));  //TODO: Beware of loadwithoutevents

	    i++;
	}
    }

    /**
     * A convenience method for constructing and loading a set of coins into the
     * machine.
     * 
     * @param coinCounts
     *            A variadic list of ints each representing the number of coins
     *            to create and load into the corresponding rack.
     * @throws SimulationException
     *             If the number of arguments is different than the number of
     *             racks, or if any of the counts are negative.
     */
	public static void loadCoins(VendingMachine vm, int... coinCounts) {
		if (coinCounts.length != vm.getNumberOfCoinRacks())
			throw new SimulationException("Coin counts have to equal number of racks");

		int i = 0;
		for (int coinCount : coinCounts) {
			if (coinCount < 0)
				throw new SimulationException("Each count must not be negative");

			CoinRack cr = vm.getCoinRack(i);
			int value = vm.getCoinKindForRack(i);
			for (int coins = 0; coins < coinCount; coins++)
				cr.loadWithoutEvents(new Coin(value));  //TODO: Beware of loadwithoutevents

			i++;
		}
	}
}
