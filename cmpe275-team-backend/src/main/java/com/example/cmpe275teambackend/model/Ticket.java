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

@Entity
@Table(name = "ticket")
@EntityListeners(AuditingEntityListener.class)
public class Ticket implements Serializable{
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)   // auto generate key ID
    private Long id;
	
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;                   // one ticket can only map to one transaction
   
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "train_name")
    private Train train;                              // one ticket can only map to one train
  
    private String user_name;
    
    //private String train_name;
    
    private String departure_station;
    
    private String departure_time;
    
    private String arrival_station;
    
    private String arrival_time;
    
    private String type;
    
    private int price;  
    
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date purchase_date;
    
    // constructors, setters, getters, etc.
    
    public void setId(Long id){
    	this.id = id;
    }
    
    public Long getId(){
    	return id;
    }
    
    public void setUser_name(String user_name){
    	this.user_name = user_name;
    }
    
    public String getUser_name(){
    	return user_name;
    }
    
//    public void setTrain_name(String train_name){
//    	this.train_name = train_name;
//    }
//    
//    public String getTrain_name(){
//    	return train_name;
//    }
    
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
    
    public void setType(String type){
    	this.type = type;
    }
    
    public String getType(){
    	return type;
    }
    
    public void setPrice(int price){
    	this.price = price;
    }
    
    public int getPrice(){
    	return price;
    }
    
    public void setTransaction(Transaction transaction){
    	this.transaction = transaction;
    }
    
    public Transaction getTransaction(){
    	return transaction;
    }
    
    public void setTrain(Train train){
    	this.train = train;
    }
    
    public Train getTrain(){
    	return train;
    }
    
    public Date getDate(){
    	return purchase_date;
    }
    
}