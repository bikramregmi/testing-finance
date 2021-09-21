package com.mobilebanking.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mobilebanking.model.AmlDTO;
import com.mobilebanking.model.AmlElementOfacDTO;

public class OfacXmlParser {

	private float entityid;

	private String source = "ofac";

	private float documentid;

	private String name = "";

	private String alias="";

	private String address="";

	private String documents="";

	private String date="";

	private String additional = "";

	private String type="";

	public List<AmlDTO> ofacParse(String location) throws IOException {

		File log = new File(location);

		String search = "xmlns=\"";

		String replace = "xmlns:noNamespaceSchemaLocation=\"";

		try {
			FileReader fr = new FileReader(log);
			String s;
			String totalStr = "";
			try (BufferedReader br = new BufferedReader(fr)) {

				while ((s = br.readLine()) != null) {
					totalStr += s;
				}

				if (totalStr.contains(search)) {
					totalStr = totalStr.replace(search, replace);

					FileWriter fw = new FileWriter(log);
					fw.write(totalStr);
					fw.close();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<AmlDTO> allalist = new ArrayList<AmlDTO>();

		try {

			File file = new File(location);
			JAXBContext jc = JAXBContext.newInstance(AmlElementOfacDTO.class);

			Unmarshaller ums = jc.createUnmarshaller();
			AmlElementOfacDTO amlparsed = (AmlElementOfacDTO) ums.unmarshal(file);

			for (Object el : amlparsed.getOthers()) {

				org.w3c.dom.Element elem = (org.w3c.dom.Element) el;
				AmlDTO aml = this.retriveXmlValues(elem);
				if (!(aml == null)) {
					allalist.add(aml);
				}
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

		source = "ofac";

		AmlDTO aml = null;
		NodeList nodeList = elem.getChildNodes();

		if (elem.getNodeName().equalsIgnoreCase("sdnEntry")) {
			aml = new AmlDTO();

		}

		for (int i = 0; i < nodeList.getLength(); i++) {

			Node currentNode = nodeList.item(i);
			try {

				if (currentNode.getNodeType() == Node.ELEMENT_NODE) {

					if (currentNode.getFirstChild().hasChildNodes()) {

						org.w3c.dom.Element elel = (org.w3c.dom.Element) currentNode;

						this.subretriveXmlValues(elel);

						if (currentNode.getNodeName().equalsIgnoreCase("akaList")) {

						} else if (currentNode.getNodeName().equalsIgnoreCase("addressList")) {

						} else if (currentNode.getNodeName().equalsIgnoreCase("dateOfBirthList")) {

						} else {
							additional = additional + " " + currentNode.getNodeName() + " - ";
						}

					} else {

						if (currentNode.getNodeName().equalsIgnoreCase("uid")) {
							entityid = Float.parseFloat(currentNode.getFirstChild().getNodeValue());
						} else if (currentNode.getNodeName().equalsIgnoreCase("firstname")) {
							name = name + currentNode.getFirstChild().getNodeValue() + " ";
						}

						else if (currentNode.getNodeName().equalsIgnoreCase("lastname")) {
							name = name + currentNode.getFirstChild().getNodeValue();
						} else if (currentNode.getNodeName().equalsIgnoreCase("sdnType")) {
							type = currentNode.getFirstChild().getNodeValue();
						} else {
							additional = additional + " " + currentNode.getNodeName() + " : "
									+ currentNode.getFirstChild().getNodeValue() + ", ";
						}
					}

				}
			} catch (Exception e) {

			}
		}
		if (!(aml == null)) {
			aml.setEntityid(entityid);
			aml.setAddress(address);
			aml.setAlias(alias);
			aml.setName(name);
			aml.setType(type);
			aml.setAdditional(additional);
			aml.setDate(date);
			aml.setType(type);
			aml.setSource(source);
		}

		return aml;

	}

	protected void subretriveXmlValues(org.w3c.dom.Element elem) throws IOException {
		NodeList nodeList = elem.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node currentNode = nodeList.item(i);
			try {
				if (currentNode.getNodeType() == Node.ELEMENT_NODE) {

					if (currentNode.getFirstChild().hasChildNodes()) {

						org.w3c.dom.Element elel = (org.w3c.dom.Element) currentNode;

						this.subsubretriveXmlValues(elel);

						if (currentNode.getNodeName().equalsIgnoreCase("aka")) {

						} else if (currentNode.getNodeName().equalsIgnoreCase("address")) {

						} else if (currentNode.getNodeName().equalsIgnoreCase("dateOfBirthItem")) {

						} else {
							additional = additional + " " + currentNode.getNodeName() + " - ";
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

	protected void subsubretriveXmlValues(org.w3c.dom.Element elem) throws IOException {

		NodeList nodeList = elem.getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node currentNode = nodeList.item(i);
			try {
				if (currentNode.getNodeType() == Node.ELEMENT_NODE) {

					if (currentNode.getFirstChild().hasChildNodes()) {

						org.w3c.dom.Element elel = (org.w3c.dom.Element) currentNode;

						this.subsubretriveXmlValues(elel);

					} else {

						if (elem.getNodeName().equalsIgnoreCase("aka")) {
							if (currentNode.getNodeName().equalsIgnoreCase("lastName")) {
								alias = alias + " " + currentNode.getFirstChild().getNodeValue();
							} else if (currentNode.getNodeName().equalsIgnoreCase("firstName")) {
								alias = alias + " " + currentNode.getFirstChild().getNodeValue() + " ";
							} else {
								additional = additional + " " + elem.getNodeName() + " : " + currentNode.getNodeName()
										+ " : " + currentNode.getFirstChild().getNodeValue() + ", ";
							}
						}

						else if (elem.getNodeName().equalsIgnoreCase("address")) {

							if (currentNode.getNodeName().equalsIgnoreCase("city")) {
								address = address + " " + currentNode.getFirstChild().getNodeValue() + ", ";
							} else if (currentNode.getNodeName().equalsIgnoreCase("stateOrProvince")) {
								address = address + " " + currentNode.getFirstChild().getNodeValue() + ", ";
							} else if (currentNode.getNodeName().equalsIgnoreCase("country")) {
								alias = alias + " " + currentNode.getFirstChild().getNodeValue();
							}

							else {
								additional = additional + " " + elem.getNodeName() + " : " + currentNode.getNodeName()
										+ " : " + currentNode.getFirstChild().getNodeValue() + ", ";
							}

						}

						else if (elem.getNodeName().equalsIgnoreCase("dateOfBirthItem")) {

							if (currentNode.getNodeName().equalsIgnoreCase("dateOfBirth")) {
								date = currentNode.getFirstChild().getNodeValue();
							} else {
								additional = additional + " " + elem.getNodeName() + " : " + currentNode.getNodeName()
										+ " : " + currentNode.getFirstChild().getNodeValue() + ", ";
							}

						} else {
							additional = additional + " " + currentNode.getNodeName() + " : "
									+ currentNode.getFirstChild().getNodeValue() + ", ";
						}

					}

				}
			} catch (Exception e) {

			}
		}

	}

}
