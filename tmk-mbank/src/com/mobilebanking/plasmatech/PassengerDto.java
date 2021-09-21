package com.mobilebanking.plasmatech;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Passenger")
@XmlAccessorType (XmlAccessType.FIELD)
public class PassengerDto {
	
	@XmlElement(name = "PaxType")
	private String paxType;
	
	@XmlElement(name = "Title")
	private String title;
	
	@XmlElement(name = "Gender")
	private String gender;
	
	@XmlElement(name = "FirstName")
	private String firstName;
	
	@XmlElement(name = "LastName")
	private String lastName;
	
	@XmlElement(name = "Nationality")
	private String nationality;
	
	@XmlElement(name = "PaxRemarks")
	private String paxRemarks;
	
	public String getPaxType() {
		return paxType;
	}
	public void setPaxType(String paxType) {
		this.paxType = paxType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getPaxRemarks() {
		return paxRemarks;
	}
	public void setPaxRemarks(String paxRemarks) {
		this.paxRemarks = paxRemarks;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	

}
