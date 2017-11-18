package com.sm.mmo.moba.domain.message.network;

public class UnrecognizedNetworkInput extends NetworkInput {

	@Override
	public SizeHeader getSizeHeader() {
		return SizeHeader.INVALID;
	}

	@Override
	public MessageType getType() {
		return MessageType.UNKNOWN;
	}

	@Override
	public void deserialize(byte[] packet) {
		
	}
}
