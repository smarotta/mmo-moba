package org.com.sm.mmo.moba.domain.message.network;

import org.com.sm.mmo.moba.domain.message.EntityConnected;
import org.com.sm.mmo.moba.domain.message.network.helper.CodecHelper;

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
		// C1 XX XX 01 [ID ID ID ID ID ID ID ID ID ID ID ID ID ID ID ID]
		byte [] data = new byte [4 + 16];
		
		//header
		data[0] = getSizeHeader().getByteValue();
		CodecHelper.writeShort((short)data.length, data, 1);
		data[3] = getType().getId();
		
		//entity ID
		CodecHelper.writeUUID(entityConnected.getEntity().getId(), data, 4);
		
		return data;
	}
	

}
