package org.com.sm.mmo.moba.domain.message.network;

import org.com.sm.mmo.moba.domain.message.EntityPosition;
import org.com.sm.mmo.moba.domain.message.network.helper.CodecHelper;

public class EntityPositionNetworkOutput extends NetworkOutput {
	
	private EntityPosition entityPosition;

	public EntityPositionNetworkOutput(EntityPosition entityPosition) {
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
	public byte[] serialize() {
		// C1 xx xx A2 [ID ID ID ID ID ID ID ID ID ID ID ID ID ID ID ID] [AG AG] [XX XX XX XX] [YY YY YY YY]
		byte [] data = null;
		
		data = new byte [4 + 16 + 1 + 4 + 4];
		
		//header
		data[0] = getSizeHeader().getByteValue();
		CodecHelper.writeShort((short)data.length, data, 1);
		data[3] = getType().getId();
		
		//entity ID
		CodecHelper.writeUUID(entityPosition.getEntity().getId(), data, 4);
		
		//entity angle
		CodecHelper.writeShort((short)entityPosition.getAngle(), data, 4 + 16);
		
		//entity X
		CodecHelper.writeInt(entityPosition.getX(), data, 4 + 16 + 2 + 4);
		
		//entity Y
		CodecHelper.writeInt(entityPosition.getY(), data, 4 + 16 + 2 + 4 + 4);
		
		return data;
	}

}
