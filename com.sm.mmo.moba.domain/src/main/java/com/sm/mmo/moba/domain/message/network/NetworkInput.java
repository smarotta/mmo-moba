package com.sm.mmo.moba.domain.message.network;

import com.sm.mmo.moba.domain.Entity;

public abstract class NetworkInput extends NetworkMessage {
	
	private Entity source;

	public Entity getSource() {
		return source;
	}

	public void setSource(Entity source) {
		this.source = source;
	}
	
	public abstract void deserialize(byte [] packet);
	
}
