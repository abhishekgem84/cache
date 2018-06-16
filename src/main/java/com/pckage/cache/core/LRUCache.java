package com.pckage.cache.core;


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
* This class is responsible running of LRU based caching system.
* system.
* @author Abhishek Singh
* @version 1.0
*/
public final class LRUCache<Key,Value> implements Cache<Key, Value>{
	/** 
	 * Hash Map which will be use to cache object.
	 * */
	private ConcurrentHashMap<Key, Value> cache=new ConcurrentHashMap<>();
	/**
	 * Linked Queue will be use to maintain LRU stack
	 * */
	private ConcurrentLinkedQueue<Key> queue=new ConcurrentLinkedQueue<>();
	/**
	 * To perform read write locks for transactions
	 * */
	private ReadWriteLock readWriteLock=new ReentrantReadWriteLock();
	private Lock readLock=readWriteLock.readLock();
	private Lock writeLock=readWriteLock.writeLock();
	/**
	 * Maximum size of Cache in memory
	 * */
	private final int MAX_CACHE_SIZE;
	/**
	 * Constructor to set Max cache size
	 * 
	 * @param MAX_CACHE_SIZE to set Maximum size of Cache in memory 
	 * */
	public LRUCache(final int MAX_CACHE_SIZE) {
		this.MAX_CACHE_SIZE=MAX_CACHE_SIZE;
	}
	/**
	 * This function will start eviction cycle to remove all the objects that have surpassed their expire time in cache
	 * */
	public void startEviction(){
		try {
			//new EvictionThread<Key,Value>(this.cache);
			new Thread(new EvictionThread<Key,Value>(this)).start();
		}catch(Exception e) {
			
		}
		
	}
	/**
	 * To get cache hash map
	 * 
	 * @return Hash Map
	 * */
	protected ConcurrentHashMap<Key, Value> getCache(){
		return this.cache;
	}
	/**
	 * To remove element from cache
	 * @param key identifier that was used to store object in cache
	 * @return object/value that was stored in cache
	 * */
	public Value removeElement(Key key) {
		writeLock.lock();
		Value v=null;
		try {
			if(cache.containsKey(key)) {
				v=cache.remove(key);
				queue.remove(key);
				System.out.println("Object removed from cache with key "+key + " value "+ v);
			}
		}finally {
			writeLock.unlock();
		}
		return v;
	}
	/**
	 * To get element from cache
	 * @param key identifier that was used to store object in cache
	 * @return object/value that was stored in cache
	 * */
	public Value getElement(Key key) {
		readLock.lock();
		Value v=null;
		try {
			if(cache.containsKey(key)) {
				queue.remove(key);
				v=cache.get(key);
				queue.add(key);
				System.out.println("Object fetched from cache with key "+key + " value "+ v);
			}
		}finally {
			readLock.unlock();
		}
		return v;
	}
	/**
	 * To remove element from cache
	 * @param key identifier that was used to store object in cache
	 * @param value actual object to be stored in cache
	 * @return true if object was added in cache 
	 * */
	public boolean addElement(Key key,Value value) {
		writeLock.lock();
		try {
			if(cache.containsKey(key)) {
				queue.remove(key);
			}
			if(cache.size()>=MAX_CACHE_SIZE) {
				System.out.println("Object removed from cache because of MAX_CACHE_SIZE reached with value "+cache.remove(queue.poll()));
			}
			cache.put(key, value);
			queue.add(key);
			System.out.println("Object added in cache with key "+key+" value "+value);
			return true;
		}finally{
			writeLock.unlock();
		}
	}
}
