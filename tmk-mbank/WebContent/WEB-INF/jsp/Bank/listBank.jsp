<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link href="/css/radinfo.css" rel="stylesheet"></link>
<spr:page header1="List Bank">
	<div class="col-md-12" ng-init="getInactiveBank()">
		<ul class="nav nav-tabs">
			<li><a data-toggle="tab" href="#inactive">Inactive</a></li>
			<li class="active"><a data-toggle="tab" href="#allBank">All</a></li>
		</ul>
	</div>
	<div class="col-md-12">
		<div class="tab-content">
			<div id="allBank" class="tab-pane fade in active">
				<div class="break"></div>
				<c:if test="${not empty message}">
					<p class="bg-success">
						<c:out value="${message}"></c:out>
					</p>
				</c:if>
				<div class="row">
					<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
						<div class="rad-info-box">
							<form action="/listBank" method="GET" id="filterForm">
								<div class="col-lg-3 col-md-3">
									<div class="form group">
										<label for="from_date">From Date:</label> <input type="date"
											name="fromDate" class="form-control" value="${fromDate}" "/>
									</div>
								</div>
								<div class="col-lg-3 col-md-3">
									<div class="form group">
										<label for="to_date">To Date:</label> <input type="date"
											name="toDate" class="form-control" value="${toDate}" />
									</div>
								</div>
								<div class="col-lg-3 col-md-3">
									<div class="form group">
										<label>Name</label> <input type="text" class="form-control"
											name="name" value="${name}">
									</div>
								</div>
								<div class="col-lg-2 col-md-2">
									<div class="form group">
										<label>Swift Code</label> <input type="text"
											class="form-control" name="swiftCode" value="${swiftCode}">
									</div>
								</div>
								<div class="col-lg-1 col-md-1">
									<div class="form group">
										<a onclick="postForm()"><i class="fa fa-search small"
											title="Search"></i></a>
									</div>
								</div>
							</form>
						</div>
					</div>
					<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
						<table id="bankList"
							class="table table-striped table-bordered table-condensed">
							<thead>
								<tr>
									<th>Bank Name</th>
									<th>Address</th>
									<th>Swift Code</th>
									<th>SMS Count</th>
									<th>License Count</th>
									<th>Channel Partner</th>
									<th>Remaining Balance</th>
									<th>Credit Limit</th>
									<th>CBS status</th>
									<th colspan="2">Action</th>

								</tr>
							</thead>
							<tbody>
								<c:if test="${fn:length(pageData.object) gt 0}">
									<c:forEach var="bank" items="${pageData.object}">
										<tr>
											<td>${bank.name}</td>
											<td>${bank.address}</td>
											<td>${bank.swiftCode}</td>
											<td>${bank.smsCount}</td>
											<td>${bank.licenseCount}</td>
											<td>${bank.channelPartnerName}</td>
											<td><fmt:formatNumber type="number"
													maxFractionDigits="2" value="${bank.remainingBalance}" /></td>
											<td><fmt:formatNumber type="number"
													maxFractionDigits="2" value="${bank.creditLimit}" /></td>
											<td ng-click="checkBankCbsStatus(${bank.id})"
												class="badgeDiv${bank.id}"><c:choose>
													<c:when test="${bank.cbsStatus eq 'Active'}">
														<span class="badge success-badge">${bank.cbsStatus}</span>
													</c:when>
													<c:otherwise>
														<span class="badge error-badge">${bank.cbsStatus}</span>
													</c:otherwise>
												</c:choose></td>
											<td><a href="editBank?bankId=${bank.id}"><i
													class="fa fa-pencil" data-toggle="tooltiip"
													data-placement="top" title="Edit Bank"></i></a> <a><i
													class="fa fa-envelope" data-toggle="modal"
													data-target="#loadSms" ng-click="loadBankId(${bank.id})"
													title="Add Sms Count"></i></a> <a><i
													class="fa fa-address-card" data-toggle="modal"
													data-target="#licenseCount"
													ng-click="loadBankId(${bank.id})" title="Add License Count"></i></a></td>
											<td><a href="listcommission?bank=${bank.id}"
												class="btn btn-primary btn-xs">Manage Commission</a> <a
												href="${pageContext.request.contextPath}/bank/manageaccounts?bank=${bank.swiftCode}"
												class="btn btn-primary btn-xs">Manage Accounts</a> <a
												href="${pageContext.request.contextPath}/cardlessbank/manage?bank=${bank.swiftCode}"
												class="btn btn-primary btn-xs">Manage Cardless Bank</a></td>
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
						<div class="row">
							<c:if test="${fn:length(pageData.pageList) gt 1}">
								<div class="pagination-block pull-left">
									<ul class="pagination pagination-sm no-margin pagingclass">
										<li><c:if test="${pageData.currentPage > 1}">
												<a
													href="/listBank?page=1&fromDate=${fromDate}&toDate=${toDate}&name=${name}&swiftCode=${swiftCode}"
													class="pn prev mypgactive"><<</a>
												<a
													href="/listBank?page=${pageData.currentPage-1}&fromDate=${fromDate}&toDate=${toDate}&name=${name}&swiftCode=${swiftCode}"
													class="pn prev mypgactive">Prev</a>
											</c:if></li>
										<c:forEach var="pagelist" items="${pageData.pageList}">
											<li><c:choose>
													<c:when test="${pagelist == pageData.currentPage}">
														<span>${pagelist}</span>
													</c:when>
													<c:otherwise>
														<a
															href="/listBank?page=${pagelist}&${fromDate}&toDate=${toDate}&name=${name}&swiftCode=${swiftCode}"
															class="mypgactive">${pagelist}</a>
													</c:otherwise>
												</c:choose></li>
										</c:forEach>
										<li><c:if
												test="${pageData.currentPage + 1 <= pageData.lastpage}">
												<a
													href="/listBank?page=${pageData.currentPage+1}&fromDate=${fromDate}&toDate=${toDate}&name=${name}&swiftCode=${swiftCode}"
													class="pn next mypgactive">Next</a>
												<a
													href="/listBank?page=${pageData.lastpage}&fromDate=${fromDate}&toDate=${toDate}&name=${name}&swiftCode=${swiftCode}"
													class="pn next mypgactive">>></a>
											</c:if></li>
									</ul>
								</div>
							</c:if>
						</div>
					</div>
				</div>
			</div>
			<div id="inactive" class="tab-pane fade in">
				<div class="row">
					<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12"
						ng-hide="inactiveBankLoad">
						<table id="inactiveBankList"
							class="table table-striped table-bordered table-condensed">
							<thead>
								<tr>
									<th>Bank Name</th>
									<th>Address</th>
									<th>Swift Code</th>
									<th>Channel Partner</th>
									<th>CBS status</th>

								</tr>
							</thead>
							<tbody>
								<tr ng-repeat="bank in inactiveBankList">
									<td>{{bank.name}}</td>
									<td>{{bank.address}}</td>
									<td>{{bank.swiftCode}}</td>
									<td>{{bank.channelPartnerName}}</td>
									<td ng-click="checkBankCbsStatus(bank.id)"
										class="badgeDiv{{bank.id}}"><c:choose>
											<c:when test="{{bank.cbsStatus = 'Active'}}">
												<span class="badge success-badge">{{bank.cbsStatus}}</span>
											</c:when>
											<c:otherwise>
												<span class="badge error-badge">{{bank.cbsStatus}}</span>
											</c:otherwise>
										</c:choose></td>
								</tr>
							</tbody>
						</table>
					</div>
					<div>
						<i class="fa fa-spinner fa-spin loadSpinner"
							ng-show="inactiveBankLoad"></i>
					</div>
				</div>
			</div>
		</div>
	</div>
</spr:page>

<div id="loadSms" class="modal fade" role="dialog" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">Load SMS</h4>
			</div>
			<div class="modal-body">
				<div class="input-group col-lg-12">
					<h4>{{myMessage}}</h4>
					<label>Add Sms Count</label> <input type="text" class="form-control" ng-model="smscount" placeholder="Add Sms Count"> <br />
					<label>Remarks</label> <textarea class="form-control" ng-model="remarks" placeholder="Remarks"></textarea> <br />
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" ng-click="addSmsCount()"
					class="btn btn-primary pull-left">Add SMS Count</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>

<div id="licenseCount" class="modal fade" role="dialog" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">Add License Count</h4>
			</div>
			<div class="modal-body">
				<div class="input-group col-lg-12">
					<h4>{{myMessage}}</h4>
					<label>Add License Count</label> <input type="text" class="form-control" ng-model="licenseCount" placeholder="Add License Count"> <br />
					<label>Remarks</label> <textarea class="form-control" ng-model="remarks" placeholder="Remarks"></textarea> <br />
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" ng-click="addLicenseCount()"
					class="btn btn-primary pull-left">Add License Count</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>

		</div>
	</div>
</div>

<div id="messageModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">SMS Count message</h4>
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

<script src="${pageContext.request.contextPath}/resources/js/bank.js"></script>
<script>
	function postForm() {
		$("#filterForm").submit();
	}
</script>
<style>
table {
	font-size: 12px;
}

td {
	word-wrap: break-word;
}

.btn-xs {
	font-size: 11px;
}

.badge {
	float: none !important;
	cursor: pointer;
}

.success-badge {
	background-color: #18964f;
}

.error-badge {
	background-color: red;
}

.nav-tabs>li {
	float: right !important;
}

.loadSpinner {
	font-size: 50px;
	left: 50%;
	position: absolute;
	top: 50px;
}
</style>