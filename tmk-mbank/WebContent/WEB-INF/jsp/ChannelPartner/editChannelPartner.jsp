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

<spr:page header1="Add Channel Partner">
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<form action="${pageContext.request.contextPath}/editchannelpartner" modelAttribute="channelPartner" method="post" class="col-md-12" style="float: none; margin-left: auto; margin-right: auto;">
				<input type="hidden" name="id" value="${channelPartner.id}">
				<div class="form-group">
					<label>Address</label>
					<input type="text" name="address" class="form-control input-sm" required="required" value="${channelPartner.address}">
					<p class="error">${error.address}</p>
				</div>
				<div class="form-group">
					<label>State</label> 
					<select name="state" class="form-control input-sm" required="required" id="state">
						<c:if test="${fn:length(stateList) gt 0}">
						<c:forEach var="state" items="${stateList}">
							<c:choose>
								<c:when test="${state.name == channelPartner.state}">
									<option value="${state.name}" selected>${state.name}</option>
								</c:when>
								<c:otherwise>
									<option value="${state.name}">${state.name}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						</c:if>
					</select> 
					<p class="error">${error.state}</p>
				</div>
				<div class="form-group">
					<label>City</label> 
					<select name="city" class="form-control input-sm" required="required" id="city">
						<c:if test="${fn:length(cityList) gt 0}">
						<c:forEach var="city" items="${cityList}">
								<c:choose>
									<c:when
										test="${city.name == channelPartner.city}">
										<option value="${city.id}" selected>${city.name}</option>
									</c:when>
									<c:otherwise>
										<option value="${city.id}">${city.name}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							</c:if>
					</select> 
					<p class="error">${error.city}</p>
				</div>
				
					<div class="form-group">
					<label>Username</label>
					<input type="text" name="uniqueCode" class="form-control input-sm" value="${channelPartner.uniqueCode}" required>
					<p class="error">${error.uniqueCode}</p>
				</div>
				<div class="form-group">
					<label>Password</label>
					<input type="password" name="passCode" class="form-control input-sm" value="${channelPartner.passCode}" required >
					<p class="error">${error.passCode}</p>
				</div>
				
				<div class="form-group">
			    	<label>Status</label>
			    	<select name="status" class="form-control input-sm" required>
			    		<c:if test="${channelPartner.status eq 'Active'}">
			    			<option value="Active" selected>Active</option>
			    			<option value="Inactive">Inactive</option>
			    		</c:if>
			    		<c:if test="${channelPartner.status eq 'Inactive'}">
			    			<option value="Active">Active</option>
			    			<option value="Inactive" selected>Inactive</option>
			    		</c:if>
			    	</select>
			    </div>
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Save Channel Partner</button>
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
