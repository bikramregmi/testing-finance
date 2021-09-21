<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/custom.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/starter-template.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.datetimepicker.min.css" />
<spr:page header1="Bulk SMS Alert">
	<div class="col-md-12">
		<div class="break"></div>
		<c:if test="${not empty message}">
			<div class="alert alert-success alert-dismissible">
				<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>${message}</div>
		</c:if>
		<c:if test="${not empty errorMessage}">
			<div class="alert alert-danger alert-dismissible">
				<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>${errorMessage}</div>
		</c:if>
	</div>
	<div class="col-xs-12">
		<div class="well">
			<div class="file-upload">
				<div class="file-select">
					<div class="file-select-button" id="fileName">Choose File</div>
					<div class="file-select-name" id="noFile">No file chosen...</div>
					<input type="file" name="file" file-model="fileModel" id="chooseFile">
				</div>
				<span><a href="${pageContext.request.contextPath}/smsalert/bulk/sample" class="pull-right">Download sample</a></span> <input type="submit"
					ng-click="uploadBulkSmsAlert()" class="btn btn-default" style="margin-left: 0px; margin-top: 10px;" value="upload">
			</div>
			<div ng-show="uploadedData">
				<hr>
				<!-- <div class="row">
					<div class='col-sm-1'>
						<label>Schedule:</label>
					</div>
					<div class="col-sm-4">
						<input type='text' class="form-control" id='datetimepicker' ng-model="smsAlertschedule" ng-change="smsAlertScheduleChange()"/>
					</div>
				</div> -->
			</div>
		</div>
	</div>
	<div class="col-xs-12" ng-show="uploadedData">
		<form action="${pageContext.request.contextPath}/smsalert/bulk/send" method="POST" id="sendAlertForm">
			<div class="send-button">
				<button class="btn btn-success btn-send btn-sm btn-block" ng-click="sendBulkSmsAlert()" id="sendAlertButton">
					Send <i class="fa fa-paper-plane" ng-hide="sendLoading"></i> <i class="fa fa-spinner fa-spin" ng-show="sendLoading"></i>
				</button>
			</div>
			<table class="table table-condensed table-striped table-bordered" id="smsAlertTable" style="width: 100%">
				<thead>
					<tr>
						<th>Mobile Number</th>
						<th>Message</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="smsAlert in smsAlertList">
						<td><input type="text" name="smsAlertList[{{$index}}].mobileNumber" value="{{smsAlert.mobileNumber}}" style="display: none;">{{smsAlert.mobileNumber}}</td>
						<td><input type="text" name="smsAlertList[{{$index}}].message" value="{{smsAlert.message}}" style="display: none;">{{smsAlert.message}}</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	<i class="fa fa-spinner fa-spinner-custom fa-spin" ng-show="uploadDataLoading"></i>
</spr:page>
<script src="${pageContext.request.contextPath}/js/jquery.datetimepicker.full.min.js"></script>
<script type="text/javascript">
	$(function() {
		$('#datetimepicker').datetimepicker();
	});
</script>
<style>
.fa-spinner-custom {
	font-size: 50px;
	position: relative;
	left: 50%;
}

.send-button {
	width: 20%;
	position: absolute;
	left: 50%;
	transform: translateX(-50%);
	z-index: 1000;
}

label {
	line-height: 3;
}

hr {
	border-top: 1px solid #c0c0c0;
}
</style>