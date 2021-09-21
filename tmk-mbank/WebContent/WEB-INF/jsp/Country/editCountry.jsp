<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/custom.css" rel="stylesheet">
<link href="../css/starter-template.css" rel="stylesheet">
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- <script src ="../js/jquery.min.js"></script> -->
<!-- <script src ="../js/bootstrap.js"></script> -->


<spr:page header1="Edit Country" >
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<form action="editCountry" modelAttribute="country" method="post"
				class="col-md-12"
				style="float: none; margin-left: auto; margin-right: auto;">
				<div class="form-group">
					<label>Country Name</label> <input type="hidden" name="id"
						value="${country.id}"> <input type="text" name="name"
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
				
				<div class="form-group">
					<label>Currency</label> <select name="currencyCode"
						class="form-control input-sm" required="required">
						<c:if test="${fn:length(currencyList) gt 0}">

							<c:forEach var="currency" items="${currencyList}">
							<c:choose>
							<c:when test="${currency.currencyName eq country.currencyDTO.currencyName}">
								<option value="${currency.currencyCode}" selected>${currency.currencyName}
									(${currency.currencyCode})</option>
							</c:when>
							<c:otherwise>
								<option value="${currency.currencyCode}">${currency.currencyName}
									(${currency.currencyCode})</option>
							</c:otherwise>
							</c:choose>		
							</c:forEach>

						</c:if>
					</select>
					<p class="error">${error.currency}</p>
				</div>
				
				<%-- <div class="form-group">
					<label>Currency</label> <input type="text" name="currency"
						class="form-control input-sm" required="required"
						value="${country.currency}">
						<p class="error">${error.currency}</p>
				</div> --%>
				<div class="form-group">
					<label>Status</label> <select name="status" id="status"
						class="form-control input-sm" required="required" >
						<c:choose>
							<c:when test="${country.status eq 'Active'}">
								<option value="Active" selected>Active</option>
								<option value="Inactive">InActive</option>
							</c:when>
							<c:otherwise>
								<option value="Active" >Active</option>
								<option value="Inactive" selected>InActive</option>
							</c:otherwise>
						
						</c:choose>
					</select>
					<p class="error">${error.status}</p>


				</div>
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Edit
						Country</button>
				</div>
			</form>
		</div>
</spr:page>
