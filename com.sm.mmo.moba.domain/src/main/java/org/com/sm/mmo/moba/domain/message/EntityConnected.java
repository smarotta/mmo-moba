package org.com.sm.mmo.moba.domain.message;


public class EntityConnected extends EntityUpdate {

	@Override
	public MessageType getType() {
		return MessageType.ENTITY_DISCONNECTED;
	}

}
