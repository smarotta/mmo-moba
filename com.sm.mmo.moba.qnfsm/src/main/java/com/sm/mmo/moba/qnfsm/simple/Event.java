package com.sm.mmo.moba.qnfsm.simple;

public abstract class Event {
	
	private int id;
	private int consumerId;
	private int publisherId;
	
	public Event() {

	}
	
	public Event(int id, int consumerId, int publisherId) {
		this.id = id;
		this.consumerId = consumerId;
		this.publisherId = publisherId;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getConsumerId() {
		return consumerId;
	}
	
	public void setConsumerId(int consumerId) {
		this.consumerId = consumerId;
	}
	
	public int getPublisherId() {
		return publisherId;
	}
	
	public void setPublisherId(int publisherId) {
		this.publisherId = publisherId;
	}
}
