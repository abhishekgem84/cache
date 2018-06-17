package com.pckage.cache;

import java.util.Date;

import com.pckage.cache.core.Cache;
import com.pckage.cache.core.CachedElement;
import com.pckage.cache.core.LRUCache;

/**
 * This class is responsible for creation of Object of CachingManager.
 * CachingManager helps interaction between caching system.
 * 
 * @author Abhishek Singh
 * @version 1.0
 */
public final class CachingManager {

	/** Singleton instance of CachingManager class */
	private static CachingManager cachingManager;

	private static Cache<String, CachedElement> CACHE;

	/** To support singleton object creation */
	private CachingManager() {
		CACHE = new LRUCache<>(10);// Size of cache has been hard defined can be upload via property file.
		CACHE.startEviction();
	}

	/**
	 * This function will provide instance of CachingManager
	 * 
	 * @return Object of CachingManager
	 */
	public static CachingManager getCachingManager() {
		if (cachingManager == null) {
			synchronized (CachingManager.class) {
				if (cachingManager == null) {
					cachingManager = new CachingManager();
				}
			}
		}
		return cachingManager;
	}

	/**
	 * This function will be called to store the object to cache, and object will be
	 * stored for indefinite period of time.
	 * 
	 * @param key
	 *            as string as an identifier for the stored object in cache, not be
	 *            null
	 * @param value
	 *            as object to be stored in cache
	 * @return 1 if object stored in cache
	 */
	public int store(String key, Object value) {
		if (key == null)
			throw new NullPointerException("Key cannot be null");
		if(value==null)
			throw new NullPointerException("Value cannot be null");
		CACHE.addElement(key, new CachedElement(value));
		return 1;
	}

	/**
	 * This function will be called to store the object to cache, and object will be
	 * stored for definite period of time if value of timeToLive is not zero.
	 * 
	 * @param key
	 *            as string as an identifier for the stored object in cache, not be
	 *            null
	 * @param value
	 *            as object to be stored in cache, cannot be null
	 * @param timeToLive
	 *            for how much time is seconds object will be stored in cache, if
	 *            value is zero then object will be store for indefinite period of
	 *            time.
	 * @return 1 if object stored in cache
	 */
	public int store(String key, Object value, long timeToLive) {
		if (key == null)
			throw new NullPointerException("Key cannot be null");
		if(value==null)
			throw new NullPointerException("Value cannot be null");
		CACHE.addElement(key, new CachedElement(value, timeToLive));
		return 1;
	}

	/**
	 * This function will be used to fetch object from cache based upon its
	 * existence. It will return null if no value is found against given key. It will also evict element from cache if its time to live has expired.
	 * 
	 * @param key
	 *            identifier that was used while storing object in cache
	 * @return stored object in cache.
	 */
	public Object get(String key) {
		if (key == null)
			throw new NullPointerException("Key cannot be null");
		CachedElement element = CACHE.getElement(key);
		if (element !=null && element.getTimeToLive() < System.currentTimeMillis() && element.getTimeToLive() != 0) {
			System.out.println("Auto Eviction happened for key " + key + " value " + CACHE.removeElement(key));
			element = null;
		}
		return element == null ? null : element.getCachedObject();
	}
}
