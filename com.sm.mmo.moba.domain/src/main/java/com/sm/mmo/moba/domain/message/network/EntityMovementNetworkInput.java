package com.sm.mmo.moba.domain.message.network;

import com.sm.mmo.moba.domain.message.EntityMovement;
import com.sm.mmo.moba.domain.message.network.helper.CodecHelper;

public class EntityMovementNetworkInput extends NetworkInput {

	private EntityMovement entityMovement;
	
	public EntityMovementNetworkInput(EntityMovement entityMovement) {
		this.entityMovement = entityMovement;
	}
	
	@Override
	public SizeHeader getSizeHeader() {
		return SizeHeader.C1;
	}
	
	public MessageType getType() {
		return MessageType.ENTITY_MOVEMENT;
	}
	
	public EntityMovement getEntityMovement() {
		return entityMovement;
	}
	
	@Override
	public void deserialize(byte[] packet) {
		// C1 xx xx A1 [AG AG] [XX XX XX XX] [YY YY YY YY] [DX DX DX DX] [DY DY DY DY]
		entityMovement.setAngle(CodecHelper.readShort(packet, 4));
		entityMovement.setX(CodecHelper.readInt(packet, 4 + 2));
		entityMovement.setY(CodecHelper.readInt(packet, 4 + 2 + 4));
		entityMovement.setStartedMovingTimestamp(System.currentTimeMillis());
		entityMovement.setTargetX(CodecHelper.readInt(packet, packet.length - 8));
		entityMovement.setTargetY(CodecHelper.readInt(packet, packet.length - 4));
	}
	
}
