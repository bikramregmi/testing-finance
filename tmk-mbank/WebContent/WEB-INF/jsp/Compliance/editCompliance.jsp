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


<spr:page header1="Edit Compliance">
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<form action="editCompliance" modelAttribute="compliance" method="post"
				class="col-md-12"
				style="float: none; margin-left: auto; margin-right: auto;">
				
				<div class="form-group">
					<label>Country</label>  <input type="hidden" name="id"
						value="${compliance.id}">
					<select name="country"
						class="form-control input-sm" required="required">
						<c:if test="${fn:length(countryList) gt 0}">
						<c:forEach var="country" items="${countryList}">
								<c:choose>
									<c:when
										test="${country.name eq compliance.country}">
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
				<div class="form-group">
					<label>Days</label> <input type="text" name="days"
						class="form-control input-sm" required="required"
						value="${compliance.days}">
					<p class="error">${error.days}</p>
				</div>
				<div class="form-group">
					<label>Amount</label> <input type="text" name="amount"
						class="form-control input-sm" required="required"
						value="${compliance.amount}">
					<p class="error">${error.amount}</p>
				</div>
				<div class="form-group">
					<label>Requirements</label> <textarea name="requirements"
						class="form-control input-sm" required="required">
						<c:out value="${compliance.requirements}" /></textarea>
					<p class="error">${error.requirements}</p>
				</div>
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Edit
						Compliance</button>
				</div>
			</form>

		</div>
</spr:page>
