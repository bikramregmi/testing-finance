<div class="reportHiddenTable">
	<table id="reportTable">
		<thead>
			<tr>
				<th>Date</th>
				<th>Customer Name</th>
				<th>Customer Mobile No</th>
				<th>Account No.</th>
				<th>Bank Code</th>
				<th>Target No.</th>
				<th>Transaction Id</th>
				<th>Amount</th>
				<th>Transaction Type</th>
				<th>Status</th>
				<th>Message</th>
				<c:if test="${isAdmin}">
					<th>Total Commission</th>
					<th>Operator Commission</th>
					<th>Bank Commission</th>
					<th>Channel Partner Commission</th>
					<th>Operator Settlement</th>
					<th>Bank Settlement</th>
					<th>Channel Partner Settlement</th>
				</c:if>
			</tr>
		</thead>
		<tbody>
			<tr ng-repeat="transaction in reportTransactionList">
				<td>{{transaction.createdDate}}</td>
				<td>{{transaction.originatorName}}</td>
				<td>&#8203;{{transaction.originatorMobileNo}}</td>
				<td>&#8203;{{transaction.sourceAccount}}</td>
				<td>{{transaction.bankCode}}</td>
				<td>&#8203;{{transaction.destination}}</td>
				<td>&#8203;{{transaction.transactionIdentifier}}</td>
				<td>{{transaction.amount}}</td>
				<td>{{transaction.service}}</td>
				<td>{{transaction.transactionStatus}}</td>
				<td>{{transaction.responseMessage}}</td>
				<c:if test="${isAdmin}">
					<td>{{transaction.totalCommission}}</td>
					<td>{{transaction.operatorCommissionAmount }}</td>
					<td>{{transaction.bankCommission}}</td>
					<td>{{transaction.channelPartnerCommissionAmount}}</td>
					<td>{{transaction.operatorSettlement}}</td>
					<td>{{transaction.bankSettlement}}</td>
					<td>{{transaction.channelPartnerSettlement}}</td>
				</c:if>
			</tr>
		</tbody>
	</table>
	<table id="reversalReportTable">
		<thead>
			<tr>
				<th colspan="7">Reverse Transaction Report</th>
			</tr>
			<tr>
				<th colspan="2">From Date</th>
				<th colspan="5">{{reversalReport.fromDate}}</th>
			</tr>
			<tr>
				<th colspan="2">To Date</th>
				<th colspan="5">{{reversalReport.toDate}}</th>
			</tr>
			<tr>
				<th colspan="2">Bank</th>
				<th colspan="5">{{reversalReport.bank}}</th>
			</tr>
			<tr>
				<th>Mobile Number</th>
				<th>A/C Number</th>
				<th>Bank</th>
				<th>Amount</th>
				<th>Transaction Type</th>
				<th>Ref. Stan</th>
				<th>Date</th>
			</tr>
		</thead>
		<tbody>
			<tr ng-repeat="transaction in reversalReport.transactionList">
				<td>{{transaction.mobileNo}}</td>
				<td>{{transation.accountNo}}</td>
				<td>{{transaction.bank}}</td>
				<td>{{transaction.amount}}</td>
				<td>{{transaction.transactionType}}</td>
				<td>&#8203;{{transaction.refstan}}</td>
				<td>{{transaction.date}}</td>
			</tr>
		</tbody>
		<tbody></tbody>
	</table>
</div>