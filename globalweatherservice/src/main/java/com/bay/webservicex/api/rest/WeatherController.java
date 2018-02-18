
package com.bay.webservicex.api.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;

import com.bay.webservicex.pojo.Country;
import com.bay.webservicex.utility.parser.Parser;
import com.bay.webservicex.utility.parser.helpers.DomHelper;
import com.google.gson.Gson;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * @Class WeatherController This is controller class for handling all the API
 *        requests.
 * 
 * @author Dinesh Metkari
 * @version v0.1
 * @since 2018-02-18
 * 
 */
@RestController
@RequestMapping(value = "/v1/globalweather")
@Api(value = "Weather", description = "Weather API")
public class WeatherController {

	@Value("${service.domain.name}")
	private String domainName;

	private static final Parser.WhitespaceBehaviour COMPACT_WHITE_SPACE = Parser.WhitespaceBehaviour.Preserve;
	
	@RequestMapping(value = "/GetCitiesByCountry", method = RequestMethod.POST)
	 @ApiOperation(value = "Get Cities By Country using POST.", notes = "Returns the resposne in json format")
	public ResponseEntity<String> citiesByCountry(@RequestBody Country country, HttpServletRequest request)
			throws Exception {

		String URLString = generateServiceOperationName(country.getName(), request);
		StringBuilder finalOutput = new StringBuilder();

		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(URLString);
			HttpGet get = new HttpGet(URLString);

			HttpResponse response;
			response = client.execute(get);

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				System.out.println(line);
				finalOutput.append(line);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	
		return new ResponseEntity<String>(convertXMLtoJSON(finalOutput.toString()), HttpStatus.OK);
	}

	@GetMapping(value = "/GetCitiesByCountry/{name}")
	 @ApiOperation(value = "Get Cities By Country using GET.", notes = "Returns the resposne in json format")
	public Map<String, String> getCitiesByCountry(@PathVariable("name") String name) {

	
		String URLString = domainName + "/GetCitiesByCountry?CountryName=" + name;
		StringBuilder finalOutput = new StringBuilder();

		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(URLString);
			HttpGet get = new HttpGet(URLString);

			HttpResponse response;
			response = client.execute(get);

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				System.out.println(line);
				finalOutput.append(line);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return Collections.singletonMap("name", convertXMLtoJSON(finalOutput.toString()));
	}

	/**
	 * Generate Service Operation
	 * 
	 * @param countryName
	 * @param request
	 * @return
	 */
	private String generateServiceOperationName(String countryName, HttpServletRequest request) {
		String requestUI = request.getRequestURI();
		String operationName = requestUI.substring(requestUI.lastIndexOf("/"), requestUI.length());
		return domainName + operationName + "?CountryName=" + countryName;

	}

	
/**
 * Converts XML to JSON
 * @param xml
 * @return
 */
	private String convertXMLtoJSON(String xml) {

		// Parse XML to DOM
		Document doc;
		try {
			doc = DomHelper.parse(xml);

			// Convert DOM to terse representation, and convert to JSON
			Parser.Options opts = Parser.Options.with(COMPACT_WHITE_SPACE).and(Parser.ErrorBehaviour.ThrowAllErrors);

			Object terseDoc = new Parser(opts).convert(doc);
			String json = new Gson().toJson(terseDoc);

			System.out.println(json);
			return json;
		} catch (Exception e) {
			//TODO exception flow
			e.printStackTrace();
			return e.getMessage();
		}

	}

}
