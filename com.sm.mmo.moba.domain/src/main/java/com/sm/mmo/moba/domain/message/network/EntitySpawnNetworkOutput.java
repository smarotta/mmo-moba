package com.sm.mmo.moba.domain.message.network;

import com.sm.mmo.moba.domain.message.EntitySpawn;
import com.sm.mmo.moba.domain.message.network.helper.CodecHelper;

public class EntitySpawnNetworkOutput extends NetworkOutput {

	private EntitySpawn entitySpawn;
	
	public EntitySpawnNetworkOutput(EntitySpawn entitySpawn) {
		this.entitySpawn = entitySpawn;
	}
	
	public EntitySpawn getEntitySpawn() {
		return entitySpawn;
	}

	public void setEntitySpawn(EntitySpawn entitySpawn) {
		this.entitySpawn = entitySpawn;
	}

	@Override
	public SizeHeader getSizeHeader() {
		return SizeHeader.C1;
	}

	@Override
	public MessageType getType() {
		return MessageType.ENTITY_SPAWN;
	}

	@Override
	public byte[] serialize() {
		// C1 xx xx A0 [ID ID ID ID ID ID ID ID ID ID ID ID ID ID ID ID] [AG AG] [XX XX XX XX] [YY YY YY YY] [RC RC RC RC]
		byte [] data = null;
		
		data = new byte [4 + 16 + 2 + 4 + 4 + 4];
		
		//header
		data[0] = getSizeHeader().getByteValue();
		CodecHelper.writeShort((short)data.length, data, 1);
		data[3] = getType().getId();
		
		//entity ID
		CodecHelper.writeUUID(entitySpawn.getEntity().getId(), data, 4);
		
		//entity angle
		CodecHelper.writeShort((short)entitySpawn.getAngle(), data, 4 + 16);
				
		//entity X
		CodecHelper.writeInt(entitySpawn.getX(), data, 4 + 16 + 2);
		
		//entity Y
		CodecHelper.writeInt(entitySpawn.getY(), data, 4 + 16 + 2 + 4);
		
		//entity Race
		CodecHelper.writeInt(entitySpawn.getRace(), data, 4 + 16 + 2 + 4 + 4);
				
		return data;
	}

}
