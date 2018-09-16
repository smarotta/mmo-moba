package com.sm.mmo.moba.domain.message;

import com.sm.mmo.moba.domain.Message;

public class NetworkOutput<Destination, RawMessage> extends Message {

	private Destination destination;
	private RawMessage rawMessage;
	
	public NetworkOutput(Destination destination, RawMessage rawMessage) {
		this.destination = destination;
		this.rawMessage = rawMessage;
	}
	
	public Destination getDestination() {
		return destination;
	}
	
	public void setDestination(Destination destination) {
		this.destination = destination;
	}
	
	public RawMessage getRawMessage() {
		return rawMessage;
	}
	
	public void setRawMessage(RawMessage rawMessage) {
		this.rawMessage = rawMessage;
	}

	@Override
	public MessageType getType() {
		return MessageType.NETWORK_INPUT;
	}	
}
