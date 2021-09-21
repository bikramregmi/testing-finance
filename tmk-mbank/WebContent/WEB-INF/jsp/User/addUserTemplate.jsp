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

<spr:page header1="Add UserTemplate">
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<form action="addUserTemplate" modelAttribute="userTemplate"
				method="post" class="col-md-12"
				style="float: none; margin-left: auto; margin-right: auto;">
				
				<div class="form-group">
					<label>User Group Name</label> <input type="input" name="userTemplateName"
						class="form-control input-sm" value="${user.userTemplateName}">
					<p class="error">${error.userTemplateName}</p>
				</div>
				<div class="form-group">
				
				
					<label> Menu Template ${fn:length(menuTemplateList)}</label> <select name="menuTemplate"
						class="form-control input-sm" required="required">
						<c:if test="${fn:length(menuTemplateList) gt 0}">
							<c:forEach var="menuTemplate" items="${menuTemplateList}">
								<c:choose>
									<c:when test="${menuTemplate.name == userTemplate.menuTemplate}">
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
					<button class="btn btn-primary btn-md btn-block btncu">Add
						UserTemplate</button>
				</div>
			</form>
		</div>
	</div>
</spr:page>