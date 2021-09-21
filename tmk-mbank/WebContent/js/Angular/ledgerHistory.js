angular.module("tmkmobilebanking").controller('ledgerHistory',ledgerHistory);
function ledgerHistory($http,$scope){
/*	alert("hey");

	$scope.message="";

	$scope.bankPara="";
	$scope.fromDatePara="";
	$scope.toDatePara="";
	$scope.transactionTypePara="";*/
	
	$scope.loadLedger=function loadLedger(bank,fromDate,toDate,ledgerType,pageNumber){
		alert("oooi");
		
		var url="/ledger/load_ledger";
		$scope.ledgerReport={};
	    $scope.bankPara=bank;
		$scope.fromDatePara=fromDate;
		$scope.toDatePara=toDate;
		$scope.ledgerTypePara=ledgerType;
		alert($scope.fromDatePara);
		alert($scope.fromDatePara);
		
		try{
			let parameter={
					bank:$scope.bankPara,
					from_date:$scope.fromDatePara,
					to_date:$scope.toDatePara,
					ledger_type:$scope.ledgerTypePara,
					page_number:pageNumber
			};
			get(url,function(data){
				alert("ok");
				$scope.ledgerReport=data;
				alert($scope.ledgerReport);
			},parameter);
			
		}
		catch(message){
			
		}
	}
	function get(url, callback, parameter) {
		$http.get(url, {
			params : parameter
		}).then(function success(data) {
			if (callback)
				callback(data.data.detail);
		}, function error(data) {
			console.log("error", data);
			throw data.message;
		});
	}

	
}