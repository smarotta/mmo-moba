package com.sm.mmo.moba.client.codec;

import org.com.sm.mmo.moba.domain.Message;
import org.com.sm.mmo.moba.domain.message.EntityConnected;
import org.com.sm.mmo.moba.domain.message.EntityDisconnected;
import org.com.sm.mmo.moba.domain.message.EntityMovement;
import org.com.sm.mmo.moba.domain.message.EntityPosition;
import org.com.sm.mmo.moba.domain.message.network.EntityMovementNetworkInput;
import org.com.sm.mmo.moba.domain.message.network.EntityPositionNetworkInput;
import org.com.sm.mmo.moba.domain.message.network.NetworkMessage;
import org.com.sm.mmo.moba.domain.message.network.NetworkOutput;
import org.com.sm.mmo.moba.domain.message.network.UnrecognizedNetworkInput;

import com.sm.mmo.moba.client.messages.ClientEntityConnectedNetworkInput;
import com.sm.mmo.moba.client.messages.ClientEntityMovementNetworkInput;
import com.sm.mmo.moba.client.messages.ClientEntityPositionNetworkInput;

public class ClientNetworkMessageFactory {

	public static Message deserialize(NetworkMessage.SizeHeader sizeHeader, byte [] bucket) {
		Message message = null;
		
		switch(sizeHeader) {
			case C1:
				message = deserializeLightMessage(bucket);
				break;
				
			case C2:
			case C3:
			default:
				message = new UnrecognizedNetworkInput();
		}
		
		return message;
	}
	
	private static Message deserializeLightMessage(byte [] bucket) {
		Message message = null;
		
		switch(Message.MessageType.getTypeForValue(bucket[3])) {
			
			case ENTITY_CONNECTED:
				ClientEntityConnectedNetworkInput entityConnected = new ClientEntityConnectedNetworkInput(new EntityConnected());
				entityConnected.deserialize(bucket);
				message = entityConnected;
			break;
			
			case ENTITY_DISCONNECTED:
				EntityDisconnected entityDisconnected = new EntityDisconnected();
				//entityDisconnected.deserialize(bucket);
				message = entityDisconnected;
			break;
			
			case ENTITY_MOVEMENT:
				ClientEntityMovementNetworkInput entityMovement = new ClientEntityMovementNetworkInput(new EntityMovement());
				entityMovement.deserialize(bucket);
				message = entityMovement;
			break;
			
			case ENTITY_POSITION:
				ClientEntityPositionNetworkInput entityPosition = new ClientEntityPositionNetworkInput(new EntityPosition());
				entityPosition.deserialize(bucket);
				message = entityPosition;
			break;
			
			case INTERNAL:
			case UNKNOWN:
			default:
				message = new UnrecognizedNetworkInput();
				break;
		}
		
		return message;
	}
	
	public static byte [] serializeCommand(NetworkOutput message) {
		return message.serialize();
	}
	
}
