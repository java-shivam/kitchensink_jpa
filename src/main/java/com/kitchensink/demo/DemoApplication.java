package com.kitchensink.demo;


//import org.jboss.weld.module.web.el.WeldELContextListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.kitchensink.demo")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
//	 @Bean
//	 ServletRegistrationBean<jakarta.faces.webapp.FacesServlet> facesServletRegistration() {
//	        ServletRegistrationBean<jakarta.faces.webapp.FacesServlet> registration = new ServletRegistrationBean<>(new jakarta.faces.webapp.FacesServlet(), "*.xhtml");
//	        registration.setName("FacesServlet");
//	        registration.setLoadOnStartup(1);
//	        return registration;
//	 }
	 
//	 @Bean
//	 public ServletContextListener weldListener() {
//	     return new WeldContextListener();
//	 }

}
