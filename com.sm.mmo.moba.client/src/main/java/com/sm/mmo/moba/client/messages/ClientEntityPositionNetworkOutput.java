package com.sm.mmo.moba.client.messages;

import org.com.sm.mmo.moba.domain.message.EntityPosition;
import org.com.sm.mmo.moba.domain.message.network.EntityPositionNetworkOutput;
import org.com.sm.mmo.moba.domain.message.network.helper.CodecHelper;

public class ClientEntityPositionNetworkOutput extends EntityPositionNetworkOutput {

	private EntityPosition entityPosition;
	
	public ClientEntityPositionNetworkOutput(EntityPosition entityPosition) {
		super(entityPosition);
		this.entityPosition = entityPosition;
	}

	@Override
	public byte[] serialize() {
		// C1 xx xx A2 [AG AG] [XX XX XX XX] [YY YY YY YY]
		byte [] data = null;
		
		data = new byte [4 + 2 + 4 + 4];
		
		//header
		data[0] = getSizeHeader().getByteValue();
		CodecHelper.writeShort((short)data.length, data, 1);
		data[3] = getType().getId();
		
		//entity angle
		CodecHelper.writeShort((short)entityPosition.getAngle(), data, 4);
		
		//entity X
		CodecHelper.writeInt(entityPosition.getX(), data, 4 + 2);
		
		//entity Y
		CodecHelper.writeInt(entityPosition.getY(), data, 4 + 2 + 4);
		
		return data;
	}
}
