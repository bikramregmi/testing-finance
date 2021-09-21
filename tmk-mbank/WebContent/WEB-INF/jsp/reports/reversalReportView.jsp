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

<spr:page header1="Reversal Transaction Report" >
<div id="rtReport" ng-controller="reportController" ng-init="hide=true">
	<div class="col-md-12" >
		<div class="break"></div>
		<c:if test="${not empty message}">
   		<p class="bg-success"><c:out value="${message}" ></c:out></p>
    	</c:if>
	</div>
			
		
				<div class="header">
					<h4 class="title">Choose Filters</h4>
					<div id="message" ng-model="message" align="center">
			            <p style="color:red;">{{message}}</p>
			     </div>
			     <div id="message" ng-model="responseMessage" align="center">
			            <p style="color:green;"><b>{{responseMessage}}</b></p>
			     </div>
				</div>
			<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="rad-info-box">
					<div class="row">
							<div class="col-lg-2 col-md-2">
								<div class="form group">
									<label for="from_date">From Date:</label> <input type="date" id="fromDateFilter" name="from_date" class="form-control" />
								</div>
							</div>
							<div class="col-lg-2 col-md-2">
								<div class="form group">
									<label for="to_date">To Date:</label> <input type="date" id="toDateFilter" name="to_date" class="form-control" />
								</div>
							</div>
							
					     	<c:if test="${isAdmin eq true}"> 
									<div class="col-lg-2 col-md-2">
										<div class="form group">
											<label>Bank</label> <select class="form-control selectpicker" name="bank" data-live-search="true" id="bankFilter">
												<option value="" >Select Bank</option> 
												<c:if test="${fn:length(bankList) gt 0}">
												<c:forEach var="bnk" items="${bankList}">
															<option value="${bnk.id}">${bnk.name}</option>
													
												</c:forEach>
												</c:if>
											</select>
										</div>
									</div>
								 </c:if> 
						
							
							
							<div class="col-lg-1 col-md-1">
								<div class="form group">
									<a ng-click="reversedTransactionReport()"><i class="fa fa-search small" title="Search"></i></a>
								</div>
							</div>
							
						       <div ng-hide="hide" class="col-lg-1 col-md-1">
	                            
                                  <!-- <a ng-click="downloadReport('EXCEL','customerRegestration','customerRegistrationReport')"><p style="color:blue;">Download</p></i></a> -->
	                            <button type="submit" ng-click="downloadReport('EXCEL','reversedReport','reversalReportTable')" class="btn btn-warning">
					              Download <span class="glyphicon glyphicon-download"></span>
				               </button>
	                        </div>
					</div>
				</div>
			</div>
		</div>
            <%@include file="financialTransactionReport.jsp"%>
	<div ng-hide="hide" >
	
		<table id="reversalReportTableView" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th colspan="7">Reverse Transaction Report</th>
			</tr>
			<tr>
				<th colspan="2">From Date</th>
				<th colspan="5">{{reversalReport.fromDate}}</th>
			</tr>
			<tr>
				<th colspan="2">To Date</th>
				<th colspan="5">{{reversalReport.toDate}}</th>
			</tr>
			<tr>
				<th colspan="2">Bank</th>
				<th colspan="5">{{reversalReport.bank}}</th>
			</tr>
			<tr>
				<th>Mobile Number</th>
				<th>A/C Number</th>
				<th>Bank</th>
				<th>Amount</th>
				<th>Transaction Type</th>
				<th>Ref. Stan</th>
				<th>Date</th>
			</tr>
		</thead>
		<tbody>
			<tr ng-repeat="transaction in reversalReport.transactionList">
				<td>{{transaction.mobileNo}}</td>
				<td>{{transation.accountNo}}</td>
				<td>{{transaction.bank}}</td>
				<td>{{transaction.amount}}</td>
				<td>{{transaction.transactionType}}</td>
				<td>&#8203;{{transaction.refstan}}</td>
				<td>{{transaction.date}}</td>
			</tr>
		</tbody>
		</table>
	</div>
</div>
</spr:page>
<script src="${pageContext.request.contextPath}/js/Angular/report.js"></script>