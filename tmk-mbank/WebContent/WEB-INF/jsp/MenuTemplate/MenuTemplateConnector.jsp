<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<spr:page header1="List Menu">
	<div class="col-md-12">
		<div class="break"></div>
			<c:if test="${not empty message}">
			<div class="alert alert-success alert-dismissible">
				<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>${message}</div>
		</c:if>

		<div class="panel panel-default">
			<div class="panel-heading">
				<p class="panel-title">Manage Menu for ${menuTemplate.name}</p>
			</div>
			<div class="panel-body">
				<div class="col-md-4 col-sm-12">
					<form action="addMenutotemplate" method="post">
						<input type="hidden" name="templateId" value="${menuTemplate.id}" />
						<ul class="list-group">
							<li class="list-group-item"><b>Template Name : </b>${menuTemplate.name}</li>
							<li class="list-group-item">
								<div class="row">
									<div class="col-md-3">
										<b style="line-height: 2.7;">Menu :</b>
									</div>
									<div class="col-md-9">
										<select name="menuUrl" class="form-control input-sm menutypechooser selectpicker" data-live-search="true">
											<option value="" selected disabled>Select a Menu</option>
											<c:forEach items="${menu}" var="menu">
												<option value="${menu.url}">${menu.name}(${menu.url})</option>
											</c:forEach>
										</select>
										<p class="error">${error}</p>
									</div>
								</div>
							</li>
							<li class="list-group-item"><button class="btn btn-info btn-md btn-block btncu">Add Menu</button></li>
						</ul>
					</form>
				</div>
				<div class="col-md-8 col-sm-12">
					<table id="menuTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th>Name</th>
								<th>URL</th>
								<th>Status</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${fn:length(Addedmenu) gt 0}">
								<c:forEach var="Addedmenu" items="${Addedmenu}">
									<tr>
										<td>${Addedmenu.name}</td>
										<td>${Addedmenu.url}</td>
										<td>${Addedmenu.status}</td>
										<td><a href="${pageContext.request.contextPath}/templatemenu/remove?menu_id=${Addedmenu.id}&template_id=${menuTemplate.id}" class="btn btn-danger btn-xs">remove</a></td>
									</tr>
								</c:forEach>
							</c:if>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</spr:page>
<script>
	$(document).ready(function() {
		$('#menuTable').dataTable();
	})
</script>
<style>
.dataTables_filter, .dataTables_length,.dataTables_info {
	width: 100% !important;
}
.dataTables_paginate>.pagination{
margin-top:0px !important;
}
</style>
<%-- <script src="${pageContext.request.contextPath}/js/Angular/menu.js"></script> --%>
