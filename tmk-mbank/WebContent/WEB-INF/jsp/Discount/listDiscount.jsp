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
<spr:page header1="List Discount" >
	<div class="col-md-12">
		<div class="break"></div>
		<c:if test="${not empty message}">
   		<p class="bg-success"><c:out value="${message}" ></c:out></p>
	</c:if>
		<table id="discountList" class="table table-striped">
			<thead>
				<tr>
					<th> From Agent </th>
					<th> To Agent</th>
					<th>From Amount</th>
					<th>To Amount</th>
					<th>Overall Discount</th>
					<th>Overall Discount Flat</th>
					<th>DiscountType</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(discountList) gt 0}">
					<c:forEach var="discount" items="${discountList}">
						<tr>
							<td>${discount.fromAgent}</td>
							<td>${discount.toAgent}</td>
							<td>${discount.fromAmount}</td>
							<td>${discount.toAmount}</td>
							<td>${discount.overall_discount}</td>
							<td>${discount.overall_discountFlat}</td>
							<td>${discount.discountType}</td>
							<td><a href="editDiscount?discountId=${discount.id}"><img
									src="../../../images/edit.png" width="15" height="15" /></a></td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
</spr:page>