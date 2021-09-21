<%@page import="com.mobilebanking.entity.User"%>
<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
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
.mypgactive{
	background : #2A3F54 !important;
	cursor:pointer !important;
	color:white !important;
}
</style>

<spr:page header1="Revenue Report" >
<div id="sbReport" ng-controller="reportController"  ng-init="hide=true" >
	<div class="col-md-12" >
		<div class="break"></div>
		<c:if test="${not empty message}">
   		<p class="bg-success"><c:out value="${message}" ></c:out></p>
    	</c:if>
	</div>
			
		
				<div class="header">
					<h4 class="title">Choose Filter</h4>
				
					<div id="message" ng-model="message" align="center">
			            <p style="color:red;">{{message}}</p>
			     </div>
			     <div id="message" ng-model="responseMessage" align="center">
			            <p style="color:green;"><b>{{responseMessage}}</b></p>
			     </div>
				</div>
			      <div ng-hide="hide" class="col-lg-1 col-md-1">
	                    <button align="right" type="submit" ng-click="downloadReport('EXCEL','Revenue Sharing Report','revenueReportTable')" class="btn btn-warning">
				   	              Download <span class="glyphicon glyphicon-download"></span>
				               </button>
	                        </div>
				<div class="row">
			    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="rad-info-box">
					<div class="row">
					<c:if test="${isAdmin eq true }">
							<div class="col-lg-4 col-md-4">
								<div class="form group">
									<label>Bank </label> <select class="form-control selectpicker" id="bankFilter" data-live-search="true" name="bank">
										<option value="" selected disabled>Select A Bank</option>
										<c:forEach var="newbank" items="${bankList}">
											<c:choose>
												<c:when test="${bank eq newbank.id}">
													<option value="${newbank.id}" selected>${newbank.name}</option>
												</c:when>
												<c:otherwise>
													<option value="${newbank.id}">${newbank.name}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</div>
							</div>
							</c:if>
							<div class="col-lg-3 col-md-3">
								<div class="form group">
									<label>Merchant </label> <select class="form-control selectpicker" id="merchantFilter" data-live-search="true" name="merchant">
										<option value="" selected disabled>Select A Merchant</option>
										<c:forEach var="newMerchant" items="${merchantList}">
											<c:choose>
												<c:when test="${merchant eq newMerchant.id}">
													<option value="${newMerchant.id}" selected>${newMerchant.name}</option>
												</c:when>
												<c:otherwise>
													<option value="${newMerchant.id}">${newMerchant.name}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-lg-2 col-md-2">
								<div class="form group">
									<label>From</label> <input type="date" id="fromDateFilter" class="form-control" name="fromDate" value="${fromDate}"></input>
								</div>
							</div>
							<div class="col-lg-2 col-md-2">
								<div class="form group">
									<label>To</label> <input type="date" id="toDateFilter" class="form-control" name="toDate" value="${toDate}"></input>
								</div>
							</div>
					
							<div class="col-lg-1 col-md-1">
								<div class="form group">
									<a ng-click="generateRevenueReport()"><i class="fa fa-search small" title="Search"></i></a>
								</div>
							</div>
							
					</div>
				</div>
			</div>
		</div>
     <%@include file="/WEB-INF/jsp/reports/commissionReport.jsp"%>
   <div ng-hide="hide" >
<table id="revenueReportTableView" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th colspan="4" rowspan="2">Revenue Share Report</th>
			</tr>
			<tr style="display: none;" data-tableexport-display="always">
				<th colspan="4"></th>
			</tr>
			<tr ng-if='revenueReport.fromDate != null && revenueReport.fromDate != ""'>
				<td>From Date</td>
				<td colspan="3">{{revenueReport.fromDate}}</td>
			</tr>
			<tr ng-if='revenueReport.toDate != null && revenueReport.toDate != ""'>
				<td>To Date</td>
				<td colspan="3">{{revenueReport.toDate}}</td>
			</tr>
			<tr ng-if='revenueReport.bankName != null && revenueReport.bankName != ""'>
				<td>Bank</td>
				<td colspan="3">{{revenueReport.bankName}}</td>
			</tr>
			<tr ng-if='revenueReport.merchant != null && revenueReport.merchant != ""'>
				<td>Merchant</td>
				<td colspan="3">{{revenueReport.merchant}}</td>
			</tr>
			<tr style="display: none;" data-tableexport-display="always">
				<th colspan="4"></th>
			</tr>
			<tr>
				<th colspan="4">Summary</th>
			</tr>
			<tr>
				<td>No of Transactions</td>
				<td colspan="3">{{revenueReport.totalTransactions}}</td>
			</tr>
			<tr>
				<td>Total value of Transaction(NPR)</td>
				<td colspan="3">{{revenueReport.totalTransactionAmount}}</td>
			</tr>
			<tr>
				<td>Total Commission Earned(NPR)</td>
				<td colspan="3">{{revenueReport.totalCommissionEarned}}</td>
			</tr>
			<tr style="display: none;" data-tableexport-display="always">
				<th colspan="4"></th>
			</tr>
			<tr>
				<th>Transaction Type</th>
				<th>No Of transactions</th>
				<th>Value Of transactions(NPR)</th>
				<th>Commission Earned</th>
			</tr>
		</thead>
		<tbody>
			<tr ng-repeat="revenue in revenueReport.revenueList">
				<td>{{revenue.transactionType}}</td>
				<td>{{revenue.noOfTransaction}}</td>
				<td>{{revenue.valueOfTransaction}}</td>
				<td>{{revenue.commissionEarned}}</td>
			</tr>
		</tbody>
	</table>
</div>


</div>
 

</spr:page>

<script src="${pageContext.request.contextPath}/js/Angular/report.js"></script>