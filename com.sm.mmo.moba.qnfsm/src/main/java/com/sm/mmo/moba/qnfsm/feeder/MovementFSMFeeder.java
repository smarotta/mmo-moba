package com.sm.mmo.moba.qnfsm.feeder;

import java.util.UUID;

import com.sm.mmo.moba.qnfsm.FSM;
import com.sm.mmo.moba.qnfsm.FSMFeeder;
import com.sm.mmo.moba.qnfsm.FSMFeederBroadcaster;

public class MovementFSMFeeder extends FSMFeeder{

	public MovementFSMFeeder(UUID id, FSM fsm, FSMFeederBroadcaster broadcaster) {
		super(id, fsm, broadcaster);
	}

	@Override
	public Type getType() {
		return Type.FSM_MOVEMENT;
	}

}
