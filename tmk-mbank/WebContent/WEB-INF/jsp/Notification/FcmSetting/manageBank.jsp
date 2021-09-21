<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" >
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<spr:page header1="Manage Bank">
	<div id="page-wrapper">
		<div class="container-fluid">
			<div class="row">
				<div class="panel panel-default">
					<div class="panel-heading">
						<p class="panel-title">Add Bank to FCM server</p>
					</div>
					<div class="panel-body">
						<c:if test="${not empty message}">
							<div class="alert alert-success alert-dismissible">
								<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>${message}</div>
						</c:if>
						<div class="detail-list col-md-4 col-sm-12">
							<form action="${pageContext.request.contextPath}/fcmServerSetting/bank/add" method="post">
								<ul class="list-group">
									<li class="list-group-item"><input type="hidden" name="serverKey" value="${fcmServerSetting.serverKey}" />
										<div class="row">
											<div class="col-md-3" style="margin-top: 5px;">
												<b>Bank:</b>
											</div>
											<div class="col-md-9">
												<select name="bank" class="selectpicker" data-width="100%" multiple data-live-search="true" data-actions-box="true" data-selected-text-format="count">
													<c:if test="${fn:length(notAddedBank) gt 0}">
														<c:forEach var="bank" items="${notAddedBank}">
															<option value="${bank.swiftCode}" data-subtext="${bank.swiftCode}">${bank.name}</option>
														</c:forEach>
													</c:if>
												</select>
												<p class="error">${error}</p>
											</div>
										</div></li>
									<li class="list-group-item">
										<button class="btn btn-primary btn-md btn-block btncu">Add Bank</button>
									</li>
								</ul>
							</form>
						</div>
						<div class="col-md-8 col-sm-12">
							<table class="responstable table">
								<thead>
									<tr>
										<th>Bank Name</th>
										<th>Action</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${fn:length(addedBank) gt 0}">
										<c:forEach var="bank"  items="${addedBank}" varStatus = "i">
											<tr>
												<td>${bank.name}</td>
												<td><a href="${pageContext.request.contextPath}/fcmServerSetting/bank/remove?serverKey=${fcmServerSetting.serverKey}&bankCode=${bank.swiftCode}"
													class="btn btn-danger btn-xs"> <i class="fa fa-trash-o"></i> Remove
												</a></td>
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</spr:page>
<style>
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
</style>

