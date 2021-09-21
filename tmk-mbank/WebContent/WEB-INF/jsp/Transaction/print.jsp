<div id="printDiv">
	<div class="print-header">
		<div class="row">
			<div class="col-xs-4">
				<span class="mbank-logo"><img src="/assets/img/logo-normal.png" height="60" width="120" alt="" /></span>
			</div>
			<div class="col-xs-4">
				<span class="title">Payment Receipt</span>
			</div>
			<div class="col-xs-4">
				<span class="service-logo"><img ng-src="/mbank/serviceIcon/{{printDetail.serviceImageUrl}}" height="60" width="120" alt="" /></span>
			</div>
		</div>
	</div>
	<div class="print-body">
		<span class="text-heading">We confirm your payment has been made with following details:</span>
		<table class="table table-bordered">
			<tbody>
				<tr>
					<td>Transaction ID</td>
					<td>{{printDetail.transactionId}}</td>
				</tr>
				<tr>
					<td>Transaction DateTime</td>
					<td>{{printDetail.created}}</td>
				</tr>
				<tr>
					<td>Service To</td>
					<td>{{printDetail.serviceTo}}</td>
				</tr>
				<tr>
					<td>Channel</td>
					<td>{{printDetail.channel}}</td>
				</tr>
				<tr>
					<td>Service Name</td>
					<td>{{printDetail.serviceName}}</td>
				</tr>
				<tr>
					<td>Amount(NRP)</td>
					<td>{{printDetail.amount}}</td>
				</tr>
				<tr>
					<td>Transaction Status</td>
					<td>{{printDetail.status}}</td>
				</tr>
			</tbody>
		</table>
		<table class="table table-bordered">
			<tbody>
				<tr>
					<td>Bank Code</td>
					<td>{{printDetail.bankCode}}</td>
				</tr>
				<tr>
					<td>Bank Name</td>
					<td>{{printDetail.bankName}}</td>
				</tr>
				<tr>
					<td>Branch Name</td>
					<td>{{printDetail.branchName}}</td>
				</tr>
				<tr>
					<td>Receipt Print DateTime:</td>
					<td>{{printDetail.currentDate}}</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="print-footer">
		<div class="row">
			<div class="col-xs-offset-1 col-xs-3">
				<span class="enteredBy">{{printDetail.enteredBy}}</span>
				<p>{{printDetail.enteredByMobileNo}}</p>
				<hr>
				<span>Entered By</span>
			</div>
			<div class="col-xs-offset-4 col-xs-3 company-seal">
				<hr>
				<span>Company Seal</span>
			</div>
		</div>
	</div>
</div>
<style>
div#printDiv {
	position: absolute;
	left: 1000%;
	top: -1000%;
}
</style>