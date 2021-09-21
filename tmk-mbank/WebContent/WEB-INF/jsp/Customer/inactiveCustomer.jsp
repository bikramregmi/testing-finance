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
<link href="/css/radinfo.css" rel="stylesheet"></link>
<link rel="stylesheet" href="../css/jquery.dataTables.min.css">

<!-- <script src ="../js/jquery.min.js"></script> -->
<!-- <script src ="../js/bootstrap.js"></script> -->
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
<spr:page header1="Inactive Customer" >
<div class="col-md-12">
	<div class="break"></div>
	<c:if test="${not empty message}">
   		<p class="bg-success"><c:out value="${message}" ></c:out></p>
	</c:if>
	<table id="customerList" class="table table-striped table-bordered table-condensed" > 
		<thead>  
			<tr>
				<th>First Name</th>
				<th>Middle Name</th>
				<th>Last Name</th>
				<th>Address One</th>
				<th>Mobile No.</th>
				<th>State</th>
				<th>City</th>
				<th>Status</th>
				<th>Action</th>						
			</tr>  
		</thead>
		<tbody>   
			<c:if test="${fn:length(customerList) gt 0}">
				<c:forEach var="customer" items="${customerList}">
					<tr>
						<td>${customer.firstName}</td>
						<td>${customer.middleName}</td>
						<td>${customer.lastName}</td>
						<td>${customer.addressOne}</td>
						<td>${customer.mobileNumber}</td>
						<td>${customer.state}</td>
						<td>${customer.city}</td>
						<td>${customer.status }</td>
						
						<td>
							<c:if test="${canApprove eq true}">
								<a href="${pageContext.request.contextPath}/customer/edit?customerId=${customer.id}"><i class="fa fa-pencil" 
								data-toggle="tooltip" data-placement="top" title="Edit Customer"></i></a>&nbsp;
								<c:if test="${customer.status eq 'Approved'}">
									<a href="#" class="statusUpdate"><i class="fa fa-minus-circle" 
								data-toggle="tooltip" data-placement="top" title="Block Customer"></i></a>&nbsp;	
								</c:if>
								<c:if test="${customer.status ne 'Approved'}">
									<a class="statusUpdate" href="#"><i class="fa fa-check" 
								data-toggle="tooltip" data-placement="top" title="Approve Customer"></i></a>&nbsp;
								</c:if>
							</c:if>
							<c:if test="${userAdmin ne 'true'}">
							<a href="/customer/edit?customer=${customer.uniqueId}"><i class="fa fa-pencil" data-toggle="tooltiip" data-placement="top" title="Edit Customer"></i></a>
							</c:if>
							<a href="${pageContext.request.contextPath}/customer/details?customer=${customer.uniqueId}" ><i class="fa fa-arrow-right" 
								data-toggle="tooltip" data-placement="top" title="View Customer Details"></i></a>
							<c:if test="${userAdmin ne 'true'}">
							<a href="${pageContext.request.contextPath}/customer/document/view?customerId=${customer.uniqueId}" ><i class="fa fa-file-image-o	" 
								data-toggle="tooltip" data-placement="top" title="View Customer Documents"></i></a>
							<button class="btn btn-primary" data-toggle="modal" data-target="#confirmResetPin" ng-click="setCustomerUniqueId('${customer.uniqueId}')">Reset Pin</button>
							</c:if>
						</td>
					</tr>
				  	<div class="row">
				     <div class="col-xs-6">
				        <div id="dialogForm" title="Update Customer" style="display:none">
				           <form id="addNoteForm">
				        	  <textarea name="remarks" id="remarks" style="width:98%;" class="required"></textarea>
				              <input type="hidden" name="forUser" readonly="readonly" value="${customer.id}" id="forUser">
				              <input type="hidden" name="userType" readonly="readonly" value="Customer" id="userType">
				              <c:if test="${customer.status eq 'Approved' }">
				              	<input type="hidden" name="action" readonly="readonly" value="Blocked" id="action">
				              </c:if>
				              <c:if test="${customer.status ne 'Approved' }">
				              	<input type="hidden" name="action" readonly="readonly" value="Approved" id="action">
				              </c:if>
				           </form>
				        </div>
				      </div>
				 <!-- <div class="col-xs-3"><div class="span3"/></div> -->
				      <div class="col-xs-6">
				         <div class="span6"></div>
				      </div>
				    </div>
				</c:forEach>
			</c:if>
		</tbody>  
	</table> 
	</div>
</spr:page>
<script
	src="${pageContext.request.contextPath}/resources/js/customer.js"></script>
<script>
	$(document).ready(function() {		
		init("${pageContext.request.contextPath}");
	});
</script>

<div id="confirmResetPin" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Reset Pin</h4>
      </div>
      <div class="modal-body">
        <p>Are you sure you want to reset pin for this customer?</p>
      </div>
      <div class="modal-footer">
       	<a href="${pageContext.request.contextPath}/customer/resetpin?customer={{customerUniqueId}}" type="button" class="btn btn-danger pull-left">Confirm</a>
        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
      </div>
    </div>

  </div>
</div>
<script>
function postForm(){
	$("#filterForm").submit();
}
</script>