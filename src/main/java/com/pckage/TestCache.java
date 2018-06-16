package com.pckage;

import com.pckage.cache.CachingManager;

public class TestCache {

	public static void main(String[] args) {
		try {
			//Creating caching Manager
			CachingManager cachingManager=CachingManager.getCachingManager();
			//Adding objects in cache
			cachingManager.store("1", new String("First Value"),0);
			cachingManager.store("2", new String("Second Value"),10);
			cachingManager.store("3", new String("Third Value"),20);
			cachingManager.store("4", new String("Forth Value"),30);
			cachingManager.store("5", new String("Fifth Value"),40);
			cachingManager.store("6", new String("Sixth Value"),40);
			cachingManager.store("7", new String("Seventh Value"),60);
			cachingManager.store("8", new String("Eight Value"),70);
			cachingManager.store("9", new String("Nine Value"),80);
			cachingManager.store("10", new String("Ten Value"),40);
			cachingManager.store("11", new String("Eleven Value"),100);
			cachingManager.store("12", new String("Twelve Value"),110);
			//Fetching elements from cache
			System.out.println("Element Received from Cache "+cachingManager.get("8"));
			System.out.println("Element Received from Cache "+cachingManager.get("99"));
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

}
