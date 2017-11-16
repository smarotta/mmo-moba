package com.sm.mmo.moba.client.messages;

import org.com.sm.mmo.moba.domain.Entity;
import org.com.sm.mmo.moba.domain.message.EntityPosition;
import org.com.sm.mmo.moba.domain.message.network.EntityPositionNetworkInput;
import org.com.sm.mmo.moba.domain.message.network.EntityPositionNetworkOutput;
import org.com.sm.mmo.moba.domain.message.network.helper.CodecHelper;

public class ClientEntityPositionNetworkInput extends EntityPositionNetworkInput {

	private EntityPosition entityPosition;
	
	public ClientEntityPositionNetworkInput(EntityPosition entityPosition) {
		super(entityPosition);
		this.entityPosition = entityPosition;
	}

	@Override
	public void deserialize(byte[] packet) {
		// C1 xx xx A2 [ID ID ID ID ID ID ID ID ID ID ID ID ID ID ID ID] [AG AG] [XX XX XX XX] [YY YY YY YY]
		entityPosition.setEntity(new Entity());
		entityPosition.getEntity().setId(CodecHelper.readUUID(packet, 4));
		entityPosition.setAngle(CodecHelper.readShort(packet, 4 + 16));
		entityPosition.setX(CodecHelper.readInt(packet, 4 + 16 + 2));
		entityPosition.setY(CodecHelper.readInt(packet, 4 + 16 + 2 + 4));
	}

}
