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


<spr:page header1="Edit Branch">
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<form action="/bank/branch/edit" modelAttribute="branch"
				method="post" class="col-md-12"
				style="float: none; margin-left: auto; margin-right: auto;">
				<input type="hidden" name="id" value="${branch.id}">

				<div class="form-group">
					<label>Branch Name</label> 
						<input type="text" name="name" class="form-control input-sm"
						required="required" value="${branch.name}">
					</select>
					<p class="error">${error.branchName}</p>
				</div>
				
					<div class="form-group">
					<label>Branch Code</label> 
						<input type="text" name="branchCode" class="form-control input-sm"
						required="required" value="${branch.branchCode}">
					</select>
					<p class="error">${error.branchCode}</p>
				</div>
				
				<div class="form-group">
					<label>Email</label> 
						<input type="text" name="email" class="form-control input-sm"
						required="required" value="${branch.email}">
					</select>
					<p class="error">${error.email}</p>
				</div>

				<div class="form-group">
					<label>Branch Address</label> <input type="text" name="address"
						class="form-control input-sm" required="required"
						value="${branch.address}">
					<p class="error">${error.address}</p>
				</div>
				<div class="form-group">
					<label>State</label> <select name="state"
						class="form-control input-sm" required="required" id="state">
						<c:if test="${fn:length(stateList) gt 0}">
							<c:forEach var="state" items="${stateList}">
								<c:choose>
									<c:when test="${state.name == branch.state}">
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
					<label>City</label> <select name="city"
						class="form-control input-sm" required="required" id="city">
						<c:if test="${fn:length(cityList) gt 0}">
							<c:forEach var="city" items="${cityList}">
								<c:choose>
									<c:when test="${city.name == bank.city}">
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
					<label>Latitude</label>
					<input type="text" name="latitude" id="latitude" class="form-control input-sm" value="${branch.latitude}">
				<p class="error"><font color="red">${error.latitude}</font></p>	
				</div>
					<div class="form-group">
					<label>Longitude</label>
					<input type="text" name="longitude" id="longitude" class="form-control input-sm" value="${branch.longitude}">
				<p class="error"><font color="red">${error.longitude}</font></p>	
				</div>

				<div class="form-group">
					<table>
						<tr>
							<td><label>Checker</label></td>
							<td>&nbsp&nbsp&nbsp<input type="checkbox" name="checker"
								id="checker" /></td>
							<td>&nbsp&nbsp&nbsp<label> Maker </label></td>
							<td>&nbsp&nbsp&nbsp<input type="checkbox" name="maker"
								id="maker" /></td>
						</tr>

					</table>
					<p class="error">${error.checker}</p>
				</div>

				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Edit
						Branch</button>
				</div>
			</form>

		</div>
	</div>
</spr:page>
<script>
	var checker = "${branch.checker}";
	var maker = "${branch.maker}";
</script>
<script>
	$(document)
			.ready(
					function() {

						if (checker === 'true') {
							$('#checker').prop('checked', true);
						}
						if (maker === 'true') {
							$('#maker').prop('checked', true);
						}

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