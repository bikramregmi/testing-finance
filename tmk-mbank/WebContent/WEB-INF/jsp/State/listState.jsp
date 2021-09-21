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
<spr:page header1="List State">
	
	<style>
.add-on .input-group-btn>.btn {
	border-left-width: 0;
	left: -2px;
	-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
	box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
}
/* stop the glowing blue shadow */
.add-on .form-control:focus {
	box-shadow: none;
	-webkit-box-shadow: none;
	border-color: #cccccc;
}

.form-control {
	width: 20%
}

.navbar-nav>li>a {
	border-right: 1px solid #ddd;
	padding-bottom: 15px;
	padding-top: 15px;
}

.navbar-nav:last-child {
	border-right: 0
}

.mypgactive{
	background : #2A3F54 !important;
	cursor:pointer !important;
	color:white !important;
}

</style>
	<div class="col-md-12">
		<div class="break"></div>
		<c:if test="${not empty message}">

			<p class="bg-success">
				<c:out value="${message}"></c:out>
			</p>
		</c:if>

		<form class="navbar-form pull-right" action="/listState" method="get"
			role="search">
			<div class="input-group add-on">
				<input class="form-control" value="${searchWord}"
					style="height: 28px;" name="name" placeholder="Search"
					name="srch-term" id="srch-term" type="text">
				<div class="input-group-btn ">
					<button class="btn btn-default" type="submit">
						<i class="glyphicon glyphicon-search"></i>
					</button>
				</div>
			</div>
		</form>
	
		<table id="stateList" class="table table-striped table-bordered table-condensed">

			<thead>
				<tr>
					<th>State Name</th>
					<th>Country</th>
					<th>Status</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(stateList.object) gt 0}">
					<c:forEach var="state" items="${stateList.object}">
						<tr>
							<td>${state.name}</td>
							<td>${state.country}</td>
							<td>${state.status}</td>
							<td><a href="editstate?stateid=${state.id}"><i
									class="fa fa-pencil" data-toggle="tooltiip"
									data-placement="top" title="Edit State"></i></a></td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>

		<c:if test="${fn:length(stateList.pageList) gt 1}">
			<div class="pagination-block pull-left">

				<ul class="pagination pagination-sm no-margin pagingclass">
					<li><c:if test="${stateList.currentPage > 1}">
							<a href="/listState?pageNo=${stateList.currentPage-1}&name=${searchWord}"
								class="pn prev mypgactive">Prev</a>
						</c:if></li>

					<c:forEach var="pagelist" items="${stateList.pageList}">
						<li><c:choose>

								<c:when test="${pagelist == stateList.currentPage}">

									<span>${pagelist}</span>

								</c:when>
								<c:otherwise>

									<a href="/listState?pageNo=${pagelist}&name=${searchWord}"
										class="mypgactive">${pagelist}</a>

								</c:otherwise>
							</c:choose></li>
					</c:forEach>
					<li><c:if test="${stateList.currentPage + 1 <= stateList.lastpage}">
							<a href="/listState?pageNo=${stateList.currentPage+1}&name=${searchWord}"
								class="pn next mypgactive">Next</a>
						</c:if></li>
				</ul>
			</div>

		</c:if>

	</div>
</spr:page>