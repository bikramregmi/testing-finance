<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="org.slf4j.Logger" %>
<%@page import="org.slf4j.LoggerFactory" %>
<% Logger logger = LoggerFactory.getLogger(this.getClass().getName());%>
<%logger.info("in message.jsp"); %>
<c:out value="${message}" ></c:out>
