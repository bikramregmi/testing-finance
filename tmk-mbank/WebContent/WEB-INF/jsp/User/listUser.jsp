<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
table {
	font-size: 12px;
}

td {
	word-wrap: break-word;
}
</style>
<spr:page header1="List User">
	<input type="hidden" ng-model="saveUrl" ng-init="saveUrl='${pageContext.request.contextPath}/changeuserpassword'" />
	<input type="hidden" ng-model="editUrl" ng-init="editUrl='${pageContext.request.contextPath}/updateUser'" />

	<div class="col-md-12">
		<div class="break"></div>
		<c:if test="${not empty message}">
			<div class="alert alert-success alert-dismissible">
				<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a> ${message}
			</div>
		</c:if>
		<c:if test="${not empty errorMessage}">
		<div class="alert alert-danger alert-dismissible">
				<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a> ${errorMessage}
			</div>
		</c:if>

		<%-- <div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="rad-info-box">
					<div class="row">


						<c:if test="${userAdmin eq true}">

							<!-- 			<div class="col-lg-2 col-md-2">
								<div class="form group">
									<label for="from_date">From Date:</label> <input type="date" id="from_date" name="from_date" class="form-control" />
								</div>
							</div>
							<div class="col-lg-2 col-md-2">
								<div class="form group">
									<label for="to_date">To Date:</label> <input type="date" id="to_date" name="to_date" class="form-control" />
								</div>
							</div>
							 -->

							<form action="/user_list_by_filter">
								<div class="col-lg-2 col-md-2">
									<div class="form group">
										<label>User Type : </label> <select class="form-control selectpicker" name="user_type" id="userTypeFilter" onchange="userTypeChange()">
											<option value="">--Select UserType--</option>
											<c:if test="${fn:length(userTypeList) gt 0}">
												<c:forEach var="userType" items="${userTypeList}">
													<option value="${userType}">${userType}</option>

												</c:forEach>
											</c:if>
										</select>
									</div>
								</div>

								<div class="col-lg-2 col-md-2" id="bankFilterDiv">
									<div class="form group">
										<label>Bank </label> <select class="form-control selectpicker" name="bank_id" data-live-search="true" id="bankFilter">
											<option value="">--select bank--</option>
											<c:if test="${fn:length(bankList) gt 0}">
												<c:forEach var="bnk" items="${bankList}">
													<option value="${bnk.id}">${bnk.name}</option>

												</c:forEach>
											</c:if>
										</select>
									</div>
								</div>

								<div class="col-lg-1 col-md-1">
									<div class="form group">

										<Button>
											<i class="fa fa-search" style="font-size: 24px;" title="Search"></i>
										</Button>
									</div>
								</div>

							</form>
						</c:if>
					</div>
				</div>
			</div>
		</div> --%>

		<table id="userList" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>User Name</th>
					<th>User Type</th>
					<th>Address</th>
					<th>State</th>
					<th>City</th>
					<th>Status</th>
					<th colspan=3>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(userList) gt 0}">
					<c:forEach var="user" items="${userList}">
						<tr>
							<td>${user.userName}</td>
							<td>${user.userType}</td>
							<td>${user.address}</td>
							<td>${user.state}</td>
							<td>${user.city}</td>
							<td>${user.status}</td>
							<c:choose>
								<c:when test="${user.userType eq 'BankBranch'}">
									<td><a data-toggle="modal" data-target="#changePasswordModal" ng-click="loadUsername('${user.userName}')" class="btn btn-success btn-xs"><i
											class="fa fa-pencil"></i> Change Password </a></td>
									<td>
										<button class="btn btn-primary btn-xs" ng-click="changeBranch(${user.id},${user.associatedId})">Change Branch</button>
									</td>
								</c:when>
								<c:otherwise>
									<td colspan=2><a data-toggle="modal" data-target="#changePasswordModal" ng-click="loadUsername('${user.userName}')" class="btn btn-success btn-xs"><i
											class="fa fa-pencil"></i> Change Password </a>
								</c:otherwise>
							</c:choose>
							<td><a data-toggle="modal" data-target="#editModal" ng-click="getUserById('${user.id}','${user.userType}')" class=" btn
								btn-info btn-xs"><i
									class="fa fa-pencil"></i> Edit </a> <a class="btn btn-danger btn-xs" data-toggle="modal" data-target="#confirmDelete" ng-click="loadUserId(${user.id})"><i
									class="fa fa-trash-o"></i> Delete </a></td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>

	<!-- Modal -->
	<div id="changePasswordModal" class="modal fade" role="dialog" tabindex="-1">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">Change Password</h4>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<br />
					<div class="input-group col-lg-12">
						<label>New Password</label> <input type="password" class="form-control" ng-model="password" placeholder="New Password"> <br /> {{userError.password}}
					</div>
					<br />
					<div class="input-group col-lg-12">
						<label>Re-Enter Password</label> <input type="password" class="form-control" ng-model="repassword" placeholder="Old Password"> <br />
						{{userError.repassword}}
					</div>
					<div class="modal-footer">
						<button type="button" ng-click="changePassword('changepasswordbyadmin')" class="btn btn-primary btn-modal pull-left">Change Password</button>

						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</div>

			</div>
		</div>
	</div>


	<!-- Modal -->
	<div id="editModal" class="modal fade" role="dialog" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<a class="close" data-dismiss="modal" aria-label="Close"> <span aria-hidden="true">&times;</span>
					</a>
					<h4 class="modal-title">Edit User</h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-sm-12">
							<h4>{{myMessage}}</h4>
							<div class="form-group">
								<label>Address</label> <input type="text" class="form-control" ng-model="address" placeholder="Address">
								<h6 style="color: red;">{{userError.address}}</h6>
							</div>
							<div class="form-group">
								<label>State</label> <select ng-model="state" class="form-control input-sm" required="required" id="state">
									<c:if test="${fn:length(statesList) gt 0}">
										<option>Select State</option>
										<c:forEach var="state" items="${statesList}">
											<option value="${state.name}">${state.name}</option>
										</c:forEach>
									</c:if>
								</select>
								<h6 style="color: red;">${userError.state}</h6>
							</div>
							<div class="form-group">
								<label>City</label> <select ng-options="city.id as city.name for city in cityList" name="city" ng-model="city" class="form-control input-sm" required="required"
									id="city">
								</select>
								<h6 style="color: red;">{{userError.city}}</h6>
							</div>

							<div class="form-group" id="makerCheckerForm" ng-hide="checkerHide">
								<table style="font-size: 13px;">
									<tr>
										<td><label>Checker</label></td>
										<td>&nbsp&nbsp&nbsp<input type="checkbox" name="checker" id="editChecker" style="margin: 0px;" ng-model="checker" /></td>
										<td>&nbsp&nbsp&nbsp<label>Maker</label></td>
										<td>&nbsp&nbsp&nbsp<input type="checkbox" name="maker" id="editMaker" style="margin: 0px;" ng-model="maker" /></td>
									</tr>
								</table>
							</div>

						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" ng-click="editUser(editUrl)" class="btn btn-primary btn-modal pull-left">Edit User</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
</spr:page>


<div id="confirmDelete" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Delete User</h4>
			</div>
			<div class="modal-body">
				<p>Are you sure you want to Delete selected User?</p>
			</div>
			<div class="modal-footer">
				<a href="${pageContext.request.contextPath}/deleteUser?userId={{selectedUserId}}" type="button" class="btn btn-danger pull-left">Confirm</a>
				<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
			</div>
		</div>

	</div>
</div>

<div id="changeBranchModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Change Branch</h4>
			</div>
			<form action="/changebranch" method="POST">
				<div class="modal-body">
					<input type="hidden" name="user" value="{{changeBranchUserId}}">
					<div class="row">
						<div class="col-xs-12">
							<select class="form-control" name="branch">
								<c:forEach var="branch" items="${branchList}">
									<option value="${branch.id}" ng-if="selectedBranchId == ${branch.id}" selected>${branch.name}</option>
									<option value="${branch.id}" ng-if="selectedBranchId != ${branch.id}">${branch.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-danger pull-left">Confirm</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
				</div>
			</form>
		</div>

	</div>
</div>

<div id="confirmPassword" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Password Change</h4>
			</div>
			<div class="modal-body">
				<p>Password Changed Successfully.</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		</div>

	</div>
</div>
<script>
	function userTypeChange() {

		if ($('#userTypeFilter').val() === "Bank") {
			document.getElementById("bankFilterDiv").style.display = "block";
		} else {
			document.getElementById("bankFilterDiv").style.display = "none";
		}
	}

	$(document)
			.ready(
					function() {
						$("#state")
								.change(
										function() {
											var stateName = $("#state").find(
													"option:selected").val();
											$("#city").find("option").remove();
											var option = '';
											var option = '<option value="'+0+'">Select City</option>'
											$
													.ajax({
														type : "GET",
														url : "/ajax/state/getCitiesByState?state="
																+ stateName,
														success : function(data) {
															$
																	.each(
																			data.citiesList,
																			function(
																					index,
																					city) {
																				option += '<option value="'+city.id + '">'
																						+ city.name
																						+ '</option>';
																				$(
																						"#city")
																						.append(
																								option);
																				option = '';
																			});
														}
													});
										});
					});
</script>
