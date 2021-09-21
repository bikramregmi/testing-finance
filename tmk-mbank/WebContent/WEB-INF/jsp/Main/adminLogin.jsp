<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" >
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="/css/radinfo.css" rel="stylesheet"></link>
<link href="/css/pagination.css" rel="stylesheet"></link>
<input id="firstLogin" type="hidden" value="${firstLogin}" />
<spr:page header1="DashBoard">

	<!-- added by amrit -->
	<div class="dropdown pull-right" ng-controller="reportController">
		<a class="btn btn-info" data-toggle="dropdown" ng-click="getDataForReport('businessSummary')"><i class="fa fa-download small" title="Download Business Summary"></i></a>
		<ul class="dropdown-menu">
			<li style="text-align: center; color: grey;">Download Report</li>
			<li class="divider new-divider"></li>
			<li><a href="" ng-click="downloadReport('EXCEL','businessSummary','businessSummaryReportTable')" ng-show="reportDataLoad"> <i
					class="fa fa-file-excel-o fa-2x" style="color: green;"></i> Business Summary Report
			</a></li>
			<li ng-hide="reportDataLoad" style="text-align: center;"><i class="fa fa-spinner fa-2x fa-spin"></i></li>
			
			<li><a href="" data-toggle="modal" data-target="#filterModal" ng-show="reportDataLoad"> <i
					class="fa fa-file-excel-o fa-2x" style="color: green;"></i> Bankwise Electronic Report
			</a></li>
			<li ng-hide="reportDataLoad" style="text-align: center;"><i class="fa fa-spinner fa-2x fa-spin"></i></li>
			
		</ul>
		<%@include file="/WEB-INF/jsp/reports/businessSummaryReport.jsp"%>
	
		
		<!-- Modal for the date and bank filter-->
			
	   <div id="filterModal" class="modal fade" role="dialog" tabindex="-1">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">Choose Filters</h4>
					<button  type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<div id="message" ng-model="message" align="center">
			            <p style="color:red;">{{message}}</p>
			     </div>
				</div>
				<div class="modal-body">
				<div class="input-group">
				<label class="col-md-6 control-label">Select Bank:</label>
					 <select ng-model="bank" class="form-control">
						<option selected disabled value="">Select Bank</option>
						<c:if test="${fn:length(bankList) gt 0}">
							<c:forEach items="${bankList}" var="bank">
								<option value="${bank.id}">${bank.name}</option>
							</c:forEach>
						</c:if>
					</select>
					
				</div>
				
				
			       <div class="input-group">
				       <label class="col-md-6 control-label">Report Type:</label>
					 <select id="reportType" ng-model="reportType" class="form-control" onchange="typeChange()" required="true">
					 <option selected disabled value="">--Select Report type--</option>
						<option selected value="ebanking">Monthly E-banking Report</option>
						<option selected value="etransaction">E-transaction Report</option>
						
					</select>
				</div>
				
				<div  class="input-group" >
					<label class="col-md-6 control-label">Select Date:</label>
				<!--   <input id="monthYear" type="month" name="monthYear" ng-model="monthYear" class="form-control" >	
				  <input id="date" type="date" name="date" ng-model="date" class="form-control" ></input> -->
				  <input type="text" id="nepaliDate"  ng-model="nepaliDate" class="nepali-calendar"/>
                  </div> 
      
			        
					<div class="modal-footer">
						<!-- <button type="button" ng-click="getReport(bank,monthYear,date,reportType)" class="btn btn-danger btn-modal">Load {{reportType}} </button>
				 -->	
				 <button type="button" ng-click="getReport(bank,nepaliDate,reportType)" class="btn btn-danger btn-modal">Load {{reportType}} </button>
				
				 </div>
			
				</div>

			</div>
		</div>
	</div>
			<%@include file="/WEB-INF/jsp/reports/downloadeBankingReport.jsp"%>
			<%@include file="/WEB-INF/jsp/reports/downloadeTransactionReport.jsp"%>
	</div>
	<ul class="nav nav-tabs">
		<li class="${overall}" style="color: #0000FF"><a href="/">Overall</a></li>
		<li class="${daily }" style="color: #0000FF"><a href="${pageContext.request.contextPath}/today_dashboard">Daily</a></li>

	</ul>
	<!-- end added by amrit -->
	<div class="row tile_count">
		<div class="row">
			<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
				<div class="rad-info-box rad-txt-primary">
					<a href="/customer/list"> <i class="fa fa-user large"></i> <span class="heading">Customer</span> <span class="value"><span>${totalCustomer}</span></span>
					</a>
				</div>
			</div>

			<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
				<div class="rad-info-box rad-txt-danger">
					<a href="/customer/inactive/list"> <i class="fa fa-user large"></i> <span class="heading">INACTIVE CUSTOMER</span> <span class="value"><span>${inactiveCustomer}</span></span>
					</a>
				</div>
			</div>

			<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
				<div class="rad-info-box rad-txt-success">
					<i class="fa fa-money large"></i> <span class="heading">TRANSACTION</span> <span class="value"><span>${totalTransaction}</span></span>
				</div>
			</div>

			<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
				<div class="rad-info-box rad-txt-info">
					<i class="fa fa-money large"></i> <span class="heading">Total balance enquiry</span> <span class="value"><span>${totalBalanceEnquiry}</span></span>
				</div>
			</div>

			<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
				<div class="rad-info-box rad-txt-info">
					<i class="fa fa-table large"></i> <span class="heading">Mini-statement check</span> <span class="value"><span>${totalMiniStatement}</span></span>
				</div>
			</div>

			<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12" ng-mouseenter="options=true" ng-mouseleave="options=false">
				<div class="rad-info-box rad-txt-info" ng-hide="options">
					<i class="fa fa-envelope large"></i> <span class="heading">TOTAL SMS</span> <span class="value"><span>${totalCount}</span></span>
				</div>
				<div class="rad-info-box rad-txt-info" ng-show="options">
					<i class="fa fa-envelope large"></i> <span class="heading"><spaceh>INCOMING OUTGOING</spaceh></span> <span class="value word-space-value"><span><spacev>${totalIncoming}
							${totalOutgoing}</spacev></span></span>
				</div>
			</div>

			<c:forEach var="service" items="${serviceList}">
				<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
					<div class="rad-info-box">
						<img src="/mbank/serviceIcon/${service.icon}" class="icon"></img> <span class="heading">${service.service}</span> <span class="value"><span>${service.count}</span></span>
					</div>
				</div>
			</c:forEach>
		</div>

	</div>

	<input type="hidden" ng-model="saveUrl" ng-init="saveUrl='${pageContext.request.contextPath}/changeuserpassword'" />
	<input type="hidden" ng-model="username" ng-init="username='${username}'" />

	<!-- Modal -->
	<div id="myfirstLoginModal" class="modal fade" role="dialog" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Change Password</h4>
				</div>
				<div class="modal-body" ng-hide="passwordChangeSuccess">
					<div class="input-group col-lg-12">
						<h4>{{myMessage}}</h4>
						<label>Old Password</label> <input type="password" class="form-control" ng-model="oldPassword" placeholder="Old Password" autocomplete="current-password" />
						<h6 style="color: red">{{userError.oldPassword}}</h6>

					</div>
					<div class="input-group col-lg-12">
						<label>New Password</label> <input type="password" class="form-control" ng-model="password" placeholder="New Password" autocomplete="new-password" />
						<h6 style="color: red">{{userError.password}}</h6>
					</div>
					<div class="input-group col-lg-12">
						<label>Re-Enter Password</label> <input type="password" class="form-control" ng-model="repassword" placeholder="Re-Enter Password" autocomplete="new-password" />
						<h6 style="color: red">{{userError.repassword}}</h6>
					</div>
				</div>
				<div class="modal-body" ng-show="passwordChangeSuccess">
					<p style="color: black; font-size: 20px; text-align: center;">Password Changed Successfully.</p>
					<p style="color: black; font-size: 12px; text-align: center;">
						You will be logged out in <span class="fade in" style="font-size: 20px; font-weight: bold;" ng-bind="logoutSecCounter"></span> seconds. Please use your new password to
						login.
					</p>
				</div>
				<div class="modal-footer" ng-hide="changePasswordLoading">
					<button type="button" ng-click="changePassword(saveUrl)" class="btn btn-primary btn-modal pull-left">Change Password</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
				<div class="modal-footer" ng-show="changePasswordLoading">
					<i class="fa fa-cog fa-spin fa-2x"></i>
				</div>
			</div>
		</div>
	</div>
</spr:page>
<script>
$(document).ready(typeChange());
function typeChange(){
	$('#nepaliDate').nepaliDatePicker({
		npdMonth: true,
		npdYear: true,
		npdYearCount: 21
	});
   /*    if($('#reportType').val()=="ebanking"){
	$('#date').hide();
	$('#monthYear').show();
        }
    else{
	
	$('#monthYear').hide();
	$('#date').show();
    } */
}

</script>
<script>
	if ($('#firstLogin').val() == 'true') {
		$('#myfirstLoginModal').modal({
			backdrop : 'static',
			keyboard : false,
			show : true
		});
	}
</script>
<script src="${pageContext.request.contextPath}/js/Angular/report.js"></script>