package com.sm.mmo.moba.qnfsm;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.sm.mmo.moba.domain.Message;
public class FSMFeederController implements FSMFeederBroadcaster {
		
	private final Map<FSMFeeder.Type, Collection<FSMFeeder>> feedersMap = new HashMap<FSMFeeder.Type, Collection<FSMFeeder>>();
	
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
			if (feeders != null) {
				for(FSMFeeder feeder:feeders) {
					feeder.enqueueMessage(msg);
				}
			}
		}		
	}
	
}
