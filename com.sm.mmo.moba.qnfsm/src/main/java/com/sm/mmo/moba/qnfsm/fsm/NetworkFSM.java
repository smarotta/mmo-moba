package com.sm.mmo.moba.qnfsm.fsm;

import java.util.HashSet;
import java.util.Set;

import com.sm.mmo.moba.domain.Entity;
import com.sm.mmo.moba.domain.Message;
import com.sm.mmo.moba.domain.message.EntityConnected;
import com.sm.mmo.moba.domain.message.EntityMovement;
import com.sm.mmo.moba.domain.message.EntityPosition;
import com.sm.mmo.moba.domain.message.network.EntityConnectedNetworkOutput;
import com.sm.mmo.moba.domain.message.network.EntityDisconnectedNetworkOutput;
import com.sm.mmo.moba.domain.message.network.EntityMovementNetworkInput;
import com.sm.mmo.moba.domain.message.network.EntityPositionNetworkInput;
import com.sm.mmo.moba.domain.message.network.NetworkInput;
import com.sm.mmo.moba.domain.message.network.NetworkMessage;
import com.sm.mmo.moba.domain.message.network.NetworkOutput;
import com.sm.mmo.moba.qnfsm.FSM;
import com.sm.mmo.moba.qnfsm.FSMFeederBroadcaster;
import com.sm.mmo.moba.qnfsm.FSMFeeder.Type;

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
			case ENTITY_CONNECTED:
			case ENTITY_DISCONNECTED:
				sendMessage(Type.FSM_GAME_LOGIC, msg);
			break;
			default:
		}
	}
	
	private void processInput(NetworkOutput msg) {
		networkHandler.sendMessage(msg.getDestination(), msg);
	}
	
	private void processInput(NetworkInput msg) {
		if (msg instanceof EntityMovementNetworkInput) {
			((EntityMovementNetworkInput) msg).getEntityMovement().setEntity(msg.getSource());
			sendMessage(Type.FSM_MOVEMENT, ((EntityMovementNetworkInput)msg).getEntityMovement());
		} else if (msg instanceof EntityPositionNetworkInput) {
			((EntityPositionNetworkInput) msg).getEntityPosition().setEntity(msg.getSource());
			sendMessage(Type.FSM_MOVEMENT, ((EntityPositionNetworkInput)msg).getEntityPosition());
		}
	}
}
