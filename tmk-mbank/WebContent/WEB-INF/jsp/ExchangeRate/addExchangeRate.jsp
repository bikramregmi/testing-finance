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

<spr:page header1="Add ExchangeRate">
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<form action="addExchangeRate" modelAttribute="exchangeRate" method="post"
				class="col-md-12"
				style="float: none; margin-left: auto; margin-right: auto;">
				<div class="form-group" >
					<label>From Currency</label> 
					<select name="fromCurrency"
						class="form-control input-sm" required="required">
						<c:if test="${fn:length(currencyList) gt 0}">
						<c:forEach var="currency" items="${currencyList}">
								<c:choose>
									<c:when
										test="${currency.currencyCode == exchangeRate.fromCurrency}">
										<option value="${currency.currencyCode}" selected>${currency.currencyCode}</option>
									</c:when>
									<c:otherwise>
										<option value="${currency.currencyCode}">${currency.currencyCode}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							</c:if>
					</select> 
					<p class="error">${error.fromCurrency}</p>
				</div>
				<div class="form-group">
					<label>To Currency</label> 
					<select name="toCurrency"
						class="form-control input-sm" required="required">
						<c:if test="${fn:length(currencyList) gt 0}">
						<c:forEach var="currency" items="${currencyList}">
								<c:choose>
									<c:when
										test="${currency.currencyCode == exchangeRate.toCurrency}">
										<option value="${currency.currencyCode}" selected>${currency.currencyCode}</option>
									</c:when>
									<c:otherwise>
										<option value="${currency.currencyCode}">${currency.currencyCode}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							</c:if>
					</select> 
					<p class="error">${error.toCurrency}</p>
				</div>
				<div class="form-group">
					<label>Buying Rate </label> <input type="text" name="buyingRate"
						class="form-control input-sm" required="required"
						value="${exchangeRate.buyingRate}">
					<p class="error">${error.buyingRate}</p>
				</div>
				
				<div class="form-group">
					<label>Selling Rate </label> <input type="text" name="sellingRate"
						class="form-control input-sm" required="required"
						value="${exchangeRate.sellingRate}">
					<p class="error">${error.sellingRate}</p>
				</div>
				<%-- <div class="form-group">
					<label>Status</label> <select name="status" id="status"
						class="form-control input-sm" required="required" >
						<option value="Active" selected>Active</option>
						<option value="InActive">InActive</option>
					</select>
					<p class="error">${error.status}</p>
				</div> --%>
				<div class="form-group gptype">
					<label>Group Type</label> <select name="type" id="type"
						class="form-control input-sm" required="required" >
						<option value="Country" selected>Country</option>
						<option value="SuperAgent">SuperAgent</option>
						<option value="Agent">Agent</option>
					</select>
					<p class="error">${error.type}</p>
				</div>

				<div class="form-group">
				
					<label>SuperAgent</label> <select name="superAgent"
						class="form-control input-sm" required="required" id="superagent-select">
						<c:if test="${fn:length(superAgentList) gt 0}">
							<c:forEach var="superAgent" items="${superAgentList}">
								<c:choose>
									<c:when
										test="${superAgent.agencyName == exchangeRate.superAgent}">
										<option value="${superAgent.agencyName}" selected>${superAgent.agencyName}</option>
									</c:when>
									<c:otherwise>
										<option value="${superAgent.agencyName}">${superAgent.agencyName}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:if>
					</select>
					<p class="error">${error.superAgent}</p>
				</div>
				
				<div class="form-group">
					<label>Agent</label> <select id="agent-select" name="agent"
						class="form-control input-sm" required="required">
						<c:if test="${fn:length(agentList) gt 0}">
							<c:forEach var="agent" items="${agentList}">
								<c:choose>
									<c:when test="${agent.agencyName == exchangeRate.agent}">
										<option value="${agent.agencyName}" selected>${agent.agencyName}</option>
									</c:when>
									<c:otherwise>
										<option value="${agent.agencyName}">${agent.agencyName}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:if>
					</select>
					<p class="error">${error.agent}</p>
				</div>
				
				
				<div class="form-group" >
					<label>Country</label> <select name="country"
						class="form-control input-sm" required="required">
						<c:if test="${fn:length(countryList) gt 0}">
							<c:forEach var="country" items="${countryList}">
								<c:choose>
									<c:when test="${country.name == exchangeRate.country}">
										<option value="${country.name}" selected>${country.name}</option>
									</c:when>
									<c:otherwise>
										<option value="${country.name}">${country.name}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:if>
					</select>
					<p class="error">${error.country}</p>
				</div>
				
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Add
						ExchangeRate</button>
				</div>
			</form>
		</div>
</spr:page>

<script src="${pageContext.request.contextPath}/resources/js/exchangerate.js"></script>

<script>
	$(document).ready(function(){
		init();
	});
</script>