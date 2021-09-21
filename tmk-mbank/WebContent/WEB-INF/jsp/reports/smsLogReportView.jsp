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

<spr:page header1="SMS Business Report" >
<div id="sbReport" ng-controller="reportController" ng-init="hide=true">
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
									<label for="from_date">From Date:</label> <input type="date" id="from_date" name="from_date" class="form-control " ng-model="fromDate" />
								</div>
							</div>
							<div class="col-lg-2 col-md-2">
								<div class="form group">
									<label for="to_date">To Date:</label> <input type="date" id="to_date" name="to_date" class="form-control" ng-model="toDate" />
								</div>
							</div>
							
						
							<%-- 	<c:when test="${userAdmin eq true}"> --%>
									<%-- <div class="col-lg-2 col-md-2">
										<div class="form group">
											<label>Bank</label> <select class="form-control selectpicker" name="bank" data-live-search="true" id="bankFilter">
												<!-- <option value="" selected disabled>Select Bank</option> -->
												<c:if test="${fn:length(bankList) gt 0}">
												<c:forEach var="bnk" items="${bankList}">
															<option value="${bnk.swiftCode}">${bnk.name}</option>
													
												</c:forEach>
												</c:if>
											</select>
										</div>
									</div> --%>
								<%-- </c:when> --%>
							<%-- 	<c:when test="${isBank eq true}">
									<div class="col-lg-2 col-md-2">
										<div class="form group">
											<label>Branch</label> <select class="form-control selectpicker" name="branch" data-live-search="true" id="branchFilter">
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
								</c:when> --%>
							
							
							<div class="col-lg-1 col-md-1">
								<div class="form group">
									<a ng-click="getDataForReport('smsLogReport')"><i class="fa fa-search small" title="Search"></i></a>
								</div>
							</div>
							
						       <div ng-hide="hide" class="col-lg-1 col-md-1">
	                            <!-- 
                                  <a ng-click="downloadReport('EXCEL','SMS Business Report','sms_log_report')" ><p  >Download</p></i></a> -->
	                          <button type="submit" ng-click="downloadReport('EXCEL','SMS Business Report','sms_log_report')" class="btn btn-warning">
					              Download <span class="glyphicon glyphicon-download"></span>
				               </button>
	                        </div>
					</div>
				</div>
			</div>
		</div>
       <%@include file="/WEB-INF/jsp/reports/downloadSmsBusinessReport.jsp"%>
   <div ng-hide="hide" >
	

<table id="sms_log_report_view">
<thead>
    <tr> <th> SMS BUSINESS REPORT</th></tr>
     <tr><th> Today :  {{today | date}}</th></tr>
      <tr><th></th></tr>
     <tr>
				<th>S No</th>
				<th>Bank</th>
				<th>Total SMS</th>
				<th>Total In coming SMS</th>
				<th>Total Out Going SMS</th>
			
	</tr>
	</thead>
	<tbody>
	    <tr ng-repeat="(key, values) in smsReport" >
	    <td ><p align="center">{{$index+1}}</p></td>
	    <td align="center">{{key}}</td>
	    
	    <td ng-repeat="value in values track by $index" align="center"><p align="center">{{value}}</p></td>
	    
	    </tr>
	
	</tbody>
</table>
</div>


</div>
 

</spr:page>
<script src="${pageContext.request.contextPath}/js/Angular/report.js"></script>
