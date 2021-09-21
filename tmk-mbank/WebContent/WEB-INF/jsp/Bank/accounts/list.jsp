<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<spr:page header1="Accounts Details">
	<div class="col-md-12">
		<div class="break"></div>
		<c:if test="${not empty message}">
			<div class="alert alert-success alert-dismissible">
				<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>${message}</div>
		</c:if>
		<div class="row">
			<div class="col-md-12">
				<ul class="nav nav-tabs">
					<li><a data-toggle="tab" href="#merchantAccounts">Merchant Accounts</a></li>
					<li class="active"><a data-toggle="tab" href="#accounts">Accounts</a></li>
				</ul>
			</div>
			<div class="col-md-12">
				<div class="tab-content">
					<div id="accounts" class="tab-pane fade in active">
						<div class="panel panel-default">
							<div class="panel-heading" style="background-color: #f5f5f5 !important;">
								<span>Accounts</span>
								<c:if test="${account eq null}">
									<a href="/bank/accounts?bank=${bank.swiftCode}" style="float: right; background-color: #18964f; border-color: #18964f;" class="btn btn-primary btn-xs">Add
										Account</a>
								</c:if>
							</div>
							<div class="panel-body">
								<table id="accountTable" class="table table-striped table-bordered table-condensed tablewrapper">
									<thead>
										<tr>
											<th>Bank Name</th>
											<th>mBank Account Number</th>
											<th>Channel Partner Account Number</th>
											<th>Bank Commission Account Number</th>
											<th>Bank Parking Account Number</th>
											<th>status</th>
											<th>Action</th>

										</tr>
									</thead>

									<tr>
										<td>${account.bank}</td>
										<td>${account.operatorAccountNumber}</td>
										<td>${account.channelPartnerAccountNumber}</td>
										<td>${account.bankPoolAccountNumber }</td>
										<td>${account.bankParkingAccountNumber }</td>
										<td>${account.status }</td>
										<c:choose>
											<c:when test="${account ne null}">
												<td><a href="/bank/editaccount?bank=${bank.swiftCode}"><i class="fa fa-pencil" data-toggle="tooltiip" data-placement="top" title="Edit Account"></i></a></td>
											</c:when>
											<c:otherwise>
												<td></td>
											</c:otherwise>
										</c:choose>
									</tr>
								</table>
							</div>
						</div>
					</div>
					<div id="merchantAccounts" class="tab-pane fade in">
						<div class="panel panel-default">
							<div class="panel-heading" style="background-color: #f5f5f5 !important;">
								<span>Merchant Accounts</span> <a href="/bank/merchantaccounts?bank=${bank.swiftCode}" style="float: right; background-color: #18964f; border-color: #18964f;"
									class="btn btn-primary btn-xs">Add Merchant Account</a>
							</div>
							<div class="panel-body">
								<table id="merchantAccountTable" class="table table-striped table-condensed table-bordered tablewrapper">
									<thead>
										<tr>
											<th>Merchant</th>
											<th>Merchant Account</th>
											<th>Status</th>
											<th>Action</th>
										</tr>
									</thead>
									<c:forEach var="account" items="${merchantAccountsList}">
										<tr>
											<td>${account.merchant}</td>
											<td>${account.accountNumber}</td>
											<td>${account.status}</td>
											<td><a href="/bank/editmerchantaccount?id=${account.id}"><i class="fa fa-pencil" data-toggle="tooltiip" data-placement="top" title="Edit Account"></i></a></td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</spr:page>
<style>
.nav-tabs>li {
	float: right !important;
}

.nav-tabs>li.active>a, .nav-tabs>li.active>a:focus, .nav-tabs>li.active>a:hover
	{
	background-color: #f5f5f5 !important;
	z-index: 1000;
}

.tablewrapper {
	overflow-y: auto;
}

.tablewrapper th {
	font-size: 12px;
}

.tablewrapper td {
	font-size: 12px;
}

.heading {
	float: left;
	color: #18964f;
	cursor: pointer;
}

.tab-content {
	margin-top: -1px;
}

.panel-default>.panel-heading {
	font-weight: bold;
	color: #73879C;
}
</style>
