package com.sm.mmo.moba.client;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import org.com.sm.mmo.moba.domain.Entity;
import org.com.sm.mmo.moba.domain.message.EntityMovement;
import org.com.sm.mmo.moba.domain.message.network.EntityMovementNetworkOutput;
import org.com.sm.mmo.moba.domain.message.network.EntityPositionNetworkInput;
import org.com.sm.mmo.moba.domain.message.network.NetworkInput;

import com.sm.mmo.moba.client.messages.ClientEntityConnectedNetworkInput;
import com.sm.mmo.moba.client.messages.ClientEntityMovementNetworkInput;
import com.sm.mmo.moba.client.messages.ClientEntityMovementNetworkOutput;
import com.sm.mmo.moba.client.messages.ClientEntityPositionNetworkInput;

@ChannelHandler.Sharable
public class EntityClientHandler extends ChannelInboundHandlerAdapter {
	
	private static final Map<ChannelHandlerContext, ConnectedPlayer> contextClientMap = new ConcurrentHashMap<ChannelHandlerContext, ConnectedPlayer>();
	private static final Map<UUID, Entity> entitiesMap = new ConcurrentHashMap<UUID, Entity>();
	private static final Map<UUID, ChannelHandlerContext> entitiesCtxMap = new ConcurrentHashMap<UUID, ChannelHandlerContext>();
	private JFrame frame = new JFrame();
	private ConnectedPlayer me;
	private ClientPanel clientPanel;
	
	private static class ClientPanel extends JPanel  implements MouseInputListener {
		
		Map<Entity, Point> entityDestinations = new HashMap<Entity, Point>();
		
		Entity me;
		
		public ClientPanel(Entity me) {
			super();
			this.me = me;
		}
		
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			g.setColor(Color.white);
			g.fillRect(0, 0, 300, 300);
			
			g.setColor(Color.black);
			synchronized(entitiesMap) {
				for (Entry<Entity, Point> entry:entityDestinations.entrySet()) {
					g.setColor(Color.GRAY);
					g.drawLine(entry.getKey().getX(), entry.getKey().getY(), (int)entry.getValue().getX(), (int)entry.getValue().getY());
					g.drawArc((int)entry.getValue().getX() - 5, (int)entry.getValue().getY() - 5, 10, 10, 0, 360);
				}
				
				for(Entity entity:entitiesMap.values()) {
					if (entity.getId().equals(me.getId())) {
						g.setColor(Color.BLACK);
						me = entity;
					} else {
						g.setColor(Color.RED);
					}
					g.fillRect((int)entity.getX() - 2, (int)entity.getY() - 2, 4, 4);
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			
			//me.setX(e.getX());
			//me.setY(e.getY());
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			System.out.println("mouse pressed!");
			if (entitiesCtxMap.containsKey(me.getId())) {
				ChannelHandlerContext ctx = entitiesCtxMap.get(me.getId());
				EntityMovement em = new EntityMovement();
				em.setEntity(me);
				em.setTargetX(e.getX());
				em.setTargetY(e.getY());
				em.setX(me.getX());
				em.setY(me.getY());
				ClientEntityMovementNetworkOutput mv = new ClientEntityMovementNetworkOutput(em);
				mv.setDestination(me);
				ctx.writeAndFlush(mv);
				System.out.println("From [" + em.getX() + "," + em.getY() + "] to [" + em.getTargetX() + "," + em.getTargetY() + "]");
			}
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		public void setDestination(Entity e, int dx, int dy) {
			entityDestinations.put(e, new Point(dx, dy));
		}
	}
	
	private void setupPanel() {
		clientPanel = new ClientPanel(me);
		clientPanel.setBounds(0, 0, 300, 300);
		clientPanel.setBackground(Color.gray);
		clientPanel.addMouseListener(clientPanel);
		
		frame = new JFrame();
		frame.add(clientPanel);
		frame.pack();
		frame.setVisible(true); 
		frame.setBounds(0, 0, 350, 350);
		
		me.setX(0);
		me.setY(0);
	}
	
	private void handle(ClientEntityMovementNetworkInput entityMovementNetworkInput, ChannelHandlerContext ctx) {
		if (!entitiesMap.containsKey(entityMovementNetworkInput.getEntityMovement().getEntity().getId())) {
			entitiesMap.put(entityMovementNetworkInput.getEntityMovement().getEntity().getId(), entityMovementNetworkInput.getEntityMovement().getEntity());
		}
	
		Entity entity = entitiesMap.get(entityMovementNetworkInput.getEntityMovement().getEntity().getId());
		entity.setX(entityMovementNetworkInput.getEntityMovement().getX());
		entity.setY(entityMovementNetworkInput.getEntityMovement().getY());
		clientPanel.setDestination(entity, entityMovementNetworkInput.getEntityMovement().getTargetX(), entityMovementNetworkInput.getEntityMovement().getTargetY());
		frame.getContentPane().validate();
		frame.getContentPane().repaint();
	}
	
	private void handle(ClientEntityPositionNetworkInput entityPositionNetworkInput, ChannelHandlerContext ctx) {
		if (!entitiesMap.containsKey(entityPositionNetworkInput.getEntityPosition().getEntity().getId())) {
			entitiesMap.put(entityPositionNetworkInput.getEntityPosition().getEntity().getId(), entityPositionNetworkInput.getEntityPosition().getEntity());
		}
	
		Entity entity = entitiesMap.get(entityPositionNetworkInput.getEntityPosition().getEntity().getId());
		entity.setX(entityPositionNetworkInput.getEntityPosition().getX());
		entity.setY(entityPositionNetworkInput.getEntityPosition().getY());
		frame.getContentPane().validate();
		frame.getContentPane().repaint();
	}
	
	private void handle(ClientEntityConnectedNetworkInput cecni, ChannelHandlerContext ctx) {
		Entity ce = cecni.getEntityConnected().getEntity();
		if (me == null) {
			me = new ConnectedPlayer(ctx);
			me.setId(ce.getId());
			contextClientMap.put(ctx, me);
			entitiesMap.put(me.getId(), me);
			entitiesCtxMap.put(me.getId(), ctx);
			setupPanel();
		} else if (!ce.getId().equals(me.getId())) {
			entitiesMap.put(ce.getId(), ce);
			entitiesCtxMap.put(ce.getId(), ctx);
		}
	}
	
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object objMsg) {
		if (objMsg instanceof NetworkInput) {
			NetworkInput input = (NetworkInput) objMsg;
			ConnectedPlayer player = contextClientMap.get(ctx);
			if (player != null) {
				input.setSource(player);
			}
			
			if (input instanceof ClientEntityMovementNetworkInput) {
				handle((ClientEntityMovementNetworkInput) input, ctx);
				
			} else if (input instanceof ClientEntityPositionNetworkInput) {
				handle((ClientEntityPositionNetworkInput) input, ctx);
				
			} else if (input instanceof ClientEntityConnectedNetworkInput) {
				handle((ClientEntityConnectedNetworkInput) input, ctx);

			}
		}
    }

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		/*
		contextClientMap.put(ctx, me);
		entitiesMap.put(me.getId(), me);
		entitiesCtxMap.put(me.getId(), ctx);
		*/
		//call FSM
	}
	
	private void removeConnectedPlayer(ChannelHandlerContext ctx) {
		ConnectedPlayer player = contextClientMap.get(ctx);
		if (player != null) {
			contextClientMap.remove(ctx);
			entitiesMap.remove(player.getId());
			entitiesCtxMap.remove(player.getId());
		}
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		removeConnectedPlayer(ctx);
		//call FSM
	}
	
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
