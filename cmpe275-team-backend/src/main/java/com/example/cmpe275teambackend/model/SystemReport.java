package com.example.cmpe275teambackend.model;

import org.hibernate.validator.constraints.NotBlank;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.cmpe275teambackend.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "systemreport")
public class SystemReport implements Serializable{
	
	@Id
    private String date;        // PK
	
	private int total_request;
	
	private int none_request;
	
	private int one_request;
	
	private int any_request;
	
	private long none_latency;
	
	private long one_latency;
	
	private long any_latency;
	
	private String none_percentage;
	
	private String one_percentage;
	
	private String any_percentage;
 
    // constructors, setters, getters, etc.
    
    public void setDate(String date){
    	this.date = date;
    }
    
    public String getDate(){
    	return date;
    }
    
    public void setTotal_request(int total_request){
    	this.total_request = total_request;
    }
    
    public int getTotal_request(){
    	return total_request;
    }
    
    public void setNone_request(int none_request){
    	this.none_request = none_request;
    }
    
    public int getNone_request(){
    	return none_request;
    }
    
    public void setOne_request(int one_request){
    	this.one_request = one_request;
    }
    
    public int getOne_request(){
    	return one_request;
    }
    
    public void setAny_request(int any_request){
    	this.any_request = any_request;
    }
    
    public int getAny_request(){
    	return any_request;
    }
     
    public void setNone_latency(long none_latency){
    	this.none_latency = none_latency;
    }
    
    public long getNone_latency(){
    	return none_latency;
    }
    
    public void setOne_latency(long one_latency){
    	this.one_latency = one_latency;
    }
    
    public long getOne_latency(){
    	return one_latency;
    }
    
    public void setAny_latency(long any_latency){
    	this.any_latency = any_latency;
    }
    
    public long getAny_latency(){
    	return any_latency;
    }  
    
    public void setAny_percentage(String any_percentage){
    	this.any_percentage = any_percentage;
    }
    
    public String getAny_percentage(){
    	return any_percentage;
    } 
    
    public void setOne_percentage(String one_percentage){
    	this.one_percentage = one_percentage;
    }
    
    public String getOne_percentage(){
    	return one_percentage;
    } 
    
    public void setNone_percentage(String none_percentage){
    	this.none_percentage = none_percentage;
    }
    
    public String getNone_percentage(){
    	return none_percentage;
    } 
    

}