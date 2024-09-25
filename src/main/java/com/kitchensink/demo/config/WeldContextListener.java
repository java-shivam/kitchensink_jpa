//package com.kitchensink.demo.config;
//import jakarta.servlet.ServletContext;
//import jakarta.servlet.ServletContextEvent;
//import jakarta.servlet.ServletContextListener;
//import jakarta.servlet.annotation.WebListener;
//
//@WebListener
//public class WeldContextListener implements ServletContextListener {
//    @Override
//    public void contextInitialized(ServletContextEvent sce) {
//        ServletContext servletContext = sce.getServletContext(); // Initialize Weld listener
//        org.jboss.weld.environment.servlet.Listener listener = new org.jboss.weld.environment.servlet.Listener();
//        listener.contextInitialized(sce);
//    }
//
//    @Override
//    public void contextDestroyed(ServletContextEvent sce) {
//    }
//}
