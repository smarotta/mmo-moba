package com.sm.mmo.moba.domain.message.network;

import com.sm.mmo.moba.domain.message.EntityConnected;
import com.sm.mmo.moba.domain.message.network.helper.CodecHelper;

public class EntityConnectedNetworkOutput extends NetworkOutput {

	private EntityConnected entityConnected;
	
	public EntityConnectedNetworkOutput(EntityConnected entityConnected) {
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
	
	public EntityConnected getEntityConnected() {
		return entityConnected;
	}
	
	@Override
	public byte[] serialize() {
		// C1 XX XX 01 [ID ID ID ID ID ID ID ID ID ID ID ID ID ID ID ID] [MK]
		byte [] data = new byte [4 + 16 + 1];
		
		//header
		data[0] = getSizeHeader().getByteValue();
		CodecHelper.writeShort((short)data.length, data, 1);
		data[3] = getType().getId();
		
		//entity ID
		CodecHelper.writeUUID(entityConnected.getEntity().getId(), data, 4);

		//entity is current player?
		data[4 + 16] = entityConnected.isCurrentPlayer() ? (byte)1 : (byte)0;

		return data;
	}
	

}
