<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<spr:page header1="List Webservice Client" >
	<div class="col-md-12">
		<div class="break"></div>
		<c:if test="${not empty message}">
   		<p class="bg-success"><c:out value="${message}" ></c:out></p>
	</c:if>
		<table id="clientListTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>Client ID</th>
					<th>Client Secret</th>
					<th>Authorized Grant</th>
					<th>Redirect URI</th>
					<th>Access Token Validity</th>
					<th>Authorities</th>
					<th>Action</th>
					
				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(oAuthClients) gt 0}">
					<c:forEach var="client" items="${oAuthClients}">
						<tr>
							<td>${client.clientId}</td>
							<td>${client.clientSecret }</td>
							<td>${client.authorizedGrantTypes}</td>
							<td>${client.webServerRedirectUri}</td>
							<td>${client.accessTokenValidity}</td>
							<td>${client.authorities}</td>
							<td></td>
							
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		</div>
</spr:page>
<style>
	table{
		table-layout: fixed; 
		font-size: 12px;
	}
	td{
		word-wrap: break-word;
	}
</style>
<script>
	$(document).ready(function() {

		$('#clientListTable').DataTable();
	});
</script>