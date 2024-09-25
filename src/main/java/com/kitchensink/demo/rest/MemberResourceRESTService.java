/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kitchensink.demo.rest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kitchensink.demo.data.MemberRepository;
import com.kitchensink.demo.model.Member;
import com.kitchensink.demo.service.MemberRegistration;

//import jakarta.enterprise.context.RequestScoped;
//import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.core.Response;
//import jakarta.ws.rs.Consumes;
//import jakarta.ws.rs.GET;
//import jakarta.ws.rs.POST;
//import jakarta.ws.rs.Path;
//import jakarta.ws.rs.PathParam;
//import jakarta.ws.rs.Produces;
//import jakarta.ws.rs.WebApplicationException;
//import jakarta.ws.rs.core.MediaType;
//import jakarta.ws.rs.core.Response;

/**
 * JAX-RS Example
 * <p/>
 * This class produces a RESTful service to read/write the contents of the members table.
 */
//@Path("/members")
//@RequestScoped
@RestController
@RequestMapping("/members")
public class MemberResourceRESTService {

    //@Inject
    //private Logger log;
	
    private final Logger log;

    public MemberResourceRESTService(Logger log) {
        this.log = log;
    }

    //@Inject
    @Autowired
    private Validator validator;

    //@Inject
    @Autowired
    private MemberRepository repository;

    //@Inject
    @Autowired
    MemberRegistration registration;

    //@GET
    @GetMapping(produces = "application/json")
    //@Produces(MediaType.APPLICATION_JSON)
    public List<Member> listAllMembers() {
        return repository.findAllOrderedByName();
    }

//    @GET
//    @Path("/{id:[0-9][0-9]*}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Member lookupMemberById(@PathParam("id") long id) {
//        Member member = repository.findById(id);
//        if (member == null) {
//            throw new WebApplicationException(Response.Status.NOT_FOUND);
//        }
//        return member;
//    }
    
    @GetMapping(value = {"/{id}", "/{id:\\d+}"}, produces = "application/json")
    public ResponseEntity<Member> lookupMemberById(@PathVariable("id") long id) {
        Member member = repository.findById(id);
        if (member == null) {
            // Return a 404 Not Found response
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // Return 200 OK with the member data
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    /**
     * Creates a new member from the values provided. Performs validation, and will return a JAX-RS response with either 200 ok,
     * or with a map of fields, and related errors.
     */
//    @POST
//    //@PostMapping
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response createMember(@RequestBody Member member) {
//
//        Response.ResponseBuilder builder = null;
//
//        try {
//            // Validates member using bean validation
//            validateMember(member);
//
//            registration.register(member);
//
//            // Create an "ok" response
//            builder = Response.ok();
//        } catch (ConstraintViolationException ce) {
//            // Handle bean validation issues
//            builder = createViolationResponse(ce.getConstraintViolations());
//        } catch (ValidationException e) {
//            // Handle the unique constrain violation
//            Map<String, String> responseObj = new HashMap<>();
//            responseObj.put("email", "Email taken");
//            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
//        } catch (Exception e) {
//            // Handle generic exceptions
//            Map<String, String> responseObj = new HashMap<>();
//            responseObj.put("error", e.getMessage());
//            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
//        }
//
//        return builder.build();
//    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createMember(@RequestBody Member member) {
        // Method body
    	//Response.ResponseBuilder builder = null;
    	ResponseEntity<Member> builder = null;
    	
    	        try {
    	            // Validates member using bean validation
    	            validateMember(member);
    	
    	            registration.register(member);
    	
    	            // Create an "ok" response
    	            builder = ResponseEntity.ok(member);//Response.ok();
    	            
    	        } catch (ConstraintViolationException ce) {
    	            // Handle bean validation issues
    	            //builder = createViolationResponse(ce.getConstraintViolations());
    	        	//builder = createViolationResponse(ce.getConstraintViolations());
    	            return ResponseEntity.ok(createViolationResponse(ce.getConstraintViolations()));
    	            
    	        } catch (ValidationException e) {
    	            // Handle the unique constrain violation
    	            Map<String, String> responseObj = new HashMap<>();
    	            responseObj.put("email", "Email taken");
    	            return new ResponseEntity<>(responseObj, HttpStatus.CONFLICT);
    	            //builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
    	           // return ResponseEntity.ok(member);
    	        } catch (Exception e) {
    	            // Handle generic exceptions
    	            Map<String, String> responseObj = new HashMap<>();
    	            responseObj.put("error", e.getMessage());
    	            return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
    	            //builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
    	           // return ResponseEntity.ok(member);
    	        }
				return builder;
    	
    	        //return builder.build();
       // return ResponseEntity.ok(member);
    }
    /**
     * <p>
     * Validates the given Member variable and throws validation exceptions based on the type of error. If the error is standard
     * bean validation errors then it will throw a ConstraintValidationException with the set of the constraints violated.
     * </p>
     * <p>
     * If the error is caused because an existing member with the same email is registered it throws a regular validation
     * exception so that it can be interpreted separately.
     * </p>
     *
     * @param member Member to be validated
     * @throws ConstraintViolationException If Bean Validation errors exist
     * @throws ValidationException If member with the same email already exists
     */
    private void validateMember(Member member) throws ConstraintViolationException, ValidationException {
        // Create a bean validator and check for issues.
        Set<ConstraintViolation<Member>> violations = validator.validate(member);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<>(violations));
        }

        // Check the uniqueness of the email address
        if (emailAlreadyExists(member.getEmail())) {
            throw new ValidationException("Unique Email Violation");
        }
    }

    /**
     * Creates a JAX-RS "Bad Request" response including a map of all violation fields, and their message. This can then be used
     * by clients to show violations.
     *
     * @param violations A set of violations that needs to be reported
     * @return JAX-RS response containing all violations
     */
    private Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {
        //log.fine("Validation completed. violations found: " + violations.size());
    	log.debug("Validation completed. Violations found: {}", violations.size());

        Map<String, String> responseObj = new HashMap<>();

        for (ConstraintViolation<?> violation : violations) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
    }
//    private ResponseEntity<Map<String, String>> createViolationResponse(Set<ConstraintViolation<?>> violations) {
//        log.debug("Validation completed. Violations found: {}", violations.size());
//
//        Map<String, String> responseObj = new HashMap<>();
//
//        for (ConstraintViolation<?> violation : violations) {
//            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
//        }
//
//        // Return a ResponseEntity with BAD_REQUEST status and the map of validation errors
//        return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
//    }
    
    /**
     * Checks if a member with the same email address is already registered. This is the only way to easily capture the
     * "@UniqueConstraint(columnNames = "email")" constraint from the Member class.
     *
     * @param email The email to check
     * @return True if the email already exists, and false otherwise
     */
    public boolean emailAlreadyExists(String email) {
        Member member = null;
        try {
            member = repository.findByEmail(email);
        } catch (NoResultException e) {
        	System.out.println("e"+e);
        	e.printStackTrace();
            // ignore
        } catch (Exception e1) {
        	System.out.println("e1"+e1);
        	e1.printStackTrace();
            // ignore
        }
        return member != null;
    }
}
