<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<spr:page header1="List Notification Group">
	<div class="col-md-12">
		<div class="break"></div>
		<c:if test="${not empty message}">
			<div class="alert alert-success alert-dismissible">
				<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>${message}</div>
		</c:if>
		<table class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>Name</th>
					<c:if test="${userType eq 'Admin'}">
					<th>Bank Name</th>
					</c:if>
					<th>Subscribed Customer</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(notificationGroupList) gt 0}">
					<c:forEach var="notificationGroup" items="${notificationGroupList}">
						<tr>
							<td>${notificationGroup.name}</td>
							<c:if test="${userType eq 'Admin'}">
					<td>${notificationGroup.bankName}</td>
					</c:if>
							<td>${notificationGroup.customerCount}</td>
							<td>
								<a href="${pageContext.request.contextPath}/notificationGroup/customer?notificationGroup=${notificationGroup.name}&bankCode=${notificationGroup.bankCode}" class="btn btn-success btn-xs">Manage Customer</a>
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>
</spr:page>
