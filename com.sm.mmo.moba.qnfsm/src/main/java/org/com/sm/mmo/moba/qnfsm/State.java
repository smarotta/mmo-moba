package org.com.sm.mmo.moba.qnfsm;

public abstract class State {

	private FSM fsm;
	
	public State(FSM fsm) {
		this.fsm = fsm;
	}
	
	protected FSM getFsm() {
		return fsm;
	}
	
	public abstract String getId();
	
	public abstract void update();
	
	
	
}
