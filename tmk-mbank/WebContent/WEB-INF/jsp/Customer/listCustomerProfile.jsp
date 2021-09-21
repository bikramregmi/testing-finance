<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<spr:page header1="List Customer Profile">
	<div class="col-md-12">
		<div class="break"></div>
		<c:if test="${not empty message}">
			<p class="bg-success">
				<c:out value="${message}"></c:out>
			</p>
		</c:if>

		<table id="customerList" class="table table-bordered table-striped table-condensed">
			<thead>
				<tr>
					<th>Name</th>
					<th>Profile ID</th>
					<th>Daily Transaction Limit Count</th>
					<th>Per Transaction Limit</th>
					<th>Registration Charge</th>
					<th>Renew Charge</th>
					<th>Pin Reset Charge</th>
					<th>Daily Transaction Limit</th>
					<th>Weekly Transaction Limit</th>
					<th>Monthly Transaction Limit</th>
					<th>Status</th>
					<th colspan=2>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(customerProfileList) gt 0}">
					<c:forEach var="profile" items="${customerProfileList}">
						<tr>
							<td>${profile.name}</td>
							<td>${profile.profileUniqueId}</td>
							<td>${profile.dailyTxnLimit}</td>
							<td>${profile.perTxnLimit}</td>
							<td>${profile.registrationAmount}</td>
							<td>${profile.renewAmount}</td>
							<td>${profile.pinResetCharge}</td>
							<td>${profile.dailyTransactionAmount}</td>
							<td>${profile.weeklyTxnLimit}</td>
							<td>${profile.monthlyTxnLimit}</td>
							<td>${profile.status}</td>
							<c:choose>
								<c:when test="${profile.isDefault && isAdmin ne true}">
									<td><a style="cursor: not-allowed;"> <i class="fa fa-pencil" data-toggle="tooltip"
											data-placement="top" title="Edit Customer Profile"></i></a></td>
									<td>
										<button disabled class="btn btn-primary btn-sm">Change Status</button>
									</td>
								</c:when>
								<c:otherwise>
									<td><a href="${pageContext.request.contextPath}/customerProfile/edit?profileId=${profile.id}"> <i class="fa fa-pencil" data-toggle="tooltip"
											data-placement="top" title="Edit Customer Profile"></i></a></td>
									<td>
										<button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#changeStatus" ng-click="setCustomerProfileId('${profile.id}')">Change Status</button>
									</td>
								</c:otherwise>
							</c:choose>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>
	<div id="changeStatus" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Change Status</h4>
				</div>
				<div class="modal-body">
					<p>Are you sure you want to Change status for the profile?</p>
				</div>
				<div class="modal-footer">
					<a href="${pageContext.request.contextPath}/customerProfile/changeStatus?profileId={{profileId}}" type="button" class="btn btn-danger pull-left">Confirm</a>
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
				</div>
			</div>

		</div>
	</div>
</spr:page>