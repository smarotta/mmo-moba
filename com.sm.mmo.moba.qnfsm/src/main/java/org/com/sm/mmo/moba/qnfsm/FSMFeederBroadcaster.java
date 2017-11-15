package org.com.sm.mmo.moba.qnfsm;

import org.com.sm.mmo.moba.domain.Message;
import org.com.sm.mmo.moba.qnfsm.FSMFeeder.Type;

public interface FSMFeederBroadcaster {
	void sendMessage(FSMFeeder.Type type, Message msg);
	void sendMessage(Type type, Message msg, long scheduledTimeStamp);
}
