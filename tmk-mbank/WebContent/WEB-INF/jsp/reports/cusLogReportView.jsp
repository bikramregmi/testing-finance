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

<spr:page header1="Customer Log Report" >
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
					
						
				
							<c:choose>
								<c:when test="${userAdmin eq true}">
									<div class="col-lg-2 col-md-2">
										<div class="form group">
											<label>Bank</label> <select class="form-control selectpicker" name="bank" data-live-search="true" id="bankFilter"  ng-model="bank" ng-change="getCustomerList(fromDate,toDate,bank,branch)">
												<option value="" selected disabled>Select Bank</option>
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
								</c:when>
								<c:when test="${isBank eq true}">
									<div class="col-lg-2 col-md-2">
										<div class="form group">
											<label>Branch</label> <select class="form-control selectpicker" name="branch" data-live-search="true" ng-model="branch" ng-change="getCustomerList(fromDate,toDate,bank,branch)">
												<option value="" selected disabled>Select Branch</option>
												<c:forEach var="brnch" items="${branchList}">
													<c:choose>
														<c:when test="${branch eq brnch.branchCode}">
															<option value="${brnch.branchCode}" selected>${brnch.branchCode}--${brnch.name}</option>
														</c:when>
														<c:otherwise>
															<option value="${brnch.branchCode}">${brnch.branchCode}--${brnch.name}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select>
										</div>
									</div>
								</c:when>
							</c:choose>
							
									<div class="col-lg-2 col-md-2">
								<div class="form group">
									<label for="from_date">From Date:</label> <input type="date" id="from_date" name="from_date" class="form-control" value="${from_date}"  ng-model="fromDate" ng-change="getCustomerList(fromDate,toDate,bank,branch)"/>
								</div>
							</div>
							<div class="col-lg-2 col-md-2">
								<div class="form group">
									<label for="to_date">To Date:</label> <input type="date" id="to_date" name="to_date" class="form-control" value="${to_date}"  ng-model="toDate" ng-change="getCustomerList(fromDate,toDate,bank,branch)"/>
								</div>
							</div>
							
							<div ng-if="customerList.length > 0" class="col-lg-2 col-md-2" >
										<div class="form group">
											<label>Customer</label> <select class="form-control selectpicker" name="customer" id="customerFilter" ng-model="uuid">
												<option value="" disabled="disabled" >Select Customer</option> 
												<option ng-repeat="value in customerList" value='{{value.uniqueId}}'>{{value.fullName}}</option>
											</select>
										</div>
								
					</div>
							<div class="col-lg-1 col-md-1">
								<div class="form group">
									<a ng-click="getCustomerLogReport()"><i class="fa fa-search small" title="Search"></i></a>
								</div>
							</div>
						
						    <!--     <div ng-hide="hide" class="col-lg-1 col-md-1">
	                            
	                            <button type="submit" ng-click="downloadReport('EXCEL','customerLog,'customerLogListView')" class="btn btn-warning">
					              Download <span class="glyphicon glyphicon-download"></span>
				               </button>
	                        </div>  -->
					</div>
				</div>
			</div>
		</div>
          
	<div ng-hide="hide" >
		<table id="customerLogListView" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th>Customer Name</th>
							<th>Remarks</th>
							<th>Date</th>
							<th>Commented By</th>

						</tr>
					</thead>
					<tbody>



						<tr ng-repeat="customerLog in customerLogList">
							<td>{{customerLog.customerName}}</td>
							<td>{{customerLog.remarks}}</td>
							<td>{{customerLog.date}}</td>
							<td>{{customerLog.changedBy}}</td>
						</tr>
					</tbody>
				</table>
	</div>
</div>
</spr:page>

<script src="${pageContext.request.contextPath}/js/Angular/report.js"></script>