package ca.ucalgary.seng301.myvendingmachine.test.assn4;

import org.junit.After;

public class WRAPPER {
	private static int counter = 0;
	public static StringBuilder actual = null;

	@After
	public void WRAPPERteardown() {
		System.err.println("TEST: " + ++counter);
		System.err.print(actual.toString());
		System.err.println();
	}
}
