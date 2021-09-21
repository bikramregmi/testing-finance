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

<spr:page header1="Edit Transaction">
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<form action="editTransaction" modelAttribute="transaction"
				method="post" class="col-md-12"
				style="float: none; margin-left: auto; margin-right: auto;">
				<div class="form-group">
					<label>Remit Channel</label> <select name="remitChannel"
						id="remitChannel" class="form-control input-sm"
						required="required" >
						<option value="agent" selected>agent</option>
						<option value="online">online</option>
					</select>
					 <input type="hidden" name="trackingNumber"
						value="${transaction.trackingNumber}"> 
					<p class="error">${error.remitChannel}</p>
				</div>
				<div class="form-group">
					<label>Payout Channel</label> <select name="payoutChannel"
						id="payoutChannel" class="form-control input-sm"
						required="required" >
						<option value="agent" selected>agent</option>
						<option value="bankAccount">bankAccount</option>
					</select>
					<p class="error">${error.payoutChannel}</p>
				</div>
				<div class="form-group">
					<label>Remit Agent</label> <select name="remitAgent"
						class="form-control input-sm" required="required">
						<c:if test="${fn:length(agentList) gt 0}">
							<c:forEach var="remitAgent" items="${agentList}">
								<c:choose>
									<c:when test="${remitAgent.firstName == transaction.remitAgent}">
										<option value="${remitAgent.firstName}" selected>${remitAgent.firstName}</option>
									</c:when>
									<c:otherwise>
										<option value="${remitAgent.firstName}">${remitAgent.firstName}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:if>
					</select>
					<p class="error">${error.remitAgent}</p>
				</div>
				<div class="form-group">
				<label>Payout Agent </label> <select name="payoutAgent"
						class="form-control input-sm" required="required">
						<c:if test="${fn:length(agentList) gt 0}">
							<c:forEach var="payoutAgent" items="${agentList}">
								<c:choose>
									<c:when test="${payoutAgent.firstName == transaction.payoutAgent}">
										<option value="${payoutAgent.firstName}" selected>${payoutAgent.firstName}</option>
									</c:when>
									<c:otherwise>
										<option value="${payoutAgent.firstName}">${payoutAgent.firstName}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:if>
					</select>
					<p class="error">${error.payoutAgent}</p>
				</div>
				<div class="form-group">
					<label>Sender</label> <select name="sender"
						class="form-control input-sm" required="required">
						<c:if test="${fn:length(customerList) gt 0}">
							<c:forEach var="sender" items="${customerList}">
								<c:choose>
									<c:when test="${sender.firstName == transaction.sender}">
										<option value="${sender.firstName}" selected>${sender.firstName}</option>
									</c:when>
									<c:otherwise>
										<option value="${sender.firstName}">${sender.firstName}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:if>
					</select>
					<p class="error">${error.sender}</p>
				</div>
				<div class="form-group">
					<label>Beneficiary</label> <select name="beneficiary"
						class="form-control input-sm" required="required">
						<c:if test="${fn:length(customerList) gt 0}">
							<c:forEach var="beneficiary" items="${customerList}">
								<c:choose>
									<c:when test="${beneficiary.firstName == transaction.beneficiary}">
										<option value="${beneficiary.firstName}" selected>${beneficiary.firstName}</option>
									</c:when>
									<c:otherwise>
										<option value="${beneficiary.firstName}">${beneficiary.firstName}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:if>
					</select>
					<p class="error">${error.beneficiary}</p>
				</div>
				<div class="form-group">
					<label>Remit Country</label> <select name="remitCountry"
						class="form-control input-sm" required="required">
						<c:if test="${fn:length(countryList) gt 0}">
							<c:forEach var="remitCountry" items="${countryList}">
								<c:choose>
									<c:when test="${remitCountry.name == transaction.remitCountry}">
										<option value="${remitCountry.name}" selected>${remitCountry.name}</option>
									</c:when>
									<c:otherwise>
										<option value="${remitCountry.name}">${remitCountry.name}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:if>
					</select>
					<p class="error">${error.remitCountry}</p>
				</div>
				<div class="form-group">
					<label>Payout Country</label> <select name="payoutCountry"
						class="form-control input-sm" required="required">
						<c:if test="${fn:length(countryList) gt 0}">
							<c:forEach var="payoutCountry" items="${countryList}">
								<c:choose>
									<c:when test="${payoutCountry.name == transaction.payoutCountry}">
										<option value="${payoutCountry.name}" selected>${payoutCountry.name}</option>
									</c:when>
									<c:otherwise>
										<option value="${payoutCountry.name}">${payoutCountry.name}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:if>
					</select>
					<p class="error">${error.payoutCountry}</p>
				</div>
				<div class="form-group">
					<label>Sending Amount</label> <input type="text" name="sendingAmount"
						class="form-control input-sm" value="${transaction.sendingAmount}">
					<p class="error">${error.sendingAmount}</p>
				</div>
				<div class="form-group">
					<label>Sending Currency</label> <select name="sendingCurrency"
						class="form-control input-sm" required="required">
						<c:if test="${fn:length(currencyList) gt 0}">
							<c:forEach var="sendingCurrency" items="${currencyList}">
								<c:choose>
									<c:when test="${sendingCurrency.currencyCode == transaction.sendingCurrency}">
										<option value="${sendingCurrency.currencyCode}" selected>${sendingCurrency.currencyCode}</option>
									</c:when>
									<c:otherwise>
										<option value="${sendingCurrency.currencyCode}">${sendingCurrency.currencyCode}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:if>
					</select>
					<p class="error">${error.sendingCurrency}</p>
				</div>
				<div class="form-group">
					<label>Sending Commission</label> <input type="text" name="sendingCommission"
						class="form-control input-sm" value="${transaction.sendingCommission}">
					<p class="error">${error.sendingCommission}</p>
				</div>
				<div class="form-group">
					<label>Commission Ref</label> <select name="commissionRef"
						class="form-control input-sm" required="required">
						<c:if test="${fn:length(commissionList) gt 0}">
							<c:forEach var="commissionRef" items="${commissionList}">
								<c:choose>
									<c:when test="${commissionRef.fromAgent == transaction.commissionRef}">
										<option value="${commissionRef.fromAgent}" selected>${commissionRef.fromAgent}</option>
									</c:when>
									<c:otherwise>
										<option value="${commissionRef.fromAgent}">${commissionRef.fromAgent}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:if>
					</select>
					<p class="error">${error.commissionRef}</p>
				</div>
				<div class="form-group">
					<label>Receiving Amount</label> <input type="text" name="receivingAmount"
						class="form-control input-sm" value="${transaction.receivingAmount}">
					<p class="error">${error.receivingAmount}</p>
				</div>
				<div class="form-group">
					<label>Discount Amount</label> <input type="text" name="discountAmount"
						class="form-control input-sm" value="${transaction.discountAmount}">
					<p class="error">${error.discountAmount}</p>
				</div>
				<div class="form-group">
					<label>Discount Ref</label> <select name="discountRef"
						class="form-control input-sm" required="required">
						<c:if test="${fn:length(discountList) gt 0}">
							<c:forEach var="discountRef" items="${discountList}">
								<c:choose>
									<c:when test="${discountRef.fromAgent == transaction.discountRef}">
										<option value="${discountRef.fromAgent}" selected>${discountRef.fromAgent}</option>
									</c:when>
									<c:otherwise>
										<option value="${discountRef.fromAgent}">${discountRef.fromAgent}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:if>
					</select>
					<p class="error">${error.discountRef}</p>
				</div>
				<div class="form-group">
					<label>Exchange Rate</label> <input type="text" name="exchangeRate"
						class="form-control input-sm" value="${transaction.exchangeRate}">
					<p class="error">${error.exchangeRate}</p>
				</div>
				<div class="form-group">
					<label>Sending Commission</label> <input type="text" name="sendingCommission"
						class="form-control input-sm" value="${transaction.sendingCommission}">
					<p class="error">${error.sendingCommission}</p>
				</div>
				<div class="form-group">
					<label>Remittance Commission</label> <input type="text" name="remittanceCommission"
						class="form-control input-sm" value="${transaction.remittanceCommission}">
					<p class="error">${error.remittanceCommission}</p>
				</div>
				<div class="form-group">
					<label>Transaction Status</label> <select name="transactionStatus"
						id="transactionStatus" class="form-control input-sm"
						required="required" onClick="staff()">
						<option value="Created" selected>Created</option>
						<option value="Hold">Hold</option>
						<option value="Completed">Completed</option>
						<option value="Cancelled">Cancelled</option>
					</select>
					<p class="error">${error.transactionStatus}</p>
				</div>
				<div class="form-group">
					<label>Remit User</label> <select name="remitUser"
						class="form-control input-sm" required="required">
						<c:if test="${fn:length(userList) gt 0}">
							<c:forEach var="remitUser" items="${userList}">
								<c:choose>
									<c:when test="${remitUser.username == transaction.remitUser}">
										<option value="${remitUser.username}" selected>${remitUser.username}</option>
									</c:when>
									<c:otherwise>
										<option value="${remitUser.username}">${remitUser.username}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:if>
					</select>
					<p class="error">${error.remitUser}</p>
				</div>
				<div class="form-group">
					<label>Payout User</label> <select name="payoutUser"
						class="form-control input-sm" required="required">
						<c:if test="${fn:length(userList) gt 0}">
							<c:forEach var="payoutUser" items="${userList}">
								<c:choose>
									<c:when test="${payoutUser.username == transaction.payoutUser}">
										<option value="${payoutUser.username}" selected>${payoutUser.username}</option>
									</c:when>
									<c:otherwise>
										<option value="${payoutUser.username}">${payoutUser.username}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:if>
					</select>
					<p class="error">${error.payoutUser}</p>
				</div>
				<div class="form-group">
					<label>Aml Type</label> <select name="amlStatus"
						id="amlStatus" class="form-control input-sm"
						required="required" onClick="staff()">
						<option value="hit" selected>hit</option>
						<option value="clear">clear</option>
						<option value="match">match</option>
					</select>
					<p class="error">${error.transactionType}</p>
				</div>
				<div class="form-group">
					<label>Aml Ref</label> <select name="amlRef"
						class="form-control input-sm" required="required">
						<c:if test="${fn:length(amlList) gt 0}">
							<c:forEach var="amlRef" items="${amlList}">
								<c:choose>
									<c:when test="${amlRef.name == transaction.amlRef}">
										<option value="${amlRef.name}" selected>${amlRef.name}</option>
									</c:when>
									<c:otherwise>
										<option value="${amlRef.name}">${amlRef.name}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:if>
					</select>
					<p class="error">${error.amlRef}</p>
				</div>
				<div class="form-group">
					<label>Aml Checked By</label> <select name="amlCheckedBy"
						class="form-control input-sm" required="required">
						<c:if test="${fn:length(userList) gt 0}">
							<c:forEach var="amlCheckedBy" items="${userList}">
								<c:choose>
									<c:when test="${amlCheckedBy.username == transaction.amlCheckedBy}">
										<option value="${amlCheckedBy.username}" selected>${amlCheckedBy.username}</option>
									</c:when>
									<c:otherwise>
										<option value="${amlCheckedBy.username}">${amlCheckedBy.username}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:if>
					</select>
					<p class="error">${error.amlCheckedBy}</p>
				</div>	
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Edit
						Transaction</button>
				</div>
			</form>
		</div>
</spr:page>