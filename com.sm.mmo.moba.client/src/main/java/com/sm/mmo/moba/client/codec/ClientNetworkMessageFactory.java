package com.sm.mmo.moba.client.codec;

import com.sm.mmo.moba.client.ByteHelper;
import com.sm.mmo.moba.client.messages.ClientEntityConnectedNetworkInput;
import com.sm.mmo.moba.client.messages.ClientEntityMovementNetworkInput;
import com.sm.mmo.moba.client.messages.ClientEntityPositionNetworkInput;
import com.sm.mmo.moba.client.messages.ClientEntitySpawnNetworkInput;
import com.sm.mmo.moba.domain.Message;
import com.sm.mmo.moba.domain.message.EntityConnected;
import com.sm.mmo.moba.domain.message.EntityDisconnected;
import com.sm.mmo.moba.domain.message.EntityMovement;
import com.sm.mmo.moba.domain.message.EntityPosition;
import com.sm.mmo.moba.domain.message.EntitySpawn;
import com.sm.mmo.moba.domain.message.network.EntityMovementNetworkInput;
import com.sm.mmo.moba.domain.message.network.EntityPositionNetworkInput;
import com.sm.mmo.moba.domain.message.network.NetworkMessage;
import com.sm.mmo.moba.domain.message.network.NetworkOutput;
import com.sm.mmo.moba.domain.message.network.UnrecognizedNetworkInput;

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
			
			case ENTITY_SPAWN:
				ClientEntitySpawnNetworkInput entitySpawn = new ClientEntitySpawnNetworkInput(new EntitySpawn());
				entitySpawn.deserialize(bucket);
				message = entitySpawn;
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
		byte [] data = message.serialize();
		System.out.println("ENCODED: " + ByteHelper.debug(data));
		return data;
	}
	
}
