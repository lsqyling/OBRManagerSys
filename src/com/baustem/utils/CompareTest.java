package com.baustem.utils;

import java.util.ArrayList;
import java.util.List;

public class CompareTest {
	
	public static void afterEffect(List<String> listA){
		listA.add("d");
	}
	
	public static void main(String[] args) {
		
		List<String> listA = new ArrayList<String>();
		listA.add("a");
		listA.add("b");
		listA.add("c");
			
		afterEffect(listA);
		
		System.out.println(listA);
		
		
		
	}
	

}
