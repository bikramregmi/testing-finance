<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<spr:page header1="Add Channel Partner">
	<div class="row">
		<div class="col-md-4">
			<div class="break"></div>
			<form action="${pageContext.request.contextPath}/addchannelpartner" method="post">
				<div class="form-group">
					<label>Channel Partner Name</label>
					<input type="text" name="name" class="form-control input-sm" value="${channelPartner.name}">
					<p class="error">${error.name}</p>
				</div>
				<div class="form-group">
					<label>Owner</label>
					<input type="text" name="owner" class="form-control input-sm"  value="${channelPartner.owner}">
					<p class="error">${error.owner}</p>
				</div>
				<div class="form-group">
					<label>Address</label>
					<input type="text" name="address" class="form-control input-sm" value="${channelPartner.address}">
					<p class="error">${error.address}</p>
				</div>
				<div class="form-group">
					<label>State</label> <select name="state" class="form-control input-sm"  id="state">
						<c:if test="${fn:length(statesList) gt 0}">
							<option value="">Select State</option>
							<c:forEach var="state" items="${statesList}">
								<option value="${state.name}">${state.name}</option>
							</c:forEach>
						</c:if>
					</select>
					<p class="error">${error.state}</p>
				</div>
				<div class="form-group">
					<label>City</label>
					<select name="city" class="form-control input-sm" id="city">
					</select>
					<p class="error">${error.city}</p>
				</div>
				<div class="form-group">
					<label>Username</label>
					<input type="text" name="uniqueCode" class="form-control input-sm" value="${channelPartner.uniqueCode}">
					<p class="error">${error.uniqueCode}</p>
				</div>
				<div class="form-group">
					<label>Password</label>
					<input type="password" name="passCode" class="form-control input-sm"  value="${channelPartner.passCode}">
					<p class="error">${error.passCode}</p>
				</div>
				<div class="form-group">
					<label>Re-Enter Password</label>
					<input type="password" name="repassCode" class="form-control input-sm"  value="${channelPartner.repassCode}">
					<p class="error">${error.repassCode}</p>
				</div>
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Add Channel Partner</button>
				</div>
			</form>
		</div>
	</div>
</spr:page>
<script>
	$(document).ready(function() {
		$("#state").change(function() {
			var stateName = $("#state").find("option:selected").val();
			$("#city").find("option").remove();
			var option = '';
			var option = '<option value="'+0+'">Select City</option>'
			$.ajax({
				type : "GET",
				url : "/ajax/state/getCitiesByState?state="+ stateName,
				success : function(data) {
					$.each(data.citiesList,function(index,city) {
						option += '<option value="'+city.id + '">'+ city.name+ '</option>';
						$("#city").append(option);
						option = '';
					});
				}
			});
		});
	});
</script>
<style>
	.error{
		color:red;
	}
</style>