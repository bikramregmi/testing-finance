<!--by amrit  -->
<div id="downloadeBankingReport" class="reportHiddenTable">
<table id="ebanking_report" ng-repeat="(bank,report) in ebankingReport">
<thead>
    <tr> <th> NRB Directive Form No. 9.15</th></tr>
     <tr><th>{{bank}}</th></tr>
    <tr><th>Statement of e-Banking Service (Monthly)</th></tr>
     <tr><th>As on {{datePara}}</th></tr>
     <tr>
				<th>S No</th>
				<th>Particulars</th>
				<th>Up to Last month</th>
				<th>This month</th>
				<th>Total</th>
				<th>Remarks</th>
			
	</tr>
	</thead>
	<tbody>
     <tr><td style="text-align: center;"> <b>2</b></td><td align="center"><b>Mobile banking service:</b></td><td></td><td></td><td></td><td></td></tr>
     
	    <tr ng-repeat="values in report" >
					<td>{{values['sn']}}</td>
					<td>{{values['particulars']}}</td>
					<td>{{values['lastMonth']}}</td>
					<td>{{values['thisMonth']}}</td>
					<td>{{values['total']}}</td>
					<td>{{values['remarks']}}</td>
	   
	    
	    </tr>
	
	
	</tbody>
</table>
</div>

