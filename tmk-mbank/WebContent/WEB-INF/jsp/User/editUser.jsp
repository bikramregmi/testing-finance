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


<spr:page header1="Edit User">
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<form action="editUser" modelAttribute="user" method="post"
				class="col-md-12"
				style="float: none; margin-left: auto; margin-right: auto;">
				<div class="form-group">
				<input type="hidden" name="operation" value="edit">
				<input type="hidden" name="id"  value="${user.id}">
					<label>Username</label> <input type="text" name="username"
						class="form-control input-sm" required="required" readonly="readonly"
						value="${user.username}">
					<p class="error">${error.username}</p>
				</div>
				
				<%-- <div class="form-group">
					<label>User Type</label> <select name="userType" id="userType"
						class="form-control input-sm" required="required">
						<c:choose>
							<c:when test="${user.userType == 'Super_Agent'}">
							<option value="Admin">Admin</option>
							<option value="Super_Agent" selected>Super Agent</option>
								<option value="Agent">Agent</option>
								<option value="Customer">Customer</option>
							</c:when>
							<c:when test="${user.userType == 'Agent'}">
							    <option value="Admin">Admin</option>
							    <option value="Super_Agent">Super Agent</option>
								<option value="Agent" selected>Agent</option>
								<option value="Customer">Customer</option>
							</c:when>
							<c:when test="${user.userType == 'Customer'}">
								<option value="Admin">Admin</option>
							    <option value="Super_Agent">Super Agent</option>
								<option value="Agent">Agent</option>
								<option value="Customer" selected>Customer</option>
							</c:when>
							<c:when test="${user.userType == 'Admin'}">
							     <option value="Admin" selected="selected">Admin</option>
								<option value="Super_Agent">Super Agent</option>
								<option value="Agent">Agent</option>
								<option value="Customer">Customer</option>
							</c:when>
							<c:otherwise>
								<option value="Admin" selected>Admin</option>
								<option value="Super_Agent" selected>Super Agent</option>
								<option value="Agent" selected>Agent</option>
								<option value="Customer" selected>Customer</option>
							</c:otherwise>
						</c:choose>
					</select>
					<p class="error">${error.userType}</p>
				</div> --%>
				<div id="form-group">
					<label>User Group</label> <select name="userTemplate" id="superAgentId" 
							class="form-control input-sm">
							
							<c:if test="${fn:length(userTemplate) gt 0}">
								
								<c:forEach var="userTemplate" items="${userTemplate}">
										<c:choose>
											<c:when test="${userTemplate.userTemplateName eq  user.userTemplate.userTemplateName}">
												
												<option value="${userTemplate.userTemplateName}" selected>${userTemplate.userTemplateName}</option>
											</c:when>
											<c:otherwise>
												<option value="${userTemplate.userTemplateName}" >${userTemplate.userTemplateName}</option>
											</c:otherwise>
										</c:choose>
											
								</c:forEach>
							</c:if>
						</select>
						<p class="error">${error.userTemplate}</p>
				</div>
				<div class="form-group">
					<label>Status</label> <select name="status" id="status"
						class="form-control input-sm" required="required">
						<c:choose>
							<c:when test="${user.status == 'Active'}">
								<option value="Active" selected>Active</option>
								<option value="Inactive">Inactive</option>
								<option value="Deleted">Deleted</option>
							</c:when>
							<c:when test="${user.status == 'Inactive'}">
								<option value="Active" >Active</option>
								<option value="Inactive"selected>Inactive</option>
								<option value="Deleted">Deleted</option>
							</c:when>
							<c:when test="${user.status == 'Deleted'}">
								<option value="Active" >Active</option>
								<option value="Inactive">Inactive</option>
								<option value="Deleted"selected>Deleted</option>
							
							</c:when>

							<c:otherwise>
								
								<option value="Active" selected>Active</option>
								<option value="Inactive">Inactive</option>
								<option value="Deleted">Deleted</option>
							
							</c:otherwise>
						</c:choose>
					</select>
					<p class="error">${error.status}</p>
				</div>
				<div class="form-group">
					<label>TimeZone</label> <select name="timeZone" id="timeZone"
						class="form-control input-sm" required="required">
						<c:forEach items="${timeZoneList}" var="element">
						
						<c:choose>
							<c:when test="${user.timeZone == element}">
								<option value="${element}" selected >${element}</option>
							</c:when>
							<c:otherwise>
								<option value="${element}">${element}</option>
							</c:otherwise>
						</c:choose>
						</c:forEach>
					</select>
					<p class="error">${error.timeZone}</p>
				</div>
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Edit
						User</button>
				</div>
			</form>
		</div>
</spr:page>