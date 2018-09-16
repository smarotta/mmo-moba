package com.sm.mmo.moba.gameserver.codec;

import com.google.protobuf.InvalidProtocolBufferException;
//import com.sm.mmo.moba.domain.Message;
//import com.sm.mmo.moba.domain.message.network.NetworkMessage;
import com.google.protobuf.Message;
import com.sm.mmo.moba.network.MessageCodec;

import io.netty.buffer.ByteBuf;

public class NetworkMessageDecoder {

	public static Message decode(ByteBuf buffer) throws InvalidProtocolBufferException {
		byte [] bucket = readPackageFromBuffer(buffer);
		return MessageCodec.decode(bucket);
	}
	
	/**
	 * ND DL DL TP TP DT DT DT DT DT DT
	 * HEADERS:
	 *  - ND: number of bytes describing lenght
	 *  - DL DL: bytes describing length
	 * CONTENT:
	 *  - TP TP: bytes describing type
	 *  - DT DT DT DT DT DT: data
	 * @param buffer
	 * @return
	 */
	private static byte [] readPackageFromBuffer(ByteBuf buffer) {
		int numberOfBytesDescribingLength = buffer.getUnsignedByte(0);
		int packageSize = getPackageSize(buffer, numberOfBytesDescribingLength);
		int headerSize = numberOfBytesDescribingLength + 1;
		int packageSizeWithoutHeaders = packageSize - headerSize;
		
		byte [] array = new byte [packageSizeWithoutHeaders];
		buffer.readerIndex(headerSize);
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
