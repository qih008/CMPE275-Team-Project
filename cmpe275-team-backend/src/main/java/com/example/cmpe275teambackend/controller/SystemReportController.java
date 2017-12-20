package com.example.cmpe275teambackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cmpe275teambackend.model.SystemReport;
import com.example.cmpe275teambackend.model.Ticket;
import com.example.cmpe275teambackend.model.Train;
import com.example.cmpe275teambackend.model.TrainReservationRate;
import com.example.cmpe275teambackend.repository.SystemReportRepository;
import com.example.cmpe275teambackend.repository.TrainRepository;

import javax.validation.Valid;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@RestController
public class SystemReportController {
	
	@Autowired
    SystemReportRepository systemreportRepository;
	
	@Autowired
    TrainRepository trainRepository;
	
	// init system report
	@PostMapping("/initSystemReport")
	public void initReport(){
		
		// set reprot availability to next 30 days
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		systemreportRepository.deleteAll();
		
		Calendar cal = Calendar.getInstance();
		for(int k = 0; k < 30; k++){			
			SystemReport report = new SystemReport();
			String date = sdf.format(cal.getTime());
			List<Long> latency_list = new ArrayList<>();
			report.setDate(date);
			cal.add(Calendar.DATE, 1);
			systemreportRepository.save(report);
		}
	}
	
	// Get All Reports
	@GetMapping("/reports")
	public List<SystemReport> getAllReports() {
	    return systemreportRepository.findAll();
	}
	
    // Get a Report
	@GetMapping("/ticketSearchReport/{date}")
	public ResponseEntity<SystemReport> getReportByDate(@PathVariable(value = "date") String reportDate) {
		SystemReport report = systemreportRepository.findOne(reportDate);
	    if(report == null) {
	        return ResponseEntity.notFound().build();
	    }
	    
	    int total = report.getTotal_request();
	    if(total != 0){
	    	float none = (float) report.getNone_request() / total;
	    	float one = (float) report.getOne_request() / total;
	    	float any = (float) report.getAny_request() / total;
	    	report.setNone_percentage(none);
	    	report.setOne_percentage(one);
	    	report.setAny_percentage(any);
	    }
	    
	    SystemReport newReport = systemreportRepository.save(report);
	    
	    return ResponseEntity.ok().body(newReport);
	}
	
    // Get Train Reservation Rate
	@GetMapping("/trainReservationRate/{date}")
	public List<TrainReservationRate> getRateByDate(@PathVariable(value = "date") String date) {
		
		List<TrainReservationRate> rate_list = new ArrayList<>();
		
		for(int i = 6; i < 22; i++){
			for(int j = 0; j < 60; j += 15){
				if(i == 21 && j != 0)             // last departure is 2100
					break;  
				
				String start_time = "" + (i < 10 ? "0" + i : i) + (j == 0 ? "00" : j);
				String train_name = "SB" + start_time;
				String train_with_date = "SB" + start_time + " " + date;
				
				//System.out.println(train_with_date);
				
				Train train = trainRepository.findOne(train_with_date);
				List<Ticket> tickets = train.getTickets();
				int sum = 0;
				for(Ticket ticket : tickets){
					sum += Math.abs(ticket.getArrival_station().charAt(0) - ticket.getDeparture_station().charAt(0));
				}
				
				float percentage = (float) sum / 25;
				//System.out.println(percentage);
				String formattedString = String.format("%.02f", percentage);
				TrainReservationRate rate = new TrainReservationRate();
				rate.setTrain_name(train_name);
				rate.setRate(formattedString);
				rate_list.add(rate);
			}
		}
		
		for(int i = 6; i < 22; i++){
			for(int j = 0; j < 60; j += 15){
				if(i == 21 && j != 0)             // last departure is 2100
					break;  
				
				String start_time = "" + (i < 10 ? "0" + i : i) + (j == 0 ? "00" : j);
				String train_name = "NB" + start_time;
				String train_with_date = "NB" + start_time + " " + date;
				
				Train train = trainRepository.findOne(train_with_date);
				List<Ticket> tickets = train.getTickets();
				int sum = 0;
				for(Ticket ticket : tickets){
					sum += Math.abs(ticket.getArrival_station().charAt(0) - ticket.getDeparture_station().charAt(0));
				}
				
				float percentage = (float) sum / 25;
				//System.out.println(percentage);
				String formattedString = String.format("%.02f", percentage);
				TrainReservationRate rate = new TrainReservationRate();
				rate.setTrain_name(train_name);
				rate.setRate(formattedString);
				rate_list.add(rate);
			}
		}
		
		return rate_list;
	}
	
    // Get Daily Reservation Rate
	@GetMapping("/dailyReservationRate/{date1}/{date2}")
	public List<String> getRateByDate(@PathVariable(value = "date1") String date1,
			                                        @PathVariable(value = "date2") String date2) 
	{
		List<String> list = new ArrayList<>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		
		cal.set(Integer.parseInt(date1.substring(0, 4)), Integer.parseInt(date1.substring(5, 7))-1, Integer.parseInt(date1.substring(8)));
		
		while(!sdf.format(cal.getTime()).equals(date2)){
			String temp = sdf.format(cal.getTime());
			
			List<TrainReservationRate> rate_list = getRateByDate(temp);
			
			float sum = (float) 0;
			for(TrainReservationRate rate : rate_list){
				sum += Float.parseFloat(rate.getRate());
			}
			float avg_rate = sum / rate_list.size();

			list.add(temp + " " +avg_rate);
			cal.add(Calendar.DATE, 1);
		}

		return list;
	}
		
	
	

}