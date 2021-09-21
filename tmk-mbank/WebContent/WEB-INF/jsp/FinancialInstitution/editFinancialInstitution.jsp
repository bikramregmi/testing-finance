<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/custom.css" rel="stylesheet">
<link href="../css/starter-template.css" rel="stylesheet">
<!-- <script src ="../js/jquery.min.js"></script> -->
<!-- <script src ="../js/bootstrap.js"></script> -->


<spr:page header1="Edit Financial Institute">
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<form action="editFinancialInstitute" modelAttribute="instituteDTO" method="post"
				class="col-md-12"
				style="float: none; margin-left: auto; margin-right: auto;">
				<div class="form-group">
					<label>Institute Name</label> 
					<input type="hidden" name="id" value="${instituteDTO.id}">
			        <input type="text" name="name" class="form-control input-sm" required="required" value="${instituteDTO.name}">
					<p class="error">${error.name}</p>
				</div>
				<div class="form-group">
					<label>Address</label> 
					<input type="text" name="address" class="form-control input-sm" value="${instituteDTO.address}">
					<p class="error">${error.address}</p>
				</div>
				<div class="form-group">
					<label>Swift Code</label> <input type="text" name="swiftCode"
						class="form-control input-sm" required="required"
						value="${instituteDTO.swiftCode}">
					<p class="error">${error.swiftCode}</p>
				</div>
				<div class="form-group">
					<label>City</label> 
					<select name="city" class="form-control input-sm" required="required">
					    <c:forEach items="${cityList}" var="city">
					       <option value="${city.name}">${city.name}</option>
					    </c:forEach>
					</select>
					<p class="error">${error.city}</p>
				</div>
				<div class="form-group">
					<label>Institute Type</label> 
					   <select name="instituteType" class="form-control input-sm">
					      <c:forEach items="${instituteTypes}" var="instituteType">
					         <option value="${instituteType}">${instituteType}</option>
					      </c:forEach>
					   </select>
					<p class="error">${error.insituteType}</p>
				</div>
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Edit
						Institute</button>
				</div>
			</form>
		</div>
</spr:page>
