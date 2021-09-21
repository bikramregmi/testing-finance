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

<spr:page header1="Edit Merchant" >
<div class="col-md-12">
	<div class="row col-md-4">
		<div class="break"></div>
		<form  action="${pageContext.request.contextPath}/merchant/edit" modelAttribute="merchant" method="post" class="col-md-12" style="float: none; margin-left: auto; margin-right: auto;">
			<input type="hidden" name="id" value="${merchant.id}">
			<div class="form-group">
				<label>Merchant Name</label>
				<input type="text" name="name" class="form-control input-sm" required="required" value="${merchant.name}">
				<h6><font color="red">${error.name}</font></h6>
			</div>
			<div class="form-group">
				<label>Address</label>
				<input type="text" name="address" class="form-control input-sm" required="required" value="${merchant.address}">
				<p class="error">${error.address}</p>
			 </div>
			<div class="form-group">
					<label>State</label> 
					<select name="state" class="form-control input-sm" required="required" id="state">
						<c:if test="${fn:length(stateList) gt 0}">
						<c:forEach var="state" items="${stateList}">
							<c:choose>
								<c:when test="${state.name == merchant.state}">
									<option value="${state.name}" selected>${state.name}</option>
								</c:when>
								<c:otherwise>
									<option value="${state.name}">${state.name}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						</c:if>
					</select> 
					<h6><font color="red">${error.state}</font></h6>
				</div>
				<div class="form-group">
					<label>City</label> 
					<select name="city" class="form-control input-sm" required="required" id="city">
						<c:if test="${fn:length(cityList) gt 0}">
						<c:forEach var="city" items="${cityList}">
								<c:choose>
									<c:when
										test="${city.name == merchant.city}">
										<option value="${city.id}" selected>${city.name}</option>
									</c:when>
									<c:otherwise>
										<option value="${city.id}">${city.name}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							</c:if>
					</select> 
					<h6><font color="red">${error.city}</font></h6>
				</div>
				<div class="form-group">
					<label>Api URl</label>
					<input type="text" name="apiUrl" class="form-control input-sm" required="required" value="${merchant.apiUrl}">
					<h6><font color="red">${error.apiUrl}</font></h6>
			    </div>
			    <div class="form-group">
					<label>Api Username</label>
					<input type="text" name="apiUsername" class="form-control input-sm" required="required" value="${merchant.apiUsername}">
					<h6><font color="red">${error.apiUsername}</font></h6>
			    </div>
			    <div class="form-group">
					<label>Api Password</label>
					<input type="text" name="apiPassword" class="form-control input-sm" required="required" value="${merchant.apiPassword}">
					<h6><font color="red">${error.apiPassword}</font></h6>
			    </div>
			    <div class="form-group">
					<label>Description</label>
					<input type="text" name="description" class="form-control input-sm" required="required" value="${merchant.description}">
					<h6><font color="red">${error.description}</font></h6>
			    </div>
			    <div class="form-group">
			    	<label>Status</label>
			    	<select name="status" class="form-control input-sm" required>
			    		<c:if test="${merchant.status eq 'Active'}">
			    			<option value="Active" selected>Active</option>
			    			<option value="Inactive">Inactive</option>
			    		</c:if>
			    		<c:if test="${merchant.status eq 'Inactive'}">
			    			<option value="Active">Active</option>
			    			<option value="Inactive" selected>Inactive</option>
			    		</c:if>
			    	</select>
			    </div>
			        <div class="form-group">
					<label>Extra Field 1.</label>
					<input type="text" name="extraField1" class="form-control input-sm" value="${merchant.extraField1}">
					<h6><font color="red">${error.extraField1}</font></h6>
			    </div>
			      <div class="form-group">
					<label>Extra Field 2.</label>
					<input type="text" name="extraField2" class="form-control input-sm" value="${merchant.extraField2}">
					<h6><font color="red">${error.extraField2}</font></h6>
			    </div>
			      <div class="form-group">
					<label>Extra Field 3.</label>
					<input type="text" name="extraField3" class="form-control input-sm" value="${merchant.extraField3}">
					<h6><font color="red">${error.extraField3}</font></h6>
			    </div>
				
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Save Merchant</button>
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