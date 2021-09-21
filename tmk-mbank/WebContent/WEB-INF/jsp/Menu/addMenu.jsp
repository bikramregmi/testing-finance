<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/custom.css" rel="stylesheet">
<link href="../css/starter-template.css" rel="stylesheet">
<!-- <script src ="../js/jquery.min.js"></script> -->
<!-- <script src ="../js/bootstrap.js"></script> -->

<spr:page header1="Add MenuTemplate">
	<div class="break"></div>
	<div class="row col-md-4">
	<form action="addMenu" modelAttribute="menu" method="post"
		class="col-md-12"
		style="float: none; margin-left: auto; margin-right: auto;">
		<div class="form-group">
			<label>Name</label> <input type="text" name="name"
				class="form-control input-sm" required="required"
				value="${menu.name}"> <label class="control-label"
				for="inputError1">${error.name}</label>
		</div>
		<div class="form-group">
			<label>URL</label> <input type="text" name="url"
				class="form-control input-sm" required="required"
				value="${menu.url}"> <label class="control-label"
				for="inputError1">${error.url}</label>
		</div>
		<div class="form-group">
			<label>Menu Type</label> <select name="superMenu" class="form-control input-sm menutypechooser">
				<option value="">Select Super Menu</option>
				<c:forEach items="${superMenu}" var="superMenu">
					<option value="${superMenu.url}">${superMenu.url}</option>
				</c:forEach>
			</select>
		</div>
		
		<div class="form-group">
			<button class="btn btn-primary btn-md btn-block btncu">Add
				Menu</button>
		</div>
	</form>
	</div>
	
</spr:page>