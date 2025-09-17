package com.practice.app.dummy.service;

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
import com.practice.app.service.EmailService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailServiceTest {

    @Mock
    private EmployeeDetartmentClient client;

    @Mock
    private EmailRepository repository;

    @InjectMocks
    private EmailService emailService;

    private EmailRequest emailRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        emailRequest = new EmailRequest(1, 101, "Hello", "This is body");
        emailRequest.setToken("validToken");
    }

    // ------------------- sendMail -------------------
    @Test
    void testSendMail_Success() {
        // mock employee exists
        when(client.getEmployee("Bearer validToken", 1))
                .thenReturn(new EmployeeResponse(1, "John", "FullTime", "IT", "john@example.com"));

        EmailResponse response = emailService.sendMail(emailRequest);

        assertEquals(AppConstants.SUCCESS.getValue(), response.getStatus());
        assertNotNull(response.getTime());

        // verify repository save
        verify(repository, times(1)).save(any(EmailEntity.class));
    }

    @Test
    void testSendMail_Failure() {
        // mock employee not found
        when(client.getEmployee("Bearer validToken", 1)).thenReturn(null);

        EmailResponse response = emailService.sendMail(emailRequest);

        assertEquals(AppConstants.FAILED.getValue(), response.getStatus());
        assertNotNull(response.getTime());

        verify(repository, times(1)).save(any(EmailEntity.class));
    }

    // ------------------- getMailDetails -------------------
    @Test
    void testGetMailDetails_Found() {
        EmailEntity entity = new EmailEntity(emailRequest, AppConstants.SUCCESS.getValue(), LocalDateTime.now());
        entity.setId(1);

        when(repository.findById(1)).thenReturn(Optional.of(entity));

        EmailResponse response = emailService.getMailDetails(1);

        assertEquals(emailRequest.getEmployeeId(), response.getEmployeeId());
        assertEquals(emailRequest.getSubject(), response.getSubject());
        assertEquals(emailRequest.getBody(), response.getBody());
    }

    @Test
    void testGetMailDetails_NotFound() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> emailService.getMailDetails(99));
    }

    // ------------------- login -------------------
    @Test
    void testLogin() {
        UserRequest userRequest = new UserRequest();
        when(client.login(userRequest)).thenReturn("jwt-token");

        String result = emailService.login(userRequest);

        assertEquals("jwt-token", result);
        verify(client, times(1)).login(userRequest);
    }

    // ------------------- validateForSingleMail -------------------
    @Test
    void testValidateForSingleMail_Valid() {
        // should not throw exception
        assertDoesNotThrow(() -> emailService.validateForSingleMail(emailRequest));
    }

    @Test
    void testValidateForSingleMail_InvalidEmployeeId() {
        emailRequest.setEmployeeId(0);
        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> emailService.validateForSingleMail(emailRequest));
        assertEquals("Invalid/Empty Employee ID", ex.getMessage());
    }

    @Test
    void testValidateForSingleMail_EmptySubject() {
        emailRequest.setSubject("");
        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> emailService.validateForSingleMail(emailRequest));
        assertEquals("Email Subject is required", ex.getMessage());
    }

    @Test
    void testValidateForSingleMail_EmptyBody() {
        emailRequest.setBody(" ");
        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> emailService.validateForSingleMail(emailRequest));
        assertEquals("Email Body is required", ex.getMessage());
    }

    @Test
    void testValidateForSingleMail_EmptyToken() {
        emailRequest.setToken(null);
        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> emailService.validateForSingleMail(emailRequest));
        assertEquals("Provide a Valid Token", ex.getMessage());
    }
}
