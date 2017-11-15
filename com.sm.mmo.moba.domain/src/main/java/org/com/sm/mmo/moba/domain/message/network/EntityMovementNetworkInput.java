package org.com.sm.mmo.moba.domain.message.network;

import org.com.sm.mmo.moba.domain.message.EntityMovement;
import org.com.sm.mmo.moba.domain.message.network.helper.CodecHelper;

public class EntityMovementNetworkInput extends EntityPositionNetworkInput {

	private EntityMovement entityMovement;
	
	public EntityMovementNetworkInput(EntityMovement entityMovement) {
		super(entityMovement);
		this.entityMovement = entityMovement;
	}
	
	@Override
	public SizeHeader getSizeHeader() {
		return SizeHeader.C1;
	}
	
	public MessageType getType() {
		return entityMovement.getType();
	}
	
	public EntityMovement getEntityMovement() {
		return entityMovement;
	}
	
	@Override
	public void deserialize(byte[] packet) {
		// C1 xx xx A1 [AG AG] [XX XX XX XX] [YY YY YY YY] [DX DX DX DX] [DY DY DY DY]
		super.deserialize(packet);
		entityMovement.setStartedMovingTimestamp(System.currentTimeMillis());
		entityMovement.setTargetX(CodecHelper.readInt(packet, packet.length - 8));
		entityMovement.setTargetY(CodecHelper.readInt(packet, packet.length - 4));
	}
	
}
