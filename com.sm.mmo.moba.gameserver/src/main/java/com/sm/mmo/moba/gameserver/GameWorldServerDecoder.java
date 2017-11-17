package com.sm.mmo.moba.gameserver;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteOrder;

import com.sm.mmo.moba.gameserver.codec.NetworkMessageDecoder;

public class GameWorldServerDecoder extends LengthFieldBasedFrameDecoder {
	
	public GameWorldServerDecoder() {
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
			System.out.println("Server <- Client: " + debug(frame));
			return NetworkMessageDecoder.decode(frame);
		} else {
			return null;
		}
	}
}