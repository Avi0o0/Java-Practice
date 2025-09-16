package com.practice.app.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.practice.app.client.EmployeeDetartmentClient;
import com.practice.app.entity.EmailEntity;
import com.practice.app.repository.EmailRepository;
import com.practice.app.request.EmailRequest;
import com.practice.app.response.EmailResponse;

@Service
public class EmailService {
	
	private final EmployeeDetartmentClient client;
	private final EmailRepository repository;
	
	public EmailService(EmployeeDetartmentClient employeeDetartmentClient, EmailRepository repository) {
		this.client = employeeDetartmentClient;
		this.repository = repository;
	}

	public EmailResponse sendMail(EmailRequest emailRequest) {
		validateEmployee(emailRequest.getEmployeeId());
		saveNotification(emailRequest);
		String status = "SUCCESS";
		LocalDateTime sentAt = LocalDateTime.now();
		return new EmailResponse(emailRequest, status, sentAt);
	}

	public EmailResponse getMailDetails(Integer id) {
		return null;
	}

	public List<EmailResponse> sendMailToAll(EmailRequest emailRequest) {
		return null;
	}

	public String sendMailByDeptId(Integer deptId) {
		return null;
	}

	private void validateEmployee(Integer empId) {
		client.getEmployee(empId);
	}
	
	private void saveNotification(EmailRequest emailRequest) {
		String status = "SUCCESS";
		LocalDateTime sentAt = LocalDateTime.now();
        EmailEntity email = new EmailEntity(emailRequest, status, sentAt);
        repository.save(email);
    }
}
