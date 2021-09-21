package com.wallet.serviceprovider.bussewa;

public class TripSearch {
private String from;
private String to;
private String date;
public String getFrom() {
	return from;
}
public void setFrom(String from) {
	this.from = from;
}
public String getTo() {
	return to;
}
public void setTo(String to) {
	this.to = to;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
@Override
public String toString() {
	return "TripSearch [from=" + from + ", to=" + to + ", date=" + date + "]";
}



}
