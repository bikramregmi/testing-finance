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

<spr:page header1="Add Financial Institute" >
<div class="col-md-12">
	<div class="row col-md-4">
		<div class="break"></div>
		<form  action="addFinancialInstitute" modelAttribute="financialInstitute" method="post" class="col-md-12" style="float: none; margin-left: auto; margin-right: auto;">
			<div class="form-group">
				<label>Institute Name</label>
				<input type="text" name="name" class="form-control input-sm" required="required" value="${financialInstitute.name}">
				<p class="error">${error.name}</p>
			</div>
			<div class="form-group">
				<label>Address</label>
				<input type="text" name="address" class="form-control input-sm" value="${financialInstitute.address}">
				<p class="error">${error.address}</p>
			</div>
			<div class="form-group">
				<label>Swift Code</label>
				<input type="text" name="swiftCode" class="form-control input-sm" value="${financialInstitute.swiftCode}">
				<p class="error">${error.swiftCode}</p>
			</div>
			<div class="form-group">
					<label>TimeZone</label> <select name="timeZone" id="timeZone"
						class="form-control input-sm" required="required">
						<c:forEach items="${timeZoneList}" var="element">
							<option value="${element}">${element}</option>
						</c:forEach>
					</select>
					<p class="error">${error.timeZone}</p>
			</div>
			
			<!--  -->
			<div class="form-group">
					<label>Institute Type</label> 
					<select name="instituteType"
						class="form-control input-sm" required="required">
						<c:if test="${fn:length(instituteTypes) gt 0}">
						<c:forEach var="institute" items="${instituteTypes}">
							 <option value="${institute}">${institute}</option>	
						</c:forEach>
						</c:if>
					</select> 
					<p class="error">${error.instituteType}</p>
				</div>
				<!--  -->
			
			<div class="form-group">
					<label>City</label> 
					<select name="city"
						class="form-control input-sm" required="required">
						<c:if test="${fn:length(cityList) gt 0}">
						<c:forEach var="city" items="${cityList}">
								<c:choose>
									<c:when
										test="${city.name == financialInstitute.city}">
										<option value="${city.name}" selected>${city.name}</option>
									</c:when>
									<c:otherwise>
										<option value="${city.name}">${city.name}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							</c:if>
					</select> 
					<p class="error">${error.city}</p>
				</div>
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Add Institute</button>
				</div>
		</form>
</div>
</spr:page>