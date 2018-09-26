package com.sm.mmo.moba.qnfsm.simple.entity.event;

public class MovementCancelEvent extends EntityMachineEvent {

	public static final int ID = 0x03;
	
	public MovementCancelEvent() {
		this(0);
	}
	
	public MovementCancelEvent(int publisherId) {
		super(ID, publisherId);
	}

}
