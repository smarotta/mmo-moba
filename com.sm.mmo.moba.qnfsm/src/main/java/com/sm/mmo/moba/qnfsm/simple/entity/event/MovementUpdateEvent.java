package com.sm.mmo.moba.qnfsm.simple.entity.event;

public class MovementUpdateEvent extends EntityMachineEvent {

	public static final int ID = 0x02;
	
	public MovementUpdateEvent() {
		this(0);
	}
	
	public MovementUpdateEvent(int publisherId) {
		super(ID, publisherId);
	}

}
