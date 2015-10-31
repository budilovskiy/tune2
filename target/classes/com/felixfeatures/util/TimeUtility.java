package com.felixfeatures.util;

/**
 * 
 * Time Utility with static method convert() to convert integer value of time in
 * seconds to string "mm:ss"
 *
 */
public class TimeUtility {
	private static final String DIVIDER = ":";

	/**
	 * convert integer value of time in seconds to string "mm:ss"
	 * 
	 * @param time - time in seconds
	 * @return sting value in "mm:ss" format
	 */
	public static String convert(int time) {
		if (time < 0) {
			throw new IllegalArgumentException("Time must be positive.");
		}
		return String.format("%02d" + DIVIDER + "%02d", time / 60, time % 60);
	}

}
