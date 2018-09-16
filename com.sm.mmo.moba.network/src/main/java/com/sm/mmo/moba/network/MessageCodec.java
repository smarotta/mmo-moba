package com.sm.mmo.moba.network;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import com.sm.mmo.moba.network.protobuf.EntityProtos.EntityDestroyed;
import com.sm.mmo.moba.network.protobuf.EntityProtos.EntitySpawned;

@SuppressWarnings("serial")
public class MessageCodec {

	private static final Map<Integer, MessageType> MESSAGE_ID_MAP = new HashMap<Integer, MessageType>(){{
		for(MessageType type:MessageType.values()) {
			this.put((int)type.getId(), type);	
		}
	}};
	
	private static final Map<MessageType, Parser<? extends Message>> MESSAGE_TYPE_MAP = new HashMap<MessageType, Parser<? extends Message>>(){{
		this.put(MessageType.ENTITY_SPAWNED, EntitySpawned.parser());
		this.put(MessageType.ENTITY_DESTROYED, EntityDestroyed.parser());
	}};
	
	public static Message decode(byte [] data) throws InvalidProtocolBufferException {
		Integer messageTypeId = MessageCodecHelper.readShort(data);
		MessageType messageType = MESSAGE_ID_MAP.get(messageTypeId);
		if (messageType != null) {
			Parser<? extends Message> parser = MESSAGE_TYPE_MAP.get(messageType);
			if (parser != null) {
				return parser.parseFrom(data, 2, data.length - 2);
			}
		}
		return null;
	}
	
	public static void encode(Message message, OutputStream out) throws IOException {
		message.writeTo(out);
	}
}
