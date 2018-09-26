package com.sm.mmo.moba.qnfsm.simple.entity.state;

import java.util.HashSet;
import java.util.Set;

import com.sm.mmo.moba.qnfsm.simple.Event;
import com.sm.mmo.moba.qnfsm.simple.EventPublisher;
import com.sm.mmo.moba.qnfsm.simple.Machine;
import com.sm.mmo.moba.qnfsm.simple.State;
import com.sm.mmo.moba.qnfsm.simple.entity.event.MovementStartEvent;

public class IdleState extends State {

	@SuppressWarnings("serial")
	private static final Set<Integer> CONSUMABLES = new HashSet<Integer>() {{
		add(MovementStartEvent.ID);
	}};
	
	@Override
	public boolean canConsume(Event event) {
		return CONSUMABLES.contains(event.getId());
	}

	@Override
	public State consume(EventPublisher publisher, Machine target, Event event) {
		switch(event.getId()) {
			case MovementStartEvent.ID:
				return new MovingState((MovementStartEvent) event);
			default:
				return this;
		}
	}

}
