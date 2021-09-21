<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/custom.css" rel="stylesheet">
<link href="../css/starter-template.css" rel="stylesheet">
<!-- <script src ="../js/jquery.min.js"></script> -->
<!-- <script src ="../js/bootstrap.js"></script> -->


<spr:page header1="Credit Limit">
<br/>
	
	
	
		<!--added by amrit  -->
		<div class="dropdown pull-right" ng-controller="reportController">
		
		<div class="col-md-5 filter" ng-init="fromInputType='text'" ng-mouseenter="fromInputType='date'" ng-mouseleave="fromInputType='text'">
			<input type="{{fromInputType}}" class="form-control" id="from_date" placeholder="From Date">
		</div>
		<div class="col-md-5 filter" ng-init="toInputType='text'" ng-mouseenter="toInputType='date'" ng-mouseleave="toInputType='text'">
			<input type="{{toInputType}}" class="form-control" id="to_date"  placeholder="To Date">
		</div>
	
			<a class="btn btn-info" data-toggle="dropdown" ng-click="getDataForReport('creditLimit')"><i class="fa fa-download small" title="Download Excel Report"></i></a>
			<ul class="dropdown-menu">
				<li style="text-align: center; color: grey;">Credit Limit </li>
				<li class="divider new-divider"></li>
			 	<li><a href="" ng-click="downloadReport('EXCEL','Credit Limit Ledger','credit_limit_ledger')" ng-show="reportDataLoad"> <i class="fa fa-file-excel-o fa-2x" style="color: green;"></i> Ledger Excel
				</a></li> 
				<li><a href="" ng-click="downloadReport('EXCEL','Credit Limit Report','credit_limit_report')" ng-show="reportDataLoad"> <i class="fa fa-file-excel-o fa-2x" style="color: green;"></i> Report Excel
				</a></li>
				<li ng-hide="reportDataLoad" style="text-align: center;"><i class="fa fa-spinner fa-2x fa-spin"></i></li>
			</ul>
		        <%@include file="/WEB-INF/jsp/reports/downloadCreditLimitReport.jsp"%>
		         <%@include file="/WEB-INF/jsp/reports/downloadCreditLimitLedger.jsp"%> 
		</div>
		<<!-- end added -->
	<br/>
		<h4></h4>
		

<form class="well form-horizontal">
<h4>	</h4>

	<div class="form-group">

		<label class="col-md-2 control-label">Bank</label>
		<div class="col-md-4 input GroupContainer">
			<div class="input-group">
				<span class="input-group-addon"><i
					class="glyphicon glyphicon-user"></i></span> 
					<select ng-model="bank" class="form-control"  >
						<option selected disabled value="">Select Bank</option>
						<c:if test="${fn:length(banks) gt 0}">
							<c:forEach items="${banks}" var="bank">
								<option value="${bank.id}">${bank.name}--${bank.address}</option>
							</c:forEach>
						</c:if>
					</select>
			</div>
		</div>
	</div>
	
	<div class="form-group">
		<label class="col-md-2 control-label">Remarks : </label>
		<div class="col-md-4 inputGroupContainer">
			<div class="input-group">
				<span class="input-group-addon"><i
					class="glyphicon glyphicon-pencil"></i></span>
					 <textarea ng-model="remarks" class="form-control" rows='3'></textarea>
					
			</div>
		</div>
	</div>

	<div class="form-group">
		<label class="col-md-2 control-label">Balance: </label>
		<div class="col-md-4 inputGroupContainer">
			<div class="input-group">
				<span class="input-group-addon"><i
					class="glyphicon glyphicon-user"></i></span>
					 <input ng-model="amount"
					class="form-control" type="text">
					
			</div>
		</div>
	</div>
	<div class="form-group">
		<label class="col-md-2 control-label"></label>
		<div class="col-md-4">
			<button ng-click="confirm()" class="btn btn-warning">
				Load Money <span class="glyphicon glyphicon-send"></span>
			</button>
		</div>
	</div>
</form>
</spr:page>

<div id="confirmationModal" class="modal fade" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
     <div class="modal-header">
      <button type="button class="close" data-dismiss="modal">&times;</button>
      <h4 class="modal-title">Please Confirm</h4>
     </div>
     <div class="modal-body">
      <h5>You are about to credit  amount of {{amount}} .</h5>
     </div>
     <div class="modal-footer">
       <button type="button" class="btn btn-primary" ng-click="loadCredits()">OK</button>
       <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
     </div>
   </div>
  </div>
</div>

<div id="successModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Success</h4>
      </div>
      <div class="modal-body">
        <h5>Credit Loaded Successfully.</h5>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>
<script src ="../js/customer/controller.js"></script>

<script src="${pageContext.request.contextPath}/js/Angular/report.js"></script>

