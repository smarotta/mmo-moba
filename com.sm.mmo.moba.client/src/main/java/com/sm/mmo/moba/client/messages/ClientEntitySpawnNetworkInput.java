package com.sm.mmo.moba.client.messages;

import com.sm.mmo.moba.domain.Entity;
import com.sm.mmo.moba.domain.message.EntitySpawn;
import com.sm.mmo.moba.domain.message.network.NetworkInput;
import com.sm.mmo.moba.domain.message.network.helper.CodecHelper;

public class ClientEntitySpawnNetworkInput extends NetworkInput {

	private EntitySpawn entitySpawn;
	
	public ClientEntitySpawnNetworkInput(EntitySpawn entitySpawn) {
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
	public void deserialize(byte[] packet) {
		// C1 xx xx A0 [ID ID ID ID ID ID ID ID ID ID ID ID ID ID ID ID] [AG AG] [XX XX XX XX] [YY YY YY YY] [RC RC RC RC]
		entitySpawn.setEntity(new Entity());
		entitySpawn.getEntity().setId(CodecHelper.readUUID(packet, 4));
		entitySpawn.setAngle(CodecHelper.readShort(packet, 4 + 16));
		entitySpawn.setX(CodecHelper.readInt(packet, 4 + 16 + 2));
		entitySpawn.setY(CodecHelper.readInt(packet, 4 + 16 + 2 + 4));
		entitySpawn.setRace(CodecHelper.readInt(packet, 4 + 16 + 2 + 4));
	}


}
