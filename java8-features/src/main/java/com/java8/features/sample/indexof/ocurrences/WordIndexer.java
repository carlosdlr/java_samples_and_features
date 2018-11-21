package com.java8.features.sample.indexof.ocurrences;

import java.util.ArrayList;
import java.util.List;

public class WordIndexer {
	
	/**
	 * search for all the word occurrences in a string using the
	 * indexOf method of java string API @see {@link StringIndexOf} and
	 * brute force approach.  
	 * @param textString text in which will be performed the searching 
	 * @param word to search
	 * @return a list of indexes where the occurrence happened 
	 */
	public List<Integer> findWordOccurrences(String textString, String word) {
		List<Integer> indexOccurrences = new ArrayList<>();
		String lowerCaseTextString = textString.toLowerCase();
		String lowerCaseWord = word.toLowerCase();
		
		int index = 0;
		while (index != -1) {
			index = lowerCaseTextString.indexOf(lowerCaseWord, index);
			if(index != -1) {
				indexOccurrences.add(index);
				index++;
			}
		}
		
		return indexOccurrences;
	}
	
	/**
	 * search for all the word occurrences in a string using the
	 * indexOf method of java string API @see {@link StringIndexOf} and 
	 * adding a little improvement slide over to the location just 
	 * after the end of the latest occurrence found
	 * @param textString
	 * @param word
	 * @return
	 */
	public List<Integer> findWordOccurrencesUpgrade(String textString, String word) {
	    List<Integer> indexes = new ArrayList<Integer>();
	    String lowerCaseTextString = textString.toLowerCase();
	    String lowerCaseWord = word.toLowerCase();
	    int wordLength = word.length();
	 
	    int index = 0;
	    while(index != -1){
	        index = lowerCaseTextString.indexOf(lowerCaseWord, index + wordLength);  // Slight improvement
	        if (index != -1) {
	            indexes.add(index);
	        }
	    }
	    return indexes;
	}
}
