<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/custom.css" rel="stylesheet">
<link href="../css/starter-template.css" rel="stylesheet">
<!-- <script src ="../js/jquery.min.js"></script> -->
<!-- <script src ="../js/bootstrap.js"></script> -->
<%@page import="org.slf4j.Logger"%>
<%@page  import="org.slf4j.LoggerFactory"%>
<%@page  import="org.slf4j.Logger"%>

<spr:page header1="Edit Customer" >
<div class="col-md-12">
	<div class="row col-md-4">
		<div class="break"></div>
	<form  action="editMenu" modelAttribute="menu" method="post" class="col-md-12" style="float: none; margin-left: auto; margin-right: auto;">
			<div class="form-group">
				<label>Name</label>
				<input type="text" name="name" class="form-control input-sm" required="required" value="${menu.name}">
				<label class="control-label" for="inputError1">${error.name}</label>
				
			</div>
			<input type="hidden" name="id"  value="${menu.id}">
			<div class="form-group">
				<label>URL</label>
				<input type="text" name="url" class="form-control input-sm" required="required" value="${menu.url}">
				<label class="control-label" for="inputError1">${error.url}</label>
			
			<div class="form-group">
				<label>Status</label>
				
					<select name="status" id="status" class="form-control input-sm" required="required" onClick="staff()">
						<c:choose>
							<c:when test="${menu.status=='Active'}">
								<option value="Active" selected>Active</option>
								<option value="Inactive" >InActive</option>
								<option value="Deleted" >Deleted</option>
							</c:when>
							<c:when test="${menu.status=='Inactive'}">
								<option value="Active" >Active</option>
								<option value="Inactive" selected>InActive</option>
								<option value="Deleted" >Deleted</option>
							</c:when>
							<c:when test="${menu.status=='Deleted'}">
								<option value="Active" >Active</option>
								<option value="Inactive" >InActive</option>
								<option value="Deleted" selected>Deleted</option>
							</c:when>
							<c:otherwise>
								<option value="Active" selected>Active</option>
								<option value="Inactive" >InActive</option>
								<option value="Deleted" >Deleted</option>
							</c:otherwise>
						</c:choose>
				</select>
				
				<label class="control-label" for="inputError1">${error.status}</label>
			</div>
			
			</div>
						<div class="form-group">
				<button class="btn btn-primary btn-md btn-block btncu">Edit Menu</button>
			</div>
		</form>
	</div>

</spr:page>	