package com.java8.features.sample.enummap;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Assert;

public class EnumMapSample {

	public static void main(String[] args) {
		/**
		 * Example that shows the EnumMap usage.
		 * Mapping the days of the week associated with an activity
		 */
		EnumMap<DayOfWeek, String> activityMap = new EnumMap<>(DayOfWeek.class);
		activityMap.put(DayOfWeek.MONDAY, "Soccer");
		activityMap.put(DayOfWeek.TUESDAY, "Basketball");
		
		/**
		 * Creating enumMap copies using the constructor
		 */
		EnumMap<DayOfWeek, String> activityMapCopy = new EnumMap<>(activityMap);
		Assert.assertEquals(2, activityMapCopy.size());
		Assert.assertEquals("Soccer", activityMapCopy.get(DayOfWeek.MONDAY));
		Assert.assertEquals("Basketball", activityMapCopy.get(DayOfWeek.TUESDAY));
		
		/**
		 * Passing an ordinary map <Enum, String> to the enummap constructor
		 * that allows create an enumap copy 
		 */
		Map<DayOfWeek, String> ordinaryMap = new HashMap<>();
		ordinaryMap.put(DayOfWeek.MONDAY, "Soccer");
		
		EnumMap enumMap = new EnumMap(ordinaryMap);
		Assert.assertEquals(1, enumMap.size());
		Assert.assertEquals("Soccer", enumMap.get(DayOfWeek.MONDAY));
		
		/***
		 * adding new elements using put method and retrieving elements using get method
		 */
		activityMap.put(DayOfWeek.THURSDAY, "Karate");
		Assert.assertEquals("Basketball", activityMap.get(DayOfWeek.TUESDAY));
		
		/**
		 * checking elements using containsKey and containsValue method
		 */
		Assert.assertEquals(true, activityMap.containsKey(DayOfWeek.MONDAY));
		Assert.assertEquals(true, activityMap.containsValue("Basketball"));
		
		/**
		 * Using null as a valid value in an enummap
		 */
		Assert.assertEquals(false, activityMap.containsKey(DayOfWeek.SATURDAY));
		Assert.assertEquals(false, activityMap.containsValue(null));
		activityMap.put(DayOfWeek.SATURDAY, null);
		Assert.assertEquals(true, activityMap.containsKey(DayOfWeek.SATURDAY));
		Assert.assertEquals(true, activityMap.containsValue(null));
		
		/**
		 * removing elements
		 */
		Assert.assertEquals("Soccer", activityMap.remove(DayOfWeek.MONDAY));
		
		/**
		 * getting values 
		 */
	    Collection<String>  values = activityMapCopy.values();
	    String[] expectedData = {"Soccer","Basketball"};
	    Assert.assertArrayEquals(expectedData, values.toArray());
	    
	    /**
		 * getting keys 
		 */
	    Set<DayOfWeek> keys = activityMapCopy.keySet();
	    DayOfWeek [] expectedKeys = {DayOfWeek.MONDAY, DayOfWeek.TUESDAY};  
	    Assert.assertArrayEquals(expectedKeys, keys.toArray());
	    
	    /**
		 * getting entrySet 
		 */
	    Set<Entry<DayOfWeek, String>> entries = activityMapCopy.entrySet();
	    List<Entry<DayOfWeek, String>> expectedEntries = new ArrayList<>();
	    expectedEntries.add(new SimpleEntry(DayOfWeek.MONDAY, "Soccer"));
	    expectedEntries.add(new SimpleEntry(DayOfWeek.TUESDAY, "Basketball"));
	    Assert.assertArrayEquals(expectedEntries.toArray(), entries.toArray());
	}

}
