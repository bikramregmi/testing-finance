<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/custom.css" rel="stylesheet">
<link href="../css/starter-template.css" rel="stylesheet">
<!-- <script src ="../js/jquery.min.js"></script> -->
<!-- <script src ="../js/bootstrap.js"></script> -->


<spr:page header1="Change User Password">
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<form action="changeUserPassword" modelAttribute="user" method="post"
				class="col-md-12"
				style="float: none; margin-left: auto; margin-right: auto;">
				<div class="form-group">
				<input type="hidden" name="operation" value="password">
				<input type="hidden" name="id"  value="${user.id}">
					<label>New Password</label> <input type="password" name="password"
						class="form-control input-sm" required="required" 
						>
					<p class="error">${error.password}</p>
				</div>
				<div class="form-group">
					<label>Re-Password</label>
					 <input type="password" name="repassword"
						class="form-control input-sm" required="required" 
						>
					<p class="error">${error.repassword}</p>
				</div>
				
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Change Password
						</button>
				</div>
			</form>
		</div>
</spr:page>