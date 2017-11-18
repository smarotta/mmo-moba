package com.sm.mmo.moba.domain;


public class Entity extends Identifiable implements Positionable {
	private int x;
	private int y;
	private short angle;
	private int race;
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public short getAngle() {
		return angle;
	}
	
	public void setAngle(short angle) {
		this.angle = angle;
	}

	public int getRace() {
		return race;
	}

	public void setRace(int race) {
		this.race = race;
	}
}
