<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/custom.css" rel="stylesheet">
<link href="../css/starter-template.css" rel="stylesheet">
<link rel="stylesheet" href="../css/jquery.dataTables.min.css">
</style>
<!-- <script src ="../js/jquery.min.js"></script> -->
<!-- <script src ="../js/bootstrap.js"></script> -->
<spr:page header1="Bank Account Detail Report">
<div ng-controller="reportController" id="bankAccountReport">
	<div class="col-md-12" >
		<div class="break"></div>
		<c:if test="${not empty message}">
			<p class="bg-success">
				<c:out value="${message}"></c:out>
			</p>
		</c:if>
		<table id="bankBalanceTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>Bank Name</th>
					<th>Address</th>
					<th>State</th>
					<th>City</th>
					<th>Swift Code</th>
					<th>Sms Count</th>
					<th>License Count</th>
					<th>Channel Partner</th>
					<th>Remaining Balance</th>
					<th>Credit Limit</th>
					

				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(bankList) gt 0}">
					<c:forEach var="bank" items="${bankList}">
						<tr>
							<td>${bank.name}</td>
							<td>${bank.address}</td>
							<td>${bank.state}</td>
							<td>${bank.city}</td>
							<td>${bank.swiftCode}</td>
							<td>${bank.smsCount}</td>
							<td>${bank.licenseCount}</td>
							<td>${bank.channelPartnerName}</td>
							<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${bank.remainingBalance}" /></td>
							<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${bank.creditLimit}" /></td>
		
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		   <button type="submit" ng-click="downloadReport('EXCEL','CheckBankBanlanceReport','bankBalanceTable')" class="btn btn-warning">
		   Download <span class="glyphicon glyphicon-download"></span>
		</button>
		</div>
</spr:page>

<script src="${pageContext.request.contextPath}/js/Angular/report.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bank.js"></script>
<script>
	$(document).ready(function() {
		init("${pageContext.request.contextPath}");
	});
</script>
<style>
table {
	font-size: 12px;
}

td {
	word-wrap: break-word;
}

.btn-xs {
	font-size: 11px;
}
</style>