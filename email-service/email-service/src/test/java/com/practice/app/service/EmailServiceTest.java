package com.practice.app.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.practice.app.client.EmployeeDetartmentClient;
import com.practice.app.constants.AppConstants;
import com.practice.app.entity.EmailEntity;
import com.practice.app.exceptionhandler.InvalidRequestException;
import com.practice.app.exceptionhandler.ResourceNotFoundException;
import com.practice.app.repository.EmailRepository;
import com.practice.app.request.EmailRequest;
import com.practice.app.request.UserRequest;
import com.practice.app.response.EmailResponse;
import com.practice.app.response.EmployeeResponse;

class EmailServiceTest {

    @Mock
    private EmployeeDetartmentClient client;

    @Mock
    private EmailRepository repository;

    @InjectMocks
    private EmailService emailService;

    private EmailRequest validEmailRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        validEmailRequest = new EmailRequest(1, 101, "Subject", "Body");
        validEmailRequest.setToken("valid-token");
    }

    // ---------- sendMail tests ----------
    @Test
    void testSendMail_ValidEmployee_ShouldReturnSuccess() {
        // return a proper EmployeeResponse (no no-arg constructor available)
        EmployeeResponse empResp = new EmployeeResponse(1, "John", "Regular", "IT", "john@innodeed.com");
        when(client.getEmployee(anyString(), eq(1))).thenReturn(empResp);

        // repository.save returns the saved entity - we can just return the argument
        when(repository.save(any(EmailEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        EmailResponse response = emailService.sendMail(validEmailRequest);

        assertEquals(AppConstants.SUCCESS.getValue(), response.getStatus());
        assertEquals(validEmailRequest.getEmployeeId(), response.getEmployeeId());
        verify(repository, times(1)).save(any(EmailEntity.class));
    }

    @Test
    void testSendMail_InvalidEmployee_ShouldReturnFailed() {
        when(client.getEmployee(anyString(), eq(1))).thenReturn(null);
        when(repository.save(any(EmailEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        EmailResponse response = emailService.sendMail(validEmailRequest);

        assertEquals(AppConstants.FAILED.getValue(), response.getStatus());
        verify(repository, times(1)).save(any(EmailEntity.class));
    }

    // ---------- getMailDetails tests ----------
    @Test
    void testGetMailDetails_Found_ShouldReturnEmailResponse() {
        EmailEntity entity = new EmailEntity(validEmailRequest, "SUCCESS", LocalDateTime.now());
        when(repository.findById(1)).thenReturn(Optional.of(entity));

        EmailResponse response = emailService.getMailDetails(1);

        assertNotNull(response);
        assertEquals(validEmailRequest.getEmployeeId(), response.getEmployeeId());
    }

    @Test
    void testGetMailDetails_NotFound_ShouldThrowException() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> emailService.getMailDetails(99));
    }

    // ---------- login (Feign) tests ----------
    @Test
    void testLogin_Success() {
        // Build UserRequest via setters (no 2-arg ctor available)
        UserRequest req = new UserRequest();
        req.setUsername("user");
        req.setPassword("pass");
        req.setRole("EMPLOYEE");

        when(client.login(any(UserRequest.class))).thenReturn("TOKEN123");

        String response = emailService.login(req);

        assertEquals("TOKEN123", response);
    }

    @Test
    void testLogin_FallbackDirectInvocation() {
        // In a plain unit test the Resilience4j annotation is not active,
        // so we assert the fallback method directly.
        UserRequest req = new UserRequest();
        req.setUsername("user");
        req.setPassword("pass");
        req.setRole("EMPLOYEE");

        String fallback = emailService.sendMailFallback(req, new RuntimeException("service down"));

        assertEquals("Login service unavailable. Please try later.", fallback);
    }

    // ---------- validateForSingleMail ----------
    @Test
    void testValidateForSingleMail_ValidRequest() {
        assertDoesNotThrow(() -> emailService.validateForSingleMail(validEmailRequest));
    }

    @Test
    void testValidateForSingleMail_InvalidEmployeeId_ShouldThrow() {
        EmailRequest request = new EmailRequest(0, 101, "Subject", "Body");
        request.setToken("token");

        assertThrows(InvalidRequestException.class, () -> emailService.validateForSingleMail(request));
    }

    @Test
    void testValidateForSingleMail_MissingSubject_ShouldThrow() {
        EmailRequest request = new EmailRequest(1, 101, null, "Body");
        request.setToken("token");

        assertThrows(InvalidRequestException.class, () -> emailService.validateForSingleMail(request));
    }

    @Test
    void testValidateForSingleMail_MissingBody_ShouldThrow() {
        EmailRequest request = new EmailRequest(1, 101, "Subject", " ");
        request.setToken("token");

        assertThrows(InvalidRequestException.class, () -> emailService.validateForSingleMail(request));
    }

    @Test
    void testValidateForSingleMail_MissingToken_ShouldThrow() {
        EmailRequest request = new EmailRequest(1, 101, "Subject", "Body");
        // token not set
        assertThrows(InvalidRequestException.class, () -> emailService.validateForSingleMail(request));
    }
}
