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

<spr:page header1="Download NRB Report" >
<div id="nrbDownload" ng-controller="reportController">
	<div class="col-md-12" >
		<div class="break"></div>
		<c:if test="${not empty message}">
   		<p class="bg-success"><c:out value="${message}" ></c:out></p>
    	</c:if>
	</div>
			
		

			<!-- Modal content-->
			
				<div class="header">
					<h4 class="modal-title">Choose Filters</h4>
				
					<div id="message" ng-model="message" align="center">
			            <p style="color:red;">{{message}}</p>
			     </div>
			     <div id="message" ng-model="responseMessage" align="center">
			            <p style="color:green;"><b>{{responseMessage}}</b></p>
			     </div>
				</div>
				<div class="body">
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
      
			        
				<div class="footer">
						<!-- <button type="button" ng-click="getReport(bank,monthYear,date,reportType)" class="btn btn-danger btn-modal">Load {{reportType}} </button>
				 -->	
				     <button type="button" ng-click="getReport(bank,nepaliDate,reportType)" class="btn btn-danger btn-modal">Load {{reportType}} </button>
				
				 </div>
			
				</div>
            <%@include file="/WEB-INF/jsp/reports/downloadeBankingReport.jsp"%>
			<%@include file="/WEB-INF/jsp/reports/downloadeTransactionReport.jsp"%>
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
<script src="${pageContext.request.contextPath}/js/Angular/report.js"></script>