package org.com.sm.mmo.moba.domain.message;


public class EntityPosition extends EntityUpdate {
	
	private long x;
	private long y;
	private byte angle;
	
	public long getX() {
		return x;
	}

	public void setX(long x) {
		this.x = x;
	}

	public long getY() {
		return y;
	}

	public void setY(long y) {
		this.y = y;
	}

	public byte getAngle() {
		return angle;
	}

	public void setAngle(byte angle) {
		this.angle = angle;
	}	
	
	@Override
	public MessageType getType() {
		return MessageType.ENTITY_POSITION;
	}
}
