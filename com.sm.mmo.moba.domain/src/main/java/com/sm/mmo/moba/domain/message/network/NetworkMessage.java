package com.sm.mmo.moba.domain.message.network;

import com.sm.mmo.moba.domain.Message;

public abstract class NetworkMessage extends Message {

	public enum SizeHeader {
		C1((byte)0xc1), //2 bytes to represent the size of the package (short)
		C2((byte)0xc2), //4 bytes to represent the size of the package (int)
		C3((byte)0xc3), //8 bytes to represent the size of the package (long) NOT SUPPORTED FOR NOW
		
		INVALID((byte)0x00);
		
		private byte value;
		
		public byte getByteValue() {
			return value;
		}
		
		private SizeHeader(byte value) {
			this.value = value;
		}
		
		public static SizeHeader getTypeForValue(byte value) {
			for(SizeHeader type:SizeHeader.values()) {
				if (type.getByteValue() == value) {
					return type;
				}
			}
			return SizeHeader.INVALID;
		}
	}

	public abstract SizeHeader getSizeHeader();
}
