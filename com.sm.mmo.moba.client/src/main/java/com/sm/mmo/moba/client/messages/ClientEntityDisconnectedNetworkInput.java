package com.sm.mmo.moba.client.messages;

import com.sm.mmo.moba.domain.Entity;
import com.sm.mmo.moba.domain.message.EntityDisconnected;
import com.sm.mmo.moba.domain.message.network.NetworkInput;
import com.sm.mmo.moba.domain.message.network.helper.CodecHelper;

public class ClientEntityDisconnectedNetworkInput extends NetworkInput {

	private EntityDisconnected entityDisconnected;
	
	public ClientEntityDisconnectedNetworkInput(EntityDisconnected entityDisconnected) {
		this.entityDisconnected = entityDisconnected;
	}
	
	public EntityDisconnected getEntityDisconnected() {
		return entityDisconnected;
	}

	public void setEntityDisconnected(EntityDisconnected entityDisconnected) {
		this.entityDisconnected = entityDisconnected;
	}

	@Override
	public SizeHeader getSizeHeader() {
		return SizeHeader.C1;
	}

	@Override
	public MessageType getType() {
		return MessageType.ENTITY_DISCONNECTED;
	}

	@Override
	public void deserialize(byte[] packet) {
		// C1 XX XX 02 [ID ID ID ID ID ID ID ID ID ID ID ID ID ID ID ID]
		entityDisconnected.setEntity(new Entity());
		entityDisconnected.getEntity().setId(CodecHelper.readUUID(packet, 4));
	}

	
	
}
