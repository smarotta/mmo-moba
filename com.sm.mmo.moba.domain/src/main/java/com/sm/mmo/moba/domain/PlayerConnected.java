package com.sm.mmo.moba.domain;

public class ConnectedPlayer<Connection> extends Entity {

	private Connection connection;
	
	public ConnectedPlayer(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
}
