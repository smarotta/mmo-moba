package com.sm.mmo.moba.qnfsm.fsm.movement;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
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
import com.sm.mmo.moba.domain.message.network.EntityMovementNetworkOutput;
import com.sm.mmo.moba.domain.message.network.EntityPositionNetworkOutput;
import com.sm.mmo.moba.domain.message.network.EntitySpawnNetworkOutput;
import com.sm.mmo.moba.domain.tree.QuadTree;
import com.sm.mmo.moba.qnfsm.FSM;
import com.sm.mmo.moba.qnfsm.FSMFeeder.Type;

public class EntityPositionProcessor extends AbstractSpatialProcessor {

	public EntityPositionProcessor(Rectangle mapBoundary, Dimension proximityDimension) {
		super(mapBoundary, proximityDimension);
	}

	public void process(FSM fsm, QuadTree<Entity> entityTree, Map<UUID, EntityMovement> movingEntities, EntityPosition  msg) {
		
		Collection<Entity> proximityBefore = getProximity(entityTree, msg.getEntity());
		System.out.println("proximityBefore: " + proximityBefore.size());
		
		msg.getEntity().setX(msg.getX());
		msg.getEntity().setY(msg.getY());
		
		//temporary fix (quad tree is not dynamic)
		entityTree.remove(msg.getEntity());
		entityTree.insert(msg.getEntity());
		
		Collection<Entity> proximityNow = getProximity(entityTree, msg.getEntity());
		System.out.println("proximityNow: " + proximityNow.size());
		
		//stopped seeing
		Collection<Entity> stoppedSeeing = new ArrayList<Entity>(proximityBefore);
		stoppedSeeing.removeAll(proximityNow);
		System.out.println("stoppedSeeing: " + stoppedSeeing.size());
		
		//started seeing
		Collection<Entity> startedToSee = new ArrayList<Entity>(proximityNow);
		startedToSee.removeAll(proximityBefore);
		System.out.println("startedToSee: " + startedToSee.size());
		
		//add itself to the list (temporary)
		//proximityNow.add(msg.getEntity());
		
		//remove the entity from the ones that stopped seeing it
		for(Entity e:stoppedSeeing) {
			EntityDisconnectedNetworkOutput diconnectedMsg = new EntityDisconnectedNetworkOutput(new EntityDisconnected());
			diconnectedMsg.getEntityDisconnected().setEntity(msg.getEntity());
			diconnectedMsg.setDestination(e);
			fsm.sendMessage(Type.FSM_NETWORK, diconnectedMsg);
		}
		
		//now add the entities to the ones that started seeing it
		for(Entity e:startedToSee) {
			/*
			EntityConnectedNetworkOutput connectedMsg = new EntityConnectedNetworkOutput(new EntityConnected());
			connectedMsg.getEntityConnected().setEntity(msg.getEntity());
			connectedMsg.getEntityConnected().setCurrentPlayer(false);
			connectedMsg.setDestination(e);
			fsm.sendMessage(Type.FSM_NETWORK, connectedMsg);
			*/
			
			EntitySpawnNetworkOutput spawnMsg = new EntitySpawnNetworkOutput(new EntitySpawn());
			spawnMsg.getEntitySpawn().setAngle(msg.getEntity().getAngle());
			spawnMsg.getEntitySpawn().setRace(msg.getEntity().getRace());
			spawnMsg.getEntitySpawn().setX(msg.getEntity().getX());
			spawnMsg.getEntitySpawn().setY(msg.getEntity().getY());
			spawnMsg.getEntitySpawn().setEntity(msg.getEntity());
			spawnMsg.setDestination(e);
			fsm.sendMessage(Type.FSM_NETWORK, spawnMsg);
			
			EntitySpawnNetworkOutput spawnMsgForMovingEntity = new EntitySpawnNetworkOutput(new EntitySpawn());
			spawnMsgForMovingEntity.getEntitySpawn().setAngle(e.getAngle());
			spawnMsgForMovingEntity.getEntitySpawn().setRace(e.getRace());
			spawnMsgForMovingEntity.getEntitySpawn().setX(e.getX());
			spawnMsgForMovingEntity.getEntitySpawn().setY(e.getY());
			spawnMsgForMovingEntity.getEntitySpawn().setEntity(e);
			spawnMsgForMovingEntity.setDestination(msg.getEntity());
			fsm.sendMessage(Type.FSM_NETWORK, spawnMsgForMovingEntity);
		}
		
		for(Entity e:proximityNow) {
			if (movingEntities.containsKey(msg.getEntity().getId()) && !movingEntities.get(msg.getEntity().getId()).isExpiredMovement()) {
				//this entity was already moving, we can just send the movement package
				EntityMovementNetworkOutput movementMsg = new EntityMovementNetworkOutput(new EntityMovement());
				EntityMovement oldMovement = movingEntities.get(msg.getEntity().getId());
				movementMsg.getEntityMovement().setAngle(oldMovement.getAngle());
				movementMsg.getEntityMovement().setEntity(oldMovement.getEntity());
				movementMsg.getEntityMovement().setTargetX(oldMovement.getTargetX());
				movementMsg.getEntityMovement().setTargetY(oldMovement.getTargetY());
				movementMsg.getEntityMovement().setX(msg.getEntity().getX());
				movementMsg.getEntityMovement().setY(msg.getEntity().getY());
				movementMsg.setDestination(e);
				fsm.sendMessage(Type.FSM_NETWORK, movementMsg);
			} else {
				//this entity is stopped in one place, send the position package
				EntityPositionNetworkOutput positionMsg = new EntityPositionNetworkOutput(new EntityPosition());
				positionMsg.getEntityPosition().setAngle(msg.getEntity().getAngle());
				positionMsg.getEntityPosition().setX(msg.getEntity().getX());
				positionMsg.getEntityPosition().setY(msg.getEntity().getY());
				positionMsg.getEntityPosition().setEntity(msg.getEntity());
				positionMsg.setDestination(e);
				fsm.sendMessage(Type.FSM_NETWORK, positionMsg);
			}
		}
	
		//let the game client know about the new position (calculate hits, damage, etc)
		fsm.sendMessage(Type.FSM_GAME_LOGIC, msg);

		//debug
		EntityPositionNetworkOutput positionMsg = new EntityPositionNetworkOutput(new EntityPosition());
		positionMsg.getEntityPosition().setAngle(msg.getEntity().getAngle());
		positionMsg.getEntityPosition().setX(msg.getEntity().getX());
		positionMsg.getEntityPosition().setY(msg.getEntity().getY());
		positionMsg.getEntityPosition().setEntity(msg.getEntity());
		positionMsg.setDestination(msg.getEntity());
		fsm.sendMessage(Type.FSM_NETWORK, positionMsg);
		
		System.out.println("New position [" + msg.getX() + ", " + msg.getY() + "]");
	}
}
