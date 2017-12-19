package com.example.cmpe275teambackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cmpe275teambackend.model.Transaction;
import com.example.cmpe275teambackend.model.Ticket;
import com.example.cmpe275teambackend.model.Train;
import com.example.cmpe275teambackend.repository.TransactionRepository;
import com.example.cmpe275teambackend.repository.TicketRepository;
import com.example.cmpe275teambackend.repository.TrainRepository;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TicketController {
	
	@Autowired
	TicketRepository ticketRepository;
	
	@Autowired
	TransactionRepository transactionRepository;
	
	@Autowired
    TrainRepository trainRepository;
		
	// Get All Tickets
	@GetMapping("/tickets")
	public List<Ticket> getAllTickets() {
	    return ticketRepository.findAll();
	}
	
    // Create a new Ticket
	@PostMapping("/ticket")
	public ResponseEntity<Ticket> createTicket( 
			@RequestParam(value="transaction_id", required=true) Long transaction_id,
			@RequestParam(value="user_name", required=true) String user_name,
			@RequestParam(value="train_name", required=true) String train_name,
			@RequestParam(value="departure_station", required=true) String departure_station,
			@RequestParam(value="departure_time", required=true) String departure_time,
			@RequestParam(value="arrival_station", required=true) String arrival_station,
			@RequestParam(value="arrival_time", required=true) String arrival_time,
			@RequestParam(value="type", required=true) String type,
			@RequestParam(value="price", required=true) int price)
	{

		Ticket ticket = new Ticket();
		
		Transaction transaction = transactionRepository.findOne(transaction_id);
		if(transaction != null){                          // check whether exist transaction have this id
			ticket.setTransaction(transaction);
		}
		else
		    return ResponseEntity.badRequest().build();
		
		// reduce selected train's capacity
		Train train = trainRepository.findOne(train_name);
		if(train_name.charAt(0) == 'S'){                        // southbound train
		    for(char i = departure_station.charAt(0); i < arrival_station.charAt(0); i++){
				switch(Character.toString(i)){
			    case "A": train.setA(train.getA() - 1);	break;
			    case "B": train.setB(train.getB() - 1);	break;
			    case "C": train.setC(train.getC() - 1);	break;
			    case "D": train.setD(train.getD() - 1);	break;
			    case "E": train.setE(train.getE() - 1);	break;
			    case "F": train.setF(train.getF() - 1);	break;
			    case "G": train.setG(train.getG() - 1);	break;
			    case "H": train.setH(train.getH() - 1);	break;
			    case "I": train.setI(train.getI() - 1);	break;
			    case "J": train.setJ(train.getJ() - 1);	break;
			    case "K": train.setK(train.getK() - 1);	break;
			    case "L": train.setL(train.getL() - 1);	break;
			    case "M": train.setM(train.getM() - 1);	break;
			    case "N": train.setN(train.getN() - 1);	break;
			    case "O": train.setO(train.getO() - 1);	break;
			    case "P": train.setP(train.getP() - 1);	break;
			    case "Q": train.setQ(train.getQ() - 1);	break;
			    case "R": train.setR(train.getR() - 1);	break;
			    case "S": train.setS(train.getS() - 1);	break;
			    case "T": train.setT(train.getT() - 1);	break;
			    case "U": train.setU(train.getU() - 1);	break;
			    case "V": train.setV(train.getV() - 1);	break;
			    case "W": train.setW(train.getW() - 1);	break;
			    case "X": train.setX(train.getX() - 1);	break;
			    case "Y": train.setY(train.getY() - 1);	break;
			    //case "Z": train.setZ(train.getZ() - 1);

			    }
		    }
		    //train.setCapacity(train.getCapacity() - 1);
		}
		else{                                                   // northbound train
		    for(char i = departure_station.charAt(0); i > arrival_station.charAt(0); i--){
				switch(Character.toString(i)){
			    //case "A": train.setA(train.getA() - 1);
			    case "B": train.setB(train.getB() - 1);	break;
			    case "C": train.setC(train.getC() - 1);	break;
			    case "D": train.setD(train.getD() - 1);	break;
			    case "E": train.setE(train.getE() - 1);	break;
			    case "F": train.setF(train.getF() - 1);	break;
			    case "G": train.setG(train.getG() - 1);	break;
			    case "H": train.setH(train.getH() - 1);	break;
			    case "I": train.setI(train.getI() - 1);	break;
			    case "J": train.setJ(train.getJ() - 1);	break;
			    case "K": train.setK(train.getK() - 1);	break;
			    case "L": train.setL(train.getL() - 1);	break;
			    case "M": train.setM(train.getM() - 1);	break;
			    case "N": train.setN(train.getN() - 1);	break;
			    case "O": train.setO(train.getO() - 1);	break;
			    case "P": train.setP(train.getP() - 1);	break;
			    case "Q": train.setQ(train.getQ() - 1);	break;
			    case "R": train.setR(train.getR() - 1);	break;
			    case "S": train.setS(train.getS() - 1);	break;
			    case "T": train.setT(train.getT() - 1);	break;
			    case "U": train.setU(train.getU() - 1);	break;
			    case "V": train.setV(train.getV() - 1);	break;
			    case "W": train.setW(train.getW() - 1);	break;
			    case "X": train.setX(train.getX() - 1);	break;
			    case "Y": train.setY(train.getY() - 1);	break;
				}
		    }
		}
		Train new_train = trainRepository.save(train);
		ticket.setTrain(new_train);
		
		ticket.setUser_name(user_name);
		ticket.setDeparture_station(departure_station);
		ticket.setDeparture_time(departure_time);
		ticket.setArrival_station(arrival_station);
		ticket.setArrival_time(arrival_time);
		ticket.setType(type);
		ticket.setPrice(price);
		Ticket newticket = ticketRepository.save(ticket);    // save new create transaction
	    return ResponseEntity.ok(newticket);
	}

    // Get a Ticket
	@GetMapping("/ticket/{id}")
	public ResponseEntity<Ticket> getTicketById(@PathVariable(value = "id") Long ticketId) {
		Ticket ticket = ticketRepository.findOne(ticketId);
	    if(ticket == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok().body(ticket);
	}

    // Delete a Ticket
	@DeleteMapping("/ticket/{id}")
    public ResponseEntity<Ticket> deleteTicket(@PathVariable(value = "id") Long ticketId) {
		Ticket ticket = ticketRepository.findOne(ticketId);
        if(ticket == null) {
            return ResponseEntity.notFound().build();
        }
        
        // cancel one ticket will also cancel entire transaction include all other tickets
        Transaction transaction = ticket.getTransaction();
        List<Ticket> tickets = transaction.getTickets();
        
        for(Ticket temp : tickets){
		    // restore selected train's capacity
		    Train train = temp.getTrain();
		    String train_name = train.getName();
		    String departure_station = temp.getDeparture_station();
		    String arrival_station = temp.getArrival_station();
			if(train_name.charAt(0) == 'S'){                        // southbound train
			    for(char i = departure_station.charAt(0); i < arrival_station.charAt(0); i++){
					switch(Character.toString(i)){
				    case "A": train.setA(train.getA() + 1);	break;
				    case "B": train.setB(train.getB() + 1);	break;
				    case "C": train.setC(train.getC() + 1);	break;
				    case "D": train.setD(train.getD() + 1);	break;
				    case "E": train.setE(train.getE() + 1);	break;
				    case "F": train.setF(train.getF() + 1);	break;
				    case "G": train.setG(train.getG() + 1);	break;
				    case "H": train.setH(train.getH() + 1);	break;
				    case "I": train.setI(train.getI() + 1);	break;
				    case "J": train.setJ(train.getJ() + 1);	break;
				    case "K": train.setK(train.getK() + 1);	break;
				    case "L": train.setL(train.getL() + 1);	break;
				    case "M": train.setM(train.getM() + 1);	break;
				    case "N": train.setN(train.getN() + 1);	break;
				    case "O": train.setO(train.getO() + 1);	break;
				    case "P": train.setP(train.getP() + 1);	break;
				    case "Q": train.setQ(train.getQ() + 1);	break;
				    case "R": train.setR(train.getR() + 1);	break;
				    case "S": train.setS(train.getS() + 1);	break;
				    case "T": train.setT(train.getT() + 1);	break;
				    case "U": train.setU(train.getU() + 1);	break;
				    case "V": train.setV(train.getV() + 1);	break;
				    case "W": train.setW(train.getW() + 1);	break;
				    case "X": train.setX(train.getX() + 1);	break;
				    case "Y": train.setY(train.getY() + 1);	break;
				    //case "Z": train.setZ(train.getZ() + 1);	break;

				    }
			    }
			    //train.setCapacity(train.getCapacity() + 1);
			}
			else{                                                   // northbound train
			    for(char i = departure_station.charAt(0); i > arrival_station.charAt(0); i--){
					switch(Character.toString(i)){
				    //case "A": train.setA(train.getA() + 1);
				    case "B": train.setB(train.getB() + 1);	break;
				    case "C": train.setC(train.getC() + 1);	break;
				    case "D": train.setD(train.getD() + 1);	break;
				    case "E": train.setE(train.getE() + 1);	break;
				    case "F": train.setF(train.getF() + 1);	break;
				    case "G": train.setG(train.getG() + 1);	break;
				    case "H": train.setH(train.getH() + 1);	break;
				    case "I": train.setI(train.getI() + 1);	break;
				    case "J": train.setJ(train.getJ() + 1);	break;
				    case "K": train.setK(train.getK() + 1);	break;
				    case "L": train.setL(train.getL() + 1);	break;
				    case "M": train.setM(train.getM() + 1);	break;
				    case "N": train.setN(train.getN() + 1);	break;
				    case "O": train.setO(train.getO() + 1);	break;
				    case "P": train.setP(train.getP() + 1);	break;
				    case "Q": train.setQ(train.getQ() + 1);	break;
				    case "R": train.setR(train.getR() + 1);	break;
				    case "S": train.setS(train.getS() + 1);	break;
				    case "T": train.setT(train.getT() + 1);	break;
				    case "U": train.setU(train.getU() + 1);	break;
				    case "V": train.setV(train.getV() + 1);	break;
				    case "W": train.setW(train.getW() + 1);	break;
				    case "X": train.setX(train.getX() + 1);	break;
				    case "Y": train.setY(train.getY() + 1);	break;
					}
			    }
			}
		    //train.setCapacity(train.getCapacity() + 1);
		    trainRepository.save(train);
            ticketRepository.delete(temp);
        }
        
        transactionRepository.delete(transaction);
        
        return ResponseEntity.ok().body(ticket);
    }
}

