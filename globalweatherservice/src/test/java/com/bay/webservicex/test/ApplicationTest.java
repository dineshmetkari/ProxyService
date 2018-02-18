
package com.bay.webservicex.test;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bay.webservicex.Application;

/**
 * @Class ApplicationTest
 * 
 * @author Dinesh Metkari
 * @version v0.1
 * @since 2018-02-18
 * 
 */
@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ApplicationTest {

	@Autowired
	private CamelContext camelContext;

	@Test
	public void shouldProduceMessages() throws Exception {

		// Route as it uses a timer to trigger
		NotifyBuilder notify = new NotifyBuilder(camelContext).whenDone(1).create();

		assertTrue(notify.matches(10, TimeUnit.SECONDS));
	}

}
