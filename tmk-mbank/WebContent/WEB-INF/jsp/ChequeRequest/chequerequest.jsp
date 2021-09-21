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
<link rel="stylesheet" href="../css/jquery.dataTables.min.css">
</style>
<!-- <script src ="../js/jquery.min.js"></script> -->
<!-- <script src ="../js/bootstrap.js"></script> -->
<spr:page header1="List Cheque Request List">
	<div class="col-md-12">
		<div class="break"></div>
		<c:if test="${not empty message}">
			<p class="bg-success">
				<c:out value="${message}"></c:out>
			</p>
		</c:if>
		<form action="${pageContext.request.contextPath}/searchAccount"
			modelAttribute="cheque" method="post">
			<div class="form-group">
				<label>Account Number</label> <input type="text"
					name="accountNumber" class="form-control input-sm"
					required="required">
			</div>
			<div class="form-group">
				<button class="btn btn-primary btn-md btn-block btncu">Search
					Account</button>
			</div>
		</form>
		<table id="chequeRequestList" class="table table-striped">
			<thead>
				<tr>
					<th>Date</th>
					<th>Customer Name</th>
					<th>Account Number</th>
					<th>Request Leaves</th>
					<th>Mobile Number</th>
					<th>Action</th>

				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(chequeRequestList) gt 0}">
					<c:forEach var="chequeRequest" items="${chequeRequestList}">
						<tr>
							<td>${chequeRequest.date}</td>
							<td>${chequeRequest.customerName}</td>
							<td>${chequeRequest.accountNumber}</td>
							<td>${chequeRequest.chequeLeaves}</td>
							<td>${chequeRequest.mobileNumber}</td>
							<td><c:choose>
									<c:when test="${chequeRequest.status == 'COMPLETED' }">
										<a class="btn btn-danger" disabled>${chequeRequest.status}</a>
									</c:when>
									<c:otherwise>
										<a class="btn btn-danger"
											ng-click="showDialog(${chequeRequest.status})"
											href="editchequeRequest?chequeRequestId=${chequeRequest.id}">${chequeRequest.status}</a>
									</c:otherwise>
								</c:choose></td>
							<td></td>

						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
</spr:page>
<script src="${pageContext.request.contextPath}/resources/js/bank.js"></script>
<script>
	$(document).ready(function() {
		init("${pageContext.request.contextPath}");
	});
</script>