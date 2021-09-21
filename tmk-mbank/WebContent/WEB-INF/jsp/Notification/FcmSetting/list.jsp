<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<spr:page header1="FCM Server List">
	<div class="col-md-12">
		<div class="break"></div>
		<c:if test="${not empty message}">
			<div class="alert alert-success alert-dismissible">
				<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>${message}</div>
		</c:if>
		<table class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>Server Key</th>
					<th>Server ID</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(fcmServerSettingList) gt 0}">
					<c:forEach var="serverSetting" items="${fcmServerSettingList}">
						<tr>
							<td>${serverSetting.serverKey}</td>
							<td>${serverSetting.serverId}</td>
							<td>
								<a href="${pageContext.request.contextPath}/fcmServerSetting/bank/add?key=${serverSetting.serverKey}" class="btn btn-success btn-xs">Manage Bank</a>
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>
</spr:page>
