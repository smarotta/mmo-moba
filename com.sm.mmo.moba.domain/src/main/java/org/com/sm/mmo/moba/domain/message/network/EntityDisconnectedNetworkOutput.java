package org.com.sm.mmo.moba.domain.message.network;

import org.com.sm.mmo.moba.domain.message.EntityDisconnected;
import org.com.sm.mmo.moba.domain.message.network.helper.CodecHelper;

public class EntityDisconnectedNetworkOutput extends NetworkOutput {

	private EntityDisconnected entityDisconnected;
	
	public EntityDisconnectedNetworkOutput(EntityDisconnected entityDisconnected) {
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
	
	public EntityDisconnected getEntityDisconnected() {
		return entityDisconnected;
	}
	
	@Override
	public byte[] serialize() {
		// C1 XX XX 02 [ID ID ID ID ID ID ID ID ID ID ID ID ID ID ID ID]
		byte [] data = new byte [4 + 16];
		
		//header
		data[0] = getSizeHeader().getByteValue();
		CodecHelper.writeShort((short)data.length, data, 1);
		data[3] = getType().getId();
		
		//entity ID
		CodecHelper.writeUUID(entityDisconnected.getEntity().getId(), data, 4);
		
		return data;
	}
	

}
