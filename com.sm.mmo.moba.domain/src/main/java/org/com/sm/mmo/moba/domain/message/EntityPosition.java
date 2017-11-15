package org.com.sm.mmo.moba.domain.message;


public class EntityPosition extends EntityUpdate {
	
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
	
	@Override
	public MessageType getType() {
		return MessageType.ENTITY_POSITION;
	}
}
