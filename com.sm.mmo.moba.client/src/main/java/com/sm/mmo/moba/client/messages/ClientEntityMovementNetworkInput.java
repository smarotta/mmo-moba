package com.sm.mmo.moba.client.messages;

import org.com.sm.mmo.moba.domain.message.EntityMovement;
import org.com.sm.mmo.moba.domain.message.network.helper.CodecHelper;

public class ClientEntityMovementNetworkInput extends ClientEntityPositionNetworkInput {

	private EntityMovement entityMovement;

	public ClientEntityMovementNetworkInput(EntityMovement entityMovement) {
		super(entityMovement);
		this.entityMovement = entityMovement;
	}

	public EntityMovement getEntityMovement() {
		return entityMovement;
	}

	public void setEntityMovement(EntityMovement entityMovement) {
		this.entityMovement = entityMovement;
	}

	
	@Override
	public void deserialize(byte[] packet) {
		System.out.println(debug(packet));
		// C1 xx xx A1 [ID ID ID ID ID ID ID ID ID ID ID ID ID ID ID ID] [AG AG] [XX XX XX XX] [YY YY YY YY] [DX DX DX DX] [DY DY DY DY]
		super.deserialize(packet);
		entityMovement.setTargetX(CodecHelper.readInt(packet, 4 + 16 + 2 + 4 + 4));
		entityMovement.setTargetY(CodecHelper.readInt(packet, 4 + 16 + 2 + 4 + 4 + 4));
	}
	
}
