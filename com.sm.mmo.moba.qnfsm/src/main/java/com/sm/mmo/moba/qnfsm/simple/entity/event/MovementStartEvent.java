package com.sm.mmo.moba.qnfsm.simple.entity.event;

public class MovementStartEvent extends EntityMachineEvent {

	public static final int ID = 0x01;
	
	private int destX = 0;
	private int destY = 0;
	private int destZ = 0;
	
	private int originX = 0;
	private int originY = 0;
	private int originZ = 0;
	
	private double speed = 1;
	
	public MovementStartEvent() {
		this(0);
	}
	
	public MovementStartEvent(int publisherId) {
		super(ID, publisherId);
	}
	
	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public int getDestX() {
		return destX;
	}

	public void setDestX(int destX) {
		this.destX = destX;
	}

	public int getDestY() {
		return destY;
	}

	public void setDestY(int destY) {
		this.destY = destY;
	}

	public int getDestZ() {
		return destZ;
	}

	public void setDestZ(int destZ) {
		this.destZ = destZ;
	}

	public int getOriginX() {
		return originX;
	}

	public void setOriginX(int originX) {
		this.originX = originX;
	}

	public int getOriginY() {
		return originY;
	}

	public void setOriginY(int originY) {
		this.originY = originY;
	}

	public int getOriginZ() {
		return originZ;
	}

	public void setOriginZ(int originZ) {
		this.originZ = originZ;
	}
}
