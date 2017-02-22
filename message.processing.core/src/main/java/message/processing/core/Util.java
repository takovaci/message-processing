package message.processing.core;

import message.processing.model.PriceUnit;

public class Util {

	
	/**
	 * 
	 * @param p1:
	 *            first product unit
	 * @param p2:
	 *            second product unit
	 * @return adjustment if we want to compare units
	 */
	public static float calculateUnitAdjustment(PriceUnit p1, PriceUnit p2) {
		if (p1.compareTo(p2) == 0) {
			return 1f;
		} else if (p1.compareTo(PriceUnit.GBP) == 0) {
			return 100f;
		} else {
			return 0.01f;
		}

	}
}
