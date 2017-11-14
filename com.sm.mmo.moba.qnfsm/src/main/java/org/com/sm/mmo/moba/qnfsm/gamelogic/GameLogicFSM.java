package org.com.sm.mmo.moba.qnfsm.gamelogic;

import java.util.HashSet;
import java.util.Set;

import org.com.sm.mmo.moba.domain.Entity;
import org.com.sm.mmo.moba.domain.Message;
import org.com.sm.mmo.moba.domain.message.EntityMovement;
import org.com.sm.mmo.moba.domain.message.EntityPosition;
import org.com.sm.mmo.moba.domain.message.EntityPositionNetwork;
import org.com.sm.mmo.moba.qnfsm.FSM;
import org.com.sm.mmo.moba.qnfsm.FSMFeeder.Type;
import org.com.sm.mmo.moba.qnfsm.FSMFeederBroadcaster;

public class GameLogicFSM extends FSM {

	private Set<Entity> entities = new HashSet<Entity>();
	
	public GameLogicFSM(FSMFeederBroadcaster broadcaster) {
		super(broadcaster);
	}

	@Override
	protected void processInput(Message msg) {
		switch(msg.getType()) {
			case ENTITY_MOVEMENT:
				processInput((EntityMovement)msg);
			break;
			case ENTITY_POSITION:
				processInput((EntityPosition)msg);
			break;
			default:
				//do nothing
		}
	}

	private void processInput(EntityMovement msg) {
		getBroadcaster().sendMessage(Type.NPC_CONTROLLER, msg);
	}
	
	private void processInput(EntityPosition msg) {
		if (!entities.contains(msg.getEntity())) {
			entities.add(msg.getEntity());
		}
		for(Entity target:entities) {
			if (target != msg.getEntity()) {
				EntityPositionNetwork newMsg = new EntityPositionNetwork();
				newMsg.setAngle(msg.getAngle());
				newMsg.setEntity(msg.getEntity());
				newMsg.setEntityDestination(target);
				newMsg.setX(msg.getX());
				newMsg.setY(msg.getY());
				getBroadcaster().sendMessage(Type.NETWORK, newMsg);
			}
		}
	}
}
