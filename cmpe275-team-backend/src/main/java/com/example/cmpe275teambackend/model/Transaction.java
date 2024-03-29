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
@Table(name = "transaction")
public class Transaction implements Serializable{
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)   // auto generate key ID
    private Long id;
	
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_email")
    private User user;                   // one transaction can only map to one user
     
    private int price;  
    
    @OneToMany(mappedBy = "transaction")
    @JsonIgnore
    private List<Ticket> tickets = new ArrayList<>();    // one transaction can map to many tickets
  
 
    // constructors, setters, getters, etc.
    
    public void setId(Long id){
    	this.id = id;
    }
    
    public Long getId(){
    	return id;
    }
    
    public void setPrice(int price){
    	this.price = price;
    }
    
    public int getPrice(){
    	return price;
    }
    
    public void setUser(User user){
    	this.user = user;
    }
    
    public User getUser(){
    	return user;
    }
    
    public void setTickets(List<Ticket> tickets){
    	this.tickets = tickets;
    }
    
    public List<Ticket> getTickets(){
    	return tickets;
    }
    
}