package com.sm.mmo.moba.domain.message;


public class EntityConnected extends EntityUpdate {

	private boolean currentPlayer;
	
	@Override
	public MessageType getType() {
		return MessageType.ENTITY_CONNECTED;
	}

	public boolean isCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(boolean currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

}
