package com.pckage.cache.core;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
/**
* This class is responsible running thread to run eviction cycle, that will remove all expired objects from cache based upon time to live parameter value.
* system.
* @author Abhishek Singh
* @version 1.0
*/
public class EvictionThread<Key, Value> implements Runnable {
	/** 
	 * Hash Map which will be use to cache object.
	 * */
	private ConcurrentHashMap<Key, Value> cache;
	/** 
	 * LRUCache.class.
	 * */
	private LRUCache<Key, Value> LRUcache;
	/** 
	 * Constructor that accepts LRUCache.class.
	 * @param LRUcache LRUCache.class
	 * */
	public EvictionThread(LRUCache<Key, Value> LRUcache) {
		this.LRUcache = LRUcache;
		this.cache=this.LRUcache.getCache();
	}

	@Override
	public void run() {
		try {
			System.out.println("Starting process of eviction.....");
			while (true) {
				//iterate all elements in hash map that is cache and if found any element that has expired time to live then remove that element from cache
				cache.keySet().parallelStream().forEach(k -> {
					CachedElement element = (CachedElement) cache.get(k);
					long test=System.currentTimeMillis();
					if (element.getTimeToLive() < test && element.getTimeToLive() != 0) {
						System.out.println("Eviction happened for key " + k + " value " + cache.remove(k)+" at "+ new Date(test));
					}
				});
				Thread.sleep(1000l);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}

}
