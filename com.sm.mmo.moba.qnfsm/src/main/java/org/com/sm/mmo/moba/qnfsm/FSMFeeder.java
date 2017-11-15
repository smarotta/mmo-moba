package org.com.sm.mmo.moba.qnfsm;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import org.com.sm.mmo.moba.domain.Message;

public abstract class FSMFeeder {

	public static enum Type {
		FSM_GAME_LOGIC,
		FSM_ENTITY_MOVEMENT,
		FSM_NETWORK
	}
	
	private UUID id;
	private FSMFeederBroadcaster broadcaster;
	private FSM fsm;
	private final BlockingQueue<Message> queue = new LinkedBlockingDeque<Message>();

	private volatile boolean stopTicking = false;
	private Thread tickingThread = null;
	
	public FSMFeeder(UUID id, FSM fsm, FSMFeederBroadcaster broadcaster) {
		this.id = id;
		this.fsm = fsm;
		this.broadcaster = broadcaster;
		
		tickingThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						if (!stopTicking) {
							feedMessage();
						} else {
							try {
								Thread.sleep(1000l);
							} catch (InterruptedException e) { }
						}
					} catch (Exception e) { }
				}
			}
		});
	}
	
	public FSMFeederBroadcaster getBroadcaster() {
		return broadcaster;
	}

	public void setBroadcaster(FSMFeederBroadcaster broadcaster) {
		this.broadcaster = broadcaster;
	}

	public void enqueueMessage(Message msg) {
		queue.add(msg);
	}
	
	public void feedMessage() {
		Message msg = null;
		try {
			msg = queue.take();
		} catch (InterruptedException e) { }
		fsm.update(msg);
	}
	
	public synchronized void startTicking() {
		if (!tickingThread.isAlive()) {
			tickingThread.setName(id.toString());
			tickingThread.start();
		}
	}
	
	public void stopTicking() {
		stopTicking = true;
	}
	
	abstract public Type getType();
}
