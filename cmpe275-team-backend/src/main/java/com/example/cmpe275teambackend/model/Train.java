package com.example.cmpe275teambackend.model;

import org.hibernate.validator.constraints.NotBlank;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "train")
public class Train implements Serializable{
	
	@Id
    private String name;        // PK
    
    private String direction;    
    
    private String start_time;
 
    private int capacity;
    
    private boolean express;
    
    @OneToMany(mappedBy = "train")
    @JsonIgnore
    private List<Ticket> tickets = new ArrayList<>();    // one transaction can map to many tickets
  
    
    // constructors, setters, getters, etc.
    
    public void setName(String name){
    	this.name = name;
    }
    
    public String getName(){
    	return name;
    }
    
    public void setDirection(String direction){
    	this.direction = direction;
    }
    
    public String getDirection(){
    	return direction;
    }
    
    public void setStart_time(String start_time){
    	this.start_time = start_time;
    }
    
    public String getStart_time(){
    	return start_time;
    }
    
    public void setCapacity(int capacity){
    	this.capacity = capacity;
    }
    
    public int getCapacity(){
    	return capacity;
    }
    
    public void setExpress(boolean express){
    	this.express = express;
    }
    
    public boolean getExpress(){
    	return express;
    }
    
    public void setTickets(List<Ticket> tickets){
    	this.tickets = tickets;
    }
    
    public List<Ticket> getTickets(){
    	return tickets;
    }
   
    
}