package org.com.sm.mmo.moba.domain;

import java.util.UUID;

public abstract class Identifiable {

	private UUID id;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
}
