package com.sm.mmo.moba.client.codec;

import com.sm.mmo.moba.domain.message.network.NetworkOutput;

public class ClientNetworkMessageEncoder {

	public static byte [] encode(NetworkOutput message) {
		return ClientNetworkMessageFactory.serializeCommand(message);
	}

}
