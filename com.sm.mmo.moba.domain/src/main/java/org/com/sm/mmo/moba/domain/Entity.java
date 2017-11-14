package org.com.sm.mmo.moba.domain;

public class Entity extends Identifiable {

	private int x;
	private int y;
	private byte angle;
	
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
	
	public byte getAngle() {
		return angle;
	}
	
	public void setAngle(byte angle) {
		this.angle = angle;
	}
}
