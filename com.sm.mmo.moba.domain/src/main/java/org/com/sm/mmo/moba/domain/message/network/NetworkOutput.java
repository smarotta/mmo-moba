package org.com.sm.mmo.moba.domain.message.network;

import org.com.sm.mmo.moba.domain.Entity;

public abstract class NetworkOutput extends NetworkMessage {
	
	private Entity destination;
	
	public Entity getDestination() {
		return destination;
	}

	public void setDestination(Entity destination) {
		this.destination = destination;
	}
	
	public abstract byte [] serialize();
}
