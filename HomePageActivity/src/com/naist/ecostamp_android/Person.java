package com.naist.ecostamp_android;

public class Person {
	
	private String name;
	private String country;
	private String twitter;
	
	protected void setName(String name){
		this.name = name;
	}
	
	protected void setCountry(String country){
		this.country = country;
	}
	
	protected void setTwitter(String twitter){
		this.twitter = twitter;
	}
	
	protected String getName(){
		return this.name;
	}
	
	protected String getCountry(){
		return this.country;
	}
	
	protected String getTwitter(){
		return this.twitter;
	}
	
}