package com.serviceprovider.AirlinesResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Flightinfo {
	@XmlElement
	 private String TOD;
	@XmlElement
	    private String DOA;
	@XmlElement
	    private String DOD;
	@XmlElement
	    private String TOA;
	@XmlElement
	    private String elecTicketing;
	@XmlElement
	    private String productdetailqualifier;
	@XmlElement
	    private String refno;
	@XmlElement
	    private SrcLocation SrcLocation;
	@XmlElement
	    private String equiptype;
	@XmlElement
	    private String flighttime;
	@XmlElement
	    private String marketingCarrier;
	@XmlElement
	    private DstLocation DstLocation;
	@XmlElement
	    private String flightno;
	@XmlElement
	    private String operatingCarrier;

		public String getTOD() {
			return TOD;
		}

		public void setTOD(String tOD) {
			TOD = tOD;
		}

		public String getDOA() {
			return DOA;
		}

		public void setDOA(String dOA) {
			DOA = dOA;
		}

		public String getDOD() {
			return DOD;
		}

		public void setDOD(String dOD) {
			DOD = dOD;
		}

		public String getTOA() {
			return TOA;
		}

		public void setTOA(String tOA) {
			TOA = tOA;
		}

		public String getElecTicketing() {
			return elecTicketing;
		}

		public void setElecTicketing(String elecTicketing) {
			this.elecTicketing = elecTicketing;
		}

		public String getProductdetailqualifier() {
			return productdetailqualifier;
		}

		public void setProductdetailqualifier(String productdetailqualifier) {
			this.productdetailqualifier = productdetailqualifier;
		}

		public String getRefno() {
			return refno;
		}

		public void setRefno(String refno) {
			this.refno = refno;
		}

		public SrcLocation getSrcLocation() {
			return SrcLocation;
		}

		public void setSrcLocation(SrcLocation srcLocation) {
			SrcLocation = srcLocation;
		}

		public String getEquiptype() {
			return equiptype;
		}

		public void setEquiptype(String equiptype) {
			this.equiptype = equiptype;
		}

		public String getFlighttime() {
			return flighttime;
		}

		public void setFlighttime(String flighttime) {
			this.flighttime = flighttime;
		}

		public String getMarketingCarrier() {
			return marketingCarrier;
		}

		public void setMarketingCarrier(String marketingCarrier) {
			this.marketingCarrier = marketingCarrier;
		}

		public DstLocation getDstLocation() {
			return DstLocation;
		}

		public void setDstLocation(DstLocation dstLocation) {
			DstLocation = dstLocation;
		}

		public String getFlightno() {
			return flightno;
		}

		public void setFlightno(String flightno) {
			this.flightno = flightno;
		}

		public String getOperatingCarrier() {
			return operatingCarrier;
		}

		public void setOperatingCarrier(String operatingCarrier) {
			this.operatingCarrier = operatingCarrier;
		}
	    
}
