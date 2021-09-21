<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/custom.css" rel="stylesheet">
<link href="../css/starter-template.css" rel="stylesheet">
<!-- <script src ="../js/jquery.min.js"></script> -->
<!-- <script src ="../js/bootstrap.js"></script> -->


<spr:page header1="Add Discount" >
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<form action="addDiscount" modelAttribute="discount"
				method="post" class="col-md-12"
				style="float: none; margin-left: auto; margin-right: auto;">
				<div class="form-group">			
					<label>From Agent</label> 
					<select	name="fromAgent" class="form-control input-sm" required="required">
						<c:if test="${fn:length(superAgentList) gt 0}">
							<c:forEach var="superAgent" items="${superAgentList}">
								<c:choose>
									<c:when test="${superAgent.agencyName == discount.fromAgent}">
										<option value="${superAgent.agencyName}" selected>${superAgent.agencyName}</option>
									</c:when>
									<c:otherwise>
										<option value="${superAgent.agencyName}">${superAgent.agencyName}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:if>
					</select> 
					<p class="error">${error.fromAgent}</p>
				</div>
				
				<div class="form-group">
					<label>To Agent</label>
					<select	name="toAgent" class="form-control input-sm" required="required">
						<c:if test="${fn:length(superAgentList) gt 0}">
							<c:forEach var="superAgent" items="${superAgentList}">
								<c:choose>
									<c:when test="${superAgent.agencyName == discount.toAgent}">
										<option value="${superAgent.agencyName}" selected>${superAgent.agencyName}</option>
									</c:when>
									<c:otherwise>
										<option value="${superAgent.agencyName}">${superAgent.agencyName}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:if>
					</select> 
						<p class="error">${error.toAgent}</p>
				</div>
				<div class="form-group">
					<label>From Amount</label> <input type="text" name="fromAmount"
						class="form-control input-sm" required="required"
						value="${discount.fromAmount}">
						<p class="error">${error.fromAmount}</p>
				</div>
				<div class="form-group">
					<label>To Amount</label> <input type="text" name="toAmount"
						class="form-control input-sm" required="required"
						value="${discount.toAmount}">
						<p class="error">${error.toAmount}</p>
				</div>
				
				<div class="form-group">
					<label>Total Discount</label> <input type="text"
						name="overall_discount" class="form-control input-sm"
						required="required" value="${discount.overall_discount}">
				</div>
				
				<div class="form-group">
					<label>Total Discount Flat</label> <input type="text"
						name="overall_discountFlat" class="form-control input-sm"
						required="required" value="${discount.overall_discountFlat}">
				</div>

				<div class="form-group">
					<label>Discount Type</label> <select name="discountType"
						id="discountType" class="form-control input-sm"
						required="required">
						<option value="Agent" selected>Agent</option>
						<option value="BankAccout">BankAccount</option>
					</select>
					<p class="error">${error.discountType}</p>
				</div>
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Add
						Discount</button>
				</div>
			</form>
		</div>
</spr:page>
