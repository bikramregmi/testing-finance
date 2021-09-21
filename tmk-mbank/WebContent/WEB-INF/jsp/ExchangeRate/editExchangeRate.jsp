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

<spr:page header1="Edit ExchangeRate">
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<form action="editExchangeRate" modelAttribute="exchangeRate" method="post"
				class="col-md-12"
				style="float: none; margin-left: auto; margin-right: auto;">
				<div class="form-group">
					<label>From Currency</label> <input type="hidden" name="id"
						value="${exchangeRate.id}"> 
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
										test="${currency.currencyName == exchangeRate.toCurrency}">
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
				<div class="form-group">
					<label>Status</label> <select name="status" id="status"
						class="form-control input-sm" required="required" >
						<c:choose>
							<c:when test="${exchangeRate.status} eq 'Active'">
								<option value="Active" selected>Active</option>
								<option value="Inactive">InActive</option>
							</c:when>
							
							<c:otherwise>
								<option value="Active" >Active</option>
								<option value="Inactive" selected>InActive</option>
							</c:otherwise>
							
						</c:choose>
						
					</select>
					<p class="error">${error.status}</p>
				</div>
				<div class="form-group">
					<label>Group Type</label>
					 <select name="type" id="type" class="form-control input-sm" required="required" >
					 	<c:choose>
						 	<c:when test="${exchangeRate.type} eq 'Country'">
						 		<option value="Country" selected>Country</option>
								<option value="SuperAgent">SuperAgent</option>
								<option value="Agent">Agent</option>
						 	</c:when>
						 	<c:when test="${exchangeRate.type} eq 'SuperAgent'">
						 		<option value="Country" >Country</option>
								<option value="SuperAgent" selected>SuperAgent</option>
								<option value="Agent">Agent</option>
						 	</c:when>
						 	<c:otherwise>
						 		<option value="Country" >Country</option>
								<option value="SuperAgent" >SuperAgent</option>
								<option value="Agent" selected>Agent</option>
						 	</c:otherwise>
						</c:choose>
					</select>
					<p class="error">${error.type}</p>
				</div>
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Edit
						ExchangeRate</button>
				</div>
			</form>

		</div>
</spr:page>
