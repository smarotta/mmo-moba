package org.com.sm.mmo.moba.domain;


public abstract class Message {

	public static enum MessageType {
		ENTITY_MOVEMENT((short)0xA1),
		ENTITY_POSITION((short)0xA2),
		UNKNOWN((short)0x00);
		
		private short id;
		
		MessageType(short id) {
			this.id = id;
		}
		
		public short getId() {
			return id;
		}
		
		public MessageType fromId(short id) {
			for(MessageType mt:MessageType.values()) {
				if (mt.getId() == id) {
					return mt;
				}
			}
			return UNKNOWN;
		}
	}
	
	abstract public MessageType getType();
	
	
	
}
