var cartApp = angular.module("mywallet", []);

cartApp.controller("mycontroller", function($scope, $http) {

	function getbalance() {
		$http.post('/getBalance').success(function(data) {
			if (data.status == 'success') {
				$scope.myBalance = data.detail.balance;
			}
		});
	}
	getbalance();

	$http.post('/getMyName').success(function(data) {
		if (data.status == 'success') {
			if (data.message == 'customer') {
				$scope.myName = data.detail.fullName;
			} else {
				$scope.myName = data.detail.agencyName;
			}
		}
	});

	
	$scope.loadFundFromVouchure = function() {
	

		$http(
				{
					method : 'POST',
					url : '/loadfundfromvoucher',
					headers : {
						'Content-Type' : 'application/x-www-form-urlencoded'
					},
					transformRequest : function(obj) {
						var str = [];
						for ( var p in obj)
							str.push(encodeURIComponent(p) + "="
									+ encodeURIComponent(obj[p]));
						return str.join("&");
					},
					data : {
						vouchureId : $scope.vouchureId
					}
				}).success(function(data) {
			getbalance();
			
				$scope.vouchureloadMessage = data.message;
			
		
		});
	};
	
	
	$scope.peerTransfer = function() {

		$http(
				{
					method : 'POST',
					url : '/peertransfer',
					headers : {
						'Content-Type' : 'application/x-www-form-urlencoded'
					},
					transformRequest : function(obj) {
						var str = [];
						for ( var p in obj)
							str.push(encodeURIComponent(p) + "="
									+ encodeURIComponent(obj[p]));
						return str.join("&");
					},
					data : {
						p2pwalletid : $scope.p2pwalletid,
						p2pamount : $scope.p2pamount,
						owner : $scope.owner
					}
				}).success(function(data) {
			getbalance();
			if (data.status == 'validationError') {
				$scope.p2pmessage = data.detail.message;
			} else {
				$scope.p2pmessage = data.message;
			}
			if (data.status == 'success') {
				$scope.p2pwalletid = '';
				$scope.p2pamount = '';
				$scope.owner = '';
			}
		});
	};
	
	$scope.loadFund = function() {
	

		$http(
				{
					method : 'POST',
					url : '/loadfund',
					headers : {
						'Content-Type' : 'application/x-www-form-urlencoded'
					},
					transformRequest : function(obj) {
						var str = [];
						for ( var p in obj)
							str.push(encodeURIComponent(p) + "="
									+ encodeURIComponent(obj[p]));
						return str.join("&");
					},
					data : {
						financialIncId : $scope.financialIncId,
						finamount : $scope.finamount
					}
				}).success(function(data) {
			getbalance();
			
				$scope.p2pmessage = data.message;
			
			if (data.status == 'success') {
				$scope.financialIncId = '';
				$scope.finamount = '';
			}
		});
	};
	
	
	$scope.withdrawFund = function() {
	

		$http(
				{
					method : 'POST',
					url : '/withdrawfund',
					headers : {
						'Content-Type' : 'application/x-www-form-urlencoded'
					},
					transformRequest : function(obj) {
						var str = [];
						for ( var p in obj)
							str.push(encodeURIComponent(p) + "="
									+ encodeURIComponent(obj[p]));
						return str.join("&");
					},
					data : {
						financialIncId : $scope.financialIncId,
						finamount : $scope.finamount
					}
				}).success(function(data) {
			getbalance();
			
				$scope.p2pmessage = data.message;
			
			if (data.status == 'success') {
				$scope.financialIncId = '';
				$scope.finamount = '';
			}
		});
	};
	
	
});//end of controller
