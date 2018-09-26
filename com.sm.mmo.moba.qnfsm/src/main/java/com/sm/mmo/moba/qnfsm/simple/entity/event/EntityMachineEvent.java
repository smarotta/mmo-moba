package com.sm.mmo.moba.qnfsm.simple.entity.event;

import com.sm.mmo.moba.qnfsm.simple.Event;

public class EntityMachineEvent extends Event {

	public EntityMachineEvent() {
		this(0, 0);
	}
	
	public EntityMachineEvent(int id, int publisherId) {
		super(id, 0xC00001, publisherId);
	}

}
