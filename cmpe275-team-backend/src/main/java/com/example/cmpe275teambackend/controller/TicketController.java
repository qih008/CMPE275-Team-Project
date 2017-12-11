package com.example.cmpe275teambackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cmpe275teambackend.model.Transaction;
import com.example.cmpe275teambackend.model.Ticket;
import com.example.cmpe275teambackend.repository.TransactionRepository;
import com.example.cmpe275teambackend.repository.TicketRepository;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TicketController {
	
	@Autowired
	TicketRepository ticketRepository;
	
	@Autowired
	TransactionRepository transactionRepository;
		
	// Get All Tickets
	@GetMapping("/tickets")
	public List<Ticket> getAllTickets() {
	    return ticketRepository.findAll();
	}
	
    // Create a new Ticket
	@PostMapping("/ticket")
	public ResponseEntity<Ticket> createTicket( 
			@RequestParam(value="transaction_id", required=true) Long transaction_id,
			@RequestParam(value="user_email", required=true) String user_email,
			@RequestParam(value="train_name", required=true) String train_name,
			@RequestParam(value="departure", required=true) String departure,
			@RequestParam(value="arrival", required=true) String arrival,
			@RequestParam(value="type", required=true) String type,
			@RequestParam(value="price", required=true) String price)
	{

		Ticket ticket = new Ticket();
		
		Transaction transaction = transactionRepository.findOne(transaction_id);
		if(transaction != null){                          // check whether exist transaction have this id
			ticket.setTransaction(transaction);
		}
		else
		    return ResponseEntity.badRequest().build();
		
		ticket.setUser_email(user_email);
		ticket.setTrain_name(train_name);
		ticket.setDeparture(departure);
		ticket.setArrival(arrival);
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

        ticketRepository.delete(ticket);
        return ResponseEntity.ok().body(ticket);
    }
}

