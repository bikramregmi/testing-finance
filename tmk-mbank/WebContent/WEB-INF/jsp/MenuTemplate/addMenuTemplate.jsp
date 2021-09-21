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

<spr:page header1="Add MenuTemplate">
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<form action="addMenuTemplate" modelAttribute="menuTemplate"
				method="post" class="col-md-12"
				style="float: none; margin-left: auto; margin-right: auto;">
				<div class="form-group">
					<label>Name</label> <input type="text" name="name"
						class="form-control input-sm" required="required"
						value="${menuTemplate.name}"> <label class="control-label"
						for="inputError1">${error.name}</label>
						<p class="error">${error.name}</p>
				</div>
				
				<div class="form-group">
				<label>User Type</label>
				<select	name="userType" class="form-control input-sm" required="required" id="">
						<c:if test="${fn:length(userTypeList) gt 0}">
							<option>Select User Type</option>
							<c:forEach var="usertype" items="${userTypeList}">
								<option value="${usertype}">${usertype}</option>
							</c:forEach>
						</c:if>
					</select> 
					</div>
				
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Add
						MenuTemplate</button>
				</div>
			</form>
		</div>
	</div>
</spr:page>