package com.sm.mmo.moba.domain.message;

import com.sm.mmo.moba.domain.Message;

public class NetworkInput<Source, RawMessage> extends Message {

	private Source source;
	private RawMessage rawMessage;
	
	public NetworkInput(Source source, RawMessage rawMessage) {
		this.source = source;
		this.rawMessage = rawMessage;
	}
	
	public Source getSource() {
		return source;
	}
	
	public void setSource(Source source) {
		this.source = source;
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
