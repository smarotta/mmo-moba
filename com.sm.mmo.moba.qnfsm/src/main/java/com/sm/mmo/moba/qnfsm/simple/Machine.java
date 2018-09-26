package com.sm.mmo.moba.qnfsm.simple;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public abstract class Machine implements EventConsumer {

	private final BlockingQueue<Event> queue = new LinkedBlockingDeque<Event>();
	
	private EventPublisher publisher;
	private String name;
	private State state;
	private Thread eventConsumerThread = null;
	
	public Machine(EventPublisher publisher, String name, State initialState) {
		this.publisher = publisher;
		this.publisher.addEventListener(this);
		this.name = name;
		this.state = initialState;
		this.initializeConsumerThread();
	}
	
	private void initializeConsumerThread() {
		eventConsumerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(isActive()) {
					try {
						consumeEvent();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		eventConsumerThread.setName(name);
		eventConsumerThread.start();
	}
	
	public State getState() {
		return state;
	}
	
	public boolean isActive() {
		return state != null;
	}
	
	public void onEvent(Event event) {
		try {
			this.queue.put(event);
		} catch (InterruptedException e) { }
	}
	
	private void consumeEvent() throws InterruptedException {
		Event event = queue.take();
		if (state.canConsume(event)) {
			State newState = state.consume(publisher, this, event);
			if (newState != state) {
				System.out.println("State changed from: " + state.getClass().getSimpleName() + " to " + newState.getClass().getSimpleName());
				state = newState;
			}
		} else {
			System.out.println("State " + state.getClass().getSimpleName() + " can't consume " + event.getClass().getSimpleName());
		}
	}
}
