package com.sm.mmo.moba.qnfsm;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.sm.mmo.moba.domain.Message;
import com.sm.mmo.moba.qnfsm.FSMFeeder.Type;
public class FSMFeederController implements FSMFeederBroadcaster {
	
	private static class DelayedMessageRunnable implements Runnable {
		private Type type;
		private Message msg;
		private FSMFeederBroadcaster broadcaster;
		
		public DelayedMessageRunnable(FSMFeederBroadcaster broadcaster, Type type, Message msg) {
			this.type = type;
			this.msg = msg;
			this.broadcaster = broadcaster;
		}
		
		@Override
		public void run() {
			if (!msg.isDiscarted()) {
				broadcaster.sendMessage(type, msg);
			}
		}
	}
	
	private final Map<FSMFeeder.Type, Collection<FSMFeeder>> feedersMap = new HashMap<FSMFeeder.Type, Collection<FSMFeeder>>();
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);
	
	public void register(FSMFeeder node) {
		if (!feedersMap.containsKey(node.getType())){
			feedersMap.put(node.getType(), new HashSet<FSMFeeder>());
		}
		feedersMap.get(node.getType()).add(node);
		node.setBroadcaster(this);
		node.startTicking();
	}

	@Override
	public void sendMessage(FSMFeeder.Type type, Message msg) {
		if (type != null) {
			Collection<FSMFeeder> feeders = feedersMap.get(type);
			//System.out.println("Found feeders " + (feeders == null ? "0":""+feeders.size()) + " for type " + type);
			if (feeders != null) {
				for(FSMFeeder feeder:feeders) {
					feeder.enqueueMessage(msg);
				}
			}
		}		
	}

	@Override
	public void sendMessage(Type type, Message msg, long scheduledTimeStamp) {
		if (scheduledTimeStamp <= System.currentTimeMillis() + 10l) {
			sendMessage(type, msg);
		} else {
			scheduler.schedule(
					new DelayedMessageRunnable(this, type, msg), 
					scheduledTimeStamp - System.currentTimeMillis(), 
					TimeUnit.MILLISECONDS);
		}
	}
	
}
