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

<spr:page header1="Confirm Transaction">
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<form action="${pageContext.request.contextPath}/transaction/confirm" modelAttribute="transaction"
				method="post" class="col-md-112" style="float: none; margin-left: auto; margin-right: auto;">
					<input type="hidden" name="transactionUniqueNumber" value="${transaction.uniqueNumber}"/>
		
			<table class="table table-striped" style="font-size: 12px !important;">
				<tr>
					<td>Unique Number</td>
					<td>${transaction.uniqueNumber}</td>
				</tr>
				<tr>
					<td>Destination Country</td>
					<td>${transaction.payoutCountry}</td>
				</tr>
				<tr>
					<td>Sender</td>
					<td>${transaction.sender}</td>
				</tr>
				<tr>
					<td>Beneficiary</td>
					<td>${transaction.beneficiary}</td>
				</tr>
				<tr>
					<td>Payout Channel</td>
					<td>${transaction.payoutChannel}</td>
				</tr>
				<tr>
					<td>Remit Agent</td>
					<td>${transaction.remitAgent}</td>
				</tr>
				<tr>
					<td>Payout Agent</td>
					<td>${transaction.payoutAgent}</td>
				</tr>
				
				<tr>
					<td>Sending Amount</td>
					<td>${transaction.sendingAmount}</td>
				</tr>
				<tr>
					<td>Sending Fee</td>
					<td>${transaction.sendingCurrency}.${transaction.transactionFee}</td>
				</tr>
				
				<tr>
					<td>Total  Amount</td>
					<td>${transaction.sendingCurrency}.${transaction.totalSendingAmount}</td>
				</tr>
				
				<tr>
					<td>Receiving Amount</td>
					<td>${transaction.receivingCurrency}.${transaction.receivingAmount}</td>
				</tr>
				
				<tr>
					<td>Exchange Rate</td>
					<td>${transaction.exchangeRate}</td>
				</tr>
				
				<tr>
						<td>
							<div class="form-group">
								<button class="btn btn-primary btn-md btn-block btncu">
									Confirm Transaction</button>
							</div>
						</td>
				</tr>
					
			</table>	
			</form>	
				<%-- <div class="form-group">
					<label>Unique Number</label> <input type="text" name="uniqueNumber"
						class="form-control input-sm" readonly value="${transaction.uniqueNumber}">
						<input type="hidden" name="trackingNumber"
						value="${transaction.trackingNumber}"> 
					<p class="error">${error.uniqueNumber}</p>
				</div>
				<div class="form-group">
					<fieldset>
					<label>Destination Country</label> <input type="text" name="payoutCountry"
						class="form-control input-sm" readonly value="${transaction.payoutCountry}" readonly>
					<p class="error">${error.payoutCountry}</p>
					</fieldset>
				</div>
				

				<div class="form-group">
					<label>Sender</label> <input type="text" name="sender"
						class="form-control input-sm" readonly value="${transaction.sender}">
					<p class="error">${error.sender}</p>
				</div>
				
				<div class="form-group">
					<label>Beneficiary</label> <input type="text" name="beneficiary"
						class="form-control input-sm" readonly value="${transaction.beneficiary}">
					<p class="error">${error.beneficiary}</p>
				</div>
				
				<div class="form-group">
					<label>Payout Channel</label> <input type="text" name="payoutChannel"
						class="form-control input-sm" readonly value="${transaction.payoutChannel}">
					<p class="error">${error.payoutChannel}</p>
				</div>
				
				<div class="form-group">
					<label>Remit Agent</label> <input type="text" name="remitAgent"
						class="form-control input-sm" readonly value="${transaction.remitAgent}">
					<p class="error">${error.remitAgent}</p>
				</div>
				<div class="form-group">
					<label>Payout Agent</label> <input type="text" name="payoutAgent"
						class="form-control input-sm" readonly value="${transaction.payoutAgent}">
					<p class="error">${error.payoutAgent}</p>
				</div>
				 --%>
				<%-- <div class="form-group sndamt">
					<label>Bank</label> <input type="text" name="bank"
						class="form-control input-sm" readonly value="${transaction.bank}">
					<p class="error">${error.sendingAmount}</p>
				</div>
				<div class="form-group sndamt">
					<label>Bank Branch</label> <input type="text" name="branch"
						class="form-control input-sm" readonly value="${transaction.branch}">
					<p class="error">${error.branch}</p>
				</div>
				<div class="form-group sndamt">
					<label>Bank Account Number</label> <input type="text" name="bankAccountNumber"
						class="form-control input-sm" readonly value="${transaction.bankAccountNumber}">
					<p class="error">${error.bankAccountNumber}</p>
				</div> --%>
				
				
				
				<%-- <div class="form-group sndamt">
					<label>Sending Amount</label> <input type="text" name="sendingAmount"
						class="form-control input-sm" readonly value="${transaction.sendingAmount}">
					<p class="error">${error.sendingAmount}</p>
				</div>
				
				<div class="form-group">
					<label>Sending Fee</label> <input type="text" name="sendingCommission"
						class="form-control input-sm" readonly value="${transaction.sendingCommission}">
					<p class="error">${error.sendingCommission}</p>
				</div>
				<div class="form-group totamt">
					<label>Total  Amount</label> <input type="text" name="totalSendingAmount"
						class="form-control input-sm" readonly value="${transaction.totalSendingAmount}">
					<p class="error">${error.totalSendingAmount}</p>
				</div>

				<div class="form-group">
					<label>Receiving Amount</label> <input type="text" name="receivingAmount"
						class="form-control input-sm" readonly value="${transaction.receivingAmount}">
					<p class="error">${error.receivingAmount}</p>
				</div>
				<div class="form-group">
					<label>Exchange Rate</label> <input type="text" name="exchangeRate"
						class="form-control input-sm" readonly value="${transaction.exchangeRate}">
					<p class="error">${error.exchangeRate}</p>
				</div>
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">
						Payout Transaction</button>
				</div> --%>
		</div>
	</div>
</spr:page>