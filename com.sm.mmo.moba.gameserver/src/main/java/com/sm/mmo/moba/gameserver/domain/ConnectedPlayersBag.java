package com.sm.mmo.moba.gameserver.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.ChannelHandlerContext;

public class ConnectedPlayersBag {
	private Map<ChannelHandlerContext, ConnectedPlayer> contextClientMap;
	private Map<UUID, ConnectedPlayer> connectedPlayerIdMap;
	private Map<UUID, ChannelHandlerContext> connectedPlayerContextMap;
	
	
	public ConnectedPlayersBag() {
		contextClientMap = new ConcurrentHashMap<ChannelHandlerContext, ConnectedPlayer>();
		connectedPlayerIdMap = new ConcurrentHashMap<UUID, ConnectedPlayer>();
		connectedPlayerContextMap = new ConcurrentHashMap<UUID, ChannelHandlerContext>();
	}
	
	private ConnectedPlayersBag(Map<ChannelHandlerContext, ConnectedPlayer> contextClientMap, 
			Map<UUID, ConnectedPlayer> connectedPlayerIdMap, 
			Map<UUID, ChannelHandlerContext> connectedPlayerContextMap) {
		this.contextClientMap = new HashMap<ChannelHandlerContext, ConnectedPlayer>(contextClientMap);
		this.connectedPlayerIdMap = new HashMap<UUID, ConnectedPlayer>(connectedPlayerIdMap);
		this.connectedPlayerContextMap = new HashMap<UUID, ChannelHandlerContext>(connectedPlayerContextMap);
	}
	
	public ConnectedPlayer getPlayerByContext(ChannelHandlerContext chc) {
		return contextClientMap.get(chc);
	}
	
	public ChannelHandlerContext getContextByPlayer(ConnectedPlayer player) {
		return connectedPlayerContextMap.get(player.getId());
	}
	
	public ChannelHandlerContext getContextByPlayerId(UUID id) {
		ConnectedPlayer player = connectedPlayerIdMap.get(id);
		if (player != null) {
			return getContextByPlayer(player);
		}
		return null;
	}
	
	public ConnectedPlayer getPlayerById(UUID id) {
		return connectedPlayerIdMap.get(id);
	}
	
	public void add(ChannelHandlerContext chc, ConnectedPlayer player) {
		contextClientMap.put(chc, player);
		connectedPlayerIdMap.put(player.getId(), player);
		connectedPlayerContextMap.put(player.getId(), chc);
	}
	
	public void remove(ChannelHandlerContext chc) {
		ConnectedPlayer player = contextClientMap.get(chc);
		if (player != null) {
			contextClientMap.remove(chc);
			connectedPlayerIdMap.remove(player.getId());
			connectedPlayerContextMap.remove(player.getId());
		}
	}
	
	public void remove(UUID playerId) {
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
