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
<spr:page header1="List City" >
	<div class="col-md-12">
		<div class="break"></div>
		<c:if test="${not empty message}">
   		<p class="bg-success"><c:out value="${message}" ></c:out></p>
	</c:if>
		<table id="cityList" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>City Name</th>
					<th>State</th>
					<th>Status</th>
					<th>Action</th>
					
				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(cityList) gt 0}">
					<c:forEach var="city" items="${cityList}">
						<tr>
							<td>${city.name}</td>
							<td>${city.state}</td>
							<td>${city.status}</td>
							<td><a href="editcity?cityid=${city.id}"><i class="fa fa-pencil" data-toggle="tooltiip" data-placement="top" title="Edit City"></i></a></td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		</div>
</spr:page>