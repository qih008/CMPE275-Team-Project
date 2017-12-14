package com.example.cmpe275teambackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cmpe275teambackend.model.SB_Schedule;
import com.example.cmpe275teambackend.model.NB_Schedule;
import com.example.cmpe275teambackend.repository.NB_Repository;
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
	
	@Autowired
    NB_Repository nb_Repository;
	
	// input default time schedule
	@PostMapping("/initSchedule")
	public void initSchedule(){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		
		// set southbound time schedule	
		for(int i = 6; i < 21; i++){
			for(int j = 0; j < 60; j += 15){
				SB_Schedule sb = new SB_Schedule();
				
				if(j != 0){                                 // regular train
					
					Calendar cal = Calendar.getInstance();
					
					String start_time = "" + (i < 10 ? "0" + i : i) + j;   
					cal.set(Calendar.HOUR_OF_DAY, i);
					cal.set(Calendar.MINUTE, j);
					
					String name = "SB" + start_time;
					sb.setTrain_name(name);
					sb.setA(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 5);
					sb.setB(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					sb.setC(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					sb.setD(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					sb.setE(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					sb.setF(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					sb.setG(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					sb.setH(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					sb.setI(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					sb.setJ(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					sb.setK(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					sb.setL(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					sb.setM(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					sb.setN(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					sb.setO(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					sb.setP(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					sb.setQ(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					sb.setR(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					sb.setS(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					sb.setT(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					sb.setU(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					sb.setV(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					sb.setW(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					sb.setX(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					sb.setY(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					sb.setZ(sdf.format(cal.getTime()));
					
					sb_Repository.save(sb);
				}
				else{                                      // express train
                    Calendar cal = Calendar.getInstance();
					
					String start_time = "" + (i < 10 ? "0" + i : i) + "00";   
					cal.set(Calendar.HOUR_OF_DAY, i);
					cal.set(Calendar.MINUTE, j);
					
					String name = "SB" + start_time;
					sb.setTrain_name(name);
					sb.setA(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 25);
					sb.setF(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 25);
					sb.setK(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 25);
					sb.setP(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 25);
					sb.setU(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 25);
					sb.setZ(sdf.format(cal.getTime()));
					
					sb_Repository.save(sb);
				}
			}
		}
		SB_Schedule sb = new SB_Schedule();
		Calendar cal = Calendar.getInstance();
		
		String start_time = "2100";   
		cal.set(Calendar.HOUR_OF_DAY, 21);
		cal.set(Calendar.MINUTE, 0);
		
		String name = "SB" + start_time;
		sb.setTrain_name(name);
		sb.setA(sdf.format(cal.getTime()));
		
		cal.add(Calendar.MINUTE, 25);
		sb.setF(sdf.format(cal.getTime()));
		
		cal.add(Calendar.MINUTE, 25);
		sb.setK(sdf.format(cal.getTime()));
		
		cal.add(Calendar.MINUTE, 25);
		sb.setP(sdf.format(cal.getTime()));
		
		cal.add(Calendar.MINUTE, 25);
		sb.setU(sdf.format(cal.getTime()));
		
		cal.add(Calendar.MINUTE, 25);
		sb.setZ(sdf.format(cal.getTime()));
		
		sb_Repository.save(sb);
	
		
		// set northbound time schedule	
		for(int i = 6; i < 21; i++){
			for(int j = 0; j < 60; j += 15){
				NB_Schedule nb = new NB_Schedule();
				
				if(j != 0){                                 // regular train
					
					start_time = "" + (i < 10 ? "0" + i : i) + j;   
					cal.set(Calendar.HOUR_OF_DAY, i);
					cal.set(Calendar.MINUTE, j);
					
					name = "NB" + start_time;
					nb.setTrain_name(name);
					nb.setZ(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 5);
					nb.setY(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					nb.setX(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					nb.setW(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					nb.setV(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					nb.setU(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					nb.setT(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					nb.setS(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					nb.setR(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					nb.setQ(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					nb.setP(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					nb.setO(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					nb.setN(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					nb.setM(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					nb.setL(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					nb.setK(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					nb.setJ(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					nb.setI(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					nb.setH(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					nb.setG(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					nb.setF(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					nb.setE(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					nb.setD(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					nb.setC(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					nb.setB(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 8);
					nb.setA(sdf.format(cal.getTime()));
					
					nb_Repository.save(nb);
				}
				else{                                      // express train
                    cal = Calendar.getInstance();
					
					start_time = "" + (i < 10 ? "0" + i : i) + "00";   
					cal.set(Calendar.HOUR_OF_DAY, i);
					cal.set(Calendar.MINUTE, j);
					
					name = "NB" + start_time;
					nb.setTrain_name(name);
					nb.setZ(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 25);
					nb.setU(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 25);
					nb.setP(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 25);
					nb.setK(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 25);
					nb.setF(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 25);
					nb.setA(sdf.format(cal.getTime()));
					
					nb_Repository.save(nb);
				}
			}
		}
		NB_Schedule nb = new NB_Schedule();
		cal = Calendar.getInstance();
		
		start_time = "2100";   
		cal.set(Calendar.HOUR_OF_DAY, 21);
		cal.set(Calendar.MINUTE, 0);
		
		name = "NB" + start_time;
		nb.setTrain_name(name);
		nb.setZ(sdf.format(cal.getTime()));
		
		cal.add(Calendar.MINUTE, 25);
		nb.setU(sdf.format(cal.getTime()));
		
		cal.add(Calendar.MINUTE, 25);
		nb.setP(sdf.format(cal.getTime()));
		
		cal.add(Calendar.MINUTE, 25);
		nb.setK(sdf.format(cal.getTime()));
		
		cal.add(Calendar.MINUTE, 25);
		nb.setF(sdf.format(cal.getTime()));
		
		cal.add(Calendar.MINUTE, 25);
		nb.setA(sdf.format(cal.getTime()));
		
		nb_Repository.save(nb);
	
		
		

		
		
	}
	
}

