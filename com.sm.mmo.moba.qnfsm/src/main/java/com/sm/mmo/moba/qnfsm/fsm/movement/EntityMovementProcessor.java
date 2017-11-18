package com.sm.mmo.moba.qnfsm.fsm.movement;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Map;
import java.util.UUID;

import com.sm.mmo.moba.domain.Entity;
import com.sm.mmo.moba.domain.message.EntityMovement;
import com.sm.mmo.moba.domain.message.EntityPosition;
import com.sm.mmo.moba.domain.tree.QuadTree;
import com.sm.mmo.moba.qnfsm.FSM;
import com.sm.mmo.moba.qnfsm.FSMFeeder.Type;

public class EntityMovementProcessor extends AbstractSpatialProcessor {

	public EntityMovementProcessor(Rectangle mapBoundary, Dimension proximityDimension) {
		super(mapBoundary, proximityDimension);
	}

	private void calculateSpeed(EntityMovement msg) {
		if (msg.getTimeToReachDestination() == 0l) {
			int distance = (int) (Math.hypot(msg.getX() - msg.getTargetX(), msg.getY() - msg.getTargetY()) + 0.5d);
			double speed = 100.0d / 1000.0d; //100 units of distance are covered in 1000ms
			long timeToReach = (long)(distance / speed + 0.5d);
			System.out.println("Time to reach a distance of " + distance + " in a speed of " + speed*1000l + "pxl/s takes " + timeToReach + "ms");
			msg.setTimeToReachDestination(timeToReach);
		}
	}
	
	public void process(FSM fsm, QuadTree<Entity> entityTree, Map<UUID, EntityMovement> movingEntities, EntityMovement msg) {
		calculateSpeed(msg);
		
		if (movingEntities.containsKey(msg.getEntity().getId())) {
			if (movingEntities.get(msg.getEntity().getId()) != msg) {
				movingEntities.get(msg.getEntity().getId()).setDiscarted(true);
				movingEntities.put(msg.getEntity().getId(), msg);
			}
		} else {
			movingEntities.put(msg.getEntity().getId(), msg);
		}
		
		EntityPosition newPosition = new EntityPosition();
		newPosition.setEntity(msg.getEntity());
		double fraction = 0.0d;
		int x;
		int y;
		if (msg.isExpiredMovement()) {
			x = msg.getTargetX();
			y = msg.getTargetY();
			fraction = 1.0d;
			movingEntities.remove(msg.getEntity().getId());
			//sendMessage(Type.FSM_GAME_LOGIC, newPosition);
		} else {
			long timePast = System.currentTimeMillis() - msg.getStartedMovingTimestamp();
			fraction = timePast / (msg.getTimeToReachDestination() * 1.0d);
			int xdistance = msg.getTargetX() - msg.getX();
			int ydistance = msg.getTargetY() - msg.getY();
			
			x = (int) (msg.getX() + (xdistance * fraction) + 0.5d);
			y = (int) (msg.getY() + (ydistance * fraction) + 0.5d);
		}
		newPosition.setX(x);
		newPosition.setY(y);
		
		//let the engine know about the new position
		//sendMessage(Type.FSM_GAME_LOGIC, newPosition);
		if (fraction < 1.0d) {
			fsm.sendMessage(Type.FSM_MOVEMENT, msg, System.currentTimeMillis() + 100l);
		}
		fsm.sendMessage(Type.FSM_MOVEMENT, newPosition);
		
		System.out.println("New position during movement [" + newPosition.getX() + ", " + newPosition.getY() + "]");
	}
}
