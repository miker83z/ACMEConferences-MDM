package it.unibo.soseng.mdm.util;

import java.util.Random;

/**
 * The Class RandomAlphanumericString, used to generate random alpha numeric strings.
 */
public final class RandomAlphanumericString {

	/** The Constant stringLength. */
	private static final int stringLength = 32; 
	
	/**
	 * Generate a random string.
	 *
	 * @return the string
	 */
	public static String generate() {
	    StringBuilder stringBuilder = new StringBuilder();

	    for (int i = 0; i < stringLength; i++) {
	        switch (new Random().nextInt(3)) {
	            case 0:
	                stringBuilder.append((char) (new Random().nextInt(9) + 48));
	                break;
	            case 1:
	                stringBuilder.append((char) (new Random().nextInt(25) + 65));
	                break;
	            case 2:
	                stringBuilder.append((char) (new Random().nextInt(25) + 97));
	                break;
	            default:
	                break;
	        }
	    }
	    return stringBuilder.toString();
	}
	
	/**
	 * Generate a random string with stringLength characters.
	 *
	 * @param stringLength the string length
	 * @return the string
	 */
	public static String generate( int stringLength ) {
	    StringBuilder stringBuilder = new StringBuilder();

	    for (int i = 0; i < stringLength; i++) {
	        switch (new Random().nextInt(3)) {
	            case 0:
	                stringBuilder.append((char) (new Random().nextInt(9) + 48));
	                break;
	            case 1:
	                stringBuilder.append((char) (new Random().nextInt(25) + 65));
	                break;
	            case 2:
	                stringBuilder.append((char) (new Random().nextInt(25) + 97));
	                break;
	            default:
	                break;
	        }
	    }
	    return stringBuilder.toString();
	}
	
	/**
	 * Gets the string length.
	 *
	 * @return the string length
	 */
	public static int getStringLength() {
		return stringLength;
	}
}
