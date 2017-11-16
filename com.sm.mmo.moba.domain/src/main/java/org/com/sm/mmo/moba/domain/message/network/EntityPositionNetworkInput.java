package org.com.sm.mmo.moba.domain.message.network;

import org.com.sm.mmo.moba.domain.Entity;
import org.com.sm.mmo.moba.domain.message.EntityPosition;
import org.com.sm.mmo.moba.domain.message.network.helper.CodecHelper;

public class EntityPositionNetworkInput extends NetworkInput {

	private EntityPosition entityPosition;
	
	public EntityPositionNetworkInput(EntityPosition entityPosition) {
		this.entityPosition = entityPosition;
	}
	
	@Override
	public SizeHeader getSizeHeader() {
		return SizeHeader.C1;
	}

	@Override
	public MessageType getType() {
		return entityPosition.getType();
	}

	public EntityPosition getEntityPosition() {
		return entityPosition;
	}
	
	@Override
	public void deserialize(byte[] packet) {
		// C1 xx xx A2 [AG AG] [XX XX XX XX] [YY YY YY YY]
		entityPosition.setAngle(CodecHelper.readShort(packet, 4));
		entityPosition.setX(CodecHelper.readInt(packet, 4 + 2));
		entityPosition.setY(CodecHelper.readInt(packet, 4 + 2 + 4));
	}
	
}
