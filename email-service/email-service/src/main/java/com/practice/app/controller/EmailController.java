package com.practice.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.practice.app.request.EmailRequest;
import com.practice.app.request.UserRequest;
import com.practice.app.response.EmailResponse;
import com.practice.app.service.EmailService;

@RestController
public class EmailController {

	private final EmailService emailService;

	public EmailController(EmailService emailService) {
		super();
		this.emailService = emailService;
	}

	@PostMapping("/sendMail")
	public EmailResponse sendMail(@RequestBody EmailRequest emailRequest) {
		emailService.validateForSingleMail(emailRequest);
		return emailService.sendMail(emailRequest);
	}
	
	@GetMapping("/getMailDetails/{id}")
	public EmailResponse getMailDetails(@PathVariable Integer id) {
		return emailService.getMailDetails(id);
	}
	
	@PostMapping("/login")
	public String login(@RequestBody UserRequest userRequest) {
		return emailService.login(userRequest);
	}
}
