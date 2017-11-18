package com.sm.mmo.moba.qnfsm.fsm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.sm.mmo.moba.domain.Entity;
import com.sm.mmo.moba.domain.Message;
import com.sm.mmo.moba.domain.message.EntityConnected;
import com.sm.mmo.moba.domain.message.EntityDisconnected;
import com.sm.mmo.moba.domain.message.EntityMovement;
import com.sm.mmo.moba.domain.message.EntityPosition;
import com.sm.mmo.moba.domain.message.EntityRemoved;
import com.sm.mmo.moba.domain.message.EntitySpawn;
import com.sm.mmo.moba.domain.message.network.EntityConnectedNetworkOutput;
import com.sm.mmo.moba.qnfsm.FSM;
import com.sm.mmo.moba.qnfsm.FSMFeeder.Type;
import com.sm.mmo.moba.qnfsm.FSMFeederBroadcaster;

public class GameLogicFSM extends FSM {

	private Map<UUID, Entity> entityIdMap = new HashMap<UUID, Entity>();
	private Set<UUID> networkedEntities = new HashSet<UUID>();
	
	
	public GameLogicFSM(FSMFeederBroadcaster broadcaster) {
		super(broadcaster);
	}

	@Override
	protected void processInput(Message msg) {
		switch(msg.getType()) {
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
	
	private void addEntity(Entity entity, boolean isNetworked) {
		EntityConnectedNetworkOutput connectedMsg = new EntityConnectedNetworkOutput(new EntityConnected());
		connectedMsg.getEntityConnected().setEntity(entity);
		connectedMsg.getEntityConnected().setCurrentPlayer(true);
		connectedMsg.setDestination(entity);
		sendMessage(Type.FSM_NETWORK, connectedMsg);
		
		EntitySpawn entitySpawn = new EntitySpawn();
		if (isNetworked) {
			networkedEntities.add(entity.getId());
			entitySpawn.setRace(0x01);
		} else {
			entitySpawn.setRace(0x15);
		}
		entityIdMap.put(entity.getId(), entity);
		entitySpawn.setAngle((short)(Math.random() * 359));
		entitySpawn.setX((int)Math.random() * 500);
		entitySpawn.setY((int)Math.random() * 500);
		entitySpawn.setEntity(entity);
		sendMessage(Type.FSM_MOVEMENT, entitySpawn);
	}
	
	private void removeEntity(Entity entity) {
		entityIdMap.remove(entity.getId());
		networkedEntities.remove(entity.getId());
		EntityRemoved entityRemoved = new EntityRemoved();
		entityRemoved.setEntity(entity);
		sendMessage(Type.FSM_MOVEMENT, entityRemoved);
	}
	
	private void processInput(EntityConnected msg) {
		Entity connectedEntity = msg.getEntity();
		addEntity(connectedEntity, true);
	}
	
	private void processInput(EntityDisconnected msg) {
		Entity disconnectedEntity = entityIdMap.get(msg.getEntity().getId());
		removeEntity(disconnectedEntity);
	}
	
	private void processInput(EntityMovement msg) {
	
	}
}
