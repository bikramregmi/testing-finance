<div class="reportHiddenTable">
	<table id="businessSummaryReportTable">
		<thead>
			<tr>
				<th colspan="{{businessSummaryReport.serviceCategoryList.length + 7}}">Business Summary Report</th>
			</tr>
			</thead>
			<tbody>
			<tr>
				<th colspan="{{businessSummaryReport.serviceCategoryList.length + 7}}">{{businessSummaryReport.date}}</th>
			</tr>
			<tr>
				<th colspan="{{businessSummaryReport.serviceCategoryList.length + 7}}"></th>
			</tr>
			<tr>
				<th colspan=3>Duration</th>
				<th colspan="{{businessSummaryReport.serviceCategoryList.length + 4}}">Active Registration</th>
			</tr>
			<tr>
				<td colspan=3>Till Date</td>
				<td colspan="{{businessSummaryReport.serviceCategoryList.length + 4}}">{{businessSummaryReport.activeRegistration.tillDate}}</td>
			</tr>
			<tr>
				<td colspan=3>Last 30 Days</td>
				<td colspan="{{businessSummaryReport.serviceCategoryList.length + 4}}">{{businessSummaryReport.activeRegistration.thirtyDays}}</td>
			</tr>
			<tr>
				<td colspan=3>Last 7 Days</td>
				<td colspan="{{businessSummaryReport.serviceCategoryList.length + 4}}">{{businessSummaryReport.activeRegistration.sevenDays}}</td>
			</tr>
			<tr>
				<td colspan=3>Last 1 Day</td>
				<td colspan="{{businessSummaryReport.serviceCategoryList.length + 4}}">{{businessSummaryReport.activeRegistration.oneDay}}</td>
			</tr>
			<tr>
				<th colspan="{{businessSummaryReport.serviceCategoryList.length + 7}}"></th>
			</tr>
			<tr>
				<th colspan=3>Duration</th>
				<th colspan="{{businessSummaryReport.serviceCategoryList.length + 4}}">Blocked Registration</th>
			</tr>
			<tr>
				<td colspan=3>Till Date</td>
				<td colspan="{{businessSummaryReport.serviceCategoryList.length + 4}}">{{businessSummaryReport.blockedRegistration}}</td>
			</tr>
			<tr>
				<th colspan="{{businessSummaryReport.serviceCategoryList.length + 7}}"></th>
			</tr>
			<tr>
				<th colspan=3>Duration</th>
				<th colspan="{{businessSummaryReport.serviceCategoryList.length/2 + 2}}">Transaction Volume</th>
				<th colspan="{{businessSummaryReport.serviceCategoryList.length/2 + 2}}">Transaction Value</th>
			</tr>
			<tr>
				<td colspan=3>Till Date</td>
				<td colspan="{{businessSummaryReport.serviceCategoryList.length/2 + 2}}">{{businessSummaryReport.transaction.transactionVolume.tillDate}}</td>
				<td colspan="{{businessSummaryReport.serviceCategoryList.length/2 + 2}}">{{businessSummaryReport.transaction.transactionValue.tillDate}}</td>
			</tr>
			<tr>
				<td colspan=3>Last 30 Days</td>
				<td colspan="{{businessSummaryReport.serviceCategoryList.length/2 + 2}}">{{businessSummaryReport.transaction.transactionVolume.thirtyDays}}</td>
				<td colspan="{{businessSummaryReport.serviceCategoryList.length/2 + 2}}">{{businessSummaryReport.transaction.transactionValue.thirtyDays}}</td>
			</tr>
			<tr>
				<td colspan=3>Last 7 Days</td>
				<td colspan="{{businessSummaryReport.serviceCategoryList.length/2 + 2}}">{{businessSummaryReport.transaction.transactionVolume.sevenDays}}</td>
				<td colspan="{{businessSummaryReport.serviceCategoryList.length/2 + 2}}">{{businessSummaryReport.transaction.transactionValue.sevenDays}}</td>
			</tr>
			<tr>
				<td colspan=3>Last 1 Day</td>
				<td colspan="{{businessSummaryReport.serviceCategoryList.length/2 + 2}}">{{businessSummaryReport.transaction.transactionVolume.oneDay}}</td>
				<td colspan="{{businessSummaryReport.serviceCategoryList.length/2 + 2}}">{{businessSummaryReport.transaction.transactionValue.oneDay}}</td>
			</tr>
			<tr>
				<th colspan="{{businessSummaryReport.serviceCategoryList.length + 7}}"></th>
			</tr>
			<tr>
				<th colspan=2>Duration</th>
				<th>Balance Check</th>
				<th>Mini-Statement</th>
				<th>Fund Transfer</th>
				<th>Cheque Book Request</th>
				<th>Cheque Block</th>
				<th ng-repeat="category in businessSummaryReport.serviceCategoryList">
					{{category.name}}
				</th>
			</tr>
			<tr>
				<td colspan=2>Till Date</td>
				<td>{{businessSummaryReport.balanceCheck.tillDate}}</td>
				<td>{{businessSummaryReport.miniStatement.tillDate}}</td>
				<td>{{businessSummaryReport.fundTransfer.tillDate}}</td>
				<td>{{businessSummaryReport.chequeBook.tillDate}}</td>
				<td>{{businessSummaryReport.chequeBlock.tillDate}}</td>
				<td ng-repeat="category in businessSummaryReport.serviceCategoryList">
					{{category.tillDate}}
				</td>
			</tr>
			<tr>
				<td colspan=2>Last 30 Days</td>
				<td>{{businessSummaryReport.balanceCheck.thirtyDays}}</td>
				<td>{{businessSummaryReport.miniStatement.thirtyDays}}</td>
				<td>{{businessSummaryReport.fundTransfer.thirtyDays}}</td>
				<td>{{businessSummaryReport.chequeBook.thirtyDays}}</td>
				<td>{{businessSummaryReport.chequeBlock.thirtyDays}}</td>
				<td ng-repeat="category in businessSummaryReport.serviceCategoryList">
					{{category.thirtyDays}}
				</td>
			</tr>
			<tr>
				<td colspan=2>Last 7 Days</td>
				<td>{{businessSummaryReport.balanceCheck.sevenDays}}</td>
				<td>{{businessSummaryReport.miniStatement.sevenDays}}</td>
				<td>{{businessSummaryReport.fundTransfer.sevenDays}}</td>
				<td>{{businessSummaryReport.chequeBook.sevenDays}}</td>
				<td>{{businessSummaryReport.chequeBlock.sevenDays}}</td>
				<td ng-repeat="category in businessSummaryReport.serviceCategoryList">
					{{category.sevenDays}}
				</td>
			</tr>
			<tr>
				<td colspan=2>Last 1 Day</td>
				<td>{{businessSummaryReport.balanceCheck.oneDay}}</td>
				<td>{{businessSummaryReport.miniStatement.oneDay}}</td>
				<td>{{businessSummaryReport.fundTransfer.oneDay}}</td>
				<td>{{businessSummaryReport.chequeBook.oneDay}}</td>
				<td>{{businessSummaryReport.chequeBlock.oneDay}}</td>
				<td ng-repeat="category in businessSummaryReport.serviceCategoryList">
					{{category.oneDay}}
				</td>
			</tr>
			<tr>
				<th colspan="{{businessSummaryReport.serviceCategoryList.length + 7}}"></th>
			</tr>
			<tr>
				<th colspan=3>Duration</th>
				<th colspan="{{businessSummaryReport.serviceCategoryList.length + 4}}">Revenue Share Earning(NPR)</th>
			</tr>
			<tr>
				<td colspan=3>Till Date</td>
				<td colspan="{{businessSummaryReport.serviceCategoryList.length + 4}}">{{businessSummaryReport.revenue.oneDay}}</td>
			</tr>
			<tr>
				<td colspan=3>Last 30 Days</td>
				<td colspan="{{businessSummaryReport.serviceCategoryList.length + 4}}">{{businessSummaryReport.revenue.thirtyDays}}</td>
			</tr>
			<tr>
				<td colspan=3>Last 7 Days</td>
				<td colspan="{{businessSummaryReport.serviceCategoryList.length + 4}}">{{businessSummaryReport.revenue.sevenDays}}</td>
			</tr>
			<tr>
				<td colspan=3>Last 1 Day</td>
				<td colspan="{{businessSummaryReport.serviceCategoryList.length + 4}}">{{businessSummaryReport.revenue.oneDay}}</td>
			</tr>
		</tbody>
	</table>
</div>