<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags/channelPartner"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<spr:page header1="List Bank">
	<div class="col-md-12">
		<div class="break"></div>
		<c:if test="${not empty message}">
			<p class="bg-success">
				<c:out value="${message}"></c:out>
			</p>
		</c:if>
		<table id="bankList" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>Bank Name</th>
					<th>Address</th>
					<th>State</th>
					<th>City</th>
					<th>Swift Code</th>
					<th>Sms Count</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(bankList) gt 0}">
					<c:forEach var="bank" items="${bankList}">
						<tr>
							<td>${bank.name}</td>
							<td>${bank.address}</td>
							<td>${bank.state}</td>
							<td>${bank.city}</td>
							<td>${bank.swiftCode}</td>
							<td>${bank.smsCount}</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>
</spr:page>
<script>
	$(document).ready(function() {
		$("#bankList").dataTable();
	});
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
</style>