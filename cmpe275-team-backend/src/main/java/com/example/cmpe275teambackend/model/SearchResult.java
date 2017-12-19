package com.example.cmpe275teambackend.model;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

public class SearchResult implements Serializable{
	   
    private String train_name;
    
    private String departure_station;
    
    private String departure_time;
    
    private String arrival_station;
    
    private String arrival_time;
    
    private boolean transfer;
    
    private int price;  
    
    private List<SearchResult> transfer_train;
    
    // constructors, setters, getters, etc.
   
    
    public void setTrain_name(String train_name){
    	this.train_name = train_name;
    }
    
    public String getTrain_name(){
    	return train_name;
    }
    
    public void setDeparture_station(String departure_station){
    	this.departure_station = departure_station;
    }
    
    public String getDeparture_station(){
    	return departure_station;
    }
    
    public void setDeparture_time(String departure_time){
    	this.departure_time = departure_time;
    }
    
    public String getDeparture_time(){
    	return departure_time;
    }
    
    public void setArrival_station(String arrival_station){
    	this.arrival_station = arrival_station;
    }
    
    public String getArrival_station(){
    	return arrival_station;
    }
    
    public void setArrival_time(String arrival_time){
    	this.arrival_time = arrival_time;
    }
    
    public String getArrival_time(){
    	return arrival_time;
    }
    
    public void setPrice(int price){
    	this.price = price;
    }
    
    public int getPrice(){
    	return price;
    }
    
    public void setTransfer(boolean transfer){
    	this.transfer = transfer;
    }
    
    public boolean getTransfer(){
    	return transfer;
    }
    
    public void setTransfer_train(List<SearchResult> transfer_train){
    	this.transfer_train = transfer_train;
    }
    
    public List<SearchResult> getTransfer_train(){
    	return transfer_train;
    }
    
}