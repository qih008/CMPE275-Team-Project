package com.example.cmpe275teambackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cmpe275teambackend.model.SystemReport;
import com.example.cmpe275teambackend.model.Ticket;
import com.example.cmpe275teambackend.model.Train;
import com.example.cmpe275teambackend.repository.SystemReportRepository;

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

}