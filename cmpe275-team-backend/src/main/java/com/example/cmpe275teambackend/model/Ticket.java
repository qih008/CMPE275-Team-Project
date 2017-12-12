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
     
    private String user_name;
    
    private String train_name;
    
    private String departure;
    
    private String arrival;
    
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
    
    public void setTrain_name(String train_name){
    	this.train_name = train_name;
    }
    
    public String getTrain_name(){
    	return train_name;
    }
    
    public void setDeparture(String departure){
    	this.departure = departure;
    }
    
    public String getDeparture(){
    	return departure;
    }
    
    public void setArrival(String arrival){
    	this.arrival = arrival;
    }
    
    public String getArrival(){
    	return arrival;
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
    
    public Date getDate(){
    	return purchase_date;
    }
    
}