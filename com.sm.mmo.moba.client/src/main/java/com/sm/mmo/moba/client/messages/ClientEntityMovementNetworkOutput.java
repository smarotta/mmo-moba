package com.sm.mmo.moba.client.messages;

import java.util.Arrays;

import org.com.sm.mmo.moba.domain.message.EntityMovement;
import org.com.sm.mmo.moba.domain.message.network.helper.CodecHelper;

public class ClientEntityMovementNetworkOutput extends ClientEntityPositionNetworkOutput {

	private EntityMovement entityMovement;
	
	public ClientEntityMovementNetworkOutput(EntityMovement entityMovement) {
		super(entityMovement);
		this.entityMovement = entityMovement;
	}

	@Override
	public byte[] serialize() {
		// C1 xx xx A1 [AG AG] [XX XX XX XX] [YY YY YY YY] [DX DX DX DX] [DY DY DY DY]
		byte [] data = null;
		byte [] positionData = super.serialize();
		data = Arrays.copyOf(positionData, positionData.length + 4 + 4);
		
		//new size
		CodecHelper.writeShort((short)data.length, data, 1);
		
		//entity DX
		CodecHelper.writeInt(entityMovement.getTargetX(), data, positionData.length);
		
		//entity DY
		CodecHelper.writeInt(entityMovement.getTargetY(), data, positionData.length + 4);
				
		return data;
	}
}
