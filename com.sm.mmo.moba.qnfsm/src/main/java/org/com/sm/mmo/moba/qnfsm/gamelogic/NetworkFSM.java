package org.com.sm.mmo.moba.qnfsm.gamelogic;

import org.com.sm.mmo.moba.domain.Message;
import org.com.sm.mmo.moba.qnfsm.FSM;
import org.com.sm.mmo.moba.qnfsm.FSMFeederBroadcaster;

public class NetworkFSM extends FSM {

	public NetworkFSM(FSMFeederBroadcaster broadcaster) {
		super(broadcaster);
	}

	@Override
	protected void processInput(Message msg) {
		
	}

}
