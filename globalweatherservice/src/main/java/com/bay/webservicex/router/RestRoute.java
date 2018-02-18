package com.bay.webservicex.router;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Class RestRoute A Camel route that calls the REST service
 * 
 * @author Dinesh Metkari
 * @version v0.1
 * @since 2018-02-18
 * 
 */
@Component
public class RestRoute extends RouteBuilder {

	@Value("${server.port}")
	private int port;
	
	@Value("${default.country.name}")
	private String countryName;
	
    @Override
    public void configure() throws Exception {
        // call the embedded rest service 
        restConfiguration().host("localhost").port(port);

		
        from("timer:hello?period={{timer.period}}")
            .setHeader("name", simple(countryName))
            .to("rest:get:v1/globalweather/GetCitiesByCountry/{name}")
            .log("${body}");
			
    
			
		  from("direct:start")
        .setHeader(Exchange.HTTP_METHOD, constant("POST"))
        .to("rest:post:v1/globalweather/GetCitiesByCountry");
		

    }

}
