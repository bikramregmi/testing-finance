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
						<img alt="icon" src="/mbank/serviceIcon/${serviceDTO.icon}"
							height="150" width="150" />
						<h2>${serviceDTO.service }</h2>
						<input type="hidden"
							ng-init="generalpaymenturl='${serviceDTO.url}'"
							ng-model="generalpaymenturl"> <input type="hidden"
							name="serviceUnique"
							ng-model="generalserviceUnique='${serviceDTO.uniqueIdentifier}'"
							ng-model="generalserviceUnique">
						<p>{{message}}</p>
						<p>{{generalMessage}}</p>
						<div class="col-lg-12">
							<div class="form-group col-md-5">
								<c:choose>
									<c:when
										test="${serviceDTO.uniqueIdentifier eq 'dishhome_online_topup'||serviceDTO.uniqueIdentifier eq 'simtv_online_topup'}">
										<label>Chip ID</label>
									</c:when>

									<c:otherwise>
										<label>Phone/Mobile No</label>
									</c:otherwise>
								</c:choose>
								<input type="text" class="form-control"
									ng-model="generaltransactionId" />
								<p class="error"></p>
							</div>
						</div>

						<c:choose>
							<c:when test="${serviceDTO.priceInput eq false}">
								<div class="col-lg-12 form-group">
									<div class="form-group col-md-5">
										<label>Amount</label> <input type="text" class="form-control"
											ng-model="generalamount" name="amount" />
										<p class="error"></p>
									</div>
								</div>
							</c:when>
							<c:otherwise>
								<div class="form-group col-md-5">
									<label>Amount</label> <select name="serviceId"
										ng-model="generalamount" class="form-control input-sm">
										<c:forEach var="pricerange" items="${serviceDTO.priceRange}">
											<option value="${pricerange}">${pricerange}</option>
										</c:forEach>
									</select>
									<p class="error"></p>
								</div>
							</c:otherwise>
						</c:choose>

						<c:if
							test="${ServicesDTO.uniqueIdentifier == 'NTCPIN' || ServicesDTO.uniqueIdentifier == 'NCELLPIN' || ServicesDTO.uniqueIdentifier == 'DHPIN' || ServicesDTO.uniqueIdentifier == 'CDMAPIN' || ServicesDTO.uniqueIdentifier == 'BROADLINKPIN' || ServicesDTO.uniqueIdentifier == 'BROADTELPIN' || ServicesDTO.uniqueIdentifier == 'UTLPIN' or ServicesDTO.uniqueIdentifier eq 'SMARTCELLPIN'}">
							<span> 
							<div class="form-control col-md-5">
							<label>Quantity</label> <!-- <span ng-show="rangeHide">
                                                    <input type="range" name="range" ng-model="cardQuantity"  min="{{min}}" ng-max="{{max}}" />
                                                 </span> -->
							<input type="number" ng-model="cardQuantity" placeholder="Quantity" class="form-control" />
							</div>
							</span>
						</c:if>
					</div>
					<div class="clearfix"></div>
					<div class="form-group col-md-5">
						<button ng-click="generalMerchantPayment(generalpaymenturl)"
							class="btn btn-primary btn-md btn-block btncu"
							ng-disabled="exporting">Submit</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div ng-hide="show" class="successResponse" >
	<!--logo & name of the bank to be added !!, service to to be changed to paid to !!, if branch is present then mention the branch too!!  -->
		<div ng-model="tableName" id="reportTable">
		<table  width="100%" >
		<tr style="background-color:#E5E7E9 ; color:#070809;">
		<th rowspan="2">
		<h3>{{message}}</h3></th>
		<td align="center">
		<img alt="icon" src="/mbank/serviceIcon/{{service_icon}}"
							height="150" width="150"/></td></tr>
		
						<tr style="background-color:#E5E7E9" align="center"><td ><h6 style="color:#2874A6 ">{{bank_name}}</h6></td></tr>
		
		<tr><td>
		<h3>Transaction Id    : {{transactionIdentifier}}</h3>
		</td>
		</tr>
		<tr>
		<td>
		<h3>Transaction Date  : {{date | date}}</h3>
		</td>
		</tr>
		<tr><td>
		<h3>Payment To        : {{serviceTo}}</h3>
		</td>
		</tr>
		<tr>
		<td>
		<h3>Bank/Branch       : {{(branch_name!=""?bank_name+"/"+branch_name:bank_name) }}	</h3></td>
		</tr>
		</table>
		
		</div>
		<br />
		<button class="btn btn-primary" ng-click="print('printDiv')">Print</button> 
		<button class="btn btn-primary" ng-click="reload()">Back</button>
	</div>
 <%@include file="../Transaction/print.jsp" %>
</spr:page>
<script src="../js/customer/controller.js"></script>
<script type="text/javascript">
     function print(){
    	// alert("hi");
	        var divToPrint = document.getElementById("reportTable");
		      //alert(divToPrint);
		     newWin = window.open("","Print","");
		        newWin.document.write(divToPrint.outerHTML);
		        newWin.print();
		        newWin.close();
		     

   
     }
</script>
