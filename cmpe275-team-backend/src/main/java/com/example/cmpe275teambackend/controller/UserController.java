package com.example.cmpe275teambackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cmpe275teambackend.model.Address;
import com.example.cmpe275teambackend.model.User;
import com.example.cmpe275teambackend.repository.UserRepository;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
	
	@Autowired
    UserRepository userRepository;
	
//	@Autowired
//	SponsorRepository sponsorRepository;

	// Get All Users
	@GetMapping("/users")
	public List<User> getAllUsers() {
	    return userRepository.findAll();
	}
	
    // Create a new User
	@PostMapping("/user")
	public ResponseEntity<User> createUser( 
			@RequestParam(value="name", required=true) String name,
			@RequestParam(value="email", required=true) String email,
			@RequestParam(value="password", required=true) String password,
			@RequestParam(value="street", required=false) String street,
			@RequestParam(value="city", required=false) String city,
			@RequestParam(value="state", required=false) String state,
			@RequestParam(value="zip", required=false) String zip)
	{

		User user = new User();
		
		// check duplicate email for new user
		List<User> userList = userRepository.findAll();
		for(int i = 0; i < userList.size(); i++){
			User temp = userList.get(i);
			if(temp.getEmail().equals(email))
				return ResponseEntity.badRequest().build();
		}
		
		
		//System.out.print(player.getId());
//		if(sponsor_id != null){                           // check if params contain sponsor_id
//		    Sponsor sponsor = sponsorRepository.findOne(sponsor_id);
//		    if(sponsor != null){                          // check whether exist sponsor have this id
//			    player.setSponsor(sponsor);
//		    }
//		    else
//		        return ResponseEntity.badRequest().build();
//		}
		
		Address address = new Address();
		address.setCity(city);
		address.setState(state);
		address.setStreet(street);
		address.setZip(zip);
		
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		user.setAddress(address);
		
		
		
	    User newuser = userRepository.save(user);    // save new create play
	    return ResponseEntity.ok(newuser);
	}

    // Get a User
	@GetMapping("/user/{email}")
	public ResponseEntity<User> getUserByEmail(@PathVariable(value = "email") String userEmail) {
	    User user = userRepository.findOne(userEmail);
	    if(user == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok().body(user);
	}
	
	// Check Login info
	@GetMapping("/user/{email}/{password}")
	public boolean checkLogin(@PathVariable(value = "email") String userEmail,
			                  @PathVariable(value = "password") String userPassword) {
	    User user = userRepository.findOne(userEmail);
	    if(user == null) {
	        return false;
	    }
	    
	    if(!user.getPassword().equals(userPassword))
	    	return false;
	    
	    return true;
	}

    // Update a User
	@PutMapping("/user/{email}")
	public ResponseEntity<User> updatePlayer(@PathVariable(value = "email") String userEamil,                                     
	        @RequestParam(value="name", required=true) String name,
	        @RequestParam(value="email", required=true) String email,
	        @RequestParam(value="password", required=true) String password,
	        @RequestParam(value="street", required=false) String street,
	        @RequestParam(value="city", required=false) String city,
	        @RequestParam(value="state", required=false) String state,
	        @RequestParam(value="zip", required=false) String zip)
	{
	    User user = userRepository.findOne(userEamil);
	    if(user == null) {
	        return ResponseEntity.notFound().build();
	    }
	    
	    // check duplicate email for updated user
	 	List<User> userList = userRepository.findAll();
	 	for(int i = 0; i < userList.size(); i++){
	 		User temp = userList.get(i);
	 		if(temp.getEmail().equals(email) && !user.getEmail().equals(email))
	 			return ResponseEntity.badRequest().build();
	 	}
	    
//	    if(sponsor_id != null){                                            // check params have sponsor_id
//	    	//System.out.println(sponsor_id);
//	    	
//	        Sponsor sponsor = sponsorRepository.findOne(sponsor_id);       // check if sponsor exist
//		    if(sponsor != null){
//			    player.setSponsor(sponsor);
//			    //System.out.println("!!!!!!!!!!!!");
//		    }
//		    else
//			    return ResponseEntity.badRequest().build();
//	    }
//	    else
//	    	player.setSponsor(null);
	    
	    Address address = new Address();
		address.setCity(city);
		address.setState(state);
		address.setStreet(street);
		address.setZip(zip);
		
	    user.setName(name);
		user.setEmail(email);
		user.setPassword(password);	
		user.setAddress(address);
		
		
	    
	    User updatedUser = userRepository.save(user);          // save updated user
	    return ResponseEntity.ok(updatedUser);
	}

    // Delete a User
	@DeleteMapping("/user/{email}")
    public ResponseEntity<User> deleteUser(@PathVariable(value = "email") String userEmail) {
        User user = userRepository.findOne(userEmail);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }
        
//        List<Player> opponentList1 = player.getOpponents();
//        if(!opponentList1.isEmpty()){                                 // remove any reference of this player
//        	for(int i = 0; i < opponentList1.size(); i++){
//        		Player temp = playerRepository.findOne(opponentList1.get(i).getId());
//        		List<Player> opponentList2 = temp.getOpponents();
//        		opponentList2.remove(player);
//        		temp.setOpponents(opponentList2);
//        		playerRepository.save(temp);
//        	}
//        }
//        
//        player.setOpponents(new ArrayList<Player>());

        userRepository.delete(user);
        return ResponseEntity.ok().body(user);
    }

}

