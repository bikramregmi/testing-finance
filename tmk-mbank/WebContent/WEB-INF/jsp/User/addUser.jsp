<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/custom.css" rel="stylesheet">
<link href="../css/starter-template.css" rel="stylesheet">
<spr:page header1="Add User">
	<div class="row">
		<div class="row col-md-4">
			<div class="break"></div>
			<form action="adduser" modelAttribute="user" method="post" class="col-md-12" style="float: none; margin-left: auto; margin-right: auto;">
				<div class="form-group">
					<label>Username</label> <input type="text" name="userName" class="form-control input-sm" required="required" value="${user.userName}">
					<h6 style="color: red">${error.userName}</h6>
				</div>
				<div class="form-group">
					<label>Password</label> <input type="password" name="password" class="form-control input-sm" value="${user.password}">
					<h6 style="color: red">${error.password}</h6>
				</div>
				<div class="form-group">
					<label>Confirm Password</label> <input type="password" name="repassword" class="form-control input-sm" value="${user.repassword}">
					<h6 style="color: red">${error.repassword}</h6>
				</div>
				<div class="form-group">
					<label>Address</label> <input type="text" name="address" class="form-control input-sm" value="${user.address}" required="required">
					<h6 style="color: red">${error.address}</h6>
				</div>

				<div class="form-group">
					<label>State</label> <select name="state" class="form-control input-sm" required="required" id="state">
						<c:if test="${fn:length(statesList) gt 0}">
							<option>Select State</option>
							<c:forEach var="state" items="${statesList}">
								<option value="${state.name}">${state.name}</option>
							</c:forEach>
						</c:if>
					</select>
					<h6 style="color: red">${error.state}</h6>
				</div>
				<div class="form-group">
					<label>City</label> <select name="city" class="form-control input-sm" required="required" id="city">

					</select>
					<h6 style="color: red">${error.city}</h6>
				</div>
				<div class="form-group">
					<label>User Type</label> <select name="userType" numcounter="0" id="userTypeSelection" class="form-control input-sm jqusersel" required="required">
						<option value="0">Select User Type</option>
						<c:forEach var="userType" items="${userTypeList}">
							<option value="${userType}">${userType}</option>
						</c:forEach>
					</select>
					<h6 style="color: red">${error.userType}</h6>
				</div>
				<div id="role" style="display: none;">
					<div id="form-group " class="usertemp">
						<label>Role</label> <select name="userTemplate" class="form-control input-sm" id="userTemplate">
							<c:forEach var="usertemp" items="${userTemplate}">
								<option value="${usertemp}">${usertemp.userTemplateName}</option>
							</c:forEach>
						</select>
						<h6 style="color: red">${error.userTemplate}</h6>
					</div>
				</div>
				<div id="usertemp" style="display: none;">
					<div class="form-group">
						<c:if test="${userObject eq 'admin'}">
							<label>Search By Name</label>
						</c:if>

						<c:if test="${userObject eq 'bank' }">
							<label>Search Bank Branch By Name</label>
						</c:if>
						<input thisUri="${pageContext.request.contextPath}/getusertypesobjects" firecounter="0" respcounter="0" status="0" type="text" name="userObject"
							class="form-control input-sm userObject">
					</div>
					<input type="hidden" name="objectUserId" class="objectUserId" value="0" />
					<div class="form-group fortblee">
						<table class="table table-striped returncusdata">
							<thead>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
				</div>
				<div id="makerCheckerForm">
					<div class="form-group">
						<table style="font-size: 13px;">
							<tr>
								<td><label>Checker</label></td>
								<td>&nbsp&nbsp&nbsp<input type="checkbox" name="checker" id="checker" style="margin: 0px;" /></td>
								<td>&nbsp&nbsp&nbsp<label>Maker</label></td>
								<td>&nbsp&nbsp&nbsp<input type="checkbox" name="maker" id="maker" style="margin: 0px;" /></td>
							</tr>
						</table>
					</div>
				</div>
				<c:if test="${userType eq 'Bank'}">
					<label>License Count</label>
					<input type="text" name="licenseCount" required="required" class="form-control input-sm" value="${user.licenseCount}">
				</c:if>

				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Add User</button>
				</div>
			</form>
		</div>
	</div>
	<c:if test="${branch.checker eq 'true'}">
		<script>
			$("#checker").prop("checked", true);
		</script>
	</c:if>
	<c:if test="${branch.maker eq 'true'}">
		<script>
			$("#maker").prop("checked", true);
		</script>
	</c:if>
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
<script>
	$(document).ready(function() {
		$("#userTypeSelection").change(function() {
			console.log("changed");
			var userType = $("#userTypeSelection").find("option:selected").val();
			console.log(userType);
			if(userType!="ChannelPartner"){
				$("#userTemplate").find("option").remove();
				var option = '';
				var option = '<option value="" selected>Select Role</option>'
				$.ajax({
					type : "GET",
					url : "/ajax/usertemplate/getbyusertype?usertype="+ userType,
					success : function(data) {
						$.each(data.userTemplateList,function(index,userTemplate) {
							option += '<option value="'+userTemplate.userTemplateName + '">'+ userTemplate.userTemplateName+ '</option>';
							$("#userTemplate").append(option);
							option = '';
						});
					}
				});
			}//end of if
		});
	});
</script>
<c:if test="${userObject eq 'admin'}">
	<script>
		$("#userTypeSelection").change(
				function() {
					$(this).attr('numcounter',parseInt($(this).attr('numcounter')) + 1);
					$('.userObject').val("");
					$('.userObject').css('background-color', '#fff');
					$('.returncusdata').empty();
					if (($(this).val() == "Bank")) {
						$("#role").show();
						$("#usertemp").show();
						$("#makerCheckerForm").show();
					}else if (($(this).val() == "ChannelPartner")) {
						$("#usertemp").show();
						$("#makerCheckerForm").hide();
						$("#role").hide();
					}else {
						$("#role").show();
						$("#usertemp").hide();
						$("#makerCheckerForm").hide();
					}

				});
		$("#makerCheckerForm").hide();
	</script>
</c:if>

<c:if test="${userObject eq 'bank'}">
	<script>
		$("#userTypeSelection").change(
				function() {
					$(this).attr('numcounter',parseInt($(this).attr('numcounter')) + 1);
					if(($(this).val() == "Bank")){
						$("#usertemp").hide();
					}else{
						$("#usertemp").show();
					}
					$('.userObject').val("");
					$('.userObject').css('background-color', '#fff');
					$('.returncusdata').empty();
					$("#role").show();
				});
	</script>
</c:if>