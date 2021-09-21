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
<spr:page header1="List Branch" >
	<div class="col-md-12">
		<div class="break"></div>
		<c:if test="${not empty message}">
   		<p class="bg-success"><c:out value="${message}" ></c:out></p>
	</c:if>
		<table id="branchList" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>Branch Name</th>
					<th>Branch Code</th>
					<th>Address</th>
					<th>State</th>
					<th>City</th>
					<th>Action</th>
					
				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(bankBranchList) gt 0}">
					<c:forEach var="branch" items="${bankBranchList}">
						<tr>
							<td>${branch.bank} - ${branch.name}</td>
							<td>${branch.branchCode}</td>
							<td>${branch.address}</td>
							<td>${branch.state }</td>
							<td>${branch.city}</td>
							<td><a href="/bank/branch/edit?branchId=${branch.id}"><i class="fa fa-pencil" data-toggle="tooltiip" data-placement="top" title="Edit Bank"></i></a></td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
</div>
</spr:page>
<div id="userDetailModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title" style="text-align:center;">User Detail For ${newBranchName}</h4>
      </div>
      <div class="modal-body">
        <h5><b>Username:</b> ${newBranchUsername}</h5>
        <h5><b>Password:</b> ${newBranchPassword}</h5>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>
<c:if test="${newUser}">
	<script>
		$(document).ready(function() {	
			$('#userDetailModal').modal('show');
		});
	</script>
</c:if>