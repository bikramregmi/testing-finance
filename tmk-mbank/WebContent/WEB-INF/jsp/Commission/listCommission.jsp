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
<!-- <script src ="../js/jquery.min.js"></script> -->
<!-- <script src ="../js/bootstrap.js"></script> -->
<spr:page header1="List Commission" >
	<div class="col-md-12">
		<div class="break"></div>
		<c:if test="${not empty message}">
   		<p class="bg-success"><c:out value="${message}" ></c:out></p>
	</c:if>
		<table id="commissionList" class="table table-striped">
			<thead>
				<tr>
					<th>Merchant</th>
					<th>Service</th>
					<th>Status</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(commissionList) gt 0}">
					<c:forEach var="commission" items="${commissionList}">
						<tr>
							<td>${commission.merchant}</td>
							<td>${commission.service}</td>
							<td>${commission.status}</td>
							<td>
								<a href="${pageContext.request.contextPath}/commission/edit?commission=${commission.id}">
								<i class="fa fa-pencil" data-toggle="tooltiip" data-placement="top" title="Edit Commission"></i></a>
								&nbsp;
								<a href="${pageContext.request.contextPath}/commission/details?commission=${commission.id}" ><i class="fa fa-arrow-right" 
										data-toggle="tooltip" data-placement="top" title="View Commission Details"></i></a>
								<a href="${pageContext.request.contextPath}/commission/remove?commission=${commision.id}"><i class="fa fa-trash-o" 
								data-toggle="tooltip" data-placement="top" title="View Merchant Details"></i></a>
							</td>
							
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		</div>
</spr:page>