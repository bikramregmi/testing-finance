<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="/css/radinfo.css" rel="stylesheet"></link>
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

.nav-tabs>li {
	float: right !important;
}
</style>
<spr:page header1="Expired Customer">
	<div class="col-md-12">
		<ul class="nav nav-tabs">
			<li><a data-toggle="tab" href="#renewed">Renewed</a></li>
			<li class="active"><a data-toggle="tab" href="#expired">Expired</a></li>
		</ul>
	</div>
	<div class="col-md-12">
		<div class="tab-content">
			<div id="expired" class="tab-pane fade in active">
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
								<form action="/customer/list/expired" method="get" id="filterForm">
									<div class="col-lg-1 col-md-1">
										<div class="form group">
											<label>Name</label> <input type="text" class="form-control" placeholder="Name" name="name" value="${name}"></input>
										</div>
									</div>
									<div class="col-lg-2 col-md-2">
										<div class="form group">
											<label>Mobile Number</label> <input type="text" class="form-control" placeholder="Mobile Number" name="mobileNo" value="${mobileNo}"></input>
										</div>
									</div>

									<div class="col-lg-2 col-md-2">
										<div class="form group">
											<label for="from_date">From Date:</label> <input type="date" id="from_date" name="from_date" class="form-control" value="${from_date}" />
										</div>
									</div>
									<div class="col-lg-2 col-md-2">
										<div class="form group">
											<label for="to_date">To Date:</label> <input type="date" id="to_date" name="to_date" class="form-control" value="${to_date}" />
										</div>
									</div>
									<div class="col-lg-2 col-md-2">
										<div class="form group">
											<label>Account Number</label> <input type="text" class="form-control" placeholder="Account Number" name="accountNo" value="${accountNo}"></input>
										</div>
									</div>
									<c:choose>
										<c:when test="${userAdmin eq true}">
											<div class="col-lg-2 col-md-2">
												<div class="form group">
													<label>Bank</label> <select class="form-control selectpicker" name="bank" data-live-search="true" id="bankFilter">
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
									<td>${customer.city}</td>
									<td>${customer.addressOne}</td>
									<c:if test="${userAdmin eq true}">
										<td>${customer.bank}</td>
									</c:if>
									<c:if test="${isBank eq true}">
										<td>${customer.bankBranch}</td>
									</c:if>

									<td>${customer.status }</td>

									<td><a href="${pageContext.request.contextPath}/customer/details?customer=${customer.uniqueId}"> <i class="fa fa-arrow-right" data-toggle="tooltip"
											data-placement="top" title="View Customer Details"></i>
									</a>
										<button class="btn btn-primary btn-xs" ng-click="setRenewCustomer('${customer.fullName}','${customer.uniqueId}')" data-toggle="modal" data-target="#renewModal"
											data-controls-modal="renewModal" data-backdrop="static" data-keyboard="false">Renew</button></td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
				<c:if test="${fn:length(customerList.pageList) gt 1}">
					<div class="pagination-block pull-left">
						<ul class="pagination pagination-sm no-margin pagingclass">
							<li><c:if test="${customerList.currentPage > 1}">
									<a href="/customer/list/expired?pageNo=${customerList.currentPage-1}&name=${name}&mobileNo=${mobileNo}&city=${city}&status=${status}&bank=${bank}"
										class="pn prev mypgactive">Prev</a>
								</c:if></li>
							<c:forEach var="pagelist" items="${customerList.pageList}">
								<li><c:choose>
										<c:when test="${pagelist == customerList.currentPage}">
											<span>${pagelist}</span>
										</c:when>
										<c:otherwise>
											<a href="/customer/list/expired?pageNo=${pagelist}&name=${name}&mobileNo=${mobileNo}&city=${city}&status=${status}&bank=${bank}" class="mypgactive">${pagelist}</a>
										</c:otherwise>
									</c:choose></li>
							</c:forEach>
							<li><c:if test="${customerList.currentPage + 1 <= customerList.lastpage}">
									<a href="/customer/list/expired?pageNo=${customerList.currentPage+1}&name=${name}&mobileNo=${mobileNo}&city=${city}&status=${status}&bank=${bank}"
										class="pn next mypgactive">Next</a>
								</c:if></li>
						</ul>
					</div>
				</c:if>
				<div id="renewModal" class="modal fade" role="dialog">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" ng-hide="loading">&times;</button>
								<h4 class="modal-title">Renew Customer</h4>
							</div>
							<div ng-hide="response">
								<div class="modal-body" ng-hide="loading">
									<p>Are you sure you want to renew {{customer.name}}?</p>
								</div>
								<div class="modal-footer" ng-hide="loading">
									<button type="button" class="btn btn-primary pull-left" ng-click="renewCustomer()">Confirm</button>
									<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
								</div>
								<div class="modal-footer" ng-show="loading" style="text-align: center">
									<i class="fa fa-cog fa-spin fa-2x"></i>
								</div>
							</div>
							<div ng-show="response">
								<div class="modal-body">
									<p style="font-size: 15px; font-weight: bold; text-align: center; color: green;" ng-show="success">{{message}}</p>
									<p style="font-size: 15px; font-weight: bold; text-align: center; color: red;" ng-hide="success">
										<i class="fa fa-exclamation-triangle"></i> {{message}}
									</p>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default" ng-click="reload()">Close</button>
								</div>
							</div>
						</div>

					</div>
				</div>
			</div>
			<div id="renewed" class="tab-pane fade in">
				<div class="panel panel-default">
					<div class="panel-heading" style="background-color: #f5f5f5 !important;">
						Recently Renewed Customer <span class="pull-right">Last <input type="text" style="width: 30px; text-align: center;" ng-model="days"
							ng-change="getRenewedCustomer()"> Days
						</span>
					</div>
					<div class="panel-body">
						<table id="customerList" class="table table-striped table-bordered table-condensed">
							<thead>
								<tr>
									<th>Customer Name</th>
									<th>Mobile No.</th>
									<th>Address</th>
									<th>Bank</th>
									<th>Branch</th>
									<th>Status</th>
									<th>Created Date</th>
									<th>Expired Date</th>
									<th>Renewed Date</th>
								</tr>
							</thead>
							<tbody ng-hide="renewedListLoad">
								<tr ng-repeat="customer in renewedCustomerList">
									<td>{{customer.fullName}}</td>
									<td>{{customer.mobileNumber}}</td>
									<td>{{customer.addressOne}}</td>
									<td>{{customer.bank}}</td>
									<td>{{customer.bankBranch}}</td>
									<td>{{customer.status}}</td>
									<td>{{customer.created}}</td>
									<td>{{customer.expiredDate}}</td>
									<td>{{customer.lastRenewDate}}</td>
								</tr>
							</tbody>
							<tbody ng-show="renewedListLoad">
								<tr>
									<td colspan="10"><i class="fa fa-spinner fa-spin fa-5x"></i></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</spr:page>
<script>
	function postForm() {
		$("#filterForm").submit();
	}
</script>