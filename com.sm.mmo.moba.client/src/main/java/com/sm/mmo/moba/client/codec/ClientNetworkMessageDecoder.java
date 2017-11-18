package com.sm.mmo.moba.client.codec;

import com.sm.mmo.moba.client.ByteHelper;
import com.sm.mmo.moba.domain.Message;
import com.sm.mmo.moba.domain.message.network.NetworkMessage;

import io.netty.buffer.ByteBuf;

public class ClientNetworkMessageDecoder {

	public static Message decode(ByteBuf buffer) {
		//read to a byte array
		byte [] bucket = readPackageFromBuffer(buffer);
		
		System.out.println("READY TO DECODE: " + ByteHelper.debug(bucket));
		
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
