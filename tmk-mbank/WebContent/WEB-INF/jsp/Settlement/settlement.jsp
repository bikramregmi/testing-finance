<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" >
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<input id="firsrLogin" type="hidden" value="${firstLogin}" />
<spr:page header1="Incomplete Settlement">
	<div class="col-md-12">
		<div class="break"></div>
		<c:if test="${not empty message}">
			<p class="bg-success">
				<c:out value="${message}"></c:out>
			</p>
		</c:if>
		<table id="settlementLogList" class="table table-striped table-bordered table-condensed" style="font-size: 12px !important;">
			<thead>
				<tr>
					<th>Date</th>
					<th>Bank Name</th>
					<th>Transaction Id</th>
					<th>Settlement Status</th>
					<th>Settlement Type</th>
					<th>Response Code</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(settlementList) gt 0}">
					<c:forEach var="settlement" items="${settlementList}">
						<tr>
							<td>${settlement.settlementDate}</td>
							<td>${settlement.bankName}</td>
							<td>${settlement.transactionIdentifier}</td>
							<td>${settlement.settlementStatus}</td>
							<td>${settlement.settlementType}</td>
							<td>${settlement.responseCode}</td>
							<td><a href="autoUpdatePendingSettlement?settlementLogId=${settlement.id}" class="btn btn-xs btn-primary">Auto Update</a>
								<button class="btn btn-xs btn-primary" data-toggle="modal" data-target="#manualUpdateModal" ng-click="settlementId = ${settlement.id}">Manual Update</button></td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>
</spr:page>

<div id="manualUpdateModal" class="modal fade" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Manual Update</h4>
			</div>
			<div class="modal-body">
				<p>Are you sure you want to change this settlement's status to success.</p>
			</div>
			<div class="modal-footer">
				<a href="manualUpdatePendingSettlement?settlementLogId={{settlementId}}" type="button" class="btn btn-primary pull-left">Confirm</a>
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>

<div id="success" class="modal fade" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title"></h4>
			</div>
			<div class="modal-body">
				<p>Settlement Successful.</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>

<div id="failure" class="modal fade" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title"></h4>
			</div>
			<div class="modal-body">
				<p>Something went wrong. Please try again later.</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>

<div id="loading" class="modal fade modal-center">
	<i class="fa fa-spinner fa-spin fa-4x"></i>
</div>
<script>
	$(document).ready(function() {
		$('#settlementLogList').DataTable();
	});
</script>

<c:if test="${valid eq 'isValid'}">
	<script>
		$('#success').modal('show');
	</script>
</c:if>
<c:if test="${valid eq 'isNotValid'}">
	<script>
		$('#failure').modal('show');
	</script>
</c:if>
<style>
.modal-center {
	left: 50%;
	top: 50%;
}

.modal-center i {
	text-align: center;
	color: white;
}
</style>