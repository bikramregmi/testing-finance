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
<!-- <script src ="../js/jquery.min.js"></script> -->
<!-- <script src ="../js/bootstrap.js"></script> -->
<style>
.mypgactive {
	background: #2A3F54 !important;
	cursor: pointer !important;
	color: white !important;
}
</style>
<!-- by amrit. -->
<spr:page header1="Transaction Alert Report">
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
						<form action="/transaction_alert_report" method="get" id="filterForm">
							<c:if test="${not empty user}">
								<div class="col-lg-2 col-md-2">
									<div class="form group">
										<label>Swift Code</label> <select class="form-control selectpicker" name="swift-code" data-live-search="true">
											<option value="" selected>Swift Code</option>
											<%-- <c:choose>
									
										</c:choose> --%>

											<c:forEach var="banks" items="${bankList}">
												<option value="${banks.swiftCode}">${banks.swiftCode}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</c:if>

							<div class="col-lg-2 col-md-2">
								<div class="form group">
									<label>From</label> <input type="date" class="form-control" id="from-date" name="from-date" value="${fromDate}"></input>
								</div>
							</div>
							<div class="col-lg-2 col-md-2">
								<div class="form group">
									<label>To</label> <input type="date" class="form-control" id="to-date" name="to-date" value="${toDate}"></input>
								</div>
							</div>
							<div class="col-lg-3 col-md-3">
								<div class="form group">
									<label>Mobile Number</label> <input type="text" class="form-control" placeholder="Mobile Number" name="mobile-no" value="${mobileNo}"></input>
								</div>
							</div>
							<div class="col-lg-2 col-md-2">
								<div class="form group">
									<label>Channel Partner</label> <select class="form-control selectpicker" name="channel-partner">
										<option value="" selected disabled>Channel Partner</option>

										<c:forEach var="cp" items="${channelPartnerList}">
											<option value="${cp.uniqueCode}">${cp.name}</option>
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
					<th>Mobile No</th>
					<th>Swift Code</th>
					<th>Channel Partner</th>
					<th>SMS Status</th>
					<th>Notification Status</th>
					<th>Message</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="talist" items="${transactionAlertList.object}">
					<tr>
						<td>${talist.dateTime}</td>
						<td>${talist.mobileNumber}</td>
						<td>${talist.swiftCode}</td>
						<td>${talist.channelPartner}</td>
						<td>${talist.smsStatus}</td>
						<td>${talist.smsStatus}</td>
						<td style="text-align: left">${talist.message}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:if test="${fn:length(transactionAlertList.pageList) gt 1}">
			<div class="pagination-block pull-left">

				<ul class="pagination pagination-sm no-margin pagingclass">
					<li><c:if test="${transactionAlertList.currentPage > 1}">
							<a
								href="/transaction_alert_report?pageNo=${transactionAlertList.currentPage-1}&from-date=${fromDate}&to-date=${toDate}&mobile-no=${mobileNo}&channel-partner=${channelPartner}&swift-code=${swiftCode}"
								class="pn prev mypgactive">Prev</a>
						</c:if></li>

					<c:forEach var="pagelist" items="${transactionAlertList.pageList}">
						<li><c:choose>

								<c:when test="${pagelist == transactionAlertList.currentPage}">

									<span>${pagelist}</span>

								</c:when>
								<c:otherwise>

									<a
										href="/transaction_alert_report?pageNo=${pagelist}&from-date=${fromDate}&to-date=${toDate}&mobile-no=${mobileNo}&channel-partner=${channelPartner}&swift-code=${swiftCode}"
										class="mypgactive">${pagelist}</a>

								</c:otherwise>
							</c:choose></li>
					</c:forEach>
					<li><c:if test="${transactionAlertList.currentPage + 1 <= transactionAlertList.lastpage}">
							<a
								href="/transaction_alert_report?pageNo=${transactionAlertList.currentPage+1}&from-date=${fromDate}&to-date=${toDate}&mobile-no=${mobileNo}&channel-partner=${channelPartner}&swift-code=${swiftCode}"
								class="pn next mypgactive">Next</a>
						</c:if></li>
				</ul>
			</div>

		</c:if>
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