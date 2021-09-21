<div class="reportHiddenTable">
	<table id="customerRegistrationReport">
		<thead>
			<tr>
				<th colspan=9 rowspan=2>Customer Registration Report</th>
			</tr>
			<tr>
				<th rowspan=0></th>
			</tr>
			<tr>
				<th colspan=4>From Date</th>
				<th colspan=5>{{registrationReport.fromDate}}</th>
			</tr>
			<tr>
				<th colspan=4>To Date</th>
				<th colspan=5>{{registrationReport.toDate}}</th>
			</tr>
			<tr>
				<th colspan=4>Bank Code</th>
				<th colspan=5>{{registrationReport.bankCode}}</th>
			</tr>
			<tr>
				<th colspan=4>Branch Code</th>
				<td colspan=5>{{registrationReport.branchCode}}</td>
			</tr>
			<tr>
				<th colspan=4>Total Registered Users</th>
				<th colspan=5>{{registrationReport.totalRegisteredCustomer}}</th>
			</tr>
			<tr>
				<th colspan=4>Loan A/C</th>
				<th colspan=5>{{registrationReport.loanAccount}}</th>
			</tr>
			<tr>
				<th colspan=4>Saving A/C</th>
				<th colspan=5>{{registrationReport.savingAccount}}</th>
			</tr>
			<tr>
				<th colspan=4>Current A/C</th>
				<th colspan=5>{{registrationReport.currentAccount}}</th>
			</tr>
			<tr>
				<th colspan=4>OD A/C</th>
				<th colspan=5>{{registrationReport.odAccount}}</th>
			</tr>
			<tr>
				<th colspan=9 rowspan=2>User Status</th>
			</tr>
			<tr>
				<th rowspan=0></th>
			</tr>
			<tr>
				<th colspan=4>Active User(Till Date)</th>
				<th colspan=5>{{registrationReport.activeUser}}</th>
			</tr>
			<tr>
				<th colspan=4>Blocked User(Till Date)</th>
				<th colspan=5>{{registrationReport.blockedUser}}</th>
			</tr>
			<tr>
				<th>S No</th>
				<th>Mobile No</th>
				<th>Customer Name</th>
				<th>Registration Date</th>
				<th>A/C No</th>
				<th>A/C Type</th>
				<th>Status</th>
				<th>Bank Code</th>
				<th>Branch Code</th>
			</tr>
		</thead>
		<tbody>
			<tr ng-repeat="customer in registrationReport.customerList">
				<td>{{$index+1}}</td>
				<td>{{customer.mobileNumber}}</td>
				<td>{{customer.fullName}}</td>
				<td>{{customer.created}}</td>
				<td><span ng-repeat="account in customer.accountDetail"> &#8203;{{account.accountNumber}}<span ng-if="customer.accountDetail.length != $index+1">,
					</span>
				</span></td>
				<td><span ng-repeat="account in customer.accountDetail"> {{account.accountType}}<span ng-if="customer.accountDetail.length != $index+1">, </span>
				</span></td>
				<td>{{customer.status}}</td>
				<td>{{customer.bankCode}}</td>
				<td>&#8203;{{customer.bankBranchCode}}</td>
			</tr>
		</tbody>
	</table>
</div>