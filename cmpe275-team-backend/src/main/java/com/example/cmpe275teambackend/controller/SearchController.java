package com.example.cmpe275teambackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cmpe275teambackend.model.Transaction;
import com.example.cmpe275teambackend.model.SB_Schedule;
import com.example.cmpe275teambackend.model.Ticket;
import com.example.cmpe275teambackend.model.Train;
import com.example.cmpe275teambackend.model.SearchResult;
import com.example.cmpe275teambackend.model.SystemReport;
import com.example.cmpe275teambackend.model.NB_Schedule;
import com.example.cmpe275teambackend.repository.SB_Repository;
import com.example.cmpe275teambackend.repository.SystemReportRepository;
import com.example.cmpe275teambackend.repository.NB_Repository;
import com.example.cmpe275teambackend.repository.TrainRepository;

import javax.validation.Valid;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

@RestController
public class SearchController {
	
	@Autowired
	SB_Repository sb_Repository;

	@Autowired
	NB_Repository nb_Repository;
	
	@Autowired
    TrainRepository trainRepository;
	
	@Autowired
    SystemReportRepository systemreportRepository;
	
    // Get top 5 fast train base on user requirement
//	@GetMapping("/search")
//	public List<String> searchTicket( 
//			@RequestParam(value="departure_station", required=true) char departure_station,
//			@RequestParam(value="departure_date", required=true) String departure_date,
//			@RequestParam(value="departure_time", required=true) String departure_time,
//			@RequestParam(value="arrival_station", required=true) char arrival_station,
//			@RequestParam(value="type", required=true) String type,
//			//@RequestParam(value="number", required=true) int number,
//			@RequestParam(value="exact_time", required=true) boolean exact_time,
//			@RequestParam(value="connection", required=true) int connection)
//	{	
//        List<String> train_list = new ArrayList<>();
//        String start_time = " ";
//        String train_name = " ";
//        String train_with_date = " ";
//        String dir = (departure_station < arrival_station ? "SB" : "NB");
//        
//        List<Character> express_list = new ArrayList<>();
//        express_list.add('A');
//        express_list.add('F');
//        express_list.add('K');
//        express_list.add('P');
//        express_list.add('U');
//        express_list.add('Z');
//        
//        
//    	if(express_list.contains(departure_station) && express_list.contains(arrival_station)){
//			if(exact_time){   				              // only one train will be return	
//				for(int i = 6; i < 22; i++){
//					for(int j = 0; j < 60; j += 15){
//						if(i == 21 && j != 0)             // last departure is 2100
//							break;                       
//						
//						String temp = " ";							                        
//						start_time = "" + (i < 10 ? "0" + i : i) + (j == 0 ? "00" : j);
//						train_name = dir + start_time;
//						train_with_date = dir + start_time + " " + departure_date;
//						temp = getSchedule(train_name, Character.toString(departure_station));
//						//System.out.println(temp);
//						if(temp.equals(departure_time)){
//							if(type.equals("Regular") && j == 0)
//								return train_list;
//							else if(type.equals("Express") && j != 0)
//								return train_list;
//							else{
//						        train_list.add(train_with_date);
//						        return train_list;
//							}
//						}
//					}
//				}
//			}
//			else{                                          // return top 5 choice
//				int regular_count = 0;
//				int express_count = 0;
//				Queue<String> timePriorityQueue = new PriorityQueue<>(7, timeComparator);
//		    	HashMap<String, String> map = new HashMap<>();
//				for(int i = 6; i < 22; i++){
//					for(int j = 0; j < 60; j += 15){
//						if(i == 21 && j != 0)             // last departure is 2100
//							break;  
//						
//						String temp = " ";							                        
//						start_time = "" + (i < 10 ? "0" + i : i) + (j == 0 ? "00" : j);
//						train_name = dir + start_time;
//						train_with_date = dir + start_time + " " + departure_date;
//						temp = getSchedule(train_name, Character.toString(departure_station));
//						String real_temp = getSchedule(train_name, Character.toString(arrival_station));
//						String temp_arrival = departureToArrival(real_temp);
//						if(earlierTime(departure_time, temp) || temp.equals(departure_time)){
//						    if(type.equals("Regular") && j != 0){
//							    if(train_list.size() == 5){
//							    	return train_list;
//							    }
//							    else
//							    	train_list.add(train_with_date + " Arrival time is: " + temp_arrival);								    
//						    }
//						    else if(type.equals("Express") && j == 0){
//						    	if(train_list.size() == 5){
//							    	return train_list;
//							    }
//							    else
//							    	train_list.add(train_with_date + " Arrival time is: " + temp_arrival);
//						    }
//						    else if(type.equals("Any")){           // compare both regular and express train
//						    	if(regular_count < 5 && j != 0){
//						    		timePriorityQueue.add(temp_arrival+train_name);
//						    	    map.put(temp_arrival+train_name, train_with_date + " Arrival time is: " + temp_arrival);
//						    	    regular_count++;
//						    	    //System.out.println(train_with_date + " Arrival time is: " + temp_arrival);
//						    	}
//						    	if(express_count < 2 && j == 0){
//						    		timePriorityQueue.add(temp_arrival+train_name);
//						    	    map.put(temp_arrival+train_name, train_with_date + " Arrival time is: " + temp_arrival);
//						    	    express_count++;
//						    	    //System.out.println(train_with_date + " Arrival time is: " + temp_arrival);
//						    	}
//						    	if(i == 21 || timePriorityQueue.size() == 7){
//						    		int limit = (5 < timePriorityQueue.size() ? 5 : timePriorityQueue.size());
//						    		for(int x = 0; x < limit; x++){
//						    			//System.out.println(timePriorityQueue.peek());
//						    			train_list.add(map.get(timePriorityQueue.poll()));
//						    		}
//						    		return train_list;
//						    	}
//						    }
//						}
//					}
//				}
//				return train_list;
//			}
//    	}
//    	else if(express_list.contains(departure_station) || express_list.contains(arrival_station))
//    	{   // only one station is express station
//    		// in this case, 0 connection and regular ticket means user can only take regular train
//    		if(connection == 0 || type.equals("Regular")){   
//        		if(!type.equals("Express")){                      // in this case, any = regular
//        			if(exact_time){   				              // only one train will be return	
//    					for(int i = 6; i < 21; i++){
//    						for(int j = 0; j < 60; j += 15){
//    							if( j != 0){                     // don't support express
//        							String temp = " ";							                        
//        							start_time = "" + (i < 10 ? "0" + i : i) + (j == 0 ? "00" : j);
//        							train_name = dir + start_time;
//        							train_with_date = dir + start_time + " " + departure_date;
//        							temp = getSchedule(train_name, Character.toString(departure_station));
//        							//System.out.println(temp);
//        							if(temp.equals(departure_time)){
//        							    train_list.add(train_with_date);
//        							    //System.out.println(train_with_date);
//        							    return train_list;
//        							}  							
//    						    }
//    						}
//    					}
//        			}
//        			else{     // return top 5 reuglar train
//        				for(int i = 6; i < 21; i++){
//    						for(int j = 0; j < 60; j += 15){ 
//    							if(j != 0){
//        							String temp = " ";							                        
//        							start_time = "" + (i < 10 ? "0" + i : i) + (j == 0 ? "00" : j);
//        							train_name = dir + start_time;
//        							train_with_date = dir + start_time + " " + departure_date;
//        							temp = getSchedule(train_name, Character.toString(departure_station));
//        							String real_temp = getSchedule(train_name, Character.toString(arrival_station));
//        							String temp_arrival = departureToArrival(real_temp);
//        							if(earlierTime(departure_time, temp) || temp.equals(departure_time)){
//        								if(train_list.size() == 5){
//        								    return train_list;
//        								}
//        								else
//        								    train_list.add(train_with_date + " Arrival time is: " + temp_arrival);		    
//        							}								
//    							}
//    						}
//        				}
//        			}
//        		}
//
//    	    }
//    		// one station include express, connection is 1 or 2, the result will be same
//    		else if(connection == 1 || connection ==2)
//    		{   // no need to take express train
//    			if(Math.abs(arrival_station-departure_station) < 6){
//            		if(!type.equals("Express")){                      // in this case, any = regular
//            			if(exact_time){   				              // only one train will be return	
//        					for(int i = 6; i < 21; i++){
//        						for(int j = 0; j < 60; j += 15){
//        							if( j != 0){                     // don't support express
//            							String temp = " ";							                        
//            							start_time = "" + (i < 10 ? "0" + i : i) + (j == 0 ? "00" : j);
//            							train_name = dir + start_time;
//            							train_with_date = dir + start_time + " " + departure_date;
//            							temp = getSchedule(train_name, Character.toString(departure_station));
//            							//System.out.println(temp);
//            							if(temp.equals(departure_time)){
//            							    train_list.add(train_with_date);
//            							    //System.out.println(train_with_date);
//            							    return train_list;
//            							}  							
//        						    }
//        						}
//        					}
//            			}
//            			else{     // return top 5 reuglar train
//            				for(int i = 6; i < 21; i++){
//        						for(int j = 0; j < 60; j += 15){ 
//        							if(j != 0){
//            							String temp = " ";							                        
//            							start_time = "" + (i < 10 ? "0" + i : i) + (j == 0 ? "00" : j);
//            							train_name = dir + start_time;
//            							train_with_date = dir + start_time + " " + departure_date;
//            							temp = getSchedule(train_name, Character.toString(departure_station));
//            							String real_temp = getSchedule(train_name, Character.toString(arrival_station));
//            							String temp_arrival = departureToArrival(real_temp);
//            							if(earlierTime(departure_time, temp) || temp.equals(departure_time)){
//            								if(train_list.size() == 5){
//            								    return train_list;
//            								}
//            								else
//            								    train_list.add(train_with_date + " Arrival time is: " + temp_arrival);		    
//            							}								
//        							}
//        						}
//            				}
//            			}
//            		}
//    			}
//    			else{      // distance larger than 5 station
//					int remain = Math.abs(departure_station-arrival_station) % 5;
//					
//    				if(express_list.contains(departure_station)){     // take express first, then regular
//    					char transfer_station = (char) (dir.equals("SB") ? arrival_station - remain : arrival_station + remain);
//    					if(exact_time){
//        					for(int i = 6; i < 22; i++){
//        						for(int j = 0; j < 60; j += 15){
//        							if(i == 21 && j != 0)             // last departure is 2100
//        								break;
//        							
//            						String temp = " ";							                        
//            						start_time = "" + (i < 10 ? "0" + i : i) + (j == 0 ? "00" : j);
//            						train_name = dir + start_time;
//            						train_with_date = dir + start_time + " " + departure_date;
//            						temp = getSchedule(train_name, Character.toString(departure_station));
//    								String real_temp = getSchedule(train_name, Character.toString(transfer_station));
//    								String transfer_arrival = departureToArrival(real_temp);
//    								real_temp = getSchedule(train_name, Character.toString(arrival_station));
//    								String arrival_time = "";
//    								if(real_temp != null){
//    								    arrival_time = departureToArrival(real_temp);
//    								}
//            						if(temp.equals(departure_time)){
//            							if(type.equals("Express") && j != 0)
//            								return train_list;
//            							else if(j != 0){         // this is regular train
//            								train_list.add(train_with_date + ", arrival time is: " + arrival_time);
//            								return train_list;
//            							}
//            							else
//            							{
//            								String next_train = findRegular(transfer_station, departure_date, transfer_arrival, arrival_station ,dir);
//            							    train_list.add(train_with_date + ", first arrival time is: " + transfer_arrival + ", " + next_train);
//            							    return train_list;
//            							}
//            						}  							
//        						}
//        					}
//    					}
//    					else{  // return top 5 combinatino train
//    						
//    						int regular_count = 0;
//    						int express_count = 0;
//    						Queue<String> timePriorityQueue = new PriorityQueue<>(6, timeComparator);
//    				    	HashMap<String, String> map = new HashMap<>();
//        					for(int i = 6; i < 22; i++){
//        						for(int j = 0; j < 60; j += 15){
//        							if(i == 21 && j != 0)             // last departure is 2100
//        								break;
//        							
//            						String temp = " ";							                        
//            						start_time = "" + (i < 10 ? "0" + i : i) + (j == 0 ? "00" : j);
//            						train_name = dir + start_time;
//            						train_with_date = dir + start_time + " " + departure_date;
//            						temp = getSchedule(train_name, Character.toString(departure_station));
//    								String real_temp = getSchedule(train_name, Character.toString(transfer_station));
//    								String transfer_arrival = departureToArrival(real_temp);
//    								real_temp = getSchedule(train_name, Character.toString(arrival_station));
//    								String arrival_time = "";
//    								if(real_temp != null){
//    								    arrival_time = departureToArrival(real_temp);
//    								}
//    								if(earlierTime(departure_time, temp) || temp.equals(departure_time)){
//            							if(type.equals("Express") && j == 0){
//            						    	if(train_list.size() == 5){
//            							    	return train_list;
//            							    }
//            							    else{
//                								String next_train = findRegular(transfer_station, departure_date, transfer_arrival, arrival_station, dir);
//                							    train_list.add(train_with_date + ", first arrival time is: " + transfer_arrival + ", " + next_train);            					
//            							    }
//            							}
//            							else if(type.equals("Any"))
//            							{  //System.out.println(regular_count);
//            						    	if(regular_count < 4 && j != 0){
//            						    		timePriorityQueue.add(arrival_time+train_name);
//            						    	    map.put(arrival_time+train_name, train_with_date + " Arrival time is: " + arrival_time);
//            						    	    regular_count++;
//            						    	    //System.out.println(train_with_date + " Arrival time is: " + temp_arrival);
//            						    	}
//            						    	if(express_count < 2 && j == 0){
//            						    		String next_train = findRegular(transfer_station, departure_date, transfer_arrival, arrival_station ,dir);
//            						    		String final_arrival = findRegularTime(transfer_station, departure_date, transfer_arrival, arrival_station ,dir);
//            						    		//train_list.add(train_with_date + ", first arrival time is: " + transfer_arrival + ", " + next_train);
//            						    		timePriorityQueue.add(final_arrival+train_name);
//            						    	    map.put(final_arrival+train_name, train_with_date + ", first arrival time is: " + transfer_arrival + ", " + next_train);
//            						    	    express_count++;
//            						    	    //System.out.println(train_with_date + " Arrival time is: " + temp_arrival);
//            						    	}
//            						    	if(i == 21 || timePriorityQueue.size() == 6){
//            						    		int limit = (5 < timePriorityQueue.size() ? 5 : timePriorityQueue.size());
//            						    		for(int x = 0; x < limit; x++){
//            						    			//System.out.println(timePriorityQueue.peek());
//            						    			train_list.add(map.get(timePriorityQueue.poll()));
//            						    		}
//            						    		return train_list;
//            						    	}
//           								           							    
//            							 
//            							}
//            						}  							
//        						}
//        					}
//    					}
//    				}
//    				else{                                 // arrival station is express station
//    					char transfer_station = (char) (dir.equals("SB") ? departure_station + remain : departure_station - remain);
//    					if(exact_time){                   // take regular first, then express
//    						Queue<String> timePriorityQueue = new PriorityQueue<>(2, timeComparator);
//    				    	HashMap<String, String> map = new HashMap<>();
//        					for(int i = 6; i < 21; i++){
//        						for(int j = 0; j < 60; j += 15){
//        							if(j != 0){
//                						String temp = " ";							                        
//                						start_time = "" + (i < 10 ? "0" + i : i) + (j == 0 ? "00" : j);
//                						train_name = dir + start_time;
//                						train_with_date = dir + start_time + " " + departure_date;
//                						temp = getSchedule(train_name, Character.toString(departure_station));
//        								String real_temp = getSchedule(train_name, Character.toString(transfer_station));
//        								String transfer_arrival = departureToArrival(real_temp);
//        								real_temp = getSchedule(train_name, Character.toString(arrival_station));
//        								String arrival_time = "";
//        								if(real_temp != null){
//        								    arrival_time = departureToArrival(real_temp);
//        								}
//        								//System.out.println(train_name + " " + departure_station);
//                						if(temp.equals(departure_time)){
//                							if(j == 0)
//                								return train_list;
//                							else if(type.equals("Express")){
//                								List<String> next_train = findExpress(transfer_station, departure_date, transfer_arrival, arrival_station ,dir);
//                								train_list.add(train_with_date + ", first arrival time is: " + transfer_arrival + " at station " +
//                								               transfer_station + ", " + next_train.get(1) + " final arrive at: " + next_train.get(3));
//                							    return train_list;
//                							}
//                							else           // decide transfer or not transfer train
//                							{
//                								timePriorityQueue.add(arrival_time+train_name);
//            						    	    map.put(arrival_time+train_name, train_with_date + " Arrival time is: " + arrival_time);
//            						    	    
//                								List<String> next_train = findExpress(transfer_station, departure_date, transfer_arrival, arrival_station ,dir);
//                								timePriorityQueue.add(next_train.get(3)+train_name);
//                								map.put(next_train.get(3)+train_name, train_with_date + ", first arrival time is: " + transfer_arrival + " at station " +
//                								        transfer_station + ", " + next_train.get(1) + " final arrive at: " + next_train.get(3));
//                								
//                								train_list.add(map.get(timePriorityQueue.poll()));
//                							    return train_list;
//                							}
//                						}  							
//            						}
//        						}     						
//        					}
//    					}
//    					else{  // return top 5 combinatino train, start with regular train
//    						
//    						int regular_count = 0;
//    						int express_count = 0;
//    						Queue<String> timePriorityQueue = new PriorityQueue<>(6, timeComparator);
//    				    	HashMap<String, String> map = new HashMap<>();
//        					for(int i = 6; i < 21; i++){
//        						for(int j = 0; j < 60; j += 15){
//        							if(j != 0)
//        							{
//                						String temp = " ";							                        
//                						start_time = "" + (i < 10 ? "0" + i : i) + (j == 0 ? "00" : j);
//                						train_name = dir + start_time;
//                						train_with_date = dir + start_time + " " + departure_date;
//                						temp = getSchedule(train_name, Character.toString(departure_station));
//        								String real_temp = getSchedule(train_name, Character.toString(transfer_station));
//        								String transfer_arrival = departureToArrival(real_temp);
//        								real_temp = getSchedule(train_name, Character.toString(arrival_station));
//        								String arrival_time = "";
//        								if(real_temp != null){
//        								    arrival_time = departureToArrival(real_temp);
//        								}
//        								if(earlierTime(departure_time, temp) || temp.equals(departure_time)){
//                							if(type.equals("Express")){
//                						    	if(train_list.size() == 5){
//                							    	return train_list;
//                							    }
//                							    else{
//                    								List<String> next_train = findExpress(transfer_station, departure_date, transfer_arrival, arrival_station ,dir);
//                    								train_list.add(train_with_date + ", first arrival time is: " + transfer_arrival + " at station " +
//                    								               transfer_station + ", " + next_train.get(1) + " final arrive at: " + next_train.get(3));
//                    							}
//                							}
//                							else if(type.equals("Any"))
//                							{  //System.out.println(regular_count);
//                						    	if(regular_count < 3){
//                						    		timePriorityQueue.add(arrival_time+train_name);
//                						    	    map.put(arrival_time+train_name, train_with_date + " Arrival time is: " + arrival_time);
//                						    	    regular_count++;
//                						    	    //System.out.println(train_with_date + " Arrival time is: " + temp_arrival);
//                						    	}
//                						    	if(express_count < 3){
//                    								List<String> next_train = findExpress(transfer_station, departure_date, transfer_arrival, arrival_station ,dir);
//                						    		timePriorityQueue.add(next_train.get(3)+train_name);
//                						    	    map.put(next_train.get(3)+train_name, train_with_date + ", first arrival time is: " + transfer_arrival + " at station "+
//             								                transfer_station + ", " + next_train.get(1) + " final arrive at: " + next_train.get(3));
//                						    	    express_count++;
//                						    	    //System.out.println(train_with_date + " Arrival time is: " + temp_arrival);
//                						    	}
//                						    	if(i == 21 || timePriorityQueue.size() == 6){
//                						    		int limit = (5 < timePriorityQueue.size() ? 5 : timePriorityQueue.size());
//                						    		for(int x = 0; x < limit; x++){
//                						    			//System.out.println(timePriorityQueue.peek());
//                						    			train_list.add(map.get(timePriorityQueue.poll()));
//                						    		}
//                						    		return train_list;
//                						    	}              								           							  		 
//                							}
//                						}
//        							}					
//        						}
//        					}
//    					}
//
//    				}
//    			}
//    		}
//    		
//    	}
//    	else
//    	{   // both stations are not include express station
//    		// when connection is 0/1 or Regular ticket, user can only take reuglar train
//    		if(connection == 0 || connection == 1 || type.equals("Regular")){   
//        		if(!type.equals("Express")){                      // in this case, any = regular
//        			if(exact_time){   				              // only one train will be return	
//    					for(int i = 6; i < 21; i++){
//    						for(int j = 0; j < 60; j += 15){
//    							if( j != 0){                     // don't support express
//        							String temp = " ";							                        
//        							start_time = "" + (i < 10 ? "0" + i : i) + (j == 0 ? "00" : j);
//        							train_name = dir + start_time;
//        							train_with_date = dir + start_time + " " + departure_date;
//        							temp = getSchedule(train_name, Character.toString(departure_station));
//        							//System.out.println(temp);
//        							if(temp.equals(departure_time)){
//        							    train_list.add(train_with_date);
//        							    //System.out.println(train_with_date);
//        							    return train_list;
//        							}  							
//    						    }
//    						}
//    					}
//        			}
//        			else{     // return top 5 reuglar train
//        				for(int i = 6; i < 21; i++){
//    						for(int j = 0; j < 60; j += 15){ 
//    							if(j != 0){
//        							String temp = " ";							                        
//        							start_time = "" + (i < 10 ? "0" + i : i) + (j == 0 ? "00" : j);
//        							train_name = dir + start_time;
//        							train_with_date = dir + start_time + " " + departure_date;
//        							temp = getSchedule(train_name, Character.toString(departure_station));
//        							String real_temp = getSchedule(train_name, Character.toString(arrival_station));
//        							String temp_arrival = departureToArrival(real_temp);
//        							if(earlierTime(departure_time, temp) || temp.equals(departure_time)){
//        								if(train_list.size() == 5){
//        								    return train_list;
//        								}
//        								else
//        								    train_list.add(train_with_date + " Arrival time is: " + temp_arrival);		    
//        							}								
//    							}
//    						}
//        				}
//        			}
//        		}
//
//    	    }
//    		else if(connection == 2)
//    		{   // no need to take express train
//    			if((Math.abs(arrival_station-departure_station) < 7) || 
//    			(Math.abs((departure_station-(dir.equals("SB") ? 'A' : 'Z')))%5 == 1 && Math.abs(arrival_station-departure_station) < 10) ||    // Ex: B to J
//    			(Math.abs((departure_station-(dir.equals("SB") ? 'A' : 'Z')))%5 == 2 && Math.abs(arrival_station-departure_station) < 9) ||     // Ex: C to J
//    			(Math.abs((departure_station-(dir.equals("SB") ? 'A' : 'Z')))%5 == 3 && Math.abs(arrival_station-departure_station) < 8))       // Ex: D to J
//    			{
//            		if(!type.equals("Express")){                      // in this case, any = regular
//            			if(exact_time){   				              // only one train will be return	
//        					for(int i = 6; i < 21; i++){
//        						for(int j = 0; j < 60; j += 15){
//        							if( j != 0){                     // don't support express
//            							String temp = " ";							                        
//            							start_time = "" + (i < 10 ? "0" + i : i) + (j == 0 ? "00" : j);
//            							train_name = dir + start_time;
//            							train_with_date = dir + start_time + " " + departure_date;
//            							temp = getSchedule(train_name, Character.toString(departure_station));
//            							//System.out.println(temp);
//            							if(temp.equals(departure_time)){
//            							    train_list.add(train_with_date);
//            							    //System.out.println(train_with_date);
//            							    return train_list;
//            							}  							
//        						    }
//        						}
//        					}
//            			}
//            			else{     // return top 5 reuglar train
//            				for(int i = 6; i < 21; i++){
//        						for(int j = 0; j < 60; j += 15){ 
//        							if(j != 0){
//            							String temp = " ";							                        
//            							start_time = "" + (i < 10 ? "0" + i : i) + (j == 0 ? "00" : j);
//            							train_name = dir + start_time;
//            							train_with_date = dir + start_time + " " + departure_date;
//            							temp = getSchedule(train_name, Character.toString(departure_station));
//            							String real_temp = getSchedule(train_name, Character.toString(arrival_station));
//            							String temp_arrival = departureToArrival(real_temp);
//            							if(earlierTime(departure_time, temp) || temp.equals(departure_time)){
//            								if(train_list.size() == 5){
//            								    return train_list;
//            								}
//            								else
//            								    train_list.add(train_with_date + " Arrival time is: " + temp_arrival);		    
//            							}								
//        							}
//        						}
//            				}
//            			}
//            		}
//    			}
//    			else{      // trips contains at least two express station
//    				int remain = Math.abs((departure_station-(dir.equals("SB") ? 'A' : 'Z')))%5;
//    				char transfer_station1 = (char) (dir.equals("SB") ? departure_station + 5 - remain : departure_station -5 + remain);
//    				remain = Math.abs((arrival_station-(dir.equals("SB") ? 'A' : 'Z')))%5;
//    				char transfer_station2 =  (char) (dir.equals("SB") ? arrival_station - remain : arrival_station + remain);
//    				if(transfer_station1 == transfer_station2){
//    					System.out.println("two transfer stations are same with shouldn't happer!");
//    					return train_list;
//    				}
//					if(exact_time){                   // take regular first, then express, then back to regular
//						Queue<String> timePriorityQueue = new PriorityQueue<>(2, timeComparatorAdvance);
//				    	HashMap<String, String> map = new HashMap<>();
//    					for(int i = 6; i < 21; i++){
//    						for(int j = 0; j < 60; j += 15){
//    							if(j != 0){
//            						String temp = " ";							                        
//            						start_time = "" + (i < 10 ? "0" + i : i) + (j == 0 ? "00" : j);
//            						train_name = dir + start_time;
//            						train_with_date = dir + start_time + " " + departure_date;
//            						temp = getSchedule(train_name, Character.toString(departure_station));
//    								String real_temp = getSchedule(train_name, Character.toString(transfer_station1));
//    								String transfer_arrival1 = departureToArrival(real_temp);
//    								real_temp = getSchedule(train_name, Character.toString(arrival_station));
//    								String arrival_time = "";
//    								if(real_temp != null){
//    								    arrival_time = departureToArrival(real_temp);
//    								}
//            						if(temp.equals(departure_time)){
//            							if(type.equals("Express")){
//            								List<String> next_train1 = findExpress(transfer_station1, departure_date, transfer_arrival1, transfer_station2 ,dir);
//            								String transfer_arrival2 = next_train1.get(3);
//            								List<String> next_train2 = findRegularAdvance(transfer_station2, departure_date, transfer_arrival2, arrival_station ,dir);
//            								train_list.add(train_with_date + ", first arrival time is: " + transfer_arrival1 + " at station " +
//            								               transfer_station1 + ", " + next_train1.get(1) + " second arrive time is: " + next_train1.get(3)+ " at station " +
//                    								       transfer_station2 + ", " + next_train2.get(1) + " final arrive time is: " + next_train2.get(3)) ;
//            							    return train_list;
//            							}
//            							else           // decide transfer or not transfer train
//            							{
//            								timePriorityQueue.add(arrival_time+train_name+"0");
//        						    	    map.put(arrival_time+train_name+"0", train_with_date + " Arrival time is: " + arrival_time);
//        						    	    
//            								List<String> next_train1 = findExpress(transfer_station1, departure_date, transfer_arrival1, transfer_station2 ,dir);
//            								String transfer_arrival2 = next_train1.get(3);
//            								List<String> next_train2 = findRegularAdvance(transfer_station2, departure_date, transfer_arrival2, arrival_station ,dir);
//            								timePriorityQueue.add(next_train2.get(3)+train_name+"1");
//            								map.put(next_train2.get(3)+train_name+"1", train_with_date + ", first arrival time is: " + transfer_arrival1 + " at station " +
//            								               transfer_station1 + ", " + next_train1.get(1) + " second arrive time is: " + next_train1.get(3)+ " at station " +
//                    								       transfer_station2 + ", " + next_train2.get(1) + " final arrive time is: " + next_train2.get(3)) ;
//		
//            								train_list.add(map.get(timePriorityQueue.poll()));
//            							    return train_list;
//            							}
//            						}  							
//        						}
//    						}     						
//    					}
//					}
//					else{  // return top 5 combinatino train, regular -> express -> regular or regular
//						
//						int regular_count = 0;
//						int express_count = 0;
//						Queue<String> timePriorityQueue = new PriorityQueue<>(6, timeComparatorAdvance);
//				    	HashMap<String, String> map = new HashMap<>();
//    					for(int i = 6; i < 21; i++){
//    						for(int j = 0; j < 60; j += 15){
//    							if(j != 0)
//    							{
//            						String temp = " ";							                        
//            						start_time = "" + (i < 10 ? "0" + i : i) + (j == 0 ? "00" : j);
//            						train_name = dir + start_time;
//            						train_with_date = dir + start_time + " " + departure_date;
//            						temp = getSchedule(train_name, Character.toString(departure_station));
//    								String real_temp = getSchedule(train_name, Character.toString(transfer_station1));
//    								String transfer_arrival1 = departureToArrival(real_temp);
//    								real_temp = getSchedule(train_name, Character.toString(arrival_station));
//    								String arrival_time = "";
//    								if(real_temp != null){
//    								    arrival_time = departureToArrival(real_temp);
//    								}
//    								if(earlierTime(departure_time, temp) || temp.equals(departure_time)){
//            							if(type.equals("Express")){
//            								List<String> next_train1 = findExpress(transfer_station1, departure_date, transfer_arrival1, transfer_station2 ,dir);
//            								String transfer_arrival2 = next_train1.get(3);
//            								List<String> next_train2 = findRegularAdvance(transfer_station2, departure_date, transfer_arrival2, arrival_station ,dir);
//            								timePriorityQueue.add(next_train2.get(3)+train_name+"1");
//            								map.put(next_train2.get(3)+train_name+"1", train_with_date + ", first arrival time is: " + transfer_arrival1 + " at station " +
//            								               transfer_station1 + ", " + next_train1.get(1) + " second arrive time is: " + next_train1.get(3)+ " at station " +
//                    								       transfer_station2 + ", " + next_train2.get(1) + " final arrive time is: " + next_train2.get(3)) ;
//		
//            						    	if(i == 21 || timePriorityQueue.size() == 5){
//            						    		int limit = (5 < timePriorityQueue.size() ? 5 : timePriorityQueue.size());
//            						    		for(int x = 0; x < limit; x++){
//            						    			//System.out.println(timePriorityQueue.peek());
//            						    			train_list.add(map.get(timePriorityQueue.poll()));
//            						    		}
//            						    		return train_list;
//            						    	}
//                    							
//            							}
//            							else if(type.equals("Any"))
//            							{  //System.out.println(regular_count);
//            						    	if(regular_count < 3){
//            						    		timePriorityQueue.add(arrival_time+train_name+"0");
//            						    	    map.put(arrival_time+train_name+"0", train_with_date + " Arrival time is: " + arrival_time);
//            						    	    regular_count++;
//            						    	    //System.out.println(train_with_date + " Arrival time is: " + temp_arrival);
//            						    	}
//            						    	if(express_count < 3){
//                								List<String> next_train1 = findExpress(transfer_station1, departure_date, transfer_arrival1, transfer_station2 ,dir);
//                								String transfer_arrival2 = next_train1.get(3);
//                								List<String> next_train2 = findRegularAdvance(transfer_station2, departure_date, transfer_arrival2, arrival_station ,dir);
//                								timePriorityQueue.add(next_train2.get(3)+train_name+"1");
//                								map.put(next_train2.get(3)+train_name+"1", train_with_date + ", first arrival time is: " + transfer_arrival1 + " at station " +
//                								               transfer_station1 + ", " + next_train1.get(1) + " second arrive time is: " + next_train1.get(3)+ " at station " +
//                        								       transfer_station2 + ", " + next_train2.get(1) + " final arrive time is: " + next_train2.get(3)) ;
//            						    	    express_count++;
//            						    	    //System.out.println(train_with_date + " Arrival time is: " + temp_arrival);
//            						    	}
//            						    	if(i == 21 || timePriorityQueue.size() == 6){
//            						    		int limit = (5 < timePriorityQueue.size() ? 5 : timePriorityQueue.size());
//            						    		for(int x = 0; x < limit; x++){
//            						    			//System.out.println(timePriorityQueue.peek());
//            						    			train_list.add(map.get(timePriorityQueue.poll()));
//            						    		}
//            						    		return train_list;
//            						    	}              								           							  		 
//            							}
//            						}
//    							}					
//    						}
//    					}
//					}
//    				
//    			}
//    		}	
//        }            
//        return train_list;
//	}
	
	
    // Get top 5 fast train base on user requirement
	@GetMapping("/searchTicket")
	public List<SearchResult> searchTicketAdvance( 
			@RequestParam(value="departure_station", required=true) char departure_station,
			@RequestParam(value="departure_date", required=true) String departure_date,
			@RequestParam(value="departure_time", required=true) String departure_time,
			@RequestParam(value="arrival_station", required=true) char arrival_station,
			@RequestParam(value="type", required=true) String type,
			//@RequestParam(value="number", required=true) int number,
			@RequestParam(value="exact_time", required=true) boolean exact_time,
			@RequestParam(value="connection", required=true) int connection)
	{	
        List<SearchResult> train_list = new ArrayList<>();
        String start_time = " ";
        String train_name = " ";
        String train_with_date = " ";
        int price = 0;
        String dir = (departure_station < arrival_station ? "SB" : "NB");
        
        // record request data and lantence
		SystemReport report = systemreportRepository.findOne(departure_date);
	    if(report == null) {
	        return train_list;
	    }
	    if(connection == 0){
	    	report.setNone_request(report.getNone_request() + 1);
	    }
	    else if(connection == 1){
	    	report.setOne_request(report.getOne_request() + 1);
	    }
	    else if(connection == 2){
	    	report.setAny_request(report.getAny_request() + 1);
	    }
    	long start_millis =  System.currentTimeMillis();
	    report.setTotal_request(report.getTotal_request() + 1);
        
        List<Character> express_list = new ArrayList<>();
        express_list.add('A');
        express_list.add('F');
        express_list.add('K');
        express_list.add('P');
        express_list.add('U');
        express_list.add('Z');
        
        
    	if(express_list.contains(departure_station) && express_list.contains(arrival_station)){
			if(exact_time){   				              // only one train will be return	
				for(int i = 6; i < 22; i++){
					for(int j = 0; j < 60; j += 15){
						if(i == 21 && j != 0)             // last departure is 2100
							break;                       
						
						String temp = " ";							                        
						start_time = "" + (i < 10 ? "0" + i : i) + (j == 0 ? "00" : j);
						train_name = dir + start_time;
						train_with_date = dir + start_time + " " + departure_date;
						temp = getSchedule(train_name, Character.toString(departure_station));
						//System.out.println(temp);
						if(temp.equals(departure_time)){
							if(type.equals("Regular") && j == 0){
						        // calculate server side latancy
						        report = setReport(report, connection, start_millis);
							    systemreportRepository.save(report);
								return train_list;
							}
							else if(type.equals("Express") && j != 0){
						        // calculate server side latancy
						        report = setReport(report, connection, start_millis);
							    systemreportRepository.save(report);
								return train_list;
							}
							else{
								String real_temp = getSchedule(train_name, Character.toString(arrival_station));
								String temp_arrival = departureToArrival(real_temp);
								price = findPrice(departure_station, arrival_station) * 2;
								
								SearchResult sr = setSearchResult(train_with_date, Character.toString(departure_station), temp, Character.toString(arrival_station),
										                          temp_arrival, false, price, null);
						        train_list.add(sr);
						        
						        // calculate server side latancy
						        report = setReport(report, connection, start_millis);
							    systemreportRepository.save(report);
								
						        return train_list;
							}
						}
					}
				}
			}
			else{                                          // return top 5 choice
				int regular_count = 0;
				int express_count = 0;
				Queue<String> timePriorityQueue = new PriorityQueue<>(7, timeComparator);
		    	HashMap<String, SearchResult> map = new HashMap<>();
				for(int i = 6; i < 22; i++){
					for(int j = 0; j < 60; j += 15){
						if(i == 21 && j != 0)             // last departure is 2100
							break;  
						
						String temp = " ";							                        
						start_time = "" + (i < 10 ? "0" + i : i) + (j == 0 ? "00" : j);
						train_name = dir + start_time;
						train_with_date = dir + start_time + " " + departure_date;
						temp = getSchedule(train_name, Character.toString(departure_station));
						String real_temp = getSchedule(train_name, Character.toString(arrival_station));
						String temp_arrival = departureToArrival(real_temp);
						if(earlierTime(departure_time, temp) || temp.equals(departure_time)){
						    if(type.equals("Regular") && j != 0){
							    if(train_list.size() == 5){
							        // calculate server side latancy
							        report = setReport(report, connection, start_millis);
								    systemreportRepository.save(report);
							    	return train_list;
							    }
							    else{
									price = findPrice(departure_station, arrival_station);
									
									SearchResult sr = setSearchResult(train_with_date, Character.toString(departure_station), temp, Character.toString(arrival_station),
					                          temp_arrival, false, price, null);
	                                train_list.add(sr);					    
	                            }
						    }
						    else if(type.equals("Express") && j == 0){
						    	if(train_list.size() == 5){
							        // calculate server side latancy
							        report = setReport(report, connection, start_millis);
								    systemreportRepository.save(report);
							    	return train_list;
							    }
							    else{
									price = findPrice(departure_station, arrival_station) * 2;
									
									SearchResult sr = setSearchResult(train_with_date, Character.toString(departure_station), temp, Character.toString(arrival_station),
					                          temp_arrival, false, price, null);
	                                train_list.add(sr);	
							    }
						    }
						    else if(type.equals("Any")){           // compare both regular and express train
						    	if(regular_count < 5 && j != 0){
						    		timePriorityQueue.add(temp_arrival+train_name);
									price = findPrice(departure_station, arrival_station);
									
									SearchResult sr = setSearchResult(train_with_date, Character.toString(departure_station), temp, Character.toString(arrival_station),
					                          temp_arrival, false, price, null);

						    	    map.put(temp_arrival+train_name, sr);
						    	    regular_count++;						  
						    	}
						    	if(express_count < 2 && j == 0){
						    		timePriorityQueue.add(temp_arrival+train_name);
									price = findPrice(departure_station, arrival_station) * 2;
									
									SearchResult sr = setSearchResult(train_with_date, Character.toString(departure_station), temp, Character.toString(arrival_station),
					                          temp_arrival, false, price, null);

						    	    map.put(temp_arrival+train_name, sr);
						    	    express_count++;
						    	}
						    	if(i == 21 || timePriorityQueue.size() == 7){
						    		int limit = (5 < timePriorityQueue.size() ? 5 : timePriorityQueue.size());
						    		for(int x = 0; x < limit; x++){
						    			//System.out.println(timePriorityQueue.peek());
						    			train_list.add(map.get(timePriorityQueue.poll()));
						    		}
							        // calculate server side latancy
							        report = setReport(report, connection, start_millis);
								    systemreportRepository.save(report);
						    		return train_list;
						    	}
						    }
						}
					}
				}
		        // calculate server side latancy
		        report = setReport(report, connection, start_millis);
			    systemreportRepository.save(report);
				return train_list;
			}
    	}
    	else if(express_list.contains(departure_station) || express_list.contains(arrival_station))
    	{   // only one station is express station
    		// in this case, 0 connection and regular ticket means user can only take regular train
    		if(connection == 0 || type.equals("Regular")){   
        		if(!type.equals("Express")){                      // in this case, any = regular
        			if(exact_time){   				              // only one train will be return	
    					for(int i = 6; i < 21; i++){
    						for(int j = 0; j < 60; j += 15){
    							if( j != 0){                     // don't support express
        							String temp = " ";							                        
        							start_time = "" + (i < 10 ? "0" + i : i) + (j == 0 ? "00" : j);
        							train_name = dir + start_time;
        							train_with_date = dir + start_time + " " + departure_date;
        							temp = getSchedule(train_name, Character.toString(departure_station));
        							//System.out.println(temp);
        							if(temp.equals(departure_time)){
        								String real_temp = getSchedule(train_name, Character.toString(arrival_station));
        								String temp_arrival = departureToArrival(real_temp);
    									price = findPrice(departure_station, arrival_station);
    									
    									SearchResult sr = setSearchResult(train_with_date, Character.toString(departure_station), temp, Character.toString(arrival_station),
    					                          temp_arrival, false, price, null);

        							    train_list.add(sr);
        							    //System.out.println(train_with_date);
        						        // calculate server side latancy
        						        report = setReport(report, connection, start_millis);
        							    systemreportRepository.save(report);
        							    return train_list;
        							}  							
    						    }
    						}
    					}
        			}
        			else{     // return top 5 reuglar train
        				for(int i = 6; i < 21; i++){
    						for(int j = 0; j < 60; j += 15){ 
    							if(j != 0){
        							String temp = " ";							                        
        							start_time = "" + (i < 10 ? "0" + i : i) + (j == 0 ? "00" : j);
        							train_name = dir + start_time;
        							train_with_date = dir + start_time + " " + departure_date;
        							temp = getSchedule(train_name, Character.toString(departure_station));
        							String real_temp = getSchedule(train_name, Character.toString(arrival_station));
        							String temp_arrival = departureToArrival(real_temp);
        							if(earlierTime(departure_time, temp) || temp.equals(departure_time)){
        								if(train_list.size() == 5){
        							        // calculate server side latancy
        							        report = setReport(report, connection, start_millis);
        								    systemreportRepository.save(report);
        								    return train_list;
        								}
        							    else{
        									price = findPrice(departure_station, arrival_station);
        									
        									SearchResult sr = setSearchResult(train_with_date, Character.toString(departure_station), temp, Character.toString(arrival_station),
        					                          temp_arrival, false, price, null);
        	                                train_list.add(sr);					    
        	                            }      							
        							}								
    							}
    						}
        				}
        			}
        		}

    	    }
    		// one station include express, connection is 1 or 2, the result will be same
    		else if(connection == 1 || connection ==2)
    		{   // no need to take express train
    			if(Math.abs(arrival_station-departure_station) < 6){
            		if(!type.equals("Express")){                      // in this case, any = regular
            			if(exact_time){   				              // only one train will be return	
        					for(int i = 6; i < 21; i++){
        						for(int j = 0; j < 60; j += 15){
        							if( j != 0){                     // don't support express
            							String temp = " ";							                        
            							start_time = "" + (i < 10 ? "0" + i : i) + (j == 0 ? "00" : j);
            							train_name = dir + start_time;
            							train_with_date = dir + start_time + " " + departure_date;
            							temp = getSchedule(train_name, Character.toString(departure_station));
            							//System.out.println(temp);
            							if(temp.equals(departure_time)){
            								String real_temp = getSchedule(train_name, Character.toString(arrival_station));
            								String temp_arrival = departureToArrival(real_temp);
        									price = findPrice(departure_station, arrival_station);
        									
        									SearchResult sr = setSearchResult(train_with_date, Character.toString(departure_station), temp, Character.toString(arrival_station),
        					                          temp_arrival, false, price, null);

            							    train_list.add(sr);
            						        // calculate server side latancy
            						        report = setReport(report, connection, start_millis);
            							    systemreportRepository.save(report);
            							    return train_list;
            							}  							
        						    }
        						}
        					}
            			}
            			else{     // return top 5 reuglar train
            				for(int i = 6; i < 21; i++){
        						for(int j = 0; j < 60; j += 15){ 
        							if(j != 0){
            							String temp = " ";							                        
            							start_time = "" + (i < 10 ? "0" + i : i) + (j == 0 ? "00" : j);
            							train_name = dir + start_time;
            							train_with_date = dir + start_time + " " + departure_date;
            							temp = getSchedule(train_name, Character.toString(departure_station));
            							String real_temp = getSchedule(train_name, Character.toString(arrival_station));
            							String temp_arrival = departureToArrival(real_temp);
            							if(earlierTime(departure_time, temp) || temp.equals(departure_time)){
            								if(train_list.size() == 5){
            							        // calculate server side latancy
            							        report = setReport(report, connection, start_millis);
            								    systemreportRepository.save(report);
            								    return train_list;
            								}
            							    else{
            									price = findPrice(departure_station, arrival_station);
            									
            									SearchResult sr = setSearchResult(train_with_date, Character.toString(departure_station), temp, Character.toString(arrival_station),
            					                          temp_arrival, false, price, null);
            	                                train_list.add(sr);					    
            	                            }          							
            							}								
        							}
        						}
            				}
            			}
            		}
    			}
    			else{      // distance larger than 5 station
					int remain = Math.abs(departure_station-arrival_station) % 5;
					
    				if(express_list.contains(departure_station)){     // take express first, then regular
    					char transfer_station = (char) (dir.equals("SB") ? arrival_station - remain : arrival_station + remain);
    					if(exact_time){
        					for(int i = 6; i < 22; i++){
        						for(int j = 0; j < 60; j += 15){
        							if(i == 21 && j != 0)             // last departure is 2100
        								break;
        							
            						String temp = " ";							                        
            						start_time = "" + (i < 10 ? "0" + i : i) + (j == 0 ? "00" : j);
            						train_name = dir + start_time;
            						train_with_date = dir + start_time + " " + departure_date;
            						temp = getSchedule(train_name, Character.toString(departure_station));
    								String real_temp = getSchedule(train_name, Character.toString(transfer_station));
    								String transfer_arrival = departureToArrival(real_temp);
    								real_temp = getSchedule(train_name, Character.toString(arrival_station));
    								String arrival_time = "";
    								if(real_temp != null){
    								    arrival_time = departureToArrival(real_temp);
    								}
            						if(temp.equals(departure_time)){
            							if(type.equals("Express") && j != 0)
            								return train_list;
            							else if(j != 0){         // this is regular train
            								price = findPrice(departure_station, arrival_station);
            									
            								SearchResult sr = setSearchResult(train_with_date, Character.toString(departure_station), temp, Character.toString(arrival_station),
            										          arrival_time, false, price, null);
            	                            train_list.add(sr);
            						        // calculate server side latancy
            						        report = setReport(report, connection, start_millis);
            							    systemreportRepository.save(report);
            								return train_list;
            							}
            							else
            							{
            								price = findPrice(departure_station, transfer_station) * 2;
        									
            								SearchResult sr1 = setSearchResult(train_with_date, Character.toString(departure_station), temp, 
            										           Character.toString(transfer_station), transfer_arrival, true, price, null);
            								
            								
            								List<String> next_train = findRegularAdvance(transfer_station, departure_date, transfer_arrival, arrival_station ,dir);
            								
            								SearchResult sr2 = setSearchResult(next_train.get(1), Character.toString(transfer_station), next_train.get(2), 
 										           Character.toString(arrival_station), next_train.get(3), false, Integer.parseInt(next_train.get(4)), null);
 	
            								List<SearchResult> sr_list = new ArrayList<>();
            								sr_list.add(sr2);
            								sr1.setTransfer_train(sr_list);
            								
            							    train_list.add(sr1);
            						        // calculate server side latancy
            						        report = setReport(report, connection, start_millis);
            							    systemreportRepository.save(report);
            							    return train_list;
            							}
            						}  							
        						}
        					}
    					}
    					else{  // return top 5 combinatino train
    						
    						int regular_count = 0;
    						int express_count = 0;
    						Queue<String> timePriorityQueue = new PriorityQueue<>(6, timeComparator);
    				    	HashMap<String, SearchResult> map = new HashMap<>();
        					for(int i = 6; i < 22; i++){
        						for(int j = 0; j < 60; j += 15){
        							if(i == 21 && j != 0)             // last departure is 2100
        								break;
        							
            						String temp = " ";							                        
            						start_time = "" + (i < 10 ? "0" + i : i) + (j == 0 ? "00" : j);
            						train_name = dir + start_time;
            						train_with_date = dir + start_time + " " + departure_date;
            						temp = getSchedule(train_name, Character.toString(departure_station));
    								String real_temp = getSchedule(train_name, Character.toString(transfer_station));
    								String transfer_arrival = departureToArrival(real_temp);
    								real_temp = getSchedule(train_name, Character.toString(arrival_station));
    								String arrival_time = "";
    								if(real_temp != null){
    								    arrival_time = departureToArrival(real_temp);
    								}
    								if(earlierTime(departure_time, temp) || temp.equals(departure_time)){
            							if(type.equals("Express") && j == 0){
            						    	if(train_list.size() == 5){
            							        // calculate server side latancy
            							        report = setReport(report, connection, start_millis);
            								    systemreportRepository.save(report);
            							    	return train_list;
            							    }
            							    else{
                								price = findPrice(departure_station, transfer_station) * 2;
            									
                								SearchResult sr1 = setSearchResult(train_with_date, Character.toString(departure_station), temp, 
                										           Character.toString(transfer_station), transfer_arrival, true, price, null);
                								
                								
                								List<String> next_train = findRegularAdvance(transfer_station, departure_date, transfer_arrival, arrival_station ,dir);
                								
                								SearchResult sr2 = setSearchResult(next_train.get(1), Character.toString(transfer_station), next_train.get(2), 
     										           Character.toString(arrival_station), next_train.get(3), false, Integer.parseInt(next_train.get(4)), null);
     	
                								List<SearchResult> sr_list = new ArrayList<>();
                								sr_list.add(sr2);
                								sr1.setTransfer_train(sr_list);
                								
                							    train_list.add(sr1);          					
            							    }
            							}
            							else if(type.equals("Any"))
            							{  //System.out.println(regular_count);
            						    	if(regular_count < 4 && j != 0){
            						    		timePriorityQueue.add(arrival_time+train_name);
                								price = findPrice(departure_station, arrival_station);
            									
                								SearchResult sr = setSearchResult(train_with_date, Character.toString(departure_station), temp, Character.toString(arrival_station),
                										          arrival_time, false, price, null);

            						    	    map.put(arrival_time+train_name, sr);
            						    	    regular_count++;
            						    	    //System.out.println(train_with_date + " Arrival time is: " + temp_arrival);
            						    	}
            						    	if(express_count < 2 && j == 0){
                								price = findPrice(departure_station, transfer_station) * 2;
            									
                								SearchResult sr1 = setSearchResult(train_with_date, Character.toString(departure_station), temp, 
                										           Character.toString(transfer_station), transfer_arrival, true, price, null);
                								
                								
                								List<String> next_train = findRegularAdvance(transfer_station, departure_date, transfer_arrival, arrival_station ,dir);
                								
                								SearchResult sr2 = setSearchResult(next_train.get(1), Character.toString(transfer_station), next_train.get(2), 
     										           Character.toString(arrival_station), next_train.get(3), false, Integer.parseInt(next_train.get(4)), null);
     	
                								List<SearchResult> sr_list = new ArrayList<>();
                								sr_list.add(sr2);
                								sr1.setTransfer_train(sr_list);
         
            						    		timePriorityQueue.add(next_train.get(3)+train_name);
            						    	    map.put(next_train.get(3)+train_name, sr1);
            						    	    express_count++;
            						    	    //System.out.println(train_with_date + " Arrival time is: " + temp_arrival);
            						    	}
            						    	if(i == 21 || timePriorityQueue.size() == 6){
            						    		int limit = (5 < timePriorityQueue.size() ? 5 : timePriorityQueue.size());
            						    		for(int x = 0; x < limit; x++){
            						    			//System.out.println(timePriorityQueue.peek());
            						    			train_list.add(map.get(timePriorityQueue.poll()));
            						    		}
            							        // calculate server side latancy
            							        report = setReport(report, connection, start_millis);
            								    systemreportRepository.save(report);
            						    		return train_list;
            						    	}
           								           							    
            							 
            							}
            						}  							
        						}
        					}
    					}
    				}
    				else{                                 // arrival station is express station
    					char transfer_station = (char) (dir.equals("SB") ? departure_station + remain : departure_station - remain);
    					if(exact_time){                   // take regular first, then express
    						Queue<String> timePriorityQueue = new PriorityQueue<>(2, timeComparator);
    				    	HashMap<String, SearchResult> map = new HashMap<>();
        					for(int i = 6; i < 21; i++){
        						for(int j = 0; j < 60; j += 15){
        							if(j != 0){
                						String temp = " ";							                        
                						start_time = "" + (i < 10 ? "0" + i : i) + (j == 0 ? "00" : j);
                						train_name = dir + start_time;
                						train_with_date = dir + start_time + " " + departure_date;
                						temp = getSchedule(train_name, Character.toString(departure_station));
        								String real_temp = getSchedule(train_name, Character.toString(transfer_station));
        								String transfer_arrival = departureToArrival(real_temp);
        								real_temp = getSchedule(train_name, Character.toString(arrival_station));
        								String arrival_time = "";
        								if(real_temp != null){
        								    arrival_time = departureToArrival(real_temp);
        								}
        								//System.out.println(train_name + " " + departure_station);
                						if(temp.equals(departure_time)){
                							if(j == 0){
                						        // calculate server side latancy
                						        report = setReport(report, connection, start_millis);
                							    systemreportRepository.save(report);
                								return train_list;
                							}
                							else if(type.equals("Express")){
                								price = findPrice(departure_station, transfer_station);
            									
                								SearchResult sr1 = setSearchResult(train_with_date, Character.toString(departure_station), temp, 
                										           Character.toString(transfer_station), transfer_arrival, true, price, null);
                								             								
                								List<String> next_train = findExpress(transfer_station, departure_date, transfer_arrival, arrival_station ,dir);
                								
                								SearchResult sr2 = setSearchResult(next_train.get(1), Character.toString(transfer_station), next_train.get(2), 
     										           Character.toString(arrival_station), next_train.get(3), false, Integer.parseInt(next_train.get(4)), null);
     	
                								List<SearchResult> sr_list = new ArrayList<>();
                								sr_list.add(sr2);
                								sr1.setTransfer_train(sr_list);
                								
                							    train_list.add(sr1);
                						        // calculate server side latancy
                						        report = setReport(report, connection, start_millis);
                							    systemreportRepository.save(report);
                							    return train_list;
                							}
                							else           // decide transfer or not transfer train
                							{
                								timePriorityQueue.add(arrival_time+train_name);
                								price = findPrice(departure_station, arrival_station);
            									
                								SearchResult sr = setSearchResult(train_with_date, Character.toString(departure_station), temp, Character.toString(arrival_station),
                										          arrival_time, false, price, null);

            						    	    map.put(arrival_time+train_name, sr);
            						    	    
                								price = findPrice(departure_station, transfer_station);
                								SearchResult sr1 = setSearchResult(train_with_date, Character.toString(departure_station), temp, 
                										           Character.toString(transfer_station), transfer_arrival, true, price, null);
                								             								
                								List<String> next_train = findExpress(transfer_station, departure_date, transfer_arrival, arrival_station ,dir);
                								
                								SearchResult sr2 = setSearchResult(next_train.get(1), Character.toString(transfer_station), next_train.get(2), 
     										           Character.toString(arrival_station), next_train.get(3), false, Integer.parseInt(next_train.get(4)), null);
     	
                								List<SearchResult> sr_list = new ArrayList<>();
                								sr_list.add(sr2);
                								sr1.setTransfer_train(sr_list);
                								timePriorityQueue.add(next_train.get(3)+train_name);
                								map.put(next_train.get(3)+train_name, sr1);
                								
                								train_list.add(map.get(timePriorityQueue.poll()));
                						        // calculate server side latancy
                						        report = setReport(report, connection, start_millis);
                							    systemreportRepository.save(report);
                							    return train_list;
                							}
                						}  							
            						}
        						}     						
        					}
    					}
    					else{  // return top 5 combinatino train, start with regular train
    						
    						int regular_count = 0;
    						int express_count = 0;
    						Queue<String> timePriorityQueue = new PriorityQueue<>(6, timeComparator);
    				    	HashMap<String, SearchResult> map = new HashMap<>();
        					for(int i = 6; i < 21; i++){
        						for(int j = 0; j < 60; j += 15){
        							if(j != 0)
        							{
                						String temp = " ";							                        
                						start_time = "" + (i < 10 ? "0" + i : i) + (j == 0 ? "00" : j);
                						train_name = dir + start_time;
                						train_with_date = dir + start_time + " " + departure_date;
                						temp = getSchedule(train_name, Character.toString(departure_station));
        								String real_temp = getSchedule(train_name, Character.toString(transfer_station));
        								String transfer_arrival = departureToArrival(real_temp);
        								real_temp = getSchedule(train_name, Character.toString(arrival_station));
        								String arrival_time = "";
        								if(real_temp != null){
        								    arrival_time = departureToArrival(real_temp);
        								}
        								if(earlierTime(departure_time, temp) || temp.equals(departure_time)){
                							if(type.equals("Express")){
                						    	if(train_list.size() == 5){
                							        // calculate server side latancy
                							        report = setReport(report, connection, start_millis);
                								    systemreportRepository.save(report);
                							    	return train_list;
                							    }
                							    else{
                    								price = findPrice(departure_station, transfer_station);
                									
                    								SearchResult sr1 = setSearchResult(train_with_date, Character.toString(departure_station), temp, 
                    										           Character.toString(transfer_station), transfer_arrival, true, price, null);
                    								             								
                    								List<String> next_train = findExpress(transfer_station, departure_date, transfer_arrival, arrival_station ,dir);
                    								
                    								SearchResult sr2 = setSearchResult(next_train.get(1), Character.toString(transfer_station), next_train.get(2), 
         										           Character.toString(arrival_station), next_train.get(3), false, Integer.parseInt(next_train.get(4)), null);
         	
                    								List<SearchResult> sr_list = new ArrayList<>();
                    								sr_list.add(sr2);
                    								sr1.setTransfer_train(sr_list);
                    								
                    							    train_list.add(sr1);
                    							}
                							}
                							else if(type.equals("Any"))
                							{  //System.out.println(regular_count);
                						    	if(regular_count < 3){
                    								timePriorityQueue.add(arrival_time+train_name);
                    								price = findPrice(departure_station, arrival_station);
                									
                    								SearchResult sr = setSearchResult(train_with_date, Character.toString(departure_station), temp, Character.toString(arrival_station),
                    										          arrival_time, false, price, null);

                						    	    map.put(arrival_time+train_name, sr);
                						    	    regular_count++;
                						    	    //System.out.println(train_with_date + " Arrival time is: " + temp_arrival);
                						    	}
                						    	if(express_count < 3){
                    								price = findPrice(departure_station, transfer_station);
                									
                    								SearchResult sr1 = setSearchResult(train_with_date, Character.toString(departure_station), temp, 
                    										           Character.toString(transfer_station), transfer_arrival, true, price, null);
                    								             								
                    								List<String> next_train = findExpress(transfer_station, departure_date, transfer_arrival, arrival_station ,dir);
                    								
                    								SearchResult sr2 = setSearchResult(next_train.get(1), Character.toString(transfer_station), next_train.get(2), 
         										           Character.toString(arrival_station), next_train.get(3), false, Integer.parseInt(next_train.get(4)), null);
         	
                    								List<SearchResult> sr_list = new ArrayList<>();
                    								sr_list.add(sr2);
                    								sr1.setTransfer_train(sr_list);
                    								
                						    		timePriorityQueue.add(next_train.get(3)+train_name);
                						    	    map.put(next_train.get(3)+train_name, sr1);
                						    	    express_count++;
                						    	    //System.out.println(train_with_date + " Arrival time is: " + temp_arrival);
                						    	}
                						    	if(i == 21 || timePriorityQueue.size() == 6){
                						    		int limit = (5 < timePriorityQueue.size() ? 5 : timePriorityQueue.size());
                						    		for(int x = 0; x < limit; x++){
                						    			//System.out.println(timePriorityQueue.peek());
                						    			train_list.add(map.get(timePriorityQueue.poll()));
                						    		}
                							        // calculate server side latancy
                							        report = setReport(report, connection, start_millis);
                								    systemreportRepository.save(report);
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
    		}
    		
    	}
    	else
    	{   // both stations are not include express station
    		// when connection is 0/1 or Regular ticket, user can only take reuglar train
    		if(connection == 0 || connection == 1 || type.equals("Regular")){   
        		if(!type.equals("Express")){                      // in this case, any = regular
        			if(exact_time){   				              // only one train will be return	
    					for(int i = 6; i < 21; i++){
    						for(int j = 0; j < 60; j += 15){
    							if( j != 0){                     // don't support express
        							String temp = " ";							                        
        							start_time = "" + (i < 10 ? "0" + i : i) + (j == 0 ? "00" : j);
        							train_name = dir + start_time;
        							train_with_date = dir + start_time + " " + departure_date;
        							temp = getSchedule(train_name, Character.toString(departure_station));
        							//System.out.println(temp);
        							if(temp.equals(departure_time)){
        								String real_temp = getSchedule(train_name, Character.toString(arrival_station));
        								String temp_arrival = departureToArrival(real_temp);
    									price = findPrice(departure_station, arrival_station);
    									
    									SearchResult sr = setSearchResult(train_with_date, Character.toString(departure_station), temp, Character.toString(arrival_station),
    					                          temp_arrival, false, price, null);

        							    train_list.add(sr);
        						        // calculate server side latancy
        						        report = setReport(report, connection, start_millis);
        							    systemreportRepository.save(report);
        							    return train_list;
        							}  							
    						    }
    						}
    					}
        			}
        			else{     // return top 5 reuglar train
        				for(int i = 6; i < 21; i++){
    						for(int j = 0; j < 60; j += 15){ 
    							if(j != 0){
        							String temp = " ";							                        
        							start_time = "" + (i < 10 ? "0" + i : i) + (j == 0 ? "00" : j);
        							train_name = dir + start_time;
        							train_with_date = dir + start_time + " " + departure_date;
        							temp = getSchedule(train_name, Character.toString(departure_station));
        							String real_temp = getSchedule(train_name, Character.toString(arrival_station));
        							String temp_arrival = departureToArrival(real_temp);
        							if(earlierTime(departure_time, temp) || temp.equals(departure_time)){
        								if(train_list.size() == 5){
        							        // calculate server side latancy
        							        report = setReport(report, connection, start_millis);
        								    systemreportRepository.save(report);
        								    return train_list;
        								}
        							    else{
        									price = findPrice(departure_station, arrival_station);
        									
        									SearchResult sr = setSearchResult(train_with_date, Character.toString(departure_station), temp, Character.toString(arrival_station),
        					                          temp_arrival, false, price, null);
        	                                train_list.add(sr);					    
        	                            }		    
        							}								
    							}
    						}
        				}
        			}
        		}

    	    }
    		else if(connection == 2)
    		{   // no need to take express train
    			if((Math.abs(arrival_station-departure_station) < 7) || 
    			(Math.abs((departure_station-(dir.equals("SB") ? 'A' : 'Z')))%5 == 1 && Math.abs(arrival_station-departure_station) < 10) ||    // Ex: B to J
    			(Math.abs((departure_station-(dir.equals("SB") ? 'A' : 'Z')))%5 == 2 && Math.abs(arrival_station-departure_station) < 9) ||     // Ex: C to J
    			(Math.abs((departure_station-(dir.equals("SB") ? 'A' : 'Z')))%5 == 3 && Math.abs(arrival_station-departure_station) < 8))       // Ex: D to J
    			{
            		if(!type.equals("Express")){                      // in this case, any = regular
            			if(exact_time){   				              // only one train will be return	
        					for(int i = 6; i < 21; i++){
        						for(int j = 0; j < 60; j += 15){
        							if( j != 0){                     // don't support express
            							String temp = " ";							                        
            							start_time = "" + (i < 10 ? "0" + i : i) + (j == 0 ? "00" : j);
            							train_name = dir + start_time;
            							train_with_date = dir + start_time + " " + departure_date;
            							temp = getSchedule(train_name, Character.toString(departure_station));
            							//System.out.println(temp);
            							if(temp.equals(departure_time)){
            								String real_temp = getSchedule(train_name, Character.toString(arrival_station));
            								String temp_arrival = departureToArrival(real_temp);
        									price = findPrice(departure_station, arrival_station);
        									
        									SearchResult sr = setSearchResult(train_with_date, Character.toString(departure_station), temp, Character.toString(arrival_station),
        					                          temp_arrival, false, price, null);

            							    train_list.add(sr);
            						        // calculate server side latancy
            						        report = setReport(report, connection, start_millis);
            							    systemreportRepository.save(report);
            							    return train_list;
            							}  							
        						    }
        						}
        					}
            			}
            			else{     // return top 5 reuglar train
            				for(int i = 6; i < 21; i++){
        						for(int j = 0; j < 60; j += 15){ 
        							if(j != 0){
            							String temp = " ";							                        
            							start_time = "" + (i < 10 ? "0" + i : i) + (j == 0 ? "00" : j);
            							train_name = dir + start_time;
            							train_with_date = dir + start_time + " " + departure_date;
            							temp = getSchedule(train_name, Character.toString(departure_station));
            							String real_temp = getSchedule(train_name, Character.toString(arrival_station));
            							String temp_arrival = departureToArrival(real_temp);
            							if(earlierTime(departure_time, temp) || temp.equals(departure_time)){
            								if(train_list.size() == 5){
            							        // calculate server side latancy
            							        report = setReport(report, connection, start_millis);
            								    systemreportRepository.save(report);
            								    return train_list;
            								}
            							    else{
            									price = findPrice(departure_station, arrival_station);
            									
            									SearchResult sr = setSearchResult(train_with_date, Character.toString(departure_station), temp, Character.toString(arrival_station),
            					                          temp_arrival, false, price, null);
            	                                train_list.add(sr);					    
            	                            }		    
            							}								
        							}
        						}
            				}
            			}
            		}
    			}
    			else{      // trips contains at least two express station
    				int remain = Math.abs((departure_station-(dir.equals("SB") ? 'A' : 'Z')))%5;
    				char transfer_station1 = (char) (dir.equals("SB") ? departure_station + 5 - remain : departure_station -5 + remain);
    				remain = Math.abs((arrival_station-(dir.equals("SB") ? 'A' : 'Z')))%5;
    				char transfer_station2 =  (char) (dir.equals("SB") ? arrival_station - remain : arrival_station + remain);
    				if(transfer_station1 == transfer_station2){
    					System.out.println("two transfer stations are same with shouldn't happer!");
    					return train_list;
    				}
					if(exact_time){                   // take regular first, then express, then back to regular
						Queue<String> timePriorityQueue = new PriorityQueue<>(2, timeComparatorAdvance);
				    	HashMap<String, SearchResult> map = new HashMap<>();
    					for(int i = 6; i < 21; i++){
    						for(int j = 0; j < 60; j += 15){
    							if(j != 0){
            						String temp = " ";							                        
            						start_time = "" + (i < 10 ? "0" + i : i) + (j == 0 ? "00" : j);
            						train_name = dir + start_time;
            						train_with_date = dir + start_time + " " + departure_date;
            						temp = getSchedule(train_name, Character.toString(departure_station));
    								String real_temp = getSchedule(train_name, Character.toString(transfer_station1));
    								String transfer_arrival1 = departureToArrival(real_temp);
    								real_temp = getSchedule(train_name, Character.toString(arrival_station));
    								String arrival_time = "";
    								if(real_temp != null){
    								    arrival_time = departureToArrival(real_temp);
    								}
            						if(temp.equals(departure_time)){
            							if(type.equals("Express")){
            								price = findPrice(departure_station, transfer_station1);
        									
            								SearchResult sr1 = setSearchResult(train_with_date, Character.toString(departure_station), temp, 
            										           Character.toString(transfer_station1), transfer_arrival1, true, price, null);        								             								
            								
            								List<String> next_train1 = findExpress(transfer_station1, departure_date, transfer_arrival1, transfer_station2 ,dir);
            								String transfer_arrival2 = next_train1.get(3);
            								
            								SearchResult sr2 = setSearchResult(next_train1.get(1), Character.toString(transfer_station1), next_train1.get(2), 
  										           Character.toString(transfer_station2), next_train1.get(3), false, Integer.parseInt(next_train1.get(4)), null);
  	
            								List<String> next_train2 = findRegularAdvance(transfer_station2, departure_date, transfer_arrival2, arrival_station ,dir);
            								
            								SearchResult sr3 = setSearchResult(next_train2.get(1), Character.toString(transfer_station2), next_train2.get(2), 
   										           Character.toString(arrival_station), next_train2.get(3), false, Integer.parseInt(next_train2.get(4)), null);
   
             								List<SearchResult> sr_list = new ArrayList<>();
             								sr_list.add(sr2);
             								sr_list.add(sr3);
             								sr1.setTransfer_train(sr_list);
             								
            								train_list.add(sr1) ;
            						        // calculate server side latancy
            						        report = setReport(report, connection, start_millis);
            							    systemreportRepository.save(report);
            							    return train_list;
            							}
            							else           // decide transfer or not transfer train
            							{
        									price = findPrice(departure_station, arrival_station);
        									
        									SearchResult sr = setSearchResult(train_with_date, Character.toString(departure_station), temp, Character.toString(arrival_station),
        											                          arrival_time, false, price, null);
            								timePriorityQueue.add(arrival_time+train_name+"0");
        						    	    map.put(arrival_time+train_name+"0", sr);
        						    	    
            								price = findPrice(departure_station, transfer_station1);
        									
            								SearchResult sr1 = setSearchResult(train_with_date, Character.toString(departure_station), temp, 
            										           Character.toString(transfer_station1), transfer_arrival1, true, price, null);        								             								
            								
            								List<String> next_train1 = findExpress(transfer_station1, departure_date, transfer_arrival1, transfer_station2 ,dir);
            								String transfer_arrival2 = next_train1.get(3);
            								
            								SearchResult sr2 = setSearchResult(next_train1.get(1), Character.toString(transfer_station1), next_train1.get(2), 
  										           Character.toString(transfer_station2), next_train1.get(3), false, Integer.parseInt(next_train1.get(4)), null);
  	
            								List<String> next_train2 = findRegularAdvance(transfer_station2, departure_date, transfer_arrival2, arrival_station ,dir);
            								
            								SearchResult sr3 = setSearchResult(next_train2.get(1), Character.toString(transfer_station2), next_train2.get(2), 
   										           Character.toString(arrival_station), next_train2.get(3), false, Integer.parseInt(next_train2.get(4)), null);
   
             								List<SearchResult> sr_list = new ArrayList<>();
             								sr_list.add(sr2);
             								sr_list.add(sr3);
             								sr1.setTransfer_train(sr_list);
          
            								timePriorityQueue.add(next_train2.get(3)+train_name+"1");
            								map.put(next_train2.get(3)+train_name+"1", sr1);
		
            								train_list.add(map.get(timePriorityQueue.poll()));
            						        // calculate server side latancy
            						        report = setReport(report, connection, start_millis);
            							    systemreportRepository.save(report);
            							    return train_list;
            							}
            						}  							
        						}
    						}     						
    					}
					}
					else{  // return top 5 combinatino train, regular -> express -> regular or regular
						
						int regular_count = 0;
						int express_count = 0;
						Queue<String> timePriorityQueue = new PriorityQueue<>(6, timeComparatorAdvance);
				    	HashMap<String, SearchResult> map = new HashMap<>();
    					for(int i = 6; i < 21; i++){
    						for(int j = 0; j < 60; j += 15){
    							if(j != 0)
    							{
            						String temp = " ";							                        
            						start_time = "" + (i < 10 ? "0" + i : i) + (j == 0 ? "00" : j);
            						train_name = dir + start_time;
            						train_with_date = dir + start_time + " " + departure_date;
            						temp = getSchedule(train_name, Character.toString(departure_station));
    								String real_temp = getSchedule(train_name, Character.toString(transfer_station1));
    								String transfer_arrival1 = departureToArrival(real_temp);
    								real_temp = getSchedule(train_name, Character.toString(arrival_station));
    								String arrival_time = "";
    								if(real_temp != null){
    								    arrival_time = departureToArrival(real_temp);
    								}
    								if(earlierTime(departure_time, temp) || temp.equals(departure_time)){
            							if(type.equals("Express")){
            								price = findPrice(departure_station, transfer_station1);           									
            								SearchResult sr1 = setSearchResult(train_with_date, Character.toString(departure_station), temp, 
            										           Character.toString(transfer_station1), transfer_arrival1, true, price, null);        								             								
            								
            								List<String> next_train1 = findExpress(transfer_station1, departure_date, transfer_arrival1, transfer_station2 ,dir);
            								String transfer_arrival2 = next_train1.get(3);
            								
            								SearchResult sr2 = setSearchResult(next_train1.get(1), Character.toString(transfer_station1), next_train1.get(2), 
  										           Character.toString(transfer_station2), next_train1.get(3), false, Integer.parseInt(next_train1.get(4)), null);
  	
            								List<String> next_train2 = findRegularAdvance(transfer_station2, departure_date, transfer_arrival2, arrival_station ,dir);
            								
            								SearchResult sr3 = setSearchResult(next_train2.get(1), Character.toString(transfer_station2), next_train2.get(2), 
   										           Character.toString(arrival_station), next_train2.get(3), false, Integer.parseInt(next_train2.get(4)), null);
   
             								List<SearchResult> sr_list = new ArrayList<>();
             								sr_list.add(sr2);
             								sr_list.add(sr3);
             								sr1.setTransfer_train(sr_list);
             								
            								timePriorityQueue.add(next_train2.get(3)+train_name+"1");
            								map.put(next_train2.get(3)+train_name+"1", sr1) ;
		
            						    	if(i == 21 || timePriorityQueue.size() == 5){
            						    		int limit = (5 < timePriorityQueue.size() ? 5 : timePriorityQueue.size());
            						    		for(int x = 0; x < limit; x++){
            						    			//System.out.println(timePriorityQueue.peek());
            						    			train_list.add(map.get(timePriorityQueue.poll()));
            						    		}
            							        // calculate server side latancy
            							        report = setReport(report, connection, start_millis);
            								    systemreportRepository.save(report);
            						    		return train_list;
            						    	}
                    							
            							}
            							else if(type.equals("Any"))
            							{  //System.out.println(regular_count);
            						    	if(regular_count < 3){
            									price = findPrice(departure_station, arrival_station);            						
            									SearchResult sr = setSearchResult(train_with_date, Character.toString(departure_station), temp, Character.toString(arrival_station),
            											                          arrival_time, false, price, null);
            						    		timePriorityQueue.add(arrival_time+train_name+"0");
            						    	    map.put(arrival_time+train_name+"0", sr);
            						    	    regular_count++;
            						    	    //System.out.println(train_with_date + " Arrival time is: " + temp_arrival);
            						    	}
            						    	if(express_count < 3){
                								price = findPrice(departure_station, transfer_station1);           									
                								SearchResult sr1 = setSearchResult(train_with_date, Character.toString(departure_station), temp, 
                										           Character.toString(transfer_station1), transfer_arrival1, true, price, null);        								             								
                								
                								List<String> next_train1 = findExpress(transfer_station1, departure_date, transfer_arrival1, transfer_station2 ,dir);
                								String transfer_arrival2 = next_train1.get(3);
                								
                								SearchResult sr2 = setSearchResult(next_train1.get(1), Character.toString(transfer_station1), next_train1.get(2), 
      										           Character.toString(transfer_station2), next_train1.get(3), false, Integer.parseInt(next_train1.get(4)), null);
      	
                								List<String> next_train2 = findRegularAdvance(transfer_station2, departure_date, transfer_arrival2, arrival_station ,dir);
                								
                								SearchResult sr3 = setSearchResult(next_train2.get(1), Character.toString(transfer_station2), next_train2.get(2), 
       										           Character.toString(arrival_station), next_train2.get(3), false, Integer.parseInt(next_train2.get(4)), null);
       
                 								List<SearchResult> sr_list = new ArrayList<>();
                 								sr_list.add(sr2);
                 								sr_list.add(sr3);
                 								sr1.setTransfer_train(sr_list);
                 								
                								timePriorityQueue.add(next_train2.get(3)+train_name+"1");
                								map.put(next_train2.get(3)+train_name+"1",sr1) ;
            						    	    express_count++;
            						    	    //System.out.println(train_with_date + " Arrival time is: " + temp_arrival);
            						    	}
            						    	if(i == 21 || timePriorityQueue.size() == 6){
            						    		int limit = (5 < timePriorityQueue.size() ? 5 : timePriorityQueue.size());
            						    		for(int x = 0; x < limit; x++){
            						    			//System.out.println(timePriorityQueue.peek());
            						    			train_list.add(map.get(timePriorityQueue.poll()));
            						    		}
            							        // calculate server side latancy
            							        report = setReport(report, connection, start_millis);
            								    systemreportRepository.save(report);
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
        }    
        // calculate server side latancy
        report = setReport(report, connection, start_millis);
	    systemreportRepository.save(report);
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
	
	// helper function to compare time
	public boolean earlierTime(String t1, String t2){           // default time format xx:xx
		int t1_hour = Integer.parseInt(t1.substring(0,2));
		int t2_hour = Integer.parseInt(t2.substring(0,2));
		int t1_min = Integer.parseInt(t1.substring(3));
		int t2_min = Integer.parseInt(t2.substring(3));
		
		if(t1_hour < t2_hour)
			return true;
		else if(t1_hour > t2_hour)
			return false;
		else{
			if(t1_min < t2_min)
				return true;
			else
				return false;
		}
	}
	
	// comparator for time priority queue
	public static Comparator<String> timeComparator = new Comparator<String>(){
		@Override
		public int compare(String s1, String s2){
			String t1 = s1.substring(0, 5);
			String t2 = s2.substring(0, 5);
			int t1_hour = Integer.parseInt(t1.substring(0,2));
			int t2_hour = Integer.parseInt(t2.substring(0,2));
			int t1_min = Integer.parseInt(t1.substring(3));
			int t2_min = Integer.parseInt(t2.substring(3));
			
			if(t1_hour < t2_hour)
				return -1;
			else if(t1_hour > t2_hour)
				return 1;
			else{
				if(t1_min < t2_min)
					return -1;
				else if(t1_min > t2_min)
					return 1;
				else{
					int train1 = Integer.parseInt(s1.substring(7));
					int train2 = Integer.parseInt(s2.substring(7));
					if(train1 < train2)
						return 1;
					else
						return -1;
				}
			}
		}
	};
	
	// comparator for time priority queue with two connection
	public static Comparator<String> timeComparatorAdvance = new Comparator<String>(){
		@Override
		public int compare(String s1, String s2){
			String t1 = s1.substring(0, 5);
			String t2 = s2.substring(0, 5);
			int t1_hour = Integer.parseInt(t1.substring(0,2));
			int t2_hour = Integer.parseInt(t2.substring(0,2));
			int t1_min = Integer.parseInt(t1.substring(3));
			int t2_min = Integer.parseInt(t2.substring(3));
			
			if(t1_hour < t2_hour)
				return -1;
			else if(t1_hour > t2_hour)
				return 1;
			else{
				if(t1_min < t2_min)
					return -1;
				else if(t1_min > t2_min)
					return 1;
				else{
					int train1 = Integer.parseInt(s1.substring(7, 11));
					int train2 = Integer.parseInt(s2.substring(7, 11));
					if(train1 < train2)
						return 1;
					else if(train1 > train2)
						return -1;
					else{
						if(s1.charAt(11) == '1')            // 1 means this is a connected train
							return 1;
						else
							return -1;
					}
				}
			}
		}
	};
	
	// change departure time to arrival time
	public String departureToArrival(String departure_time){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(departure_time.substring(0, 2)));
		cal.set(Calendar.MINUTE, Integer.parseInt(departure_time.substring(3)));
		cal.add(Calendar.MINUTE, -3);
		return sdf.format(cal.getTime());  // this is actual arrival time
	}
	
	// helper function to find connected one earlies regular train
	public String findRegular( char departure_station, String departure_date, String departure_time, 
			                   char arrival_station, String dir)
	{
		for(int i = 6; i < 21; i++){
			for(int j = 0; j < 60; j += 15){
				if( j != 0){                     // don't support express
					String temp = " ";							                        
					String start_time = "" + (i < 10 ? "0" + i : i) + j;
					String train_name = dir + start_time;
					String train_with_date = dir + start_time + " " + departure_date;
					temp = getSchedule(train_name, Character.toString(departure_station));
					String real_temp = getSchedule(train_name, Character.toString(arrival_station));
					String temp_arrival = departureToArrival(real_temp);
					if(earlierTime(departure_time, temp) || temp.equals(departure_time)){
					    return train_with_date + ", Final arrival time is: " + temp_arrival;
					}  							
			    }
			}
		}
		
		return "";
	}
	
	// helper function to find connected one earlies regular train
	public String findRegularTime( char departure_station, String departure_date, String departure_time, 
			                   char arrival_station, String dir)
	{
		for(int i = 6; i < 21; i++){
			for(int j = 0; j < 60; j += 15){
				if( j != 0){                     // don't support express
					String temp = " ";							                        
					String start_time = "" + (i < 10 ? "0" + i : i) + j;
					String train_name = dir + start_time;
					temp = getSchedule(train_name, Character.toString(departure_station));
					String real_temp = getSchedule(train_name, Character.toString(arrival_station));
					String temp_arrival = departureToArrival(real_temp);
					if(earlierTime(departure_time, temp) || temp.equals(departure_time)){
					    return temp_arrival;
					}  							
			    }
			}
		}
		
		return "";
	}
	
	public List<String> findExpress( char departure_station, String departure_date, String departure_time, 
                               char arrival_station, String dir)
    {
		List<String> train_info = new ArrayList<>();
		for(int i = 6; i < 22; i++){
			String temp = " ";							                        
			String start_time = "" + (i < 10 ? "0" + i : i) + "00";
			String train_name = dir + start_time;
			String train_with_date = dir + start_time + " " + departure_date;
			temp = getSchedule(train_name, Character.toString(departure_station));
			String real_temp = getSchedule(train_name, Character.toString(arrival_station));
			String temp_arrival = departureToArrival(real_temp);
			if(earlierTime(departure_time, temp) || temp.equals(departure_time)){
				train_info.add(train_name);
				train_info.add(train_with_date);
				train_info.add(temp);
				train_info.add(temp_arrival);
				int price = findPrice(departure_station, arrival_station) * 2;
				train_info.add(Integer.toString(price));
				return train_info;
			}  							
		}
		return train_info;
    }
	
	// helper function to find connected one earlies regular train
	public List<String> findRegularAdvance( char departure_station, String departure_date, String departure_time, 
			                   char arrival_station, String dir)
	{
		List<String> train_info = new ArrayList<>();
		for(int i = 6; i < 21; i++){
			for(int j = 0; j < 60; j += 15){
				if( j != 0){                     // don't support express
					String temp = " ";							                        
					String start_time = "" + (i < 10 ? "0" + i : i) + j;
					String train_name = dir + start_time;
					String train_with_date = dir + start_time + " " + departure_date;
					temp = getSchedule(train_name, Character.toString(departure_station));
					String real_temp = getSchedule(train_name, Character.toString(arrival_station));
					String temp_arrival = departureToArrival(real_temp);
					if(earlierTime(departure_time, temp) || temp.equals(departure_time)){
						train_info.add(train_name);
						train_info.add(train_with_date);
						train_info.add(temp);
						train_info.add(temp_arrival);
						int price = findPrice(departure_station, arrival_station);
						train_info.add(Integer.toString(price));
						return train_info;
					}  							
			    }
			}
		}
		
		return train_info;
	}
	
	public int findPrice(char departure_station, char arrival_station){
		int stations = Math.abs(departure_station-arrival_station);
		int price = 0;
		
		if(stations % 5 == 0)
			price = stations / 5;
		else
			price = stations / 5 + 1;

		return price;
	}
	
	public SearchResult setSearchResult(String train_name, String departure_station, String departure_time, String arrival_station,
                                        String arrival_time, boolean transfer, int price, List<SearchResult> transfer_train)
	{
		SearchResult sr = new SearchResult();
		sr.setTrain_name(train_name);
		sr.setDeparture_station(departure_station);
		sr.setDeparture_time(departure_time);
		sr.setArrival_station(arrival_station);
		sr.setArrival_time(arrival_time);
		sr.setTransfer(transfer);
		sr.setPrice(price);
		sr.setTransfer_train(transfer_train);
		return sr;
	}
	
	public SystemReport setReport(SystemReport report, int connection, long start_millis){
        // calculate server side latancy
		long end_millis = System.currentTimeMillis();						
		long system_latency = end_millis - start_millis;
		System.out.println("Server side latency is: " + system_latency);
		
	    if(connection == 0){
	    	long prev = report.getNone_latency();
	    	int num = report.getNone_request();
	    	long avg = (system_latency + (num-1) * prev) / num;
	    	report.setNone_latency(avg);
	    }
	    else if(connection == 1){
	    	long prev = report.getOne_latency();
	    	int num = report.getOne_request();
	    	long avg = (system_latency + (num-1) * prev) / num;
	    	report.setOne_latency(avg);
	    }
	    else if(connection == 2){
	    	long prev = report.getAny_latency();
	    	int num = report.getAny_request();
	    	long avg = (system_latency + (num-1) * prev) / num;
	    	report.setAny_latency(avg);
	    }
	    return report;
	}
	
	
}

