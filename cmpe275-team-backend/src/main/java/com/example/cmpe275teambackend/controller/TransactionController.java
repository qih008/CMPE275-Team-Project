package com.example.cmpe275teambackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cmpe275teambackend.model.Transaction;
import com.example.cmpe275teambackend.model.User;
import com.example.cmpe275teambackend.repository.TransactionRepository;
import com.example.cmpe275teambackend.repository.UserRepository;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TransactionController {
	
	@Autowired
	TransactionRepository transactionRepository;
	
	@Autowired
	UserRepository userRepository;
	
	// Get All Transactions
	@GetMapping("/transactions")
	public List<Transaction> getAllTransactions() {
	    return transactionRepository.findAll();
	}
	
    // Create a new Transaction
	@PostMapping("/transaction")
	public ResponseEntity<Transaction> createTransaction( 
			@RequestParam(value="user_email", required=true) String user_email,
			@RequestParam(value="price", required=true) int price)
	{

		Transaction transaction = new Transaction();
		
		
		User user = userRepository.findOne(user_email);
		if(user != null){                          // check whether exist user have this email
			 transaction.setUser(user);
		}
		else
		    return ResponseEntity.badRequest().build();
			
		transaction.setPrice(price + 1);          // service fee is $1
		
		Transaction newtransaction = transactionRepository.save(transaction);    // save new create transaction
	    return ResponseEntity.ok(newtransaction);
	}

    // Get a Transaction
	@GetMapping("/transaction/{id}")
	public ResponseEntity<Transaction> getTransactionById(@PathVariable(value = "id") Long transactionId) {
		Transaction transaction = transactionRepository.findOne(transactionId);
	    if(transaction == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok().body(transaction);
	}

    // Delete a Transaction
	@DeleteMapping("/transaction/{id}")
    public ResponseEntity<Transaction> deleteTransaction(@PathVariable(value = "id") Long transactionId) {
		Transaction transaction = transactionRepository.findOne(transactionId);
        if(transaction == null) {
            return ResponseEntity.notFound().build();
        }

        transactionRepository.delete(transaction);
        return ResponseEntity.ok().body(transaction);
    }
}
