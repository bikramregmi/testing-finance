angular.module("tmkmobilebanking").controller("userController",
		function($scope,$http){

	
	
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

});