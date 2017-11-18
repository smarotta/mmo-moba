package com.sm.mmo.moba.domain.message;

public class EntityRemoved extends EntityUpdate {

	@Override
	public MessageType getType() {
		return MessageType.INTERNAL;
	}

}
