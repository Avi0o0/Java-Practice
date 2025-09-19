package com.practice.app.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.practice.app.client.EmployeeDetartmentClient;
import com.practice.app.constants.AppConstants;
import com.practice.app.entity.EmailEntity;
import com.practice.app.exceptionhandler.InvalidRequestException;
import com.practice.app.repository.EmailRepository;
import com.practice.app.request.EmailRequest;
import com.practice.app.request.UserRequest;
import com.practice.app.response.EmailResponse;
import com.practice.app.response.EmployeeResponse;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class EmailService {
	
	private static final String FALL_BACK_MSG = "Login service unavailable. Please try later.";
	private final EmployeeDetartmentClient client;
	private final EmailRepository repository;
	
	public EmailService(EmployeeDetartmentClient employeeDetartmentClient, EmailRepository repository) {
		this.client = employeeDetartmentClient;
		this.repository = repository;
	}

	public EmailResponse sendMail(EmailRequest emailRequest) {
		if(validateEmployee(emailRequest.getToken(), emailRequest.getEmployeeId())) {
			String status = AppConstants.SUCCESS.getValue();
			saveNotification(emailRequest, status);
			LocalDateTime time = LocalDateTime.now();
			return new EmailResponse(emailRequest, status, time);
		} else {
			String status = AppConstants.FAILED.getValue();
			saveNotification(emailRequest, status);
			LocalDateTime time = LocalDateTime.now();
			return new EmailResponse(emailRequest, status, time);
		}
	}

	public EmailResponse getMailDetails(Integer id) {
		Optional<EmailEntity> emailDetails = repository.findById(id);
		return emailDetails
		.map(email -> new EmailResponse(email.getEmployeeId(), email.getSubject(), email.getBody(), email.getStatus(), email.getTime()))
		.orElseThrow(() -> new com.practice.app.exceptionhandler.ResourceNotFoundException("Email Details for ID: " + id + " not found"));
	}

	private boolean validateEmployee(String token, Integer empId) {
		String bearerToken = "Bearer " + token;
	    EmployeeResponse employeeResponse = client.getEmployee(bearerToken, empId);
	    return employeeResponse != null;
	}
	
	private void saveNotification(EmailRequest emailRequest, String status) {
		LocalDateTime time = LocalDateTime.now();
        EmailEntity email = new EmailEntity(emailRequest, status, time);
        repository.save(email);
    }

	@CircuitBreaker(name = "mailService", fallbackMethod = "sendMailFallback")
	public String login(UserRequest userRequest) {
		return client.login(userRequest);
	}
	
	public void validateForSingleMail(EmailRequest emailRequest) throws InvalidRequestException {
	    if (emailRequest.getEmployeeId() == null || emailRequest.getEmployeeId() == 0) {
	        throw new InvalidRequestException("Invalid/Empty Employee ID");
	    }
	    if (emailRequest.getSubject() == null || emailRequest.getSubject().trim().isEmpty()) {
	        throw new InvalidRequestException("Email Subject is required");
	    }
	    if (emailRequest.getBody() == null || emailRequest.getBody().trim().isEmpty()) {
	        throw new InvalidRequestException("Email Body is required");
	    }
	    if (emailRequest.getToken() == null || emailRequest.getToken().trim().isEmpty()) {
	        throw new InvalidRequestException("Provide a Valid Token");
	    }
	}
	
	// Fallback for login
	public String sendMailFallback(UserRequest userRequest, Throwable ex) {
	    return FALL_BACK_MSG;
	}

}
