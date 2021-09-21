<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" >
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link href="/css/radinfo.css" rel="stylesheet"></link>
<link href="/css/pagination.css" rel="stylesheet"></link>
<input id="firsrLogin"  type="hidden" value="${firstLogin}" />
<spr:page header1="Ambiguous Transaction">
	<div class="col-md-12">
		<div class="break"></div>
		<c:if test="${not empty message}">
			<p class="bg-success">
				<c:out value="${message}"></c:out>
			</p>
		</c:if>
		<table id="transactionList" class="table table-striped table-bordered table-condensed" style="font-size: 12px !important;">
			<thead>
				<tr>
					<th>Date</th>
					<th>Customer Name</th>
					<th>Customer Mobile No</th>
					<th>Account No.</th>
					<th>Bank Code</th>
					<th>Target No.</th>
					<th>Transaction Id</th>
					<th>Amount</th>
					<th>Transaction Type</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(transactionList) gt 0}">
					<c:forEach var="transaction" items="${transactionList}">
						<tr>
							<td>${transaction.createdDate}</td>
							<td>${transaction.originatorName}</td>
							<td>${transaction.originatorMobileNo}</td>
							<td>${transaction.sourceAccount}</td>
							<td>${transaction.bankCode}</td>
							<td>${transaction.destination}</td>
							<td>${transaction.transactionIdentifier}</td>
							<td>${transaction.amount}</td>
							<td>${transaction.service}</td>
							<td>
								<a href="/transaction/checkambiguoustransactionstatus?transactionidentifier=${transaction.transactionIdentifier}" class="btn btn-sm btn-primary">Check Status</a>
								<button class="btn btn-sm btn-primary" data-toggle="modal" data-target="#manualUpdateModal" ng-click="setTransactionIdentifier('${transaction.transactionIdentifier}')">Manual Update</button>
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		</div>
</spr:page>

<div id="featureNotAvailableModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title"></h4>
      </div>
      <div class="modal-body">
        <p>This feature is not available right now. Please try again later.</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>
<c:if test="${status eq 'notAvailable'}">
<script>
$('#featureNotAvailableModal').modal({show: true});
</script>
</c:if>

<div id="stillAmbigiousModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title"></h4>
      </div>
      <div class="modal-body">
        <p>This Transaction is Still Ambiguous. Please try again later.</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>
<c:if test="${status eq 'unknown'}">
<script>
$('#stillAmbigiousModal').modal({show: true});
</script>
</c:if>

<div id="manualUpdateModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Manual Update</h4>
      </div>
      <div class="modal-body">
      	<div class="row">
      	<div class="col-sm-2"></div>
      	<div class="col-sm-3"><a href="/transaction/manualUpdateAmbiguousTransaction?transactionIdentifier={{transactionIdentifier}}&status=true" class="btn btn-danger">Success</a></div>
      	<div class="col-sm-2"></div>
      	<div class="col-sm-3"><a href="/transaction/manualUpdateAmbiguousTransaction?transactionIdentifier={{transactionIdentifier}}&status=false" class="btn btn-danger">Failure</a></div>
      	<div class="col-sm-2"></div>
      	</div>
      </div>
      <div class="modal-footer">
      </div>
    </div>
  </div>
</div>
