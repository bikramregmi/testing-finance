<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<spr:page header1="Payment">
<input type="hidden" ng-init="generalpaymenturl='${serviceDTO.url}'"  ng-model="generalpaymenturl" >
<input type="hidden" name="serviceUnique" ng-model="generalserviceUnique='${serviceDTO.uniqueIdentifier}'"  ng-model="generalserviceUnique">
	<div class="container-fluid container-main" ng-show="show" ng-if="!toggled" ng-hide ="success">
		<div class="row">
			<div class="col-lg-12 col-md-12 ">
				<div class="row">
					<div class="col-lg-5">
						<img alt="icon" src="/images/serviceIcon/${serviceDTO.icon}" height ="150" width = "150"/>
						<h2>${serviceDTO.service }</h2>
						<p>{{message}}</p>
						<p>{{generalMessage}}</p>
						<div class="col-lg-12 form-group">
							<div class="form-group">
								<label>Office Code </label>
								<select	ng-model ="$parent.officeCode" class="form-control input-sm" required="required" ng-click="getOfficeCode()">
									<option value = "" selected disabled>Select Service Category</option>

									<option ng-repeat="value in neaOfficeCodeList" value={{value.officeCode}}>{{value.officeCode}} {{value.office}} </option>
									
							<!-- 		<option value="201">201 Ratnapark DC</option>
									<option value="205">205 Kuleshwor DC</option>
									<option value="207">207 Pulchowk DC</option>
									<option value="211">211 Bharatpur DC</option>
									<option value="214">214 Naxal DC</option>
									<option value="215">215 Balaju DC</option>
									<option value="216">216 Lagankhel DC</option>
									<option value="217">217 CHAPAGAUN SDC</option>
									<option value="218">218 LUBHU SDC</option>
									<option value="219">219 Baneshwor DC</option>
									<option value="220">220 Chabahil DC</option>
									<option value="221">221 Jorpati DC</option>
									<option value="222">222 Maharajgunj DC</option>
									<option value="223">223 Budhanilkantha DC</option>
									<option value="226">226 Pokhara DC</option>
									<option value="227">227 Pokhara Gramin DC</option>
									<option value="237">237 Badegaun SDC</option>
									<option value="244">244 Thimi DC</option>
									<option value="245">245 Bhaktapur DC</option>
									<option value="246">246 Kirtipur DC</option>
									<option value="322">322 Sankhu DC</option> -->
									
								</select> 
							</div>
							<div class="form-group">
								<label>SC No</label>
								 <input class="form-control" type="text" ng-model="$parent.scno"  />
								<p class="error"></p>
							</div>
							<div class="form-group">
								<label>Customer Id</label>
								<input class="form-control" type="text" ng-model="$parent.customerId"  />
								<p class="error"></p>
							</div>

						</div>
						<div class="clearfix"></div>
						<div class="form-group">
							<button ng-click="neaGetPayment()" class="btn btn-primary btn-md btn-block btncu"ng-disabled = "exporting">Submit</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div ng-if = "toggled" class = "successResponse">
		<h3>{{message}}</h3>
		<h3>{{generalMessage}}</h3>
		<div class="row">
			<div class="col-md-5">
				<ul class="list-group">
					<li class="list-group-item"><b>Customer Id :</b> {{customerId}}</li>
					<li class="list-group-item"><b>Customer Name :</b> {{hash.customerName}}</li>
					<li class="list-group-item"><b>Total Amount :</b> Rs {{hash.generalamount/100}}</li>
				</ul>
			</div>
		</div>
		<p class="small-text pull-right"><font color="red">*</font>Only The Highlighted One Will Be Paid</p>
		<table class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>Date</th>
					<th>Amount</th>
					<th>Status</th>
					<th>Total Amount</th>
					<th>Description</th>
				</tr>
			</thead>
			<tbody>
				<tr ng-repeat="x in payment">
					<td ng-class="{hightlight: $index==0}" class="{{x._billDate}}">{{x._billDate}}</td>
					<td ng-class="{hightlight: $index==0}" class="{{x._billDate}}">{{x._billAmount/100}}</td>
					<td ng-class="{hightlight: $index==0}" class="{{x._billDate}}">{{x._status}}</td>
					<td ng-class="{hightlight: $index==0}" class="{{x._billDate}}">{{x._amount/100}}</td>
					<td ng-class="{hightlight: $index==0}" class="{{x._billDate}}">{{x._description}}</td>
				</tr>
			</tbody>
		</table>
		<label>Pay Due Upto</label> <select ng-options="x as x._billDate for x in payment" name="serviceId" ng-model="$parent.dueDate" ng-change = "addDueAmounts(dueDate)" placeholder="Select A Date"
										class="form-control input-sm">
									<option value="0" selected="selected" disabled="disabled">Select A Due Date</option>		
									</select>
		<button class="btn btn-primary" ng-click="onlinePaymentNea()" >Payment</button>
	</div>
	<div ng-hide = "show" class = "successResponse">
		<h3>{{message}}</h3>
		<div class="row">
			<div class="col-md-5">
				<ul class="list-group">
					<li class="list-group-item"><b>Transaction Date :</b> {{date}}</li>
					<li class="list-group-item"><b>Transaction Id :</b> {{transactionIdentifier}}</li>
					<li class="list-group-item"><b>Service To :</b> {{serviceTo}}</li>
					<li class="list-group-item"><b>Office Name :</b> {{officeName}}</li>
					<li class="list-group-item"><b>Due Bill Date :</b> {{dueDate}}</li>
				</ul>
			</div>
		</div>
		<button class="btn btn-primary" ng-click = "reload()">Back</button>
	</div>
</spr:page>
<script src ="../js/customer/controller.js"></script>
<style>
table{
 table-layout: fixed; 
 font-size: 12px;
 margin-top:0px;
}
.hightlight{
 background-color: rgba(24, 150, 79, 0.53);
 opacity: 2;
}
td{
 word-wrap: break-word;
}
.small-text{
 color: black;
 font-size:9px;
 margin-bottom:0px;
}
</style>