<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/custom.css" rel="stylesheet">
<link href="../css/starter-template.css" rel="stylesheet">

<spr:page header1="Add Customer Profile">
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<form action="${pageContext.request.contextPath}/customerProfile/add" method="post" class="col-md-12" style="float: none; margin-left: auto; margin-right: auto;">
				<div class="form-group">
					<label>Name*</label>
					<input type="text" name="name" class="form-control input-sm" required value="${profile.name}">
					<p class="error">${error.name}</p>
				</div>
				<div class="form-group">
					<label>Per Transaction Limit*</label>
					<input type="text" name="perTxnLimit" class="form-control input-sm" required value="${profile.perTxnLimit}" onkeypress='validateLong(event)'>
					<p class="error">${error.perTxnLimit}</p>
				</div>
				<div class="form-group">
					<label>Daily Transaction Limit Count*</label>
					<input type="text" name="dailyTxnLimit" class="form-control input-sm" required value="${profile.dailyTxnLimit}" onkeypress='validateLong(event)'>
					<p class="error">${error.dailyTxnLimit}</p>
					
					<div class="form-group">
					<label>Registration Amount*</label>
					<input type="text" name="registrationAmount" class="form-control input-sm" required value="${profile.registrationAmount}" onkeypress='validateLong(event)'>
					<p class="error">${error.registrationAmount}</p>
				</div>
					<div class="form-group">
					<label>Renewal Amount*</label>
					<input type="text" name="renewAmount" class="form-control input-sm" required value="${profile.renewAmount}" onkeypress='validateLong(event)'>
					<p class="error">${error.renewAmount}</p>
				</div>
				
					<div class="form-group">
					<label>Pin Reset Amount*</label>
					<input type="text" name="pinResetCharge" class="form-control input-sm" required value="${profile.pinResetCharge}" onkeypress='validateLong(event)'>
					<p class="error">${error.pinResetCharge}</p>
				</div>
					
				</div>
					<div class="form-group">
					<label>Daily Transaction Limit Amount*</label>
					<input type="text" name="dailyTransactionAmount" class="form-control input-sm" required value="${profile.dailyTransactionAmount}" onkeypress='validateLong(event)'>
					<p class="error">${error.dailyTransactionAmount}</p>
				</div>
				<div class="form-group">
					<label>Weekly Transaction Limit Amount*</label>
					<input type="text" name="weeklyTxnLimit" class="form-control input-sm" required value="${profile.weeklyTxnLimit}" onkeypress='validateLong(event)'>
					<p class="error">${error.weeklyTxnLimit}</p>
				</div>
				<div class="form-group">
					<label>Monthly Transaction Limit Amount*</label>
					<input type="text" name="monthlyTxnLimit" class="form-control input-sm" required value="${profile.monthlyTxnLimit}" onkeypress='validateLong(event)'>
					<p class="error">${error.monthlyTxnLimit}</p>
				</div>
				<div class="form-group">
					<button class="btn btn-danger btn-md btn-block btncu">Add Customer Profile</button>
				</div>
			</form>
		</div>
	</div>
</spr:page>
<script>
$("#registrationChargeDiv").hide();
$("#trialIntervalDiv").hide();
	$(document).ready(function() {
		$("#isRegistrationCharge").change(function() {
			if ($("#isRegistrationCharge").is(':checked')) {
				$("#isRegistrationCharge").attr('value', 'true');
				$("#registrationChargeDiv").show();
			} else {
				$("#isRegistrationCharge").attr('value', 'false');
				$("#registrationChargeDiv").hide();
			}
		});
		
		$("#isTraialPeriod").change(function() {
			if ($("#isTraialPeriod").is(':checked')) {
				$("#isTraialPeriod").attr('value', 'true');
				$("#trialIntervalDiv").show();
			} else {
				$("#isTraialPeriod").attr('value', 'false');
				$("#trialIntervalDiv").hide();
			}
		});
	});
	function validateDouble(evt) {
		  var theEvent = evt || window.event;
		  var key = theEvent.keyCode || theEvent.which;
		  key = String.fromCharCode( key );
		  var regex = /[0-9]|\./;
		  if( !regex.test(key) ) {
		    theEvent.returnValue = false;
		    if(theEvent.preventDefault) theEvent.preventDefault();
		  }
		}
	function validateLong(evt) {
		  var theEvent = evt || window.event;
		  var key = theEvent.keyCode || theEvent.which;
		  key = String.fromCharCode( key );
		  var regex = /[0-9]/;
		  if( !regex.test(key) ) {
		    theEvent.returnValue = false;
		    if(theEvent.preventDefault) theEvent.preventDefault();
		  }
		}
</script>
