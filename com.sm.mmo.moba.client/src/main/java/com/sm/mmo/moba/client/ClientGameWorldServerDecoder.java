package com.sm.mmo.moba.client;

import java.nio.ByteOrder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import com.sm.mmo.moba.client.codec.ClientNetworkMessageDecoder;

public class ClientGameWorldServerDecoder extends LengthFieldBasedFrameDecoder {
	
	public ClientGameWorldServerDecoder() {
		super(ByteOrder.BIG_ENDIAN, 0xFFFF, 
				1, //lengthFieldOffset    
				2, //lengthFieldLength  
				-3, //lengthAdjustment
				0, //initialBytesToStrip 
				false);
	}
	
	public String debug(ByteBuf data) {
		StringBuilder sb = new StringBuilder();
		for(int x=0; x < data.readableBytes(); x++) {
			sb.append(String.format("%02x", data.getByte(x)).toUpperCase()).append(" ");
		}
		return sb.toString();
	}
	
	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		ByteBuf frame = (ByteBuf) super.decode(ctx, in);
		if (frame != null) {
			//System.out.println("Client <- Server: " + debug(frame));
			return ClientNetworkMessageDecoder.decode(frame);
		} else {
			return null;
		}
	}
}
