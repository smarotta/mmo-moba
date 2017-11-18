package com.sm.mmo.moba.qnfsm.fsm.movement;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import com.sm.mmo.moba.domain.Entity;
import com.sm.mmo.moba.domain.message.EntityConnected;
import com.sm.mmo.moba.domain.message.EntityDisconnected;
import com.sm.mmo.moba.domain.message.EntityMovement;
import com.sm.mmo.moba.domain.message.EntityPosition;
import com.sm.mmo.moba.domain.message.EntitySpawn;
import com.sm.mmo.moba.domain.message.network.EntityConnectedNetworkOutput;
import com.sm.mmo.moba.domain.message.network.EntityDisconnectedNetworkOutput;
import com.sm.mmo.moba.domain.message.network.EntityPositionNetworkOutput;
import com.sm.mmo.moba.domain.message.network.EntitySpawnNetworkOutput;
import com.sm.mmo.moba.domain.tree.QuadTree;
import com.sm.mmo.moba.qnfsm.FSM;
import com.sm.mmo.moba.qnfsm.FSMFeeder.Type;

public class EntitySpawnProcessor extends AbstractSpatialProcessor {

	public EntitySpawnProcessor(Rectangle mapBoundary, Dimension proximityDimension) {
		super(mapBoundary, proximityDimension);
	}

	public void process(FSM fsm, QuadTree<Entity> entityTree, Map<UUID, EntityMovement> movingEntities, EntitySpawn  msg) {
		msg.getEntity().setAngle(msg.getAngle());
		msg.getEntity().setRace(msg.getRace());
		msg.getEntity().setX(msg.getX());
		msg.getEntity().setY(msg.getY());
		entityTree.insert(msg.getEntity());
		
		//dont care about who stopped/started seeing it
		for(Entity e:getProximity(entityTree, msg.getEntity())) {
			EntitySpawnNetworkOutput newMsg = new EntitySpawnNetworkOutput(msg);
			newMsg.setDestination(e);
			fsm.sendMessage(Type.FSM_NETWORK, newMsg);
			
			EntitySpawnNetworkOutput newEntityMsg = new EntitySpawnNetworkOutput(new EntitySpawn());
			newEntityMsg.getEntitySpawn().setAngle(e.getAngle());
			newEntityMsg.getEntitySpawn().setRace(e.getRace());
			newEntityMsg.getEntitySpawn().setX(e.getX());
			newEntityMsg.getEntitySpawn().setY(e.getY());
			newEntityMsg.getEntitySpawn().setEntity(e);
			newEntityMsg.setDestination(msg.getEntity());
			fsm.sendMessage(Type.FSM_NETWORK, newEntityMsg);
		}
		
		/*
		//no need to keep letting the client know about this (here for debug)
		for(Entity target:entityIdMap.values()) {
			EntityPositionNetworkOutput newMsg = new EntityPositionNetworkOutput(msg);
			newMsg.setDestination(target);
			sendMessage(Type.FSM_NETWORK, newMsg);
		}
		*/
	}
}
