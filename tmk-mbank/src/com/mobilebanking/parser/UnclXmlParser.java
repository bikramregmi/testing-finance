package com.mobilebanking.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mobilebanking.model.AmlDTO;
import com.mobilebanking.model.AmlElementUnDTO;

public class UnclXmlParser {

	private float entityid;

	private String uploadedBy;

	private String source;

	private int documentid;

	private String name;

	private String alias;

	private String address;

	private String documents;

	private String date;

	private String additional;

	private String type;

	public List<AmlDTO> unclxmlParse(String location) throws IOException {
		List<AmlDTO> allalist = new ArrayList<AmlDTO>();

		try {

			File file = new File(location);
			JAXBContext jc = JAXBContext.newInstance(AmlElementUnDTO.class);

			Unmarshaller ums = jc.createUnmarshaller();
			AmlElementUnDTO amlparsed = (AmlElementUnDTO) ums.unmarshal(file);

			for (Object el : amlparsed.getOthers()) {

				org.w3c.dom.Element elem = (org.w3c.dom.Element) el;

				List<AmlDTO> retparse = this.retriveXmlValues(elem);
				for (AmlDTO amlpars : retparse) {
					allalist.add(amlpars);
				}

			}

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		return allalist;

	}

	protected List<AmlDTO> retriveXmlValues(org.w3c.dom.Element elem) throws IOException {

		List<AmlDTO> parsedelem = new ArrayList<AmlDTO>();

		NodeList nodeList = elem.getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++) {

			entityid = 0;

			uploadedBy = "";

			source = "";

			documentid = 0;

			name = "";

			alias = "";

			address = "";

			documents = "";

			date = "";

			additional = "";

			type = "";

			AmlDTO ur = null;
			Node currentNode = nodeList.item(i);
			try {

				if (currentNode.getNodeType() == Node.ELEMENT_NODE) {

					if (currentNode.getFirstChild().hasChildNodes()) {

						if (currentNode.getNodeName().equalsIgnoreCase("INDIVIDUAL")) {

							ur = new AmlDTO();
							type = "INDIVIDUAL";

							org.w3c.dom.Element elel = (org.w3c.dom.Element) currentNode;

							this.subretriveXmlValuesind(elel);

						} else if (currentNode.getNodeName().equalsIgnoreCase("ENTITY")) {

							ur = new AmlDTO();
							type = "ENTITY";

							org.w3c.dom.Element elel = (org.w3c.dom.Element) currentNode;

							this.subretriveXmlValuesent(elel);

						}

					}

					else {

					}

				}
			} catch (Exception e) {

			}

			System.out.println("id "+entityid);
			System.out.println("name "+name);
			System.out.println("doucment "+documents);
			System.out.println("address "+address);
			System.out.println("alias "+alias);
			System.out.println("date "+date);
			System.out.println("Source "+source);
			System.out.println("type "+type);
			System.out.println("additional "+additional);
			System.out.println("-----------------------------------------------");
			if (!(ur == null)) {
				ur.setEntityid(entityid);
				ur.setName(name);
				ur.setDocuments(documents);
				ur.setAddress(address);
				ur.setAlias(alias);
				ur.setDate(date);
				ur.setSource(source);
				ur.setType(type);
				ur.setAdditional(additional);

				parsedelem.add(ur);
			}

		}

		return parsedelem;

	}

	protected void subretriveXmlValuesind(org.w3c.dom.Element elem) throws IOException {

		NodeList nodeList = elem.getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node currentNode = nodeList.item(i);
			try {
				if (currentNode.getNodeType() == Node.ELEMENT_NODE) {

					if (currentNode.getFirstChild().hasChildNodes()) {

						if (currentNode.getNodeName().equalsIgnoreCase("LIST_TYPE")) {

						} else if (currentNode.getNodeName().equalsIgnoreCase("INDIVIDUAL_ADDRESS")) {

						} else if (currentNode.getNodeName().equalsIgnoreCase("INDIVIDUAL_DOCUMENT")) {

						} else if (currentNode.getNodeName().equalsIgnoreCase("INDIVIDUAL_ALIAS")) {

						} else {

							additional = additional + " " + currentNode.getNodeName() + " : ";

						}

						org.w3c.dom.Element elel = (org.w3c.dom.Element) currentNode;

						this.subsubretriveXmlValuesind(elel);

					} else {

						if (currentNode.getNodeName().equalsIgnoreCase("DATAID")) {

							entityid = Float.parseFloat(currentNode.getFirstChild().getNodeValue());

						} else if (currentNode.getNodeName().equalsIgnoreCase("FIRST_NAME")) {
							name = currentNode.getFirstChild().getNodeValue();

						} else if (currentNode.getNodeName().equalsIgnoreCase("SECOND_NAME")) {
							name = name + " " + currentNode.getFirstChild().getNodeValue();
						} else if (currentNode.getNodeName().equalsIgnoreCase("LISTED_ON")) {
							date = currentNode.getFirstChild().getNodeValue();
						} else {
							additional = additional + currentNode.getNodeName() + " : "
									+ currentNode.getFirstChild().getNodeValue() + ", ";
						}

					}

				}
			} catch (Exception e) {

			}
		}

	}

	protected void subsubretriveXmlValuesind(org.w3c.dom.Element elem) throws IOException {

		NodeList nodeList = elem.getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node currentNode = nodeList.item(i);
			try {
				if (currentNode.getNodeType() == Node.ELEMENT_NODE) {

					if (currentNode.getFirstChild().hasChildNodes()) {

						org.w3c.dom.Element elel = (org.w3c.dom.Element) currentNode;

						this.subsubretriveXmlValuesind(elel);

					} else {

						if (elem.getNodeName().equalsIgnoreCase("LIST_TYPE")) {

							if (currentNode.getNodeName().equalsIgnoreCase("VALUE")) {

								source = currentNode.getFirstChild().getNodeValue();

							}
						} else if (elem.getNodeName().equalsIgnoreCase("INDIVIDUAL_ADDRESS")) {

							if (currentNode.getNodeName().equalsIgnoreCase("STREET")) {
								address = address + " " + currentNode.getFirstChild().getNodeValue();
							} else if (currentNode.getNodeName().equalsIgnoreCase("STATE_PROVINCE")) {
								address = address + " " + currentNode.getFirstChild().getNodeValue();
							} else if (currentNode.getNodeName().equalsIgnoreCase("COUNTRY")) {
								address = address + " " + currentNode.getFirstChild().getNodeValue() + ", ";
							}
						} else if (elem.getNodeName().equalsIgnoreCase("INDIVIDUAL_DOCUMENT")) {

							if (currentNode.getNodeName().equalsIgnoreCase("TYPE_OF_DOCUMENT")) {
								documents = documents + " " + currentNode.getFirstChild().getNodeValue();
							} else if (currentNode.getNodeName().equalsIgnoreCase("NUMBER")) {
								documents = documents + " " + currentNode.getNodeName() + " : "
										+ currentNode.getFirstChild().getNodeValue() + ", ";
							} else {
								additional = additional + " " + currentNode.getNodeName() + " : "
										+ currentNode.getFirstChild().getNodeValue() + ", ";
							}

						} else if (elem.getNodeName().equalsIgnoreCase("INDIVIDUAL_ALIAS")) {

							if (currentNode.getNodeName().equalsIgnoreCase("ALIAS_NAME")) {
								alias = alias + " " + currentNode.getFirstChild().getNodeValue() + ", ";

							} else {
								additional = additional + " " + currentNode.getNodeName() + " : "
										+ currentNode.getFirstChild().getNodeValue() + ", ";
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

	protected void subretriveXmlValuesent(org.w3c.dom.Element elem) throws IOException {

		NodeList nodeList = elem.getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node currentNode = nodeList.item(i);
			try {
				if (currentNode.getNodeType() == Node.ELEMENT_NODE) {

					if (currentNode.getFirstChild().hasChildNodes()) {

						if (currentNode.getNodeName().equalsIgnoreCase("LIST_TYPE")) {

						} else if (currentNode.getNodeName().equalsIgnoreCase("ENTITY_ADDRESS")) {

						} else if (currentNode.getNodeName().equalsIgnoreCase("ENTITY_ALIAS")) {

						} else {

							additional = additional + " " + currentNode.getNodeName() + " : ";

						}

						org.w3c.dom.Element elel = (org.w3c.dom.Element) currentNode;

						this.subsubretriveXmlValuesent(elel);

					} else {

						if (currentNode.getNodeName().equalsIgnoreCase("DATAID")) {

							entityid = Float.parseFloat(currentNode.getFirstChild().getNodeValue());

						} else if (currentNode.getNodeName().equalsIgnoreCase("FIRST_NAME")) {
							name = currentNode.getFirstChild().getNodeValue();

						} else if (currentNode.getNodeName().equalsIgnoreCase("SECOND_NAME")) {
							name = name + " " + currentNode.getFirstChild().getNodeValue();
						} else if (currentNode.getNodeName().equalsIgnoreCase("LISTED_ON")) {
							date = currentNode.getFirstChild().getNodeValue();
						} else {
							additional = additional + currentNode.getNodeName() + " : "
									+ currentNode.getFirstChild().getNodeValue() + ", ";
						}

					}

				}
			} catch (Exception e) {

			}
		}

	}

	protected void subsubretriveXmlValuesent(org.w3c.dom.Element elem) throws IOException {

		NodeList nodeList = elem.getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node currentNode = nodeList.item(i);
			try {
				if (currentNode.getNodeType() == Node.ELEMENT_NODE) {

					if (currentNode.getFirstChild().hasChildNodes()) {

						org.w3c.dom.Element elel = (org.w3c.dom.Element) currentNode;

						this.subsubretriveXmlValuesent(elel);

					} else {

						if (elem.getNodeName().equalsIgnoreCase("LIST_TYPE")) {

							if (currentNode.getNodeName().equalsIgnoreCase("VALUE")) {

								source = currentNode.getFirstChild().getNodeValue();

							}
						} else if (elem.getNodeName().equalsIgnoreCase("ENTITY_ADDRESS")) {

							if (currentNode.getNodeName().equalsIgnoreCase("STREET")) {
								address = address + " " + currentNode.getFirstChild().getNodeValue();
							} else if (currentNode.getNodeName().equalsIgnoreCase("STATE_PROVINCE")) {
								address = address + " " + currentNode.getFirstChild().getNodeValue();
							} else if (currentNode.getNodeName().equalsIgnoreCase("COUNTRY")) {
								address = address + " " + currentNode.getFirstChild().getNodeValue() + ", ";
							}

						} else if (elem.getNodeName().equalsIgnoreCase("ENTITY_ALIAS")) {

							if (currentNode.getNodeName().equalsIgnoreCase("ALIAS_NAME")) {
								alias = alias + " " + currentNode.getFirstChild().getNodeValue() + ", ";

							} else {
								additional = additional + " " + currentNode.getNodeName() + " : "
										+ currentNode.getFirstChild().getNodeValue() + ", ";
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
