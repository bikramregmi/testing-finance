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
<spr:page header1="List Country">
	<div class="col-md-12">
		<div class="break"></div>
		<c:if test="${not empty message}">
			<p class="bg-success">
				<c:out value="${message}"></c:out>
			</p>
		</c:if>
		<table id="countryList" class="table table-striped">
			<thead>
				<tr>
					<th>Country Name</th>
					<th>Iso Two</th>
					<th>Iso Three</th>
					<th>Dial Code</th>
					<th>Currency</th>
					<th>Status</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(countryList) gt 0}">
					<c:forEach var="country" items="${countryList}">
						<tr>
							<td>${country.name}</td>
							<td>${country.isoTwo}</td>
							<td>${country.isoThree}</td>
							<td>${country.dialCode}</td>
							<td>${country.currencyDTO.currencyName}</td>
							<td>${country.status}</td>
							<td><a href="secondaryCurrency?countryId=${country.id}"
								class="btn btn-success btn-xs"><i class="fa fa-pencil"></i>
									Secondary Currency </a></td>
							<td><a href="editCountry?countryId=${country.id}"
								class="btn btn-info btn-xs"><i class="fa fa-pencil"></i>
									Edit </a></td>
							<td><a href="/deleteCountry?countryId=${country.id}"
								class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i>
									Delete </a></td>

						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
</spr:page>