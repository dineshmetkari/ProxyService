package com.bay.webservicex.utility.soapmanipulator;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.reficio.ws.builder.SoapBuilder;
import org.reficio.ws.builder.SoapOperation;
import org.reficio.ws.builder.core.Wsdl;
import org.reficio.ws.client.core.SoapClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import com.bay.webservicex.api.rest.WeatherController;

/**
 * @Class SOAPServiceManipulator This class used to fetch soap request, change
 *        the request parameters, invoke soap call and returns back soap
 *        response.
 * 
 * @author Dinesh Metkari
 * @version v0.1
 * @since 2018-02-21
 * 
 */
public class SOAPServiceManipulator {

	private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

	/**
	 * 
	 * @param wsdlURL
	 * @param localPart
	 * @param soapAction
	 * @param parametersMap
	 * @return
	 * @throws Exception
	 */
	public String invokeSOAPService(String wsdlURL, String localPart, String soapAction, HashMap<String, String> parametersMap) throws Exception {

		Wsdl wsdl = Wsdl.parse(wsdlURL);

		SoapBuilder builder = wsdl.binding().localPart(localPart).find();
		SoapOperation operation = builder.operation().soapAction(soapAction).find();
		String request = builder.buildInputMessage(operation);

		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new InputSource(new ByteArrayInputStream(request.getBytes("utf-8"))));

		String endPointUrl = wsdlURL.substring(0, wsdlURL.indexOf("?"));
		SoapClient client = SoapClient.builder().endpointUri(endPointUrl).build();
		logger.debug("SOAPRequest:\n" + request);
		request = modifySoapRequest(request, parametersMap);
		logger.debug("SOAPRequest Modified:\n" + request);
		String response = client.post(soapAction, request);
		return response;
	}

	/**
	 * Loads Documents model from soap string
	 * 
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	private Document loadXMLFromString(String xml) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xml));
		return builder.parse(is);
	}

	/**
	 * Modifies the mentioned elementName value inside SOAP request.
	 * 
	 * @param soapRequest
	 * @param parametersMap
	 * @return
	 */
	private String modifySoapRequest(String soapRequest, HashMap<String, String> parametersMap) {
		String soapRequstModified = "";
		
		
	       
	        
		
		try {

			Document document = loadXMLFromString(soapRequest);

			 for (Map.Entry<String,String> entry : parametersMap.entrySet()) {
				 String elementName= entry.getKey();
				 String elementValue = entry.getValue();
				 document.getElementsByTagName(elementName).item(0).setTextContent(elementValue);
				 //Node elementNameNode = document.getElementsByTagName(elementName).item(0);
				 //elementNameNode.setTextContent();

			 }
	
			TransformerFactory tff = TransformerFactory.newInstance();
			Transformer transformer = tff.newTransformer();

			DOMSource xmlSource = new DOMSource(document);
			StringWriter writer = new StringWriter();

			StreamResult outputTarget = new StreamResult(writer);

			transformer.transform(xmlSource, outputTarget);
			soapRequstModified = writer.toString();

		} catch (Exception e) {
			e.printStackTrace();

		}
		return soapRequstModified;
	}
}