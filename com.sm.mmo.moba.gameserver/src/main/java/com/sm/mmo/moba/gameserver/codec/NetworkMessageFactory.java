package com.sm.mmo.moba.gameserver.codec;

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

public class NetworkMessageFactory {

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
				EntityConnected entityConnected = new EntityConnected();
				message = entityConnected;
			break;
			
			case ENTITY_DISCONNECTED:
				EntityDisconnected entityDisconnected = new EntityDisconnected();
				message = entityDisconnected;
			break;
			
			case ENTITY_MOVEMENT:
				EntityMovementNetworkInput entityMovement = new EntityMovementNetworkInput(new EntityMovement());
				entityMovement.deserialize(bucket);
				message = entityMovement;
			break;
			
			case ENTITY_POSITION:
				EntityPositionNetworkInput entityPosition = new EntityPositionNetworkInput(new EntityPosition());
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
