<div id="downloadCreditlimitLedger" class="reportHiddenTable">
<table id="credit_limit_ledger">
<thead>
    <tr> <th> Credit Limit Update Ledger</th></tr>
     <tr><th> Today :  {{today | date}}</th></tr>
    <tr><th> From Date : {{fromDate}}</th></td></tr>
     <tr><th> To Date : {{toDate}}</th></td></tr>
     <tr>
				<th>S No</th>
				<th>Date </th>
				<th>Account Head</th>
				<th>Account Number</th>
				<th>Credit Limit Amount</th>
				<th>Remarks</th>
			
	</tr>
	</thead>
	<tbody  ><tr  ng-repeat="(key, values) in creditLimitLedger">
	          <td ><p align="center">{{key}}</p></td>
	          <td ng-repeat="value in values">{{value}}</td>

	    
	    </tr>
	
	</tbody>
</table>


</div>