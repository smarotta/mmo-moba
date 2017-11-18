package com.sm.mmo.moba.qnfsm.fsm;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import com.sm.mmo.moba.domain.Entity;
import com.sm.mmo.moba.domain.Message;
import com.sm.mmo.moba.domain.message.EntityMovement;
import com.sm.mmo.moba.domain.message.EntityPosition;
import com.sm.mmo.moba.domain.message.EntityRemoved;
import com.sm.mmo.moba.domain.message.EntitySpawn;
import com.sm.mmo.moba.domain.message.EntityUpdate;
import com.sm.mmo.moba.domain.tree.QuadTree;
import com.sm.mmo.moba.qnfsm.FSM;
import com.sm.mmo.moba.qnfsm.FSMFeederBroadcaster;
import com.sm.mmo.moba.qnfsm.FSMFeeder.Type;
import com.sm.mmo.moba.qnfsm.fsm.movement.EntityMovementProcessor;
import com.sm.mmo.moba.qnfsm.fsm.movement.EntityPositionProcessor;
import com.sm.mmo.moba.qnfsm.fsm.movement.EntityRemovedProcessor;
import com.sm.mmo.moba.qnfsm.fsm.movement.EntitySpawnProcessor;

public class MovementFSM extends FSM {

	private static final Rectangle MAP_BOUNDARY = new Rectangle(0, 0, 1000, 1000);
	private static final Dimension PROXIMITY_DIMENSION = new Dimension(100, 100);
	
	private static class EntityMovementsCleanup extends EntityUpdate {
		@Override
		public MessageType getType() {
			return MessageType.INTERNAL;
		}
		
	}
	
	private final EntityMovementsCleanup cleanupMessage = new EntityMovementsCleanup();
	
	private QuadTree<Entity> entityTree = new QuadTree<Entity>(MAP_BOUNDARY); 
	private Map<UUID, EntityMovement> movingEntities = new HashMap<UUID, EntityMovement>();
	
	private EntityMovementProcessor movementProcessor = new EntityMovementProcessor(MAP_BOUNDARY, PROXIMITY_DIMENSION);
	private EntityPositionProcessor positionProcessor = new EntityPositionProcessor(MAP_BOUNDARY, PROXIMITY_DIMENSION);
	private EntitySpawnProcessor entitySpawnProcessor = new EntitySpawnProcessor(MAP_BOUNDARY, PROXIMITY_DIMENSION);
	private EntityRemovedProcessor entityRemovedProcessor = new EntityRemovedProcessor(MAP_BOUNDARY, PROXIMITY_DIMENSION);
	
	
	public MovementFSM(FSMFeederBroadcaster broadcaster) {
		super(broadcaster);
		scheduleCleanup();
	}
	
	private void scheduleCleanup() {
		sendMessage(Type.FSM_MOVEMENT, cleanupMessage, System.currentTimeMillis() + 5000l);
	}
	
	@Override
	protected void processInput(Message msg) {
		switch(msg.getType()) {
			case INTERNAL:
				if (msg instanceof EntityMovementsCleanup) {
					removeExpiredMovements();
				} else if (msg instanceof EntityRemoved) {
					processInput((EntityRemoved)msg);
				}
			break;
			case ENTITY_SPAWN:
				processInput((EntitySpawn)msg);
			break;
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
	
	private void removeExpiredMovements() {
		/* BUG!
		Iterator<Entry<UUID, EntityMovement>> iterator = movingEntities.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<UUID, EntityMovement> entry = iterator.next();
			if (entry.getValue().isExpiredMovement()) {
				movingEntities.remove(entry.getValue().getEntity().getId());
				iterator.remove();
			}
		}
		scheduleCleanup();
		*/
	}
	
	private void processInput(EntityRemoved msg) {
		entityRemovedProcessor.process(this, entityTree, movingEntities, msg);
	}
	
	private void processInput(EntitySpawn msg) {
		entitySpawnProcessor.process(this, entityTree, movingEntities, msg);
	}
	
	private void processInput(EntityPosition msg) {
		positionProcessor.process(this, entityTree, movingEntities, msg);
	}
	
	private void processInput(EntityMovement msg) {
		movementProcessor.process(this, entityTree, movingEntities, msg);
	}
	
	
	

}
