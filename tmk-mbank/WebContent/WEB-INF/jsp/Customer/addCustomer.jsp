<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/custom.css" rel="stylesheet">
<link href="../css/starter-template.css" rel="stylesheet">
<!-- <script src ="../js/jquery.min.js"></script> -->
<!-- <script src ="../js/bootstrap.js"></script> -->

<spr:page header1="Add Customer">
	<input type="hidden" ng-model="uploadUrl" ng-init="uploadUrl='${pageContext.request.contextPath}/bulkCustomerUpload'" />
	<button class="btn btn-primary" style="float: right;" data-toggle="modal" data-target="#bankUpload">Bulk Upload</button>
	<div class="col-md-12">
		<div class="row col-md-4">
			<div>
				<h2>
					<c:if test="${userType eq 'bank' }">
					Customer for  ${bankName}
				</c:if>
					<c:if test="${userType eq 'branch' }">
					Customer for ${bankBranchName}
				</c:if>
				</h2>
			</div>
			<div class="break"></div>
			<form action="${pageContext.request.contextPath}/customer/add" method="post" class="col-md-12" style="float: none; margin-left: auto; margin-right: auto;">
				<div class="form-group">
					<label>First Name*</label> <input type="text" name="firstName" class="form-control input-sm" required="required" value="${customer.firstName}">
					<h6>
						<font color="red">${error.firstName}</font>
					</h6>
				</div>
				<div class="form-group">
					<label>Middle Name</label> <input type="text" name="middleName" class="form-control input-sm" value="${customer.middleName}">
				</div>
				<div class="form-group">
					<label>Last Name*</label> <input type="text" name="lastName" class="form-control input-sm" required="required" value="${customer.lastName}">
					<h6>
						<font color="red">${error.lastName}</font>
					</h6>
				</div>
				<div class="form-group">
					<label>Address One*</label> <input type="text" name="addressOne" class="form-control input-sm" required="required" value="${customer.addressOne}">
					<h6>
						<font color="red">${error.addressOne}</font>
					</h6>
				</div>
				<div class="form-group">
					<label>Address Two</label> <input type="text" name="addressTwo" class="form-control input-sm" value="${customer.addressTwo}">
				</div>
				<div>
					<label>Email</label> <input type="email" name="email" class="form-control inpupt-sm" value="${customer.email}">
					<h6>
						<font color="red">${error.email}</font>
					</h6>
				</div>

				<div class="form-group">
					<label>Customer Profile*</label> <select name="customerProfileId" class="form-control input-sm" required="required">
						<c:if test="${fn:length(customerProfileList) gt 0}">
							<option selected disabled>Select Customer Profile</option>
							<c:forEach var="profile" items="${customerProfileList}">
								<c:choose>
									<c:when test="${profile.status eq 'Inactive'}">
										<option disabled value="${profile.id}">${profile.name}</option>
									</c:when>
									<c:otherwise>
										<option value="${profile.id}">${profile.name}</option>
									</c:otherwise>
								</c:choose>

							</c:forEach>
						</c:if>
					</select>
					<p class="error">${error.state}</p>
				</div>

				<div class="form-group">
					<label>State*</label> <select name="state" class="form-control input-sm" required="required" id="state">
						<c:if test="${fn:length(statesList) gt 0}">
							<option>Select State</option>
							<c:forEach var="state" items="${statesList}">
								<option value="${state.name}">${state.name}</option>
							</c:forEach>
						</c:if>
					</select>
					<h6>
						<font color="red">${error.state}</font>
					</h6>
				</div>
				<div class="form-group">
					<label>City*</label> <select name="city" class="form-control input-sm" required="required" id="city">

					</select>
					<h6>
						<font color="red">${error.city}</font>
					</h6>
				</div>
				<c:if test="${userType eq 'bank'}">
					<div class="form-group">
						<label>Bank Branch*</label> <select name="bankBranch" class="form-control input-sm" required="required" id="state">
							<c:if test="${fn:length(branchList) gt 0}">
								<option>Select Branch</option>
								<c:forEach var="branch" items="${branchList}">
									<option value="${branch.id}">${branch.bank}-- ${branch.name} -- ${branch.branchCode}</option>
								</c:forEach>
							</c:if>
						</select>
						<h6>
							<font color="red">${error.bankBranch}</font>
						</h6>
					</div>
				</c:if>
				<div class="form-group">
					<label>Account Number*</label> <input type="text" name="accountNumber" class="form-control input-sm" value="${customer.accountNumber}">
					<h6>
						<font color="red">${error.accountNumber}</font>
					</h6>
				</div>
				<div class="form-group">
					<label>Account Type*</label> <select name="accountMode" id="accountMode" class="form-control input-sm" required="required">
						<option>Select Account Type</option>
						<c:forEach items="${accountMode}" var="aMode">
							<option value="${aMode}">${aMode}</option>
						</c:forEach>
					</select>
					<h6>
						<font color="red">${error.accountType}</font>
					</h6>
				</div>
				<div class="form-group">
					<label>Landline</label> <input type="text" name="landline" class="form-control input-sm" value="${customer.landline}">
					<h6>
						<font color="red">${error.landline}</font>
					</h6>
				</div>
				<div class="form-group">
					<label>Mobile No*</label> <input type="text" name="mobileNumber" class="form-control input-sm" required="required" value="${customer.mobileNumber}">
					<h6>
						<font color="red">${error.mobileNumber}</font>
					</h6>
				</div>

				<div class="form-group">
					<label>App Service</label> <input type="checkbox" name="appService" id="appService"> &nbsp; <label>SMS Subscribed</label> <input type="checkbox"
						name="smsService" id="smsService">
				</div>
				<div class="form-group"></div>
				<label>SMS Mode</label> <select class="form-control" multiple="multiple" id="fromSelect">
					<c:if test="${fn:length(smsModeList) gt 0}">
						<c:forEach var="sMode" items="${smsModeList}">
							<option value="${sMode.smsType}">${sMode.smsType}</option>
						</c:forEach>
					</c:if>
				</select> <select name="smsType" class="form-control" multiple id="toSelect"></select>

				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Add Customer</button>
				</div>
			</form>
		</div>
	</div>
</spr:page>

<!-- Modal -->
<div id=bankUpload class="modal fade" role="dialog" tabindex="-1">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">Bulk Upload</h4>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<div id="page-wrapper">
					<div class="container-fluid">

						<form method="post" enctype="multipart/form-data">

							<div class="file-upload">
								<div class="file-select">
									<div class="file-select-button" id="fileName">Choose File</div>
									<div class="file-select-name" id="noFile">No file chosen...</div>
									<input type="file" name="file" file-model="fileModel" id="chooseFile">
								</div>
								<br /> <input type="submit" ng-click="uploadBulkFile(uploadUrl)" class="btn  btn-default" style="margin-left: 0px;" value="upload">
							</div>
						</form>

					</div>
				</div>
			</div>

		</div>
	</div>
</div>

<div id="errorModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Bulk upload message</h4>
			</div>
			<div class="modal-body">
				<p>{{message}}</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		</div>

	</div>
</div>

<script>
	$(document)
			.ready(
					function() {

						$('#fromSelect').click(
								function() {
									return !$('#fromSelect option:selected')
											.remove().appendTo('#toSelect');
								});
						$('#toSelect').click(
								function() {
									return !$('#toSelect option:selected')
											.remove().appendTo('#fromSelect');
								});

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