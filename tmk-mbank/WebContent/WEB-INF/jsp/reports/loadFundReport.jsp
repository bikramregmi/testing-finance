<div class="reportHiddenTable">
	<table id="loadFundReport" class="table table-border">
		<thead>
			<tr>
				<th colspan=5 rowspan=2>mBank Bank Account Balance</th>
			</tr>
			<tr>
				<th rowspan=0></th>
			</tr>
			<tr>
				<th colspan=2>From Date</th>
				<th colspan=3>{{loadFundReport.fromDate}}</th>
			</tr>
			<tr>
				<th colspan=2>To Date</th>
				<th colspan=3>{{loadFundReport.toDate}}</th>
			</tr>
			<tr>
				<th colspan=2>Total Bank</th>
				<th colspan=3>{{loadFundReport.totalBank}}</th>
			</tr>
			<tr>
				<th>Bank Name</th>
				<th>Bank Code</th>
				<th>Amount</th>
				<th>Status</th>
				<th>Date</th>
			</tr>
		</thead>
		<tbody>
			<tr ng-repeat="ledger in loadFundReport.ledgerList">
				<td>{{ledger.bankName}}</td>
				<td>{{ledger.bankCode}}</td>
				<td>{{ledger.amount}}</td>
				<td>{{ledger.status}}</td>
				<td>{{ledger.date}}</td>
			</tr>
		</tbody>
	</table>
</div>