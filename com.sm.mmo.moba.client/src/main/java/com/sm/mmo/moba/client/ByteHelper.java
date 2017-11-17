package com.sm.mmo.moba.client;

public class ByteHelper {
	
	public static String debug(byte [] data) {
		StringBuilder sb = new StringBuilder();
		for(int x=0; x < data.length; x++) {
			sb.append(String.format("%02x", data[x]).toUpperCase()).append(" ");
		}
		return sb.toString();
	}
	
	
}
