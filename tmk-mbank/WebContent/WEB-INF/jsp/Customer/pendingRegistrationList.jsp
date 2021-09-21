<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/custom.css" rel="stylesheet">
<link href="../css/starter-template.css" rel="stylesheet">
<link href="/css/radinfo.css" rel="stylesheet"></link>
<link rel="stylesheet" href="../css/jquery.dataTables.min.css">

<style>
.add-on .input-group-btn>.btn {
	border-left-width: 0;
	left: -2px;
	-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
	box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
}
/* stop the glowing blue shadow */
.add-on .form-control:focus {
	box-shadow: none;
	-webkit-box-shadow: none;
	border-color: #cccccc;
}

.form-control {
	width: 20%
}

.navbar-nav>li>a {
	border-right: 1px solid #ddd;
	padding-bottom: 15px;
	padding-top: 15px;
}

.navbar-nav:last-child {
	border-right: 0
}

.mypgactive {
	background: #2A3F54 !important;
	cursor: pointer !important;
	color: white !important;
}

table {
	font-size: 12px;
}

td {
	word-wrap: break-word;
}
</style>
<spr:page header1="List Customer">
	<div class="col-md-12">
		<div class="break"></div>
		<c:if test="${not empty message}">
			<p class="bg-success">
				<c:out value="${message}"></c:out>
			</p>
		</c:if>
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="rad-info-box">
					<div class="row">
						<form action="/customer/list/pendingRegistration" method="get" id="filterForm">
							<c:choose>
								<c:when test="${isBank eq true or userAdmin eq true}">
									<div class="col-lg-2 col-md-2">
										<div class="form group">
											<label>Name</label> <input type="text" class="form-control" placeholder="Name" name="name" value="${name}"></input>
										</div>
									</div>
								</c:when>
								<c:otherwise>
									<div class="col-lg-3 col-md-3">
										<div class="form group">
											<label>Name</label> <input type="text" class="form-control" placeholder="Name" name="name" value="${name}"></input>
										</div>
									</div>
								</c:otherwise>
							</c:choose>
							<div class="col-lg-2 col-md-2">
								<div class="form group">
									<label>Mobile Number</label> <input type="text" class="form-control" placeholder="Mobile Number" name="mobileNo" value="${mobileNo}"></input>
								</div>
							</div>
							<div class="col-lg-2 col-md-2">
								<div class="form group">
									<label>Account Number</label> <input type="text" class="form-control" placeholder="Account Number" name="accountNo" value="${accountNo}"></input>
								</div>
							</div>
							<div class="col-lg-2 col-md-2">
								<div class="form group">
									<label>City</label> <select class="form-control selectpicker" name="city" data-live-search="true">
										<option value="" selected>City</option>
										<c:forEach var="citys" items="${cityList}">
											<c:choose>
												<c:when test="${city eq citys.id}">
													<option value="${citys.id}" selected>${citys.name}</option>
												</c:when>
												<c:otherwise>
													<option value="${citys.id}">${citys.name}</option>
												</c:otherwise>
											</c:choose>

										</c:forEach>
									</select>
								</div>
							</div>
							<c:choose>
								<c:when test="${userAdmin eq true}">
									<div class="col-lg-2 col-md-2">
										<div class="form group">
											<label>Bank</label> <select class="form-control selectpicker" name="bank" data-live-search="true">
												<option value="" selected disabled>Select Bank</option>
												<c:forEach var="bnk" items="${bankList}">
													<c:choose>
														<c:when test="${bank eq bnk.swiftCode}">
															<option value="${bnk.swiftCode}" selected>${bnk.name}</option>
														</c:when>
														<c:otherwise>
															<option value="${bnk.swiftCode}">${bnk.name}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select>
										</div>
									</div>
								</c:when>
								<c:when test="${isBank eq true}">
									<div class="col-lg-2 col-md-2">
										<div class="form group">
											<label>Branch</label> <select class="form-control selectpicker" name="branch" data-live-search="true">
												<option value="" selected disabled>Select Branch</option>
												<c:forEach var="brnch" items="${branchList}">
													<c:choose>
														<c:when test="${branch eq brnch.branchCode}">
															<option value="${brnch.branchCode}" selected>${brnch.branchCode}--${brnch.name}</option>
														</c:when>
														<c:otherwise>
															<option value="${brnch.branchCode}">${brnch.branchCode}--${brnch.name}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select>
										</div>
									</div>
								</c:when>
							</c:choose>
							<div class="col-lg-1 col-md-1">
								<div class="form group">
									<a onclick="postForm()"><i class="fa fa-search small" title="Search"></i></a>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>

		<table id="customerList" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>Customer Name</th>
					<th>Mobile No.</th>
					<th>State</th>
					<th>City</th>
					<th>Address</th>
					<c:if test="${userAdmin eq true}">
						<th>Bank</th>
					</c:if>
					<c:if test="${isBank eq true}">
						<th>Branch</th>
					</c:if>
					<th>Status</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(customerList.object) gt 0}">
					<c:forEach var="customer" items="${customerList.object}">
						<tr>
							<td>${customer.fullName}</td>
							<td>${customer.mobileNumber}</td>
							<td>${customer.state}</td>
							<td>${customer.city}</td>
							<td>${customer.addressOne}</td>
							<c:if test="${userAdmin eq true}">
								<td>${customer.bank}</td>
							</c:if>
							<c:if test="${isBank eq true}">
								<td>${customer.bankBranch}</td>
							</c:if>

							<td><c:if test="${canApprove}"><button class="btn btn-primary btn-xs"data-toggle="modal" data-target="#statusModal" ng-click="setCustomerUniqueId('${customer.uniqueId}')"></c:if>${customer.status }<c:if test="${canApprove}"></button></c:if></td>
						
						<td><c:if test="${canApprove}"><a class="btn btn-primary btn-xs" href="${pageContext.request.contextPath}/customer/retryRegistration?customer=${customer.uniqueId}"></c:if>Retry<c:if test="${canApprove}"></a></c:if></td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		<c:if test="${fn:length(customerList.pageList) gt 1}">
			<div class="pagination-block pull-left">

				<ul class="pagination pagination-sm no-margin pagingclass">
					<li><c:if test="${customerList.currentPage > 1}">
							<a href="/customer/list?pageNo=${customerList.currentPage-1}&name=${name}&mobileNo=${mobileNo}&city=${city}&status=${status}" class="pn prev mypgactive">Prev</a>
						</c:if></li>

					<c:forEach var="pagelist" items="${customerList.pageList}">
						<li><c:choose>

								<c:when test="${pagelist == customerList.currentPage}">

									<span>${pagelist}</span>

								</c:when>
								<c:otherwise>

									<a href="/customer/list?pageNo=${pagelist}&name=${name}&mobileNo=${mobileNo}&city=${city}&status=${status}" class="mypgactive">${pagelist}</a>

								</c:otherwise>
							</c:choose></li>
					</c:forEach>
					<li><c:if test="${customerList.currentPage + 1 <= customerList.lastpage}">
							<a href="/customer/list?pageNo=${customerList.currentPage+1}&name=${name}&mobileNo=${mobileNo}&city=${city}&status=${status}" class="pn next mypgactive">Next</a>
						</c:if></li>
				</ul>
			</div>

		</c:if>
	</div>
</spr:page>
<script src="${pageContext.request.contextPath}/resources/js/customer.js"></script>
<script>
	$(document).ready(function() {
		init("${pageContext.request.contextPath}");
	});
</script>



<div id="statusModal" class="modal fade" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Change Status</h4>
			</div>
			<form action="${pageContext.request.contextPath}/customer/changestatus" method="POST">
				<input type="hidden" name="customer" value="{{customerUniqueId}}">
				<div class="modal-body">
					<div class="form-group">
						<label>Status</label> <select class="form-control" name="status">
							<option value="">Select a Status</option>
							<c:forEach var="status" items="${customerStatusList}">
								<option value="${status}">${status}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<label>Remarks</label>
						<textarea class="form-control" name="remarks" style="resize: vertical;"></textarea>
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-danger pull-left">Save</button>
					<a class="btn btn-default" data-dismiss="modal">Cancel</a>
				</div>
			</form>
		</div>
	</div>
</div>
<script>
	function postForm() {
		$("#filterForm").submit();
	}
</script>