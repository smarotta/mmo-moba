package com.sm.mmo.moba.network;

public enum MessageType {

	ENTITY_SPAWNED(0x01),
	ENTITY_DESTROYED(0x02),
	;
	
	
	private short id;
	
	MessageType(int id){
		this.id = (short) id;
	}
	
	public short getId() {
		return this.id;
	}
}
