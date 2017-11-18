package com.sm.mmo.moba.qnfsm.feeder;

import java.util.UUID;

import com.sm.mmo.moba.qnfsm.FSM;
import com.sm.mmo.moba.qnfsm.FSMFeeder;
import com.sm.mmo.moba.qnfsm.FSMFeederBroadcaster;

public class GameLogicFSMFeeder extends FSMFeeder{

	public GameLogicFSMFeeder(UUID id, FSM fsm, FSMFeederBroadcaster broadcaster) {
		super(id, fsm, broadcaster);
	}

	@Override
	public Type getType() {
		return Type.FSM_GAME_LOGIC;
	}

}
