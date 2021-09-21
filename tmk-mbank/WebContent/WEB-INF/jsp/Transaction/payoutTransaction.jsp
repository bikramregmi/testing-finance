<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/custom.css" rel="stylesheet">
<link href="../css/starter-template.css" rel="stylesheet">
<link rel="stylesheet" hre="../css/jquery.dataTables.min.css">
</style>
<!-- <script src ="../js/jquery.min.js"></script> -->
<!-- <script src ="../js/bootstrap.js"></script> -->
<spr:page header1="Payout Transaction">
	<div class="col-md-12"><c:out value="${transaction.uniqueNumber}"></c:out>
		<div class="break"></div>
		<c:if test="${not empty message}">
			<p class="bg-success">
				<c:out value="${message}"></c:out>
			</p>
		</c:if>
		<table id="transaction" class="table table-striped">
			<thead>
				<tr>
					<th>Sender Name</th>
					<th>Beneficiary Name</th>
					<th>Remit Country</th>
					<th>Payout Country</th>
					<th>Sending Amount</th>
					<th>Sending Currency</th>
					<th>Sending Commission</th>
					<th>Receiving Amount</th>
					<th>Exchange Rate</th>
					<th>Transaction Status</th>
					<th colspan="3">Action</th>
				</tr>
			</thead>
			<tbody>
						<tr>
							<td>${transaction.sender.firstName}</td>
							<td>${transaction.beneficiary.firstName}</td>
							<td>${transaction.remitCountry.name}</td>
							<td>${transaction.payoutCountry.name}</td>
							<td>${transaction.sendingAmount}</td>
							<td>${transaction.sendingCurrency.currencyCode}</td>
							<td>${transaction.sendingCommission}</td>
							<td>${transaction.receivingAmount}</td>
							<td>${transaction.exchangeRate}</td>
							<td>${transaction.transactionStatus}</td>
							<td><a
								href="${pageContext.request.contextPath}/transaction/payout/view?transactionId=${transaction.id}"
								class="btn btn-info btn-xs"><i class="fa fa-pencil"></i>
									View Details </a></td>
							<td><a
								href="${pageContext.request.contextPath}/editTransaction?transactionId=${transaction.id}"
								class="btn btn-info btn-xs"><i class="fa fa-pencil"></i>
									Edit </a></td>
							<td><a
								href="${pageContext.request.contextPath}/deleteTransaction?transactionId=${transaction.uniqueNumber}"
								class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i>
									Delete </a></td>
						</tr>
				
			</tbody>
		</table>
</spr:page>
