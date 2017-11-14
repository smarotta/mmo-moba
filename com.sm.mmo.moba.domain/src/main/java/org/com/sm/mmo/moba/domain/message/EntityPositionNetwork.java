package org.com.sm.mmo.moba.domain.message;

import org.com.sm.mmo.moba.domain.Entity;

public class EntityPositionNetwork extends EntityPosition implements NetworkMessage {
	
	private Entity entityDestination;
	
	@Override
	public Entity getEntityDestination() {
		return entityDestination;
	}
	
	public void setEntityDestination(Entity entityDestionation) {
		this.entityDestination = entityDestionation;
	}

}
