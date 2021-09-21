<!--by amrit  -->
<div id="downloadSmsReport" class="reportHiddenTable">
<table id="sms_log_report">
<thead>
    <tr> <th> SMS BUSINESS REPORT</th></tr>
     <tr><th> Today :  {{today | date}}</th></tr>
    <tr><th> From Date : {{fromDate}}</th></td></tr>
     <tr><th> To Date : {{toDate}}</th></td></tr>
     <tr>
				<th>S No</th>
				<th>Bank</th>
				<th>Total SMS</th>
				<th>Total In coming SMS</th>
				<th>Total Out Going SMS</th>
			
	</tr>
	</thead>
	<tbody>
	    <tr ng-repeat="(key, values) in smsReport" >
	    <td ><p align="center">{{$index+1}}</p></td>
	    <td align="center">{{key}}</td>
	    
	    <td ng-repeat="value in values track by $index" align="center"><p align="center">{{value}}</p></td>
	    
	    </tr>
	
	</tbody>
</table>
</div>

