package com.practice.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
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
@RefreshScope
public class EmailController {
	
	private Logger logger = LoggerFactory.getLogger(EmailController.class);

	private final EmailService emailService;

	public EmailController(EmailService emailService) {
		super();
		this.emailService = emailService;
	}

	@PostMapping("/sendMail")
	public EmailResponse sendMail(@RequestBody EmailRequest emailRequest) {
		logger.info("in class {}, Send mail to Employee with ID: {}", EmailController.class.getName(), emailRequest.getEmployeeId());
		emailService.validateForSingleMail(emailRequest);
		return emailService.sendMail(emailRequest);
	}
	
	@GetMapping("/getMailDetails/{id}")
	public EmailResponse getMailDetails(@PathVariable Integer id) {
		logger.info("Get mail info through mail id {}", id);
		return emailService.getMailDetails(id);
	}
	
	@PostMapping("/login")
	public String login(@RequestBody UserRequest userRequest) {
		logger.info("Login request through email - service for user: {}", userRequest.getUsername());
		return emailService.login(userRequest);
	}
}
