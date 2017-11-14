package org.com.sm.mmo.moba.domain.message;

public class EntityMovement extends EntityUpdate {

	private long startedMovingTimestamp;
	private long timeToReachDestination;
	private int sourceX;
	private int sourceY;
	private int targetX;
	private int targetY;
	
	public long getTimeToReachDestination() {
		return timeToReachDestination;
	}

	public void setTimeToReachDestination(long timeToReachDestination) {
		this.timeToReachDestination = timeToReachDestination;
	}

	public int getSourceX() {
		return sourceX;
	}
	
	public void setSourceX(int sourceX) {
		this.sourceX = sourceX;
	}
	
	public int getSourceY() {
		return sourceY;
	}
	
	public void setSourceY(int sourceY) {
		this.sourceY = sourceY;
	}
	
	public int getTargetX() {
		return targetX;
	}
	
	public void setTargetX(int targetX) {
		this.targetX = targetX;
	}
	
	public int getTargetY() {
		return targetY;
	}
	
	public void setTargetY(int targetY) {
		this.targetY = targetY;
	}
	
	public long getStartedMovingTimestamp() {
		return startedMovingTimestamp;
	}

	public void setStartedMovingTimestamp(long startedMovingTimestamp) {
		this.startedMovingTimestamp = startedMovingTimestamp;
	}

	@Override
	public MessageType getType() {
		return MessageType.ENTITY_MOVEMENT;
	}
	
}
