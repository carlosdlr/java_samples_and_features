package com.java8.features.streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StreamForEachIfElsTest {
	
	private List<String> weekDays;
	
	@Before
	public void initData() {
		weekDays = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
	}
	
	@Test
	public void usingIfConditionalInsideStream() {
		weekDays.stream().forEach(w -> {
			if(w.startsWith("S")) {
				Assert.assertTrue(w + " the day starts with S", w.startsWith("S"));
			} else {
				Assert.assertTrue(w + " the day not starts with S", !w.startsWith("S"));
			}
		});
		
		
	}
	
	@Test
	public void usingStreamFilters() {
		Stream<String> weekDayStarsWithS = weekDays.stream().filter(w -> w.startsWith("S"));
		Stream<String> weekDayStarsWithoutS = weekDays.stream().filter(w -> !w.startsWith("S"));
		
		weekDayStarsWithS.forEach(w -> Assert.assertTrue(w + " the day starts with S", w.startsWith("S")));
		weekDayStarsWithoutS.forEach(w -> Assert.assertTrue(w + " the day not starts with S", !w.startsWith("S")));
	}
	
	

}
