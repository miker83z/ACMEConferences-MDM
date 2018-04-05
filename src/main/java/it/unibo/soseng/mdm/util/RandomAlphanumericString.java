package it.unibo.soseng.mdm.util;

import java.util.Random;

public final class RandomAlphanumericString {

	private static final int stringLength = 32; 
	
	public static String generate() {
	    return generate(stringLength);
	}
	
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
	
	public static int getStringLength() {
		return stringLength;
	}
}
