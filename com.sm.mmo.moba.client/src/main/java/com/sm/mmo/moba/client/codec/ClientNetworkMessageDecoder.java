package com.sm.mmo.moba.client.codec;

import org.com.sm.mmo.moba.domain.Message;
import org.com.sm.mmo.moba.domain.message.network.NetworkMessage;

import io.netty.buffer.ByteBuf;

public class ClientNetworkMessageDecoder {

	private static String debug(ByteBuf buffer, int length) {
		StringBuilder sb = new StringBuilder();
		for(int x=0; x < length; x++) {
			sb.append(String.format("%02x", buffer.getByte(x)).toUpperCase()).append(" ");
		}
		return sb.toString();
	}
	
	public static boolean isReadyToDecode(ByteBuf buffer) {
		if (buffer != null && buffer.readableBytes() > 1) {
			int numberOfBytesDescribingLength = getNumberOfBytesDescribingLength(buffer);
			if (buffer.readableBytes() >= 1 + numberOfBytesDescribingLength + 1) {
				int length = getPackageSize(buffer, numberOfBytesDescribingLength);
				if (buffer.readableBytes() >= length) {
					System.out.println("READY TO DECODE: " + debug(buffer, length));
					return true;
				}
			}
		}
		return false;
	}
	
	public static Message decode(ByteBuf buffer) {
		//read to a byte array
		byte [] bucket = readPackageFromBuffer(buffer);
		
		//read the type
		NetworkMessage.SizeHeader sizeHeader = NetworkMessage.SizeHeader.getTypeForValue(bucket[0]);
		
		//call the factory with the actual type to give a hint to it of wth this package is about
		return ClientNetworkMessageFactory.deserialize(sizeHeader, bucket);
	}
	
	private static int getNumberOfBytesDescribingLength(ByteBuf buffer) {
		return (int) Math.round(Math.pow(2, (buffer.getUnsignedByte(0) - 0xC0)));
	}
	
	private static byte [] readPackageFromBuffer(ByteBuf buffer) {
		int length = getPackageSize(buffer, getNumberOfBytesDescribingLength(buffer));
		byte [] array = new byte [length];
		buffer.readBytes(array);
		return array;
	}	
	
	private static int getPackageSize(ByteBuf buffer, int numberOfBytesDescribingLength) {
		int size = 0;
		for(int i=0; i < numberOfBytesDescribingLength; i++) {
			int shift = 8 * (numberOfBytesDescribingLength - 1 - i);
			size += buffer.getByte(1 + i) << shift;
		}
		return size;
	}
}