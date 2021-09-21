<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script src="${pageContext.request.contextPath}/js/State/state.js"></script>
<script src="${pageContext.request.contextPath}/js/City/city.js"></script>
<spr:page header1="Customer Profile">
	<c:if test="${not empty message}">
		<div class="alert alert-success alert-dismissible fade in">
			<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a> <strong>Success!</strong> ${message}
		</div>
	</c:if>
	<c:if test="${not empty errorMesssage}">
		<div class="alert alert-danger alert-dismissible fade in">
			<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a> <strong>Error!</strong> ${errorMesssage}
		</div>
	</c:if>
	<div class="container container-main">
		<div class="panel panel-default">
			<div class="panel-heading">
				<c:if test="${canApprove}">
					<button ng-click="setCustomerUniqueId('${customer.uniqueId}')" class="btn btn-primary" data-toggle="modal" data-target="#addAccountModal">Add Account</button>
					<button ng-click="setCustomerUniqueIdAndMobileNumber('${customer.uniqueId}','${customer.mobileNumber}')" class="btn btn-primary" data-toggle="modal" data-target="#editMobileNumber">Edit Mobile Number</button>
				</c:if>
				<p class="panel-title">Customer Summary</p>
			</div>
			<div class="panel-body">
				<div class="detail-list col-md-4 col-sm-12">
					<ul class="list-group">
						<li class="list-group-item"><b>Name : </b>${customer.fullName}</li>
						<li class="list-group-item"><b>Address : </b>${customer.addressOne}</li>
						<li class="list-group-item"><b>City : </b>${customer.city}</li>
						<li class="list-group-item"><b>State : </b>${customer.state}</li>
						<li class="list-group-item"><b>Mobile Number : </b>${customer.mobileNumber}</li>
						
						<li class="list-group-item"><b>User Status : </b>
							<c:choose>
								<c:when test="${customer.userStatus eq 'Active'}">
									<span class="badge success-badge">${customer.userStatus}</span>
								</c:when>
								<c:otherwise>
									<span class="badge error-badge">${customer.userStatus}</span>
								</c:otherwise>
							</c:choose>
						</li>
						<li class="list-group-item"><b>FCM Token : </b>
							<c:choose>
								<c:when test="${customer.firebaseToken}">
									<i class="fa fa-check fa-icon-success"></i>
								</c:when>
								<c:otherwise>
									<i class="fa fa-times fa-icon-error"></i>
								</c:otherwise>
							</c:choose>
						</li>
						<li class="list-group-item"><b>Device Token : </b>${customer.deviceToken}</li>
						<li class="list-group-item"><b>Oauth Token : </b>
							<c:choose>
								<c:when test="${customer.oauthTokenCount eq 0 or customer.oauthTokenCount eq 1}">
									<span class="badge success-badge">${customer.oauthTokenCount}</span>
								</c:when>
								<c:otherwise>
									<span class="badge error-badge">${customer.oauthTokenCount}</span>
								</c:otherwise>
							</c:choose>
						</li>
						<li class="list-group-item" style="text-align: center;"><button ng-click="setCustomerUniqueId('${customer.uniqueId}')" class="btn btn-info" data-toggle="modal" data-target="#resetTokenModal">Flush Token</button></li>
						<li class="list-group-item"><b>Registered date : </b>${customer.created}</li>
						<li class="list-group-item"><b>Last Renewed date : </b>${customer.lastRenewDate}</li>
						<li class="list-group-item"><b>Last Modified Date : </b>${customer.lastModified}</li>
						<li class="list-group-item"><b>Last Comment : </b>${customer.comment}</li>
						<li class="list-group-item"><b>Last Commented By : </b>${customer.commentedBy}</li>
						<li class="list-group-item"><b>Approval Message : </b>${customer.approvalMessage}</li>
						<li class="list-group-item" style="text-align: center;"><button class="btn btn-info" data-toggle="modal" data-target="#logModal" ng-click="getCustomerLog('${customer.uniqueId}',0)">View Log</button></li>
					</ul>
				</div>
				<div class="col-md-8 col-sm-12">
					<table class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th>Account Number</th>
								<th>Account Type</th>
								<th>Branch Code</th>
								<c:if test="${canApprove}">
									<th colspan="2">Action</th>
								</c:if>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${customerBankAccountList}" var="customerBankAccount">
								<tr>
									<td>${customerBankAccount.accountNumber}</td>
									<td>${customerBankAccount.accountMode}</td>
									<td>${customerBankAccount.branchCode}</td>
									<c:if test="${userTypeAdmin ne 'true'}">
										<td><a
											ng-click="setCustomerBankAccount('${customer.uniqueId}','${customerBankAccount.accountNumber}','${customerBankAccount.accountMode}','${customerBankAccount.id}')"
											data-toggle="modal" data-target="#changeAccountModal" style="color:#5bc0de;"> <i class="fa fa-pencil" data-toggle="tooltiip" data-placement="top" title="Edit Account Number"></i></a>
										</td>
										<td><a
											ng-click="setCustomerBankAccount('${customer.uniqueId}','${customerBankAccount.accountNumber}','${customerBankAccount.accountMode}','${customerBankAccount.id}')"
											data-toggle="modal" data-target="#deleteModal" style="color:red;"> <i class="fa fa-trash-o" data-toggle="tooltiip" data-placement="top" title="Edit Account Number"></i></a></td>
									</c:if>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>	
	</div>
</spr:page>

<div id="changeAccountModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Change Account Number</h4>
			</div>
			<form action="${pageContext.request.contextPath}/customer/changeAccountNumber" method="post">
				<div class="modal-body">
					<c:if test="${error ne 'null'}">
						<br>
						<p>
							<font color="red">${error}</font>
						</p>
					</c:if>
					<div class="form-group">
						<label>Account Type</label> <select name="accountMode" id="accountMode" class="form-control" required ng-model="accountTypeScope">
							<option value="" selected disabled>Select Account Type</option>
							<c:forEach var="accountType" items="${accountMode}">
								<option value="${accountType}">${accountType}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<input type="hidden" name="uniqueId" id="uniqueIdedit" value="{{customerUniqueId}}"> <input type="hidden" name="currentAccountNumber"
							id="currentAccountNumberedit" value="{{currentCustomerBankAccountNo}}"> <label>Account Number</label> <input type="text" class="form-control"
							placeholder="Account Number" name="accountNumber" id="accountNumberedit" value="{{currentCustomerBankAccountNo}}">
					</div>
					<div class="form-group">
						<label>Comment *</label>
						<textarea rows="4" cols="50" name="comment" class="form-control" required></textarea>
					</div>
				</div>

				<div class="modal-footer">
					<button type="submit" class="btn btn-primary pull-left">Confirm</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
				</div>
			</form>
		</div>

	</div>
</div>

<div id="deleteModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Delete Account Number</h4>
			</div>
			<form action="${pageContext.request.contextPath}/customer/account/delete" method="post">
				<div class="modal-body">
					<input type="hidden" name="account" value="{{accountId}}"> <input type="hidden" name="customer" value="{{customerUniqueId}}">
					<div class="form-group">
						<p>Are you sure you want to delete account no: {{currentCustomerBankAccountNo}} ?</p>
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


<div id="addAccountModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Add Account Number</h4>
			</div>
			<form action="${pageContext.request.contextPath}/customer/addAccount" method="post">
				<c:if test="${error ne 'null'}">
					<br>
					<p>
						<font color="red">${error}</font>
					</p>
				</c:if>
				<input type="hidden" name="uniqueId" id="uniqueIdadd" value="{{customerUniqueId}}">
				<div class="modal-body">
					<c:if test="${userTypeBank eq 'true'}">
						<div class="form-group">
							<label>Branch</label> <select name="branch" class="form-control" required>
								<option value="" selected disabled>Select Branch</option>
								<c:forEach var="branch" items="${branchList}">
									<option value="${branch.id}">${branch.bank}--${branch.name} -- ${branch.branchCode}</option>
								</c:forEach>
							</select>
						</div>
					</c:if>
					<div class="form-group">
						<label>Account Type</label> <select name="accountMode" class="form-control" required>
							<option value="" selected disabled>Select Account Type</option>
							<c:forEach var="accountType" items="${accountMode}">
								<option value="${accountType}">${accountType}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<label>Account Number</label> <input type="text" class="form-control" placeholder="Account Number" required name="accountNumber">
					</div>
					<div class="form-group">
						<label>Comment *</label>
						<textarea rows="4" cols="50" name="comment"  class="form-control" required></textarea>
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-primary pull-left">Save</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
				</div>
			</form>
		</div>

	</div>
</div>

<div id="editMobileNumber" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Change Mobile Number</h4>
			</div>
			<form id="changeNumber" action="${pageContext.request.contextPath}/customer/mobileNumber" method="post">
				<div class="modal-body">
					<c:if test="${error ne 'null'}">
						<br>
						<p>
							<font color="red">${error}</font>
						</p>
					</c:if>
					<div class="form-group">
						<input type="hidden" name="uniqueId" id="uniqueIdedit" value="{{customerUniqueId}}"> <input type="hidden" name="currentMobileNumber"
							id="currentMobileNumberedit" value="{{mobileNumber}}"> <label>Mobile Number</label> <input type="text" class="form-control" placeholder="Mobile Number"
							name="mobileNumber" id="mobileNumberdit" value="{{mobileNumber}}">
					</div>
					<div class="form-group">
						<label>Comment *</label>
						<textarea rows="4" cols="50" name="comment" class="form-control" required></textarea>
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" id="chaangeMobileNumber" class="btn btn-danger pull-left">Confirm</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
				</div>
			</form>
		</div>
	</div>
</div>

<div id="customerLogModal" class="modal fade" role="dialog">
	<div class="modal-dialog modal-lg">

		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Customer Log</h4>
			</div>
			<div class="modal-body">
				<p>{{message}}</p>
				<table id="customerLogList" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th>Customer Name</th>
							<th>Remarks</th>
							<th>Date</th>
							<th>Commented By</th>

						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="customerLog in customerLogList">
							<td>{{customerLog.customerName}}</td>
							<td>{{customerLog.remarks}}</td>
							<td>{{customerLog.date}}</td>
							<td>{{customerLog.changedBy}}</td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
			</div>
		</div>

	</div>
</div>

<div id="resetTokenModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Flush Token</h4>
			</div>
			<div class="modal-body">
				<p>Are you sure you want to flush token for this customer?</p>
			</div>
			<div class="modal-footer">
				<a href="${pageContext.request.contextPath}/customer/resetToken?uniqueId={{customerUniqueId}}" type="button" class="btn btn-danger pull-left">Confirm</a>
				<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
			</div>
		</div>

	</div>
</div>
<script type="text/javascript">
	$('#changeNumber').submit(function() {
		$(this).find(':input[type=submit]').prop('disabled', true);
	});
</script>
<c:if test="${addAccountValid eq false}">
	<script>
		$('#addAccountModal').modal('show');
		$("#uniqueIdadd").val("${uniqueId}");
	</script>
</c:if>
<c:if test="${editAccountValid eq false}">
	<script>
		$("#uniqueIdedit").val("${uniqueId}");
		$("#currentAccountNumberedit").val("${currentAccountNumber}");
		$("#accountNumberedit").val("${accountNumber}");
		$('#changeAccountModal').modal('show');
	</script>
</c:if>

<c:if test="${mobileNumberValidation eq false}">
	<script>
		$('#editMobileNumber').modal('show');
		$("#uniqueIdedit").val("${uniqueId}");
		$("#mobileNumberdit").val("${mobileNumber}");
		$("#currentMobileNumberedit").val("${currentMobileNumber}");
	</script>
</c:if>

<style>
.pull-down {
	position: absolute;
	bottom: 0;
	right: 0;
}
.success-badge{
    background-color: #18964f;
}
.error-badge{
    background-color: red;
}
.fa-icon-success{
	font-size:23px;
	color: #29e77c;
}
.fa-icon-error{
	font-size:23px;
	color: red;
}
</style>
<style>
.pull-down {
	position: absolute;
	bottom: 0;
	right: 0;
}

.table {
	margin-top: 0px;
}

.table th {
	height: 20px
}

.table td {
	height: 10px
}

.panel-heading {
	padding-right: 0px;
}

.panel .panel-heading button {
	float: right;
}

.panel-title {
	padding: 5px;
	font-size: 18px;
	color: #73879c;
}
.badge{
	float:none !important;
}
</style>