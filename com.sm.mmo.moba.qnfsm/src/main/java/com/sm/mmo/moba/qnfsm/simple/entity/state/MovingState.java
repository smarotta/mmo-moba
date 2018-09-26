package com.sm.mmo.moba.qnfsm.simple.entity.state;

import java.util.HashSet;
import java.util.Set;

import com.sm.mmo.moba.qnfsm.simple.Event;
import com.sm.mmo.moba.qnfsm.simple.EventPublisher;
import com.sm.mmo.moba.qnfsm.simple.Machine;
import com.sm.mmo.moba.qnfsm.simple.State;
import com.sm.mmo.moba.qnfsm.simple.entity.EntityMachine;
import com.sm.mmo.moba.qnfsm.simple.entity.event.MovementCancelEvent;
import com.sm.mmo.moba.qnfsm.simple.entity.event.MovementStartEvent;
import com.sm.mmo.moba.qnfsm.simple.entity.event.MovementUpdateEvent;

public class MovingState extends State {

	@SuppressWarnings("serial")
	private static final Set<Integer> CONSUMABLES = new HashSet<Integer>() {{
		add(MovementUpdateEvent.ID);
		add(MovementCancelEvent.ID);
	}};
	
	private long createdAtTimestamp;
	
	private int destX = 0;
	private int destY = 0;
	private int destZ = 0;
	private int originX = 0;
	private int originY = 0;
	private int originZ = 0;
	private double speed = 0;
	
	public MovingState(MovementStartEvent startEvent) {
		initialize(startEvent);
	}
	
	private void initialize(MovementStartEvent startEvent) {
		createdAtTimestamp = System.currentTimeMillis();
		this.destX = startEvent.getDestX();
		this.destY = startEvent.getDestY();
		this.destZ = startEvent.getDestZ();
		this.originX = startEvent.getOriginX();
		this.originY = startEvent.getOriginY();
		this.originZ = startEvent.getOriginZ();
		this.speed = startEvent.getSpeed();
	}
	
	@Override
	public boolean canConsume(Event event) {
		return CONSUMABLES.contains(event.getId());
	}

	private long getElapsedTime() {
		return System.currentTimeMillis() - createdAtTimestamp;
	}
	
	private boolean isAtDestination(int dx, int dy, int dz) {
		boolean isAtXDst = destX > originX ? dx >= destX : dx <= destX;
		boolean isAtYDst = destY > originY ? dy >= destY : dy <= destY;
		boolean isAtZDst = destZ > originZ ? dz >= destZ : dz <= destZ;
		return isAtXDst && isAtYDst && isAtZDst;
	}
	
	private double distance(double x1, double y1, double z1, double x2, double y2, double z2) {
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2));
	}
	
	private State consume(EventPublisher publisher, EntityMachine target, MovementUpdateEvent event, long elapsedTime) {
		//distance in pixels
		/*
		double dx = destX - originX;
		double dy = destY - originY;
		double dz = destZ - originZ;
		double length = Math.sqrt(dx*dx + dy*dy);
		dx = dx / length * speed * (elapsedTime / 1000.0d);
		dy = dy / length * speed * (elapsedTime / 1000.0d);
		*/
		
		double distance = distance(originX, originY, originZ, destX, destY, destZ);
		
		double dx = originX + ((destX - originX)/distance * speed * (elapsedTime / 1000.0d));
		double dy = originY + ((destY - originY)/distance * speed * (elapsedTime / 1000.0d));
		double dz = originZ + ((destZ - originZ)/distance * speed * (elapsedTime / 1000.0d));
		
		if (isAtDestination((int)dx, (int)dy, (int)dz)) {
			target.updatePosition(destX, destY, destZ);
			return new IdleState();
		} else {
			target.updatePosition((int)dx, (int)dy, (int)dz);
			return this;
		}
	}

	@Override
	public State consume(EventPublisher publisher, Machine machine, Event event) {
		switch(event.getId()) {
			case MovementUpdateEvent.ID:
				return this.consume(publisher, (EntityMachine) machine, (MovementUpdateEvent) event, getElapsedTime());
			case MovementCancelEvent.ID:
				return new IdleState();
			default:
				return this;
		}
	}
	
}
