<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<spr:page header1="Nea Office List">
	<div class="col-md-12">
		<div class="break"></div>
		<c:if test="${not empty message}">
			<p class="bg-success">
				<c:out value="${message}"></c:out>
			</p>
		</c:if>
		<table id="officetable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>Office Code</th>
					<th>Office</th>
					<th>status</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="office" items="${officeCodeList}">
					<tr>
						<td>${office.officeCode}</td>
						<td>${office.office}</td>
						<td>${office.status}</td>
						<td><a href="/officecode/changestatus?office=${office.id}" class="btn btn-primary btn-xs">Change Status</a> <a href="/officecode/edit?office=${office.id}"
							class="btn btn-success btn-xs"><i class="fa fa-pencil"></i> Edit</a> <a href="/officecode/delete?office=${office.id}" class="btn btn-danger btn-xs"><i
								class="fa fa-trash"></i> Delete</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</spr:page>
<script>
	$(document).ready(function() {
		$('#officetable').DataTable();
	});
</script>