package com.wallet.serviceprovider.bussewa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class BackToPreviousPage {
private int id;
private String ticketSrlNo;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getTicketSrlNo() {
	return ticketSrlNo;
}
public void setTicketSrlNo(String ticketSrlNo) {
	this.ticketSrlNo = ticketSrlNo;
}
@Override
public String toString() {
	return "BackToPreviousPage [id=" + id + ", ticketSrlNo=" + ticketSrlNo + "]";
}


	
}
/*back to previous page

int id, String ticketSrlNo as given below.
{
    id="NzI4OTdhZG1pbg==";
    ticketSrlNo="25932";
 }*/