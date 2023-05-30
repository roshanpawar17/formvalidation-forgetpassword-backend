package com.reactspringboot.controller;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.Repositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reactspringboot.dao.UserDataRepository;
import com.reactspringboot.entities.EmailRequest;
import com.reactspringboot.entities.Otp;
import com.reactspringboot.entities.User;
import com.reactspringboot.service.MyService;
import com.reactspringboot.service.SendEmail;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class MyController {
	@Autowired
	private MyService myService;
	@Autowired
	private SendEmail semail;
	@Autowired
	private UserDataRepository userDataRepository;
	
	private int generateOtp;
	private String enterEmail;
	
	@PostMapping("/adduser")
	public ResponseEntity<User> addUsers(@RequestBody User user) {
		User u=null;
		try {
			u=this.myService.addUser(user);
			return ResponseEntity.of(Optional.of(u));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	
	@PostMapping("/forgetPassword")
	public ResponseEntity<?> forgetPassword(@RequestBody EmailRequest emailRequest) {
		Random random = new Random();
        int OTP = random.nextInt(900000) + 100000;
        
        String msg="OTP is"+OTP;
     
        enterEmail=emailRequest.getEmailTo();
        generateOtp=OTP;
		
        //send email
        boolean s=semail.sendMail(emailRequest.getEmailTo(),msg);
		if(s) {
			return ResponseEntity.ok("OTP sent on your email ");
		}else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong..!");
		}
		
		
	}
	
	@PostMapping("/verifyotp")
	public ResponseEntity<?> verifyOtp(@RequestBody Otp votp) {
		int enterOtp=votp.getVotp();
		int oldotp=generateOtp;
		System.out.println("enter otp "+enterOtp);
		System.out.println("generated otp "+oldotp);
		System.out.println("enter email "+enterEmail);
		String email=enterEmail;
		if(enterOtp == oldotp) {
			User user=this.userDataRepository.findByEmail(email);
			if(user == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
			}else {
				return ResponseEntity.status(HttpStatus.OK).build();
			}
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP"); 
		}
		
		
		
	}

	
	
}
