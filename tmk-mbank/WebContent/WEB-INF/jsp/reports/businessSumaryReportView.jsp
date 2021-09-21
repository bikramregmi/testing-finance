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

<spr:page header1="Business Summary Report" >
<div id="sbReport" ng-controller="reportController" ng-init="hide=true">
	<div class="col-md-12" >
		<div class="break"></div>
		<c:if test="${not empty message}">
   		<p class="bg-success"><c:out value="${message}" ></c:out></p>
    	</c:if>
	</div>
			
		
				<div class="header">
					<h4 class="title">Click to view report</h4>
				
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
				
							<div class="col-lg-1 col-md-1">
							
								<div class="form group">
									<Button ng-click="getDataForReport('businessSummary')" style="color:green, background:blue;">View Report</i></a>
								</div>
						
							</div>
						       <div ng-hide="hide" class="col-lg-1 col-md-1">

	                          <button type="submit" ng-click="downloadReport('EXCEL','businessSummary','businessSummaryReportTable')" class="btn btn-warning">
					              Download <span class="glyphicon glyphicon-download"></span>
				               </button>
	                        </div>
					</div>
				</div>
			</div>
		</div>
       <%@include file="/WEB-INF/jsp/reports/businessSummaryReport.jsp"%>
   <div ng-hide="hide" >
	
	<table id="businessSummaryReportTableView" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th colspan="{{businessSummaryReport.serviceCategoryList.length + 7}}">Business Summary Report</th>
			</tr>
			</thead>
			<tbody>
			<tr>
				<th colspan="{{businessSummaryReport.serviceCategoryList.length + 7}}">{{businessSummaryReport.date}}</th>
			</tr>
			<tr>
				<th colspan="{{businessSummaryReport.serviceCategoryList.length + 7}}"></th>
			</tr>
			<tr>
				<th colspan=3>Duration</th>
				<th colspan="{{businessSummaryReport.serviceCategoryList.length + 4}}">Active Registration</th>
			</tr>
			<tr>
				<td colspan=3>Till Date</td>
				<td colspan="{{businessSummaryReport.serviceCategoryList.length + 4}}">{{businessSummaryReport.activeRegistration.tillDate}}</td>
			</tr>
			<tr>
				<td colspan=3>Last 30 Days</td>
				<td colspan="{{businessSummaryReport.serviceCategoryList.length + 4}}">{{businessSummaryReport.activeRegistration.thirtyDays}}</td>
			</tr>
			<tr>
				<td colspan=3>Last 7 Days</td>
				<td colspan="{{businessSummaryReport.serviceCategoryList.length + 4}}">{{businessSummaryReport.activeRegistration.sevenDays}}</td>
			</tr>
			<tr>
				<td colspan=3>Last 1 Day</td>
				<td colspan="{{businessSummaryReport.serviceCategoryList.length + 4}}">{{businessSummaryReport.activeRegistration.oneDay}}</td>
			</tr>
			<tr>
				<th colspan="{{businessSummaryReport.serviceCategoryList.length + 7}}"></th>
			</tr>
			<tr>
				<th colspan=3>Duration</th>
				<th colspan="{{businessSummaryReport.serviceCategoryList.length + 4}}">Blocked Registration</th>
			</tr>
			<tr>
				<td colspan=3>Till Date</td>
				<td colspan="{{businessSummaryReport.serviceCategoryList.length + 4}}">{{businessSummaryReport.blockedRegistration}}</td>
			</tr>
			<tr>
				<th colspan="{{businessSummaryReport.serviceCategoryList.length + 7}}"></th>
			</tr>
			<tr>
				<th colspan=3>Duration</th>
				<th colspan="{{businessSummaryReport.serviceCategoryList.length/2 + 2}}">Transaction Volume</th>
				<th colspan="{{businessSummaryReport.serviceCategoryList.length/2 + 2}}">Transaction Value</th>
			</tr>
			<tr>
				<td colspan=3>Till Date</td>
				<td colspan="{{businessSummaryReport.serviceCategoryList.length/2 + 2}}">{{businessSummaryReport.transaction.transactionVolume.tillDate}}</td>
				<td colspan="{{businessSummaryReport.serviceCategoryList.length/2 + 2}}">{{businessSummaryReport.transaction.transactionValue.tillDate}}</td>
			</tr>
			<tr>
				<td colspan=3>Last 30 Days</td>
				<td colspan="{{businessSummaryReport.serviceCategoryList.length/2 + 2}}">{{businessSummaryReport.transaction.transactionVolume.thirtyDays}}</td>
				<td colspan="{{businessSummaryReport.serviceCategoryList.length/2 + 2}}">{{businessSummaryReport.transaction.transactionValue.thirtyDays}}</td>
			</tr>
			<tr>
				<td colspan=3>Last 7 Days</td>
				<td colspan="{{businessSummaryReport.serviceCategoryList.length/2 + 2}}">{{businessSummaryReport.transaction.transactionVolume.sevenDays}}</td>
				<td colspan="{{businessSummaryReport.serviceCategoryList.length/2 + 2}}">{{businessSummaryReport.transaction.transactionValue.sevenDays}}</td>
			</tr>
			<tr>
				<td colspan=3>Last 1 Day</td>
				<td colspan="{{businessSummaryReport.serviceCategoryList.length/2 + 2}}">{{businessSummaryReport.transaction.transactionVolume.oneDay}}</td>
				<td colspan="{{businessSummaryReport.serviceCategoryList.length/2 + 2}}">{{businessSummaryReport.transaction.transactionValue.oneDay}}</td>
			</tr>
			<tr>
				<th colspan="{{businessSummaryReport.serviceCategoryList.length + 7}}"></th>
			</tr>
			<tr>
				<th colspan=2>Duration</th>
				<th>Balance Check</th>
				<th>Mini-Statement</th>
				<th>Fund Transfer</th>
				<th>Cheque Book Request</th>
				<th>Cheque Block</th>
				<th ng-repeat="category in businessSummaryReport.serviceCategoryList">
					{{category.name}}
				</th>
			</tr>
			<tr>
				<td colspan=2>Till Date</td>
				<td>{{businessSummaryReport.balanceCheck.tillDate}}</td>
				<td>{{businessSummaryReport.miniStatement.tillDate}}</td>
				<td>{{businessSummaryReport.fundTransfer.tillDate}}</td>
				<td>{{businessSummaryReport.chequeBook.tillDate}}</td>
				<td>{{businessSummaryReport.chequeBlock.tillDate}}</td>
				<td ng-repeat="category in businessSummaryReport.serviceCategoryList">
					{{category.tillDate}}
				</td>
			</tr>
			<tr>
				<td colspan=2>Last 30 Days</td>
				<td>{{businessSummaryReport.balanceCheck.thirtyDays}}</td>
				<td>{{businessSummaryReport.miniStatement.thirtyDays}}</td>
				<td>{{businessSummaryReport.fundTransfer.thirtyDays}}</td>
				<td>{{businessSummaryReport.chequeBook.thirtyDays}}</td>
				<td>{{businessSummaryReport.chequeBlock.thirtyDays}}</td>
				<td ng-repeat="category in businessSummaryReport.serviceCategoryList">
					{{category.thirtyDays}}
				</td>
			</tr>
			<tr>
				<td colspan=2>Last 7 Days</td>
				<td>{{businessSummaryReport.balanceCheck.sevenDays}}</td>
				<td>{{businessSummaryReport.miniStatement.sevenDays}}</td>
				<td>{{businessSummaryReport.fundTransfer.sevenDays}}</td>
				<td>{{businessSummaryReport.chequeBook.sevenDays}}</td>
				<td>{{businessSummaryReport.chequeBlock.sevenDays}}</td>
				<td ng-repeat="category in businessSummaryReport.serviceCategoryList">
					{{category.sevenDays}}
				</td>
			</tr>
			<tr>
				<td colspan=2>Last 1 Day</td>
				<td>{{businessSummaryReport.balanceCheck.oneDay}}</td>
				<td>{{businessSummaryReport.miniStatement.oneDay}}</td>
				<td>{{businessSummaryReport.fundTransfer.oneDay}}</td>
				<td>{{businessSummaryReport.chequeBook.oneDay}}</td>
				<td>{{businessSummaryReport.chequeBlock.oneDay}}</td>
				<td ng-repeat="category in businessSummaryReport.serviceCategoryList">
					{{category.oneDay}}
				</td>
			</tr>
			<tr>
				<th colspan="{{businessSummaryReport.serviceCategoryList.length + 7}}"></th>
			</tr>
			<tr>
				<th colspan=3>Duration</th>
				<th colspan="{{businessSummaryReport.serviceCategoryList.length + 4}}">Revenue Share Earning(NPR)</th>
			</tr>
			<tr>
				<td colspan=3>Till Date</td>
				<td colspan="{{businessSummaryReport.serviceCategoryList.length + 4}}">{{businessSummaryReport.revenue.oneDay}}</td>
			</tr>
			<tr>
				<td colspan=3>Last 30 Days</td>
				<td colspan="{{businessSummaryReport.serviceCategoryList.length + 4}}">{{businessSummaryReport.revenue.thirtyDays}}</td>
			</tr>
			<tr>
				<td colspan=3>Last 7 Days</td>
				<td colspan="{{businessSummaryReport.serviceCategoryList.length + 4}}">{{businessSummaryReport.revenue.sevenDays}}</td>
			</tr>
			<tr>
				<td colspan=3>Last 1 Day</td>
				<td colspan="{{businessSummaryReport.serviceCategoryList.length + 4}}">{{businessSummaryReport.revenue.oneDay}}</td>
			</tr>
		</tbody>
	</table>
</div>


</div>
 

</spr:page>
<script src="${pageContext.request.contextPath}/js/Angular/report.js"></script>
