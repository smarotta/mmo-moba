package org.com.sm.mmo.moba.qnfsm;

import org.com.sm.mmo.moba.domain.Message;

public abstract class FSM {

	private State state;
	private FSMFeederBroadcaster broadcaster;
	
	public FSM(FSMFeederBroadcaster broadcaster) {
		this.broadcaster = broadcaster;
	}
	
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	protected void update(Message msg) {
		if (msg != null) {
			processInput(msg);
		}
		
		if (state != null) {
			state.update();
		}
	}
	
	protected FSMFeederBroadcaster getBroadcaster() {
		return broadcaster;
	}
	
	abstract protected void processInput(Message msg);
	
}
