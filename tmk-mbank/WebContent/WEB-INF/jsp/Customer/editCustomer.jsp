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

<spr:page header1="Edit Customer">
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<form action="${pageContext.request.contextPath}/customer/edit" method="post" class="col-md-12" style="float: none; margin-left: auto; margin-right: auto;">
				<input type="hidden" name="uniqueId" value="${customer.uniqueId}">
				<div class="form-group">
					<label>First Name*</label> <input type="text" name="firstName" class="form-control input-sm" required="required" value="${customer.firstName}">
					<h6>
						<font color="red">${error.firstName}</font>
					</h6>
				</div>
				<div class="form-group">
					<label>Middle Name</label> <input type="text" name="middleName" class="form-control input-sm" value="${customer.middleName}">
				</div>
				<div class="form-group">
					<label>Last Name*</label> <input type="text" name="lastName" class="form-control input-sm" required="required" value="${customer.lastName}">
					<h6>
						<font color="red">${error.lastName}</font>
					</h6>
				</div>
				<div class="form-group">
					<label>Address One*</label> <input type="text" name="addressOne" class="form-control input-sm" required="required" value="${customer.addressOne}">
					<p class="error">${error.addressOne}</p>
				</div>
				<div class="form-group">
					<label>Address Two</label> <input type="text" name="addressTwo" class="form-control input-sm" value="${customer.addressTwo}">
				</div>
				<div class="form-group">
					<label>Customer Profile*</label> <select name="customerProfileId" class="form-control input-sm" required="required">
						<option selected disabled>Select Customer Profile</option>
						<c:if test="${fn:length(customerProfileList) gt 0}">

							<c:forEach var="profile" items="${customerProfileList}">
								<c:choose>
									<c:when test="${customerProfile.id eq profile.id}">
										<option value="${profile.id}" selected>${profile.name}</option>
									</c:when>
									<c:otherwise>

										<option value="${profile.id}">${profile.name}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:if>
					</select>
					<h6>
						<font color="red">${error.customerProfile}</font>
					</h6>
				</div>

				<div class="form-group">
					<label>State</label> <select name="state" class="form-control input-sm" required="required" id="state">
						<option value="">Select a state</option>
						<c:if test="${fn:length(stateList) gt 0}">
							<c:forEach var="state" items="${stateList}">
								<c:choose>
									<c:when test="${state.name == customer.state}">
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
					<label>City</label> <select name="city" class="form-control input-sm" required="required" id="city">
						<c:if test="${fn:length(cityList) gt 0}">
							<c:forEach var="city" items="${cityList}">
								<c:choose>
									<c:when test="${city.name == customer.city}">
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
					<label>App Service</label> <input type="checkbox" name="appService" id="appService"> &nbsp; <label>SMS Subscribed</label> <input type="checkbox"
						name="smsService" id="smsService">
				</div>
				<div class="form-group">
					<label>Comment *</label>
					<textarea rows="4" cols="50" name="comment" required></textarea>
				</div>
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Save Customer</button>
				</div>
			</form>
		</div>
	</div>
</spr:page>
<c:if test="${customer.appService eq 'true'}">
	<script>
		$("#appService").prop("checked", true);
	</script>
</c:if>
<c:if test="${customer.smsService eq 'true'}">
	<script>
		$("#smsService").prop("checked", true);
	</script>
</c:if>
<script>
	$(document)
			.ready(
					function() {
						$("#state")
								.change(
										function() {
											var stateName = $("#state").find(
													"option:selected").val();
											$("#city").find("option").remove();
											var option = '';
											var option = '<option value="'+0+'">Select City</option>'
											$
													.ajax({
														type : "GET",
														url : "/ajax/state/getCitiesByState?state="
																+ stateName,
														success : function(data) {
															$
																	.each(
																			data.citiesList,
																			function(
																					index,
																					city) {
																				option += '<option value="'+city.id + '">'
																						+ city.name
																						+ '</option>';
																				$(
																						"#city")
																						.append(
																								option);
																				option = '';
																			});
														}
													});
										});
					});
</script>