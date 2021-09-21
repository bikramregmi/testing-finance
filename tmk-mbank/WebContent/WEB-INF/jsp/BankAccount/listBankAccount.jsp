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
<spr:page header1="List BankAccount" >
	<div class="col-md-12">
		<div class="break"></div>
		<table id="bankAccountList" class="table table-striped">
			<thead>
				<tr>
					<th>BankAccount Name</th>
					<th>Address</th>
					<th>City</th>
					<th>Swift Code</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(bankAccountList) gt 0}">
					<c:forEach var="bankAccount" items="${bankAccountList}">
						<tr>
							<td>${bankAccount.name}</td>
							<td>${bankAccount.address}</td>
							<td>${bankAccount.city}</td>
							<td>${bankAccount.swiftCode}</td>
							<td><a href="editBankAccount?bankAccountId=${bankAccount.id}"><img
									src="../../../images/edit.png" width="15" height="15" /></a></td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
</spr:page>