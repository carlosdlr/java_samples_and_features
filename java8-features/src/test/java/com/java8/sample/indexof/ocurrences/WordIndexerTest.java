package com.java8.sample.indexof.ocurrences;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WordIndexerTest {
	
    String theStringToSerach;
    WordIndexer wordIndexer;

    @Before
    public void setUp() throws Exception {
        wordIndexer = new WordIndexer();

        theStringToSerach = "To be, or not to be: that is the question: "
          + "Whether 'tis nobler in the mind to suffer "
          + "The slings and arrows of outrageous fortune, "
          + "Or to take arms against a sea of troubles, "
          + "And by opposing end them? To die: to sleep; "
          + "No more; and by a sleep to say we end "
          + "The heart-ache and the thousand natural shocks "
          + "That flesh is heir to, 'tis a consummation "
          + "Devoutly to be wish'd. To die, to sleep; "
          + "To sleep: perchance to dream: ay, there's the rub: "
          + "For in that sleep of death what dreams may come,";
    }
	
	@Test
	public void givenWordWhenSearchingThenFindAllIndexedLocations() {
	
	    List<Integer> expectedResult = Arrays.asList(7, 122, 130, 221, 438);
	    List<Integer> actualResult = wordIndexer.findWordOccurrences(theStringToSerach, "or");
	    Assert.assertEquals(expectedResult, actualResult);
	
	}
	
	@Test
	public void givenWordWhenSearchingThenFindAllIndexedLocationsUpdate() {
	    List<Integer> expectedResult = Arrays.asList(7, 122, 130, 221, 438);
	    List<Integer> actualResult = wordIndexer.findWordOccurrencesUpgrade(theStringToSerach, "or");
	    Assert.assertEquals(expectedResult, actualResult);
	}
	
	
	

}
