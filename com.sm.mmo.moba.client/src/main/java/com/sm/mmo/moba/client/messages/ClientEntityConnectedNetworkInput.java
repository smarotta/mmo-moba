package com.sm.mmo.moba.client.messages;

import com.sm.mmo.moba.domain.Entity;
import com.sm.mmo.moba.domain.message.EntityConnected;
import com.sm.mmo.moba.domain.message.network.NetworkInput;
import com.sm.mmo.moba.domain.message.network.helper.CodecHelper;

public class ClientEntityConnectedNetworkInput extends NetworkInput {

	private EntityConnected entityConnected;
	
	public ClientEntityConnectedNetworkInput(EntityConnected entityConnected) {
		this.entityConnected = entityConnected;
	}
	
	public EntityConnected getEntityConnected() {
		return entityConnected;
	}

	public void setEntityConnected(EntityConnected entityConnected) {
		this.entityConnected = entityConnected;
	}

	@Override
	public SizeHeader getSizeHeader() {
		return SizeHeader.C1;
	}

	@Override
	public MessageType getType() {
		return MessageType.ENTITY_CONNECTED;
	}

	@Override
	public void deserialize(byte[] packet) {
		// C1 XX XX 01 [ID ID ID ID ID ID ID ID ID ID ID ID ID ID ID ID] [MK]
		entityConnected.setEntity(new Entity());
		entityConnected.getEntity().setId(CodecHelper.readUUID(packet, 4));
		entityConnected.setCurrentPlayer(packet[4 + 16] > 0);
	}

	
	
}
