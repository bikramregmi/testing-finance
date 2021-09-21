<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<spr:page header1="Payment">
	<div class="container-fluid container-main" ng-show="show">
		<div class="row">

			<div class="col-lg-8 col-md-8 ">
				<div class="row">
					<div class="col-lg-12">
					<img alt="icon" src="/mbank/serviceIcon/${serviceDTO.icon}" height ="150" width = "150"/>
						<h2>${serviceDTO.service }</h2>
						<input type="hidden" ng-init="generalpaymenturl='${serviceDTO.url}'"  ng-model="generalpaymenturl" >
						<input type="hidden" name="serviceUnique" ng-model="generalserviceUnique='${serviceDTO.uniqueIdentifier}'"  ng-model="generalserviceUnique">
							<p>{{message}}</p>
							<p>{{generalMessage}}</p>
						<div class="col-lg-12 form-group">
							<div class="form-group">
							<label>Subisu Username</label>
							<input class="form-control" type="text" ng-model="generalcustomerId"  />
								<p class="error"></p>
								
							<label>Mobile No</label>
								 <input class="form-control" type="text" ng-model="generaltransactionId"  />
								<p class="error"></p>
							</div>
						</div>
						
						<c:choose>
							<c:when test="${serviceDTO.priceInput eq false}">
								<div class="col-lg-12 form-group">
									<div class="form-group">
										<label>Amount</label> <input class="form-control" type="text" ng-model="generalamount" name="amount" />
										<p class="error"></p>
									</div>
								</div>
							</c:when>
							<c:otherwise>
								<div class="form-group">

									
									<label>Amount</label> <select name="serviceId" ng-model="generalamount"
										class="form-control input-sm">
											<c:forEach var="pricerange" items="${serviceDTO.priceRange}">
												<option value="${pricerange}">${pricerange}</option>
											</c:forEach>
									</select>
									<p class="error"></p>
								</div>
							</c:otherwise>
						</c:choose>


					</div>
					<div class="clearfix"></div>
					<div class="form-group">
					<button ng-click="subishuMerchantPayment()" class="btn btn-primary btn-md btn-block btncu"ng-disabled = "exporting">Submit</button>
				</div>
				</div>
			</div>
		</div>
	</div>
	
	<div ng-hide = "show" class = "successResponse">
	<h3>{{message}}</h3>
	<h3>Transaction Id : {{transactionIdentifier}}</h3>
	<h3>Transaction Date : {{date}}</h3>
	<h3>Service To : {{serviceTo}}</h3>
	<br />
	<button class="btn btn-primary" ng-click = "reload()">Back</button>
	</div>

</spr:page>
<script src ="../js/customer/controller.js"></script>
