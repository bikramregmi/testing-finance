<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<spr:page header1="Notification">
	<div class="col-md-12">
		<div class="break"></div>
		<c:if test="${not empty message}">
			<div class="alert alert-success alert-dismissible">
				<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>${message}</div>
		</c:if>
		<a href="#" class="btn btn-primary pull-right" data-toggle="modal" data-target="#sendNotification">Send Notification</a>
		<table id="notificationList" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>Date</th>
					<th>Title</th>
					<th>Message</th>
					<th>Response</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(notificationList.object) gt 0}">
					<c:forEach var="notification" items="${notificationList.object}">
						<tr>
							<td>${notification.date}</td>
							<td>${notification.title}</td>
							<td>${notification.body}</td>
							<td>${notification.response}</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		<c:if test="${fn:length(notificationList.pageList) gt 1}">
			<div class="pull-left">

				<ul class="pagination mpagination">
					<c:if test="${notificationList.currentPage > 1}">
						<li><a href="/notification?pageNo=1">First</a></li>
						<li><a href="/notification?pageNo=${notificationList.currentPage-1}">Prev</a></li>
					</c:if>

					<c:forEach var="pagelist" items="${notificationList.pageList}">
						<c:choose>

							<c:when test="${pagelist == notificationList.currentPage}">
								<li class="active"><a>${pagelist}</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="/notification?pageNo=${pagelist}">${pagelist}</a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:if test="${notificationList.currentPage + 1 <= notificationList.lastpage}">
						<li><a href="/notification?pageNo=${notificationList.currentPage+1}">Next</a></li>
						<li><a href="/notification?pageNo=${notificationList.lastpage}">Last</a></li>
					</c:if>
				</ul>
			</div>

		</c:if>
	</div>
	<div id="sendNotification" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<form method="post" action="notification/send">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Send Notification</h4>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<div class="row">
								<div class="col-xs-2">
									<label style="margin-top: 6px;">Title:</label>
								</div>
								<div class="col-xs-10">
									<input type="text" name=title class="form-control input-sm" required="required">
								</div>
							</div>
							<h6>
								<font color="red">${error.title}</font>
							</h6>
						</div>
						<div class="form-group">
							<div class="row">
								<div class="col-xs-2">
									<label>Message:</label>
								</div>
								<div class="col-xs-10">
									<textarea name="body" class="form-control input-sm" style="resize: vertical;" required="required"></textarea>
								</div>
							</div>
							<h6>
								<font color="red">${error.body}</font>
							</h6>
						</div>
						<div class="form-group">
							<div class="row">
								<div class="col-xs-3">
									<label style="font-size: 12px;">To Notification Group:</label>
								</div>
								<div class="col-xs-3">
									<input type="checkbox" name="topic" data-ng-model="isTopic" ng-disabled="isCustomer">
								</div>

								<div class="col-xs-3">
									<label style="font-size: 12px;">To All Customer:</label>
								</div>
								<div class="col-xs-3">
									<input type="checkbox" name="allCustomer" data-ng-model="isCustomer" ng-disabled="isTopic">
								</div>
							</div>
						</div>
						<c:if test="${userType eq 'Admin'}">
							<div class="form-group">
								<div class="row">
									<div class="col-xs-2">
										<label style="margin-top: 6px;">Bank:</label>
									</div>
									<div class="col-xs-10">
										<select name="bankCode" class="form-control input-sm" required="required" ng-change="getNotificationGroup()" ng-model="bankCode">
											<c:if test="${fn:length(bankList) gt 0}">
												<option ng-selected="true" disabled>Select Bank</option>
												<c:forEach var="bank" items="${bankList}">
													<option value="${bank.swiftCode}">${bank.name}</option>
												</c:forEach>
											</c:if>
										</select>
										<p class="error">
											<font color="red">${error.bank}</font>
										</p>
									</div>
								</div>
							</div>
						</c:if>
						<c:if test="${userType eq 'Admin'}">
							<div class="form-group" data-ng-if="isTopic">
								<div class="row" data-ng-init="initSelectPicker()">
									<div class="col-xs-3">
										<label style="margin-top: 6px;">Notification Group:</label>
									</div>
									<div class="col-xs-9">
										<select name="sendTo" class="form-control selectpicker" data-live-search="true">
											<option ng-repeat="x in notificationGroupList" value="/topics/{{bankCode}}{{x.name}}">{{x.name}}</option>
										</select>
									</div>
								</div>
							</div>
						</c:if>
						<c:if test="${userType ne 'Admin'}">
							<div class="form-group" data-ng-if="isTopic">
								<div class="row" data-ng-init="initSelectPicker()">
									<div class="col-xs-3">
										<label style="margin-top: 6px;">Notification Group:</label>
									</div>
									<div class="col-xs-9">
										<select name="sendTo" class="form-control selectpicker" data-live-search="true">
											<c:forEach items="${notificationGroupList}" var="notificationGroup">
												<option value="/topics/${bankCode}${notificationGroup.name}">${notificationGroup.name}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
						</c:if>
						<div data-ng-if="!isTopic && !isCustomer">
							<div class="form-group">
								<div class="row">
									<div class="col-xs-2">
										<label style="margin-top: 6px;">Mobile No:</label>
									</div>
									<div class="col-xs-10">
										<input id="dataInput" type="text" name="user" class="form-control" data-ng-model="$parent.user" data-ng-change="findUser()"> <input type="hidden"
											name="idList" class="form-control" data-ng-model="$parent.idList">
									</div>
								</div>
								<div class="row" style="position: fixed;">
									<div class="col-md-10 col-md-offset-2" data-ng-hide="isEmpty">
										<table class="table table-striped table-bordered table-condensed">
											<thead>
												<tr>
													<th style="height: 5px; padding: 5px;">Customer Name</th>
													<th style="height: 5px; padding: 5px;">Mobile Number</th>
												</tr>
											</thead>
											<tbody>
												<tr data-ng-repeat="x in $parent.userList" data-ng-click="setUser(x)" style="cursor: pointer; background-color: #ffffff;">
													<td style="height: 5px; padding: 5px;">{{x.fullName}}</td>
													<td style="height: 5px; padding: 5px;">{{x.mobileNumber}}</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-danger pull-left">Confirm</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					</div>
				</div>
			</form>
		</div>
		<div class="col-lg-3 notification-customer-div" ng-show="idList.length != 0 && !isTopic && !isCustomer">
			<div class="header">
				<h5>Send Notification To</h5>
			</div>
			<div class="row" id="customerDiv"></div>
		</div>
	</div>
</spr:page>
<style>
table {
	table-layout: fixed;
	font-size: 12px !important;
}

.customer {
	border: 1px solid;
	border-radius: 5px;
	text-align: center;
	margin: 5px;
	cursor: pointer;
}

.customer .close-button {
	float: right;
	color: #d9534f;
	line-height: 1.5;
	margin-right: 5px;
}

.notification-customer-div {
	float: right;
	background: white;
	height: auto;
	border-radius: 6px;
	-webkit-box-shadow: 0 5px 15px rgba(0, 0, 0, .5);
	box-shadow: 0 5px 15px rgba(0, 0, 0, .5);
	font-size: 14px;
	right: 10px;
	bottom: 68%;
	margin-top:65px;
}

.notification-customer-div .header {
	border-bottom: 1px solid #e5e5e5;
}
</style>
