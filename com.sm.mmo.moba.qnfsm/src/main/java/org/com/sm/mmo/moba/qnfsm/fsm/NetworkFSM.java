package org.com.sm.mmo.moba.qnfsm.fsm;

import java.util.HashSet;
import java.util.Set;

import org.com.sm.mmo.moba.domain.Entity;
import org.com.sm.mmo.moba.domain.Message;
import org.com.sm.mmo.moba.domain.message.EntityConnected;
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
	
	private NetworkHandler networkHandler;
	
	public NetworkFSM(FSMFeederBroadcaster broadcaster, NetworkHandler networkHandler) {
		super(broadcaster);
		this.networkHandler = networkHandler;	
	}

	@Override
	protected void processInput(Message msg) {
		if (msg instanceof NetworkMessage) {
			if (msg instanceof NetworkOutput) {
				processInput((NetworkOutput)msg);
			} else if (msg instanceof  NetworkInput) {
				processInput((NetworkInput)msg);
			}
			return;
		}
		
		switch(msg.getType()) {
			case ENTITY_MOVEMENT:
			case ENTITY_POSITION:
				//processInput((NetworkMessage)msg);
			break;
			case ENTITY_CONNECTED:
			case ENTITY_DISCONNECTED:
				sendMessage(Type.FSM_GAME_LOGIC, msg);
				sendMessage(Type.FSM_ENTITY_MOVEMENT, msg);
			break;
			default:
				//do nothing
		}
	}
	
	private void processInput(NetworkOutput msg) {
		networkHandler.sendMessage(msg.getDestination(), msg);
	}
	
	private void processInput(NetworkInput msg) {
		if (msg instanceof EntityMovementNetworkInput) {
			((EntityMovementNetworkInput) msg).getEntityMovement().setEntity(msg.getSource());
			sendMessage(Type.FSM_ENTITY_MOVEMENT, ((EntityMovementNetworkInput)msg).getEntityMovement());
		} else if (msg instanceof EntityPositionNetworkInput) {
			((EntityPositionNetworkInput) msg).getEntityPosition().setEntity(msg.getSource());
			sendMessage(Type.FSM_ENTITY_MOVEMENT, ((EntityPositionNetworkInput)msg).getEntityPosition());
		}
	}
}
