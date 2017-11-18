package com.sm.mmo.moba.domain.message;


public class EntitySpawn extends EntityPosition {

	private int race;
	
	public int getRace() {
		return race;
	}

	public void setRace(int race) {
		this.race = race;
	}

	@Override
	public MessageType getType() {
		return MessageType.ENTITY_SPAWN;
	}
		
}
