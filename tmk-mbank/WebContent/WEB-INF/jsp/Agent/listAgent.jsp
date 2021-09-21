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
<spr:page header1="List Agent">
	<div class="col-md-12">
		<div class="break"></div>
		<c:if test="${not empty message}">
			<p class="bg-success">
				<c:out value="${message}"></c:out>
			</p>
		</c:if>
		<table id="agentList" class="table table-striped">
			<thead>
				<tr>
					<th>Agency Name</th>
					<th>Agent Code</th>
					<th>Agent Type</th>
					<th>Parent</th>
					<th>Address</th>
					<th>Land line</th>
					<th>Mobile No</th>
					<th>City</th>
					<th>Time Zone</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(agentList) gt 0}">
					<c:forEach var="agent" items="${agentList}">
						<tr>
							<td>${agent.agencyName}</td>
							<td>${agent.agentCode}</td>
							 <td>${agent.agentType}</td>
							<td>${agent.parentId}</td>
							<td>${agent.address}</td>
							<td>${agent.landline}</td>
							<td>${agent.mobileNo}</td>
							<td>${agent.city}</td>
							<td>${agent.timeZone}</td>
							<%-- <td>
							<a href="destinationCountry?agentId=${agent.id}" class="btn btn-success btn-xs"><i class="fa fa-pencil"></i>Destination Country </a>
							</td> --%>
							<td><a href="/viewAgent?agentId=${agent.id}"
								class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i>
									View </a></td>
							<td><a href="editAgent?agentId=${agent.id}"
								class="btn btn-info btn-xs"><i class="fa fa-pencil"></i>
									Edit </a></td>

							<td><a href="/deleteAgent?agentId=${agent.id}"
								class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i>
									Delete </a></td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>
</spr:page>