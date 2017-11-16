package com.sm.mmo.moba.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

import org.com.sm.mmo.moba.domain.message.network.NetworkMessage;
import org.com.sm.mmo.moba.domain.message.network.NetworkOutput;

import com.sm.mmo.moba.client.codec.ClientNetworkMessageDecoder;
import com.sm.mmo.moba.client.codec.ClientNetworkMessageEncoder;

public class ClientGameWorldServerDecoder extends ByteToMessageCodec<NetworkMessage>{
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (ClientNetworkMessageDecoder.isReadyToDecode(in)) {
			out.add(ClientNetworkMessageDecoder.decode(in));
		}
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, NetworkMessage msg, ByteBuf out) throws Exception {
		if (msg instanceof NetworkOutput) {
			out.writeBytes(ClientNetworkMessageEncoder.encode((NetworkOutput)msg));
		}
	}
	
}
