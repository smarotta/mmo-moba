package org.com.sm.mmo.moba.qnfsm.fsm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.com.sm.mmo.moba.domain.Entity;
import org.com.sm.mmo.moba.domain.Message;
import org.com.sm.mmo.moba.domain.message.EntityMovement;
import org.com.sm.mmo.moba.domain.message.EntityPosition;
import org.com.sm.mmo.moba.domain.message.EntityUpdate;
import org.com.sm.mmo.moba.domain.message.network.EntityMovementNetworkInput;
import org.com.sm.mmo.moba.qnfsm.FSM;
import org.com.sm.mmo.moba.qnfsm.FSMFeederBroadcaster;
import org.com.sm.mmo.moba.qnfsm.FSMFeeder.Type;

public class EntityMovementFSM extends FSM {

	private static class EntityMovementsCleanup extends EntityUpdate {
		@Override
		public MessageType getType() {
			return MessageType.INTERNAL;
		}
		
	}
	
	private final EntityMovementsCleanup cleanupMessage = new EntityMovementsCleanup();
	
	private Map<UUID, EntityMovement> movingEntities = new HashMap<UUID, EntityMovement>();
	
	public EntityMovementFSM(FSMFeederBroadcaster broadcaster) {
		super(broadcaster);
		scheduleCleanup();
	}
	
	private void scheduleCleanup() {
		//sendMessage(Type.FSM_ENTITY_MOVEMENT, cleanupMessage, System.currentTimeMillis() + 5000l);
	}

	@Override
	protected void processInput(Message msg) {
		//System.out.println("EntityMovementFSM: Recieved " + msg.getClass() + " type:" + msg.getType());
		switch(msg.getType()) {
			case INTERNAL:
				removeExpiredMovements();
			break;
			case ENTITY_MOVEMENT:
				processInput((EntityMovement)msg);
			break;
			case ENTITY_POSITION:
				sendMessage(Type.FSM_GAME_LOGIC, msg);
			break;
			default:
				//do nothing
		}
	}
	
	private void removeExpiredMovements() {
		movingEntities.entrySet().removeIf(
				entry -> entry.getValue().isExpiredMovement());
		scheduleCleanup();
	}
	
	//calculate movement speed (should be done at the GameLogicFSM
	private void calculateSpeed(EntityMovement msg) {
		if (msg.getTimeToReachDestination() == 0l) {
			int distance = (int) (Math.hypot(msg.getX() - msg.getTargetX(), msg.getY() - msg.getTargetY()) + 0.5d);
			double speed = 100.0d / 1000.0d; //100 units of distance are covered in 1000ms
			long timeToReach = (long)(distance / speed + 0.5d);
			//System.out.println("Time to reach a distance of " + distance + " in a speed of " + speed*1000l + "pxl/s takes " + timeToReach + "ms");
			msg.setTimeToReachDestination(timeToReach);
		}
	}
	
	private void processInput(EntityMovement msg) {
		calculateSpeed(msg);
		
		boolean currentMovementStartedProcessing = false;
		if (movingEntities.containsKey(msg.getEntity().getId())) {
			if (movingEntities.get(msg.getEntity().getId()) != msg) {
				movingEntities.get(msg.getEntity().getId()).setDiscarted(true);
			} else {
				currentMovementStartedProcessing = true;
			}
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
		sendMessage(Type.FSM_GAME_LOGIC, newPosition);
		if (fraction < 1.0d) {
			movingEntities.put(msg.getEntity().getId(), msg);
			sendMessage(Type.FSM_ENTITY_MOVEMENT, msg, System.currentTimeMillis() + 100l);
			if (!currentMovementStartedProcessing) {
				sendMessage(Type.FSM_GAME_LOGIC, msg);
			}
		}
	}

}
