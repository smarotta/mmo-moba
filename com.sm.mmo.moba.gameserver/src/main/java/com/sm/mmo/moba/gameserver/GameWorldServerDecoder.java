package com.sm.mmo.moba.gameserver;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

import org.com.sm.mmo.moba.domain.message.network.NetworkMessage;
import org.com.sm.mmo.moba.domain.message.network.NetworkOutput;

import com.sm.mmo.moba.gameserver.codec.NetworkMessageDecoder;
import com.sm.mmo.moba.gameserver.codec.NetworkMessageEncoder;

public class GameWorldServerDecoder extends ByteToMessageCodec<NetworkMessage>{
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (NetworkMessageDecoder.isReadyToDecode(in)) {
			out.add(NetworkMessageDecoder.decode(in));
		}
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, NetworkMessage msg, ByteBuf out) throws Exception {
		if (msg instanceof NetworkOutput) {
			out.writeBytes(NetworkMessageEncoder.encode((NetworkOutput)msg));
		}
	}
	
}
