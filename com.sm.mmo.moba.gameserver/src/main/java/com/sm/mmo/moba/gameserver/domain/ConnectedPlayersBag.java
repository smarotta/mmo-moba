package com.sm.mmo.moba.gameserver.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.sm.mmo.moba.domain.ConnectedPlayer;

import io.netty.channel.ChannelHandlerContext;

public class ConnectedPlayersBag {
	private Map<ChannelHandlerContext, ConnectedPlayer<ChannelHandlerContext>> contextClientMap;
	private Map<Integer, ConnectedPlayer<ChannelHandlerContext>> connectedPlayerIdMap;
	private Map<Integer, ChannelHandlerContext> connectedPlayerContextMap;
	
	
	public ConnectedPlayersBag() {
		contextClientMap = new ConcurrentHashMap<ChannelHandlerContext, ConnectedPlayer<ChannelHandlerContext>>();
		connectedPlayerIdMap = new ConcurrentHashMap<Integer, ConnectedPlayer<ChannelHandlerContext>>();
		connectedPlayerContextMap = new ConcurrentHashMap<Integer, ChannelHandlerContext>();
	}
	
	private ConnectedPlayersBag(Map<ChannelHandlerContext, ConnectedPlayer<ChannelHandlerContext>> contextClientMap, 
			Map<Integer, ConnectedPlayer<ChannelHandlerContext>> connectedPlayerIdMap, 
			Map<Integer, ChannelHandlerContext> connectedPlayerContextMap) {
		this.contextClientMap = new HashMap<ChannelHandlerContext, ConnectedPlayer<ChannelHandlerContext>>(contextClientMap);
		this.connectedPlayerIdMap = new HashMap<Integer, ConnectedPlayer<ChannelHandlerContext>>(connectedPlayerIdMap);
		this.connectedPlayerContextMap = new HashMap<Integer, ChannelHandlerContext>(connectedPlayerContextMap);
	}
	
	public ConnectedPlayer<ChannelHandlerContext> getPlayerByContext(ChannelHandlerContext chc) {
		return contextClientMap.get(chc);
	}
	
	public ChannelHandlerContext getContextByPlayer(ConnectedPlayer<ChannelHandlerContext> player) {
		return connectedPlayerContextMap.get(player.getId());
	}
	
	public ChannelHandlerContext getContextByPlayerId(Integer id) {
		ConnectedPlayer<ChannelHandlerContext> player = connectedPlayerIdMap.get(id);
		if (player != null) {
			return getContextByPlayer(player);
		}
		return null;
	}
	
	public ConnectedPlayer<ChannelHandlerContext> getPlayerById(Integer id) {
		return connectedPlayerIdMap.get(id);
	}
	
	public void add(ChannelHandlerContext chc, ConnectedPlayer<ChannelHandlerContext> player) {
		contextClientMap.put(chc, player);
		connectedPlayerIdMap.put(player.getId(), player);
		connectedPlayerContextMap.put(player.getId(), chc);
	}
	
	public void remove(ChannelHandlerContext chc) {
		ConnectedPlayer<ChannelHandlerContext> player = contextClientMap.get(chc);
		if (player != null) {
			contextClientMap.remove(chc);
			connectedPlayerIdMap.remove(player.getId());
			connectedPlayerContextMap.remove(player.getId());
		}
	}
	
	public void remove(Integer playerId) {
		connectedPlayerIdMap.remove(playerId);
		connectedPlayerContextMap.remove(playerId);
		
		ChannelHandlerContext chc = connectedPlayerContextMap.get(playerId);
		if (chc != null) {
			contextClientMap.remove(chc);
		}
	}
	
	public ConnectedPlayersBag duplicate() {
		return new ConnectedPlayersBag(contextClientMap, connectedPlayerIdMap, connectedPlayerContextMap);
	}
}
