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
		
	<table id="serviceCategoryList" class="table table-striped table-bordered table-condensed" > 
		<thead>  
			<tr>
				<th>Service Category Name</th>
				<th>Action</th>
			</tr>  
		</thead>
		<tbody>   
			<c:if test="${fn:length(serviceCategory) gt 0}">
				<c:forEach var="serviceCat" items="${serviceCategory}">
					<tr>
						<td>${serviceCat.name}</td>
						<td>
						<a data-toggle="modal" data-target="#editModal" ng-click="loadCategoryId(${serviceCat.id})">
						<i class="fa fa-pencil" data-toggle="tooltiip" data-placement="top" title="Edit Service Category"></i></a>
						&nbsp;
						<a href="/deleteServiceCategory?serviceCategoryId=${serviceCat.id}"><i class="fa fa-trash-o" 
						data-toggle="tooltip" data-placement="top" title="View Category Details"></i></a></td> 
					</tr>
				</c:forEach>
			</c:if>
		</tbody>  
	</table> 
	
	<!-- Modal -->
	<div id="editModal" class="modal fade" role="dialog" tabindex="-1">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">Edit Service Category</h4>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="input-group col-lg-12">
						<h4>{{myMessage}}</h4>
						<div class="input-group col-lg-12">
							<label>Name</label> <input type="text"
								class="form-control" ng-model="name"
								placeholder="Name"> <br />
							{{serviceCategoryError.name}}
						</div>
						<br />
						
						<div class="modal-footer">
						<button type="button" ng-click="editServiceCategory()"
							class="btn btn-primary btn-modal pull-left">Save</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Close</button>
						</div>
					</div>

				</div>
			</div>
		</div> 

</spr:page>
</div>
