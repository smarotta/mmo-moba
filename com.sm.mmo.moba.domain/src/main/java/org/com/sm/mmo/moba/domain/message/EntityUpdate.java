package org.com.sm.mmo.moba.domain.message;

import org.com.sm.mmo.moba.domain.Entity;
import org.com.sm.mmo.moba.domain.Message;

public abstract class EntityUpdate extends Message {

	private Entity entity;

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
}
