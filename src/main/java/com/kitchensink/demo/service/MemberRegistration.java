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
package com.kitchensink.demo.service;

import org.slf4j.Logger;
//import org.jboss.as.quickstarts.kitchensink.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kitchensink.demo.config.MemberRegisteredEvent;
import com.kitchensink.demo.model.Member;

//import jakarta.ejb.Stateless;
//import jakarta.enterprise.event.Event;
//import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

// The @Stateless annotation eliminates the need for manual transaction demarcation
//@Stateless
@Service
public class MemberRegistration {

    //@Inject
   // private Logger log;
    private final Logger log;

    public MemberRegistration(Logger log) {
        this.log = log;
    }

    //@Inject
    @Autowired
    private EntityManager em;

    //@Inject
    @Autowired
    //private Event<Member> memberEventSrc;
    private ApplicationEventPublisher eventPublisher; 

    @Transactional
    public void register(Member member) throws Exception {
        log.info("Registering " + member.getName());
        em.persist(member);
        //memberEventSrc.fire(member);
        eventPublisher.publishEvent(new MemberRegisteredEvent(this, member));
    }
}
