package com.sm.mmo.moba.qnfsm.simple;

public interface EventConsumer {
	int getConsumerId();
	void onEvent(Event event);
}
