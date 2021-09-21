package com.mobilebanking.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mobilebanking.model.AmlDTO;
import com.mobilebanking.model.AmlElementEuDTO;

public class EuclXmlParser {

	private float entityid;

	private String source;

	private float documentid;

	private String name;

	private String alias;

	private String address;

	private String documents;

	private String date;

	private String additional;

	private String type;

	public List<AmlDTO> unclParse(String location) throws IOException {

		List<AmlDTO> allalist = new ArrayList<AmlDTO>();
		try {

			File file = new File(location);
			JAXBContext jc = JAXBContext.newInstance(AmlElementEuDTO.class);

			Unmarshaller ums = jc.createUnmarshaller();
			AmlElementEuDTO amlparsed = (AmlElementEuDTO) ums.unmarshal(file);

			for (Object el : amlparsed.getOthers()) {

				org.w3c.dom.Element elem = (org.w3c.dom.Element) el;
				AmlDTO puser = this.retriveXmlValues(elem);
				allalist.add(puser);
			}

		} catch (JAXBException e) {
			e.printStackTrace();
		}

		return allalist;

	}

	protected AmlDTO retriveXmlValues(org.w3c.dom.Element elem) throws IOException {

		entityid = 0;

		documentid = 0;

		name = "";

		alias = "";

		address = "";

		documents = "";

		date = "";

		additional = "";

		type = "";

		source = "eucl";

		AmlDTO entries = null;
		NodeList nodeList = elem.getChildNodes();

		if (elem.getNodeName().equalsIgnoreCase("ENTITY")) {

			entries = new AmlDTO();
			NamedNodeMap attrs = elem.getAttributes();

			for (int ai = 0; ai < attrs.getLength(); ai++) {
				if (attrs.item(ai).getNodeName().equalsIgnoreCase("id")) {

					entityid = Integer.parseInt(attrs.item(ai).getNodeValue());

				} else if (attrs.item(ai).getNodeName().equalsIgnoreCase("type")) {

					type = attrs.item(ai).getNodeValue();
				} else {
					additional = additional + " " + elem.getNodeName() + " : " + attrs.item(ai).getNodeName() + " : "
							+ attrs.item(ai).getNodeValue() + ", ";
				}

			}

		}

		for (int i = 0; i < nodeList.getLength(); i++) {

			Node currentNode = nodeList.item(i);
			try {

				if (currentNode.getNodeType() == Node.ELEMENT_NODE) {

					NamedNodeMap attrs = elem.getAttributes();

					for (int nai = 0; nai < attrs.getLength(); nai++) {
						additional = additional + " " + currentNode.getNodeName() + " : "
								+ attrs.item(nai).getNodeName() + " : " + attrs.item(nai).getNodeValue() + ", ";

					}

					if (currentNode.getFirstChild().hasChildNodes()) {

						if (currentNode.getNodeName().equalsIgnoreCase("NAME")) {

						} else if (currentNode.getNodeName().equalsIgnoreCase("BIRTH")) {

						} else if (currentNode.getNodeName().equalsIgnoreCase("ADDRESS")) {

						} else {
							additional = additional + " " + currentNode.getNodeName() + " : ";
						}

						org.w3c.dom.Element elel = (org.w3c.dom.Element) currentNode;

						this.subretriveXmlValues(elel);

					}

					else {

					}

				}
			} catch (Exception e) {

			}
		}

		if (!(entries == null)) {
			entries.setEntityid(entityid);
			entries.setName(name);
			entries.setAdditional(additional);
			entries.setDate(date);
			entries.setAddress(address);
			entries.setSource(source);
			entries.setType(type);
		}
		return entries;

	}

	protected void subretriveXmlValues(org.w3c.dom.Element elem) throws IOException {

		NodeList nodeList = elem.getChildNodes();

		NamedNodeMap attrs = elem.getAttributes();

		for (int ai = 0; ai < attrs.getLength(); ai++) {

			additional = additional + " " + elem.getNodeName() + " : " + attrs.item(ai).getNodeName() + " : "
					+ attrs.item(ai).getNodeValue() + ", ";

		}

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node currentNode = nodeList.item(i);
			try {
				if (currentNode.getNodeType() == Node.ELEMENT_NODE) {

					if (elem.getNodeName().equalsIgnoreCase("NAME")) {

						if (currentNode.getNodeName().equalsIgnoreCase("WHOLENAME")) {
							name = name + currentNode.getFirstChild().getNodeValue() + ", ";

						} else {
							additional = additional + " " + currentNode.getNodeName() + " : "
									+ currentNode.getFirstChild().getNodeValue() + ", ";
						}
					} else if (elem.getNodeName().equalsIgnoreCase("BIRTH")) {
						if (currentNode.getNodeName().equalsIgnoreCase("DATE")) {
							date = date + " " + currentNode.getFirstChild().getNodeValue() + ", ";
						} else {
							additional = additional + " " + currentNode.getNodeName() + " : "
									+ currentNode.getFirstChild().getNodeValue() + ", ";
						}
					} else if (elem.getNodeName().equalsIgnoreCase("ADDRESS")) {
						if (currentNode.getNodeName().equalsIgnoreCase("STREET")) {
							address = address + " " + currentNode.getFirstChild().getNodeValue() + ", ";
						} else if (currentNode.getNodeName().equalsIgnoreCase("CITY")) {
							address = address + " " + currentNode.getFirstChild().getNodeValue() + ", ";
						} else if (currentNode.getNodeName().equalsIgnoreCase("COUNTRY")) {
							address = address + " " + currentNode.getFirstChild().getNodeValue() + ", ";
						} else {
							additional = additional + " " + currentNode.getNodeName() + " : "
									+ currentNode.getFirstChild().getNodeValue() + ", ";

						}
					} else {

						additional = additional + " " + currentNode.getNodeName() + " : "
								+ currentNode.getFirstChild().getNodeValue() + ", ";

					}

				}
			} catch (Exception e) {

			}
		}

	}

}
