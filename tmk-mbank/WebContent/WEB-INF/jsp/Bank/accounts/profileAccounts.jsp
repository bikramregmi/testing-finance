<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
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
<spr:page header1="List Profile Accounts">
	<div class="col-md-12">
		<div class="break"></div>
		<c:if test="${not empty message}">
			<p class="bg-success">
				<c:out value="${message}"></c:out>
			</p>
		</c:if>
		<c:if test="${account eq null}">
			<a href="/bank/addprofileaccounts" style="float: right;background-color:#18964f;border-color:#18964f;" class="btn btn-primary">Add Account</a>
		</c:if>
		<div class="row">
		<h3 class="heading">Accounts</h3>
		<table id="bankList" class="table table-striped table-bordered tablewrapper">
			<thead>
				<tr>
					<th>Registration Poll Account Number</th>
					<th>Pin Reset Poll Account Number</th>
					<th>Renew Poll Account Number</th>
					<th>status</th>
					<th>Action</th>

				</tr>
			</thead>

			<tr>
				<td>${account.registrationAccount}</td>
				<td>${account.pinResetAccount}</td>
				<td>${account.renewAccount}</td>
				<td>${account.status}</td>
				
				<c:choose>
					<c:when test="${account ne null}">
						<td><a href="/bank/editprofileaccounts"><i class="fa fa-pencil" data-toggle="tooltiip" data-placement="top" title="Edit Account"></i></a></td>
					</c:when>
					<c:otherwise>
						<td></td>
					</c:otherwise>
				</c:choose>
			</tr>
		</table>
		</div>
	</div>
</spr:page>
<style>
.tablewrapper {
	overflow-y: auto;
}

.tablewrapper th {
	font-size: 12px;
}

.heading {
	float: left;
	color: #18964f;
	cursor:pointer;
}
/* .tablewrapper td{
	font-size:10px;
} */
</style>
