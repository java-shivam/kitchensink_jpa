//package com.kitchensink.demo.config;
//
//import javax.faces.webapp.FacesServlet;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//
//@Configuration
//@EnableWebMvc
//public class JsfConfig {
//
//    @Bean
//    ServletRegistrationBean<FacesServlet> facesServletRegistration() {
//        ServletRegistrationBean<FacesServlet> registration = new ServletRegistrationBean<>(new FacesServlet(), "*.xhtml");
//        registration.setLoadOnStartup(1);
//        return registration;
//    }
//    
//}
