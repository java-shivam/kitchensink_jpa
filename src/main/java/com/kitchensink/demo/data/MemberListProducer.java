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
package com.kitchensink.demo.data;

//import jakarta.enterprise.context.RequestScoped;
//import jakarta.enterprise.event.Observes;
//import jakarta.enterprise.event.Reception;
//import jakarta.enterprise.inject.Produces;
//import jakarta.inject.Inject;
//import jakarta.inject.Named;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.kitchensink.demo.model.Member;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;

//@RequestScoped
@Component
@RequestScope
@Named("memberListProducer") 
public class MemberListProducer {

    //@Inject
    @Autowired
    private MemberRepository memberRepository;

    private List<Member> members;

    // @Named provides access the return value via the EL variable name "members" in the UI (e.g.
    // Facelets or JSP view)
//    @Produces
//    @Named
//    public List<Member> getMembers() {
//        return members;	
//    }
    
    
    public void setMembers(List<Member> members) {
		this.members = members;
	}

	public List<Member> getMembers() {
        return members;
    }

//    public void onMemberListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Member member) {
//        retrieveAllMembersOrderedByName();
//    }
    // Instead of @Observes, you may want to implement an event listener or a mechanism
    // to handle updates. Here's a placeholder method:
    public void onMemberListChanged(Member member) {
        retrieveAllMembersOrderedByName();
    }

    @PostConstruct
    public void retrieveAllMembersOrderedByName() {
        members = memberRepository.findAllOrderedByName();
    }
}
