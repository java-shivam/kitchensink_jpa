package com.kitchensink.demo.controller;

import com.kitchensink.demo.model.Member;
import com.kitchensink.demo.service.MemberRegistration;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MemberControllerTest {

    @InjectMocks
    private MemberController memberController;

    @Mock
    private FacesContext facesContext;

    @Mock
    private MemberRegistration memberRegistration;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        memberController.initNewMember(); // Initialize the newMember
    }

    @Test
    void testRegister_Success() throws Exception {
        // Given
        Member newMember = new Member();
        memberController.setNewMember(newMember);

        // When
        memberController.register();

        // Then
        verify(memberRegistration).register(newMember);
        verify(facesContext).addMessage(eq(null), any(FacesMessage.class));
    }

    @Test
    void testRegister_Failure() throws Exception {
        // Given
        Member newMember = new Member();
        memberController.setNewMember(newMember);

        doThrow(new Exception("Registration failed")).when(memberRegistration).register(newMember);

        // When
        memberController.register();


        // Then
        verify(memberRegistration).register(newMember);
        verify(facesContext).addMessage(eq(null), any(FacesMessage.class));
    }

    @Test
    void testGetNewMember() {
        // When
        Member newMember = memberController.getNewMember();

        // Then
        assertNotNull(newMember);
    }
}
