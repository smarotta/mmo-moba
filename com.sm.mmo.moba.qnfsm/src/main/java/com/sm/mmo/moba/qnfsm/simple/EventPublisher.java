package com.sm.mmo.moba.qnfsm.simple;

import java.util.ArrayList;
import java.util.List;

public abstract class EventPublisher {
	
	private final List<EventConsumer> consumers = new ArrayList<EventConsumer>();
	
	abstract public int getPublisherId();
	
	public void addEventListener(EventConsumer consumer) {
		consumers.add(consumer);	
	}
	
	public void publish(Event event) {
		if (getPublisherId() == event.getPublisherId()) {
			for(EventConsumer consumer:consumers) {
				if (consumer.getConsumerId() == event.getConsumerId()) {
					consumer.onEvent(event);
				}
			}
		}
	}
	
}
