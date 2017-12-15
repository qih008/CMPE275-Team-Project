package com.example.cmpe275teambackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cmpe275teambackend.model.Transaction;
import com.example.cmpe275teambackend.model.SB_Schedule;
import com.example.cmpe275teambackend.model.Train;
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
			@RequestParam(value="departure_station", required=true) char departure_station,
			@RequestParam(value="departure_date", required=true) String departure_date,
			@RequestParam(value="departure_time", required=true) String departure_time,
			@RequestParam(value="arrival_station", required=true) char arrival_station,
			@RequestParam(value="type", required=true) String type,
			//@RequestParam(value="number", required=true) int number,
			@RequestParam(value="exact_time", required=true) boolean exact_time,
			@RequestParam(value="connection", required=true) int connection)
	{	
        List<String> train_list = new ArrayList<>();
        String start_time = " ";
        String train_name = " ";
        String train_with_date = " ";
        List<Character> express_list = new ArrayList<>();
        express_list.add('A');
        express_list.add('F');
        express_list.add('K');
        express_list.add('P');
        express_list.add('U');
        express_list.add('Z');
        
        if(connection == 0){
        	if(express_list.contains(departure_station) && express_list.contains(arrival_station)){
        		if(type.equals("Regular")){
        			if(exact_time){
        				if(departure_station < arrival_station){             // southbound train
        					for(int i = 6; i < 21; i++){
        						for(int j = 0; j < 60; j += 15){
        							String temp = " ";
        							if(j != 0){
        								start_time = "" + (i < 10 ? "0" + i : i) + j;
        								train_name = "SB" + start_time;
        								train_with_date = "SB" + start_time + " " + departure_date;
        								temp = getSchedule(train_name, Character.toString(departure_station));
        								//System.out.println(temp);
        								if(temp.equals(departure_time)){
        								    train_list.add(train_with_date);
        								    //System.out.println(train_with_date);
        								    return train_list;
        								}
        							}
        						}
        					}
        				}
        			}
        		}
        	}
        }
        
        return train_list;
	}
	
	
	
	// helper function to quick find schedule
	public String getSchedule(String train_name, String station_name){
		
		if(train_name.charAt(0) == 'S'){
			//System.out.println(train_name + " " + station_name);
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

