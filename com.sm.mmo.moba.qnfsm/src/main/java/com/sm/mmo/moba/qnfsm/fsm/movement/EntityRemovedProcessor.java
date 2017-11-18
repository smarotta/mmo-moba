package com.sm.mmo.moba.qnfsm.fsm.movement;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Map;
import java.util.UUID;

import com.sm.mmo.moba.domain.Entity;
import com.sm.mmo.moba.domain.message.EntityDisconnected;
import com.sm.mmo.moba.domain.message.EntityMovement;
import com.sm.mmo.moba.domain.message.EntityRemoved;
import com.sm.mmo.moba.domain.message.network.EntityDisconnectedNetworkOutput;
import com.sm.mmo.moba.domain.tree.QuadTree;
import com.sm.mmo.moba.qnfsm.FSM;
import com.sm.mmo.moba.qnfsm.FSMFeeder.Type;

public class EntityRemovedProcessor extends AbstractSpatialProcessor {

	public EntityRemovedProcessor(Rectangle mapBoundary, Dimension proximityDimension) {
		super(mapBoundary, proximityDimension);
	}

	public void process(FSM fsm, QuadTree<Entity> entityTree, Map<UUID, EntityMovement> movingEntities, EntityRemoved  msg) {
		
		for(Entity e:getProximity(entityTree, msg.getEntity())) {
			EntityDisconnectedNetworkOutput newMsg = new EntityDisconnectedNetworkOutput(new EntityDisconnected());
			newMsg.getEntityDisconnected().setEntity(msg.getEntity());
			newMsg.setDestination(e);
			fsm.sendMessage(Type.FSM_NETWORK, newMsg);
		}
		
		entityTree.remove(msg.getEntity());
		movingEntities.remove(msg.getEntity().getId());
	}
}
