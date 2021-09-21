<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<spr:page header1="Payment">
<input type="hidden" ng-init="generalpaymenturl='${serviceDTO.url}'"  ng-model="generalpaymenturl" >
						<input type="hidden" name="serviceUnique" ng-model="generalserviceUnique='${serviceDTO.uniqueIdentifier}'"  ng-model="generalserviceUnique">
	<div class="container-fluid container-main" ng-show="show" ng-if="!toggled" ng-hide ="nopackage" >
		<div class="row">

			<div class="col-lg-12 col-md-12 ">
				<div class="row">
					<div class="col-lg-5">
					<img alt="icon" src="/mbank/serviceIcon/${serviceDTO.icon}" height ="150" width = "150"/>
						<h2>${serviceDTO.service }</h2>
						
							<p>{{message}}</p>
						<div class="col-lg-12 form-group">
							<div id="form-group">
							<c:choose>
							<c:when test="${serviceDTO.uniqueIdentifier eq 'dishhome_online_topup'}">
							<label>Transaction ID</label>
							</c:when>
							
							<c:otherwise>
							<label>User Name</label>
							</c:otherwise>
							</c:choose>
								 <input class="form-control" type="text" ng-model=$parent.generaltransactionId  />
								<p class="error"></p>
							</div>
						</div>
						
					<%-- 	<c:choose>
							<c:when test="${serviceDTO.priceInput eq false}">
								<div class="col-lg-12 form-group">
									<div id="form-group">
										<label>Amount</label> <input type="text" ng-model="generalamount" name="amount" />
										<p class="error"></p>
									</div>
								</div>
							</c:when>
							<c:otherwise>
								<div id="form-group">

									
									<label>Amount</label> <select name="serviceId" ng-model="generalamount"
										class="form-control input-sm">
											<c:forEach var="pricerange" items="${serviceDTO.priceRange}">
												<option value="${pricerange}">${pricerange}</option>
											</c:forEach>
									</select>
									<p class="error"></p>
								</div>
							</c:otherwise>
						</c:choose> --%>


					</div>
					<div class="clearfix"></div>
					<div class="form-group">
					<button ng-click="generalMerchantPayment(generalpaymenturl)" class="btn btn-primary btn-md btn-block btncu">Submit</button>
				</div>
				</div>
			</div>
		</div>
	</div>
	
	<div ng-if = "toggled" class = "successResponse">
	<h3>{{message}}</h3>
	<h3>{{generalMessage}}</h3>
	<h5>User :{{userId}}</h5>
							
	<label>Package</label> <select ng-options="package as package.text for package in pakageList" name="serviceId" ng-model="$parent.packages" ng-change = "loadId(packages)" placeholder="Select A Package"
										class="form-control input-sm">
									<option value="0" selected="selected" disabled="disabled">Select A Package</option>		
									</select>
									
	<br />
	<button class="btn btn-primary" ng-click="onlinePaymentWlink('wlinkPayment')" >Payment</button>
	</div>
	
	<div ng-if = "nopackage" class = "successResponse">
	<h3>{{message1}}</h3>
	<h3>{{generalMessage}}</h3>
	<h3>Please pay the following amount.</h3>
	<h5>User :{{userId}}</h5>
							
	<div id="form-group">
										<label>Amount Rs.</label> <input disabled type="text" ng-model="$parent.generalamount" name="amount" />
										<p class="error"></p>
									</div>
									
	<br />
	<button class="btn btn-primary" ng-click="onlinePaymentWlink('wlinkPayment')" ng-disabled = "exporting">Payment</button>
	</div>
	
	<div ng-hide = "show" class = "successResponse">
	<h3>{{message1}}</h3>
	<h3>Transaction Id : {{transactionIdentifier}}</h3>
	<h3>Transaction Date : {{date}}</h3>
	<h3>Service To : {{serviceTo}}</h3>
	<br />
	<button class="btn btn-primary" ng-click = "reload()">Back</button>
	</div>

</spr:page>
<script src ="../js/customer/controller.js"></script>
