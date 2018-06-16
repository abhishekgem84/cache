package com.pckage.cache.core;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * This class represent data structure that will be stored in cache which
 * includes actual object that user wants to store with time to live, more
 * parameter can be added to this data structure to maintain any kind of meta
 * data for future use.
 * 
 * @author Abhishek Singh
 * @version 1.0
 */
public final class CachedElement implements Eviction {
	/**
	 * This object store actual object value submitted by user using CachingManger
	 * class
	 */
	private Object cachedObject;
	/**
	 * timeToLive is the time till when cachedObject can exists in cache before
	 * eligible for eviction for cache
	 */
	private long timeToLive;

	/**
	 * Constructor of CachedElement class accepts cacheable object from user
	 * 
	 * @param cachedObject
	 *            any kind of object that can be stored in cache
	 */
	public CachedElement(Object cachedObject) {
		setElement(cachedObject, 0);
	}

	/**
	 * Constructor of CachedElement class accepts cacheable object from user with
	 * its time to live in cache before becoming eligible for eviction from cache.
	 * 
	 * @param cachedObject
	 *            any kind of object that can be stored in cache
	 * @param timeToLive
	 *            time to live in cache before becoming eligible for eviction from
	 *            cache
	 */
	public CachedElement(Object cachedObject, long timeToLive) {
		setElement(cachedObject, timeToLive);
	}
	/**
	 * setElment method of CachedElement class accepts cacheable object from user with
	 * its time to live in cache before becoming eligible for eviction from cache.
	 * 
	 * @param cachedObject
	 *            any kind of object that can be stored in cache
	 * @param timeToLive
	 *            time to live in cache before becoming eligible for eviction from
	 *            cache
	 */
	private void setElement(Object cachedObject, long timeToLive) {
		this.setCachedObject(cachedObject);
		this.setTimeToLive(timeToLive);
	}
	/**
	 * To fetch object from cache.
	 * 
	 *@return cached object
	 */
	public Object getCachedObject() {
		return cachedObject;
	}
	/**
	 * To store object in cache.
	 * 
	 */
	private void setCachedObject(Object cachedObject) {
		this.cachedObject = cachedObject;
	}
	/**
	 * To get time to live of object in cache.
	 * 
	 *@return value of time to live
	 */
	public long getTimeToLive() {
		return timeToLive;
	}
	/**
	 * To to set time to live of object.
	 * 
	 *@return cached object
	 */
	private void setTimeToLive(long timeToLive) {
		if (timeToLive != 0) {
			//future timestamp of object to be stored in cache
			this.timeToLive = System.currentTimeMillis()/* current time in milli seconds */ + (timeToLive * 1000) /* time in seconds into 1000 milli second*/;
		} else {
			//to store object in cache till infinity but can be eligible for LRU
			this.timeToLive = timeToLive;
		}
	}
	/**
	 * Function use to calculate time of eviction of object from cache
	 * 
	 *@return True to start eviction else false
	 */
	public boolean isEvictionable() {
		if (this.timeToLive < System.currentTimeMillis() && this.timeToLive != 0)
			return true;
		return false;
	}

	@Override
	public String toString() {
		return "CachedElement [cachedObject=" + cachedObject + ", timeToLive="
				+ (timeToLive != 0 ? new Date(timeToLive) : "Infinity") + "]";
	}
}
