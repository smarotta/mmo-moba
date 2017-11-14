package org.com.sm.mmo.moba.qnfsm;

import org.com.sm.mmo.moba.domain.Message;

public interface FSMFeederBroadcaster {
	void sendMessage(FSMFeeder.Type type, Message msg);
}
