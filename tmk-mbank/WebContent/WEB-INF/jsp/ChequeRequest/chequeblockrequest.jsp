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
<spr:page header1="List ChequeBlockRequest">
	<div class="col-md-12">
		<div class="break"></div>
		<c:if test="${not empty message}">
			<p class="bg-success">
				<c:out value="${message}"></c:out>
			</p>
		</c:if>
		<c:if test="${bank}">
			<div class="form-group">
				<label>Select Bank Branch</label> <select name="bankBranchId"
					class="form-control input-sm" id="bankBranch">
					<c:if test="${fn:length(bankBranchList) gt 0}">
						<option value="0" selected="selected" disabled="disabled">Select
							Bank Branch</option>
						<c:forEach var="branch" items="${bankBranchList}">
							<option value="${branch.id}">${branch.name}</option>
						</c:forEach>
					</c:if>
				</select>
				<p class="error">${error.state}</p>
			</div>
		</c:if>
		<form
			action="${pageContext.request.contextPath}/searchAccountOfBlockedCheque"
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
					<th>Cheque Number</th>
					<th>Mobile Number</th>
					<th>Action</th>

				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(chequeBlockRequestList) gt 0}">
					<c:forEach var="chequeBlockRequest"
						items="${chequeBlockRequestList}">
						<tr>
							<td>${chequeBlockRequest.date}</td>
							<td>${chequeBlockRequest.customerName}</td>
							<td>${chequeBlockRequest.accountNumber}</td>
							<td>${chequeBlockRequest.chequeNumber}</td>
							<td>${chequeBlockRequest.mobileNumber}</td>
							<td><c:choose>
									<c:when test="${chequeBlockRequest.status == 'BLOCKED' }">
										<a class="btn btn-primary" disabled>${chequeBlockRequest.status}</a>
									</c:when>
									<c:otherwise>
										<a class="btn btn-danger"
											ng-click="showDialog(${chequeBlockRequest.status})"
											href="editchequeBlockRequest?chequeBlockRequestId=${chequeBlockRequest.id}">${chequeBlockRequest.status}</a>
									</c:otherwise>
								</c:choose></td>
							<td></td>

						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
</spr:page>
$(document).ready(function() {
init("${pageContext.request.contextPath}"); });

<script>
$(document).ready(function() {
	  
	$("#bankBranch").change(function() {
		var branchId = $("#bankBranch").find(
				"option:selected").val();
		
		$.ajax({
			type : "GET",
			url : "/getBlockChequeRequest?branchId="
					+ branchId,
			success : function(data) {
			if(data.message==='Data Read Success'){
				var arrayLength = data.detail.length;
				alert(arrayLength);
				var theTable = document.getElementById('chequeRequestList');
				alert(theTable);
				for (var i = 0, tr, td; i < arrayLength; i++) {
				    tr = document.createElement('tr');
				    td = document.createElement('td');
				    var cutomerName = data.detail[i]).customerName;
				    alert(data.detail[i].accountNumber);
				    td.appendChild(document.createTextNode(data.detail[i]).accountNumber);
				    tr.appendChild(td);
				    theTable.appendChild(tr);
				}
				
			}
					}
		});
	});
});
</script>