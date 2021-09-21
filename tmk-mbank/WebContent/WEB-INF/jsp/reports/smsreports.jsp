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
<link href="/css/radinfo.css" rel="stylesheet"></link>
<link rel="stylesheet" href="../css/jquery.dataTables.min.css">
<!-- <script src ="../js/jquery.min.js"></script> -->
<!-- <script src ="../js/bootstrap.js"></script> -->
<style>
.mypgactive{
	background : #2A3F54 !important;
	cursor:pointer !important;
	color:white !important;
}
</style>
<spr:page header1="SMS Report" >
<!-- <a class="btn btn-primary"style="float:right;" href="/getsmsreport">Download Reports</a> -->


	<div class="col-md-12">
		<div class="break"></div>
		<c:if test="${not empty message}">
   		<p class="bg-success"><c:out value="${message}" ></c:out></p>
	</c:if>
	
	<!-- added by amrit -->
	<c:if test="${not empty user}">
   		
	
	<div class="dropdown pull-right" ng-controller="reportController">
			<a class="btn btn-info" data-toggle="dropdown" ng-click="getDataForReport('smsLogReport')"><i class="fa fa-download small" title="Download Excel Report"></i></a>
			<ul class="dropdown-menu">
				<li style="text-align: center; color: grey;">SMS Log Report</li>
				<li class="divider new-divider"></li>
				<li><a href="" ng-click="downloadReport('EXCEL','SMS Business Report','sms_log_report')" ng-show="reportDataLoad"> <i class="fa fa-file-excel-o fa-2x" style="color: green;"></i> Excel
				</a></li>
				<li ng-hide="reportDataLoad" style="text-align: center;"><i class="fa fa-spinner fa-2x fa-spin"></i></li>
			</ul>
		        <%@include file="/WEB-INF/jsp/reports/downloadSmsBusinessReport.jsp"%>
		</div>
		</c:if>
	<!-- end of the added  -->
	<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="rad-info-box">
					<div class="row">
						<form action="/getSmsReport" method="get" id="filterForm">
							<div class="col-lg-3 col-md-3">
								<div class="form group">
									<label>From</label>
									<input type="date" class="form-control" id="fromDate" name="fromDate" value="${fromDate}" ng-model="fromDate"></input>
								</div>
							</div>
							<div class="col-lg-3 col-md-3">
								<div class="form group">
									<label>To</label>
									<input type="date" class="form-control" name="toDate" id="toDate" value="${toDate}" ng-model="toDate"></input>
								</div>
							</div>
							<div class="col-lg-3 col-md-3">
								<div class="form group">
									<label>Mobile Number</label>
									<input type="text" class="form-control" placeholder="Mobile Number" name="mobileNo" value="${mobileNo}"></input>
								</div>
							</div>
							<div class="col-lg-2 col-md-2">
								<div class="form group">
									<label>SMS Type</label>
									<select class="form-control selectpicker" name="smsType">
										<option value="" selected disabled>SMS Type</option>
										<c:choose>
										<c:when test="${smsType eq 'true'}">
											<option value="true" selected>Incoming</option>
											<option value="false">Outgoing</option>
										</c:when>
										<c:when test="${smsType eq 'false'}">
											<option value="true">Incoming</option>
											<option value="false" selected>Outgoing</option>
										</c:when>
										<c:otherwise>
											<option value="true">Incoming</option>
											<option value="false">Outgoing</option>
										</c:otherwise>
										</c:choose>
									</select>
								</div>
							</div>
							<div class="col-lg-1 col-md-1">
								<div class="form group">
									<a onclick="postForm()"><i class="fa fa-search small"
										title="Search"></i></a>
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
					<th>SMS For</th>
					<th>SMS From</th>
					<th>Message</th>
					<th>Status</th>
					<th>SMS Type</th>
				</tr>
			</thead>
			<tbody>
					<c:forEach var="smsLog" items="${smslogList.object}">
						<tr>
							<td>${smsLog.deliveredDate}</td>
							<td>${smsLog.smsFor}</td>
							<td>${smsLog.smsForUser}</td>
							<td>${smsLog.smsFromUser}</td>
							<td style="text-align:left">${smsLog.message}</td>
							<td>${smsLog.status}</td>
							<c:if test="${smsLog.isIncommingSms eq 'true'}">
								<td>Incoming</td>
							</c:if>
							<c:if test="${smsLog.isIncommingSms eq 'false'}">
								<td>Outgoing</td>
							</c:if>
							
							
						</tr>
					</c:forEach>
			</tbody>
		</table>
		<c:if test="${fn:length(smslogList.pageList) gt 1}">
			<div class="pagination-block pull-left">

				<ul class="pagination pagination-sm no-margin pagingclass">
					<li><c:if test="${smslogList.currentPage > 1}">
							<a href="/getSmsReport?pageNo=${smslogList.currentPage-1}&fromDate=${fromDate}&toDate=${toDate}&mobileNo=${mobileNo}&smsType=${smsType}"
								class="pn prev mypgactive">Prev</a>
						</c:if></li>

					<c:forEach var="pagelist" items="${smslogList.pageList}">
						<li><c:choose>

								<c:when test="${pagelist == smslogList.currentPage}">

									<span>${pagelist}</span>

								</c:when>
								<c:otherwise>

									<a href="/getSmsReport?pageNo=${pagelist}&fromDate=${fromDate}&toDate=${toDate}&mobileNo=${mobileNo}&smsType=${smsType}" class="mypgactive">${pagelist}</a>

								</c:otherwise>
							</c:choose></li>
					</c:forEach>
					<li><c:if test="${smslogList.currentPage + 1 <= smslogList.lastpage}">
							<a href="/getSmsReport?pageNo=${smslogList.currentPage+1}&fromDate=${fromDate}&toDate=${toDate}&mobileNo=${mobileNo}&smsType=${smsType}"
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
	document.getElementById('fromDate').valueAsDate=today;
	document.getElementById('toDate').valueAsDate=today;
});
</script> -->
<script src="${pageContext.request.contextPath}/js/Angular/report.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/bank.js"></script>
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
table{
 table-layout: fixed; 
 font-size: 12px;
}
td{
 word-wrap: break-word;
}
</style>