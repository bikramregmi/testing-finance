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
<c:if test="${not empty errorMessage}">
	<div class="error">${errorMessage}</div>
</c:if>
<spr:page header1="Add Transaction">
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<form action="addTransaction" modelAttribute="transaction"
				method="post" class="col-md-112"
				style="float: none; margin-left: auto; margin-right: auto;">

				<div class="form-group">
					<fieldset>
						<legend>Select Sending Country</legend>
						<select name="remitCountry" class="form-control input-sm"
							required="required" id="remitCountry">
						
							<option value="${remitCountry.isoThree}" selected>${remitCountry.name}</option>
							
						</select>
						<p class="error">${error.remitCountry}</p>
					</fieldset>
				</div>

				<div class="form-group">
					<fieldset>
						<legend>Select Destination</legend>
						<select name="payoutCountry" class="form-control input-sm"
							required="required" id="payoutCountry">
							<option value="default">Select Destination Country</option>
							<c:if test="${fn:length(countryList) gt 0}">
								<c:forEach var="payoutCountry" items="${countryList}">
									<c:choose>
										<c:when
											test="${payoutCountry.name == transaction.payoutCountry}">
											<option value="${payoutCountry.isoThree}" selected>${payoutCountry.name}</option>
										</c:when>
										<c:otherwise>
											<option value="${payoutCountry.isoThree}">${payoutCountry.name}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:if>
						</select>
						<p class="error">${error.payoutCountry}</p>
					</fieldset>
				</div>

				
				<div class="form-group">
					<label>Sender</label> <select name="sender"
						class="form-control input-sm" required="required">
						<c:if test="${fn:length(senderCustomerList) gt 0}">
							<c:forEach var="sender" items="${senderCustomerList}">
								<option value="${sender.firstName}" selected>${sender.firstName}</option>
							</c:forEach>
						</c:if>

					</select>
					<p class="error">${error.sender}</p>
					<a href="/addCustomer">Add New Sender</a>
				</div>


				<div id="beneficiary-div" class="form-group">
					<label>Beneficiary</label> <select name="beneficiary"
						id="beneficiary" class="form-control input-sm" required="required">
						<option value="${transaction.beneficiary.firstName}" selected>${transaction.beneficiary}</option>

					</select>
					<p class="error">${error.beneficiary}</p>
					<a href="/addCustomer">Add New Beneficiary</a>
				</div>

				<div class="form-group sndamt">
					<label>Sending Amount</label> <input type="text"
						name="sendingAmount" id="sendingAmount"
						class="form-control input-sm" value="${transaction.sendingAmount}">
					<p class="error">${error.sendingAmount}</p>
				</div>

				<div class="form-group sendComm" id="commission-div">
					<label>Sending Fee</label> <select name="sendingCommission"
						id="sending-commission-select" class="form-control input-sm">

					</select>
					<p class="error">${error.sendingCommission}</p>
				</div>
				<div class="form-group totamt">
					<label>Total Amount</label> <input type="text"
						name="totalSendingAmount" id="totalSendingAmount"
						class="form-control input-sm"
						value="${transaction.totalSendingAmount}">
					<p class="error">${error.totalSendingAmount}</p>
				</div>

				<div class="form-group">
					<label>Receiving Amount</label> <input type="text"
						name="receivingAmount" id="receivingAmount"
						class="form-control input-sm"
						value="${transaction.receivingAmount}">
					<p class="error">${error.receivingAmount}</p>
				</div>
				<div class="form-group" id="exchange-rate-div">
					<label>Exchange Rate</label>
					<input id="exchangeRateId" type="hidden" name="exchangeRateId"/>
					<table class="table table-striped">
					
						<tr>
							<td>From Currency</td>
							<td id="ex-from-currency"></td>
						</tr>
						<tr>
							<td>To Currency</td>
							<td id="ex-to-currency"></td>
						</tr>
						<tr>
							<td>Selling Rate</td>
							<td id="ex-selling-rate"></td>
						</tr>

						<tr>
							<td>Buying Rate</td>
							<td id="ex-buying-rate"></td>
						</tr>

					</table>

				</div>
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">
						Remit</button>
				</div>
			</form>
		</div>
	</div>
</spr:page>

<script src="${pageContext.request.contextPath}/resources/js/onlinetransaction.js"></script>

<script>
	$(document).ready(function() {
		init();
	});
</script>