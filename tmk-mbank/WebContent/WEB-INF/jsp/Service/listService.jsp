<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
.fa-icon-success{
	font-size:23px;
	color: #29e77c;
}
.fa-icon-error{
	font-size:23px;
	color: red;
}
</style>
<spr:page header1="List Service">
	<div class="col-md-12">
		<div class="break"></div>
		<c:if test="${not empty message}">
			<p class="bg-success">
				<c:out value="${message}"></c:out>
			</p>
		</c:if>
		<table id="serviceList" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>Service Name</th>
					<th>Unique Identifier</th>
					<th>Web View</th>
					<th>Status</th>
					<th colspan="2">Action</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(services) gt 0}">
					<c:forEach var="service" items="${services}">
						<tr>
							<td>${service.service}</td>
							<td>${service.uniqueIdentifier}</td>
							<td><c:choose>
								<c:when test="${service.webView}">
									<i class="fa fa-check fa-icon-success"></i>
								</c:when>
								<c:otherwise>
									<i class="fa fa-times fa-icon-error"></i>
								</c:otherwise>
							</c:choose></td>
							<td>${service.status}</td>
							<td>
							<a href="/merchant/service/edit?serviceId=${service.id}"
								class="btn btn-info btn-xs" title="Edit"><i class="fa fa-pencil"></i>
									 </a>

							<a href="/deleteService?serviceId=${service.id}"
								class="btn btn-danger btn-xs" title="Delete"><i class="fa fa-trash-o"></i>
									 </a></td>
								<td>	<a href="manageprovider?serviceid=${service.id}" class=" btn nav-blue">Manage Provider</a></td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>
</spr:page>