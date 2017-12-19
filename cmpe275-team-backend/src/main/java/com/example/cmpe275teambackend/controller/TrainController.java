package com.example.cmpe275teambackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cmpe275teambackend.model.Ticket;
import com.example.cmpe275teambackend.model.Train;
import com.example.cmpe275teambackend.model.User;
import com.example.cmpe275teambackend.repository.TrainRepository;

import javax.validation.Valid;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
		
		// set search availability to next 30 days
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		//List<Train> curTrains = trainRepository.findAll();
		trainRepository.deleteAll();
		
		for(int i = 6; i < 21; i++){
			for(int j = 0; j < 60; j += 15){
				Calendar cal = Calendar.getInstance();
				for(int k = 0; k < 30; k++){
								
					String date = sdf.format(cal.getTime());
					String start_time = "" + (i < 10 ? "0" + i : i) + (j == 0 ? "00" : j);
					
					Train trainN = new Train();             // reset Northbound Train
					String name = "NB" + start_time + " " + date;
					trainN.setName(name);
					trainN.setDirection("North");
					trainN.setStart_time(start_time);
					trainN.setExpress(j == 0 ? true : false);
					trainN.setCapacity(capacity);
					//trainN.setA(capacity);
					trainN.setB(capacity);
					trainN.setC(capacity);
					trainN.setD(capacity);
					trainN.setE(capacity);
					trainN.setF(capacity);
					trainN.setG(capacity);
					trainN.setH(capacity);
					trainN.setI(capacity);
					trainN.setJ(capacity);
					trainN.setK(capacity);
					trainN.setL(capacity);
					trainN.setM(capacity);
					trainN.setN(capacity);
					trainN.setO(capacity);
					trainN.setP(capacity);
					trainN.setQ(capacity);
					trainN.setR(capacity);
					trainN.setS(capacity);
					trainN.setT(capacity);
					trainN.setU(capacity);
					trainN.setV(capacity);
					trainN.setW(capacity);
					trainN.setX(capacity);
					trainN.setY(capacity);
					trainN.setZ(capacity);
					trainRepository.save(trainN);
					
					Train trainS = new Train();             // reset Southbound Train 
					name = "SB" + start_time + " " + date;
					trainS.setName(name);
					trainS.setDirection("South");
					trainS.setStart_time(start_time);
					trainS.setExpress(j == 0 ? true : false);
					trainS.setCapacity(capacity);
					trainS.setA(capacity);
					trainS.setB(capacity);
					trainS.setC(capacity);
					trainS.setD(capacity);
					trainS.setE(capacity);
					trainS.setF(capacity);
					trainS.setG(capacity);
					trainS.setH(capacity);
					trainS.setI(capacity);
					trainS.setJ(capacity);
					trainS.setK(capacity);
					trainS.setL(capacity);
					trainS.setM(capacity);
					trainS.setN(capacity);
					trainS.setO(capacity);
					trainS.setP(capacity);
					trainS.setQ(capacity);
					trainS.setR(capacity);
					trainS.setS(capacity);
					trainS.setT(capacity);
					trainS.setU(capacity);
					trainS.setV(capacity);
					trainS.setW(capacity);
					trainS.setX(capacity);
					trainS.setY(capacity);
					//trainS.setZ(capacity);
					trainRepository.save(trainS);
					
					cal.add(Calendar.DATE, 1);
				}
			}
		}
		
		Calendar cal = Calendar.getInstance();
		for(int k = 0; k < 30; k++){
			String date = sdf.format(cal.getTime());
			
			Train trainN = new Train();             // Train end at 9PM inclusive
			String start_time = "2100";
			String name = "NB" + start_time + " " + date;
			trainN.setName(name);
			trainN.setDirection("North");
			trainN.setStart_time(start_time);
			trainN.setExpress(true);
			trainN.setCapacity(capacity);
			//trainN.setA(capacity);
			trainN.setB(capacity);
			trainN.setC(capacity);
			trainN.setD(capacity);
			trainN.setE(capacity);
			trainN.setF(capacity);
			trainN.setG(capacity);
			trainN.setH(capacity);
			trainN.setI(capacity);
			trainN.setJ(capacity);
			trainN.setK(capacity);
			trainN.setL(capacity);
			trainN.setM(capacity);
			trainN.setN(capacity);
			trainN.setO(capacity);
			trainN.setP(capacity);
			trainN.setQ(capacity);
			trainN.setR(capacity);
			trainN.setS(capacity);
			trainN.setT(capacity);
			trainN.setU(capacity);
			trainN.setV(capacity);
			trainN.setW(capacity);
			trainN.setX(capacity);
			trainN.setY(capacity);
			//trainN.setZ(capacity);
			trainRepository.save(trainN);
			
			Train trainS = new Train();          
			name = "SB" + start_time + " " + date;
			trainS.setName(name);
			trainS.setDirection("South");
			trainS.setStart_time(start_time);
			trainS.setExpress(true);
			trainS.setCapacity(capacity);
			trainS.setA(capacity);
			trainS.setB(capacity);
			trainS.setC(capacity);
			trainS.setD(capacity);
			trainS.setE(capacity);
			trainS.setF(capacity);
			trainS.setG(capacity);
			trainS.setH(capacity);
			trainS.setI(capacity);
			trainS.setJ(capacity);
			trainS.setK(capacity);
			trainS.setL(capacity);
			trainS.setM(capacity);
			trainS.setN(capacity);
			trainS.setO(capacity);
			trainS.setP(capacity);
			trainS.setQ(capacity);
			trainS.setR(capacity);
			trainS.setS(capacity);
			trainS.setT(capacity);
			trainS.setU(capacity);
			trainS.setV(capacity);
			trainS.setW(capacity);
			trainS.setX(capacity);
			trainS.setY(capacity);
			//trainS.setZ(capacity);
			trainRepository.save(trainS);
			
			cal.add(Calendar.DATE, 1);
		}

		return trainRepository.findAll();
	}
	
	
}
