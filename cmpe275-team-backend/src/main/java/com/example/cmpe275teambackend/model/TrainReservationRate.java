package com.example.cmpe275teambackend.model;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

public class TrainReservationRate implements Serializable{
	   
    private String train_name;
    
    private String rate;
    
    public void setTrain_name(String train_name){
    	this.train_name = train_name;
    }
    
    public String getTrain_name(){
    	return train_name;
    }
    
    public void setRate(String rate){
    	this.rate = rate;
    }
    
    public String getRate(){
    	return rate;
    }
    
}
 