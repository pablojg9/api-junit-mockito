package com.gntech.api.resources.exceptions;

import com.gntech.api.services.exceptions.DataIntegrityViolationException;
import com.gntech.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest
class ResourceExceptionHandlerTest {

    @InjectMocks
    private ResourceExceptionHandler exceptionHandler;

    private final static String MESSAGE_NOT_FOUND = "Object not found";

    private final static String MESSAGE_INTEGRITY_VIOLATION = "email already in database";

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    @DisplayName("when object not found exception then return a response entity")
    void whenObjectNotFoundExceptionThenReturnAResponseEntity() {
        ResponseEntity<StandardError> response = exceptionHandler
                .objectNotFoundException(new ObjectNotFoundException(MESSAGE_NOT_FOUND), new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(MESSAGE_NOT_FOUND, response.getBody().getMessage());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(404, response.getBody().getStatus());
        assertNotEquals("/user/2", response.getBody().getPath());
        assertNotEquals(LocalDateTime.now(), response.getBody().getTimestamp());
    }

    @Test
    @DisplayName("when data integrity violation exception handler exception then return a response entity")
    void whenDataIntegrityViolationExceptionHandlerExceptionThenReturnAResponseEntity() {
        ResponseEntity<StandardError> response = exceptionHandler
                .dataIntegrityViolationExceptionHandler(new DataIntegrityViolationException(MESSAGE_INTEGRITY_VIOLATION), new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(MESSAGE_INTEGRITY_VIOLATION, response.getBody().getMessage());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(400, response.getBody().getStatus());
        assertEquals(ResponseEntity.class, response.getClass());
    }
}