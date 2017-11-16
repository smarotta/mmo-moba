package org.com.sm.mmo.moba.qnfsm.feeder;

import java.util.UUID;

import org.com.sm.mmo.moba.qnfsm.FSM;
import org.com.sm.mmo.moba.qnfsm.FSMFeeder;
import org.com.sm.mmo.moba.qnfsm.FSMFeederBroadcaster;

public class NetworkFSMFeeder extends FSMFeeder{

	public NetworkFSMFeeder(UUID id, FSM fsm, FSMFeederBroadcaster broadcaster) {
		super(id, fsm, broadcaster);
	}

	@Override
	public Type getType() {
		return Type.FSM_NETWORK;
	}

}
