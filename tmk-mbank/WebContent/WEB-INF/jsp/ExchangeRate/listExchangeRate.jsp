<%@ page contentType="text/html; charset=utf-8" language="java"	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/custom.css" rel="stylesheet">
<link href="../css/starter-template.css" rel="stylesheet"> 
<link rel="stylesheet" href="../css/jquery.dataTables.min.css">
</style>
<!-- <script src ="../js/jquery.min.js"></script> -->
<!-- <script src ="../js/bootstrap.js"></script> -->
<spr:page header1="List ExchangeRate" >
	<div class="col-md-12">
		<div class="break"></div>
		<c:if test="${not empty message}">
   		<p class="bg-success"><c:out value="${message}" ></c:out></p>
	</c:if>
		<table id="exchangeRateList" class="table table-striped">
			<thead>
				<tr>
					<th>From Currency</th>
					<th>To Currency</th>
					<th>Buying Rate</th>
					<th>Selling Rate</th>
					<th>Status</th>
					<th>Type</th>
					<th>Country</th>
					<th>SuperAgent</th>
					<th>Agent</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(exchangeRateList) gt 0}">
					<c:forEach var="exchangeRate" items="${exchangeRateList}">
						<tr>
							<td>${exchangeRate.fromCurrency}</td>
							<td>${exchangeRate.toCurrency}</td>
							<td>${exchangeRate.buyingRate}</td>
							<td>${exchangeRate.sellingRate}</td>
							<td>${exchangeRate.status}</td>
							<td>${exchangeRate.type}</td>
							<td>${exchangeRate.country}</td>
							<td>${exchangeRate.superAgent}</td>
							<td>${exchangeRate.agent}</td>
							<td><a href="editExchangeRate?exchangeRateId=${exchangeRate.id}"><img
									src="../../../images/edit.png" width="15" height="15" /></a></td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
</spr:page>