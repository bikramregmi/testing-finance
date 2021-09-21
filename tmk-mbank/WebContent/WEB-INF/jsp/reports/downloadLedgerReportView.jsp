<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
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
<link href="../css/jquery.dataTables.min.css" rel="stylesheet">
<style>
.add-on .input-group-btn>.btn {
	border-left-width: 0;
	left: -2px;
	-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
	box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
}
/* stop the glowing blue shadow */
.add-on .form-control:focus {
	box-shadow: none;
	-webkit-box-shadow: none;
	border-color: #cccccc;
}

.form-control {
	width: 20%
}

.navbar-nav>li>a {
	border-right: 1px solid #ddd;
	padding-bottom: 15px;
	padding-top: 15px;
}

.navbar-nav:last-child {
	border-right: 0
}

.mypgactive {
	background: #2A3F54 !important;
	cursor: pointer !important;
	color: white !important;
}

table {
	font-size: 12px;
}

td {
	word-wrap: break-word;
}
</style>
<spr:page header1="View Ledger History">
<div id="viewledgerReport" ng-controller="ledgerHistory">
	<div class="col-md-12">
		<div class="break"></div>
		
			<p class="bg-success">
				<c:out value="${message}"></c:out>
			</p>

	<%-- 	<div class="dropdown pull-right" ng-controller="reportController">
			<a class="btn btn-info" data-toggle="dropdown" ng-click="getDataForReport('CustomerRegestration')"><i class="fa fa-download small" title="View As PDF"></i></a>
			<ul class="dropdown-menu">
				<li style="text-align: center; color: grey;">Customer Registration</li>
				<li class="divider new-divider"></li>
				<li><a href="" ng-click="downloadReport('EXCEL','customerRegestration','customerRegistrationReport')" ng-show="reportDataLoad"> <i class="fa fa-file-excel-o fa-2x" style="color: green;"></i> Excel
				</a></li>
				<li ng-hide="reportDataLoad" style="text-align: center;"><i class="fa fa-spinner fa-2x fa-spin"></i></li>
			</ul>
			<%@include file="/WEB-INF/jsp/reports/customerRegestrationReport.jsp"%>
		</div> --%>
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="rad-info-box">
					<div class="row">
						<form action="/customer/list" method="get" id="filterForm">
							
							<div class="col-lg-2 col-md-2">
								<div class="form group">
									<label for="from_date">From Date:</label> <input type="date" id="from_date" name="from_date" class="form-control" ng-model="fromDate"/>
								</div>
							</div>
							<div class="col-lg-2 col-md-2">
								<div class="form group">
									<label for="to_date">To Date:</label> <input type="date" id="to_date" name="to_date" class="form-control" ng-model="toDate" />
								</div>
							</div>
						
							<div class="col-lg-2 col-md-2">
										<div class="form group">
											<label>Bank</label> <select class="form-control selectpicker" name="bank" data-live-search="true" id="bankFilter" ng-model="bank">
											
												<c:forEach var="bnk" items="${bankList}">
													<c:choose>
														<c:when test="${bank eq bnk.swiftCode}">
															<option value="${bnk.swiftCode}" selected>${bnk.name}</option>
														</c:when>
														<c:otherwise>
															<option value="${bnk.swiftCode}">${bnk.name}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select>
										</div>
									</div>
							
									<div class="col-lg-2 col-md-2">
										<div class="form group">
											<label>Ledger Type</label> <select class="form-control selectpicker" name="ledgerType" data-live-search="true" id="ledgerTypeFilter" ng-model="ledgerType">
											
												<c:forEach var="ltype" items="${ledgerTypeList}">
													<option value="${ltype}" selected>${ltype}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									
							<div class="col-lg-1 col-md-1">
								<div class="form group">
									<a ng-click="loadLedger(bank,fromDate,toDate,ledgerType,0)"><i class="fa fa-search small" title="Search"></i></a>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
         <div id="ledgerReport" ng-repeat="(key,value) in ledgerReport"  >
             <table id="loadFundReport" class="table table-border">
		      <thead>
			<tr>
				<th colspan=5 rowspan=2>mBank Bank Account Balance</th>
			</tr>
			<tr>
				<th rowspan=0></th>
			</tr>
			<tr>
				<th colspan=2>From Date</th>
				<th colspan=3>{{fromDatePara}}</th>
			</tr>
			<tr>
				<th colspan=2>To Date</th>
				<th colspan=3>{{toDatePara}}</th>
			</tr>
			<tr>
				<th colspan=2>Bank</th>
				<th colspan=3>{{bankPara}}</th>
			</tr>
			<tr>
				<th colspan=2>BankCode</th>
				<th colspan=3>{{bankPara}}</th>
			</tr>
			<tr>
			     <th>sn</th>
			     <th>Date</th>
			     <th>Account Head</th>
				 <th>Account Number</th>
				 <th>Amount</th>
				 <th>Status</th>
				 <th>Remarks</th>
				
			</tr>
		</thead>
		<tbody>
		
			 <tr  ng-repeat="ledger in value.object"">
			   <td>{{ledger.date}}</td>
				<td>{{ledger.bankName}}</td>
				<td>{{ledger.bankCode}}</td>
				<td>{{ledger.amount}}</td>
				<td>{{ledger.status}}</td>
				
			</tr> 
		</tbody>
	</table>
         </div>
       
<%-- 
		<c:if test="${fn:length(customerList.pageList) gt 1}"> --%>
		<div ng-if="value.pageList.length > 1)">
			<div class="pagination-block pull-left">

				<ul class="pagination pagination-sm no-margin pagingclass">
					<li><div ng-if="value.currentPage > 1">
							<a ng-click="loadLedger(bankPara,fromDatePara,toDatePara,ledgerTypePara,value.currentPage-1)" class="pn prev mypgactive">Prev</a>
						</div></li>

					<div ng-repeat="x in value.pageList">
						<li>

								<div ng-if="x == customerList.currentPage">

									<span>{{x}</span>

								</div>
								<div ng-if="x != customerList.currentPage">

									<a ng-click="loadLedger(bankPara,fromDatePara,toDatePara,ledgerTypePara,x)" class="mypgactive">${x}</a>

								</div>
						</li>
					</div>
					<li><div  ng-if="value.currentPage + 1 <= value.lastpage}">
							<a ng-click="loadLedger(bankPara,fromDatePara,toDatePara,ledgerTypePara,value.currentPage+1)" class="pn next mypgactive">Next</a>
						</div></li>
				</ul>
			</div>

		</div>
	</div>
	</div>
</spr:page>
<script src="${pageContext.request.contextPath}/resources/js/customer.js"></script>
<script>
	$(document).ready(function() {
		init("${pageContext.request.contextPath}");
	});
</script>




<script>
$(document).ready(function(){
	var today=new Date();
	
	document.getElementById('from_date').valueAsDate = today;
	document.getElementById('to_date').valueAsDate = today;
	
});
</script>
<script>
	function postForm() {
		$("#filterForm").submit();
		
	}
</script>
<script src="${pageContext.request.contextPath}/js/Angular/ledgerHistory.js"></script>