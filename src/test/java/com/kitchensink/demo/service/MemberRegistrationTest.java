package com.kitchensink.demo.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.kitchensink.demo.config.MemberRegisteredEvent;
import com.kitchensink.demo.model.Member;

import jakarta.persistence.EntityManager;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MemberRegistrationTest {

    @InjectMocks
    private MemberRegistration memberRegistration;

    @Mock
    private EntityManager entityManager;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private Logger logger;

    @BeforeEach
    void setUp() {
        // Initializes the mocks and injects them into the @InjectMocks class
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Transactional
    void testRegister_Success() throws Exception {
        // Given
        Member member = new Member();
        member.setName("John Doe");

        // When
        memberRegistration.register(member);

        // Then
        // Verify that the entity manager persisted the member object
        verify(entityManager).persist(member);
        // Verify that an event was published
        verify(eventPublisher).publishEvent(any(MemberRegisteredEvent.class));
    }

    @Test
    @Transactional
    void testRegister_Failure() throws Exception {
        // Given
        Member member = new Member();
        member.setName("Jane Doe");

        // Simulate failure in EntityManager persist
        doThrow(new RuntimeException("Persistence failed")).when(entityManager).persist(member);

        // When / Then
        try {
            memberRegistration.register(member);
        } catch (Exception e) {
            // Verify that persist was called and threw the exception
            verify(entityManager).persist(member);  // Expect this to throw
            // Ensure event was not published because of the failure
            verifyNoInteractions(eventPublisher);
        }
    }
}
