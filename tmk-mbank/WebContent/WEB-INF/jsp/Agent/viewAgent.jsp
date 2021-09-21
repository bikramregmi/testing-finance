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
<spr:page header1="View Agent">
	<div class="col-md-12">
		<div class="break"></div>
		<c:if test="${not empty message}">
			<p class="bg-success">
				<c:out value="${message}"></c:out>
			</p>
		</c:if>
		<table id="agentList">
			<tbody>
				<tr>
					<td><label>Agency Name</label></td>
					<td>${agent.agencyName}</td>
				</tr>
				<tr>
					<td><label>Agent Code</label></td>
					<td>${agent.agentCode}</td>
				</tr>
				<tr>
					<td><label>Agent Type</label></td>
					<td>${agent.agentType}</td>
				</tr>
				<tr>
					<td><label>Parent</label></td>
					<td>${agent.parentId}</td>
				</tr>
				<tr>
					<td><label>Address</label></td>
					<td>${agent.address}</td>
				</tr>
				<tr>
					<td><label>Land line</label></td>
					<td>${agent.landline}</td>
				</tr>
				<tr>
					<td><label>Mobile No</label></td>
					<td>${agent.mobileNo}</td>
				</tr>
				<tr>
					<td><label>City</label></td>
					<td>${agent.city}</td>
				</tr>
				<tr>
					<td><label>Timezone</label></td>
					<td>${agent.timeZone}</td>
				</tr>
				<tr><td><label>Destination Countries</label></td></tr>

			</tbody>
		</table>
		<table>
				<tr>
						<thead>
							<tr>
								<th width="100">Name</th>
								<th width="100">DialCode</th>
								<th width="100">Iso Two</th>
								<th width="100">Iso Three</th>
							</tr>
						</thead>
						</tr>
						<c:forEach items="${agent.destinationCountryList}" var="country">
							<tr>
								<td>${country.name}</td>
								<td>${country.dialCode}</td>
								<td>${country.isoTwo}</td>
								<td>${country.isoThree}</td>
							</tr>
						</c:forEach>
		</table>				
	</div>
</spr:page>