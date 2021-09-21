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


<spr:page header1="Edit Bank">
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<form action="editBank" modelAttribute="bank" method="post" class="col-md-12" style="float: none; margin-left: auto; margin-right: auto;">
				<input type="hidden" name="id" value="${bank.id}">
				
				<div class="form-group">
					<label>Bank Name</label>
					<input type="text" name="name" class="form-control input-sm" required="required" value="${bank.name}">
					<h6><font color="red">${error.name}</font></h6>
				</div>
				
					<div class="form-group">
					<label>Bank Email</label>
					<input type="text" name="email" class="form-control input-sm" required="required" value="${bank.email}">
					<h6><font color="red">${error.email}</font></h6>
				</div>
				
				<div class="form-group">
					<label>Bank Address</label>
					<input type="text" name="address" class="form-control input-sm" required="required" value="${bank.address}">
					<h6><font color="red">${error.address}</font></h6>
				</div>
				<div class="form-group">
					<label>State</label> 
					<select name="state" class="form-control input-sm" required="required" id="state">
						<c:if test="${fn:length(stateList) gt 0}">
						<c:forEach var="state" items="${stateList}">
							<c:choose>
								<c:when test="${state.name == bank.state}">
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
										test="${city.name == bank.city}">
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
				<%-- <div class="form-group">
					<label>SMS Count</label>
					<input type="text" name="smsCount" class="form-control input-sm" required="required" value="${bank.smsCount}">
				<h6><font color="red">${error.smsCount}</font></h6>
				</div>
				<div class="form-group">
					<label>License Count</label>
					<input type="text" name="licenseCount" class="form-control input-sm" required="required" value="${bank.licenseCount}">
				
				</div> --%>
				<div class="form-group">
					<label>Expiry Date</label>
					<input type="text" name="licenseExpiryDate" id="expiryDate" class="form-control input-sm" required="required" value="${bank.licenseExpiryDate}">
				
				</div>
				<div class="form-group">
					<label>Select Merchant(s)</label>
					<select class="form-control" multiple="multiple" id="fromSelect">
						<c:if test="${fn:length(merchantList) gt 0}">
							<c:forEach var="merchant" items="${merchantList}">
								<option value="${merchant.id}">${merchant.name}</option>
							</c:forEach>
						</c:if>
						<c:if test="${fn:length(bank.merchant) gt 0}">
							<c:forEach var="merchant" items="${bank.merchant}">
								<option value="${merchant.id}" selected>${merchant.name}</option>
							</c:forEach>
						</c:if>
					</select>
					<select name="merchants" class="form-control" multiple id="toSelect"></select>  
				</div>
				<div class="form-group">
					<label>Bank Transfer Account</label>
					<input type="text" name="bankTranferAccount" class="form-control input-sm" value="${bank.bankTranferAccount}">
					<h6><font color="red">${error.bankTranferAccount}</font></h6>
				</div>
				<div class="form-group">
					<label>ISO URL</label>
					<input type="text" name="isoUrl" class="form-control input-sm" value="${bank.isoUrl}"> 
					<h6><font color="red">${error.isoUrl}</font></h6>
				</div>
				<div class="form-group">
					<label>Port Number</label>
					<input type="text" name="portNumber" class="form-control input-sm" required value="${bank.portNumber}"> 
					<h6><font color="red">${error.portNumber}</font></h6>
				</div>
				
				<div class="form-group">
					<label>Card Acceptor Terminal Identification</label>
					<input type="text" name="cardAcceptorTerminalIdentification" class="form-control input-sm" value="${bank.cardAcceptorTerminalIdentification}" required="required">
					<h6><font color="red">${error.cardAcceptorTerminalIdentification}</font></h6>
				</div>
				<div class="form-group">
					<label>Acquiring Institution Identification Code</label>
					<input type="text" name="acquiringInstitutionIdentificationCode" class="form-control input-sm" value="${bank.acquiringInstitutionIdentificationCode}" required="required">
					<h6><font color="red">${error.acquiringInstitutionIdentificationCode}</font></h6>
				</div>
				<div class="form-group">
					<label>Merchant Type</label>
					<input type="text" name="merchantType" class="form-control input-sm" value="${bank.merchantType}" required="required">
					<h6><font color="red">${error.merchantType}</font></h6>
				</div>
				
				<div class="form-group">
					<label>Desc 1</label>
					<input type="text" name="desc1" class="form-control input-sm" value="${bank.desc1}" required="required">
					<h6><font color="red">${error.desc1}</font></h6>
				</div>
				
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Edit Bank</button>
				</div>
			</form>
		</div>
	</div>
</spr:page>
<script>
$(document).ready(function() {
	
	  !$('#fromSelect option:selected').remove().appendTo('#toSelect');  
		
	  $(function() {
		  $( "#expiryDate" ).datepicker();
	  });
	  
	 $('#fromSelect').click(function() {
		  return !$('#fromSelect option:selected').remove().appendTo('#toSelect');  
	 });  
	 $('#toSelect').click(function() {
	  	  return !$('#toSelect option:selected').remove().appendTo('#fromSelect');  
	 }); 
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
					option += '<option value="'+city.id + '">' + city.name + '</option>';
					$("#city").append(option);
					option = '';
				});
			}
		});
	});
});
</script>