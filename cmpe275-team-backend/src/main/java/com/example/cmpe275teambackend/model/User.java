package com.example.cmpe275teambackend.model;

import org.hibernate.validator.constraints.NotBlank;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.cmpe275teambackend.model.Transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "user")
public class User implements Serializable{
	
	@Id
    private String email;        // require element, check unique in controller
    
//    @GeneratedValue(strategy = GenerationType.AUTO)   // auto generate User ID
//    private Long id;

	@NotBlank
    private String name;         // require element, can't be null or blank
    

    @NotBlank
    private String password;     // require element, can't be null or blank
 
    @Embedded
    private Address address = new Address();    // embedded from address class
    
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Transaction> transactions = new ArrayList<>();    // one user can map to many transactions
  
    // constructors, setters, getters, etc.
    
//    public void setId(Long id){
//    	this.id = id;
//    }
//    
//    public Long getId(){
//    	return id;
//    }
    
    public void setName(String name){
    	this.name = name;
    }
    
    public String getName(){
    	return name;
    }
    
    
    public void setEmail(String email){
    	this.email = email;
    }
    
    public String getEmail(){
    	return email;
    }
    
    public void setPassword(String password){
    	this.password = password;
    }
    
    public String getPassword(){
    	return password;
    }
    
    public void setAddress(Address address){
    	this.address = address;
    }
    
    public Address getAddress(){
    	return address;
    }
    
    public void setTransactions(List<Transaction> transactions){
    	this.transactions = transactions;
    }
    
    public List<Transaction> getTransactions(){
    	return transactions;
    }
    
//    public void setOpponents(List<Player> opponents){
//    	this.opponents = opponents;
//    }
//    
//    public List<Player> getOpponents(){
//    	return opponents;
//    }
}