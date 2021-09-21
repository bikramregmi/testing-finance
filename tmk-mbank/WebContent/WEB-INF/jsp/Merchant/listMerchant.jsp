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
<link rel="stylesheet" hre="../css/jquery.dataTables.min.css"></style>  
<!-- <script src ="../js/jquery.min.js"></script> -->
<!-- <script src ="../js/bootstrap.js"></script> -->
<spr:page header1="List Merchant" >
<div class="col-md-12">
	<div class="break"></div>
	<c:if test="${not empty message}">
   		<p class="bg-success"><c:out value="${message}" ></c:out></p>
	</c:if>
		
	<table id="merchantList" class="table table-striped table-bordered table-condensed" > 
		<thead>  
			<tr>
				<th>Merchant</th>
				<th>Registration Number</th>
				<th>Vat Number</th>
				<th>Owner Name</th>
				<th>Description</th>
				<th>Address</th>
				<th>City</th>
				<th>State</th>
				<th>Mobile Number</th>
				<th>Land Line</th>
				<th>Status</th>	
				<th>Action</th>					
			</tr>  
		</thead>
		<tbody>   
			<c:if test="${fn:length(merchantList) gt 0}">
				<c:forEach var="merchant" items="${merchantList}">
					<tr>
						<td>${merchant.name}</td>
						<td>${merchant.registrationNumber}</td>
						<td>${merchant.vatNumber}</td>
						<td>${merchant.ownerName}</td>
						<td>${merchant.description}</td>
						<td>${merchant.address}</td>
						<td>${merchant.city}</td>
						<td>${merchant.state}</td>
						<td>${merchant.mobileNumber}</td>
						<td>${merchant.landLine}</td>
						<td>${merchant.status}</td>
						<td>
						<a href="${pageContext.request.contextPath}/merchant/manageService?merchantId=${merchant.id}">
						<i class="fa fa-plus" data-toggle="tooltiip" data-placement="top" title="Manage Service"></i></a>
						<a href="${pageContext.request.contextPath}/merchant/edit?merchantId=${merchant.id}">
						<i class="fa fa-pencil" data-toggle="tooltiip" data-placement="top" title="Edit Merchant"></i></a>
						&nbsp;
						<%-- <a href="${pageContext.request.contextPath}/customer/details/?customerId=${customer.id}" ><i class="fa fa-arrow-right" 
								data-toggle="tooltip" data-placement="top" title="View Merchant Details"></i></a> --%>
						<a href="/deleteMerchant?merchantId=${merchant.id}"><i class="fa fa-trash-o" 
						data-toggle="tooltip" data-placement="top" title="View Merchant Details"></i></a></td>
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
$(document).ready(function(){
	$("#merchantList").DataTable();
})

</script>