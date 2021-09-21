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

<spr:page header1="Add Merchant" >
<div class="col-md-12">
	<div class="row col-md-4">
		<div class="break"></div>
		<form  action="${pageContext.request.contextPath}/merchant/add" modelAttribute="merchant" method="post" class="col-md-12" style="float: none; margin-left: auto; margin-right: auto;">
			<div class="form-group">
				<label>Merchant Name</label>
				<input type="text" name="name" class="form-control input-sm" required="required" value="${merchantDto.name}">
				<h6><font color="red">${error.name}</font></h6>
			</div>
			<div class="form-group">
				<label>Registration Number</label>
				<input type="text" name="registrationNumber" class="form-control input-sm" value="${merchantDto.registrationNumber}">
				<h6><font color="red">${error.registrationNumber}</font></h6>
			</div>
			<div class="form-group">
				<label>Vat Number</label>
				<input type="text" name="vatNumber" class="form-control input-sm" required="required" value="${merchantDto.vatNumber}">
				<h6><font color="red">${error.vatNumber}</font></h6>
			</div>
			<div class="form-group">
				<label>Landline</label>
				<input type="text" name="landLine" class="form-control input-sm" required="required" value="${merchantDto.landLine}">
				<h6><font color="red">${error.landLine}</font></h6>
			</div>
			<div class="form-group">
				<label>Mobile Number</label>
				<input type="text" name="mobileNumber" class="form-control input-sm" required="required" value="${merchantDto.mobileNumber}">
				<h6><font color="red">${error.mobileNumber}</font></h6>
			</div>
			<div class="form-group">
				<label>Address</label>
				<input type="text" name="address" class="form-control input-sm" required="required" value="${merchantDto.address}">
				<h6><font color="red">${error.address}</font></h6>
			 </div>
			<div class="form-group">			
				<label>State</label> 
				<select	name="state" class="form-control input-sm" required="required" id="state">
					<c:if test="${fn:length(statesList) gt 0}">
						<option>Select State</option>
						<c:forEach var="state" items="${statesList}">
							<option value="${state.name}">${state.name}</option>
						</c:forEach>
					</c:if>
				</select> 
				<h6><font color="red">${error.state}</font></h6>
			</div>
			<div class="form-group">
				<label>City</label>
				<select name="city" class="form-control input-sm" required="required" id="city">
					
				</select>
			</div>
				<div class="form-group">
					<label>Owner Name</label>
					<input type="text" name="ownerName" class="form-control input-sm" required="required" value="${merchantDto.ownerName}">
					<h6><font color="red">${error.ownerName}</font></h6>
			    </div>
			    <div class="form-group">
					<label>Api URl</label>
					<input type="text" name="apiUrl" class="form-control input-sm" required="required" value="${merchantDto.apiUrl}">
					<h6><font color="red">${error.apiUrl}</font></h6>
			    </div>
			    <div class="form-group">
					<label>Api Username</label>
					<input type="text" name="apiUsername" class="form-control input-sm" required="required" value="${merchantDto.apiUsername}">
					<h6><font color="red">${error.apiUsername}</font></h6>
			    </div>
			    <div class="form-group">
					<label>Api Password</label>
					<input type="text" name="apiPassword" class="form-control input-sm" required="required" value="${merchantDto.apiPassword}">
					<h6><font color="red">${error.apiPassword}</font></h6>
			    </div>
			    <div class="form-group">
					<label>Description</label>
					<input type="text" name="description" class="form-control input-sm" required="required" value="${merchantDto.description}">
					<h6><font color="red">${error.description}</font></h6>
			    </div>
			      <div class="form-group">
					<label>Extra Field 1.</label>
					<input type="text" name="extraField1" class="form-control input-sm" value="${merchantDto.extraField1}">
					<h6><font color="red">${error.extraField1}</font></h6>
			    </div>
			      <div class="form-group">
					<label>Extra Field 2.</label>
					<input type="text" name="extraField2" class="form-control input-sm" value="${merchantDto.extraField2}">
					<h6><font color="red">${error.extraField2}</font></h6>
			    </div>
			      <div class="form-group">
					<label>Extra Field 3.</label>
					<input type="text" name="extraField3" class="form-control input-sm" value="${merchantDto.extraField3}">
					<h6><font color="red">${error.extraField3}</font></h6>
			    </div>
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Add Merchant</button>
				</div>
		</form>
</div>
</div>
</spr:page>
<script>
	$(document).ready(function(){
		$("#state").change(function(){
			var stateName = $("#state").find("option:selected").val();
			$("#city").find("option").remove();
			var option = '';
			var option = '<option value="'+0+'">Select City</option>'
			$.ajax({
				type: "GET",
				url: "/ajax/state/getCitiesByState?state="+stateName,
				success: function(data) {
					$.each(data.citiesList,function(index,city){			
						option+= '<option value="'+city.id + '">' +city.name+ '</option>';
						$("#city").append(option);
						option = '';
					});
				}
			});
		});
	});
</script>