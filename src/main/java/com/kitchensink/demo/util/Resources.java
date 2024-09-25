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
package com.kitchensink.demo.util;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
//import jakarta.enterprise.inject.Produces;
//import jakarta.enterprise.inject.spi.InjectionPoint;


/**
 * This class uses CDI to alias Jakarta EE resources, such as the persistence context, to CDI beans
 *
 * <p>
 * Example injection on a managed bean field:
 * </p>
 *
 * <pre>
 * &#064;Inject
 * private EntityManager em;
 * </pre>
 */

//@Component
@Configuration
public class Resources {
    //@Produces
    @PersistenceContext
    private EntityManager em;

    /*
     * 
    @Produces
    public Logger produceLog(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }
    */
    
    @Bean
    Logger produceLog(InjectionPoint injectionPoint) {
        // Using SLF4J LoggerFactory to create a logger for the declaring class
        return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass());
    }

}
