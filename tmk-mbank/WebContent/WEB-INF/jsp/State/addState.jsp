<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/custom.css" rel="stylesheet">
<link href="../css/starter-template.css" rel="stylesheet">
<!-- <script src ="../js/jquery.min.js"></script> -->
<!-- <script src ="../js/bootstrap.js"></script> -->


<spr:page header1="Add State">
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<form action="addState" modelAttribute="state" method="post"
				class="col-md-12"
				style="float: none; margin-left: auto; margin-right: auto;">
				<div class="form-group">
					<label>State Name</label> <input type="text" name="name"
						class="form-control input-sm" required="required"
						value="${state.name}">
					<p class="error">${error.name}</p>
				</div>
				<div class="form-group">
					<label>Country</label> 
					<select name="country"
						class="form-control input-sm" required="required">
						<c:if test="${fn:length(countryList) gt 0}">
						<c:forEach var="country" items="${countryList}">
								<c:choose>
									<c:when
										test="${country.name == state.country}">
										<option value="${country.name}" selected>${country.name}</option>
									</c:when>
									<c:otherwise>
										<option value="${country.name}">${country.name}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							</c:if>
					</select> 
					<p class="error">${error.country}</p>
				</div>
				<%-- <div class="form-group">
					<label>Status</label> <select name="status" id="status"
						class="form-control input-sm" required="required">
						<option value="Active" selected>Active</option>
						<option value="InActive">InActive</option>
					</select>
					<p class="error">${error.status}</p>


				</div> --%>
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Add
						State</button>
				</div>
			</form>

		</div>
</spr:page>
