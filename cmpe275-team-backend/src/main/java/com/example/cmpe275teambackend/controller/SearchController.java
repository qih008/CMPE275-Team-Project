package com.example.cmpe275teambackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cmpe275teambackend.model.Transaction;
import com.example.cmpe275teambackend.model.SB_Schedule;
import com.example.cmpe275teambackend.model.NB_Schedule;
import com.example.cmpe275teambackend.repository.SB_Repository;
import com.example.cmpe275teambackend.repository.NB_Repository;
import com.example.cmpe275teambackend.repository.TrainRepository;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SearchController {
	
	@Autowired
	SB_Repository sb_Repository;

	@Autowired
	NB_Repository nb_Repository;
	
	@Autowired
    TrainRepository trainRepository;
	
    // Get top 5 fast train base on user requirement
	@GetMapping("/search")
	public List<String> searchTicket( 
			@RequestParam(value="departure_station", required=true) String departure_station,
			@RequestParam(value="departure_time", required=true) String departure_time,
			@RequestParam(value="arrival_station", required=true) String arrival_station,
			@RequestParam(value="type", required=true) String type,
			@RequestParam(value="number", required=true) int number,
			@RequestParam(value="exact_time", required=true) boolean exact_time,
			@RequestParam(value="connection", required=true) int connection)
	{
        List<String> train_list = new ArrayList<>();
        
        
        return train_list;
	}
}

