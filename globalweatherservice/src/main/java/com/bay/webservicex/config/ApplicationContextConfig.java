package com.bay.webservicex.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.bay.webservicex.aspect.LoggingAspect;



/*This class will contain the application-context for the application. 
 * Define the following annotations:
 * @Configuration - Annotating a class with the @Configuration indicates that the 
 *                  class can be used by the Spring IoC container as a source of 
 *                  bean definitions
 * @ComponentScan - this annotation is used to search for the Spring components amongst the application
 * @EnableWebMvc - Adding this annotation to an @Configuration class imports the Spring MVC 
 * 				   configuration from WebMvcConfigurationSupport 
 * @EnableTransactionManagement - Enables Spring's annotation-driven transaction management capability.
 *
 * */
@Configuration
@EnableTransactionManagement
//@EnableWebMvc
@ComponentScan(basePackages = "com.bay.webservicex")
@EnableAspectJAutoProxy
public class ApplicationContextConfig {

	
	@Autowired
	@Bean(name = "logAspect")
	public LoggingAspect loggingAspect() {
		return new LoggingAspect();
	}

}
