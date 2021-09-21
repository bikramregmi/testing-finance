<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/custom.css" rel="stylesheet">
<link href="../css/starter-template.css" rel="stylesheet">
<link rel="stylesheet" href="../css/jquery.dataTables.min.css">
</style>
<!-- <script src ="../js/jquery.min.js"></script> -->
<!-- <script src ="../js/bootstrap.js"></script> -->
<spr:page header1="List Super Agent">
	<div class="col-md-12">
		<div class="break"></div>
		<c:if test="${not empty message}">
			<p class="bg-success">
				<c:out value="${message}"></c:out>
			</p>
		</c:if>
		
		<table id="superAgentList" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>AccountNo</th>
					<th>Agent Code</th>
					<th>Agency Name</th>
					<th>Address</th>
					<th>Landline</th>
					<th>Mobile No</th>
					<th>Time Zone</th>
					<th>City </th>
					<th>Action</th>
					
				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(superAgentList) gt 0}">
					<c:forEach var="superAgent" items="${superAgentList}">
						<tr>
							<td>${superAgent.accountNo}</td>
							<td>${superAgent.agencyName}</td>
							<td>${superAgent.agentCode}</td>
							<td>${superAgent.address}</td>
							<td>${superAgent.landline}</td>
							<td>${superAgent.mobileNo}</td>
							<td>${superAgent.timeZone}</td>
							<td>${superAgent.city}</td>
							<td><a href="/editSuperAgent?superAgentId=${superAgent.id}"
								class="btn btn-info btn-xs"><i class="fa fa-pencil"></i>
									Edit </a></td>
							<td><a
								href="/deleteSuperAgent?superAgentId=${superAgent.id}"
								class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i>
									Delete </a></td>
						</tr>

					</c:forEach>
				</c:if>
			</tbody>

		</table>
</spr:page>
