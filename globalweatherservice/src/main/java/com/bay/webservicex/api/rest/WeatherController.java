
package com.bay.webservicex.api.rest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.bay.webservicex.pojo.Weather;
import com.bay.webservicex.utility.parser.soaptojson.Parser;
import com.bay.webservicex.utility.parser.soaptojson.helpers.DomHelper;
import com.bay.webservicex.utility.soapmanipulator.SOAPServiceManipulator;
import com.google.gson.Gson;

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
// @Api(value = "Weather", description = "Weather API")
public class WeatherController {

	// Configurable parameters for Operation1
	@Value("${operation1.wsdl.url}")
	private String wsdlURL;

	@Value("${operation1.localpart}")
	private String localPart;

	@Value("${operation1.soapaction}")
	private String soapAction;

	@Value("${operation1.elementName1}")
	private String elementName;

	@Value("${operation1.elementValue1}")
	private String elementValue;

	@Value("${operation1.response.json_1.xml_0}")
	private int responseFormat;

	// Configurable parameters for Operation2
	@Value("${operation2.wsdl.url}")
	private String wsdlURL2;

	@Value("${operation2.localpart}")
	private String localPart2;

	@Value("${operation2.soapaction}")
	private String soapAction2;

	@Value("${operation2.elementName1}")
	private String operation2_elementName1;

	@Value("${operation2.elementValue1}")
	private String operation2_elementValue1;

	@Value("${operation2.elementName2}")
	private String operation2_elementName2;

	@Value("${operation2.elementValue2}")
	private String operation2_elementValue2;

	@Value("${operation2.response.json_1.xml_0}")
	private int responseFormat2;

	private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

	private static final Parser.WhitespaceBehaviour COMPACT_WHITE_SPACE = Parser.WhitespaceBehaviour.Preserve;

	/**
	 * GetCitiesByCountry API with POST request
	 * 
	 * @param country
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/GetCitiesByCountry", method = RequestMethod.POST)
	// @ApiOperation(value = "Get Cities By Country using POST.", notes =
	// "Returns the resposne in json format")
	public ResponseEntity<String> getCitiesByCountry(@RequestBody Country country, HttpServletRequest request)
			throws Exception {

		HashMap<String, String> parametersMap = new HashMap<String, String>();
		elementValue = country.getName();
		parametersMap.put(elementName, elementValue);

		logger.debug(
				"WSDLURL:" + wsdlURL + "\nLocalPart" + localPart + "\nSOAPOperation" + soapAction + "\nElementName:"
						+ elementName + "\nElementValue:" + elementValue + "\nResponseFormat:" + responseFormat);
		SOAPServiceManipulator soapServiceManipulator = new SOAPServiceManipulator();
		String response = soapServiceManipulator.invokeSOAPService(wsdlURL, localPart, soapAction, parametersMap);

		// If responseFormat is 1 then covert SOAP to JSON
		if (responseFormat == 1) {
			response = convertXMLtoJSON(response.toString());

		}
		logger.debug("Response:\n" + response);
		return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
	}

	/**
	 * GetCitiesByCountry API with GET request.
	 * 
	 * @param countryName
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/GetCitiesByCountry/{countryName}")
	// @ApiOperation(value = "Get Cities By Country using GET.", notes =
	// "Returns the resposne in json format")
	public Map<String, String> getCitiesByCountry(@PathVariable("countryName") String countryName) throws Exception {

		HashMap<String, String> parametersMap = new HashMap<String, String>();
		elementValue = countryName;
		parametersMap.put(elementName, elementValue);

		logger.debug(
				"WSDLURL:" + wsdlURL + "\nLocalPart" + localPart + "\nSOAPOperation" + soapAction + "\nElementName:"
						+ elementName + "\nElementValue:" + elementValue + "\nResponseFormat:" + responseFormat);

		SOAPServiceManipulator soapServiceManipulator = new SOAPServiceManipulator();
		String response = soapServiceManipulator.invokeSOAPService(wsdlURL, localPart, soapAction, parametersMap);

		if (responseFormat == 1) {
			response = convertXMLtoJSON(response.toString());

		}
		logger.debug("Response:\n" + response);
		return Collections.singletonMap("response", response.toString());
	}

	@RequestMapping(value = "/GetWeather", method = RequestMethod.POST)
	// @ApiOperation(value = "Get Cities By Country using POST.", notes =
	// "Returns the resposne in json format")
	public ResponseEntity<String> getWeather(@RequestBody Weather weather, HttpServletRequest request)
			throws Exception {

		operation2_elementValue1 = weather.getCityName();
		operation2_elementValue2 = weather.getCountryName();
		HashMap<String, String> parametersMap = new HashMap<String, String>();
		parametersMap.put(operation2_elementName1, operation2_elementValue1);
		parametersMap.put(operation2_elementName2, operation2_elementValue2);

		logger.debug("WSDLURL:" + wsdlURL2 + "\nLocalPart" + localPart2 + "\nSOAPOperation" + soapAction2
				+ "\nElementName1:" + operation2_elementName1 + "\nElementValue1:" + operation2_elementValue1
				+ "\nElementName2:" + operation2_elementName2 + "\nElementValue2:" + operation2_elementValue2
				+ "\nResponseFormat:" + responseFormat2);
		SOAPServiceManipulator soapServiceManipulator = new SOAPServiceManipulator();
		String response = soapServiceManipulator.invokeSOAPService(wsdlURL2, localPart2, soapAction2, parametersMap);

		// If responseFormat is 1 then covert SOAP to JSON
		if (responseFormat == 1) {
			response = convertXMLtoJSON(response.toString());

		}
		logger.debug("Response:\n" + response);
		return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
	}

	public static int PRETTY_PRINT_INDENT_FACTOR = 4;

	/**
	 * Converts XML to JSON
	 * 
	 * @param xml
	 * @return
	 * @throws JSONException
	 */
	private String convertXMLtoJSON(String xml) throws JSONException {

		if (xml.length() == 0) {
			xml = "{}";
		}
		JSONObject xmlJSONObj = XML.toJSONObject(xml);
		String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
		System.out.println("PRINTING STRING :::::::::::::::::::::" + jsonPrettyPrintString);
		if (jsonPrettyPrintString.length() <= 2) {
			jsonPrettyPrintString = xml;
		}

		return jsonPrettyPrintString;
	}

}
