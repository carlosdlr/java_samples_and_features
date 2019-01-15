package com.java8.features.samples.predicates.chain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.*;

public class PredicateChainTest {


	private List<String> names;

	@Before
	public void init(){
		names = Arrays.asList("Adam", "Alexander", "John", "Tom");
	}


	/**
	 * This test shows how to use a predicate to filter
	 * in a Stream list, in this case filter all the names that
	 * starts with A
	 */
	@Test
	public void whenFilterListThenSuccess() {
		List<String> result = names.stream()
				.filter(name -> name.startsWith("A"))
				.collect(Collectors.toList());

		assertEquals(2, result.size());
		assertThat(result, contains("Adam", "Alexander"));
	}

    
	/**
	 * This test shows how to use multiple predicates to filter
	 * in a Stream list, in this case filter all the names that
	 * starts with A and the string size is lower than 5
	 */
	@Test
	public void whenMultipleFiltersAreUsedThenSuccess() {
		List<String> result = names.stream()
				.filter(name -> name.startsWith("A"))
				.filter(name -> name.length() < 5)
				.collect(Collectors.toList());
		
		assertEquals(1, result.size());
		assertThat(result, contains("Adam"));
	}
	
	/**
	 * This test shows how to use multiple a complex predicate using conditionals 
	 * to filter in a Stream list, in this case filter all the names that
	 * starts with A and the string size is lower than 5
	 */
	@Test
	public void whenFilterListWithComplexPredicateThenSuccess(){
	    List<String> result = names.stream()
	      .filter(name -> name.startsWith("A") && name.length() < 5)
	      .collect(Collectors.toList());
	 
	    assertEquals(1, result.size());
	    assertThat(result, contains("Adam"));
	}
	
	/**
	 * This test shows how to use the predicate API and the AND
	 * conditional in a stream 
	 */
	@Test
	public void whenFilterListWithCombinedPredicatesUsingAndThenSuccess(){
	    Predicate<String> predicate1 =  str -> str.startsWith("A");
	    Predicate<String> predicate2 =  str -> str.length() < 5;
	   
	    List<String> result = names.stream()
	      .filter(predicate1.and(predicate2))
	      .collect(Collectors.toList());
	         
	    assertEquals(1, result.size());
	    assertThat(result, contains("Adam"));
	}
	
	/**
	 * This test shows how to use the predicate API and the OR
	 * conditional in a stream 
	 */
	@Test
	public void whenFilterListWithCombinedPredicatesUsingOrThenSuccess(){
	    Predicate<String> predicate1 =  str -> str.startsWith("J");
	    Predicate<String> predicate2 =  str -> str.length() < 4;
	     
	    List<String> result = names.stream()
	      .filter(predicate1.or(predicate2))
	      .collect(Collectors.toList());
	     
	    assertEquals(2, result.size());
	    assertThat(result, contains("John","Tom"));
	}
	
	/**
	 * This test shows how to use the predicate API,the OR and the negate method
	 * conditional in a stream 
	 */
	@Test
	public void whenFilterListWithCombinedPredicatesUsingOrAndNegateThenSuccess(){
	    Predicate<String> predicate1 =  str -> str.startsWith("J");
	    Predicate<String> predicate2 =  str -> str.length() < 4;
	     
	    List<String> result = names.stream()
	      .filter(predicate1.or(predicate2.negate()))
	      .collect(Collectors.toList());
	     
	    assertEquals(3, result.size());
	    assertThat(result, contains("Adam","Alexander","John"));
	}
	
	/**
	 * This test shows how to combine predicates in a stream 
	 */
	@Test
	public void whenFilterListWithCombinedPredicatesInlineThenSuccess(){
	    List<String> result = names.stream()
	      .filter(((Predicate<String>)name -> name.startsWith("A"))
	      .and(name -> name.length()<5))
	      .collect(Collectors.toList());
	 
	    assertEquals(1, result.size());
	    assertThat(result, contains("Adam"));
	}
	
	/**
	 * This test shows how to use a list of predicates to apply filters
	 * in a stream 
	 */
	@Test
	public void whenFilterListWithCollectionOfPredicatesUsingAndThenSuccess(){
	    List<Predicate<String>> allPredicates = new ArrayList<Predicate<String>>();
	    allPredicates.add(str -> str.startsWith("A"));
	    allPredicates.add(str -> str.contains("d"));        
	    allPredicates.add(str -> str.length() > 4);
	     
	    List<String> result = names.stream()
	      .filter(allPredicates.stream().reduce(x->true, Predicate::and))
	      .collect(Collectors.toList());
	     
	    assertEquals(1, result.size());
	    assertThat(result, contains("Alexander"));
	}

}
