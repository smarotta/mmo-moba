package com.sm.mmo.moba.domain.tree;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sm.mmo.moba.domain.Positionable;

public class QuadTree<T extends Positionable> {

	private static final int QUAD_MAX_ITEMS = 1;
	
	private static class Boundary {
		int x;
		int y;
		int width;
		int height;
		
		boolean contains(Positionable p) {
			boolean xCollides = p.getX() >= x && p.getX() <= x + width;
			boolean yCollides = p.getY() >= y && p.getY() <= y + width;
			return xCollides && yCollides;
		}
		
		boolean intersects(Rectangle rectangle) {
			return rectangle.intersects(x, y, width, height);
		}
	}
	
	private Boundary boundary;
	
	private Set<T> positionables;
	
	private QuadTree<T> topLeft;
	private QuadTree<T> topRight;
	private QuadTree<T> bottomLeft;
	private QuadTree<T> bottomRight;
	private QuadTree<T> parent;
	
	public QuadTree(Rectangle rect) {
		this(rect.x, rect.y, rect.width, rect.height);
	}
	
	public QuadTree(int x, int y, int width, int height) {
		boundary = new Boundary();
		boundary.x = x;
		boundary.y = y;
		boundary.width = width;
		boundary.height = height;
	}
	
	public boolean insert(T p) {
		if (!boundary.contains(p)) {
			return false;
		}
		
		if (topLeft == null) {
			if (positionables == null) {
				positionables = new HashSet<T>();
			}
			
			if (positionables.size() < QUAD_MAX_ITEMS || this.boundary.width < 5 || this.boundary.height < 5) {
				positionables.add(p);
				return true;
			} else {
				if (topLeft == null) {
					subdivide();
				}
			}
		}
		
		return topLeft.insert(p) || topRight.insert(p) || bottomLeft.insert(p) || bottomRight.insert(p);
		
	}
	
	private void subdivide() {
		int halfSize = (int)(boundary.width / 2.0d + 0.5d);
		topLeft = new QuadTree<T>(
				boundary.x, 
				boundary.y, 
				halfSize, halfSize);
		topRight = new QuadTree<T>(
				boundary.x + halfSize, 
				boundary.y,
				halfSize, halfSize);
		bottomLeft = new QuadTree<T>(
				boundary.x,
				boundary.y + halfSize, 
				halfSize, halfSize);
		bottomRight = new QuadTree<T>(
				boundary.x + halfSize, 
				boundary.y + halfSize, 
				halfSize, halfSize);
		
		for(T p:positionables) {
			boolean inserted = topLeft.insert(p) || topRight.insert(p) || bottomLeft.insert(p) || bottomRight.insert(p);
		}
		positionables = null;
		topLeft.parent = topRight.parent = bottomLeft.parent = bottomRight.parent = this;
	}
	
	private Collection<T> queryRange(Rectangle rectangle) {
		Set<T> inRange = new HashSet<T>();
		
		//empty quadtree
		if (positionables == null && topLeft == null) {
			return inRange;
		}
		
		if (boundary.intersects(rectangle)) {
			if (positionables != null) {
				for(T p:positionables) {
					if (rectangle.contains(p.getX(), p.getY())) {
						inRange.add(p);
					}
				}
			} else {
				inRange.addAll(topLeft.queryRange(rectangle));
				inRange.addAll(topRight.queryRange(rectangle));
				inRange.addAll(bottomLeft.queryRange(rectangle));
				inRange.addAll(bottomRight.queryRange(rectangle));
			}
		}
		
		return inRange;
	}
	
	public Collection<T> queryRange(int x, int y, int width, int height) {
		return queryRange(new Rectangle(x, y, width, height));
	}
	
	public boolean remove(T p) {
		if (!boundary.contains(p)) {
			return false;
		}
		
		if (topLeft == null) {
			if (positionables != null) {
				if (positionables.remove(p)) {
					if (positionables.isEmpty()) {
						underdivide();
					}
					return true;
				}
			} 
			return false;
		} else {
			return topLeft.remove(p) || topRight.remove(p) || bottomLeft.remove(p) || bottomRight.remove(p);
		}
	}
	
	public boolean isEmpty() {
		if (positionables != null) {
			return positionables.isEmpty();
		}
		
		if (topLeft == null) {
			return true;
		}
		
		return topLeft.isEmpty() && topRight.isEmpty() && bottomLeft.isEmpty() && bottomRight.isEmpty();
	}
	
	private void underdivide() {
		if (isEmpty()) {
			this.topLeft = this.topRight = this.bottomLeft = this.bottomRight = null;
			this.positionables = null;
			if (parent != null) {
				parent.underdivide();
			}
		}
	}
	
	public List<Rectangle> debug() {
		List<Rectangle> rectangles = new ArrayList<Rectangle>();
		
		if (boundary != null) {
			rectangles.add(new Rectangle(boundary.x, boundary.y, boundary.width, boundary.height));
			if (topLeft != null) {
				rectangles.addAll(topLeft.debug());
				rectangles.addAll(topRight.debug());
				rectangles.addAll(bottomLeft.debug());
				rectangles.addAll(bottomRight.debug());
			}
		}
		
		return rectangles;
	}
}
