<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/custom.css" rel="stylesheet">
<link href="../css/starter-template.css" rel="stylesheet">
<!-- <script src ="../js/jquery.min.js"></script> -->
<!-- <script src ="../js/bootstrap.js"></script> -->


<spr:page header1="Edit Customer">
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<form action="${pageContext.request.contextPath}/online/profile/edit" modelAttribute="customer" method="post"
				class="col-md-12"
				style="float: none; margin-left: auto; margin-right: auto;">
				<div class="form-group">
					<label>First Name</label> <input type="hidden" name="id"
						value="${customer.id}"> <input type="text"
						name="firstName" class="form-control input-sm" required="required"
						value="${customer.firstName}" readonly="readonly">
					<p class="error">${error.firstName}</p>
				</div>
				<div class="form-group">
					<label>Middle Name</label> <input type="text" name="middleName"
						class="form-control input-sm" value="${customer.middleName}" readonly="readonly">
					<p class="error">${error.middleName}</p>
				</div>
				<div class="form-group">
					<label>Last Name</label> <input type="text" name="lastName"
						class="form-control input-sm" required="required"
						value="${customer.lastName}" readonly="readonly">
					<p class="error">${error.lastName}</p>
				</div>
				<div class="form-group">
					<label>Address One</label> <input type="text" name="addressOne"
						class="form-control input-sm" required="required"
						value="${customer.addressOne}">
					<p class="error">${error.addressOne}</p>
				</div>
				<div class="form-group">
					<label>Address Two</label> <input type="text" name="addressTwo"
						class="form-control input-sm" value="${customer.addressTwo}">
					<p class="error">${error.addressTwo}</p>
				</div>
				<div class="form-group">
					<label>Address Two</label> <input type="text" name="addressThree"
						class="form-control input-sm" value="${customer.addressThree}">
					<p class="error">${error.addressThree}</p>
				</div>
				<div class="form-group">			
					<label>State*</label> 
					<select	name="state" class="form-control input-sm" required="required" id="state">
						<c:if test="${fn:length(statesList) gt 0}">
							<option>Select State</option>
							<c:forEach var="state" items="${statesList}">
								<c:choose>
									<c:when test="${customer.state eq state.name }">
										<option value="${state.name }" selected="selected">${state.name }</option>
									</c:when>
									<c:otherwise>
										<option value="${state.name }">${state.name }</option>
									</c:otherwise>
								</c:choose>
								
							</c:forEach>
						</c:if>
					</select> 
						<p class="error">${error.state}</p>
				</div>
				<div class="form-group">
					<label>City*</label>
					<select name="city" class="form-control input-sm" required="required" id="city">
						<c:if test="${fn:length(cityList) gt 0}">
							<option>Select State</option>
							<c:forEach var="city" items="${cityList}">
								<c:choose>
									<c:when test="${customer.city eq city.name }">
										<option value="${city.id }" selected="selected">${city.name }</option>
									</c:when>
									<c:otherwise>
										<option value="${city.id}">${city.name }</option>
									</c:otherwise>
								</c:choose>
								
							</c:forEach>
						</c:if>
					</select>
				</div>
				<div class="form-group">
					<label>Landline</label> <input type="text" name="landline"
						class="form-control input-sm" 
						value="${customer.landline}">
					<p class="error">${error.landline}</p>
				</div>
				<div class="form-group">
					<label>Mobile Number</label> <input type="text" name="mobileNumber"
						class="form-control input-sm" required="required"
						value="${customer.mobileNumber}" readonly="readonly">
					<p class="error">${error.mobileNumber}</p>
				</div>
				<div class="form-group">
				
				</div>
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Edit</button>
				</div>
			</form>
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
