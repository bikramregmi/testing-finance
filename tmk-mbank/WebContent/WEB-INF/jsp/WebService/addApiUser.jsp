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


<spr:page header1="Add User">
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<form action="addApiUser" modelAttribute="user" method="post"
				class="col-md-12"
				style="float: none; margin-left: auto; margin-right: auto;">
				<div class="form-group">
					<label>Api User</label> <input type="text" name="api_user"
						class="form-control input-sm" required="required"
						value="${user.api_user}">
					<p class="error">${error.api_user}</p>
				</div>
				<div class="form-group">
					<label>Api Password</label> <input type="text" name="api_password"
						class="form-control input-sm" value="${user.api_password}">
					<p class="error">${error.api_password}</p>
				</div>
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Add
						User</button>
				</div>
			</form>
		</div>
</spr:page>