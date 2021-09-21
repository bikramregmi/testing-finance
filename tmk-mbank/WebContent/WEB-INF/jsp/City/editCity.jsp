<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/custom.css" rel="stylesheet">
<link href="../css/starter-template.css" rel="stylesheet">
<!-- <script src ="../js/jquery.min.js"></script> -->
<!-- <script src ="../js/bootstrap.js"></script> -->

<spr:page header1="Edit City">
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<form action="editCity" modelAttribute="city" method="post" class="col-md-12" style="float: none; margin-left: auto; margin-right: auto;">
				<input type="hidden" name="id" value="${city.id}">
				<div class="form-group">
					<label>City Name</label>
					<input type="text" name="name" class="form-control input-sm" required="required" value="${city.name}"> 
					<p class="error">${error.name}</p>
				</div>
				<div class="form-group">
					<label>Status</label>
					<select name="status" id="status" class="form-control input-sm" required="required" >
						<c:choose>
							<c:when test="${city.status eq 'Active'}">
								<option value="Active" selected>Active</option>
								<option value="Inactive">InActive</option>
							</c:when>
							<c:otherwise>
								<option value="Active" >Active</option>
								<option value="Inactive" selected>Inactive</option>
							</c:otherwise>
						
						</c:choose>
					</select>
					<p class="error">${error.status}</p>

				</div>
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Edit City</button>
				</div>
			</form>

		</div>
		</div>
</spr:page>
