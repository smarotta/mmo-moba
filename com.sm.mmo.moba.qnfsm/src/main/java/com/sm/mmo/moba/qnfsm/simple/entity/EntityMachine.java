package com.sm.mmo.moba.qnfsm.simple.entity;

import com.sm.mmo.moba.domain.Entity;
import com.sm.mmo.moba.qnfsm.simple.EventPublisher;
import com.sm.mmo.moba.qnfsm.simple.Machine;
import com.sm.mmo.moba.qnfsm.simple.State;

public class EntityMachine extends Machine {

	private Entity entity;
		
	public EntityMachine(EventPublisher publisher, State initialState, Entity entity) {
		super(publisher, "Entity Machine", initialState);
		this.entity = entity;
	}
	
	public void updatePosition(int x, int y, int z) {
		entity.setX(x);
		entity.setY(y);
		entity.setZ(z);
	}

	@Override
	public int getConsumerId() {
		return 0xC00001;
	}

	public Entity getEntity() {
		return entity;
	}
	
	
}
