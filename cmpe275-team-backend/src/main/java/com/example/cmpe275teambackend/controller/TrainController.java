package com.example.cmpe275teambackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cmpe275teambackend.model.Ticket;
import com.example.cmpe275teambackend.model.Train;
import com.example.cmpe275teambackend.model.User;
import com.example.cmpe275teambackend.repository.TrainRepository;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TrainController {

	@Autowired
    TrainRepository trainRepository;
	
	// Get All Trains
	@GetMapping("/trains")
	public List<Train> getAllTrains() {
	    return trainRepository.findAll();
	}
	
    // Get a Train
	@GetMapping("/train/{name}")
	public ResponseEntity<Train> getTrainByName(@PathVariable(value = "name") String trainName) {
	    Train train = trainRepository.findOne(trainName);
	    if(train == null) {
	        return ResponseEntity.notFound().build();
	    }
	    
	    // check ticket and train mapping
	    List<Ticket> tickets = train.getTickets();
	    for(Ticket ticket : tickets){
	    	System.out.println("Tickets' id under current train: " + ticket.getId());
	    }
	    
	    return ResponseEntity.ok().body(train);
	}
	
	// Reset system
	@PutMapping("/resetTrain/{capacity}")
	public List<Train> resetTrain(@PathVariable(value = "capacity") int capacity){
		
		List<Train> curTrains = trainRepository.findAll();
		
		if(curTrains == null || curTrains.size() == 0){
			for(int i = 6; i < 21; i++){
				for(int j = 0; j < 60; j += 15){
					Train trainN = new Train();             // reset Northbound Train
					String start_time = "" + (i < 10 ? "0" + i : i) + (j == 0 ? "00" : j);
					String name = "NB" + start_time;
					trainN.setName(name);
					trainN.setDirection("North");
					trainN.setStart_time(start_time);
					trainN.setExpress(j == 0 ? true : false);
					trainN.setCapacity(capacity);
					trainRepository.save(trainN);
					
					Train trainS = new Train();             // reset Southbound Train 
					name = "SB" + start_time;
					trainS.setName(name);
					trainS.setDirection("South");
					trainS.setStart_time(start_time);
					trainS.setExpress(j == 0 ? true : false);
					trainS.setCapacity(capacity);
					trainRepository.save(trainS);
				}
			}
			
			Train trainN = new Train();             // Train end at 9PM inclusive
			String start_time = "2100";
			String name = "NB" + start_time;
			trainN.setName(name);
			trainN.setDirection("North");
			trainN.setStart_time(start_time);
			trainN.setExpress(true);
			trainN.setCapacity(capacity);
			trainRepository.save(trainN);
			
			Train trainS = new Train();          
			name = "SB" + start_time;
			trainS.setName(name);
			trainS.setDirection("South");
			trainS.setStart_time(start_time);
			trainS.setExpress(true);
			trainS.setCapacity(capacity);
			trainRepository.save(trainS);
			
		}
		else{
			for(Train train : curTrains){
				train.setCapacity(capacity);
				trainRepository.save(train);
			}
		}
		return trainRepository.findAll();
	}
	
	
}
