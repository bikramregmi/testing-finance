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
<script src ="../js/bootstrap.js"></script>
<spr:page header1="List Customer" >
<div class="col-md-12">
	<div class="break"></div>
	<c:if test="${not empty message}">
   		<p class="bg-success"><c:out value="${message}" ></c:out></p>
	</c:if>
		
	<table id="customerList" class="table table-striped table-bordered table-condensed" > 
		<thead>  
			<tr>
				<th>Bank</th>
				<th>Sms Type</th>
				<th>Message</th>
				<th>Status</th>
			    <c:if test="${empty admin}">
   		           <th>Actions</th>
	             </c:if>
						
			</tr>  
		</thead>
		<tbody>   
			<c:if test="${fn:length(smsModeList) gt 0}">
				<c:forEach var="smsMode" items="${smsModeList}">
					<tr>
						<td>${smsMode.bank}</td>
						<td>${smsMode.smsType}</td>
						<td>${smsMode.message}</td>
						<td>${smsMode.status}</td>
						 <c:if test="${empty admin}">
						<td><a href="${pageContext.request.contextPath}/smsmode/edit?smsmode=${smsMode.id}"><i class="fa fa-pencil" 
								data-toggle="tooltip" data-placement="top" title="Edit SmsMode"></i></a>&nbsp;
								<a href="${pageContext.request.contextPath}/smsmode/deactive_active?smsmode=${smsMode.id}"><i class="fa fa-star-half-o" 
								data-toggle="tooltip" data-placement="top" title="active/deactive"></i></a>&nbsp;
									<a href="${pageContext.request.contextPath}/smsmode/remove?smsmode=${smsMode.id}"><i class="fa fa-times-circle" style="font-size:18px;color:red" 
								data-toggle="tooltip" data-placement="top" title="remove smsmode"></i></a>&nbsp;</td>
						</c:if>
			
					</tr>
				</c:forEach>
			</c:if>
		</tbody>  
	</table>  

</div>
</spr:page>

