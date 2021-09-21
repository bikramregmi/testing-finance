<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<spr:page header1="List Cancel Request">
	<div class="col-md-12">
		<div class="break"></div>
		<c:if test="${not empty message}">
			<p class="bg-success">
				<c:out value="${message}"></c:out>
			</p>
		</c:if>
		<table id="cancelRequestTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>Requested Date</th>
					<th>Customer Name</th>
					<th>Mobile Number</th>
					<th>Bank</th>
					<th>Branch</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(cancelRequestlist) gt 0}">
					<c:forEach var="cancelRequest" items="${cancelRequestlist}">
						<tr>
							<td>${cancelRequest.requestedDate}</td>
							<td>${cancelRequest.customerName}</td>
							<td>${cancelRequest.mobileNumber}</td>
							<td>${cancelRequest.bank}</td>
							<td>${cancelRequest.bankbranch}</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>
</spr:page>
<script>
$(document).ready(function(){
	$('#cancelRequestTable').DataTable();
});
</script>