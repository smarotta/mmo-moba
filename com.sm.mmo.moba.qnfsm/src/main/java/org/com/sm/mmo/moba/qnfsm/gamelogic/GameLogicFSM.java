package org.com.sm.mmo.moba.qnfsm.gamelogic;

import java.util.HashSet;
import java.util.Set;

import org.com.sm.mmo.moba.domain.Entity;
import org.com.sm.mmo.moba.domain.Message;
import org.com.sm.mmo.moba.domain.message.EntityPosition;
import org.com.sm.mmo.moba.domain.message.network.EntityPositionNetworkOutput;
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
			case ENTITY_POSITION:
				processInput((EntityPosition)msg);
			break;
			default:
				//do nothing
		}
	}
	
	private void processInput(EntityPosition msg) {
		if (!entities.contains(msg.getEntity())) {
			entities.add(msg.getEntity());
		}
		msg.getEntity().setX(msg.getX());
		msg.getEntity().setY(msg.getY());
		
		for(Entity target:entities) {
			EntityPositionNetworkOutput newMsg = new EntityPositionNetworkOutput(msg);
			newMsg.setDestination(target);
			sendMessage(Type.FSM_NETWORK, newMsg);
		}
	}
}
