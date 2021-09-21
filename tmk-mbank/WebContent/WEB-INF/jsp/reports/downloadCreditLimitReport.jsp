<div id="downloadCreditlimitReport" class="reportHiddenTable">
<table id="credit_limit_report">
<thead>
    <tr> <th> Credit Limit Report</th></tr>
     <tr><th> Today :  {{today | date}}</th></tr>
   
     <tr>
				<th>S No</th>
				<th>Bank </th>
				<th>Created Date</th>
				<th>Credit Limit Amount</th>
				
			
	</tr>
	</thead>
	<tbody >
	    <tr  ng-repeat="(key, values) in creditLimitReport" >
	    <td ><p align="center">{{$index+1}}</p></td>
	    <td>{{key}}</td>
	    <td ng-repeat="(x,y) in values track by $index">{{y}}</td>
	   
	    
	    </tr>
	
	</tbody>
</table>
</div>