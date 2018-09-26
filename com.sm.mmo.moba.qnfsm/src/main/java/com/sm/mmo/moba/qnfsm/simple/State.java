package com.sm.mmo.moba.qnfsm.simple;

public abstract class State {
	
	abstract public boolean canConsume(Event event);
	
	abstract public State consume(EventPublisher publisher, Machine target, Event event);
	
}
