package com.sm.mmo.moba.qnfsm.fsm.movement;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Collection;

import com.sm.mmo.moba.domain.Entity;
import com.sm.mmo.moba.domain.tree.QuadTree;

public class AbstractSpatialProcessor {
	
	private Rectangle mapBoundary;
	
	private Dimension proximityDimension;
	
	public AbstractSpatialProcessor(Rectangle mapBoundary, Dimension proximityDimension) {
		this.mapBoundary = mapBoundary;
		this.proximityDimension = proximityDimension;
	}
	
	protected Collection<Entity> getProximity(QuadTree<Entity> entityTree, Entity entity) {
		Collection<Entity> entities = entityTree.queryRange(
					entity.getX() - proximityDimension.width/2, 
					entity.getY() - proximityDimension.height/2, 
					proximityDimension.width, 
					proximityDimension.height);
		entities.removeIf(e -> e.getId().equals(entity.getId()));
		return entities;
	}
	
	
}
