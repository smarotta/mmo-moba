package com.sm.mmo.moba.qnfsm;

import com.sm.mmo.moba.domain.Message;
import com.sm.mmo.moba.qnfsm.FSMFeeder.Type;

public interface FSMFeederBroadcaster {
	void sendMessage(FSMFeeder.Type type, Message msg);
}
