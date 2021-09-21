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
<spr:page header1="Add Aml">
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<form action="addAml" modelAttribute="aml" method="post"
				class="col-md-12"
				style="float: none; margin-left: auto; margin-right: auto;">
				<div class="form-group">
					<label>Source</label> <input type="text" name="source"
						class="form-control input-sm" required="required"
						value="${aml.source}">
					<p class="error">${error.source}</p>
				</div>
				<div class="form-group">
					<label>Aml Name</label> <input type="text" name="name"
						class="form-control input-sm" required="required"
						value="${aml.name}">
					<p class="error">${error.name}</p>
				</div>
				<div class="form-group">
					<label>Alias</label> <input type="text" name="alias"
						class="form-control input-sm" required="required"
						value="${aml.alias}">
					<p class="error">${error.alias}</p>
				</div>
				<div class="form-group">
					<label>Address</label> <input type="text" name="address"
						class="form-control input-sm" required="required"
						value="${aml.address}">
					<p class="error">${error.address}</p>
				</div>
				<div class="form-group">
					<label>Documents</label> <input type="text" name="documents"
						class="form-control input-sm" required="required"
						value="${aml.documents}">
					<p class="error">${error.documents}</p>
				</div>
				<%-- <div class="form-group">
					<label>Date</label> <input type="date" name="date"
						class="form-control input-sm" required="required"
						value="${aml.date}">
					<p class="error">${error.date}</p>
				</div> --%>
				<div class="form-group">
					<label>User</label> 
					<select name="uploadedBy"
						class="form-control input-sm" required="required">
						<c:if test="${fn:length(userList) gt 0}">
						<c:forEach var="user" items="${userList}">
								<c:choose>
									<c:when
										test="${user.username == aml.uploadedBy}">
										<option value="${user.username}" selected>${user.username}</option>
									</c:when>
									<c:otherwise>
										<option value="${user.username}">${user.username}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							</c:if>
					</select> 
					<p class="error">${error.uploadedBy}</p>
				</div>
				<div class="form-group gptype">
					<label>Group Type</label> <select name="type" id="type"
						class="form-control input-sm" required="required" >
						<option value="Country" selected>Country</option>
						<option value="SuperAgent">SuperAgent</option>
						<option value="Agent">Agent</option>
					</select>
					<p class="error">${error.type}</p>
				</div>
				<div class="form-group">
					<label>Status</label> 
					<select name="status" id="status" class="form-control input-sm" required="required">
						<option value="Active" selected>Active</option>
						<option value="InActive">InActive</option>
					</select>
					<p class="error">${error.status}</p>
				</div>
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Add
						Aml</button>
				</div>
			</form>

		</div>
</spr:page>
