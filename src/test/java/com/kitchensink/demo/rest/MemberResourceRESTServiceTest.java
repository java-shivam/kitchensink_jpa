package com.kitchensink.demo.rest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.kitchensink.demo.data.MemberRepository;
import com.kitchensink.demo.model.Member;
import com.kitchensink.demo.service.MemberRegistration;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MemberResourceRESTServiceTest {

    @Mock
    private Logger log;

    @Mock
    private Validator validator;

    @Mock
    private MemberRepository repository;

    @Mock
    private MemberRegistration registration;

    @InjectMocks
    private MemberResourceRESTService memberResource;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    public void testListAllMembers() {
        // Arrange
        Member member1 = new Member();
        Member member2 = new Member();
        List<Member> members = Arrays.asList(member1, member2);
        when(repository.findAllOrderedByName()).thenReturn(members);

        // Act
        List<Member> result = memberResource.listAllMembers();

        // Assert
        assertEquals(2, result.size());
        verify(repository).findAllOrderedByName();
    }

    
    @Test
    public void testCreateMember_Success() throws Exception {
        // Arrange
        Member member = new Member();
        member.setName("John Doe"); // Set a valid name
        member.setEmail("john.doe@example.com"); // Set a valid email
        member.setPhoneNumber("1234567890"); // Set a valid phone number (10 digits)

        // Mock the registration service and validation
        doNothing().when(registration).register(member);
        when(validator.validate(member)).thenReturn(new HashSet<>()); // Ensure validation passes

        // Act
        ResponseEntity<?> response = memberResource.createMember(member);

        // Debugging output
        System.out.println("Response Status: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(member, response.getBody());
        verify(registration).register(member);
    }

    //}





    @Test
    public void testCreateMember_UniqueEmailViolation() {
        // Arrange
        Member member = new Member();
        member.setEmail("john.doe@example.com");

        // Mock validation exception
        when(validator.validate(member)).thenThrow(new ValidationException("Unique Email Violation"));

        // Act
        ResponseEntity<?> response = memberResource.createMember(member);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testCreateMember_GeneralException() throws Exception {
        // Arrange
        Member member = new Member();
        member.setEmail("john.doe@example.com");

        // Mock validation to pass
        when(validator.validate(member)).thenReturn(new HashSet<>());

        // Mock a general exception during registration
        doThrow(new RuntimeException("Some error occurred")).when(registration).register(member);

        // Act
        ResponseEntity<?> response = memberResource.createMember(member);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    // @Test
    public void testCreateMember_ValidationError() throws Exception {
        // Arrange
        Member member = new Member();
        member.setEmail("john.doe@example.com");

        // Mock validation exception
        Set<ConstraintViolation<Member>> violations = new HashSet<>();
        when(validator.validate(member)).thenThrow(new ConstraintViolationException(violations));

        // Act
        ResponseEntity<?> response = memberResource.createMember(member);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(registration, never()).register(any(Member.class));
    }
}
