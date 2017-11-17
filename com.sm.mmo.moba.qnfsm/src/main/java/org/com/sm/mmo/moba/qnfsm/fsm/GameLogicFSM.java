package org.com.sm.mmo.moba.qnfsm.fsm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.com.sm.mmo.moba.domain.Entity;
import org.com.sm.mmo.moba.domain.Message;
import org.com.sm.mmo.moba.domain.message.EntityConnected;
import org.com.sm.mmo.moba.domain.message.EntityDisconnected;
import org.com.sm.mmo.moba.domain.message.EntityMovement;
import org.com.sm.mmo.moba.domain.message.EntityPosition;
import org.com.sm.mmo.moba.domain.message.network.EntityConnectedNetworkOutput;
import org.com.sm.mmo.moba.domain.message.network.EntityDisconnectedNetworkOutput;
import org.com.sm.mmo.moba.domain.message.network.EntityMovementNetworkOutput;
import org.com.sm.mmo.moba.domain.message.network.EntityPositionNetworkOutput;
import org.com.sm.mmo.moba.qnfsm.FSM;
import org.com.sm.mmo.moba.qnfsm.FSMFeeder.Type;
import org.com.sm.mmo.moba.qnfsm.FSMFeederBroadcaster;

public class GameLogicFSM extends FSM {

	private Map<UUID, Entity> entityIdMap = new HashMap<UUID, Entity>();
	
	public GameLogicFSM(FSMFeederBroadcaster broadcaster) {
		super(broadcaster);
	}

	@Override
	protected void processInput(Message msg) {
		switch(msg.getType()) {
			case ENTITY_POSITION:
				processInput((EntityPosition)msg);
			break;
			case ENTITY_MOVEMENT:
				processInput((EntityMovement)msg);
			break;
			case ENTITY_CONNECTED:
				processInput((EntityConnected)msg);
			break;
			case ENTITY_DISCONNECTED:
				processInput((EntityDisconnected)msg);
			break;
			default:
				//do nothing
		}
	}
	
	private void processInput(EntityConnected msg) {
		Entity connectedEntity = msg.getEntity();
		entityIdMap.put(connectedEntity.getId(),connectedEntity);
		
		for(Entity e:entityIdMap.values()) {
			EntityConnectedNetworkOutput newMsg = new EntityConnectedNetworkOutput(msg);
			newMsg.setDestination(e);
			sendMessage(Type.FSM_NETWORK, newMsg);
		}
		
		for(Entity e:entityIdMap.values()) {
			EntityPositionNetworkOutput newMsg = new EntityPositionNetworkOutput(new EntityPosition());
			newMsg.getEntityPosition().setAngle(connectedEntity.getAngle());
			newMsg.getEntityPosition().setX(connectedEntity.getX());
			newMsg.getEntityPosition().setY(connectedEntity.getY());
			newMsg.getEntityPosition().setEntity(connectedEntity);
			newMsg.setDestination(e);
			sendMessage(Type.FSM_NETWORK, newMsg);
		}
	}
	
	private void processInput(EntityDisconnected msg) {
		Entity disconnectedEntity = msg.getEntity();
		for(Entity e:entityIdMap.values()) {
			EntityDisconnectedNetworkOutput newMsg = new EntityDisconnectedNetworkOutput(msg);
			newMsg.setDestination(e);
			sendMessage(Type.FSM_NETWORK, newMsg);
		}
		entityIdMap.remove(disconnectedEntity.getId());
	}
	
	private void processInput(EntityPosition msg) {
		//System.out.println("GameLogicFSM: recieved msg");
		if (!entityIdMap.containsKey(msg.getEntity().getId())) {
			entityIdMap.put(msg.getEntity().getId(), msg.getEntity());
		}
		msg.getEntity().setX(msg.getX());
		msg.getEntity().setY(msg.getY());
		
		//no need to keep letting the client know about this (here for debug)
		for(Entity target:entityIdMap.values()) {
			EntityPositionNetworkOutput newMsg = new EntityPositionNetworkOutput(msg);
			newMsg.setDestination(target);
			sendMessage(Type.FSM_NETWORK, newMsg);
		}
	}
	
	private void processInput(EntityMovement msg) {
		//System.out.println("GameLogicFSM: recieved msg");
		if (!entityIdMap.containsKey(msg.getEntity().getId())) {
			entityIdMap.put(msg.getEntity().getId(), msg.getEntity());
		}
		msg.getEntity().setX(msg.getX());
		msg.getEntity().setY(msg.getY());
		
		for(Entity target:entityIdMap.values()) {
			EntityMovementNetworkOutput newMsg = new EntityMovementNetworkOutput(msg);
			newMsg.setDestination(target);
			sendMessage(Type.FSM_NETWORK, newMsg);
		}
	}
}
