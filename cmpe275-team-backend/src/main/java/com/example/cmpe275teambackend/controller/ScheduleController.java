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
import java.util.HashMap;
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
					
					cal.add(Calendar.MINUTE, 8);
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
					
					cal.add(Calendar.MINUTE, 28);
					sb.setF(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 28);
					sb.setK(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 28);
					sb.setP(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 28);
					sb.setU(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 28);
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
		
		cal.add(Calendar.MINUTE, 28);
		sb.setF(sdf.format(cal.getTime()));
		
		cal.add(Calendar.MINUTE, 28);
		sb.setK(sdf.format(cal.getTime()));
		
		cal.add(Calendar.MINUTE, 28);
		sb.setP(sdf.format(cal.getTime()));
		
		cal.add(Calendar.MINUTE, 28);
		sb.setU(sdf.format(cal.getTime()));
		
		cal.add(Calendar.MINUTE, 28);
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
					
					cal.add(Calendar.MINUTE, 8);
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
					
					cal.add(Calendar.MINUTE, 28);
					nb.setU(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 28);
					nb.setP(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 28);
					nb.setK(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 28);
					nb.setF(sdf.format(cal.getTime()));
					
					cal.add(Calendar.MINUTE, 28);
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
		
		cal.add(Calendar.MINUTE, 28);
		nb.setU(sdf.format(cal.getTime()));
		
		cal.add(Calendar.MINUTE, 28);
		nb.setP(sdf.format(cal.getTime()));
		
		cal.add(Calendar.MINUTE, 28);
		nb.setK(sdf.format(cal.getTime()));
		
		cal.add(Calendar.MINUTE, 28);
		nb.setF(sdf.format(cal.getTime()));
		
		cal.add(Calendar.MINUTE, 28);
		nb.setA(sdf.format(cal.getTime()));
		
		nb_Repository.save(nb);		
	}
	
	// Get specific schedule
	@GetMapping("/getSchedule/{train_name}/{station_name}")
	public String getSchedule(
			@PathVariable(value = "train_name") String train_name,
			@PathVariable(value = "station_name") String station_name){
		
		if(train_name.charAt(0) == 'S'){
			System.out.println(train_name + " " + station_name);
			SB_Schedule sb = sb_Repository.findOne(train_name);
			if(sb != null){
				switch(station_name){
				    case "A": return sb.getA();
				    case "B": return sb.getB();
				    case "C": return sb.getC();
				    case "D": return sb.getD();
				    case "E": return sb.getE();
				    case "F": return sb.getF();
				    case "G": return sb.getG();
				    case "H": return sb.getH();
				    case "I": return sb.getI();
				    case "J": return sb.getJ();
				    case "K": return sb.getK();
				    case "L": return sb.getL();
				    case "M": return sb.getM();
				    case "N": return sb.getN();
				    case "O": return sb.getO();
				    case "P": return sb.getP();
				    case "Q": return sb.getQ();
				    case "R": return sb.getR();
				    case "S": return sb.getS();
				    case "T": return sb.getT();
				    case "U": return sb.getU();
				    case "V": return sb.getV();
				    case "W": return sb.getW();
				    case "X": return sb.getX();
				    case "Y": return sb.getY();
				    case "Z": return sb.getZ();
				    default: return "cannot find one.";
				}
			}				
		}
		else{
			NB_Schedule nb = nb_Repository.findOne(train_name);
			if(nb != null){
				switch(station_name){
				    case "A": return nb.getA();
				    case "B": return nb.getB();
				    case "C": return nb.getC();
				    case "D": return nb.getD();
				    case "E": return nb.getE();
				    case "F": return nb.getF();
				    case "G": return nb.getG();
				    case "H": return nb.getH();
				    case "I": return nb.getI();
				    case "J": return nb.getJ();
				    case "K": return nb.getK();
				    case "L": return nb.getL();
				    case "M": return nb.getM();
				    case "N": return nb.getN();
				    case "O": return nb.getO();
				    case "P": return nb.getP();
				    case "Q": return nb.getQ();
				    case "R": return nb.getR();
				    case "S": return nb.getS();
				    case "T": return nb.getT();
				    case "U": return nb.getU();
				    case "V": return nb.getV();
				    case "W": return nb.getW();
				    case "X": return nb.getX();
				    case "Y": return nb.getY();
				    case "Z": return nb.getZ();
				    default: return "cannot find one.";
				}
		    }
		}
		return "cannot find one!";
	}
}

