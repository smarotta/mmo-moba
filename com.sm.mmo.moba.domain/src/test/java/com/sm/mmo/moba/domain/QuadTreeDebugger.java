package com.sm.mmo.moba.domain;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.event.MouseInputListener;

import com.sm.mmo.moba.domain.Entity;
import com.sm.mmo.moba.domain.message.EntityMovement;
import com.sm.mmo.moba.domain.tree.QuadTree;

public class QuadTreeDebugger {
	
	private static class DebugPanel extends JPanel {
		
		List<Entity> entities = new ArrayList<Entity>();
		List<Entity> foundEntities = new ArrayList<Entity>();
		List<Rectangle> searchedAreas = new ArrayList<Rectangle>();
		List<Rectangle> nodeAreas = new ArrayList<Rectangle>();
		
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			//((Graphics2D)g).scale(5, 5);
			
			g.setColor(Color.white);
			g.fillRect(0, 0, 500, 500);
			
			g.setColor(Color.black);
			synchronized(entities) {
				for(Entity entity:entities) {
					g.setColor(Color.RED);
					g.fillRect((int)entity.getX() - 1, (int)entity.getY() - 1, 2, 2);
				}
			}
			
			synchronized(searchedAreas) {
				for(Rectangle r:searchedAreas) {
					g.setColor(Color.GRAY);
					g.drawRect(r.x, r.y, r.width, r.height);
				}
			}
			
			synchronized(nodeAreas) {
				for(Rectangle r:nodeAreas) {
					g.setColor(Color.BLUE);
					g.drawRect(r.x, r.y, r.width, r.height);
				}
			}
			
			synchronized(foundEntities) {
				for(Entity entity:foundEntities) {
					g.setColor(Color.MAGENTA);
					g.drawLine(entity.getX() - 10, entity.getY(), entity.getX() + 10, entity.getY());
					g.drawLine(entity.getX(), entity.getY()- 10, entity.getX(), entity.getY() + 10);
				}
			}
			
		}
	}
	
	private JFrame frame;
	private DebugPanel debugPanel;
	public void setupPanel() {
		debugPanel = new DebugPanel();
		debugPanel.setBounds(0, 0, 500, 500);
		debugPanel.setBackground(Color.gray);
		
		frame = new JFrame();
		frame.add(debugPanel);
		frame.pack();
		frame.setVisible(true); 
		frame.setBounds(0, 0, 500, 500);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	public static void test1(QuadTreeDebugger debugger) {
		QuadTree<Entity> st = new QuadTree<Entity>(0, 0, 500, 500);

		debugger.setupPanel();
		
		Thread adding = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					for(int x=0; x < 10; x++) {
						Entity e = new Entity();
				    	e.setX((int) (500 * Math.random()));
				    	e.setY((int) (500 * Math.random()));
				    	synchronized(st) {
				    		st.insert(e);
				    	}
				    	synchronized(debugger.debugPanel.entities) {
				    		debugger.debugPanel.entities.add(e);
				    	}
					}
					return;
					/*
			    	synchronized(debugger.debugPanel.nodeAreas) {
			    		debugger.debugPanel.nodeAreas.clear();
				    	for(Rectangle r:st.debug()) {
					    	debugger.debugPanel.nodeAreas.add(r);
					    }
			    	}
			    	
			    	debugger.frame.getContentPane().validate();
			    	debugger.frame.getContentPane().repaint();
			    	try {
						Thread.sleep(1000l);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					*/
				}
			}
		});
		adding.start();
		
		Thread finding = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					Integer x = (int) (500 * Math.random());
			        Integer y = (int) (500 * Math.random());
			        Integer width = (int) (50 + 100 * Math.random());
			        Integer height = (int) (50 + 100 * Math.random());
			    	synchronized(st) {
			    		Collection<Entity> found = st.queryRange(x, y, width, height);
			    		synchronized (debugger.debugPanel.foundEntities) {
			    			debugger.debugPanel.foundEntities.clear();
			    			for(Entity fe:found) {
				    			debugger.debugPanel.foundEntities.add(fe);
				    		}
						}
				    }
			    	
			    	synchronized(debugger.debugPanel.searchedAreas) {
			    		debugger.debugPanel.searchedAreas.clear();
			    		debugger.debugPanel.searchedAreas.add(new Rectangle(x, y, width, height));
			    	}
			    	
			    	try {
						Thread.sleep(500l);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		finding.start();
		
		Thread movement = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					synchronized(debugger.debugPanel.entities) {
						for (Entity e:debugger.debugPanel.entities) {
							e.setX((int)(e.getX() -2 + Math.random() * 4));
							e.setY((int)(e.getY() -2 + Math.random() * 4));
	
							synchronized(st) {
					    		st.remove(e);
					    		st.insert(e);
					    		synchronized(debugger.debugPanel.nodeAreas) {
						    		debugger.debugPanel.nodeAreas.clear();
							    	for(Rectangle r:st.debug()) {
								    	debugger.debugPanel.nodeAreas.add(r);
								    }
					    		}
					    	}
						}
					}
					debugger.frame.getContentPane().validate();
			    	debugger.frame.getContentPane().repaint();
			    	try {
						Thread.sleep(100l);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		movement.start();
		
		Thread removing = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					if (!debugger.debugPanel.entities.isEmpty()) {
						Entity e = debugger.debugPanel.entities.get(0); 
						debugger.debugPanel.entities.remove(e);
				    	debugger.debugPanel.foundEntities.add(e);
				    	
				    	synchronized(st) {
				    		st.remove(e);
				    	}
				    					    	
				    	debugger.debugPanel.nodeAreas.clear();
				    	for(Rectangle r:st.debug()) {
					    	debugger.debugPanel.nodeAreas.add(r);
					    }
				    	debugger.frame.getContentPane().validate();
				    	debugger.frame.getContentPane().repaint();
				    	try {
							Thread.sleep(1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});
		
		//removing.start();
		
	    
	    /*
	    // do some range searches
	    for (int i = 0; i < 10; i++) {
	        Integer x = (int) (500 * Math.random());
	        Integer y = (int) (500 * Math.random());
	        Integer width = (int) (50 + 100 * Math.random());
	        Integer height = (int) (50 + 100 * Math.random());
	        
	        debugger.debugPanel.searchedAreas.add(new Rectangle(x, y, width, height));
	    	debugger.frame.getContentPane().validate();
	    	debugger.frame.getContentPane().repaint();
	    	
	        System.out.println("Looking for entities under [" + x + "," + y + "][" + width + "," + height + "]");
	        for(Entity e:st.queryRange(x, y, width, height)){
	        	debugger.debugPanel.foundEntities.add(e);
	        	debugger.frame.getContentPane().validate();
		    	debugger.frame.getContentPane().repaint();
		    	
	        	System.out.println("Found " + e.getRace() + " [" + e.getX() + "," + e.getY() + "]");
	        }
	    }
	    */
	}

	public Entity insert(QuadTree<Entity> st, int x, int y) {
		Entity e = new Entity();
    	e.setX(x);
    	e.setY(y);
   		st.insert(e);
    	synchronized(debugPanel.entities) {
    		debugPanel.entities.add(e);
    	}
    	return e;
	}
	
	public void draw(QuadTree<Entity> st) {
		//draw tree
		debugPanel.nodeAreas.clear();
    	for(Rectangle r:st.debug()) {
	    	debugPanel.nodeAreas.add(r);
	    }
		
    	frame.getContentPane().validate();
    	frame.getContentPane().repaint();
	}
	
	public static void test2(QuadTreeDebugger debugger) {
		QuadTree<Entity> st = new QuadTree<Entity>(0, 0, 500, 500);

		debugger.setupPanel();
		
		debugger.insert(st, 500/2/2/2 + 500/2/2/2/2, 500/2/2/2 + 500/2/2/2/2);
		debugger.draw(st);
		
		Entity snd = debugger.insert(st, 500/2/2/2 + 500/2/2/2/2, 500/2/2/2 + 500/2/2/2/2/2);
		debugger.draw(st);
		
		st.remove(snd);
		synchronized(debugger.debugPanel.entities) {
			debugger.debugPanel.entities.remove(snd);
    	}
		
		debugger.draw(st);
		
	}
	
	public static void main(String [] args) {
		//test1(new QuadTreeDebugger());
		//test2(new QuadTreeDebugger());
		
	}
}
