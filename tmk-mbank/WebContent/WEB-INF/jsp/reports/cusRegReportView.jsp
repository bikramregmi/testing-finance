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

<spr:page header1="Customer Registration Report">
	<div id="crReport" ng-controller="reportController" ng-init="hide=true">
		<div class="col-md-12">
			<div class="break"></div>
			<c:if test="${not empty message}">
				<p class="bg-success">
					<c:out value="${message}"></c:out>
				</p>
			</c:if>
		</div>
		<div class="header">
			<h4 class="title">Choose Filters</h4>

			<div id="message" ng-model="message" align="center">
				<p style="color: red;">{{message}}</p>
			</div>
			<div id="message" ng-model="responseMessage" align="center">
				<p style="color: green;">
					<b>{{responseMessage}}</b>
				</p>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="rad-info-box">
					<div class="row">
						<div class="col-lg-2 col-md-2">
							<div class="form group">
								<label for="from_date">From Date:</label> <input type="date" id="from_date" name="from_date" class="form-control" />
							</div>
						</div>
						<div class="col-lg-2 col-md-2">
							<div class="form group">
								<label for="to_date">To Date:</label> <input type="date" id="to_date" name="to_date" class="form-control" />
							</div>
						</div>


						<c:if test="${userAdmin eq true}">
							<div class="col-lg-2 col-md-2" ng-init="getAllBank()">
								<div class="form group">
									<label>Bank</label> <select class="form-control" ng-model="bank" name="bank" data-live-search="true" id="bankFilter" ng-change="getBranchByBank(bank)">
										<option value="">Select Bank</option>
										<option ng-repeat="bank in bankList" value="{{bank.id}}">{{bank.name}}</option>
									</select>
								</div>
							</div>
						</c:if>
						<div class="col-lg-2 col-md-2">
							<div class="form group">
								<label>Branch</label>
								<select class="form-control" name="branch" data-live-search="true" id="branchFilter" ng-model="branch">
									<option value="" selected>Select Branch</option>
									<option ng-repeat="branch in branchList" value="{{branch.id}}">{{branch.name}}</option>
								</select>
							</div>
						</div>
						<div class="col-lg-1 col-md-1">
							<div class="form group">
								<a ng-click="getDataForReport('CustomerRegestration')"><i class="fa fa-search small" title="Search"></i></a>
							</div>
						</div>

						<div ng-hide="hide" class="col-lg-1 col-md-1">

							<!-- <a ng-click="downloadReport('EXCEL','customerRegestration','customerRegistrationReport')"><p style="color:blue;">Download</p></i></a> -->
							<button type="submit" ng-click="downloadReport('EXCEL','customerRegestration','customerRegistrationReport')" class="btn btn-warning">
								Download <span class="glyphicon glyphicon-download"></span>
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@include file="customerRegestrationReport.jsp"%>
		<div ng-hide="hide">
			<table id="customerRegistrationReport" class="table table-striped table-bordered table-condensed">
				<thead style="font-size:15px;">
					<tr>
						<th colspan=9 style="font-size: 25px;">Customer Registration Report</th>
					</tr>
					<tr>
						<th colspan=4>From Date</th>
						<td colspan=5>{{registrationReport.fromDate}}</td>
					</tr>
					<tr>
						<th colspan=4>To Date</th>
						<td colspan=5>{{registrationReport.toDate}}</td>
					</tr>
					<tr>
						<th colspan=4>Bank Code</th>
						<td colspan=5>{{registrationReport.bankCode}}</td>
					</tr>
					<tr>
						<th colspan=4>Branch Code</th>
						<td colspan=5>{{registrationReport.branchCode}}</td>
					</tr>
					<tr>
						<th colspan=4>Total Registered Users</th>
						<td colspan=5>{{registrationReport.totalRegisteredCustomer}}</td>
					</tr>
					<tr>
						<th colspan=4>Loan A/C</th>
						<td colspan=5>{{registrationReport.loanAccount}}</td>
					</tr>
					<tr>
						<th colspan=4>Saving A/C</th>
						<td colspan=5>{{registrationReport.savingAccount}}</td>
					</tr>
					<tr>
						<th colspan=4>Current A/C</th>
						<td colspan=5>{{registrationReport.currentAccount}}</td>
					</tr>
					<tr>
						<th colspan=4>OD A/C</th>
						<td colspan=5>{{registrationReport.odAccount}}</td>
					</tr>
					<tr>
						<th colspan=9>User Status</th>
					</tr>
					<tr>
						<th colspan=4>Active User(Till Date)</th>
						<td colspan=5>{{registrationReport.activeUser}}</td>
					</tr>
					<tr>
						<th colspan=4>Blocked User(Till Date)</th>
						<td colspan=5>{{registrationReport.blockedUser}}</td>
					</tr>
					<tr style="font-size:12px;">
						<th>S No</th>
						<th>Mobile No</th>
						<th>Customer Name</th>
						<th>Registration Date</th>
						<th>A/C No</th>
						<th>A/C Type</th>
						<th>Status</th>
						<th>Bank Code</th>
						<th>Branch Code</th>
					</tr>
				</thead>
				<tbody style="font-size:11px;">
					<tr ng-repeat="customer in registrationReport.customerList|limitTo:10"">
						<td>{{$index+1}}</td>
						<td>{{customer.mobileNumber}}</td>
						<td>{{customer.fullName}}</td>
						<td>{{customer.created}}</td>
						<td><span ng-repeat="account in customer.accountDetail"> &#8203;{{account.accountNumber}}<span ng-if="customer.accountDetail.length != $index+1">,
							</span>
						</span></td>
						<td><span ng-repeat="account in customer.accountDetail"> {{account.accountType}}<span ng-if="customer.accountDetail.length != $index+1">, </span>
						</span></td>
						<td>{{customer.status}}</td>
						<td>{{customer.bankCode}}</td>
						<td>{{customer.bankBranchCode}}</td>
					</tr>
				</tbody>
			</table>
		</div>

	</div>
</spr:page>

<script src="${pageContext.request.contextPath}/js/Angular/report.js"></script>