package com.sm.mmo.moba.client.messages;

import com.sm.mmo.moba.domain.Entity;
import com.sm.mmo.moba.domain.message.EntityMovement;
import com.sm.mmo.moba.domain.message.network.EntityMovementNetworkInput;
import com.sm.mmo.moba.domain.message.network.helper.CodecHelper;

public class ClientEntityMovementNetworkInput extends EntityMovementNetworkInput {

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
		// C1 xx xx A1 [ID ID ID ID ID ID ID ID ID ID ID ID ID ID ID ID] [AG AG] [XX XX XX XX] [YY YY YY YY] [DX DX DX DX] [DY DY DY DY]
		entityMovement.setEntity(new Entity());
		entityMovement.getEntity().setId(CodecHelper.readUUID(packet, 4));
		entityMovement.setAngle(CodecHelper.readShort(packet, 4 + 16));
		entityMovement.setX(CodecHelper.readInt(packet, 4 + 16 + 2));
		entityMovement.setY(CodecHelper.readInt(packet, 4 + 16 + 2 + 4));
		entityMovement.setTargetX(CodecHelper.readInt(packet, 4 + 16 + 2 + 4 + 4));
		entityMovement.setTargetY(CodecHelper.readInt(packet, 4 + 16 + 2 + 4 + 4 + 4));
	}
	
}
