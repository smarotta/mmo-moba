package org.com.sm.mmo.moba.domain;

import java.util.List;

public class Entity extends Identifiable {

	private List<Message> messages;
	private int x;
	private int y;
	private short angle;
	
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
}
