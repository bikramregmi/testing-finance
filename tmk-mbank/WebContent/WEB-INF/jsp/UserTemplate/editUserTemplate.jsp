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
			<form action="editUserTemplate" modelAttribute="UserTemplate" method="post"
				class="col-md-12"
				style="float: none; margin-left: auto; margin-right: auto;">
				<div class="form-group">
				<input type="hidden" name="id" value="${userTemplate.id}" />
				<div class="form-group">
				
					<label> Menu Template </label><select name="menuTemplate"
						class="form-control input-sm" required="required">
						
						<c:if test="${fn:length(menuTemplate) gt 0}">
							<c:forEach var="menuTemplate" items="${menuTemplate}">
								<c:choose>
									<c:when test="${menuTemplate.name == userTemplate.menuTemplateDTO.name}">
										<option value="${menuTemplate.name}" selected>${menuTemplate.name}</option>
									</c:when>
									<c:otherwise>
										<option value="${menuTemplate.name}">${menuTemplate.name}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:if>
					</select>
					<p class="error">${error.menu}</p>
				</div>
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Edit
						User</button>
				</div>
			</form>
		</div>
</spr:page>