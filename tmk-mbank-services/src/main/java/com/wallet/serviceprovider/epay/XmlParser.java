package com.wallet.serviceprovider.epay;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlParser {

public HashMap<String, String> response = new HashMap<String, String>();
	
	public HashMap<String, String> getBalaceCheckResponse(String xmlresponse) throws ParserConfigurationException, SAXException, IOException {

		//DOM Parser
	    
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xmlresponse));
		Document xmlDOM = documentBuilder.parse(is);
		xmlDOM.getDocumentElement().normalize();
		NodeList nodeList = xmlDOM.getElementsByTagName("ERROR");
		System.out.println("Node List Length: " + nodeList.getLength());
		for (int count=0; count<nodeList.getLength();count++) {
			Node node = nodeList.item(count);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
//				response.put(element.getElementsByTagName("ERROR").item(0).getTextContent(), 
//						element.getElementsByTagName("ERROR").item(0).getTextContent());
				System.out.println("Elements: " + element.getElementsByTagName("ERROR").item(1).getTextContent());
			}
		}
		
		return response;
	}
	
}
