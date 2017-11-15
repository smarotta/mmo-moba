package org.com.sm.mmo.moba.qnfsm.gamelogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.com.sm.mmo.moba.domain.Entity;
import org.com.sm.mmo.moba.domain.Message;
import org.com.sm.mmo.moba.domain.message.EntityMovement;
import org.com.sm.mmo.moba.domain.message.EntityPosition;
import org.com.sm.mmo.moba.domain.message.EntityUpdate;
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
	
	private Map<Entity, EntityMovement> movingEntities = new HashMap<Entity, EntityMovement>();
	
	public EntityMovementFSM(FSMFeederBroadcaster broadcaster) {
		super(broadcaster);
		scheduleCleanup();
	}
	
	private void scheduleCleanup() {
		sendMessage(Type.FSM_ENTITY_MOVEMENT, cleanupMessage, System.currentTimeMillis() + 5000l);
	}

	@Override
	protected void processInput(Message msg) {
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
	
	private void processInput(EntityMovement msg) {
		if (movingEntities.containsKey(msg.getEntity())) {
			movingEntities.get(msg.getEntity()).setDiscarted(true);
		}
		
		EntityPosition newPosition = new EntityPosition();
		newPosition.setEntity(msg.getEntity());
		double fraction;
		int x;
		int y;
		if (msg.isExpiredMovement()) {
			x = msg.getTargetX();
			y = msg.getTargetY();
			fraction = 1.0d;
		} else {
			long timePast = System.currentTimeMillis() - msg.getStartedMovingTimestamp();
			fraction = timePast / (msg.getTimeToReachDestination() * 1.0d);
			x = (int) Math.floor(msg.getX() + (msg.getX() - msg.getTargetX()) * fraction + 0.5d);
			y = (int) Math.floor(msg.getY() + (msg.getY() - msg.getTargetY()) * fraction + 0.5d);
		}
		newPosition.setX(x);
		newPosition.setY(y);
		
		//let the engine know about the new position
		sendMessage(Type.FSM_GAME_LOGIC, newPosition);
		if (fraction < 1.0d && !msg.isExpiredMovement()) {
			movingEntities.put(msg.getEntity(), msg);
			sendMessage(Type.FSM_ENTITY_MOVEMENT, msg, System.currentTimeMillis() + 100l);
		}
	}

}
