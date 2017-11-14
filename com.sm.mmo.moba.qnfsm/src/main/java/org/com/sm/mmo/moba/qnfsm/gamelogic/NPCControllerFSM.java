package org.com.sm.mmo.moba.qnfsm.gamelogic;

import org.com.sm.mmo.moba.domain.Message;
import org.com.sm.mmo.moba.domain.message.EntityMovement;
import org.com.sm.mmo.moba.domain.message.EntityPosition;
import org.com.sm.mmo.moba.qnfsm.FSM;
import org.com.sm.mmo.moba.qnfsm.FSMFeederBroadcaster;
import org.com.sm.mmo.moba.qnfsm.FSMFeeder.Type;

public class NPCControllerFSM extends FSM {

	
	
	public NPCControllerFSM(FSMFeederBroadcaster broadcaster) {
		super(broadcaster);
	}

	@Override
	protected void processInput(Message msg) {
		switch(msg.getType()) {
			case ENTITY_MOVEMENT:
				processInput((EntityMovement)msg);
			break;
			default:
				//do nothing
		}
	}
	
	private void processInput(EntityMovement msg) {
		EntityPosition newPosition = new EntityPosition();
		newPosition.setEntity(msg.getEntity());
		
		long timePast = System.currentTimeMillis() - msg.getStartedMovingTimestamp();
		double fraction = timePast / (msg.getTimeToReachDestination() * 1.0d);
		newPosition.setX((long)Math.floor(msg.getSourceX() + (msg.getSourceX() - msg.getTargetX()) * fraction + 0.5d));
		newPosition.setY((long)Math.floor(msg.getSourceY() + (msg.getSourceY() - msg.getTargetY()) * fraction + 0.5d));
		
		if (fraction < 1.0d) {
			getBroadcaster().sendMessage(Type.NPC_CONTROLLER, msg);
		}
		getBroadcaster().sendMessage(Type.GAME_LOGIC, newPosition);
	}

}
