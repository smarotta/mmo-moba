package org.com.sm.mmo.moba.domain.message.network;

import java.util.Arrays;

import org.com.sm.mmo.moba.domain.message.EntityMovement;
import org.com.sm.mmo.moba.domain.message.network.helper.CodecHelper;

public class EntityMovementNetworkOutput extends NetworkOutput {
	
	private EntityMovement entityMovement;
	
	public EntityMovementNetworkOutput(EntityMovement entityMovement) {
		this.entityMovement = entityMovement;
	}

	@Override
	public SizeHeader getSizeHeader() {
		return SizeHeader.C1;
	}
	
	@Override
	public MessageType getType() {
		return MessageType.ENTITY_MOVEMENT;
	}
	
	public EntityMovement getEntityMovement() {
		return entityMovement;
	}
	
	@Override
	public byte[] serialize() {
		// C1 xx xx A1 [ID ID ID ID ID ID ID ID ID ID ID ID ID ID ID ID] [AG AG] [XX XX XX XX] [YY YY YY YY] [DX DX DX DX] [DY DY DY DY]
		byte [] data = null;
		
		data = new byte [4 + 16 + 2 + 4 + 4 + 4 + 4];
		
		//header
		data[0] = getSizeHeader().getByteValue();
		CodecHelper.writeShort((short)data.length, data, 1);
		data[3] = getType().getId();
		
		//entity ID
		CodecHelper.writeUUID(entityMovement.getEntity().getId(), data, 4);
		
		//entity angle
		CodecHelper.writeShort((short)entityMovement.getAngle(), data, 4 + 16);
				
		//entity X
		CodecHelper.writeInt(entityMovement.getX(), data, 4 + 16 + 2);
		
		//entity Y
		CodecHelper.writeInt(entityMovement.getY(), data, 4 + 16 + 2 + 4);
		
		//entity DX
		CodecHelper.writeInt(entityMovement.getTargetX(), data, 4 + 16 + 2 + 4 + 4);
		
		//entity DY
		CodecHelper.writeInt(entityMovement.getTargetY(), data, 4 + 16 + 2 + 4 + 4 + 4);
				
		return data;
	}

}
