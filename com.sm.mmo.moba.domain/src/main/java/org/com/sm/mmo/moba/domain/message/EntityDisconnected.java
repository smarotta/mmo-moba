package org.com.sm.mmo.moba.domain.message;


public class EntityDisconnected extends EntityUpdate {

	@Override
	public MessageType getType() {
		return MessageType.ENTITY_DISCONNECTED;
	}

}
