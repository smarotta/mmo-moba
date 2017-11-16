package com.sm.mmo.moba.client;

import io.netty.channel.ChannelHandlerContext;

import org.com.sm.mmo.moba.domain.Entity;

public class ConnectedPlayer extends Entity {

private ChannelHandlerContext context;
	
	public ConnectedPlayer(ChannelHandlerContext context) {
		this.context = context;
	}

	public ChannelHandlerContext getContext() {
		return context;
	}

	public void setContext(ChannelHandlerContext context) {
		this.context = context;
	}
	
}
