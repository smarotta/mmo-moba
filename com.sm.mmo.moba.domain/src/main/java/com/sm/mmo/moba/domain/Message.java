package com.sm.mmo.moba.domain;


public abstract class Message {

	public static enum MessageType {
		ENTITY_SPAWN(0xA0),
		ENTITY_MOVEMENT(0xA1),
		ENTITY_POSITION(0xA2),
		
		ENTITY_CONNECTED(0x01),
		ENTITY_DISCONNECTED(0x02),
		
		
		/** new ones **/
		ENTITY_POSITION_SETUP(0xA3),
		ENTITY_MEET(0xA4),
		ENTITY_DAMAGE(0xB0),
		ENTITY_DEATH(0xB1),
		
		
		
		UNKNOWN(0x00),
		INTERNAL(0xFF);
		
		private byte id;
		
		MessageType(int id) {
			this.id = (byte)id;
		}
		
		public byte getId() {
			return id;
		}
		
		public static MessageType getTypeForValue(byte id) {
			for(MessageType mt:MessageType.values()) {
				if (mt.getId() == id) {
					return mt;
				}
			}
			return UNKNOWN;
		}
	}
	
	private boolean isDiscarted;
	
	public boolean isDiscarted() {
		return isDiscarted;
	}

	public void setDiscarted(boolean isDiscarted) {
		this.isDiscarted = isDiscarted;
	}

	abstract public MessageType getType();
	
	
	
}
