package com.sm.mmo.moba.gameserver.codec;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.google.protobuf.Message;
import com.sm.mmo.moba.network.MessageCodec;

import io.netty.buffer.ByteBuf;

public class NetworkMessageEncoder {

	public static void encode(Message message, ByteBuf out) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(100);
		MessageCodec.encode(message, outputStream);
		out.writeBytes(outputStream.toByteArray());
	}

}
