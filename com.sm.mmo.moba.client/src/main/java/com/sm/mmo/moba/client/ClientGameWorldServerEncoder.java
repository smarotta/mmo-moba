package com.sm.mmo.moba.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import org.com.sm.mmo.moba.domain.message.network.NetworkMessage;
import org.com.sm.mmo.moba.domain.message.network.NetworkOutput;

import com.sm.mmo.moba.client.codec.ClientNetworkMessageEncoder;

public class ClientGameWorldServerEncoder extends MessageToByteEncoder<NetworkMessage>{
	
	public String debug(byte [] data) {
		StringBuilder sb = new StringBuilder();
		for(int x=0; x < data.length; x++) {
			sb.append(String.format("%02x", data[x]).toUpperCase()).append(" ");
		}
		return sb.toString();
	}
	
	@Override
	protected void encode(ChannelHandlerContext ctx, NetworkMessage msg, ByteBuf out) throws Exception {
		if (msg instanceof NetworkOutput) {
			byte [] data = ClientNetworkMessageEncoder.encode((NetworkOutput)msg);
			System.out.println("Server -> Client: " + debug(data));
			out.writeBytes(data);
		}
	}
	
}
