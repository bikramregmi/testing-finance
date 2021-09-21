<%@page import="com.mobilebanking.entity.User"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/custom.css" rel="stylesheet">
<link href="../css/starter-template.css" rel="stylesheet">
<link href="/css/radinfo.css" rel="stylesheet"></link>
<link rel="stylesheet" href="../css/jquery.dataTables.min.css">
<style>
.mypgactive {
	background: #2A3F54 !important;
	cursor: pointer !important;
	color: white !important;
}
</style>

<spr:page header1="BankWise Ledger">
	<div class="col-md-12">
		<div class="break"></div>
		<c:if test="${not empty message}">
			<p class="bg-success">
				<c:out value="${message}"></c:out>
			</p>
		</c:if>

		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="rad-info-box">
					<div class="row">
						<form action="/ledger/view_bank_ledger" method="get" id="filterForm">

							<div class="col-lg-2 col-md-2">
								<div class="form group">
									<label data-toggle="popover" data-placement="top" data-content="Please select a bank" data-trigger="focus" id="filterBankLabel">Bank</label> <select class="form-control selectpicker" data-live-search="true" name="bank-code" id="filterBankName">
										<option value="" selected>Bank Name</option>
										<c:forEach var="banks" items="${bankList}">
											<c:choose>
												<c:when test="${bankCode eq banks.id}">
													<option value="${banks.id}" selected>${banks.name}</option>
												</c:when>
												<c:otherwise>
													<option value="${banks.id}">${banks.name}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</div>
							</div>


							<div class="col-lg-3 col-md-3">
								<div class="form group">
									<label>From</label> <input type="date" class="form-control" id="from-date" name="from-date" value="${fromDate}"></input>
								</div>
							</div>
							<div class="col-lg-3 col-md-3">
								<div class="form group">
									<label>To</label> <input type="date" class="form-control" id="to-date" name="to-date" value="${toDate}"></input>
								</div>
							</div>
							<div class="col-lg-2 col-md-2">
								<div class="form group">
									<label>Transaction Type</label> <select class="form-control selectpicker" name="dr-cr">
										<option value="" selected>Transaction Type</option>
										<option value="debit" <c:if test="${drCr eq 'debit'}">selected</c:if>>Debit</option>
										<option value="credit" <c:if test="${drCr eq 'credit'}">selected</c:if>>Credit</option>
									</select>
								</div>
							</div>
							<div class="col-lg-1 col-md-1">
								<div class="form group">
									<a onclick="postForm()"><i class="fa fa-search small" title="Search"></i></a>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<table id="bankList" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>Date</th>
					<%-- 	<c:if test="${isAdmin}">
				  <th>Bank Name</th>
				    </c:if> --%>
					<th>From Account No.</th>
					<th>To Account No.</th>
					<th>Dr</th>
					<th>Cr</th>
					<th>Amount</th>
					<th>Status</th>
					<th>Remarks</th>


				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(bankLedgerList) gt 0}">
					<c:forEach var="ledgerlist" items="${bankLedgerList}">
						<tr>
							<%-- <c:if test="${isAdmin}">
						<td>${ledgerlist.Bank}</td>
						</c:if> --%>
							<td>${ledgerlist.date}</td>
							<td><c:choose>
									<c:when test="${ledgerlist.fromAccount eq accountNumber}">
								Bank Account
								</c:when>
									<c:otherwise>${ledgerlist.fromAccount}</c:otherwise>
								</c:choose></td>
							<td><c:choose>
									<c:when test="${ledgerlist.toAccount eq accountNumber}">
								Bank Account
								</c:when>
									<c:otherwise>${ledgerlist.toAccount}</c:otherwise>
								</c:choose></td>
							<c:choose>
								<c:when test="${ledgerlist.fromAccount eq accountNumber}">
									<td>${ledgerlist.amount}</td>
									<td>-</td>
									<td>${ledgerlist.fromBalance}</td>
								</c:when>
								<c:otherwise>
									<td>-</td>
									<td>${ledgerlist.amount}</td>
									<td>${ledgerlist.toBalance}</td>
								</c:otherwise>
							</c:choose>
							<td>${ledgerlist.transactionStatus}</td>
							<td>${ledgerlist.remarks}</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>

</spr:page>

<!-- <script>
$(document).ready(function(){
	var today=new Date();
	document.getElementById('from-date').valueAsDate=today;
	document.getElementById('to-date').valueAsDate=today;
});
</script> -->
<script src="${pageContext.request.contextPath}/resources/js/bank.js"></script>
<script>
	$(document).ready(function() {
		init("${pageContext.request.contextPath}");
	});
</script>
<script>
	function postForm() {
		if($("#filterBankName").val() === undefined || $("#filterBankName").val() === null || $("#filterBankName").val() === ""){
			$("#filterBankLabel").popover('show');
			setTimeout(function(){
				$("#filterBankLabel").popover('hide');
			},2000);
			return false;
		}
		$("#filterForm").submit();
	}
</script>
<style>
table {
	table-layout: fixed;
	font-size: 12px;
}

td {
	word-wrap: break-word;
}
.popover-content{
color:red;
}
</style>