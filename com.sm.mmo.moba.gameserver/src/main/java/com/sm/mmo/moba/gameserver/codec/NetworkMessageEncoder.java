package com.sm.mmo.moba.gameserver.codec;

import com.sm.mmo.moba.domain.message.network.NetworkOutput;

public class NetworkMessageEncoder {

	public static byte [] encode(NetworkOutput message) {
		return NetworkMessageFactory.serializeCommand(message);
	}

}
