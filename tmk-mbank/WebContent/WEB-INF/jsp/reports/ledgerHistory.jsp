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
<spr:page header1="Ledger History">
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
						<form action="/ledger/ledger_history" method="get" id="filterForm">
							<c:if test="${isAdmin}">
								<div class="col-lg-2 col-md-2">
									<div class="form group">
										<label>Bank</label> <select class="form-control selectpicker" name="swift-code">
											<option value="" selected disabled>Bank Name</option>
											<c:forEach var="banks" items="${bankList}">
												<option value="${banks.id}">${banks.name}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</c:if>
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
							<div class="col-lg-3 col-md-3">
								<div class="form group">
									<label>Ledger Type</label> <select class="form-control selectpicker" name="ledger-type">
										<option value="" selected disabled>--Ledger Type--</option>
										<c:forEach var="type" items="${ledgerTypeList}">
											<c:choose>
												<c:when test="${type.value eq ledgerType}">
													<option value="${type.value}" selected>${type.value}</option>
												</c:when>
												<c:otherwise>
													<option value="${type.value}">${type.value}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>

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
					<th>uploaded By</th>
					<th>Bank</th>
					<th>Amount</th>
					<th>Status</th>
					<th>Remarks</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(ledgerList.object) gt 0}">
					<c:forEach var="ledgerlist" items="${ledgerList.object}">
						<tr>
							<td>${ledgerlist.date}</td>
							<td>${ledgerlist.uploadedBy}</td>
							<td>${ledgerlist.bank}</td>
							<td>${ledgerlist.amount}</td>
							<td>${ledgerlist.transactionStatus}</td>
							<td>${ledgerlist.remarks}</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		<c:if test="${fn:length(ledgerList.pageList) gt 1}">
			<div class="pagination-block pull-left">
				<ul class="pagination pagination-sm no-margin pagingclass">
					<li><c:if test="${ledgerList.currentPage > 1}">
							<a href="/ledger/ledger_history?pageNo=${ledgerList.currentPage-1}&from-date=${fromDate}&to-date=${toDate}&swift-code=${bankCode}&ledger-type=${ledgerType}"
								class="pn prev mypgactive">Prev</a>
						</c:if></li>
					<c:forEach var="pagelist" items="${ledgerList.pageList}">
						<li><c:choose>
								<c:when test="${pagelist == ledgerList.currentPage}">
									<span>${pagelist}</span>
								</c:when>
								<c:otherwise>
									<a href="/ledger/ledger_history?pageNo=${pagelist}&from-date=${fromDate}&to-date=${toDate}&swift-code=${bankCode}&ledger-type=${ledgerType}" class="mypgactive">${pagelist}</a>
								</c:otherwise>
							</c:choose></li>
					</c:forEach>
					<li><c:if test="${ledgerList.currentPage + 1 <= ledgerList.lastpage}">
							<a href="/ledger/ledger_history?pageNo=${ledgerList.currentPage+1}&from-date=${fromDate}&to-date=${toDate}&swift-code=${bankCode}&ledger-type=${ledgerType}"
								class="pn next mypgactive">Next</a>
						</c:if></li>
				</ul>
			</div>
		</c:if>
	</div>
</spr:page>

<script>
	$(document).ready(function() {
		/* var today=new Date();
		document.getElementById('from-date').valueAsDate=today;
		document.getElementById('to-date').valueAsDate=today; */
	});
</script>
<script src="${pageContext.request.contextPath}/resources/js/bank.js"></script>
<script>
	$(document).ready(function() {
		init("${pageContext.request.contextPath}");
	});
</script>
<script>
	function postForm() {
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
</style>