package com.example.cmpe275teambackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cmpe275teambackend.model.SB_Schedule;
import com.example.cmpe275teambackend.repository.SB_Repository;

import javax.validation.Valid;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
public class ScheduleController {

	@Autowired
    SB_Repository sb_Repository;
	
	// input default time schedule
	@PostMapping("/initSchedule")
	public void initSchedule(){
		
		// set southbound time schedule
		for(int i = 6; i < 21; i++){
			for(int j = 0; j < 60; j += 15){
				
				SB_Schedule sb = new SB_Schedule();
				if(j != 0){                                 // regular train
					String start_time = "" + (i < 10 ? "0" + i : i) + j;
					
					             
					String name = "SB" + start_time;
					sb.setTrain_name(name);
					sb.setA(start_time);
					

				}
				else{                                      // express train
					
				}
				sb_Repository.save(sb);

					

			}
		}
		
	}
	
}

