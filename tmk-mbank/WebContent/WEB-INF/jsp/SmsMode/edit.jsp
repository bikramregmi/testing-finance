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

<spr:page header1="Add SMS Mode" >
<div class="col-md-12">
	<div class="row col-md-4">
		<div class="break"></div>
		<form  action="${pageContext.request.contextPath}/smsmode/edit" modelAttribute="smsmode" method="post" class="col-md-12" style="float: none; margin-left: auto; margin-right: auto;">
			<input type="hidden" readonly="readonly" name="id" value="${smsMode.id}">
			<div class="form-group">
					<label>Bank*</label>
					<input type="text" readonly="readonly" name="bank" value="${smsMode.bank}">
			</div>
			
			<div class="form-group">
				<label>SMS Type</label>
				<select class="form-control" id="smsType" name="smsType">
					<option>Select SMS Type</option>
						<c:if test="${fn:length(smsType) gt 0}">
							<c:forEach var="sType" items="${smsType}">
								<c:choose>
									<c:when test="${smsMode.smsType eq sType}">
										<option value="${sType}" selected="selected">${sType}</option>
									</c:when>
									<c:otherwise>
										<option value="${sType}">${sType}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:if>
				</select>
			</div>
			<div class="form-group">
				<label>Message*</label>
				<textarea rows="4" cols="50" name="message"> ${smsMode.message}</textarea>
			</div>

			<div class="form-group">
				<button class="btn btn-primary btn-md btn-block btncu">Edit</button>
			</div>
		</form>
</div>
</spr:page>