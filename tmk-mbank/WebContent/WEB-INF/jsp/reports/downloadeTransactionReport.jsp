<!--by amrit  -->
<div id="downloadeTransactionReport" class="reportHiddenTable">
<table id="etransaction_report" ng-repeat="(bank,report) in etransactionReport">
<thead>
    <tr> <th colspan="10"> Electronic transaction</th></tr>
     <tr><th colspan="10">Electronic Transaction (9.15 B)-BFIs</th></tr>
    <tr><th colspan="10">Institution Name: {{bank}}</th></td></tr>
     <tr><th colspan="10"> Statement of Electronic transaction institution wise</th></td></tr>
     <tr>
				<th  rowspan="2">Channel Used for transaction</th>
				<th rowspan="2">Form of transaction</th>
			   <th colspan="4">No. of transaction (Reporting month only)</th>
			    <th colspan="4">No. of transaction (Reporting month only)</th>
			    </tr>
				<tr >
				<th> &#8918; Rs.5000 </th><th> Rs.5000-Rs.1000 </th><th> &#8919; Rs.10000</th><th>Total</th>
				<th> &#8918; Rs.5000 </th><th> Rs.5000-Rs.1000 </th><th> &#8919; Rs.10000</th><th>Total</th>
		       </tr>
		       
	</thead>
	<tbody>
	    <tr ng-repeat="values in report"  ng-repeat="(key,value) in values track by $index">
	    
	    <td align="center"><p align="center">{{values['transaction_channel']}}</td>
	    <td align="center"><p align="center">{{values['transaction_form']}}</td>
	       <td >{{values['this_month_transaction_detail'].less_than_5k}}</td>
	  
	  
	    <td> {{values['this_month_transaction_detail'].between_5k_10k}}</td>
	    <td> {{values['this_month_transaction_detail'].greater_than_10k}}</td>
	    <td> {{values['this_month_transaction_detail'].total}}</td>
	 
	    <td align="center"><p align="center">{{values['previous_month_transaction_detail'].less_than_5k}}</p></td>
	    <td> {{values['previous_month_transaction_detail'].between_5k_10k}}</td>
	    <td> {{values['previous_month_transaction_detail'].greater_than_10k}}</td>
	    <td> {{values['previous_month_transaction_detail'].total}}</td>
	  
	    </tr>
	
	</tbody>
</table>
</div>

