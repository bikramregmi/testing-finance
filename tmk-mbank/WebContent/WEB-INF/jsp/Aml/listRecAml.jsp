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
<spr:page header1="List Aml" >
	<div class="col-md-12">
		<div class="break"></div>
		<c:if test="${not empty message}">
   		<p class="bg-success"><c:out value="${message}" ></c:out></p>
	</c:if>
		<table id="amlList" class="table table-striped">
			<thead>
				<tr>
					<th>Source</th>
					<th>Aml Name</th>
					<th>Alias</th>
					<th>Address</th>
					<th>Documents</th>
					<th>Status</th>
					<th>Uploaded By</th>
					<th>Action</th>
					
				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(amlList) gt 0}">
					<c:forEach var="aml" items="${amlList}">
						<tr>
							<td>${aml.source}</td>
							<td>${aml.name}</td>
							<td>${aml.alias}</td>
							<td>${aml.address}</td>
							<td>${aml.documents}</td>
							<td>${aml.status}</td>
							<td>${aml.uploadedBy}</td>
							<td><a href="editAml?amlId=${aml.id}"><img
									src="../../../images/edit.png" width="15" height="15" /></a></td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
</spr:page>