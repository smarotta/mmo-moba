package com.sm.mmo.moba.gameserver;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import com.google.protobuf.Message;
import com.sm.mmo.moba.gameserver.codec.NetworkMessageEncoder;

public class GameWorldServerEncoder extends MessageToByteEncoder<Message>{
	
	public String debug(byte [] data) {
		StringBuilder sb = new StringBuilder();
		for(int x=0; x < data.length; x++) {
			sb.append(String.format("%02x", data[x]).toUpperCase()).append(" ");
		}
		return sb.toString();
	}
	
	@Override
	protected void encode(ChannelHandlerContext ctx, Message message, ByteBuf out) throws Exception {
		NetworkMessageEncoder.encode(message, out);
		System.out.println("Server -> Client: " + debug(out.asReadOnly().array()));
		
	}
	
}
