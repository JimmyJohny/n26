/**
 * 
 */
package com.n26.challenge.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author jimmy
 *
 */
public class Utils {
	
	public static double round(double value) {
	    
	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(2, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

}
