package com.dev88.find.in.files;

public class rough {
	
	public static void main (String []args) {
		
		System.out.println(new rough().convert("$-100"));
	}
	
	public String convert(String input) {
		
		return input.contains("-") ? "($"+input.split("-")[1]+")": input;
		
	}

}
