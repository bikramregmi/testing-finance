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


<spr:page header1="Add Country">
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<form action="addCountry" modelAttribute="country" method="post"
				class="col-md-12"
				style="float: none; margin-left: auto; margin-right: auto;">
				<div class="form-group">
					<label>Country Name</label> <input type="text" name="name"
						class="form-control input-sm" required="required"
						value="${country.name}">
					<p class="error">${error.name}</p>
				</div>
				<div class="form-group">
					<label>Iso Two</label> <input type="text" name="isoTwo"
						class="form-control input-sm" required="required"
						value="${country.isoTwo}">
					<p class="error">${error.isoTwo}</p>
				</div>
				<div class="form-group">
					<label>Iso Three</label> <input type="text" name="isoThree"
						class="form-control input-sm" required="required"
						value="${country.isoThree}">
					<p class="error">${error.isoThree}</p>
				</div>
				<div class="form-group">
					<label>Dial Code</label> <input type="text" name="dialCode"
						class="form-control input-sm" required="required"
						value="${country.dialCode}">
					<p class="error">${error.dialCode}</p>
				</div>

				<%-- <div class="form-group">
					<label>Status</label> <select name="status" id="status"
						class="form-control input-sm" required="required" r>
						<option value="Active" selected>Active</option>
						<option value="InActive">InActive</option>
					</select>
					<p class="error">${error.status}</p>


				</div> --%>
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Add
						Country</button>
				</div>
			</form>

		</div>
</spr:page>
