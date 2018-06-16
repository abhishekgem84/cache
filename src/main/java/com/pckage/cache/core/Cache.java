package com.pckage.cache.core;

public interface Cache<Key, Value> {
	public Value getElement(Key key);

	public boolean addElement(Key key,Value value);
	
	public void startEviction();
	
	public Value removeElement(Key key);
}
