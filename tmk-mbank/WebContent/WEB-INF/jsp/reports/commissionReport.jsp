<div class="reportHiddenTable">
	<table id="reportTable">
		<thead>
			<tr>
				<th>Date</th>
				<th>Initiator</th>
				<th>Bank Code</th>
				<th>Transaction Id</th>
				<th>Amount</th>
				<th>Transaction Type</th>
				<th>Total Commission</th>
				<th>Bank Commission</th>
				<th>Operator Commission</th>
				<th>Channel Partner Commission</th>
			</tr>
		</thead>
		<tbody>
			<tr ng-repeat="transaction in reportCommissionList">
				<td>{{transaction.createdDate}}</td>
				<td>{{transaction.originatorName}}</td>
				<td>{{transaction.bankCode}}</td>
				<td>{{transaction.transactionIdentifier}}</td>
				<td>{{transaction.amount}}</td>
				<td>{{transaction.service}}</td>
				<td>{{transaction.totalCommission}}</td>
				<td>{{transaction.bankCommission}}</td>
				<td>{{transaction.operatorCommissionAmount}}</td>
				<td>{{transaction.channelPartnerCommissionAmount}}</td>
			</tr>
		</tbody>
	</table>
	<table id="revenueReportTable">
		<thead>
			<tr>
				<th colspan="4" rowspan="2">Revenue Share Report</th>
			</tr>
			<tr style="display: none;" data-tableexport-display="always">
				<th colspan="4"></th>
			</tr>
			<tr ng-if='revenueReport.fromDate != null && revenueReport.fromDate != ""'>
				<td>From Date</td>
				<td colspan="3">{{revenueReport.fromDate}}</td>
			</tr>
			<tr ng-if='revenueReport.toDate != null && revenueReport.toDate != ""'>
				<td>To Date</td>
				<td colspan="3">{{revenueReport.toDate}}</td>
			</tr>
			<tr ng-if='revenueReport.bankName != null && revenueReport.bankName != ""'>
				<td>Bank</td>
				<td colspan="3">{{revenueReport.bankName}}</td>
			</tr>
			<tr ng-if='revenueReport.merchant != null && revenueReport.merchant != ""'>
				<td>Merchant</td>
				<td colspan="3">{{revenueReport.merchant}}</td>
			</tr>
			<tr style="display: none;" data-tableexport-display="always">
				<th colspan="4"></th>
			</tr>
			<tr>
				<th colspan="4">Summary</th>
			</tr>
			<tr>
				<td>No of Transactions</td>
				<td colspan="3">{{revenueReport.totalTransactions}}</td>
			</tr>
			<tr>
				<td>Total value of Transaction(NPR)</td>
				<td colspan="3">{{revenueReport.totalTransactionAmount}}</td>
			</tr>
			<tr>
				<td>Total Commission Earned(NPR)</td>
				<td colspan="3">{{revenueReport.totalCommissionEarned}}</td>
			</tr>
			<tr style="display: none;" data-tableexport-display="always">
				<th colspan="4"></th>
			</tr>
			<tr>
				<th>Transaction Type</th>
				<th>No Of transactions</th>
				<th>Value Of transactions(NPR)</th>
				<th>Commission Earned</th>
			</tr>
		</thead>
		<tbody>
			<tr ng-repeat="revenue in revenueReport.revenueList">
				<td>{{revenue.transactionType}}</td>
				<td>{{revenue.noOfTransaction}}</td>
				<td>{{revenue.valueOfTransaction}}</td>
				<td>{{revenue.commissionEarned}}</td>
			</tr>
		</tbody>
	</table>
</div>