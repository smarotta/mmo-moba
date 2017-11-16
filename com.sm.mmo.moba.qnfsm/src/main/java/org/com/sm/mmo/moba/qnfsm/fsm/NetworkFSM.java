package org.com.sm.mmo.moba.qnfsm.fsm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.com.sm.mmo.moba.domain.Entity;
import org.com.sm.mmo.moba.domain.Message;
import org.com.sm.mmo.moba.domain.message.EntityConnected;
import org.com.sm.mmo.moba.domain.message.EntityDisconnected;
import org.com.sm.mmo.moba.domain.message.EntityMovement;
import org.com.sm.mmo.moba.domain.message.EntityPosition;
import org.com.sm.mmo.moba.domain.message.network.EntityConnectedNetworkOutput;
import org.com.sm.mmo.moba.domain.message.network.EntityDisconnectedNetworkOutput;
import org.com.sm.mmo.moba.domain.message.network.EntityMovementNetworkInput;
import org.com.sm.mmo.moba.domain.message.network.EntityPositionNetworkInput;
import org.com.sm.mmo.moba.domain.message.network.NetworkInput;
import org.com.sm.mmo.moba.domain.message.network.NetworkMessage;
import org.com.sm.mmo.moba.domain.message.network.NetworkOutput;
import org.com.sm.mmo.moba.qnfsm.FSM;
import org.com.sm.mmo.moba.qnfsm.FSMFeeder.Type;
import org.com.sm.mmo.moba.qnfsm.FSMFeederBroadcaster;

public class NetworkFSM extends FSM {

	public static interface NetworkHandler {
		void sendMessage(Entity entity, NetworkOutput out);
		void disconnect(Entity entity);
	}
	
	private final Set<Entity> connectedEntities = new HashSet<Entity>();
	private NetworkHandler networkHandler;
	
	public NetworkFSM(FSMFeederBroadcaster broadcaster, NetworkHandler networkHandler) {
		super(broadcaster);
		this.networkHandler = networkHandler;	
	}

	@Override
	protected void processInput(Message msg) {
		//System.out.println("NetworkFSM: recieved msg " + msg.getClass() + " type:" + msg.getType());
		switch(msg.getType()) {
			case ENTITY_MOVEMENT:
			case ENTITY_POSITION:
				processInput((NetworkMessage)msg);
			break;
			case ENTITY_CONNECTED:
				processInput((EntityConnectedNetworkOutput)msg);
			break;
			case ENTITY_DISCONNECTED:
				processInput((EntityDisconnectedNetworkOutput)msg);
			break;
			default:
				//do nothing
		}
	}

	private void processInput(EntityConnected msg) {
		connectedEntities.add(msg.getEntity());
	}
	
	private void processInput(EntityDisconnectedNetworkOutput msg) {
		if (connectedEntities.contains(msg.getEntityDisconnected().getEntity())) {
			networkHandler.disconnect(msg.getEntityDisconnected().getEntity());
			connectedEntities.remove(msg.getEntityDisconnected().getEntity());
			for(Type type:Type.values()) {
				if (type != Type.FSM_NETWORK) {
					sendMessage(type, msg.getEntityDisconnected());
				}
			}
		}
	}
	
	private void processInput(NetworkMessage msg) {
		if (msg instanceof NetworkInput) {
			if (msg instanceof EntityMovementNetworkInput) {
				((EntityMovementNetworkInput) msg).getEntityMovement().setEntity(((NetworkInput) msg).getSource());
				sendMessage(Type.FSM_ENTITY_MOVEMENT, ((EntityMovementNetworkInput)msg).getEntityMovement());
			} else if (msg instanceof EntityPositionNetworkInput) {
				((EntityPositionNetworkInput) msg).getEntityPosition().setEntity(((NetworkInput) msg).getSource());
				sendMessage(Type.FSM_ENTITY_MOVEMENT, ((EntityPositionNetworkInput)msg).getEntityPosition());
			} else {
				sendMessage(Type.FSM_ENTITY_MOVEMENT, msg);
			}
		} else if (msg instanceof NetworkOutput){
			//send message over TCP to the target entity
			NetworkOutput outputMsg = (NetworkOutput)msg;
			networkHandler.sendMessage(outputMsg.getDestination(), outputMsg);
		} else {
			//i have no idea why we are getting this msg!
		}
		
	}
}
