<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
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



<spr:page header1="Add SMS Mode" >

<div class="col-md-12"  ng-controller="smsModeController">


	<div class="row col-md-4">
		<div class="break"></div>
		<form  action="${pageContext.request.contextPath}/smsmode/add" modelAttribute="smsmode" method="post" class="col-md-12" style="float: none; margin-left: auto; margin-right: auto;" accept-charset="UTF-8">
			<div class="form-group">
					<label>Select Bank*</label>
					<c:choose>
						<c:when test="${forBank eq true }">
							<input type="text" class="form-control input-sm" id="bank" name="bank" value="${bank.name}" required="required" readonly="readonly"  ng-init="onBankChange()">
						</c:when>
						<c:otherwise>
							<select	name="bank" class="form-control input-sm" required="required" id="selectBank"  ng-model="selectedBank" ng-change="onBankChange()">
								<c:if test="${fn:length(bankList) gt 0}">
									<c:forEach var="bank" items="${bankList}">
										<option value="${bank.name}">${bank.name} -- ${bank.address}</option>
									</c:forEach>
								</c:if>
							</select>
						</c:otherwise>
					</c:choose>
					 
				</div>
			
			<%-- 	<div class="form-group">
					<label>SMS Type</label>
					<select class="form-control" id="smsType" name="smsType">
						<option>Select SMS Type</option>
							<c:if test="${fn:length(smsType) gt 0}">
								<c:forEach var="sType" items="${smsType}">
									<option value="${sType}">${sType}</option>
								</c:forEach>
							</c:if>
					</select>
				</div> --%>
					<div class="form-group" ng-hide="hide">
					<label>SMS Type</label>
					<select class="form-control" id="smsType" name="smsType">
						<!-- <option>Select SMS Type</option> -->
							<option ng-repeat="value in smsModeList" value={{value}}>{{value}}</option>
					</select>
				</div>
				<div class="form-group">
					<label>Message*</label>
					<textarea rows="4" cols="50" name="message" id="message"></textarea>
					<span id='remainingC'></span>
				</div>

			<div class="form-group">
				<button class="btn btn-primary btn-md btn-block btncu">Add</button>
			</div>
		</form>
</div>
</div>

</spr:page>
<script src ="../js/customer/controller.js"></script>
<script src="${pageContext.request.contextPath}/js/Angular/smsMode.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery3.3.1-jquery.min.js"></script>
<script>
$(document).ready(function() {
	$('#message').keypress(function(){
	
	    if(this.value.length > 160){
	        return false;
	    }
	    $("#remainingC").html("Remaining characters : " +(160 - this.value.length));
	});
});
</script>

