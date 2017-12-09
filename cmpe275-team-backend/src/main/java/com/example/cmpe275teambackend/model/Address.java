package com.example.cmpe275teambackend.model;

import javax.persistence.*;

// entire class embedded into player and sponsor classes
@Embeddable
public class Address {
	
	private String street;
	private String city;
	private String state;
	private String zip;
	
	// setter and getter
	public void setStreet(String street){
		this.street = street;
	}
	
	public String getStreet(){
		return street;
	}
	
	public void setCity(String city){
		this.city = city;
	}
	
	public String getCity(){
		return city;
	}
	
	public void setState(String state){
		this.state = state;
	}
	
	public String getState(){
		return state;
	}
	
	public void setZip(String zip){
		this.zip = zip;
	}
	
	public String getZip(){
		return zip;
	}

}
