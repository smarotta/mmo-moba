package org.com.sm.mmo.moba.qnfsm;

import org.com.sm.mmo.moba.domain.Message;
import org.com.sm.mmo.moba.qnfsm.FSMFeeder.Type;

public abstract class FSM {

	private FSMFeederBroadcaster broadcaster;
	
	public FSM(FSMFeederBroadcaster broadcaster) {
		this.broadcaster = broadcaster;
	}

	protected void update(Message msg) {
		if (msg != null) {
			processInput(msg);
		}
	}
	
	protected void sendMessage(Type type, Message msg) {
		broadcaster.sendMessage(type, msg);
	}
	
	protected void sendMessage(Type type, Message msg, long scheduledTimeStamp) {
		broadcaster.sendMessage(type, msg, scheduledTimeStamp);
	}
	
	abstract protected void processInput(Message msg);
	
}
