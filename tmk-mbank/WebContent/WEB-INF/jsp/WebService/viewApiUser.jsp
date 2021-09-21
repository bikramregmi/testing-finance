<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/custom.css" rel="stylesheet">
<link href="../css/starter-template.css" rel="stylesheet">
<link rel="stylesheet" href="../css/jquery.dataTables.min.css"></style>  
<!-- <script src ="../js/jquery.min.js"></script> -->
<!-- <script src ="../js/bootstrap.js"></script> -->
<spr:page header1="List API User" >
<div class="col-md-12">
	<div class="break"></div>
	<c:if test="${not empty msg}">
   		<p class="bg-success"><c:out value="${msg}" ></c:out></p>
	</c:if>
	<table id="userList" class="table table-striped table-bordered table-condensed" > 
		<thead>  
			<tr>
				<th>API User</th>
				<th>API password</th>
				<th>Access Key</th>							
			</tr>  
		</thead>
		<tbody>   
			<c:if test="${fn:length(serviceList) gt 0}">
				<c:forEach var="service" items="${serviceList}">
					<tr>
						<td>${service.api_user}</td>
						<td>${service.api_password}</td>
						<td>${service.access_key}</td>
						<!--td>
						<a href="changeUserPassword?userId=${user.id}" class="btn btn-success btn-xs"><i class="fa fa-pencil"></i> Change Password </a>
						</td>
						<td>
						<a href="editUser?userId=${user.id}" class="btn btn-info btn-xs"><i class="fa fa-pencil"></i> Edit </a>
						</td>
						<td><a href="/deleteUser?userId=${user.id}" class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i> Delete </a></td-->
					</tr>
				</c:forEach>
			</c:if>
		</tbody>  
	</table>  

</spr:page>
